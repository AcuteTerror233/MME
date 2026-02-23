package com.acuteterror233.mite.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class PermanentNegativeMobEffect extends MobEffect {
    public PermanentNegativeMobEffect(int i) {
        super(MobEffectCategory.HARMFUL, i);
    }
}
