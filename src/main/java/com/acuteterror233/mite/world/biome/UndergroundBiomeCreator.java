package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.world.gen.feature.UndergroundOrePlacedFeatures;
import com.acuteterror233.mite.world.gen.feature.UndergroundPlacedFeatures;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class UndergroundBiomeCreator {
    public static Biome createUnderground(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup){
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        DefaultBiomeFeatures.addAmethystGeodes(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
        DefaultBiomeFeatures.addSprings(lookupBackedBuilder);
        DefaultBiomeFeatures.addDripstoneCaveMobs(builder);
        DefaultBiomeFeatures.addMineables(lookupBackedBuilder);
        return new Biome.Builder()
                .precipitation(false)
                .temperature(0.4F)
                .downfall(0.8F)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomeCreator.getSkyColor(0.4F))
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_GROVE)).build())
                .spawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .carver(ConfiguredCarvers.CAVE)
                        .carver(ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND)
                        .carver(ConfiguredCarvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createDripstoneCaves(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup){
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        DefaultBiomeFeatures.addAmethystGeodes(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
        DefaultBiomeFeatures.addSprings(lookupBackedBuilder);
        DefaultBiomeFeatures.addDripstoneCaveMobs(builder);
        DefaultBiomeFeatures.addMineables(lookupBackedBuilder);
        DefaultBiomeFeatures.addDripstone(lookupBackedBuilder);
        return new Biome.Builder()
                .precipitation(false)
                .temperature(0.8F)
                .downfall(0.4F)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomeCreator.getSkyColor(0.8F))
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES)).build())
                .spawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .carver(ConfiguredCarvers.CAVE)
                        .carver(ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND)
                        .carver(ConfiguredCarvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createLushCaves(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup){
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        DefaultBiomeFeatures.addAmethystGeodes(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
        DefaultBiomeFeatures.addDripstoneCaveMobs(builder);
        DefaultBiomeFeatures.addSprings(lookupBackedBuilder);
        DefaultBiomeFeatures.addClayOre(lookupBackedBuilder);
        DefaultBiomeFeatures.addLushCavesDecoration(lookupBackedBuilder);
        DefaultBiomeFeatures.addMineables(lookupBackedBuilder);
        return new Biome.Builder()
                .precipitation(false)
                .temperature(0.5F)
                .downfall(0.5F)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomeCreator.getSkyColor(0.5F))
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_LUSH_CAVES)).build())
                .spawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .carver(ConfiguredCarvers.CAVE)
                        .carver(ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND)
                        .carver(ConfiguredCarvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createDeepDark(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup){
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        DefaultBiomeFeatures.addAmethystGeodes(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);
        DefaultBiomeFeatures.addDripstoneCaveMobs(builder);
        DefaultBiomeFeatures.addSculk(lookupBackedBuilder);
        DefaultBiomeFeatures.addMineables(lookupBackedBuilder);
        return new Biome.Builder()
                .precipitation(false)
                .temperature(0.8F)
                .downfall(0.4F)
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomeCreator.getSkyColor(0.8F))
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_DEEP_DARK)).build())
                .spawnSettings(builder.build())
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
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_COPPER);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_COPPER_SMALL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_EMERALD);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_LAPIS);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_LAPIS_BURIED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_IRON);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_IRON_SMALL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_REDSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_TUFF);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_DIRT);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_GRAVEL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_DIAMOND_FIRST_LAYER);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_DIAMOND_FIRST_LAYER_BURIED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_DIAMOND_SECOND_LAYER);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UndergroundOrePlacedFeatures.ORE_DIAMOND_SECOND_LAYER_BURIED);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, net.minecraft.world.gen.feature.UndergroundPlacedFeatures.GLOW_LICHEN);
    }
}
