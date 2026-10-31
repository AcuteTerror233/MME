package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * MME placed feature main entry point.
 * Aggregates the underground and overworld placed-feature bootstraps; registered into the
 * {@code minecraft:placed_feature} registry by {@code MMEDataGenerator}.
 */
public class MMEPlacedFeatures {
    /**
     * Delegates to the underground and overworld placed feature bootstraps.
     *
     * @param featureRegisterable datagen bootstrap context for the placed-feature registry
     */
    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        UndergroundPlacedFeatures.bootstrap(featureRegisterable);
        OverworldPlacedFeatures.bootstrap(featureRegisterable);
    }
}