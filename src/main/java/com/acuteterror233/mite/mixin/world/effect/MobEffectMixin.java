package com.acuteterror233.mite.mixin.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Mixin for {@code MobEffect} — Caps the Resistance armor bonus at level V (+20 armor): the
 * amplifier feeding the per-level attribute modifier creation is clamped to 4 for
 * {@code MobEffects#RESISTANCE} only, in both modifier application paths (the attribute-map
 * loop in {@code addAttributeModifiers} and the {@code BiConsumer} variant of
 * {@code createModifiers}). Every other effect keeps its vanilla unbounded amplifier scaling.
 */
@Mixin(MobEffect.class)
public class MobEffectMixin {

    /** Highest Resistance amplifier that still grants armor (amplifier 4 = level V = +20 armor). */
    @Unique
    private static final int MME$RESISTANCE_MAX_AMPLIFIER = 4;

    /**
     * Clamps the {@code amplifier} argument entering both modifier-application methods when the
     * effect is Resistance, so commands granting absurd levels still only ever reach {@code +20}
     * armor.
     *
     * @param amplifier the effect amplifier about to be used for modifier scaling
     * @return the clamped amplifier for Resistance, the untouched amplifier otherwise
     */
    @ModifyVariable(method = {"createModifiers", "addAttributeModifiers"}, at = @At("HEAD"), argsOnly = true)
    private int mme$clampResistanceAmplifier(int amplifier) {
        if ((Object) this == MobEffects.RESISTANCE.value()) {
            return Math.min(amplifier, MME$RESISTANCE_MAX_AMPLIFIER);
        }
        return amplifier;
    }
}
