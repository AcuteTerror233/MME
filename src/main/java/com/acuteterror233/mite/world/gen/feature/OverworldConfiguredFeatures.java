package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * 主世界配置特征注册。
 * 定义主世界新增的矿物和植被特征（如蓝莓灌木丛）。
 */
public class OverworldConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_BERRY_BUSH = createKey("blue_berry_bush");
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable){
        FeatureUtils.register(
                featureRegisterable,
                BLUE_BERRY_BUSH,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        BlockStateProvider.simple(MMEBlocks.BLUE_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3))
                )
        );
    }
    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String string) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }

}
