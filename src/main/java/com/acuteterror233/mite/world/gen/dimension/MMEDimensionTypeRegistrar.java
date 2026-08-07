package com.acuteterror233.mite.world.gen.dimension;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.registry.tag.MMETimelineTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.timeline.Timeline;

/**
 * MME 维度类型注册器。
 * 在数据生成阶段注册地下维度的维度类型参数。
 */
public class MMEDimensionTypeRegistrar {
    public static final ResourceKey<Level> UNDERGROUND_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "underground"));
    public static final ResourceKey<DimensionType> UNDERGROUND_DIMENSION_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE,
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "underground_type"));
    public static void bootstrap(BootstrapContext<DimensionType> context) {
        HolderGetter<Timeline> holderGetter = context.lookup(Registries.TIMELINE);
        context.register(UNDERGROUND_DIMENSION_TYPE_KEY, new DimensionType(
                true,
                false,
                true,
                4.0,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                0.1F,
                new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0),
                DimensionType.Skybox.NONE,
                DimensionType.CardinalLightType.DEFAULT,
                EnvironmentAttributeMap.builder()
                        .set(EnvironmentAttributes.FOG_COLOR, -4138753)
                        .set(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(0.8F))
                        .set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.OVERWORLD)
                        .set(EnvironmentAttributes.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK)
                        .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                        .set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, true)
                        .set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS)
                        .build(),
                holderGetter.getOrThrow(MMETimelineTags.IN_UNDERGROUND)));
    }
}
