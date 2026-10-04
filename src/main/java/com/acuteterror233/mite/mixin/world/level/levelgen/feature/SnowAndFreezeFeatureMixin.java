package com.acuteterror233.mite.mixin.world.level.levelgen.feature;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code SnowAndFreezeFeature} — world-generation freezing that respects (or ignores) the
 * special moon phase.
 *
 * <p>The {@code place} redirect replaces the vanilla {@code Biome#shouldFreeze} decision with an
 * inline reimplementation: a position is frozen when it can freeze by climate rules
 * ({@code warmEnoughToRain} normally blocks it), block light is below 10, it holds water, and it is
 * not fully surrounded by open water. During a
 * {@link MMEEnvironmentAttributes#SPECIAL_MOON_PHASE frost moon} the climate gate is skipped, so
 * even warm biomes freeze over as chunks generate. Runs during chunk decoration on the server only
 * (world gen is server-side); note this only shapes newly generated terrain, not existing chunks.</p>
 */
@Mixin(SnowAndFreezeFeature.class)
public class SnowAndFreezeFeatureMixin {

    /**
     * Redirects the {@code Biome#shouldFreeze} call inside {@code place}: applies the vanilla-style
     * freeze conditions inline, bypassing the climate check while a frost moon is active.
     *
     * @param instance      the biome of the position being generated
     * @param level         the level reader used during world generation
     * @param pos           the candidate freeze position
     * @param checkNeighbors the vanilla neighbor-check flag (ignored; neighbor water check is done inline)
     * @return whether the water at {@code pos} should be frozen to ice
     */
    @Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z"))
    public boolean shouldFreeze(Biome instance, LevelReader level, BlockPos pos, boolean checkNeighbors) {
        if (instance.warmEnoughToRain(pos, level.getSeaLevel()) && !level.environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.FROST_MOON)) {
            return false;
        }

        if (level.isInsideBuildHeight(pos.getY()) && level.getBrightness(LightLayer.BLOCK, pos) < 10) {
            BlockState blockState = level.getBlockState(pos);
            FluidState fluidState = level.getFluidState(pos);
            if (fluidState.is(Fluids.WATER) && blockState.getBlock() instanceof LiquidBlock) {
                boolean surroundedByWater = level.isWaterAt(pos.west()) && level.isWaterAt(pos.east()) && level.isWaterAt(pos.north()) && level.isWaterAt(pos.south());
                return !surroundedByWater;
            }
        }
        return false;
    }
}
