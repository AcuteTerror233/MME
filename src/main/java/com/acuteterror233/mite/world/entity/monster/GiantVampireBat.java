package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * 巨型吸血鬼蝙蝠实体，继承吸血鬼蝙蝠行为，体型更大。
 */
public class GiantVampireBat extends VampireBat {
    public GiantVampireBat(EntityType<? extends GiantVampireBat> entityType, Level level) {
        super(entityType, level);
    }
}