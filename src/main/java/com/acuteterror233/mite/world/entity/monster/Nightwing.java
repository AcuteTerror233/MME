package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * 暗影蝙蝠 (Nightwing)。
 * 继承 VampireBat，攻击附带 5 秒黑暗效果。
 */
public class Nightwing extends VampireBat {
    public Nightwing(EntityType<? extends Nightwing> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hurt = super.doHurtTarget(level, target);
        if (hurt && target instanceof LivingEntity living) {
            living.addEffect(
                    new MobEffectInstance(
                            MobEffects.DARKNESS,
                            100,
                            0,
                            false,
                            true
                    ),
                    this
            );
        }
        return hurt;
    }
}