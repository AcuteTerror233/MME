package com.acuteterror233.mite.world.gen.feature;


import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.block.AtBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

//矿石团簇设置
public class UndergroundOreConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_ADAMANTIUM_BURIED_SMALL = of("ore_adamantium_small");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_MITHRIL_SMALL = of("ore_mithril_small");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_ADAMANTIUM_BURIED = of("ore_adamantium");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_MITHRIL = of("ore_mithril");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_SILVER = of("ore_silver");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_SILVER_SMALL = of("ore_silver_small");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable){
        RuleTest ruleTest = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruleTest1 = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreFeatureConfig.Target> mithril_Ore_List = List.of(
                OreFeatureConfig.createTarget(ruleTest, AtBlocks.MITHRIL_ORE.getDefaultState()),
                //更新深层后替换!!!!!!!!!!!!!!!!!!
                OreFeatureConfig.createTarget(ruleTest1, AtBlocks.MITHRIL_ORE.getDefaultState())
        );
        List<OreFeatureConfig.Target> adamantium_Ore_List = List.of(
                OreFeatureConfig.createTarget(ruleTest, AtBlocks.ADAMANTIUM_ORE.getDefaultState()),
                //更新深层后替换!!!!!!!!!!!!!!!!!!
                OreFeatureConfig.createTarget(ruleTest1, AtBlocks.ADAMANTIUM_ORE.getDefaultState())
        );
        List<OreFeatureConfig.Target> silver_Ore_List = List.of(
                OreFeatureConfig.createTarget(ruleTest, AtBlocks.SILVER_ORE.getDefaultState()),
                //更新深层后替换!!!!!!!!!!!!!!!!!!
                OreFeatureConfig.createTarget(ruleTest1, AtBlocks.SILVER_ORE.getDefaultState())
        );
        ConfiguredFeatures.register(featureRegisterable, ORE_MITHRIL_SMALL, Feature.ORE, new OreFeatureConfig(mithril_Ore_List, 4, 0.5f));
        ConfiguredFeatures.register(featureRegisterable, ORE_ADAMANTIUM_BURIED_SMALL, Feature.ORE, new OreFeatureConfig(adamantium_Ore_List, 4, 1.0f));
        ConfiguredFeatures.register(featureRegisterable, ORE_MITHRIL, Feature.ORE, new OreFeatureConfig(mithril_Ore_List, 6, 0.5f));
        ConfiguredFeatures.register(featureRegisterable, ORE_ADAMANTIUM_BURIED, Feature.ORE, new OreFeatureConfig(adamantium_Ore_List, 6, 1.0f));
        ConfiguredFeatures.register(featureRegisterable, ORE_SILVER, Feature.ORE, new OreFeatureConfig(silver_Ore_List, 8, 0.2f));
        ConfiguredFeatures.register(featureRegisterable, ORE_SILVER_SMALL, Feature.ORE, new OreFeatureConfig(silver_Ore_List, 4, 0.2f));
    }
    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(At_mite.MOD_ID,id));
    }
}
