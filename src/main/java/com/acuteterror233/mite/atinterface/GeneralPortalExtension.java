package com.acuteterror233.mite.atinterface;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.List;

public interface GeneralPortalExtension {
    boolean CheckLowerCorner(BlockView world,Block block);
    List<BlockPos> getCornerList();
    void createPortal(WorldAccess world, Block portal);
    void createRunePortal(WorldAccess world, Block portal, BlockPos pos);
}
