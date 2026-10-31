package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.gen.feature.OverworldPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.List;

/**
 * Fabric biome modifier: injects MME content into vanilla biomes.
 * Adds the blueberry bush placed features to a fixed set of overworld biomes and registers the
 * MME monster spawn entries for the overworld and the nether.
 */
public final class BiomeModification {
    /**
     * Percentage of the vanilla wolf spawn weight kept in every biome. Vanilla weights are 8
     * (taiga family, savanna plateau, sparse jungle), 5 (forest), 2 (wooded badlands) and 1
     * (grove); at 25% they become 2 / 1 / 1 / 1, so a taiga pack turns from an everyday sight
     * into an occasional encounter while never dropping below weight 1.
     */
    public static final int WOLF_WEIGHT_PERCENT = 25;

    /**
     * Applies all biome modifications. Must run once during mod initialisation, before world
     * loading: the rare blueberry spread is added to meadow/grove/windswept hills/dark forest,
     * the common one to taiga-family and windswept-forest biomes, followed by the monster spawns.
     */
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
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.REVENANT, 5, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.SHADOW, 8, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.WIGHT, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, MMEEntityTypes.INVISIBLE_STALKER, 7, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), MobCategory.MONSTER, MMEEntityTypes.FIRE_ELEMENTAL, 15, 1, 2);

        lowerWolfSpawnWeight();
    }

    /**
     * Lowers the wolf spawn weight in every biome.
     *
     * <p>26.3 keeps the spawn table inside the biome's {@code gameplay/natural_mob_spawns}
     * environment attribute rather than in code or in MME's datagen output, so the entries have to
     * be rewritten at biome-load time. The existing entries are read first (they carry the
     * per-biome pack size: a flat 4 in the taiga family, 4-8 in the savanna plateau and wooded
     * badlands, 2-4 in the sparse jungle, a single wolf in the grove), then removed and re-added
     * with {@link #WOLF_WEIGHT_PERCENT} of their original weight. Pack sizes and every other
     * wolf spawn rule are left untouched.</p>
     */
    private static void lowerWolfSpawnWeight() {
        BiomeModifications.create(Identifier.fromNamespaceAndPath(MME.MOD_ID, "lower_wolf_spawn_weight"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), context -> {
                    BiomeModificationContext.MobSpawnSettingsContext spawns = context.getMobSpawnSettings();
                    List<Weighted<MobSpawnSettings.SpawnerData>> wolves = spawns.getMobs(MobCategory.CREATURE).stream()
                            .filter(entry -> entry.value().type() == EntityTypes.WOLF)
                            .toList();
                    if (wolves.isEmpty()) {
                        return;
                    }
                    spawns.removeSpawns((category, data) -> category == MobCategory.CREATURE && data.type() == EntityTypes.WOLF);
                    for (Weighted<MobSpawnSettings.SpawnerData> entry : wolves) {
                        spawns.addSpawn(MobCategory.CREATURE, entry.value(),
                                Math.max(1, entry.weight() * WOLF_WEIGHT_PERCENT / 100));
                    }
                });
    }
}