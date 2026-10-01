package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mixin for {@code Mob} — Extends general mob behavior.
 */
@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements EquipmentUser, Leashable, Targeting{
    @Final
    @Shadow
    private static List<EquipmentSlot> EQUIPMENT_POPULATION_ORDER;

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }
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
        float probability = getY() <= 0 || this.level().dimension().equals(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) ? 0.6F : 0.15F * difficultyInstance.getSpecialMultiplier();
        switch (level().environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE)) {
            case STAR_MOON -> probability *= 0.6F;
            case BLOOD_MOON -> probability *= 2F;
            case PHANTOM_MOON, BLUE_MOON ->  probability *= 0.1F;
        }
        if (randomSource.nextFloat() < probability) {
            probability*=0.3F;
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

            List<EquipmentSlot> shuffledSlots = new ArrayList<>(EQUIPMENT_POPULATION_ORDER);
            Collections.shuffle(shuffledSlots, new java.util.Random(randomSource.nextInt()));

            float equipChance = 0.4F;

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
