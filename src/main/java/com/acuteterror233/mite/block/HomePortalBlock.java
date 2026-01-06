package com.acuteterror233.mite.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class HomePortalBlock extends AbstractPortalBlock {
    public HomePortalBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel world, Entity entity, BlockPos pos) {
        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);
        return new TeleportTransition(overworld, world.getSharedSpawnPos().getBottomCenter(), Vec3.ZERO, 0.0F, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
    }
}
