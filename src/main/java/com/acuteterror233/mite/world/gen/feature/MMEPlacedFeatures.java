package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * MME placed feature main entry point.
 * Aggregates placed feature bootstrapping for underground and overworld.
 */
public class MMEPlacedFeatures {
    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        UndergroundPlacedFeatures.bootstrap(featureRegisterable);
        OverworldPlacedFeatures.bootstrap(featureRegisterable);
    }
}
