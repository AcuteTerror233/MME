package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * MME 放置特征总入口。
 * 汇总地下和主世界的放置特征引导。
 */
public class MMEPlacedFeatures {
    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        UndergroundPlacedFeatures.bootstrap(featureRegisterable);
        OverworldPlacedFeatures.bootstrap(featureRegisterable);
    }
}
