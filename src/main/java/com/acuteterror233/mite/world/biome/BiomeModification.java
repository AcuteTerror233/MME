package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.world.gen.feature.OverworldPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class BiomeModification {
    public static void init(){
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.MEADOW.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_RARE
        );
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.GROVE.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_RARE
        );
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.WINDSWEPT_HILLS.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_RARE
        );
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.DARK_FOREST.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_RARE
        );

        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.WINDSWEPT_FOREST.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_COMMON
        );
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.TAIGA.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_COMMON
        );
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.SNOWY_TAIGA.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_COMMON
        );
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.OLD_GROWTH_PINE_TAIGA.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_COMMON
        );
        BiomeModifications.addFeature(
                biomeSelectionContext -> biomeSelectionContext.getBiomeKey().isFor(Biomes.OLD_GROWTH_SPRUCE_TAIGA.registryKey()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_COMMON
        );
    }
}
