package com.acuteterror233.mite.mixin.world.entity.player;

import com.acuteterror233.mite.interfaces.FoodDataExtension;
import com.acuteterror233.mite.world.effect.MMEMobEffects;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code FoodData} — Implements the nutrition system extension
 * ({@link FoodDataExtension}): a capped food level plus protein/fiber/sugar tracking.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code maxFoodLevel} (default 6) caps {@code needsFood}, {@code add},
 *       {@code setFoodLevel} and {@code setSaturation}; it is kept in sync with the
 *       player's max health cap.</li>
 *   <li>Regeneration: 1 HP per 1280 ticks (8× faster while sleeping), heal delay doubled
 *       under MALNUTRITION; hunger drains via 4 exhaustion per 1280 ticks (half the
 *       interval under MALNUTRITION); at zero food the player starves for 1 HP every
 *       300 ticks.</li>
 *   <li>Protein/fiber deplete 1 per tick; when either is exhausted the player gains
 *       permanent MALNUTRITION (creative players are exempt).</li>
 *   <li>Sugar depletes 1 per tick; above 48000/96000/144000 the player enters diabetes
 *       tier 1/2/3+, carried by a single INSULIN_RESISTANCE ("Diabetes") buff whose
 *       amplifier encodes the tier. Tier 1 pulses blindness (3 s) every 600 ticks,
 *       tier 2 pulses nausea + blindness (3 s) every 300 ticks, tier 3+ applies
 *       permanent nausea + blindness + wither. Tier changes wipe the previous
 *       symptoms and re-arm the pulse timer to a full cycle.</li>
 *   <li>maxFoodLevel and all nutrient counters persist through NBT.</li>
 * </ul>
 */
@Mixin(FoodData.class)
public abstract class FoodDataMixin implements FoodDataExtension {
    /** Shared health/hunger cap (mirrors the player's max health). */
    @Unique
    private int maxFoodLevel = 6;
    /** Ticks accumulated toward the next natural heal (×8 while sleeping). */
    @Unique
    private int healTickTimer = 0;
    /** Ticks accumulated while starving, until the next starvation damage tick. */
    @Unique
    private int damageTickTimer = 0;
    @Mutable
    @Shadow
    private int foodLevel = 6;
    @Shadow
    private float saturationLevel;
    @Shadow
    private float exhaustionLevel;
    @Shadow
    private int tickTimer;
    /** Stored protein; 1 is consumed per tick, depletion causes MALNUTRITION. */
    @Unique
    private float protein = 100000;
    /** Stored fiber; 1 is consumed per tick, depletion causes MALNUTRITION. */
    @Unique
    private float fiber = 100000;
    /** Stored sugar; 1 is consumed per tick, high levels cause staged effects. */
    @Unique
    private float sugar = 0;
    /** Sugar stage thresholds: diabetes tier 1, 2 and 3 respectively. */
    @Unique
    private static final float sugar_threshold_1 = 48000;
    @Unique
    private static final float sugar_threshold_2 = 96000;
    @Unique
    private static final float sugar_threshold_3 = 144000;
    /** Diabetes tier-1 pulse interval: one blindness pulse every 600 ticks (30 s). */
    @Unique
    private static final int DIABETES_PULSE_INTERVAL_T1 = 600;
    /** Diabetes tier-2 pulse interval: one nausea + blindness pulse every 300 ticks (15 s). */
    @Unique
    private static final int DIABETES_PULSE_INTERVAL_T2 = 300;
    /** Duration of a single pulsed diabetes symptom (60 ticks = 3 s). */
    @Unique
    private static final int SYMPTOM_PULSE_TICKS = 60;
    /** Ticks until the next pulsed diabetes symptom; re-armed to a full cycle on every tier change. */
    @Unique
    private int diabetesPulseTimer = 0;
    @Shadow
    public abstract int getFoodLevel();

    @Shadow
    public abstract void addExhaustion(float amount);

    /** Returns the capped maximum food level (shared with max health). */
    @Unique
    @Override
    public int MME$GetMaxFoodLevel() {
        return this.maxFoodLevel;
    }

    /** Sets the capped maximum food level (shared with max health). */
    @Unique
    @Override
    public void MME$SetMaxFoodLevel(int maxFoodLevel) {
        this.maxFoodLevel = maxFoodLevel;
    }
    /**
     * @author AcuteTerror233
     * @reason Modified the not-full check
     */
    @Overwrite
    public boolean needsFood() {
        return this.foodLevel < maxFoodLevel;
    }

    /**
     * @author AcuteTerror233
     * @reason Added max food level check
     */
    @Overwrite
    private void add(int nutrition, float saturation) {
        this.foodLevel = Mth.clamp(nutrition + this.foodLevel, 0, maxFoodLevel);
        this.saturationLevel = Mth.clamp(saturation + this.saturationLevel, 0.0F, (float) this.foodLevel);
    }

