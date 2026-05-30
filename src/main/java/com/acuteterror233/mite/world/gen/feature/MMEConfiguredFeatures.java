package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/**
 * MME 配置特征总入口。
 * 汇总地下和主世界的配置特征引导。
 */
public class MMEConfiguredFeatures {
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        UndergroundConfiguredFeatures.bootstrap(featureRegisterable);
        OverworldConfiguredFeatures.bootstrap(featureRegisterable);
    }
}
