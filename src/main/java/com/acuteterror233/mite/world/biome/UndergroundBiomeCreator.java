package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.world.gen.feature.UndergroundOrePlacedFeatures;
import com.acuteterror233.mite.world.gen.feature.UndergroundPlacedFeatures;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class UndergroundBiomeCreator {
    public static Biome createUnderground(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup){
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        return new Biome.Builder()
                .precipitation(false)
                .temperature(0.4F)
                .downfall(0.8F)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomeCreator.getSkyColor(0.4F))
                        .moodSound(BiomeMoodSound.CAVE).build())
                .spawnSettings(new SpawnSettings.Builder().build())
                .generationSettings(lookupBackedBuilder
                        .carver(ConfiguredCarvers.CAVE)
                        .carver(ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND)
                        .carver(ConfiguredCarvers.CANYON)
                        .build())
                .build();

    }
    public static void addDefaultOres(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_ADAMANTIUM_BURIED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_ADAMANTIUM_BURIED_SMALL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_MITHRIL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_MITHRIL_SMALL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_SILVER);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_SILVER_SMALL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_EMERALD);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_LAPIS);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_LAPIS_BURIED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_DIAMOND);
    }
}
