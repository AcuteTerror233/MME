package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * 吸血巨蝠 (Giant Vampire Bat)。
 * 继承 VampireBat，体型增大一倍。
 * 体型由 EntityType.Builder.sized() 控制，此处无需额外覆写。
 */
public class GiantVampireBat extends VampireBat {
    public GiantVampireBat(EntityType<? extends GiantVampireBat> entityType, Level level) {
        super(entityType, level);
    }
}