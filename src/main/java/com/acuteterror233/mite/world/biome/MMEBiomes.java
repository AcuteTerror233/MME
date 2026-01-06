package com.acuteterror233.mite.world.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class MMEBiomes {
    public static void bootstrap(BootstrapContext<Biome> biomeRegisterable){
        HolderGetter<PlacedFeature> registryEntryLookup = biomeRegisterable.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> registryEntryLookup2 = biomeRegisterable.lookup(Registries.CONFIGURED_CARVER);
        biomeRegisterable.register(MMEBiomeKeys.UNDERGROUND,UndergroundBiomeCreator.createUnderground(registryEntryLookup,registryEntryLookup2));
        biomeRegisterable.register(MMEBiomeKeys.DRIPSTONE_CAVES,UndergroundBiomeCreator.createDripstoneCaves(registryEntryLookup,registryEntryLookup2));
        biomeRegisterable.register(MMEBiomeKeys.LUSH_CAVES,UndergroundBiomeCreator.createLushCaves(registryEntryLookup,registryEntryLookup2));
        biomeRegisterable.register(MMEBiomeKeys.DEEP_DARK,UndergroundBiomeCreator.createDeepDark(registryEntryLookup,registryEntryLookup2));
    }
}
