package com.acuteterror233.mite.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 永久负面状态效果基类。
 * 继承 {@link MobEffect}，类型固定为 {@link MobEffectCategory#HARMFUL}。
 */
public class PermanentNegativeMobEffect extends MobEffect {
    public PermanentNegativeMobEffect(int i) {
        super(MobEffectCategory.HARMFUL, i);
    }
}
