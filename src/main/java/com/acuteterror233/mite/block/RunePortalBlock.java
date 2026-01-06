package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.entity.RunePortalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RunePortalBlock extends AbstractPortalBlock implements EntityBlock {

    public RunePortalBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel world, Entity entity, BlockPos pos) {
        BlockPos pos1;
        if (world.getBlockEntity(pos) instanceof RunePortalBlockEntity runePortal) pos1 = runePortal.getDestinationPos();
        else pos1 = world.getSharedSpawnPos();
        return new TeleportTransition(world, pos1.getBottomCenter(), Vec3.ZERO, 0.0F, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RunePortalBlockEntity(pos, state);
    }
}
