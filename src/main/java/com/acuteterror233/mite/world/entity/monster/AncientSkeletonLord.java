package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.item.MMEItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

/**
 * Ancient Skeleton Lord entity, a Skeleton Lord variant identical in behavior (ancient corpse
 * summons, Strength/Speed self-buffs, skeleton heal aura) but spawning in a full ancient metal
 * set holding a random ancient metal sword, war hammer or battle axe.
 */
public class AncientSkeletonLord extends SkeletonLord {
    public AncientSkeletonLord(EntityType<? extends SkeletonLord> entityType, Level level) {
        super(entityType, level);
    }

    /** Always spawns in a full ancient metal set holding a random ancient metal weapon. */
    @Override
    protected void populateDefaultEquipmentSlots(@NonNull RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(MMEItems.ANCIENT_METAL_HELMET));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(MMEItems.ANCIENT_METAL_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(MMEItems.ANCIENT_METAL_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(MMEItems.ANCIENT_METAL_BOOTS));
        Item weapon = switch (randomSource.nextInt(3)) {
            case 0 -> MMEItems.ANCIENT_METAL_SWORD;
            case 1 -> MMEItems.ANCIENT_METAL_WAR_HAMMER;
            default -> MMEItems.ANCIENT_METAL_BATTLE_AXE;
        };
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(weapon));
    }
}
