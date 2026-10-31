package com.acuteterror233.mite.mixin.world.entity.player;

import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import com.acuteterror233.mite.world.effect.curse.CurseCap;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import com.acuteterror233.mite.world.effect.curse.CurseState;
import com.acuteterror233.mite.world.effect.curse.MMECurses;
import com.acuteterror233.mite.world.player.PlayerLevelScaling;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Mixin for {@code Player} — Extends player behavior: attribute rework, level-scaled max
 * health, tripled experience curve, and heavier attack exhaustion.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code createAttributes} injection (RETURN, cancellable): base max health 6,
 *       block interaction range 3, entity interaction range 1.5.</li>
 *   <li>{@code getXpNeededForNextLevel} injection: every level costs triple the
 *       vanilla experience.</li>
 *   <li>{@code tick} injection (HEAD): max health grows with experience level
 *       (6 + 2 per 5 levels, clamped to 6..20) and stays in sync with the food cap
 *       via {@link #setMaxHealth}.</li>
 *   <li>{@code attack} injection (at {@code hurtOrSimulate}): adds 0.5 exhaustion
 *       whenever an attack lands.</li>
 *   <li>{@code blockUsingItem} redirect: shields are disabled for at least 0.25s,
 *       even when the attacker's weapon defines no disable time.</li>
 *   <li>{@code getBaseExperienceReward} redirect: the vanilla {@code Math.min} cap is
 *       replaced — the reward is half the player's total experience
 *       ({@code totalExperience / 2}).</li>
 *   <li>Curse handling (duck {@code CurseCap}): persists the curse state, re-applies any
 *       cleared curse every server tick, force-drops armour for ARMOR_REJECTION carriers,
 *       makes FEAR curses miss 3/4 of attacks and slows PLANT_FEAR carriers inside plant
 *       blocks.</li>
 *   <li>{@code setDeltaMovement} override: while SLOWNESS is active in water the upward
 *       impulse is cancelled, so a slowed player cannot swim up (creative players exempt).</li>
 *   <li>Misc: a starving player can still attack ({@code cannotAttack}), the projectile
 *       lookup is limited to the first 8 inventory slots ({@code getProjectile} redirect),
 *       {@code onEnchantmentPerformed} spends raw experience points and rerolls the enchant
 *       seed, and armour absorption is recomputed with the MITE formula
 *       ({@code actuallyHurt} redirect).</li>
 * </ul>
 */
@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar implements CurseCap {
    @Unique
    private final CurseState mme$curseState = new CurseState();
    @Shadow
    public int experienceLevel;
    @Shadow
    public abstract FoodData getFoodData();
    @Shadow
    public abstract void causeFoodExhaustion(float amount);
    @Shadow
    protected int enchantmentSeed;
    @Shadow
    public int totalExperience;

    /**
     * Substitutes the shield-disable duration: if the attacking entity defines none
     * (0), 0.25 seconds is used so any hit briefly disables blocking.
     */
    @Redirect(method = "blockUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getSecondsToDisableBlocking()F"))
    public float blockUsingItem(LivingEntity instance) {
        float v = instance.getSecondsToDisableBlocking();
        return v > 0 ? v : 0.25F;
    }

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * Adds the reworked base attributes: max health 6, block interaction range 3 and entity
     * interaction range 1.75 (vanilla 3.0, MME 1.5 + 0.25 of unarmed attack reach).
     */
    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void createPlayerAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.BLOCK_INTERACTION_RANGE, 3)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 1.75)
        );
    }

    /** Triples the experience required for each level-up. */
    @Inject(method = "getXpNeededForNextLevel", at = @At("RETURN"), cancellable = true)
    public void getXpNeededForNextLevel(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 3);
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

    /**
     * Scales the damage of a normal melee swing by the experience-level bonus right before it is
     * handed to {@code hurtOrSimulate}, i.e. after vanilla has already folded in the weapon value,
     * enchantment bonus, critical hit and charge factor.
     *
     * @param damage the damage vanilla settled on
     * @return the damage after {@link PlayerLevelScaling#attackDamageMultiplier(Player)}
     */
    @ModifyArg(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
            index = 1
    )
    private float mme$levelScaledAttack(float damage) {
        return damage * PlayerLevelScaling.attackDamageMultiplier((Player) (Object) this);
    }

    /**
     * Same level scaling for the spear stab path ({@code stabAttack}), which settles its damage
     * through a second {@code hurtOrSimulate} call instead of {@code attack}.
     *
     * @param damage the damage the stab path settled on
     * @return the damage after {@link PlayerLevelScaling#attackDamageMultiplier(Player)}
     */
    @ModifyArg(
            method = "stabAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
            index = 1
    )
    private float mme$levelScaledStab(float damage) {
        return damage * PlayerLevelScaling.attackDamageMultiplier((Player) (Object) this);
    }

    /** Adds 0.5 food exhaustion when an attack connects. */
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public void attack(Entity entity, CallbackInfo ci) {
        this.causeFoodExhaustion(0.5F);
    }

    /** Replaces vanilla's {@code Math.min} cap: the reward is half the player's total experience. */
    @Redirect(method = "getBaseExperienceReward", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    public int getBaseExperienceReward(int a, int b) {
        return this.totalExperience / 2;
    }

    @Inject(method = "cannotAttack", at = @At("HEAD"), cancellable = true)
    private void cannotAttack(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (this.getFoodData().getFoodLevel() <= 0) {
            cir.setReturnValue(false);
        }
    }

    @Redirect(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getContainerSize()I"))
    private int getProjectile(Inventory instance) {
        return 8;
    }

    /** Cancels the upward impulse while SLOWNESS is active in water (creative exempt). */
    @Override
    public void setDeltaMovement(final @NonNull Vec3 deltaMovement) {
        Player player = (Player) (Object) this;
        if (player.hasEffect(MobEffects.SLOWNESS) && player.isInWater() && !player.isCreative() && deltaMovement.y > 0) {
            super.setDeltaMovement(deltaMovement.add(0, -deltaMovement.y-0.03, 0));
        }else {
            super.setDeltaMovement(deltaMovement);
        }
    }

    /** {@code CurseCap} duck accessor for the player's curse state. */
    @Override
    public CurseState mme$curseState() {
        return this.mme$curseState;
    }

    /** Persists the curse state with the player. */
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void mme$saveCurses(ValueOutput output, CallbackInfo ci) {
        this.mme$curseState.save(output);
    }

    /** Restores the curse state with the player. */
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void mme$loadCurses(ValueInput input, CallbackInfo ci) {
        this.mme$curseState.load(input);
    }

    /**
     * Server-side housekeeping: keeps effect instances in sync with the state (curses are
     * permanent, so anything vanilla clears is re-applied) and force-drops armour for
     * ARMOR_REJECTION carriers.
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void mme$curseTick(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        for (Holder<MobEffect> curse : List.copyOf(this.mme$curseState.curses())) {
            if (!self.hasEffect(curse)) {
                self.addEffect(CurseLogic.hiddenInstance(curse));
            }
        }
        if (self.hasEffect(MMECurses.ARMOR_REJECTION) && self instanceof ServerPlayer serverPlayer) {
            CurseLogic.dropAllArmor(serverPlayer);
        }
    }

    /** Fear curses make 3/4 of attacks against the feared creature miss entirely. */
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void mme$fearMiss(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity living) {
            Player self = (Player) (Object) this;
            Holder<MobEffect> fear = CurseLogic.fearedBy(self, living);
            if (fear != null && getRandom().nextFloat() >= CurseLogic.FEAR_LAND_CHANCE) {
                ci.cancel();
            }
        }
    }

    /** Slows movement through plant blocks for PLANT_FEAR carriers. */
    @Inject(method = "getBlockSpeedFactor", at = @At("TAIL"), cancellable = true)
    private void mme$plantFearSlow(CallbackInfoReturnable<Float> cir) {
        if (hasEffect(MMECurses.PLANT_FEAR) && level().getBlockState(blockPosition()).is(MMEBlockTags.PLANT_BLOCKS)) {
            cir.setReturnValue(Math.min(cir.getReturnValue(), CurseLogic.PLANT_SLOW));
        }
    }

    @Inject(method = "onEnchantmentPerformed", at = @At("HEAD"), cancellable = true)
    private void onEnchantmentPerformed(ItemStack itemStack, int enchantmentCost, CallbackInfo ci) {
        this.totalExperience -= enchantmentCost;
        this.enchantmentSeed = this.random.nextInt();
        ci.cancel();
    }

    @Redirect(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
    private float actuallyHurt(Player instance, DamageSource damageSource, float v) {
        float absorb = this.getDamageAfterArmorAbsorb(damageSource, v);
        if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
            absorb = Math.max(absorb - this.getArmorValue() * 0.7F, 0.2F);
        }
        return absorb;
    }
}
