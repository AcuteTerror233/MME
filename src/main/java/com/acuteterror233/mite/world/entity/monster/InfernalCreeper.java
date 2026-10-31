package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Infernal creeper entity, inherits creeper behavior with an explosion radius of 7.
 */
public class InfernalCreeper extends Creeper {
    /** Raises the explosion radius from the vanilla 3 to 7. */
    public InfernalCreeper(EntityType<? extends InfernalCreeper> entityType, Level level) {
        super(entityType, level);
        this.explosionRadius = 7;
    }

    /** Returns vanilla creeper attributes unchanged. */
    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Creeper.createAttributes();
    }

}
