package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MMEConfiguredFeatures {
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        UndergroundConfiguredFeatures.bootstrap(featureRegisterable);
        OverworldConfiguredFeatures.bootstrap(featureRegisterable);
    }
}
