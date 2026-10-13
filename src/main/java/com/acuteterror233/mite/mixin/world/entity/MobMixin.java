package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.entity.ai.SkeletonTargetMotionTracker;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mixin for {@code Mob} — Extends general mob behavior with moon-phase spawn buffs and a
 * custom equipment table.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code finalizeSpawn} injection (HEAD): BLOOD_MOON grants Strength and Speed
 *       for 12000 ticks at spawn; TURBID_MOON grants Invisibility for 600 ticks.
 *       Other moon phases leave spawned mobs unchanged.</li>
 *   <li>{@code populateDefaultEquipmentSlots} overwrite: reworks the vanilla equipment
 *       probability and resolves items via the tier table in
 *       {@link #getEquipmentForSlot}.</li>
 *   <li>{@code getEquipmentForSlot} overwrite: per-slot tier 0–6 — copper, silver,
 *       rusted iron, rusted iron chainmail, iron, ancient metal, mithril.</li>
 * </ul>
 *
 * <p>Moon-phase multipliers on the equipment probability:</p>
 * <ul>
 *   <li>STAR_MOON × 0.6</li>
 *   <li>BLOOD_MOON × 2.0</li>
 *   <li>PHANTOM_MOON / BLUE_MOON × 0.1</li>
 * </ul>
 *
 * <p>Blood-moon cycle weighting: every completed {@code BLOOD_MOON} timeline cycle
 * ({@link SpecialMoonPhase#BLOOD_MOON} start tick, 32 days) scales monster gear and weapons,
 * capped at cycle 4 (128 in-game days):</p>
 * <ul>
 *   <li>equipment chance and tier rolls: +25% per cycle — equipment is guaranteed after four
 *       cycles</li>
 *   <li>equipment count: the per-slot equip chance starts at 0.4 + 0.15 per cycle instead of
 *       0.4, so older worlds dress mobs in more pieces</li>
 *   <li>weapon strength: the spawned main-hand weapon is enchanted at Sharpness (melee) or
 *       Power (bow) level equal to the cycle count via a tail injection on
 *       {@code populateDefaultEquipmentEnchantments}; the higher of the vanilla random roll and
 *       the cycle level wins</li>
 * </ul>
 */
@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements EquipmentUser, Leashable, Targeting{
    @Final
    @Shadow
    private static List<EquipmentSlot> EQUIPMENT_POPULATION_ORDER;

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Feeds the skeleton predictive-aim tracker: every server tick a skeleton with a target
     * records the target's position (see {@link SkeletonTargetMotionTracker}) — this works for
     * players where {@code getDeltaMovement()} and the {@code xo/yo/zo} diff do not, because
     * movement packets are applied before entity ticking.
     */
    @Inject(method = "aiStep", at = @At("HEAD"))
    private void mme$recordSkeletonTargetMotion(CallbackInfo ci) {
        if ((Object) this instanceof AbstractSkeleton skeleton && !this.level().isClientSide()) {
            LivingEntity target = skeleton.getTarget();
            if (target != null && target.isAlive()) {
                SkeletonTargetMotionTracker.record(target, this.level().getGameTime());
            }
        }
    }

    /** Applies moon-phase spawn buffs (see class doc). */
    @Inject(method = "finalizeSpawn", at = @At("HEAD"))
    private void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData groupData, CallbackInfoReturnable<SpawnGroupData> cir){
        SpecialMoonPhase dimensionValue = level.environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE);
        if (dimensionValue.equals(SpecialMoonPhase.BLOOD_MOON)) {
            this.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 12000));
            this.addEffect(new MobEffectInstance(MobEffects.SPEED, 12000));
        }
        if (dimensionValue.equals(SpecialMoonPhase.TURBID_MOON)) {
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600));
        }
    }

    /**
     * @author AcuteTerror233.
     * @reason Add copper and other equipment
     */
    @Overwrite
    public void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        // Deep spawns (Y <= 0 or the underground dimension) are far better equipped than surface ones
        float probability = getY() <= 0 || this.level().dimension().equals(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) ? 0.6F : 0.15F * difficultyInstance.getSpecialMultiplier();
        // Moon-phase probability multipliers (see class doc)
        switch (level().environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE)) {
            case STAR_MOON -> probability *= 0.6F;
            case BLOOD_MOON -> probability *= 2F;
            case PHANTOM_MOON, BLUE_MOON ->  probability *= 0.1F;
        }
        // Blood-moon cycle weighting: each completed BLOOD_MOON timeline cycle (32 days) adds
        // +25% to the equipment chance and the tier rolls — after four cycles (128 days)
        // equipment is guaranteed and tier rolls always succeed
        int bloodMoonCycles = (int) Math.min(level().getOverworldClockTime() / SpecialMoonPhase.BLOOD_MOON.startTick(), 4L);
        float cycleBonus = bloodMoonCycles * 0.25F;
        probability = Math.min(probability + cycleBonus, 1.0F);
        if (randomSource.nextFloat() < probability) {
            // Higher tiers are progressively rarer: each extra roll at 30% of the base chance raises the tier index
            probability*=0.3F;
            // Tier rolls inherit the cycle bonus too, so older worlds roll higher equipment tiers
            probability = Math.min(probability + cycleBonus, 1.0F);
            // Base tier 0-1; subsequent rolls below can raise it up to 6
            int i = randomSource.nextInt(2);
            Level level = this.level();
            if (randomSource.nextFloat() < probability || level.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) {
                i+=2;
            }
            if (randomSource.nextFloat() < probability) {
                i++;
            }
            if (randomSource.nextFloat() < probability) {
                i++;
            }
            if (randomSource.nextFloat() < probability) {
                i++;
            }

            // Pick slots in random order so no slot is systematically favored
            List<EquipmentSlot> shuffledSlots = new ArrayList<>(EQUIPMENT_POPULATION_ORDER);
            Collections.shuffle(shuffledSlots, new java.util.Random(randomSource.nextInt()));

            // Per-slot chance decays after each successful equip; blood-moon cycles raise the
            // base so older worlds spawn mobs wearing more pieces
            float equipChance = 0.4F + 0.15F * bloodMoonCycles;

            for (EquipmentSlot equipmentSlot : shuffledSlots) {
                if (randomSource.nextFloat() < equipChance) {
                    ItemStack itemStack = this.getItemBySlot(equipmentSlot);
                    if (itemStack.isEmpty()) {
                        Item item = getEquipmentForSlot(equipmentSlot, i);
                        if (item != null) {
                            this.setItemSlot(equipmentSlot, new ItemStack(item));
                            equipChance -= 0.08F;
                        }
                    }
                }
            }
        }
    }

    /**
     * Blood-moon cycle weapon strength: every completed BLOOD_MOON timeline cycle enchants the
     * spawned main-hand weapon at that level (Sharpness for melee, Power for bows), capped at
     * cycle 4. Injected at TAIL of the vanilla enchantment pass — zombies and skeletons assign
     * their equipment (including weapons) in {@code finalizeSpawn} right before this pass runs,
     * so the weapon is final here; {@link ItemStack#enchant} keeps the higher of the vanilla
     * random roll and the cycle level.
     */
    @Inject(method = "populateDefaultEquipmentEnchantments", at = @At("TAIL"))
    private void mme$enchantWeaponByBloodMoonCycles(ServerLevelAccessor level, RandomSource randomSource, DifficultyInstance difficultyInstance, CallbackInfo ci) {
        if (this.level().isClientSide()) {
            return;
        }
        int bloodMoonCycles = (int) Math.min(this.level().getOverworldClockTime() / SpecialMoonPhase.BLOOD_MOON.startTick(), 4L);
        if (bloodMoonCycles <= 0) {
            return;
        }
        ItemStack weapon = this.getMainHandItem();
        if (weapon.isEmpty()) {
            return;
        }
        Holder<Enchantment> sharpness = this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS);
        Holder<Enchantment> power = this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.POWER);
        Holder<Enchantment> enchantment = sharpness.value().isSupportedItem(weapon) ? sharpness : power;
        if (enchantment.value().isSupportedItem(weapon)) {
            weapon.enchant(enchantment, bloodMoonCycles);
        }
    }

    /**
     * Resolves the equipment item for a slot and tier index {@code i}
     * (0 = copper … 6 = mithril); returns {@code null} beyond the table.
     *
     * @author AcuteTerror233.
     * @reason Add copper and other equipment
     */
    @Overwrite
    public static @Nullable Item getEquipmentForSlot(EquipmentSlot equipmentSlot, int i) {
        return switch (equipmentSlot) {
            case HEAD -> switch (i) {
                case 0 -> Items.COPPER_HELMET;
                case 1 -> MMEItems.SILVER_HELMET;
                case 2 -> MMEItems.RUSTED_IRON_HELMET;
                case 3 -> MMEItems.RUSTED_IRON_CHAINMAIL_HELMET;
                case 4 -> Items.IRON_HELMET;
                case 5 -> MMEItems.ANCIENT_METAL_HELMET;
                case 6 -> MMEItems.MITHRIL_HELMET;
                default -> null;
            };
            case CHEST -> switch (i) {
                case 0 -> Items.COPPER_CHESTPLATE;
                case 1 -> MMEItems.SILVER_CHESTPLATE;
                case 2 -> MMEItems.RUSTED_IRON_CHESTPLATE;
                case 3 -> MMEItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE;
                case 4 -> Items.IRON_CHESTPLATE;
                case 5 -> MMEItems.ANCIENT_METAL_CHESTPLATE;
                case 6 -> MMEItems.MITHRIL_CHESTPLATE;
                default -> null;
            };
            case LEGS -> switch (i) {
                case 0 -> Items.COPPER_LEGGINGS;
                case 1 -> MMEItems.SILVER_LEGGINGS;
                case 2 -> MMEItems.RUSTED_IRON_LEGGINGS;
                case 3 -> MMEItems.RUSTED_IRON_CHAINMAIL_LEGGINGS;
                case 4 -> Items.IRON_LEGGINGS;
                case 5 -> MMEItems.ANCIENT_METAL_LEGGINGS;
                case 6 -> MMEItems.MITHRIL_LEGGINGS;
                default -> null;
            };
            case FEET -> switch (i) {
                case 0 -> Items.COPPER_BOOTS;
                case 1 -> MMEItems.SILVER_BOOTS;
                case 2 -> MMEItems.RUSTED_IRON_BOOTS;
                case 3 -> MMEItems.RUSTED_IRON_CHAINMAIL_BOOTS;
                case 4 -> Items.IRON_BOOTS;
                case 5 -> MMEItems.ANCIENT_METAL_BOOTS;
                case 6 -> MMEItems.MITHRIL_BOOTS;
                default -> null;
            };
            default -> null;
        };
    }

}
