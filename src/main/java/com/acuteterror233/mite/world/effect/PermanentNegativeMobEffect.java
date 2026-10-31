package com.acuteterror233.mite.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Base class for MME's harmful status effects.
 * Extends {@link MobEffect} with the category fixed to {@link MobEffectCategory#HARMFUL};
 * "permanent" refers to how MME applies these effects (infinite duration, see
 * {@code CurseLogic#hiddenInstance} and {@code FoodDataMixin}), not to any override in this class.
 */
public class PermanentNegativeMobEffect extends MobEffect {
    /** @param i effect color (RGB24, {@code 0xRRGGBB}) used for effect icons. */
    public PermanentNegativeMobEffect(int i) {
        super(MobEffectCategory.HARMFUL, i);
    }
}
