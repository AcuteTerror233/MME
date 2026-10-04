package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;


/**
 * Overworld placed feature registration.
 * Defines generation rules for overworld features (height, frequency, biome, etc.).
 */
public class OverworldPlacedFeatures {
    // Placed feature keys: overworld silver ores and blueberry bush variants (common/rare).
    public static final ResourceKey<PlacedFeature> OVERWORLD_ORE_SILVER = of("overworld_ore_silver");
    public static final ResourceKey<PlacedFeature> OVERWORLD_ORE_SILVER_SMALL = of("overworld_ore_silver_small");
    public static final ResourceKey<PlacedFeature> BLUE_BERRY_COMMON = of("blue_berry_common");
    public static final ResourceKey<PlacedFeature> BLUE_BERRY_RARE = of("blue_berry_rare");

    /** Registers silver ore placement (y -48..144) and blueberry bush placement on grass. */
    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable){
        HolderGetter<Feature> registryEntryLookup = featureRegisterable.lookup(Registries.FEATURE);
        Holder<Feature> ore_silver = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_SILVER);
        Holder<Feature> ore_silver_small = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.ORE_SILVER_SMALL);
        Holder<Feature> blue_berry_bush = registryEntryLookup.getOrThrow(OverworldConfiguredFeatures.BLUE_BERRY_BUSH);
        // Overworld silver ore: 16 vein attempts per chunk between y -48 and 144.
        PlacementUtils.register(featureRegisterable, OVERWORLD_ORE_SILVER, ore_silver,
                CountPlacement.of(16),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(144)),
                BiomeFilter.biome()
        );
        PlacementUtils.register(featureRegisterable, OVERWORLD_ORE_SILVER_SMALL, ore_silver_small,
                CountPlacement.of(16),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(144)),
                BiomeFilter.biome()
        );
        // Blueberry bushes on the surface, only over grass blocks; common occurs 6x more often than rare.
        PlacementUtils.register(featureRegisterable, BLUE_BERRY_COMMON, blue_berry_bush,
                RarityFilter.onAverageOnceEvery(64),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome(),
                CountPlacement.of(96),
                OffsetPlacement.ofTriangle(7, 3),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK))
                )
        );
        PlacementUtils.register(featureRegisterable, BLUE_BERRY_RARE, blue_berry_bush,
                RarityFilter.onAverageOnceEvery(384),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome(),
                CountPlacement.of(96),
                OffsetPlacement.ofTriangle(7, 3),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK))
                )
        );

    }
    /** Creates a placed feature resource key under the MME namespace. */
    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
