package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.Mme;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

/**
 * 地下结构放置：定义并注册“地下怪物房”的 PlacedFeature。
 */
public class UndergroundPlacedFeatures {
    public static final RegistryKey<PlacedFeature> UNDERGROUND_MONSTER_ROOM = of("underground_monster_room");

    /**
     * 注册地下怪物房的放置规则：密度、方形分布、垂直高度范围与生物群系限制。
     */
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> registryEntry = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.MONSTER_ROOM);
        PlacedFeatures.register(
                featureRegisterable,
                UNDERGROUND_MONSTER_ROOM,
                registryEntry,
                CountPlacementModifier.of(50),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6),YOffset.fixed(119)),
                BiomePlacementModifier.of()
        );
    }

    /**
     * 生成命名空间内的 PlacedFeature 注册键。
     */
    public static RegistryKey<PlacedFeature> of(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Mme.MOD_ID, name));
    }

}
