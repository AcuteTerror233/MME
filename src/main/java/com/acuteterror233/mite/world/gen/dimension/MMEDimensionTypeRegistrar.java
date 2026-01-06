package com.acuteterror233.mite.world.gen.dimension;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class MMEDimensionTypeRegistrar {
    public static final ResourceKey<Level> UNDERGROUND_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "underground"));
    public static final ResourceKey<DimensionType> UNDERGROUND_DIMENSION_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "underground_type"));
    public static void bootstrap(BootstrapContext<DimensionType> context) {
        context.register(UNDERGROUND_DIMENSION_TYPE_KEY, new DimensionType(
                OptionalLong.of(30000L),
                true,
                true,
                false,
                false,
                1.0f,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "underground"),
                0.0f,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
    }
}
