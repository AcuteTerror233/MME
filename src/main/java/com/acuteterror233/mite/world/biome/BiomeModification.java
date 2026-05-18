package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.gen.feature.OverworldPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class BiomeModification {
    public static void init(){
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        Biomes.MEADOW,
                        Biomes.GROVE,
                        Biomes.WINDSWEPT_HILLS,
                        Biomes.DARK_FOREST
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_RARE
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        Biomes.WINDSWEPT_FOREST,
                        Biomes.TAIGA,
                        Biomes.SNOWY_TAIGA,
                        Biomes.OLD_GROWTH_PINE_TAIGA,
                        Biomes.OLD_GROWTH_SPRUCE_TAIGA
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OverworldPlacedFeatures.BLUE_BERRY_COMMON
        );
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.GHOUL, 10, 4, 4);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.SHADOW, 8, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.WIGHT, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.INVISIBLE_STALKER, 7, 1, 3);

        BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), MobCategory.MONSTER, MMEEntityTypes.FIRE_ELEMENTAL, 15, 1, 2);
    }
}