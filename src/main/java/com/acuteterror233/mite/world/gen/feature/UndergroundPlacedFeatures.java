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
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class UndergroundPlacedFeatures {
    public static final RegistryKey<PlacedFeature> UNDERGROUND_MONSTER_ROOM = of("underground_monster_room");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> registryEntry = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.MONSTER_ROOM);
        PlacedFeatures.register(
                featureRegisterable,
                UNDERGROUND_MONSTER_ROOM,
                registryEntry,
                CountPlacementModifier.of(50),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6),YOffset.fixed(119)),
                BiomePlacementModifier.of()
        );
    }

    public static RegistryKey<PlacedFeature> of(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(At_mite.MOD_ID, name));
    }

}
