package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.GeneralPortalExtension;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.generator.RunePortalCoordinateGenerator;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.world.gen.dimension.AtDimensionTypeRegistrar;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;

@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin {
    /**
     * @author  AcuteTerror233
     * @reason  添加了创建多种传送门的判断逻辑
     */
    @Overwrite
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            Optional<NetherPortal> optional = NetherPortal.getNewPortal(world, pos, Direction.Axis.X);
            if (optional.isPresent()) {
                GeneralPortalExtension extension = (GeneralPortalExtension) optional.get();
                RegistryKey<World> worldRegistryKey = world.getRegistryKey();
                boolean adamantiumRunePortalValid = extension.isRunePortalValid(world, AtTags.ADAMANTIUM_RUNESTORE);
                boolean mithrilPortalValid = extension.isRunePortalValid(world, AtTags.MITHRIL_RUNESTORE);
                if (adamantiumRunePortalValid || mithrilPortalValid && worldRegistryKey != World.END) {
                    int maxDistance = 6000;
                    int minDistance = 4000;
                    if (adamantiumRunePortalValid){
                        maxDistance *= 8;
                        minDistance *= 8;
                    }
                    List<BlockState> list = extension.getCornerStateList(world);
                    BlockPos PurposePos = RunePortalCoordinateGenerator.getRunePortalCoordinate(list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString(), pos, minDistance, maxDistance);
                    extension.createRunePortal(world, getSafeLocation((ServerWorld) world, PurposePos));
                    return;
                } else if (worldRegistryKey == World.OVERWORLD && extension.CheckLowerCorner(world, Blocks.BEDROCK)) {
                    extension.createPortal(world, AtBlocks.UNDERGROUND_PORTAL);
                    return;
                } else if (worldRegistryKey == AtDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY && extension.CheckLowerCorner(world,AtBlocks.MANTLE)) {
                    optional.get().createPortal(world);
                    return;
                }
                extension.createPortal(world, AtBlocks.HOME_PORTAL);
                return;
            }
            if (!state.canPlaceAt(world, pos)) {
                world.removeBlock(pos, false);
            }
        }
    }
    @Unique
    private static BlockPos getSafeLocation(ServerWorld world, BlockPos pos) {
        Direction direction = Direction.EAST;
        double bestSquaredDistance = -1.0;
        double bestSquaredDistanceFallback = -1.0;
        BlockPos bestPos = null;
        BlockPos bestPosFallback = null;
        WorldBorder worldBorder = world.getWorldBorder();
        int maxY = Math.min(world.getTopYInclusive(), world.getBottomY() + world.getLogicalHeight() - 1);
        BlockPos.Mutable mutable = pos.mutableCopy();
        int directionOffsetX = direction.getOffsetX();
        int directionOffsetZ = direction.getOffsetZ();
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
        if (bestSquaredDistance == -1.0) {
            int minY = Math.max(world.getBottomY() - 1, 70);
            int clampedMaxY = maxY - 9;
            if (clampedMaxY < minY) {
                return pos.up();
            }
            bestPos = new BlockPos(pos.getX() - directionOffsetX, MathHelper.clamp(pos.getY(), minY, clampedMaxY), pos.getZ() - directionOffsetZ).toImmutable();
            bestPos = worldBorder.clampFloored(bestPos);
            Direction direction2 = direction.rotateYClockwise();
            // 构建传送门底部
            for (int lx = -1; lx < 2; lx++) {
                for (int width = 0; width < 2; width++) {
                    for (int height = -1; height < 3; height++) {
                        BlockState blockState = height < 0 ? Blocks.DEEPSLATE.getDefaultState() : Blocks.AIR.getDefaultState();
                        mutable.set(bestPos, width * directionOffsetX + lx * direction2.getOffsetX(), height, width * directionOffsetZ + lx * direction2.getOffsetZ());
                        world.setBlockState(mutable, blockState);
                    }
                }
            }
        }
        return bestPos;
    }
    @Unique
    private static boolean isBlockStateValid(ServerWorld world, BlockPos.Mutable pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isReplaceable() && blockState.getFluidState().isEmpty();
    }
    @Unique
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
