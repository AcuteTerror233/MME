package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.gen.feature.UndergroundPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * 地下维度生物群落创建器。
 * 构建地下世界各生物群落的参数：天气、音乐、生成特性、实体生成权重等。
 */
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
        addMonsters(builder);
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
        BiomeDefaultFeatures.addDripstone(lookupBackedBuilder);
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        addMonsters(builder);
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
        BiomeDefaultFeatures.addDefaultSprings(lookupBackedBuilder);
        BiomeDefaultFeatures.addLushCavesSpecialOres(lookupBackedBuilder);
        BiomeDefaultFeatures.addLushCavesVegetationFeatures(lookupBackedBuilder);
        BiomeDefaultFeatures.dripstoneCavesSpawns(builder);
        addMonsters(builder);
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
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_ADAMANTIUM_BURIED);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_ADAMANTIUM_BURIED_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_MITHRIL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_MITHRIL_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_SILVER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_SILVER_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_COPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_COPPER_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_EMERALD);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_LAPIS);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_LAPIS_BURIED);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_IRON);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_IRON_SMALL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_REDSTONE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_TUFF);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_DIRT);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_GRAVEL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_DIAMOND_FIRST_LAYER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_DIAMOND_FIRST_LAYER_BURIED);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_DIAMOND_SECOND_LAYER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacedFeatures.UNDERGROUND_ORE_DIAMOND_SECOND_LAYER_BURIED);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
    }

    public static void addMonsters(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(MMEEntityTypes.GHOUL, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(MMEEntityTypes.SHADOW, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(MMEEntityTypes.WIGHT, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(MMEEntityTypes.INVISIBLE_STALKER, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, 6, new MobSpawnSettings.SpawnerData(MMEEntityTypes.DEMON_SPIDER, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, 9, new MobSpawnSettings.SpawnerData(MMEEntityTypes.PHASE_SPIDER, 2, 4));
        builder.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(MMEEntityTypes.INFERNAL_CREEPER, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(MMEEntityTypes.VAMPIRE_BAT, 2, 5));
        builder.addSpawn(MobCategory.MONSTER, 3, new MobSpawnSettings.SpawnerData(MMEEntityTypes.NIGHTWING, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(MMEEntityTypes.GIANT_VAMPIRE_BAT, 1, 1));
    }
}
