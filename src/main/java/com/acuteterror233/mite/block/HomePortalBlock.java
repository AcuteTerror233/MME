package com.acuteterror233.mite.block;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HomePortalBlock extends AbstractPortalBlock {
    public HomePortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        ServerWorld overworld = world.getServer().getWorld(World.OVERWORLD);
        return new TeleportTarget(overworld, world.getSpawnPos().toBottomCenterPos(), Vec3d.ZERO, 0.0F, 0.0F, PositionFlag.combine(PositionFlag.DELTA, PositionFlag.ROT), TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET));
    }
}
