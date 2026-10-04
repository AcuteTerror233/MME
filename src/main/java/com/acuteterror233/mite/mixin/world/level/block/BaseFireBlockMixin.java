package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.generator.RunePortalCoordinateGenerator;
import com.acuteterror233.mite.interfaces.UniversalPortalShapeExtension;
import com.acuteterror233.mite.registry.tag.MMEBlockTags;
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

/**
 * Mixin for {@code BaseFireBlock} — replaces fire placement with the MME portal-routing system.
 *
 * <p>{@code onPlace} is fully overwritten. When fire ignites, the mixin looks for an empty portal
 * frame around the ignition point and picks the portal type from frame material and dimension:
 * frames of {@code ADAMANTIUM_RUNESTORE} / {@code MITHRIL_RUNESTORE} build a
 * {@linkplain RunePortalCoordinateGenerator rune portal} whose destination is generated at a
 * distance of 6000–8000 blocks (×4 for adamantium) in a valid target dimension; in the Overworld a
 * frame whose bottom corner is bedrock builds an {@code UNDERGROUND_PORTAL}; in the MME underground
 * dimension a mantle-bottomed frame builds a vanilla-style nether portal back to the surface; any
 * other valid frame builds a {@code HOME_PORTAL}. If no frame matches, the vanilla
 * {@code canSurvive} check (portal-free fire needs a valid base) still applies. All of this runs
 * server-side ({@code onPlace} fires during server block updates; fire is not placeable client-side).
 * The remaining helpers adapt vanilla's portal-destination search to locate a safe frame position.
 * Portal shape capability checks go through the {@link UniversalPortalShapeExtension} duck
 * interface.</p>
 */
@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
    /**
     * @author AcuteTerror233
     * @reason  Added judgment logic for creating multiple types of portals
     *
     * <p>Overwrites vanilla {@code onPlace}: on ignition, detects the surrounding portal frame and
     * creates the matching MME portal type; otherwise applies the vanilla fire-survivability check.</p>
     *
     * @param state    the fire state that was just placed
     * @param world    the level the fire ignited in
     * @param pos      the fire position
     * @param oldState the state previously at {@code pos}
     * @param notify   whether the vanilla place should notify neighbors
     */
    @Overwrite
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.is(state.getBlock())) {
            // Look for an empty portal frame (X axis candidates) around the ignition point.
            Optional<PortalShape> optional = PortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
            if (optional.isPresent()) {
                UniversalPortalShapeExtension extension = (UniversalPortalShapeExtension) optional.get();
                ResourceKey<Level> worldRegistryKey = world.dimension();
                boolean adamantiumRunePortalValid = extension.MME$VerifyPortalValid(world, MMEBlockTags.ADAMANTIUM_RUNESTORE);
                boolean mithrilPortalValid = extension.MME$VerifyPortalValid(world, MMEBlockTags.MITHRIL_RUNESTORE);
                if (adamantiumRunePortalValid || mithrilPortalValid && worldRegistryKey != Level.END) {
                    int maxDistance = 8000;
                    int minDistance = 6000;
                    if (adamantiumRunePortalValid){
                        maxDistance *= 4;
                        minDistance *= 4;
                    }
                    List<BlockState> list = extension.MME$GetBottomStateList(world);
                    BlockPos PurposePos = RunePortalCoordinateGenerator.getRunePortalCoordinate(list, world, pos, minDistance, maxDistance);
                    extension.MME$CreateRunePortal(world, getSafeLocation((ServerLevel) world, PurposePos));
                    return;
                } else if (worldRegistryKey == Level.OVERWORLD && extension.MME$CheckBottomCorner(world, Blocks.BEDROCK)) {
                    extension.MME$CreatePortal(world, MMEBlocks.UNDERGROUND_PORTAL);
                    return;
                } else if (worldRegistryKey == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY && extension.MME$CheckBottomCorner(world, MMEBlocks.MANTLE)) {
                    optional.get().createPortalBlocks(world);
                    return;
                }
                extension.MME$CreatePortal(world, MMEBlocks.HOME_PORTAL);
                return;
            }
            if (!state.canSurvive(world, pos)) {
                world.removeBlock(pos, false);
            }
        }
    }
    /**
     * Finds a safe 2-wide × 3-high portal placement spot near {@code pos}, adapted from vanilla's
     * portal destination search. Scans a 16-block spiral, probing downward from the surface at each
     * column for a column of replaceable air with solid ground and enough vertical clearance.
     *
     * <p>Preference order: the closest position with both orthogonally adjacent portal footprints
     * valid, else the closest position with only the center footprint valid, else a carved platform
     * (deepslate floor, air chamber) built one step back from {@code pos}.</p>
     *
     * @param world the server level the destination lives in
     * @param pos   the approximate target coordinate for the portal
     * @return the best found anchor position (portal base), or {@code pos.above()} if nothing fits
     */
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
        // If no best position found, use fallback position
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
            // Build portal base
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
    /** {@return true} when the state is replaceable (air, grass...) and contains no fluid. */
    @Unique
    private static boolean isBlockStateValid(ServerLevel world, BlockPos.MutableBlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.canBeReplaced() && blockState.getFluidState().isEmpty();
    }
    /**
     * Checks that a full portal footprint (4 high × 4 wide, including the frame shell) fits at
     * {@code pos}: rows below the base must be solid, rows at/above the base must be replaceable.
     *
     * @param world                    the server level being probed
     * @param pos                      the portal base corner being tested
     * @param temp                     scratch mutable position reused during the scan
     * @param portalDirection          the portal's horizontal facing (X or Z axis step)
     * @param distanceOrthogonalToPortal lateral offset (in blocks) from the portal plane
     * @return whether the entire footprint is a valid portal space
     */
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
