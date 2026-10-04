package com.acuteterror233.mite.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Universal portal shape extension interface (duck interface).
 * Extends vanilla {@code PortalShape} with frame-corner inspection and portal construction helpers
 * used by MME's custom portal detection.
 * Implemented by {@code PortalShapeMixin}; injected into vanilla {@code PortalShape}
 * via classTweaker {@code transitive-inject-interface}.
 */
public interface UniversalPortalShapeExtension {
    /**
     * Checks whether either bottom frame corner is made of {@code block}.
     *
     * @param world read access to the level
     * @param block candidate frame block
     * @return whether the block below the bottom-left or bottom-right interior corner matches {@code block}
     */
    default boolean MME$CheckBottomCorner(BlockGetter world, Block block) {
        throw new AssertionError("Implemented in Mixin");
    }
    /** @return the block states at the four frame corner positions (bottom-left, bottom-right, top-left, top-right). */
    default List<BlockState> MME$GetBottomStateList(BlockGetter world) {
        throw new AssertionError("Implemented in Mixin");
    }
    /** @return the four frame corner positions (bottom-left, bottom-right, top-left, top-right). */
    default List<BlockPos> MME$GetBottomPosList() {
        throw new AssertionError("Implemented in Mixin");
    }
    /**
     * Verifies that all four frame corner positions carry blocks from {@code tag}.
     *
     * @param world read access to the level
     * @param tag   tag of accepted frame blocks
     * @return whether the detected frame is a valid MME portal frame
     */
    default boolean MME$VerifyPortalValid(BlockGetter world, TagKey<Block> tag) {
        throw new AssertionError("Implemented in Mixin");
    }
    /** Fills the portal interior with {@code portal}, oriented to the frame's horizontal axis. */
    default void MME$CreatePortal(LevelAccessor world, Block portal) {
        throw new AssertionError("Implemented in Mixin");
    }
    /**
     * Fills the portal interior with MME rune portal blocks and stores {@code pos} as each block
     * entity's destination (the coordinate-shifted arrival point in the target dimension).
     *
     * @param world         modifiable level
     * @param pos           destination coordinates computed for the counterpart portal
     */
    default void MME$CreateRunePortal(LevelAccessor world, BlockPos pos) {
        throw new AssertionError("Implemented in Mixin");
    }
}
