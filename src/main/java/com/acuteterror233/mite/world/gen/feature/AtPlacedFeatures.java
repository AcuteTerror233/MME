package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.*;

/**
 * 放置要素注册入口：集中触发本模组所有 PlacedFeature 的引导注册。
 */
public class AtPlacedFeatures {

    /**
     * 注册入口：将各子模块的 PlacedFeature 注册到给定的注册器。
     */
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        UndergroundOrePlacedFeatures.bootstrap(featureRegisterable);
        UndergroundPlacedFeatures.bootstrap(featureRegisterable);
    }
}
