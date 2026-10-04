package com.acuteterror233.mite.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Permanent negative status effect base class.
 * Extends {@link MobEffect}, category fixed to {@link MobEffectCategory#HARMFUL}.
 */
public class PermanentNegativeMobEffect extends MobEffect {
    /** @param i particle color (ARGB) used for effect icons/particles. */
    public PermanentNegativeMobEffect(int i) {
        super(MobEffectCategory.HARMFUL, i);
    }
}
