package com.acuteterror233.mite.mixin.world.level.biome;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code Biome} — lets a special moon phase override snow placement rules.
 *
 * <p>{@code shouldSnow} is fully overwritten: besides the vanilla conditions (biome precipitation
 * is snow, position within build height, block light below 10, air or existing snow, snow can
 * survive), snowing is also allowed while the
 * {@link MMEEnvironmentAttributes#SPECIAL_MOON_PHASE special moon phase} attribute equals
 * {@link SpecialMoonPhase#FROST_MOON frost moon}, even in biomes whose vanilla precipitation is
 * rain or none. The {@code @Shadow} precipitation accessor delegates to the real biome state.</p>
 *
 * <p>Applies on both sides: the server uses this for snow block placement during weather ticking,
 * the client for visual snowfall decisions.</p>
 */
@Mixin(Biome.class)
public abstract class BiomeMixin {
    /** Shadowed accessor for the vanilla precipitation decision at a position. */
    @Shadow
    public abstract Biome.Precipitation getPrecipitationAt(final BlockPos pos, final int seaLevel);

    /**
     * Overwrites vanilla {@code shouldSnow}: accepts either vanilla snow-precipitation biomes or
     * an active frost moon, then applies the vanilla light/occupancy/survivability checks.
     *
     * @param level the level consulted for height, light and block state
     * @param pos   the candidate snow position
     * @return whether snow may exist or be placed at {@code pos}
     */
    @Overwrite
    public boolean shouldSnow(final LevelReader level, final BlockPos pos) {
        if (this.getPrecipitationAt(pos, level.getSeaLevel()) != Biome.Precipitation.SNOW && !level.environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.FROST_MOON)) {
            return false;
        }

        if (level.isInsideBuildHeight(pos.getY()) && level.getBrightness(LightLayer.BLOCK, pos) < 10) {
            BlockState state = level.getBlockState(pos);
            return (state.isAir() || state.is(Blocks.SNOW)) && Blocks.SNOW.defaultBlockState().canSurvive(level, pos);
        }

        return false;
    }
}
