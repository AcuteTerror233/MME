package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * Giant vampire bat entity, inherits vampire bat behavior with larger size.
 */
public class GiantVampireBat extends VampireBat {
    public GiantVampireBat(EntityType<? extends GiantVampireBat> entityType, Level level) {
        super(entityType, level);
    }
}