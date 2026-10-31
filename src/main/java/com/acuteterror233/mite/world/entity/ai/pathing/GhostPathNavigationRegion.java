package com.acuteterror233.mite.world.entity.ai.pathing;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/**
 * A mirror view of a {@link PathNavigationRegion} in which every block the zombie could dig
 * right now is reported as {@link Blocks#AIR}.
 *
 * <p>{@code PathFinder.findPath} consumes its region in exactly one place — the {@code
 * nodeEvaluator.prepare(region, mob)} call — so passing this view straight into a search is
 * enough to make the whole A* search see the "dig-through" world. No evaluator subclass or
 * override is involved (see docs/MME-僵尸挖掘寻路改造方案.md §5.3).</p>
 *
 * <p>One caveat: {@code PathfindingContext} resolves path types through {@code
 * ServerLevel.getPathTypeCache()}, a coordinate-keyed cache shared by the whole level, so a
 * second search in the same tick would otherwise reuse the first pass's real-world types and
 * never consult this view. {@link DigPathFinder} therefore wraps the ghost pass in {@link
 * GhostPathPass}, which the {@code PathfindingContextMixin} uses to bypass that cache.</p>
 *
 * <p>Ghosting rules, in order:</p>
 * <ol>
 *   <li>Never ghost air: air is emitted as {@code OPEN} anyway, and treating it as diggable
 *       would put "dig air" steps into the plan.</li>
 *   <li>Only ghost at or above {@code startY}, i.e. never lie about the floor the zombie
 *       stands on. Ghosting below the feet would let A* plan a hole straight down.</li>
 *   <li>At exactly {@code startY}, require the block above to be solid. A one-high step is
 *       walkable in vanilla, so only walls of two blocks or more enter the tunnel.</li>
 *   <li>Otherwise defer to {@link DigToolPolicy#canDig} (griefing rule, forbidden tag, block
 *       entities, hardness cap, correct-tool rule).</li>
 * </ol>
 *
 * <p>The parent is constructed with a degenerate bounding box, so every inherited accessor
 * that would touch its 1×1 chunk array is delegated to the real region.</p>
 */
final class GhostPathNavigationRegion extends PathNavigationRegion {
    private final PathNavigationRegion real;
    private final ServerLevel digLevel;
    private final Mob mob;
    private final int startY;

    GhostPathNavigationRegion(PathNavigationRegion real, ServerLevel level, Mob mob, int startY) {
        // Parent state is never used: the bounding box is degenerate and every accessor below
        // is delegated to the real region.
        super(level, BlockPos.ZERO, BlockPos.ZERO);
        this.real = real;
        this.digLevel = level;
        this.mob = mob;
        this.startY = startY;
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        // Bounds guard before touching the real region: pass 2 nodes may sit outside the real
        // region's edges, and its own getBlockState would then load chunks at the origin.
        if (this.real.isOutsideBuildHeight(pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        BlockState state = this.real.getBlockState(pos);
        return isGhostable(pos, state) ? Blocks.AIR.defaultBlockState() : state;
    }

    /** Pure predicate, no bookkeeping: the search touches far more blocks than the path holds. */
    private boolean isGhostable(BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return false;
        }
        if (pos.getY() < this.startY) {
            return false;
        }
        if (pos.getY() == this.startY) {
            BlockPos above = pos.above();
            if (this.real.getBlockState(above).getCollisionShape(this.real, above).isEmpty()) {
                return false;
            }
        }
        return DigToolPolicy.canDig(this.digLevel, pos, state, this.mob.getMainHandItem());
    }

    /** Same predicate as {@link #getBlockState}, for the planner to re-derive the dig set. */
    boolean isGhosted(int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if (this.real.isOutsideBuildHeight(pos)) {
            return false;
        }
        return isGhostable(pos, this.real.getBlockState(pos));
    }

    /** Collision test against the real region, bypassing ghosting; outside height = transparent. */
    boolean isReallySolid(int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if (this.real.isOutsideBuildHeight(pos)) {
            return false;
        }
        return !this.real.getBlockState(pos).getCollisionShape(this.real, pos).isEmpty();
    }

    @Override
    public WorldBorder getWorldBorder() {
        return this.real.getWorldBorder();
    }

    @Override
    public BlockGetter getChunkForCollisions(int chunkX, int chunkZ) {
        return this.real.getChunkForCollisions(chunkX, chunkZ);
    }

    @Override
    public List<VoxelShape> getEntityCollisions(Entity entity, AABB box) {
        return this.real.getEntityCollisions(entity, box);
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        return this.real.getBlockEntity(pos);
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return this.real.getFluidState(pos);
    }

    @Override
    public int getMinY() {
        return this.real.getMinY();
    }

    @Override
    public int getHeight() {
        return this.real.getHeight();
    }
}
