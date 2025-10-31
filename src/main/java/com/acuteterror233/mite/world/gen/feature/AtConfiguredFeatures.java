package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.*;

/**
 * 已配置要素注册入口：集中触发本模组所有 ConfiguredFeature 的引导注册。
 */
public class AtConfiguredFeatures {
    /**
     * 注册入口：将各子模块的 ConfiguredFeature 注册到给定的注册器。
     */
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        UndergroundOreConfiguredFeatures.bootstrap(featureRegisterable);
    }
}
