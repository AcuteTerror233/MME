package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.item.MMEItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

/**
 * Ancient Corpse entity, inherits skeleton behavior. Spawns in a full ancient metal chainmail
 * set armed with either a bow or an ancient metal sword, and has 30 max health with 3 armor
 * points.
 */
public class AncientCorpse extends Skeleton {
    /** Ancient corpse base attributes: skeleton stats with 30 max health and 3 armor. */
    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 3.0D);
    }

    public AncientCorpse(EntityType<? extends AncientCorpse> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Always spawns in a full ancient metal chainmail set, armed with either a bow or an
     * ancient metal sword (50/50). There is no ancient metal bow or arrow item in the mod,
     * so the bow is the vanilla one.
     */
    @Override
    protected void populateDefaultEquipmentSlots(@NonNull RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(MMEItems.ANCIENT_METAL_CHAINMAIL_HELMET));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(MMEItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(MMEItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(MMEItems.ANCIENT_METAL_CHAINMAIL_BOOTS));
        Item weapon = randomSource.nextBoolean() ? Items.BOW : MMEItems.ANCIENT_METAL_SWORD;
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(weapon));
    }
}
