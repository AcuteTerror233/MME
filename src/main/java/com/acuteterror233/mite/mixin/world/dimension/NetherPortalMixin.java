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
/**
 * Mixin: 扩展下界门框架检测与创建逻辑，
 * - 提供角点检测与角列表获取
 * - 支持生成普通/符文传送门并在符文门方块实体中写入目标坐标
 * - 放宽传送门内部有效状态，允许自定义标签方块
 */
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
    /**
     * 检查门框底部两角是否为指定方块。
     */
    public boolean CheckLowerCorner(BlockView world, Block block) {
        BlockPos bottomRight = this.lowerCorner.down().offset(this.negativeDir, this.width);
        BlockPos bottomLeft = this.lowerCorner.down().offset(this.negativeDir.getOpposite(), 1);
        return world.getBlockState(bottomLeft).isOf(block) || world.getBlockState(bottomRight).isOf(block);
    }
    @Override
    /**
     * 返回该门框四个角的位置列表（含顶部两角）。
     */
    public List<BlockPos> getCornerList() {
        BlockPos pos = this.lowerCorner.down().offset(this.negativeDir, this.width);
        BlockPos pos1 = this.lowerCorner.down().offset(this.negativeDir.getOpposite(), 1);
        return List.of(pos, pos1, pos.up(this.height + 1), pos1.up(this.height + 1));
    }

    @Override
    /**
     * 用指定传送门方块填充门框内部。
     */
    public void createPortal(WorldAccess world, Block portal) {
        BlockState blockState = portal.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
        BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
                .forEach(pos -> world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE));
    }

    @Override
    /**
     * 创建符文传送门并为每个方块实体写入目标坐标。
     */
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
    /**
     * 放宽内部有效状态：允许空气、火，以及被 {@code at_mite:portal} 标签标记的方块。
     */
    private static boolean validStateInsidePortal(BlockState state) {
        return state.isAir() || state.isIn(BlockTags.FIRE) || state.isIn(AtTags.PORTAL);
    }
}
