package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;


/**
 * Underground dimension placed feature registration.
 * Defines generation rules for underground world features (height, frequency, etc.).
 */
public class UndergroundPlacedFeatures {
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_ADAMANTIUM_BURIED = of("ore_adamantium_buried");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_ADAMANTIUM_BURIED_SMALL = of("ore_adamantium_buried_small");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_MITHRIL = of("ore_mithril");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_MITHRIL_SMALL = of("ore_mithril_small");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_SILVER = of("ore_silver");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_SILVER_SMALL = of("ore_silver_small");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_IRON = of("ore_iron");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_IRON_SMALL = of("ore_iron_small");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_COPPER = of("ore_copper");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_COPPER_SMALL = of("ore_copper_small");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_EMERALD = of("ore_emerald");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_LAPIS = of("ore_lapis");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_LAPIS_BURIED = of("ore_lapis_buried");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_REDSTONE = of("ore_redstone");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_DIRT = of("ore_dirt");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_GRAVEL = of("ore_gravel");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_TUFF = of("ore_tuff");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_DIAMOND_FIRST_LAYER = of("ore_diamond_first_layer");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_DIAMOND_FIRST_LAYER_BURIED = of("ore_diamond_first_layer_buried");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_DIAMOND_SECOND_LAYER = of("ore_diamond_second_layer");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_ORE_DIAMOND_SECOND_LAYER_BURIED = of("ore_diamond_second_layer_buried");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_MONSTER_ROOM = of("underground_monster_room");

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable){
        HolderGetter<Feature> registryEntryLookup = featureRegisterable.lookup(Registries.FEATURE);
        Holder.Reference<Feature> registryEntry = registryEntryLookup.getOrThrow(CaveFeatures.MONSTER_ROOM);
        Holder<Feature> ore_adamantium_buried = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_ADAMANTIUM_BURIED);
        Holder<Feature> ore_adamantium_buried_small = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_ADAMANTIUM_BURIED_SMALL);
        Holder<Feature> ore_mithril = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_MITHRIL);
        Holder<Feature> ore_mithril_small = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_MITHRIL_SMALL);
        Holder<Feature> ore_silver = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_SILVER);
        Holder<Feature> ore_silver_small = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_SILVER_SMALL);
        Holder<Feature> ore_copper = registryEntryLookup.getOrThrow(OreFeatures.ORE_COPPER_LARGE);
        Holder<Feature> ore_copper_small = registryEntryLookup.getOrThrow(OreFeatures.ORE_COPPPER_SMALL);
        Holder<Feature> ore_emerald = registryEntryLookup.getOrThrow(OreFeatures.ORE_EMERALD);
        Holder<Feature> ore_lapis = registryEntryLookup.getOrThrow(OreFeatures.ORE_LAPIS);
        Holder<Feature> ore_lapis_buried = registryEntryLookup.getOrThrow(OreFeatures.ORE_LAPIS_BURIED);
        Holder<Feature> ore_iron = registryEntryLookup.getOrThrow(OreFeatures.ORE_IRON);
        Holder<Feature> ore_iron_small = registryEntryLookup.getOrThrow(OreFeatures.ORE_IRON_SMALL);
        Holder<Feature> ore_redstone = registryEntryLookup.getOrThrow(OreFeatures.ORE_REDSTONE);
        Holder<Feature> ore_dirt = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIRT);
        Holder<Feature> ore_gravel = registryEntryLookup.getOrThrow(OreFeatures.ORE_GRAVEL);
        Holder<Feature> ore_tuff = registryEntryLookup.getOrThrow(OreFeatures.ORE_TUFF);
        Holder<Feature> ore_diamond_small = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_SMALL);
        Holder<Feature> ore_diamond_medium = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_MEDIUM);
        Holder<Feature> ore_diamond_large = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_LARGE);
        Holder<Feature> ore_diamond_buried = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_BURIED);
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_ADAMANTIUM_BURIED, ore_adamantium_buried,
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-88), VerticalAnchor.absolute(-8)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_ADAMANTIUM_BURIED_SMALL, ore_adamantium_buried_small,
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-8)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_MITHRIL, ore_mithril,
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(40), VerticalAnchor.absolute(180)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_MITHRIL_SMALL, ore_mithril_small,
                CountPlacement.of(7),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(70)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_SILVER, ore_silver,
                CountPlacement.of(7),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_SILVER_SMALL, ore_silver_small,
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_COPPER, ore_copper,
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_COPPER_SMALL, ore_copper_small,
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_EMERALD, ore_emerald,
                CountPlacement.of(20),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_LAPIS, ore_lapis,
                CountPlacement.of(8),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(70), VerticalAnchor.absolute(320)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_LAPIS_BURIED, ore_lapis_buried,
                CountPlacement.of(12),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(70)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_IRON, ore_iron,
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_IRON_SMALL, ore_iron_small,
                CountPlacement.of(20),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_REDSTONE, ore_redstone,
                CountPlacement.of(20),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_DIRT, ore_dirt,
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_GRAVEL, ore_gravel,
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_TUFF, ore_tuff,
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_DIAMOND_FIRST_LAYER, ore_diamond_small,
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_DIAMOND_FIRST_LAYER_BURIED, ore_diamond_medium,
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_DIAMOND_SECOND_LAYER, ore_diamond_large,
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, UNDERGROUND_ORE_DIAMOND_SECOND_LAYER_BURIED, ore_diamond_buried,
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(
                featureRegisterable,
                UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM,
                registryEntry,
                CountPlacement.of(50),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6),VerticalAnchor.absolute(119)),
                BiomeFilter.biome()
        );
    }
    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
