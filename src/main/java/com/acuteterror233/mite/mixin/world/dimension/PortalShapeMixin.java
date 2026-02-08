package com.acuteterror233.mite.mixin.world.dimension;

import com.acuteterror233.mite.atinterface.UniversalPortalShapeExtension;
import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.block.entity.RunePortalBlockEntity;
import com.acuteterror233.mite.registry.tag.MMETags;
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

@Mixin(PortalShape.class)
public class PortalShapeMixin implements UniversalPortalShapeExtension {
    @Shadow
    @Final
    private BlockPos bottomLeft;
    @Shadow
    @Final
    private Direction rightDir;
    @Shadow
    @Final
    private int width;
    @Shadow
    @Final
    private int height;
    @Shadow
    @Final
    private Direction.Axis axis;

    @Override
    public boolean MME$CheckBottomCorner(BlockGetter world, Block block) {
        BlockPos bottomRight = this.bottomLeft.below().relative(this.rightDir, this.width);
        BlockPos bottomLeft = this.bottomLeft.below().relative(this.rightDir.getOpposite(), 1);
        return world.getBlockState(bottomLeft).is(block) || world.getBlockState(bottomRight).is(block);
    }
    @Override
    public List<BlockState> MME$GetBottomStateList(BlockGetter world) {
        List<BlockState> list = new ArrayList<>();
        for (BlockPos blockPos : MME$GetBottomPosList()) {
            list.add(world.getBlockState(blockPos));
        }
        return list;
    }

    @Override
    public List<BlockPos> MME$GetBottomPosList() {
        BlockPos bottomLeft = this.bottomLeft.below().relative(this.rightDir, this.width);
        BlockPos bottomRight = this.bottomLeft.below().relative(this.rightDir.getOpposite(), 1);

        BlockPos topLeft = bottomLeft.above(this.height + 1);
        BlockPos topRight = bottomRight.above(this.height + 1);

        return List.of(bottomLeft, bottomRight, topLeft, topRight);
    }


    @Override
    public boolean MME$VerifyPortalValid(BlockGetter world, TagKey<Block> tag) {
        for (BlockPos pos : MME$GetBottomPosList()) {
            if (!world.getBlockState(pos).is(tag)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void MME$CreatePortal(LevelAccessor world, Block portal) {
        BlockState state = portal.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
                .forEach(pos -> world.setBlock(pos, state, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE));
    }

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
     * @reason 放宽传送门内部有效状态
     */
    @Overwrite
    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.is(MMETags.PORTAL);
    }
}
