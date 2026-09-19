package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * MME configured feature main entry point.
 * Aggregates configured feature bootstrapping for underground and overworld.
 */
public class MMEConfiguredFeatures {
    public static void bootstrap(BootstrapContext<Feature> featureRegisterable) {
        UndergroundConfiguredFeatures.bootstrap(featureRegisterable);
        OverworldConfiguredFeatures.bootstrap(featureRegisterable);
    }
}
