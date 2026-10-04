package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code NetherPortalBlock} — reroutes nether portal travel to MME's underground
 * dimension instead of the vanilla Nether.
 *
 * <p>{@code getPortalDestination} is fully overwritten: a portal in the Nether sends entities to
 * MME's {@code UNDERGROUND_LEVEL} (destination Y = -60, scaled coordinates), while a portal
 * anywhere else (including the underground level itself) sends them back to the Nether
 * (destination Y = 50). Coordinates are scaled by the teleportation factor between the two
 * dimension types and clamped to the destination world border; the vanilla exit-portal search
 * (shadowed {@code getExitPortal}) then locates or builds the arrival frame. Server-side only —
 * portal destination resolution happens during server teleport handling.</p>
 */
@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {
    /**
     * @author  AcuteTerror233
     * @reason Changes nether portal teleportation logic
     *
     * <p>Overwrites vanilla {@code getPortalDestination}: swaps the Nether for MME's underground
     * level as the paired dimension and adjusts the arrival height accordingly.</p>
     *
     * @param world  the server level the entity is teleporting from
     * @param entity the traveling entity
     * @param pos    the portal block position entered
     * @return the teleport transition into the paired dimension, or null when it is not loaded
     */
    @Overwrite
    @Nullable
    public TeleportTransition getPortalDestination(ServerLevel world, Entity entity, BlockPos pos) {
        ResourceKey<Level> registryKey = world.dimension() == Level.NETHER ? MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY : Level.NETHER;
        ServerLevel serverWorld = world.getServer().getLevel(registryKey);
        if (serverWorld == null) {
            return null;
        } else {
            boolean bl = serverWorld.dimension() == Level.NETHER;
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            int y = bl ? 50 : -60;
            double d = DimensionType.getTeleportationScale(world.dimensionType(), serverWorld.dimensionType());
            BlockPos blockPos = worldBorder.clampToBounds(entity.getX() * d, y, entity.getZ() * d);
            return this.getExitPortal(serverWorld, entity, pos, blockPos, bl, worldBorder);
        }
    }

    /** Shadowed delegate: vanilla search/build of the exit portal around the estimated position. */
    @Shadow
    @Nullable
    private TeleportTransition getExitPortal(ServerLevel world, Entity entity, BlockPos pos, BlockPos blockPos, boolean bl, WorldBorder worldBorder) {
        return null;
    }
}
