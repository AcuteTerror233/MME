package com.acuteterror233.mite.mixin.world.effect;

import net.minecraft.world.effect.PoisonMobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code PoisonMobEffect} — slows poison damage ticks by 8×.
 *
 * <p>{@code shouldApplyEffectTickThisTick} is fully overwritten: the interval between damage ticks
 * is {@code 200 >> amplifier} (vanilla uses {@code 25 >> amplifier}), so poison hits far less
 * often at any amplifier level; once the shifted interval reaches 0 (very high amplifiers), the
 * effect applies every tick like vanilla. Evaluated wherever effect ticking runs (server-side
 * damage, client-side particle pacing).</p>
 */
@Mixin(PoisonMobEffect.class)
public class PoisonMobEffectMixin {

    /**
     * @author AcuteTerror233
     * @reason Modify poison effect interval
     *
     * <p>Overwrites vanilla {@code shouldApplyEffectTickThisTick}: damage every {@code 200 >> j}
     * ticks instead of {@code 25 >> j}.</p>
     *
     * @param i the current effect duration tick counter
     * @param j the effect amplifier level
     * @return whether poison damage should apply on this tick
     */
    @Overwrite
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        int k = 200 >> j;
        return k > 0 ? i % k == 0 : true;
    }
}
