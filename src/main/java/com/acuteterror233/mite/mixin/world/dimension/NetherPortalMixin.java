package com.acuteterror233.mite.mixin.world.dimension;

import com.acuteterror233.mite.atinterface.GeneralPortalExtension;
import com.acuteterror233.mite.block.RunePortalBlockEntity;
import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(NetherPortal.class)
public class NetherPortalMixin implements GeneralPortalExtension {
    @Shadow
    @Final
    private BlockPos lowerCorner;
    @Shadow
    @Final
    private Direction negativeDir;
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
    public boolean CheckLowerCorner(BlockView world, Block block) {
        BlockPos bottomRight = this.lowerCorner.down().offset(this.negativeDir, this.width);
        BlockPos bottomLeft = this.lowerCorner.down().offset(this.negativeDir.getOpposite(), 1);
        return world.getBlockState(bottomLeft).isOf(block) || world.getBlockState(bottomRight).isOf(block);
    }
    @Override
    public List<BlockPos> getCornerList() {
        BlockPos pos = this.lowerCorner.down().offset(this.negativeDir, this.width);
        BlockPos pos1 = this.lowerCorner.down().offset(this.negativeDir.getOpposite(), 1);
        return List.of(pos, pos1, pos.up(this.height + 1), pos1.up(this.height + 1));
    }

    @Override
    public void createPortal(WorldAccess world, Block portal) {
        BlockState blockState = portal.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
        BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
                .forEach(pos -> world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE));
    }

    @Override
    public void createRunePortal(WorldAccess world, Block portal, BlockPos pos1) {
        BlockState blockState = portal.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
        BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
                .forEach(pos -> {
                    world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
                    BlockEntity blockEntity = world.getBlockEntity(pos);
                    if (blockEntity instanceof RunePortalBlockEntity runePortalBlock){
                        runePortalBlock.setDestinationPosPos(pos1);
                    }
                });
    }

    @Overwrite
    private static boolean validStateInsidePortal(BlockState state) {
        return state.isAir() || state.isIn(BlockTags.FIRE) || state.isIn(AtTags.PORTAL);
    }
}
