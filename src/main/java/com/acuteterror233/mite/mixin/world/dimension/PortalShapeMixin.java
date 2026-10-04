package com.acuteterror233.mite.mixin.world.dimension;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.block.entity.RunePortalBlockEntity;
import com.acuteterror233.mite.interfaces.UniversalPortalShapeExtension;
import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

/**
 * Mixin for {@code PortalShape} — implements the {@link UniversalPortalShapeExtension} duck
 * interface so the MME portal system (fired from {@code BaseFireBlockMixin#onPlace}) can inspect,
 * validate and fill any detected portal frame, and relaxes the valid interior states.
 *
 * <p>Capabilities added to every detected frame: checking whether the frame's bottom corners are a
 * given block (bedrock/mantle gates for the underground portals), collecting the four corner
 * positions/states, verifying all corners against a block tag (adamantium/mithril runestore frames
 * for rune portals), filling the interior with a portal block (optionally a
 * {@link MMEBlocks#RUNE_PORTAL rune portal} carrying a destination position in its block entity),
 * and the {@code isEmpty} overwrite accepting air, fire or any MME {@code PORTAL}-tagged block as
 * a valid frame interior. Frame detection runs on the server during fire placement; the
 * {@code isEmpty} relaxation also affects any client-side shape scanning.</p>
 */
@Mixin(PortalShape.class)
public class PortalShapeMixin implements UniversalPortalShapeExtension {
    /** Shadowed vanilla frame geometry: interior bottom-left corner. */
    @Shadow
    @Final
    private BlockPos bottomLeft;
    /** Shadowed vanilla frame geometry: horizontal direction toward the interior's right edge. */
    @Shadow
    @Final
    private Direction rightDir;
    /** Shadowed vanilla frame geometry: interior width. */
    @Shadow
    @Final
    private int width;
    /** Shadowed vanilla frame geometry: interior height. */
    @Shadow
    @Final
    private int height;
    /** Shadowed vanilla frame geometry: portal axis (X or Z). */
    @Shadow
    @Final
    private Direction.Axis axis;

    /**
     * Duck-interface implementation: whether either bottom corner of the frame (one block below the
     * interior, extended by the frame width) is the given block.
     *
     * @param world the level view containing the frame
     * @param block the block to look for under the frame
     * @return whether a bottom corner matches {@code block}
     */
    @Override
    public boolean MME$CheckBottomCorner(BlockGetter world, Block block) {
        BlockPos bottomRight = this.bottomLeft.below().relative(this.rightDir, this.width);
        BlockPos bottomLeft = this.bottomLeft.below().relative(this.rightDir.getOpposite(), 1);
        return world.getBlockState(bottomLeft).is(block) || world.getBlockState(bottomRight).is(block);
    }

    /**
     * Duck-interface implementation: the block states at the frame's four corner positions.
     *
     * @param world the level view containing the frame
     * @return the states of the bottom-left/bottom-right/top-left/top-right corners
     */
    @Override
    public List<BlockState> MME$GetBottomStateList(BlockGetter world) {
        List<BlockState> list = new ArrayList<>();
        for (BlockPos blockPos : MME$GetBottomPosList()) {
            list.add(world.getBlockState(blockPos));
        }
        return list;
    }

    /**
     * Duck-interface implementation: the frame's four corner positions (one below the interior at
     * both ends, plus the same offsets raised above the interior top).
     *
     * @return the list of corner positions
     */
    @Override
    public List<BlockPos> MME$GetBottomPosList() {
        BlockPos bottomLeft = this.bottomLeft.below().relative(this.rightDir, this.width);
        BlockPos bottomRight = this.bottomLeft.below().relative(this.rightDir.getOpposite(), 1);

        BlockPos topLeft = bottomLeft.above(this.height + 1);
        BlockPos topRight = bottomRight.above(this.height + 1);

        return List.of(bottomLeft, bottomRight, topLeft, topRight);
    }


    /**
     * Duck-interface implementation: whether all four frame corners belong to the given block tag
     * (used to classify runestore frames).
     *
     * @param world the level view containing the frame
     * @param tag   the block tag the corners must match
     * @return whether every corner state is in {@code tag}
     */
    @Override
    public boolean MME$VerifyPortalValid(BlockGetter world, TagKey<Block> tag) {
        for (BlockPos pos : MME$GetBottomPosList()) {
            if (!world.getBlockState(pos).is(tag)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Duck-interface implementation: fills the frame interior with the given portal block, oriented
     * to the frame axis.
     *
     * @param world  the level to build the portal in
     * @param portal the portal block to place
     */
    @Override
    public void MME$CreatePortal(LevelAccessor world, Block portal) {
        BlockState state = portal.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
                .forEach(pos -> world.setBlock(pos, state, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE));
    }

    /**
     * Duck-interface implementation: fills the frame interior with rune portal blocks and stores
     * the teleport destination position in each rune portal block entity.
     *
     * @param world          the level to build the portal in
     * @param targetLocation the destination position the rune portal teleports to
     */
    @Override
    public void MME$CreateRunePortal(LevelAccessor world, BlockPos targetLocation) {
        BlockState state = MMEBlocks.RUNE_PORTAL.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
                .forEach(pos -> {
                    world.setBlock(pos, state, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
                    BlockEntity blockEntity = world.getBlockEntity(pos);
                    if (blockEntity instanceof RunePortalBlockEntity runePortalBlock){
                        runePortalBlock.setDestinationPosPos(targetLocation);
                    }
                });
    }

    /**
     * @author AcuteTerror233
     * @reason Relax valid portal interior states
     *
     * <p>Overwrites vanilla {@code isEmpty}: air, fire and MME {@code PORTAL}-tagged blocks are all
     * valid frame interiors (lets MME portals be replaced/relinked without breaking the frame).</p>
     *
     * @param state the candidate interior state
     * @return whether the state may occupy a portal frame interior
     */
    @Overwrite
    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.is(MMEBlockTags.PORTAL);
    }
}
