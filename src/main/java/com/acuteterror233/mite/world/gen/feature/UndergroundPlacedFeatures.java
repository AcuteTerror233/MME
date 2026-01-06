package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class UndergroundPlacedFeatures {
    public static final ResourceKey<PlacedFeature> UNDERGROUND_MONSTER_ROOM = of("underground_monster_room");

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable){
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> registryEntry = registryEntryLookup.getOrThrow(CaveFeatures.MONSTER_ROOM);
        PlacementUtils.register(
                featureRegisterable,
                UNDERGROUND_MONSTER_ROOM,
                registryEntry,
                CountPlacement.of(50),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6),VerticalAnchor.absolute(119)),
                BiomeFilter.biome()
        );
    }

    /**
     * 生成命名空间内的 PlacedFeature 注册键。
     */
    public static ResourceKey<PlacedFeature> of(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, name));
    }

}
