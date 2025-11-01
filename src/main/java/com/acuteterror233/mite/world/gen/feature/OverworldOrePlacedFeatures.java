package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.At_mite;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class OverworldOrePlacedFeatures {
    public static final RegistryKey<PlacedFeature> OVERWORLD_ORE_SILVER = of("overworld_ore_silver");
    public static final RegistryKey<PlacedFeature> OVERWORLD_ORE_SILVER_SMALL = of("overworld_ore_silver_small");
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_silver = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_SILVER);
        RegistryEntry<ConfiguredFeature<?, ?>> ore_silver_small = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_SILVER_SMALL);
        PlacedFeatures.register(featureRegisterable, OVERWORLD_ORE_SILVER, ore_silver, modifiers(
                CountPlacementModifier.of(16),
                HeightRangePlacementModifier.uniform(YOffset.fixed(-48), YOffset.fixed(144))
        ));
        PlacedFeatures.register(featureRegisterable, OVERWORLD_ORE_SILVER_SMALL, ore_silver_small, modifiers(
                CountPlacementModifier.of(16),
                HeightRangePlacementModifier.uniform(YOffset.fixed(-48), YOffset.fixed(144))
        ));
    }
    public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(At_mite.MOD_ID, id));
    }
}
