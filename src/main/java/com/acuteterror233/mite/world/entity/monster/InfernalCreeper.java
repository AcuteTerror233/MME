package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InfernalCreeper extends Creeper {
    public InfernalCreeper(EntityType<? extends InfernalCreeper> entityType, Level level) {
        super(entityType, level);
        this.explosionRadius = 3;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Creeper.createAttributes();
    }

}
