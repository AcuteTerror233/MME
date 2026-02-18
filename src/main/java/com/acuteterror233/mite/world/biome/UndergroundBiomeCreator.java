package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.world.gen.feature.UndergroundPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class UndergroundBiomeCreator {
    public static Biome createUnderground(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup){
        BiomeGenerationSettings.Builder lookupBackedBuilder = new BiomeGenerationSettings.Builder(featureLookup, carverLookup);
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        BiomeDefaultFeatures.addDefaultCrystalFormations(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultSprings(lookupBackedBuilder);
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.4F)
                .downfall(0.8F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomes.calculateSkyColor(0.4F))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_GROVE)).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .addCarver(Carvers.CAVE)
                        .addCarver(Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(Carvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createDripstoneCaves(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup){
        BiomeGenerationSettings.Builder lookupBackedBuilder = new BiomeGenerationSettings.Builder(featureLookup, carverLookup);
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        BiomeDefaultFeatures.addDefaultCrystalFormations(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultSprings(lookupBackedBuilder);
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        BiomeDefaultFeatures.addDripstone(lookupBackedBuilder);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8F)
                .downfall(0.4F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomes.calculateSkyColor(0.8F))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES)).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .addCarver(Carvers.CAVE)
                        .addCarver(Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(Carvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createLushCaves(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup){
        BiomeGenerationSettings.Builder lookupBackedBuilder = new BiomeGenerationSettings.Builder(featureLookup, carverLookup);
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        BiomeDefaultFeatures.addDefaultCrystalFormations(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(lookupBackedBuilder);
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        BiomeDefaultFeatures.addDefaultSprings(lookupBackedBuilder);
        BiomeDefaultFeatures.addLushCavesSpecialOres(lookupBackedBuilder);
        BiomeDefaultFeatures.addLushCavesVegetationFeatures(lookupBackedBuilder);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomes.calculateSkyColor(0.5F))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES)).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .addCarver(Carvers.CAVE)
                        .addCarver(Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(Carvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createDeepDark(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup){
        BiomeGenerationSettings.Builder lookupBackedBuilder = new BiomeGenerationSettings.Builder(featureLookup, carverLookup);
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        addDefaultOres(lookupBackedBuilder);
        lookupBackedBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, UndergroundPlacedFeatures.UNDERGROUND_MONSTER_ROOM);
        BiomeDefaultFeatures.addDefaultCrystalFormations(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(lookupBackedBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(lookupBackedBuilder);
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        BiomeDefaultFeatures.addDefaultSprings(lookupBackedBuilder);
        BiomeDefaultFeatures.addSculk(lookupBackedBuilder);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8F)
                .downfall(0.4F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomes.calculateSkyColor(0.8F))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DEEP_DARK)).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .addCarver(Carvers.CAVE)
                        .addCarver(Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(Carvers.CANYON)
                        .build())
                .build();
    }
    public static void addDefaultOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_ADAMANTIUM_BURIED);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_ADAMANTIUM_BURIED_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_MITHRIL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_MITHRIL_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_SILVER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_SILVER_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_COPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_COPPER_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_EMERALD);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_LAPIS);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_LAPIS_BURIED);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_IRON);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_IRON_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_REDSTONE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_TUFF);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_DIRT);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_GRAVEL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_DIAMOND_FIRST_LAYER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_DIAMOND_FIRST_LAYER_BURIED);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_DIAMOND_SECOND_LAYER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.ORE_DIAMOND_SECOND_LAYER_BURIED);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, net.minecraft.data.worldgen.placement.CavePlacements.GLOW_LICHEN);
    }
}
