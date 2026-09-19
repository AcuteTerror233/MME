package com.acuteterror233.mite.world.gen.feature;


import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * Underground dimension configured feature registration.
 * Defines ore and vegetation generation features for the underground world.
 */
public class UndergroundConfiguredFeatures {
    public static final ResourceKey<Feature> ORE_ADAMANTIUM_BURIED = of("ore_adamantium");
    public static final ResourceKey<Feature> ORE_ADAMANTIUM_BURIED_SMALL = of("ore_adamantium_small");
    public static final ResourceKey<Feature> ORE_MITHRIL = of("ore_mithril");
    public static final ResourceKey<Feature> ORE_MITHRIL_SMALL = of("ore_mithril_small");
    public static final ResourceKey<Feature> ORE_SILVER = of("ore_silver");
    public static final ResourceKey<Feature> ORE_SILVER_SMALL = of("ore_silver_small");

    public static void bootstrap(BootstrapContext<Feature> featureRegisterable){
        RuleTest ruleTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruleTest1 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<BlockReplacement> mithril_Ore_List = List.of(
                BlockReplacement.replace(ruleTest, MMEBlocks.MITHRIL_ORE.defaultBlockState()),
                BlockReplacement.replace(ruleTest1, MMEBlocks.DEEPSLATE_MITHRIL_ORE.defaultBlockState())
        );
        List<BlockReplacement> adamantium_Ore_List = List.of(
                BlockReplacement.replace(ruleTest, MMEBlocks.ADAMANTIUM_ORE.defaultBlockState()),
                BlockReplacement.replace(ruleTest1, MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE.defaultBlockState())
        );
        List<BlockReplacement> silver_Ore_List = List.of(
                BlockReplacement.replace(ruleTest, MMEBlocks.SILVER_ORE.defaultBlockState()),
                BlockReplacement.replace(ruleTest1, MMEBlocks.DEEPSLATE_SILVER_ORE.defaultBlockState())
        );
        featureRegisterable.register(ORE_ADAMANTIUM_BURIED, new OreFeature(adamantium_Ore_List, 4, 1.0f));
        featureRegisterable.register(ORE_ADAMANTIUM_BURIED_SMALL, new OreFeature(adamantium_Ore_List, 2, 1.0f));
        featureRegisterable.register(ORE_MITHRIL, new OreFeature(mithril_Ore_List, 4, 0f));
        featureRegisterable.register(ORE_MITHRIL_SMALL, new OreFeature(mithril_Ore_List, 4, 0f));
        featureRegisterable.register(ORE_SILVER, new OreFeature(silver_Ore_List, 6, 0.2f));
        featureRegisterable.register(ORE_SILVER_SMALL, new OreFeature(silver_Ore_List, 2, 0.2f));
    }
    /**
     * Generate a ConfiguredFeature registry key within the namespace.
     */
    public static ResourceKey<Feature> of(String id) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(MME.MOD_ID,id));
    }
}
