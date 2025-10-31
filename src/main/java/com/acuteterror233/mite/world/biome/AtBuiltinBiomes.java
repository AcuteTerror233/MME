package com.acuteterror233.mite.world.biome;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;

public abstract class AtBuiltinBiomes {
    public static void bootstrap(Registerable<Biome> biomeRegisterable){
        RegistryEntryLookup<PlacedFeature> registryEntryLookup = biomeRegisterable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> registryEntryLookup2 = biomeRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        biomeRegisterable.register(AtBiomeKeys.UNDERGROUND,UndergroundBiomeCreator.createUnderground(registryEntryLookup,registryEntryLookup2));
    }
}
