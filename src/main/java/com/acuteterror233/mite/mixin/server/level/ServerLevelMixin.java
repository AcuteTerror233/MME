package com.acuteterror233.mite.mixin.server.level;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntityGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.saveddata.WeatherData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code ServerLevel} — drives weather and precipitation from the special moon phase.
 *
 * <p>Three redirects in {@code advanceWeatherCycle} override the weather data lookups: a
 * {@link SpecialMoonPhase#BLOOD_MOON blood moon} forces rain and thunder with no clear weather, a
 * {@link SpecialMoonPhase#BLUE_MOON blue moon} forces clear weather (100 ticks declared), and a
 * {@link SpecialMoonPhase#FROST_MOON frost moon} forces rain (rendered as snow) with no thunder.
 * Two further redirects in {@code tickPrecipitation} apply frost-moon effects to the blocks
 * actually rained on: {@code getPrecipitationAt} reports snow during a frost moon (freezing
 * precipitation in any biome) and {@code shouldFreeze} is reimplemented inline so standing water
 * freezes (skipping the climate gate during a frost moon), mirroring {@code LevelMixin} /
 * {@code BiomeMixin}. All hooks are server-side only — weather cycles and precipitation ticking
 * run exclusively on the server.</p>
 */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements WorldGenLevel, ServerEntityGetter{
    protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    /**
     * Redirects the {@code WeatherData#isThundering} call in {@code advanceWeatherCycle}: thunder is
     * forced on during a blood moon and off during blue/frost moons; otherwise vanilla data applies.
     *
     * @param instance the level's weather data
     * @return whether it is thundering under the current moon phase
     */
    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/saveddata/WeatherData;isThundering()Z"))
    private boolean isThundering(WeatherData instance) {
        SpecialMoonPhase smp = environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE);
        switch (smp){
            case BLOOD_MOON -> {
                return true;
            }
            case BLUE_MOON, FROST_MOON -> {
                return false;
            }
        }
        return instance.isThundering();
    }

    /**
     * Redirects the {@code WeatherData#isRaining} call in {@code advanceWeatherCycle}: rain is
     * forced on during blood/frost moons and off during a blue moon; otherwise vanilla data applies.
     *
     * @param instance the level's weather data
     * @return whether it is raining under the current moon phase
     */
    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/saveddata/WeatherData;isRaining()Z"))
    private boolean isRaining(WeatherData instance) {
        SpecialMoonPhase smp = environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE);
        switch (smp){
            case BLOOD_MOON, FROST_MOON -> {
                return true;
            }
            case BLUE_MOON -> {
                return false;
            }
        }
        return instance.isRaining();
    }

    /**
     * Redirects the {@code WeatherData#getClearWeatherTime} call in {@code advanceWeatherCycle}: a
     * blue moon declares 100 clear ticks, blood/frost moons declare 0; otherwise vanilla data applies.
     *
     * @param instance the level's weather data
     * @return the clear-weather duration under the current moon phase
     */
    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/saveddata/WeatherData;getClearWeatherTime()I"))
    private int getClearWeatherTime(WeatherData instance) {
        SpecialMoonPhase smp = environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE);
        switch (smp){
            case BLUE_MOON -> {
                return 100;
            }
            case FROST_MOON, BLOOD_MOON -> {
                return 0;
            }
        }
        return instance.getClearWeatherTime();
    }

    /**
     * Redirects the {@code Biome#shouldFreeze} call in {@code tickPrecipitation}: reimplements the
     * freeze check inline, bypassing the climate gate during a frost moon.
     *
     * @param instance the biome of the rained-on position
     * @param level    the level being ticked
     * @param pos      the position receiving precipitation
     * @return whether the water at {@code pos} should freeze to ice
     */
    @Redirect(method = "tickPrecipitation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
    public boolean shouldFreeze(Biome instance, LevelReader level, BlockPos pos) {
        if (instance.warmEnoughToRain(pos, level.getSeaLevel()) && !environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.FROST_MOON)) {
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

    /**
     * Redirects the {@code Biome#getPrecipitationAt} call in {@code tickPrecipitation}: reports snow
     * during a frost moon (freezing precipitation in any biome); otherwise the biome decides.
     *
     * @param instance the biome of the rained-on position
     * @param pos      the position receiving precipitation
     * @param seaLevel current sea level, used by the vanilla climate check
     * @return the precipitation type at {@code pos}
     */
    @Redirect(method = "tickPrecipitation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation getPrecipitationAt(Biome instance, BlockPos pos, int seaLevel){
        if (environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.FROST_MOON)) {
            return Biome.Precipitation.SNOW;
        }
        return instance.getPrecipitationAt(pos, seaLevel);
    }
}
