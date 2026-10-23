package com.acuteterror233.mite.mixin.world.entity.player;

import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import com.acuteterror233.mite.world.effect.curse.CurseCap;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import com.acuteterror233.mite.world.effect.curse.CurseState;
import com.acuteterror233.mite.world.effect.curse.MMECurses;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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

    /** Adds 0.5 food exhaustion when an attack connects. */
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public void attack(Entity entity, CallbackInfo ci) {
        this.causeFoodExhaustion(0.5F);
    }

    /** Removes vanilla's {@code Math.min} cap by always returning the first operand. */
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

    @Override
    public void setDeltaMovement(final @NonNull Vec3 deltaMovement) {
        Player player = (Player) (Object) this;
        if (player.hasEffect(MobEffects.SLOWNESS) && player.isInWater() && !player.isCreative() && deltaMovement.y > 0) {
            super.setDeltaMovement(deltaMovement.add(0, -deltaMovement.y-0.03, 0));
        }else {
            super.setDeltaMovement(deltaMovement);
        }
    }

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
