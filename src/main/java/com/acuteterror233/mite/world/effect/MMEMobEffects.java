package com.acuteterror233.mite.world.effect;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

/**
 * MME mod status effect registry.
 * Register custom potion effects (malnutrition, insulin resistance, etc.).
 */
public class MMEMobEffects {
    /** Nutrition-deprivation effect applied when a player's protein/fiber/sugar balance fails (color 10404919). */
    public static final Holder<MobEffect> MALNUTRITION = register(
            "malnutrition",
            new PermanentNegativeMobEffect(10404919)
    );
    /**
     * Diabetes buff driven by sustained excess sugar intake; uses a custom blend profile (150, 20, 60).
     * The amplifier encodes the disease tier (0-2). Symptom scheduling — pulsed blindness (tier 1),
     * pulsed nausea + blindness (tier 2), permanent nausea + blindness + wither (tier 3+) — lives in
     * {@code FoodDataMixin}, which is the only place this effect is applied or removed.
     */
    public static final Holder<MobEffect> INSULIN_RESISTANCE = register(
            "insulin_resistance",
            new PermanentNegativeMobEffect(16777215).setBlendDuration(150, 20, 60)
    );

    private static Holder<MobEffect> register(String id, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MME.MOD_ID, id), mobEffect);
    }

    /** No-op classloading hook that triggers static registration. */
    public static void init() {

    }
}
