package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.*;

public class AtConfiguredFeatures {
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        UndergroundOreConfiguredFeatures.bootstrap(featureRegisterable);
    }
}
