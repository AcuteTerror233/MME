package com.acuteterror233.mite.mixin.world.entity.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code Player} — Extends player behavior: attribute rework, level-scaled max
 * health, doubled experience curve, and heavier attack exhaustion.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code createAttributes} injection (RETURN, cancellable): base max health 6,
 *       block interaction range 3, entity interaction range 1.5.</li>
 *   <li>{@code getXpNeededForNextLevel} injection: every level costs double the
 *       vanilla experience.</li>
 *   <li>{@code tick} injection (HEAD): max health grows with experience level
 *       (6 + 2 per 5 levels, clamped to 6..20) and stays in sync with the food cap
 *       via {@link #setMaxHealth}.</li>
 *   <li>{@code attack} injection (at {@code hurtOrSimulate}): adds 0.5 exhaustion
 *       whenever an attack lands.</li>
 *   <li>{@code blockUsingItem} redirect: shields are disabled for at least 0.25s,
 *       even when the attacker's weapon defines no disable time.</li>
 *   <li>{@code getBaseExperienceReward} redirect: vanilla's {@code Math.min} cap on
 *       the base experience reward is removed (first operand returned as-is).</li>
 * </ul>
 */
@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public int experienceLevel;
    @Shadow
    public abstract FoodData getFoodData();

    @Shadow
    protected FoodData foodData;

    @Shadow
    public abstract void causeFoodExhaustion(float amount);

    /**
     * Substitutes the shield-disable duration: if the attacking entity defines none
     * (0), 0.25 seconds is used so any hit briefly disables blocking.
     */
    @Redirect(method = "blockUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getSecondsToDisableBlocking()F"))
    public float blockUsingItem(LivingEntity instance) {
        float v = instance.getSecondsToDisableBlocking();
        return v > 0 ? v : 0.25F;
    }

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    /** Adds the reworked base attributes (max health 6, block range 3, entity range 1.5). */
    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void createPlayerAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.BLOCK_INTERACTION_RANGE, 3)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 1.5)
        );
    }

    /** Doubles the experience required for each level-up. */
    @Inject(method = "getXpNeededForNextLevel", at = @At("RETURN"), cancellable = true)
    public void getNextLevelExperience(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 2);
    }

    /** Applies the level-scaled max health cap every tick. */
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        // Max health: 6 base, +2 per 5 experience levels, clamped to [6, 20]
        int maxHealth = Math.clamp(6 + (this.experienceLevel / 5) * 2, 6, 20);
        this.setMaxHealth(maxHealth);
    }

    /**
     * Applies a new cap to both the MAX_HEALTH attribute and the food data, so health
     * and hunger always share the same limit; no-op when both already match.
     *
     * @param max the new shared cap (health and hunger)
     */
    @Unique
    public void setMaxHealth(int max) {
        AttributeInstance instance = this.getAttributes().getInstance(Attributes.MAX_HEALTH);
        int maxHealthBaseValue = (int) instance.getBaseValue();
        int maxFoodLevel = this.getFoodData().MME$GetMaxFoodLevel();
        if (maxHealthBaseValue != max || maxFoodLevel != max) {
            instance.setBaseValue(max);
            this.getFoodData().MME$SetMaxFoodLevel(max);
        }
    }

    /** Adds 0.5 food exhaustion when an attack connects. */
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public void attack(Entity entity, CallbackInfo ci) {
        this.causeFoodExhaustion(0.5F);
    }

    /** Removes vanilla's {@code Math.min} cap by always returning the first operand. */
    @Redirect(method = "getBaseExperienceReward", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    public int getBaseExperienceReward(int a, int b) {
        return a;
    }

    @Inject(method = "cannotAttack", at = @At("HEAD"), cancellable = true)
    private void cannotAttack(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (this.getFoodData().getFoodLevel() <= 0) {
            cir.setReturnValue(false);
        }
    }

}
