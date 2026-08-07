package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin extends Monster implements RangedAttackMob {
    @Unique
    private static final float LOW_Y_THRESHOLD_UNDERGROUND = 125.0F;
    @Unique
    private static final float LOW_Y_THRESHOLD_OVERWORLD = 0.0F;
    @Unique
    private static final float LOW_Y_DROP_RATE = 0.25F;
    @Unique
    private static final float HIGH_Y_DROP_RATE = 0.05F;
    @Unique
    private static final float HARD_DIFFICULTY_MULTIPLIER = 2.0F;

    protected AbstractSkeletonMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author AcuteTerror233
     * @reason 添加手持武器
     */
    @Overwrite
    public void populateDefaultEquipmentSlots(@NonNull RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        Level level = this.level();
        if (level.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) {
            float populateRate = (getY() < LOW_Y_THRESHOLD_UNDERGROUND ? LOW_Y_DROP_RATE : HIGH_Y_DROP_RATE) * (level.getDifficulty() == Difficulty.HARD ? HARD_DIFFICULTY_MULTIPLIER : 1.0F);
            if (randomSource.nextFloat() < populateRate) {
                int weaponIndex = randomSource.nextInt(6);
                if (getY() < LOW_Y_THRESHOLD_UNDERGROUND) {
                    setUndergroundLowYWeapon(weaponIndex);
                } else {
                    setUndergroundHighYWeapon(weaponIndex);
                }
            }
        } else if (level.dimension() == Level.OVERWORLD) {
            float populateRate = (getY() < LOW_Y_THRESHOLD_OVERWORLD ? LOW_Y_DROP_RATE : HIGH_Y_DROP_RATE) * (level.getDifficulty() == Difficulty.HARD ? HARD_DIFFICULTY_MULTIPLIER : 1.0F);
            if (randomSource.nextFloat() < populateRate) {
                int weaponIndex = randomSource.nextInt(4);
                if (getY() < LOW_Y_THRESHOLD_OVERWORLD) {
                    setOverworldLowYWeapon(weaponIndex);
                } else {
                    setOverworldHighYWeapon(weaponIndex);
                }
            }
        }
    }

    @Unique
    private void setUndergroundLowYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.ANCIENT_METAL_SWORD)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.IRON_BATTLE_AXE)); break;
            case 4: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_SWORD)); break;
            case 5: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_BATTLE_AXE)); break;
        }
    }

    @Unique
    private void setUndergroundHighYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_SWORD)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_BATTLE_AXE)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.COPPER_SWORD)); break;
            case 4: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.COPPER_BATTLE_AXE)); break;
            case 5: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_SWORD)); break;
        }
    }

    @Unique
    private void setOverworldLowYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.COPPER_SWORD)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.COPPER_BATTLE_AXE)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_SWORD)); break;
        }
    }

    @Unique
    private void setOverworldHighYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.WOODEN_CLUB)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.WOODEN_CUDGEL)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.FLINT_AXE)); break;
        }
    }

    /**
     * @author AcuteTerror233
     * @reason 修改属性
     */
    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.33);
        builder.add(Attributes.MAX_HEALTH, 6);
        return builder;
    }
}
