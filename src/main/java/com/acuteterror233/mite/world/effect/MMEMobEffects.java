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
    public static final Holder<MobEffect> MALNUTRITION = register(
            "malnutrition",
            new PermanentNegativeMobEffect(10404919)
    );
    public static final Holder<MobEffect> INSULIN_RESISTANCE = register(
            "insulin_resistance",
            new PermanentNegativeMobEffect(16777215).setBlendDuration(150, 20, 60)
    );

    private static Holder<MobEffect> register(String id, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MME.MOD_ID, id), mobEffect);
    }

    public static void init() {

    }
}
