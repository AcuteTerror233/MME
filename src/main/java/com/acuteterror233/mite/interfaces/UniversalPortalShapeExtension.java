package com.acuteterror233.mite.interfaces;

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
    default boolean MME$CheckBottomCorner(BlockGetter world, Block block) {
        throw new AssertionError("Implemented in Mixin");
    }
    default List<BlockState> MME$GetBottomStateList(BlockGetter world) {
        throw new AssertionError("Implemented in Mixin");
    }
    default List<BlockPos> MME$GetBottomPosList() {
        throw new AssertionError("Implemented in Mixin");
    }
    default boolean MME$VerifyPortalValid(BlockGetter world, TagKey<Block> tag) {
        throw new AssertionError("Implemented in Mixin");
    }
    default void MME$CreatePortal(LevelAccessor world, Block portal) {
        throw new AssertionError("Implemented in Mixin");
    }
    default void MME$CreateRunePortal(LevelAccessor world, BlockPos pos) {
        throw new AssertionError("Implemented in Mixin");
    }
}
