package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * MME configured feature main entry point.
 * Aggregates the underground and overworld configured-feature bootstraps; registered into the
 * {@code minecraft:feature} registry by {@code MMEDataGenerator}.
 */
public class MMEFeatures {
    /**
     * Delegates to the underground and overworld configured feature bootstraps.
     *
     * @param featureRegisterable datagen bootstrap context for the feature registry
     */
    public static void bootstrap(BootstrapContext<Feature> featureRegisterable) {
        UndergroundFeatures.bootstrap(featureRegisterable);
        OverworldFeatures.bootstrap(featureRegisterable);
    }
}
