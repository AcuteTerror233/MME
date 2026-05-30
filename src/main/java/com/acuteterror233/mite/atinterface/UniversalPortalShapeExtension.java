package com.acuteterror233.mite.atinterface;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 通用传送门形状扩展接口。
 * 允许自定义传送门结构检测逻辑。
 */
public interface UniversalPortalShapeExtension {
    boolean MME$CheckBottomCorner(BlockGetter world, Block block);
    List<BlockState> MME$GetBottomStateList(BlockGetter world);
    List<BlockPos> MME$GetBottomPosList();
    boolean MME$VerifyPortalValid(BlockGetter world, TagKey<Block> tag);
    void MME$CreatePortal(LevelAccessor world, Block portal);
    void MME$CreateRunePortal(LevelAccessor world, BlockPos pos);
}
