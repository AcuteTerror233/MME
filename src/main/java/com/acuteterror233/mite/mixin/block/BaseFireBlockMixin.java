package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.UniversalPortalShapeExtension;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.generator.RunePortalCoordinateGenerator;
import com.acuteterror233.mite.registry.tag.MMETags;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
    /**
     * @author  AcuteTerror233
     * @reason  添加了创建多种传送门的判断逻辑
     */
    @Overwrite
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.is(state.getBlock())) {
            Optional<PortalShape> optional = PortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
            if (optional.isPresent()) {
                UniversalPortalShapeExtension extension = (UniversalPortalShapeExtension) optional.get();
                ResourceKey<Level> worldRegistryKey = world.dimension();
                boolean adamantiumRunePortalValid = extension.MME$VerifyPortalValid(world, MMETags.ADAMANTIUM_RUNESTORE);
                boolean mithrilPortalValid = extension.MME$VerifyPortalValid(world, MMETags.MITHRIL_RUNESTORE);
                if (adamantiumRunePortalValid || mithrilPortalValid && worldRegistryKey != Level.END) {
                    int maxDistance = 6000;
                    int minDistance = 4000;
                    if (adamantiumRunePortalValid){
                        maxDistance *= 8;
                        minDistance *= 8;
                    }
                    List<BlockState> list = extension.MME$GetBottomStateList(world);
                    BlockPos PurposePos = RunePortalCoordinateGenerator.getRunePortalCoordinate(list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString(), pos, minDistance, maxDistance);
                    extension.MME$CreateRunePortal(world, getSafeLocation((ServerLevel) world, PurposePos));
                    return;
                } else if (worldRegistryKey == Level.OVERWORLD && extension.MME$CheckBottomCorner(world, Blocks.BEDROCK)) {
                    extension.MME$CreatePortal(world, AtBlocks.UNDERGROUND_PORTAL);
                    return;
                } else if (worldRegistryKey == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY && extension.MME$CheckBottomCorner(world,AtBlocks.MANTLE)) {
                    optional.get().createPortalBlocks(world);
                    return;
                }
                extension.MME$CreatePortal(world, AtBlocks.HOME_PORTAL);
                return;
            }
            if (!state.canSurvive(world, pos)) {
                world.removeBlock(pos, false);
            }
        }
    }
    @Unique
    private static BlockPos getSafeLocation(ServerLevel world, BlockPos pos) {
        Direction direction = Direction.EAST;
        double bestSquaredDistance = -1.0;
        double bestSquaredDistanceFallback = -1.0;
        BlockPos bestPos = null;
        BlockPos bestPosFallback = null;
        WorldBorder worldBorder = world.getWorldBorder();
        int maxY = Math.min(world.getMaxY(), world.getMinY() + world.getLogicalHeight() - 1);
        BlockPos.MutableBlockPos mutable = pos.mutable();
        int directionOffsetX = direction.getStepX();
        int directionOffsetZ = direction.getStepZ();
        for (BlockPos.MutableBlockPos mutable2 : BlockPos.spiralAround(pos, 16, Direction.EAST, Direction.SOUTH)) {
            int surfaceY = Math.min(maxY, world.getHeight(Heightmap.Types.MOTION_BLOCKING, mutable2.getX(), mutable2.getZ()));
            if (worldBorder.isWithinBounds(mutable2) && worldBorder.isWithinBounds(mutable2.move(direction, 1))) {
                mutable2.move(direction.getOpposite(), 1);
                for (int y = surfaceY; y >= world.getMinY(); y--) {
                    mutable2.setY(y);
                    if (isBlockStateValid(world, mutable2)) {
                        int topY = y;
                        while (y > world.getMinY() && isBlockStateValid(world, mutable2.move(Direction.DOWN))) {
                            y--;
                        }
                        if (y + 4 <= maxY) {
                            int height = topY - y;
                            // 检查高度是否无效（<=0 或 >=3）
                            if (height <= 0 || height >= 3) {
                                mutable2.setY(y);
                                if (isValidPortalPos(world, mutable2, mutable, direction, 0)) {
                                    double squaredDistance = pos.distSqr(mutable2);
                                    if (isValidPortalPos(world, mutable2, mutable, direction, -1) && isValidPortalPos(world, mutable2, mutable, direction, 1) && (bestSquaredDistance == -1.0 || bestSquaredDistance > squaredDistance)) {
                                        bestSquaredDistance = squaredDistance;
                                        bestPos = mutable2.immutable();
                                    }
                                    if (bestSquaredDistance == -1.0 && (bestSquaredDistanceFallback == -1.0 || bestSquaredDistanceFallback > squaredDistance)) {
                                        bestSquaredDistanceFallback = squaredDistance;
                                        bestPosFallback = mutable2.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // 如果没有找到最佳位置，则使用备选位置
        if (bestSquaredDistance == -1.0 && bestSquaredDistanceFallback != -1.0) {
            bestPos = bestPosFallback;
            bestSquaredDistance = bestSquaredDistanceFallback;
        }
        if (bestSquaredDistance == -1.0) {
            int minY = Math.max(world.getMinY() - 1, 70);
            int clampedMaxY = maxY - 9;
            if (clampedMaxY < minY) {
                return pos.above();
            }
            bestPos = new BlockPos(pos.getX() - directionOffsetX, Mth.clamp(pos.getY(), minY, clampedMaxY), pos.getZ() - directionOffsetZ).immutable();
            bestPos = worldBorder.clampToBounds(bestPos);
            Direction direction2 = direction.getClockWise();
            // 构建传送门底部
            for (int lx = -1; lx < 2; lx++) {
                for (int width = 0; width < 2; width++) {
                    for (int height = -1; height < 3; height++) {
                        BlockState blockState = height < 0 ? Blocks.DEEPSLATE.defaultBlockState() : Blocks.AIR.defaultBlockState();
                        mutable.setWithOffset(bestPos, width * directionOffsetX + lx * direction2.getStepX(), height, width * directionOffsetZ + lx * direction2.getStepZ());
                        world.setBlockAndUpdate(mutable, blockState);
                    }
                }
            }
        }
        return bestPos;
    }
    @Unique
    private static boolean isBlockStateValid(ServerLevel world, BlockPos.MutableBlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.canBeReplaced() && blockState.getFluidState().isEmpty();
    }
    @Unique
    private static boolean isValidPortalPos(ServerLevel world, BlockPos pos, BlockPos.MutableBlockPos temp, Direction portalDirection, int distanceOrthogonalToPortal) {
        Direction direction = portalDirection.getClockWise();

        for (int i = -1; i < 3; i++) {
            for (int j = -1; j < 4; j++) {
                temp.setWithOffset(
                        pos,
                        portalDirection.getStepX() * i + direction.getStepX() * distanceOrthogonalToPortal,
                        j,
                        portalDirection.getStepZ() * i + direction.getStepZ() * distanceOrthogonalToPortal
                );
                if (j < 0 && !world.getBlockState(temp).isSolid()) {
                    return false;
                }

                if (j >= 0 && !isBlockStateValid(world, temp)) {
                    return false;
                }
            }
        }

        return true;
    }
}
