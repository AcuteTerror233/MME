package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class UndergroundPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_ADAMANTIUM_BURIED = of("ore_adamantium_buried");
    public static final ResourceKey<PlacedFeature> ORE_ADAMANTIUM_BURIED_SMALL = of("ore_adamantium_buried_small");
    public static final ResourceKey<PlacedFeature> ORE_MITHRIL = of("ore_mithril");
    public static final ResourceKey<PlacedFeature> ORE_MITHRIL_SMALL = of("ore_mithril_small");
    public static final ResourceKey<PlacedFeature> ORE_SILVER = of("ore_silver");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_SMALL = of("ore_silver_small");
    public static final ResourceKey<PlacedFeature> ORE_IRON = of("ore_iron");
    public static final ResourceKey<PlacedFeature> ORE_IRON_SMALL = of("ore_iron_small");
    public static final ResourceKey<PlacedFeature> ORE_COPPER = of("ore_copper");
    public static final ResourceKey<PlacedFeature> ORE_COPPER_SMALL = of("ore_copper_small");
    public static final ResourceKey<PlacedFeature> ORE_EMERALD = of("ore_emerald");
    public static final ResourceKey<PlacedFeature> ORE_LAPIS = of("ore_lapis");
    public static final ResourceKey<PlacedFeature> ORE_LAPIS_BURIED = of("ore_lapis_buried");
    public static final ResourceKey<PlacedFeature> ORE_REDSTONE = of("ore_redstone");
    public static final ResourceKey<PlacedFeature> ORE_DIRT = of("ore_dirt");
    public static final ResourceKey<PlacedFeature> ORE_GRAVEL = of("ore_gravel");
    public static final ResourceKey<PlacedFeature> ORE_TUFF = of("ore_tuff");
    public static final ResourceKey<PlacedFeature> ORE_DIAMOND_FIRST_LAYER = of("ore_diamond_first_layer");
    public static final ResourceKey<PlacedFeature> ORE_DIAMOND_FIRST_LAYER_BURIED = of("ore_diamond_first_layer_buried");
    public static final ResourceKey<PlacedFeature> ORE_DIAMOND_SECOND_LAYER = of("ore_diamond_second_layer");
    public static final ResourceKey<PlacedFeature> ORE_DIAMOND_SECOND_LAYER_BURIED = of("ore_diamond_second_layer_buried");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_MONSTER_ROOM = of("underground_monster_room");

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable){
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> registryEntry = registryEntryLookup.getOrThrow(CaveFeatures.MONSTER_ROOM);
        Holder<ConfiguredFeature<?, ?>> ore_adamantium_buried = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_ADAMANTIUM_BURIED);
        Holder<ConfiguredFeature<?, ?>> ore_adamantium_buried_small = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_ADAMANTIUM_BURIED_SMALL);
        Holder<ConfiguredFeature<?, ?>> ore_mithril = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_MITHRIL);
        Holder<ConfiguredFeature<?, ?>> ore_mithril_small = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_MITHRIL_SMALL);
        Holder<ConfiguredFeature<?, ?>> ore_silver = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_SILVER);
        Holder<ConfiguredFeature<?, ?>> ore_silver_small = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_SILVER_SMALL);
        Holder<ConfiguredFeature<?, ?>> ore_copper = registryEntryLookup.getOrThrow(OreFeatures.ORE_COPPER_LARGE);
        Holder<ConfiguredFeature<?, ?>> ore_copper_small = registryEntryLookup.getOrThrow(OreFeatures.ORE_COPPPER_SMALL);
        Holder<ConfiguredFeature<?, ?>> ore_emerald = registryEntryLookup.getOrThrow(OreFeatures.ORE_EMERALD);
        Holder<ConfiguredFeature<?, ?>> ore_lapis = registryEntryLookup.getOrThrow(OreFeatures.ORE_LAPIS);
        Holder<ConfiguredFeature<?, ?>> ore_lapis_buried = registryEntryLookup.getOrThrow(OreFeatures.ORE_LAPIS_BURIED);
        Holder<ConfiguredFeature<?, ?>> ore_iron = registryEntryLookup.getOrThrow(OreFeatures.ORE_IRON);
        Holder<ConfiguredFeature<?, ?>> ore_iron_small = registryEntryLookup.getOrThrow(OreFeatures.ORE_IRON_SMALL);
        Holder<ConfiguredFeature<?, ?>> ore_redstone = registryEntryLookup.getOrThrow(OreFeatures.ORE_REDSTONE);
        Holder<ConfiguredFeature<?, ?>> ore_dirt = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIRT);
        Holder<ConfiguredFeature<?, ?>> ore_gravel = registryEntryLookup.getOrThrow(OreFeatures.ORE_GRAVEL);
        Holder<ConfiguredFeature<?, ?>> ore_tuff = registryEntryLookup.getOrThrow(OreFeatures.ORE_TUFF);
        Holder<ConfiguredFeature<?, ?>> ore_diamond_small = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_SMALL);
        Holder<ConfiguredFeature<?, ?>> ore_diamond_medium = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> ore_diamond_large = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_LARGE);
        Holder<ConfiguredFeature<?, ?>> ore_diamond_buried = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIAMOND_BURIED);
        PlacementUtils.register(featureRegisterable, ORE_ADAMANTIUM_BURIED, ore_adamantium_buried, modifiers(
                CountPlacement.of(3),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-88), VerticalAnchor.absolute(-8))
        ));
        PlacementUtils.register(featureRegisterable, ORE_ADAMANTIUM_BURIED_SMALL, ore_adamantium_buried_small, modifiers(
                CountPlacement.of(5),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-8))
        ));
        PlacementUtils.register(featureRegisterable, ORE_MITHRIL, ore_mithril, modifiers(
                CountPlacement.of(3),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(40), VerticalAnchor.absolute(180))
        ));
        PlacementUtils.register(featureRegisterable, ORE_MITHRIL_SMALL, ore_mithril_small, modifiers(
                CountPlacement.of(7),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(70))
        ));
        PlacementUtils.register(featureRegisterable, ORE_SILVER, ore_silver, modifiers(
                CountPlacement.of(7),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_SILVER_SMALL, ore_silver_small, modifiers(
                CountPlacement.of(10),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127))
        ));
        PlacementUtils.register(featureRegisterable, ORE_COPPER, ore_copper, modifiers(
                CountPlacement.of(10),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_COPPER_SMALL, ore_copper_small, modifiers(
                CountPlacement.of(15),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127))
        ));
        PlacementUtils.register(featureRegisterable, ORE_EMERALD, ore_emerald, modifiers(
                CountPlacement.of(20),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_LAPIS, ore_lapis, modifiers(
                CountPlacement.of(8),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(70), VerticalAnchor.absolute(320)))
        );
        PlacementUtils.register(featureRegisterable, ORE_LAPIS_BURIED, ore_lapis_buried, modifiers(
                CountPlacement.of(12),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(70)))
        );
        PlacementUtils.register(featureRegisterable, ORE_IRON, ore_iron, modifiers(
                CountPlacement.of(10),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()))
        );
        PlacementUtils.register(featureRegisterable, ORE_IRON_SMALL, ore_iron_small, modifiers(
                CountPlacement.of(20),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()))
        );
        PlacementUtils.register(featureRegisterable, ORE_REDSTONE, ore_redstone, modifiers(
                CountPlacement.of(20),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_DIRT, ore_dirt, modifiers(
                CountPlacement.of(25),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_GRAVEL, ore_gravel, modifiers(
                CountPlacement.of(25),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_TUFF, ore_tuff, modifiers(
                CountPlacement.of(25),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_DIAMOND_FIRST_LAYER, ore_diamond_small, modifiers(
                CountPlacement.of(5),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_DIAMOND_FIRST_LAYER_BURIED, ore_diamond_medium, modifiers(
                CountPlacement.of(5),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.top())
        ));
        PlacementUtils.register(featureRegisterable, ORE_DIAMOND_SECOND_LAYER, ore_diamond_large, modifiers(
                CountPlacement.of(5),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127))
        ));
        PlacementUtils.register(featureRegisterable, ORE_DIAMOND_SECOND_LAYER_BURIED, ore_diamond_buried, modifiers(
                CountPlacement.of(5),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127))
        ));
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
    public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }
    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
