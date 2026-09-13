package com.acuteterror233.mite.mixin.world.effect;

import net.minecraft.world.effect.PoisonMobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PoisonMobEffect.class)
/**
 * Mixin for {@code PoisonMobEffect} — Adjust poison effect damage mechanism.
 */
public class PoisonMobEffectMixin {

    /**
     * @author AcuteTerror233
     * @reason Modify poison effect interval
     */
    @Overwrite
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        int k = 200 >> j;
        return k > 0 ? i % k == 0 : true;
    }
}
