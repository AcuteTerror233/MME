package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DemonSpider extends Spider {
    public DemonSpider(EntityType<? extends DemonSpider> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hurt = super.doHurtTarget(level, target);
        if (hurt && target instanceof LivingEntity living) {
            if (this.random.nextFloat() < 0.30F) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 0), this);
                living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1), this);
                living.setRemainingFireTicks(100);
            }
        }
        return hurt;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Spider.createAttributes();
    }
}
