package com.acuteterror233.mite.mixin.world.dimension;

import com.acuteterror233.mite.atinterface.GeneralPortalExtension;
import com.acuteterror233.mite.block.RunePortalBlockEntity;
import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(NetherPortal.class)
/*
  Mixin: 扩展下界门框架检测与创建逻辑，
  - 提供角点检测与角列表获取
  - 支持生成普通/符文传送门并在符文门方块实体中写入目标坐标
  - 放宽传送门内部有效状态，允许自定义标签方块
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
    /*
      检查门框底部两角是否为指定方块。
     */
    public boolean CheckLowerCorner(BlockView world, Block block) {
        BlockPos bottomRight = this.lowerCorner.down().offset(this.negativeDir, this.width);
        BlockPos bottomLeft = this.lowerCorner.down().offset(this.negativeDir.getOpposite(), 1);
        return world.getBlockState(bottomLeft).isOf(block) || world.getBlockState(bottomRight).isOf(block);
    }
    @Override
    /*
      返回该门框四个角的方块状态列表。
     */
    public List<BlockState> getCornerStateList(BlockView world) {
        List<BlockState> list = new ArrayList<>();
        for (BlockPos blockPos : getCornerPosList()) {
            list.add(world.getBlockState(blockPos));
        }
        return list;
    }

    @Override
    /*
      返回该门框四个角方块位置列表。
     */
    public List<BlockPos> getCornerPosList() {
        // 定义底部两个角点
        BlockPos bottomLeft = this.lowerCorner.down().offset(this.negativeDir, this.width);
        BlockPos bottomRight = this.lowerCorner.down().offset(this.negativeDir.getOpposite(), 1);

        // 根据底角推导上层对应角点
        BlockPos topLeft = bottomLeft.up(this.height + 1);
        BlockPos topRight = bottomRight.up(this.height + 1);

        // 返回所有四个不同的角点（去重）
        return List.of(bottomLeft, bottomRight, topLeft, topRight);
    }


    @Override
    /*
      检查门框内部是否为指定标签方块。
     */
    public boolean isRunePortalValid(BlockView world, TagKey<Block> tag) {
        for (BlockPos pos : getCornerPosList()) {
            if (!world.getBlockState(pos).isIn(tag)) {
                return false;
            }
        }
        return true;
    }

    @Override
    /*
      用指定传送门方块填充门框内部。
     */
    public void createPortal(WorldAccess world, Block portal) {
        BlockState state = portal.getDefaultState().with(Properties.HORIZONTAL_AXIS, this.axis);
        BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
                .forEach(pos -> world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.FORCE_STATE));
    }

    @Override
    /*
      创建符文传送门并为每个方块实体写入目标坐标。
     */
    public void createRunePortal(WorldAccess world, Block portal, BlockPos targetLocation) {
        BlockState state = portal.getDefaultState().with(Properties.HORIZONTAL_AXIS, this.axis);
        BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1))
                .forEach(pos -> {
                    world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
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
    private static boolean validStateInsidePortal(BlockState state) {
        return state.isAir() || state.isIn(BlockTags.FIRE) || state.isIn(AtTags.PORTAL);
    }
}
