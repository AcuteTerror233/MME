package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin  extends Monster implements RangedAttackMob {
    protected AbstractSkeletonMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author AcuteTerror233
     * @reason 添加更多武器
     */
    @Overwrite
    public void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        Level level = this.level();
        if (level.dimension() == Level.OVERWORLD){
            if (getY() > 63) {
                addMainhand(MMEItems.WOODEN_CLUB);
            }else {
                float v = randomSource.nextFloat();
                if (v < 0.3F || !this.getSlot(100).get().isEmpty()){
                    addMainhand(MMEItems.WOODEN_CLUB);
                }else if (v < 0.7F){
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                }
            }
        } else if (level.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) {
            float v = randomSource.nextFloat();
            if (v < 0.3F || !this.getSlot(100).get().isEmpty()){
                switch (randomSource.nextInt(3)){
                    case 0: addMainhand(MMEItems.COPPER_SWORD);
                    case 1: addMainhand(MMEItems.SILVER_SWORD);
                    case 2: addMainhand(MMEItems.RUSTED_IRON_SWORD);
                }
            }else if (v < 0.7F){
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
        }
    }
    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.33);
        builder.add(Attributes.MAX_HEALTH, 6);
        return builder;
    }

    private void addMainhand(Item item) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(item));
    }
}
