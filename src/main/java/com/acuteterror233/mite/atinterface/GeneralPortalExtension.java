package com.acuteterror233.mite.atinterface;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.List;

public interface GeneralPortalExtension {
    boolean CheckLowerCorner(BlockView world, Block block);
    List<BlockState> getCornerStateList(BlockView world);
    List<BlockPos> getCornerPosList();
    boolean isRunePortalValid(BlockView world, TagKey<Block> tag);
    void createPortal(WorldAccess world, Block portal);
    void createRunePortal(WorldAccess world, Block portal, BlockPos pos);
}
