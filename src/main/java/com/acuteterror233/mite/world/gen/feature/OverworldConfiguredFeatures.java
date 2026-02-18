package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class OverworldConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_BERRY_BUSH = createKey("blue_berry_bush");
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable){
        FeatureUtils.register(
                featureRegisterable,
                BLUE_BERRY_BUSH,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(MMEBlocks.BLUE_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3))
                        ),
                        List.of(Blocks.GRASS_BLOCK))
        );
    }
    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String string) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, string));
    }

}
