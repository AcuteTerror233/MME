package com.acuteterror233.mite.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import org.jetbrains.annotations.Nullable;

/**
 * 符文传送门：支持方块实体保存目标坐标，传送时优先传送到绑定位置，
 * 否则回退到维度默认出生点。
 */
public class RunePortalBlock extends AbstractPortalBlock implements BlockEntityProvider {

    public RunePortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        BlockPos pos1;
        if (world.getBlockEntity(pos) instanceof RunePortalBlockEntity runePortal) pos1 = runePortal.getDestinationPos();
        else pos1 = world.getSpawnPos();
        return new TeleportTarget(world, pos1.toBottomCenterPos(), Vec3d.ZERO, 0.0F, 0.0F, PositionFlag.combine(PositionFlag.DELTA, PositionFlag.ROT), TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RunePortalBlockEntity(pos, state);
    }
}
