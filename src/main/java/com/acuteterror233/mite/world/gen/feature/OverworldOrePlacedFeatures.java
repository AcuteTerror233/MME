package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class OverworldOrePlacedFeatures {
    public static final ResourceKey<PlacedFeature> OVERWORLD_ORE_SILVER = of("overworld_ore_silver");
    public static final ResourceKey<PlacedFeature> OVERWORLD_ORE_SILVER_SMALL = of("overworld_ore_silver_small");
    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable){
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> ore_silver = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_SILVER);
        Holder<ConfiguredFeature<?, ?>> ore_silver_small = registryEntryLookup.getOrThrow(UndergroundOreConfiguredFeatures.ORE_SILVER_SMALL);
        PlacementUtils.register(featureRegisterable, OVERWORLD_ORE_SILVER, ore_silver, modifiers(
                CountPlacement.of(16),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(144))
        ));
        PlacementUtils.register(featureRegisterable, OVERWORLD_ORE_SILVER_SMALL, ore_silver_small, modifiers(
                CountPlacement.of(16),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(144))
        ));
    }
    public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }
    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
