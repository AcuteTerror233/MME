package com.acuteterror233.mite.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Home portal block.
 * Teleports entities to the overworld world spawn point.
 *
 * <p>Teleport semantics: the target dimension is always the overworld, while the coordinates come
 * from {@code world.getRespawnData().pos()}. For 26.3, {@code Level.getRespawnData()} on the
 * Nether/End delegates through {@code DerivedLevelData} to the wrapped overworld data, so it always
 * returns the overworld world spawn — the coordinates therefore always match the overworld target
 * dimension, regardless of the dimension the portal stands in.
 */
public class HomePortalBlock extends AbstractPortalBlock {
    public HomePortalBlock(Properties settings) {
        super(settings);
    }

    /**
     * Builds the teleport transition to the overworld world spawn.
     *
     * @param world  the dimension the teleporting entity is currently in (source of the spawn data)
     * @param entity the entity passing through the portal
     * @param pos    the portal block position (not used to compute the destination)
     * @return a transition into the overworld at bottom-center of the world spawn; velocity and
     *         rotation stay relative to the entity's current motion
     */
    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel world, Entity entity, BlockPos pos) {
        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);
        return new TeleportTransition(overworld, Vec3.atBottomCenterOf(world.getRespawnData().pos()), Vec3.ZERO, 0.0F, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
    }
}
