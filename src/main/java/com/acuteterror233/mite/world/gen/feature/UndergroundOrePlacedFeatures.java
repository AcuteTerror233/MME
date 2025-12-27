package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.Mme;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

//矿石放置位置
public class UndergroundOrePlacedFeatures {
    public static final RegistryKey<PlacedFeature> ORE_ADAMANTIUM_BURIED = of("ore_adamantium_buried");
    public static final RegistryKey<PlacedFeature> ORE_ADAMANTIUM_BURIED_SMALL = of("ore_adamantium_buried_small");
    public static final RegistryKey<PlacedFeature> ORE_MITHRIL = of("ore_mithril");
    public static final RegistryKey<PlacedFeature> ORE_MITHRIL_SMALL = of("ore_mithril_small");
    public static final RegistryKey<PlacedFeature> ORE_SILVER = of("ore_silver");
    public static final RegistryKey<PlacedFeature> ORE_SILVER_SMALL = of("ore_silver_small");
    public static final RegistryKey<PlacedFeature> ORE_IRON = of("ore_iron");
    public static final RegistryKey<PlacedFeature> ORE_IRON_SMALL = of("ore_iron_small");
    public static final RegistryKey<PlacedFeature> ORE_COPPER = of("ore_copper");
    public static final RegistryKey<PlacedFeature> ORE_COPPER_SMALL = of("ore_copper_small");
    public static final RegistryKey<PlacedFeature> ORE_EMERALD = of("ore_emerald");
    public static final RegistryKey<PlacedFeature> ORE_LAPIS = of("ore_lapis");
    public static final RegistryKey<PlacedFeature> ORE_LAPIS_BURIED = of("ore_lapis_buried");
    public static final RegistryKey<PlacedFeature> ORE_REDSTONE = of("ore_redstone");
    public static final RegistryKey<PlacedFeature> ORE_DIRT = of("ore_dirt");
    public static final RegistryKey<PlacedFeature> ORE_GRAVEL = of("ore_gravel");
    public static final RegistryKey<PlacedFeature> ORE_TUFF = of("ore_tuff");
    public static final RegistryKey<PlacedFeature> ORE_DIAMOND_FIRST_LAYER = of("ore_diamond_first_layer");
    public static final RegistryKey<PlacedFeature> ORE_DIAMOND_FIRST_LAYER_BURIED = of("ore_diamond_first_layer_buried");
    public static final RegistryKey<PlacedFeature> ORE_DIAMOND_SECOND_LAYER = of("ore_diamond_second_layer");
    public static final RegistryKey<PlacedFeature> ORE_DIAMOND_SECOND_LAYER_BURIED = of("ore_diamond_second_layer_buried");
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_adamantium_buried = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_ADAMANTIUM_BURIED);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_adamantium_buried_small = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_ADAMANTIUM_BURIED_SMALL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_mithril = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_MITHRIL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_mithril_small = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_MITHRIL_SMALL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_silver = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_SILVER);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_silver_small = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_SILVER_SMALL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_copper = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_COPPER_LARGE);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_copper_small = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_COPPER_SMALL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_emerald = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_EMERALD);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_lapis = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_LAPIS);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_lapis_buried = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_LAPIS_BURIED);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_iron = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_IRON);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_iron_small = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_IRON_SMALL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_redstone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_REDSTONE);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_dirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_DIRT);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_gravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_GRAVEL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_tuff = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_TUFF);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_diamond_small = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_DIAMOND_SMALL);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_diamond_medium = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_DIAMOND_MEDIUM);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_diamond_large = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_DIAMOND_LARGE);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_diamond_buried = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_DIAMOND_BURIED);
        PlacedFeatures.register(featureRegisterable, ORE_ADAMANTIUM_BURIED, ore_adamantium_buried, modifiers(
                CountPlacementModifier.of(3),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(-88), YOffset.fixed(-8))
        ));
        PlacedFeatures.register(featureRegisterable, ORE_ADAMANTIUM_BURIED_SMALL, ore_adamantium_buried_small, modifiers(
                CountPlacementModifier.of(5),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(-8))
        ));
        PlacedFeatures.register(featureRegisterable, ORE_MITHRIL, ore_mithril, modifiers(
                CountPlacementModifier.of(3),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(40), YOffset.fixed(180))
        ));
        PlacedFeatures.register(featureRegisterable, ORE_MITHRIL_SMALL, ore_mithril_small, modifiers(
                CountPlacementModifier.of(7),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(70))
        ));
        PlacedFeatures.register(featureRegisterable, ORE_SILVER, ore_silver, modifiers(
                CountPlacementModifier.of(7),
                HeightRangePlacementModifier.uniform(YOffset.fixed(128), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_SILVER_SMALL, ore_silver_small, modifiers(
                CountPlacementModifier.of(10),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(127))
        ));
        PlacedFeatures.register(featureRegisterable, ORE_COPPER, ore_copper, modifiers(
                CountPlacementModifier.of(10),
                HeightRangePlacementModifier.uniform(YOffset.fixed(128), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_COPPER_SMALL, ore_copper_small, modifiers(
                CountPlacementModifier.of(15),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(127))
        ));
        PlacedFeatures.register(featureRegisterable, ORE_EMERALD, ore_emerald, modifiers(
                CountPlacementModifier.of(20),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_LAPIS, ore_lapis, modifiers(
                CountPlacementModifier.of(8),
                HeightRangePlacementModifier.uniform(YOffset.fixed(70), YOffset.fixed(320)))
        );
        PlacedFeatures.register(featureRegisterable, ORE_LAPIS_BURIED, ore_lapis_buried, modifiers(
                CountPlacementModifier.of(12),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(70)))
        );
        PlacedFeatures.register(featureRegisterable, ORE_IRON, ore_iron, modifiers(
                CountPlacementModifier.of(10),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()))
        );
        PlacedFeatures.register(featureRegisterable, ORE_IRON_SMALL, ore_iron_small, modifiers(
                CountPlacementModifier.of(20),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()))
        );
        PlacedFeatures.register(featureRegisterable, ORE_REDSTONE, ore_redstone, modifiers(
                CountPlacementModifier.of(20),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_DIRT, ore_dirt, modifiers(
                CountPlacementModifier.of(25),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_GRAVEL, ore_gravel, modifiers(
                CountPlacementModifier.of(25),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_TUFF, ore_tuff, modifiers(
                CountPlacementModifier.of(25),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_DIAMOND_FIRST_LAYER, ore_diamond_small, modifiers(
                CountPlacementModifier.of(5),
                HeightRangePlacementModifier.uniform(YOffset.fixed(128), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_DIAMOND_FIRST_LAYER_BURIED, ore_diamond_medium, modifiers(
                CountPlacementModifier.of(5),
                HeightRangePlacementModifier.uniform(YOffset.fixed(128), YOffset.getTop())
        ));
        PlacedFeatures.register(featureRegisterable, ORE_DIAMOND_SECOND_LAYER, ore_diamond_large, modifiers(
                CountPlacementModifier.of(5),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(127))
        ));
        PlacedFeatures.register(featureRegisterable, ORE_DIAMOND_SECOND_LAYER_BURIED, ore_diamond_buried, modifiers(
                CountPlacementModifier.of(5),
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(127))
        ));
    }
    public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Mme.MOD_ID, id));
    }
}
