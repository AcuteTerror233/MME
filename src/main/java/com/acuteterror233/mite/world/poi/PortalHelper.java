package com.acuteterror233.mite.world.poi;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.Heightmap;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Comparator;
import java.util.Optional;

public class PortalHelper {
    public static Optional<BlockPos> getPortalPos(ServerWorld world, BlockPos pos, int PreloadSize, WorldBorder worldBorder, RegistryKey<PointOfInterestType> matchesKey, Block block) {
        PointOfInterestStorage pointOfInterestStorage = world.getPointOfInterestStorage();
        pointOfInterestStorage.preloadChunks(world, pos, PreloadSize);
        return pointOfInterestStorage.getInSquare(
                        poiType -> poiType.matchesKey(matchesKey), pos, PreloadSize, PointOfInterestStorage.OccupationStatus.ANY
                )
                .map(PointOfInterest::getPos)
                .filter(worldBorder::contains)
                .filter(portalPos -> world.getBlockState(portalPos).contains(Properties.HORIZONTAL_AXIS) || world.getBlockState(portalPos).isOf(block))
                .min(Comparator.comparingDouble(pos::getSquaredDistance));
        //.min(Comparator.comparingDouble(portalPos -> portalPos.getSquaredDistance(pos)).thenComparingInt(Vec3i::getY));   报错???????????
        //                                                       -----------------------                   -----------
    }

    /**
     * 在指定世界中创建一个传送门结构
     *
     * @param world 服务器世界对象，用于访问和修改方块状态
     * @param pos   传送门创建的中心位置坐标
     * @param axis  传送门的朝向轴（X或Z）
     * @return 包含传送门矩形区域信息的Optional对象，如果创建失败则返回空Optional
     */
    public static Optional<BlockLocating.Rectangle> createPortal(ServerWorld world, BlockPos pos, Direction.Axis axis, Block framework, Block portal) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double bestSquaredDistance = -1.0;
        BlockPos bestPos = null;
        double bestSquaredDistanceFallback = -1.0;
        BlockPos bestPosFallback = null;
        WorldBorder worldBorder = world.getWorldBorder();
        int maxY = Math.min(world.getTopYInclusive(), world.getBottomY() + world.getLogicalHeight() - 1);
        BlockPos.Mutable mutable = pos.mutableCopy();

        // 缓存方向偏移量，因为它们不会改变
        int directionOffsetX = direction.getOffsetX();
        int directionOffsetZ = direction.getOffsetZ();

        // 在指定区域内搜索合适的传送门位置
        for (BlockPos.Mutable mutable2 : BlockPos.iterateInSquare(pos, 16, Direction.EAST, Direction.SOUTH)) {
            int surfaceY = Math.min(maxY, world.getTopY(Heightmap.Type.MOTION_BLOCKING, mutable2.getX(), mutable2.getZ()));
            if (worldBorder.contains(mutable2) && worldBorder.contains(mutable2.move(direction, 1))) {
                mutable2.move(direction.getOpposite(), 1);

                for (int y = surfaceY; y >= world.getBottomY(); y--) {
                    mutable2.setY(y);
                    if (isBlockStateValid(world, mutable2)) {
                        int topY = y;

                        while (y > world.getBottomY() && isBlockStateValid(world, mutable2.move(Direction.DOWN))) {
                            y--;
                        }

                        if (y + 4 <= maxY) {
                            int height = topY - y;
                            // 检查高度是否无效（<=0 或 >=3）
                            if (height <= 0 || height >= 3) {
                                mutable2.setY(y);
                                if (isValidPortalPos(world, mutable2, mutable, direction, 0)) {
                                    double squaredDistance = pos.getSquaredDistance(mutable2);
                                    if (isValidPortalPos(world, mutable2, mutable, direction, -1) && isValidPortalPos(world, mutable2, mutable, direction, 1) && (bestSquaredDistance == -1.0 || bestSquaredDistance > squaredDistance)) {
                                        bestSquaredDistance = squaredDistance;
                                        bestPos = mutable2.toImmutable();
                                    }

                                    if (bestSquaredDistance == -1.0 && (bestSquaredDistanceFallback == -1.0 || bestSquaredDistanceFallback > squaredDistance)) {
                                        bestSquaredDistanceFallback = squaredDistance;
                                        bestPosFallback = mutable2.toImmutable();
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

        // 如果仍未找到合适位置，则创建一个新的传送门结构
        if (bestSquaredDistance == -1.0) {
            // 修复表达式：原来是 world.getBottomY() - -1
            int minY = Math.max(world.getBottomY() - 1, 70);
            int clampedMaxY = maxY - 9;
            if (clampedMaxY < minY) {
                return Optional.empty();
            }

            bestPos = new BlockPos(pos.getX() - directionOffsetX * 1, MathHelper.clamp(pos.getY(), minY, clampedMaxY), pos.getZ() - directionOffsetZ * 1).toImmutable();
            bestPos = worldBorder.clampFloored(bestPos);
            Direction direction2 = direction.rotateYClockwise();

            // 构建传送门底部
            for (int lx = -1; lx < 2; lx++) {
                for (int width = 0; width < 2; width++) {
                    for (int height = -1; height < 3; height++) {
                        BlockState blockState = height < 0 ? framework.getDefaultState() : Blocks.AIR.getDefaultState();
                        mutable.set(bestPos, width * directionOffsetX + lx * direction2.getOffsetX(), height, width * directionOffsetZ + lx * direction2.getOffsetZ());
                        world.setBlockState(mutable, blockState);
                    }
                }
            }
        }

        // 构建传送门框架
        for (int frameWidth = -1; frameWidth < 3; frameWidth++) {
            for (int frameHeight = -1; frameHeight < 4; frameHeight++) {
                if (frameWidth == -1 || frameWidth == 2 || frameHeight == -1 || frameHeight == 3) {
                    mutable.set(bestPos, frameWidth * directionOffsetX, frameHeight, frameWidth * directionOffsetZ);
                    world.setBlockState(mutable, framework.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
        }

        // 放置传送门方块
        BlockState portalBlockState = portal.getDefaultState().with(NetherPortalBlock.AXIS, axis);

        for (int portalWidth = 0; portalWidth < 2; portalWidth++) {
            for (int portalHeight = 0; portalHeight < 3; portalHeight++) {
                mutable.set(bestPos, portalWidth * directionOffsetX, portalHeight, portalWidth * directionOffsetZ);
                world.setBlockState(mutable, portalBlockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
            }
        }

        return Optional.of(new BlockLocating.Rectangle(bestPos.toImmutable(), 2, 3));
    }

    private static boolean isBlockStateValid(ServerWorld world, BlockPos.Mutable pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isReplaceable() && blockState.getFluidState().isEmpty();
    }

    private static boolean isValidPortalPos(ServerWorld world, BlockPos pos, BlockPos.Mutable temp, Direction portalDirection, int distanceOrthogonalToPortal) {
        Direction direction = portalDirection.rotateYClockwise();

        for (int i = -1; i < 3; i++) {
            for (int j = -1; j < 4; j++) {
                temp.set(
                        pos,
                        portalDirection.getOffsetX() * i + direction.getOffsetX() * distanceOrthogonalToPortal,
                        j,
                        portalDirection.getOffsetZ() * i + direction.getOffsetZ() * distanceOrthogonalToPortal
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
