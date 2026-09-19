package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.gen.feature.UndergroundPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Underground dimension biome creator.
 * Build parameters for each underground biome: weather, music, generation features, entity spawn weights, etc.
 */
public class UndergroundBiomeCreator {
    public static Biome createUnderground(HolderGetter<PlacedFeature> featureLookup, HolderGetter<WorldCarver> carverLookup){
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
                .setAttribute(EnvironmentAttributes.SKY_COLOR, ARGB.vector3fFromRGB24(OverworldBiomes.calculateSkyColor(0.4F)))
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4159204).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .addCarver(Carvers.CAVE)
                        .addCarver(Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(Carvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createDripstoneCaves(HolderGetter<PlacedFeature> featureLookup, HolderGetter<WorldCarver> carverLookup){
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
                .setAttribute(EnvironmentAttributes.SKY_COLOR, ARGB.vector3fFromRGB24(OverworldBiomes.calculateSkyColor(0.8F)))
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4159204).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .addCarver(Carvers.CAVE)
                        .addCarver(Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(Carvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createLushCaves(HolderGetter<PlacedFeature> featureLookup, HolderGetter<WorldCarver> carverLookup){
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
                .setAttribute(EnvironmentAttributes.SKY_COLOR, ARGB.vector3fFromRGB24(OverworldBiomes.calculateSkyColor(0.5F)))
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4159204).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder
                        .addCarver(Carvers.CAVE)
                        .addCarver(Carvers.CAVE_EXTRA_UNDERGROUND)
                        .addCarver(Carvers.CANYON)
                        .build())
                .build();
    }
    public static Biome createDeepDark(HolderGetter<PlacedFeature> featureLookup, HolderGetter<WorldCarver> carverLookup){
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
                .setAttribute(EnvironmentAttributes.SKY_COLOR, ARGB.vector3fFromRGB24(OverworldBiomes.calculateSkyColor(0.8F)))
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4159204).build())
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
        builder.addSpawn(MMEEntityTypes.GHOUL, 10, UniformInt.of(4, 4));
        builder.addSpawn(MMEEntityTypes.SHADOW, 10, UniformInt.of(1, 3));
        builder.addSpawn(MMEEntityTypes.WIGHT, 10, UniformInt.of(1, 2));
        builder.addSpawn(MMEEntityTypes.INVISIBLE_STALKER, 5, UniformInt.of(1, 3));
        builder.addSpawn(MMEEntityTypes.DEMON_SPIDER, 6, UniformInt.of(1, 3));
        builder.addSpawn(MMEEntityTypes.PHASE_SPIDER, 9, UniformInt.of(2, 4));
        builder.addSpawn(MMEEntityTypes.INFERNAL_CREEPER, 8, UniformInt.of(1, 2));
        builder.addSpawn(MMEEntityTypes.VAMPIRE_BAT, 5, UniformInt.of(2, 5));
        builder.addSpawn(MMEEntityTypes.NIGHTWING, 3, UniformInt.of(1, 2));
        builder.addSpawn(MMEEntityTypes.GIANT_VAMPIRE_BAT, 2, UniformInt.of(1, 1));
    }
}
