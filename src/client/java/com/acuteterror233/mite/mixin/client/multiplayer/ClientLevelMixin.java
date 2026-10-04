package com.acuteterror233.mite.mixin.client.multiplayer;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.client.multiplayer.CacheSlot;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Client-side mixin into {@link ClientLevel}, mirroring the server-side {@code LevelMixin}
 * frost-moon logic: while the {@link SpecialMoonPhase#FROST_MOON} attribute is active, all
 * precipitation on the client reports as snow so weather rendering (falling particles, etc.)
 * matches the server's frozen world without a server round-trip.
 */
@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level implements BlockAndTintGetter, CacheSlot.Cleaner<ClientLevel> {
    @Shadow
    @Final
    private EnvironmentAttributeSystem environmentAttributes;

    protected ClientLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    /**
     * Redirects the biome precipitation lookup inside {@code getPrecipitationAt}: during the
     * frost moon every position reports {@link Biome.Precipitation#SNOW} regardless of biome,
     * matching the server-side frost-moon override in {@code LevelMixin}.
     *
     * @param instance the biome being queried
     * @param pos      the queried position
     * @param seaLevel sea level used by the vanilla precipitation check
     * @return snow during the frost moon, otherwise the vanilla biome result
     */
    @Redirect(method = "getPrecipitationAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation getPrecipitationAt(Biome instance, BlockPos pos, int seaLevel){
        if (environmentAttributes.getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.FROST_MOON)) {
            return Biome.Precipitation.SNOW;
        }
        return instance.getPrecipitationAt(pos, seaLevel);
    }
}
