package com.acuteterror233.mite.world.gen.feature;


import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * 矿石团簇配置：定义秘银、艾德曼、银等矿脉的 ConfiguredFeature。
 */
public class UndergroundOreConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ADAMANTIUM_BURIED_SMALL = of("ore_adamantium_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MITHRIL_SMALL = of("ore_mithril_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ADAMANTIUM_BURIED = of("ore_adamantium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MITHRIL = of("ore_mithril");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER = of("ore_silver");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_SMALL = of("ore_silver_small");
    /**
     * 注册全部矿石的 ConfiguredFeature，并分别指定替换规则、矿脉规模与稀疏度。
     */
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable){
        RuleTest ruleTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruleTest1 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> mithril_Ore_List = List.of(
                OreConfiguration.target(ruleTest, MMEBlocks.MITHRIL_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest1, MMEBlocks.DEEPSLATE_MITHRIL_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> adamantium_Ore_List = List.of(
                OreConfiguration.target(ruleTest, MMEBlocks.ADAMANTIUM_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest1, MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> silver_Ore_List = List.of(
                OreConfiguration.target(ruleTest, MMEBlocks.SILVER_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest1, MMEBlocks.DEEPSLATE_SILVER_ORE.defaultBlockState())
        );
        FeatureUtils.register(featureRegisterable, ORE_MITHRIL_SMALL, Feature.ORE, new OreConfiguration(mithril_Ore_List, 4, 0.5f));
        FeatureUtils.register(featureRegisterable, ORE_ADAMANTIUM_BURIED_SMALL, Feature.ORE, new OreConfiguration(adamantium_Ore_List, 4, 1.0f));
        FeatureUtils.register(featureRegisterable, ORE_MITHRIL, Feature.ORE, new OreConfiguration(mithril_Ore_List, 6, 0.5f));
        FeatureUtils.register(featureRegisterable, ORE_ADAMANTIUM_BURIED, Feature.ORE, new OreConfiguration(adamantium_Ore_List, 6, 1.0f));
        FeatureUtils.register(featureRegisterable, ORE_SILVER, Feature.ORE, new OreConfiguration(silver_Ore_List, 8, 0.2f));
        FeatureUtils.register(featureRegisterable, ORE_SILVER_SMALL, Feature.ORE, new OreConfiguration(silver_Ore_List, 4, 0.2f));
    }
    /**
     * 生成命名空间内的 ConfiguredFeature 注册键。
     */
    public static ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID,id));
    }
}
