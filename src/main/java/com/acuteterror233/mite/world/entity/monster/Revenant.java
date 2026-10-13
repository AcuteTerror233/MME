package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.item.MMEItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

/**
 * Revenant entity, inherits zombie behavior. Spawns in a full rusted iron (or rusted iron
 * chainmail) set with a random rusted iron weapon, and moves 1.5x as fast as a zombie.
 */
public class Revenant extends Zombie {
    /** Revenant base attributes: zombie stats with 1.5x movement speed (0.23 * 1.5). */
    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.345D);
    }

    public Revenant(EntityType<? extends Revenant> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Always spawns in a full rusted iron or rusted iron chainmail set (50/50) and a random
     * rusted iron weapon (sword, war hammer or battle axe).
     */
    @Override
    protected void populateDefaultEquipmentSlots(@NonNull RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        boolean chainmail = randomSource.nextBoolean();
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(chainmail ? MMEItems.RUSTED_IRON_CHAINMAIL_HELMET : MMEItems.RUSTED_IRON_HELMET));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(chainmail ? MMEItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE : MMEItems.RUSTED_IRON_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(chainmail ? MMEItems.RUSTED_IRON_CHAINMAIL_LEGGINGS : MMEItems.RUSTED_IRON_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(chainmail ? MMEItems.RUSTED_IRON_CHAINMAIL_BOOTS : MMEItems.RUSTED_IRON_BOOTS));
        Item weapon = switch (randomSource.nextInt(3)) {
            case 0 -> MMEItems.RUSTED_IRON_SWORD;
            case 1 -> MMEItems.RUSTED_IRON_WAR_HAMMER;
            default -> MMEItems.RUSTED_IRON_BATTLE_AXE;
        };
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(weapon));
    }
}