    /**
     * @author AcuteTerror233
     * @reason Added max food level check
     */
    @Overwrite
    public void tick(ServerPlayer player) {
        ServerLevel serverWorld = player.level();
        Difficulty difficulty = serverWorld.getDifficulty();
        // Exhaustion drain: saturation is consumed first, then the food level itself
        if (this.exhaustionLevel > 4.0F) {
            this.exhaustionLevel -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        boolean bl = serverWorld.getGameRules().get(GameRules.NATURAL_HEALTH_REGENERATION);
        if (this.foodLevel > 0) {
            this.tickTimer++;
            if (this.damageTickTimer != 0) {
                this.damageTickTimer = 0;
            }

            if (player.isSleeping()) {
                // Sleeping regenerates 8× faster
                this.healTickTimer += 8;
            }else {
                this.healTickTimer++;
            }

            // Natural heal: 1 HP per 1280 ticks, doubled delay under MALNUTRITION
            if (this.healTickTimer >= (1280 * (player.hasEffect(MMEMobEffects.MALNUTRITION) ? 2 : 1)) && bl){
                player.heal(1.0F);
                this.healTickTimer = 0;
            }

            // Hunger drain: 4 exhaustion per 1280 ticks, halved interval under MALNUTRITION
            if (this.tickTimer >= (1280 * (player.hasEffect(MMEMobEffects.MALNUTRITION) ? 0.5 : 1))){
                this.tickTimer = 0;
                if (player.gameMode() != GameType.CREATIVE) {
                    addExhaustion(4);
                }
            }

        }else {
            if (this.healTickTimer !=0 ){
                this.healTickTimer = 0;
            }

            if (this.tickTimer > 0.0F) {
                this.tickTimer = 0;
            }

            // Starvation: 1 damage every 300 ticks with an empty food bar
            this.damageTickTimer++;
            if (this.damageTickTimer >= 300) {
                player.hurtServer(serverWorld, player.damageSources().starve(), 1.0F);
                this.damageTickTimer = 0;
            }
        }
        updatePlayerEffects(player);
    }

    /** Applies the nutrition-driven effects: malnutrition from depleted protein/fiber and sugar stages. */
    @Unique
    public void updatePlayerEffects(ServerPlayer player) {
        handleNutrientEffect(player, MMEMobEffects.MALNUTRITION);

        handleSugar(player);
    }

    /**
     * Depletes protein/fiber by 1 per tick and toggles MALNUTRITION when either runs
     * out; creative players are kept exempt and their stores never fully deplete.
     */
    @Unique
    private void handleNutrientEffect(ServerPlayer player, Holder<MobEffect> effect) {
        if (!player.hasInfiniteMaterials()){
            if (this.fiber > 0) {
                this.fiber--;
            }
            if (this.protein > 0) {
                this.protein--;
            }
            if (this.fiber <= 0 || this.protein <= 0){
                if (!player.hasEffect(effect)){
                    player.addEffect(new MobEffectInstance(effect , -1 , 0, true, false));
                }
            }else {
                if (player.hasEffect(effect)) {
                    player.removeEffect(effect);
                }
            }
        }else {
            if (this.fiber <= 0) {
                this.fiber = 1;
            }
            if (this.protein <= 0) {
                this.protein = 1;
            }
            if (player.hasEffect(effect)) {
                player.removeEffect(effect);
            }
        }
    }

    /** Depletes sugar by 1 per tick and runs the tiered diabetes scheduler. */
    @Unique
    private void handleSugar(ServerPlayer player) {
        if (player.hasInfiniteMaterials()) {
            clearDiabetes(player);
            return;
        }
        if (this.sugar > 0) this.sugar--;
        // Map sugar to a diabetes tier 1-3 (0 = healthy); amplifier = tier - 1, capped at 2
        int tier = this.sugar > sugar_threshold_3 ? 3 : this.sugar > sugar_threshold_2 ? 2 : this.sugar > sugar_threshold_1 ? 1 : 0;
        if (tier == 0) {
            if (player.hasEffect(MMEMobEffects.INSULIN_RESISTANCE)) {
                clearDiabetes(player);
            }
            return;
        }
        applyDiabetesStage(player, tier);
    }

    /**
     * Ensures the diabetes buff matches the given tier, then runs its symptom schedule.
     * On a tier change the previous symptoms are wiped and the pulse timer is re-armed
     * to a full cycle so switching stages never fires a symptom immediately.
     */
    @Unique
    private void applyDiabetesStage(ServerPlayer player, int tier) {
        MobEffectInstance current = player.getEffect(MMEMobEffects.INSULIN_RESISTANCE);
        if (current != null && current.getAmplifier() == tier - 1) {
            runDiabetesSchedule(player, tier);
            return;
        }
        clearDiabetes(player);
        player.addEffect(new MobEffectInstance(MMEMobEffects.INSULIN_RESISTANCE, -1, tier - 1, true, false), player);
        this.diabetesPulseTimer = diabetesPulseInterval(tier);
        runDiabetesSchedule(player, tier);
    }

    /**
     * Executes one scheduler step for the given tier: pulsed symptoms for tiers 1-2,
     * permanent symptoms for tier 3+.
     */
    @Unique
    private void runDiabetesSchedule(ServerPlayer player, int tier) {
        if (tier >= 3) {
            // Tier 3+: permanent symptom set; only added when missing so infinite
            // instances are never re-added and no per-tick churn occurs
            ensureEffect(player, MobEffects.NAUSEA, 0);
            ensureEffect(player, MobEffects.BLINDNESS, 0);
            ensureEffect(player, MobEffects.WITHER, 0);
            return;
        }
        if (--this.diabetesPulseTimer > 0) {
            return;
        }
        this.diabetesPulseTimer = diabetesPulseInterval(tier);
        // Pulsed symptoms are transient and fully visible so the player can read the attack
        if (tier == 1) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, SYMPTOM_PULSE_TICKS, 0));
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, SYMPTOM_PULSE_TICKS, 0));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, SYMPTOM_PULSE_TICKS, 0));
        }
    }

    /** Removes the diabetes buff and every scheduled symptom (also fixes the old stage-down BLINDNESS leak). */
    @Unique
    private static void clearDiabetes(ServerPlayer player) {
        player.removeEffect(MMEMobEffects.INSULIN_RESISTANCE);
        player.removeEffect(MobEffects.BLINDNESS);
        player.removeEffect(MobEffects.NAUSEA);
        player.removeEffect(MobEffects.WITHER);
    }

    /** Adds an infinite-duration effect only when the player does not already have it. */
    @Unique
    private static void ensureEffect(ServerPlayer player, Holder<MobEffect> effect, int amplifier) {
        if (!player.hasEffect(effect)) {
            player.addEffect(new MobEffectInstance(effect, -1, amplifier, true, false), player);
        }
    }

    /** Pulse interval in ticks for a tier: 600 (30 s) for tier 1, 300 (15 s) for tier 2+. */
    @Unique
    private static int diabetesPulseInterval(int tier) {
        return tier <= 1 ? DIABETES_PULSE_INTERVAL_T1 : DIABETES_PULSE_INTERVAL_T2;
    }

    /** Restores maxFoodLevel and the nutrient counters from NBT. */
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(ValueInput nbt, CallbackInfo ci) {
        this.maxFoodLevel = nbt.getIntOr("maxFoodLevel", 6);
        this.fiber = nbt.getFloatOr("fiber", 100000);
        this.protein = nbt.getFloatOr("protein", 100000);
        this.sugar = nbt.getFloatOr("sugar", 0);
    }

    /** Persists maxFoodLevel and the nutrient counters to NBT. */
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(ValueOutput nbt, CallbackInfo ci) {
        nbt.putInt("maxFoodLevel", this.maxFoodLevel);
        nbt.putFloat("fiber", this.fiber);
        nbt.putFloat("protein", this.protein);
        nbt.putFloat("sugar", this.sugar);
    }

    /**
     * @author AcuteTerror233
     * @reason Added max food level check
     */
    @Overwrite
    public void setFoodLevel(int i) {
        this.foodLevel = Math.min(i, maxFoodLevel);
    }

    /**
     * @author AcuteTerror233
     * @reason Added max food level check
     */
    @Overwrite
    public void setSaturation(float f) {
        this.saturationLevel = f > maxFoodLevel ? maxFoodLevel : f;
    }

    /** Adds the given food's protein, fiber and sugar values to the counters. */
    @Override
    public void MME$AddFoodNutrition(FoodNutrition foodNutrition) {
        addFiber((int) foodNutrition.fiber());
        addProtein((int) foodNutrition.protein());
        addSugar((int) foodNutrition.sugar());
    }

    /** Food is sufficient while the current level is above 0. */
    @Overwrite
    public boolean hasEnoughFood() {
        return this.getFoodLevel() > 0.0F;
    }

    /** Returns the current protein/fiber/sugar values. */
    @Override
    public FoodNutrition MME$GetFoodNutrition() {
        return new FoodNutrition(this.protein, this.fiber, this.sugar);
    }

    /** Adds fiber, capped at 160000. */
    @Unique
    public void addFiber(int i) {
        this.fiber = Math.min(160000, this.fiber + i);
    }
    /** Adds protein, capped at 160000. */
    @Unique
    public void addProtein(int i) {
        this.protein = Math.min(160000, this.protein + i);
    }
    /** Adds sugar, capped at 192000. */
    @Unique
    public void addSugar(int i) {
        this.sugar = Math.min(192000, this.sugar + i);
    }

}