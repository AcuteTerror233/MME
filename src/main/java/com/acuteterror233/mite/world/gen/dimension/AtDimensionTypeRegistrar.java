package com.acuteterror233.mite.world.gen.dimension;

import com.acuteterror233.mite.At_mite;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class AtDimensionTypeRegistrar {
    public static final RegistryKey<DimensionOptions> UNDERGROUND_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.of(At_mite.MOD_ID, "underground"));
    public static final RegistryKey<World> UNDERGROUND_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.of(At_mite.MOD_ID, "underground"));
    public static final RegistryKey<DimensionType> UNDERGROUND_DIMENSION_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.of(At_mite.MOD_ID, "underground_type"));

    public static void bootstrap(Registerable<DimensionType> context) {
        context.register(UNDERGROUND_DIMENSION_TYPE_KEY, new DimensionType(
                OptionalLong.of(30000L),
                true,
                true,
                true,
                false,
                1.0f,
                false,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                Identifier.of(At_mite.MOD_ID, "underground"),
                0.0f,
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 7), 0)));
    }
}
