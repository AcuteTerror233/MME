package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements EquipmentUser, Leashable, Targeting{
    @Final
    @Shadow
    private static List<EquipmentSlot> EQUIPMENT_POPULATION_ORDER;

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }
    /**
     * @author AcuteTerror233.
     * @reason 添加铜等装备
     */
    @Overwrite
    public void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        if (randomSource.nextFloat() < (getY() <= 0 || this.level().dimension().equals(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) ? 0.6F : 0.15F * difficultyInstance.getSpecialMultiplier())) {
            int i = randomSource.nextInt(2); // 初始值为 0 或 1
            Level level = this.level();
            float f = level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            if (randomSource.nextFloat() < 0.15F || level.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) {
                i+=2;
            }
            if (randomSource.nextFloat() < 0.15F) {
                i++;
            }
            if (randomSource.nextFloat() < 0.15F) {
                i++;
            }
            if (randomSource.nextFloat() < 0.15F) {
                i++;
            }

            boolean bl = true;

            for (EquipmentSlot equipmentSlot : EQUIPMENT_POPULATION_ORDER) {
                ItemStack itemStack = this.getItemBySlot(equipmentSlot);
                if (!bl && randomSource.nextFloat() < f) {
                    break;
                }

                bl = false;
                if (itemStack.isEmpty()) {
                    Item item = getEquipmentForSlot(equipmentSlot, i);
                    if (item != null) {
                        this.setItemSlot(equipmentSlot, new ItemStack(item));
                    }
                }
            }
        }
    }

    /**
     * @author AcuteTerror233.
     * @reason 添加铜等装备
     */
    @Overwrite
    public static @Nullable Item getEquipmentForSlot(EquipmentSlot equipmentSlot, int i) {
        return switch (equipmentSlot) {
            case HEAD -> switch (i) {
                case 0 -> MMEItems.COPPER_HELMET;
                case 1 -> MMEItems.SILVER_HELMET;
                case 2 -> MMEItems.RUSTED_IRON_HELMET;
                case 3 -> MMEItems.RUSTED_IRON_CHAINMAIL_HELMET;
                case 4 -> Items.IRON_HELMET;
                case 5 -> MMEItems.ANCIENT_METAL_HELMET;
                case 6 -> MMEItems.MITHRIL_HELMET;
                default -> null;
            };
            case CHEST -> switch (i) {
                case 0 -> MMEItems.COPPER_CHESTPLATE;
                case 1 -> MMEItems.SILVER_CHESTPLATE;
                case 2 -> MMEItems.RUSTED_IRON_CHESTPLATE;
                case 3 -> MMEItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE;
                case 4 -> Items.IRON_CHESTPLATE;
                case 5 -> MMEItems.ANCIENT_METAL_CHESTPLATE;
                case 6 -> MMEItems.MITHRIL_CHESTPLATE;
                default -> null;
            };
            case LEGS -> switch (i) {
                case 0 -> MMEItems.COPPER_LEGGINGS;
                case 1 -> MMEItems.SILVER_LEGGINGS;
                case 2 -> MMEItems.RUSTED_IRON_LEGGINGS;
                case 3 -> MMEItems.RUSTED_IRON_CHAINMAIL_LEGGINGS;
                case 4 -> Items.IRON_LEGGINGS;
                case 5 -> MMEItems.ANCIENT_METAL_LEGGINGS;
                case 6 -> MMEItems.MITHRIL_LEGGINGS;
                default -> null;
            };
            case FEET -> switch (i) {
                case 0 -> MMEItems.COPPER_BOOTS;
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
