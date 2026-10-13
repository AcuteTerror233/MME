package com.acuteterror233.mite.mixin.world.effect;

import com.acuteterror233.mite.MME;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code MobEffects} — Gives the Resistance effect the MITE armor-granting behavior:
 * while the effect is active, each level grants {@code +4} armor, applied as a per-amplifier
 * attribute modifier ({@code MobEffect} adds effect-attribute modifiers when the effect starts
 * and removes them when it expires, so the armor follows the effect's lifetime automatically).
 *
 * <p>The vanilla flat damage reduction of Resistance ({@code -20% per level}) was removed by
 * {@code LivingEntityMixin#getDamageAfterMagicAbsorb}.</p>
 */
@Mixin(MobEffects.class)
public class MobEffectsMixin {

    /** Identifier of the per-level armor modifier granted by the Resistance effect. */
    @Unique
    private static final Identifier MME$RESISTANCE_ARMOR_ID = Identifier.fromNamespaceAndPath(MME.MOD_ID, "effect.resistance_armor");

    /** Armor points granted per Resistance level (capped at level V = +20, see {@code MobEffectMixin}). */
    @Unique
    private static final double MME$RESISTANCE_ARMOR_PER_LEVEL = 4.0D;

    /**
     * Attaches the armor modifier to the Resistance effect at the tail of the registry bootstrap:
     * {@code MobEffect} scales the modifier amount by {@code amplifier + 1} when applying it, so a
     * base amount of 4 yields {@code +4} per level exactly.
     */
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void mme$resistanceGrantsArmor(CallbackInfo ci) {
        MobEffects.RESISTANCE.value().addAttributeModifier(
                Attributes.ARMOR,
                MME$RESISTANCE_ARMOR_ID,
                MME$RESISTANCE_ARMOR_PER_LEVEL,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}
