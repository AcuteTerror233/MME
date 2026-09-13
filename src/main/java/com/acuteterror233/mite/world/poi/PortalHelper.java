package com.acuteterror233.mite.world.poi;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Comparator;
import java.util.Optional;

/**
 * Portal helper utility.
 * Handles portal generation logic, finding/creating corresponding nether portal blocks in the target dimension.
 */
public class PortalHelper {
    public static Optional<BlockPos> getPortalPos(ServerLevel world, BlockPos pos, int PreloadSize, WorldBorder worldBorder, ResourceKey<PoiType> matchesKey, Block block) {
        PoiManager pointOfInterestStorage = world.getPoiManager();
        pointOfInterestStorage.ensureLoadedAndValid(world, pos, PreloadSize);
        return pointOfInterestStorage.getInSquare(
                        poiType -> poiType.is(matchesKey), pos, PreloadSize, PoiManager.Occupancy.ANY
                )
                .map(PoiRecord::getPos)
                .filter(worldBorder::isWithinBounds)
                .filter(portalPos -> world.getBlockState(portalPos).hasProperty(BlockStateProperties.HORIZONTAL_AXIS) || world.getBlockState(portalPos).is(block))
                .min(Comparator.comparingDouble(pos::distSqr));
    }

    /**
     * Creates a portal structure in the specified world
     *
     * @param world Server world object for accessing and modifying block states
     * @param pos   Center position coordinates for portal creation
     * @param axis  Portal orientation axis (X or Z)
     * @return Optional containing portal rectangle area information, or empty Optional if creation fails
     */
    public static Optional<BlockUtil.FoundRectangle> createPortal(ServerLevel world, BlockPos pos, Direction.Axis axis, Block framework, Block portal) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double bestSquaredDistance = -1.0;
        BlockPos bestPos = null;
        double bestSquaredDistanceFallback = -1.0;
        BlockPos bestPosFallback = null;
        WorldBorder worldBorder = world.getWorldBorder();
        int maxY = Math.min(world.getMaxY(), world.getMinY() + world.getLogicalHeight() - 1);
        BlockPos.MutableBlockPos mutable = pos.mutable();

        // Cache direction offsets since they don't change
        int directionOffsetX = direction.getStepX();
        int directionOffsetZ = direction.getStepZ();

        // Search for suitable portal positions within the specified area
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
                            // Check if height is invalid (<=0 or >=3)
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

        // If best position not found, use fallback position
        if (bestSquaredDistance == -1.0 && bestSquaredDistanceFallback != -1.0) {
            bestPos = bestPosFallback;
            bestSquaredDistance = bestSquaredDistanceFallback;
        }

        // If still no suitable position found, create a new portal structure
        if (bestSquaredDistance == -1.0) {
            // Fix expression: originally was world.getBottomY() - -1
            int minY = Math.max(world.getMinY() - 1, 70);
            int clampedMaxY = maxY - 9;
            if (clampedMaxY < minY) {
                return Optional.empty();
            }

            bestPos = new BlockPos(pos.getX() - directionOffsetX, Mth.clamp(pos.getY(), minY, clampedMaxY), pos.getZ() - directionOffsetZ).immutable();
            bestPos = worldBorder.clampToBounds(bestPos);
            Direction direction2 = direction.getClockWise();

            // Build portal bottom
            for (int lx = -1; lx < 2; lx++) {
                for (int width = 0; width < 2; width++) {
                    for (int height = -1; height < 3; height++) {
                        BlockState blockState = height < 0 ? framework.defaultBlockState() : Blocks.AIR.defaultBlockState();
                        mutable.setWithOffset(bestPos, width * directionOffsetX + lx * direction2.getStepX(), height, width * directionOffsetZ + lx * direction2.getStepZ());
                        world.setBlockAndUpdate(mutable, blockState);
                    }
                }
            }
        }

        // Build portal frame
        for (int frameWidth = -1; frameWidth < 3; frameWidth++) {
            for (int frameHeight = -1; frameHeight < 4; frameHeight++) {
                if (frameWidth == -1 || frameWidth == 2 || frameHeight == -1 || frameHeight == 3) {
                    mutable.setWithOffset(bestPos, frameWidth * directionOffsetX, frameHeight, frameWidth * directionOffsetZ);
                    world.setBlock(mutable, framework.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }

        // Place portal blocks
        BlockState portalBlockState = portal.defaultBlockState().setValue(NetherPortalBlock.AXIS, axis);

        for (int portalWidth = 0; portalWidth < 2; portalWidth++) {
            for (int portalHeight = 0; portalHeight < 3; portalHeight++) {
                mutable.setWithOffset(bestPos, portalWidth * directionOffsetX, portalHeight, portalWidth * directionOffsetZ);
                world.setBlock(mutable, portalBlockState, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(bestPos.immutable(), 2, 3));
    }

    private static boolean isBlockStateValid(ServerLevel world, BlockPos.MutableBlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.canBeReplaced() && blockState.getFluidState().isEmpty();
    }

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
