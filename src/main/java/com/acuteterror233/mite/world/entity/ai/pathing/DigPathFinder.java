package com.acuteterror233.mite.world.entity.ai.pathing;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Two-pass path finder: vanilla first, then a "dig-through" mirror world only when vanilla
 * cannot reach the target.
 *
 * <p>Pass 1 runs on the real region, so a path with no obstruction is byte-for-byte the
 * vanilla result and the dig machinery costs nothing. Pass 2 runs on a {@link
 * GhostPathNavigationRegion}, where blocks the zombie can dig are reported as air; the cost
 * of digging them is folded into {@link #distance(Node, Node)}, which is the per-edge weight
 * of the A* {@code g} accumulation ({@code g(child) = g(parent) + distance(parent, child) +
 * child.costMalus}). Folding dig time into the same number as walking distance is what lets
 * A* compare "dig through" against "walk around" in a single search instead of post-scoring
 * paths.</p>
 *
 * <p>The evaluator itself is stock {@code WalkNodeEvaluator}: the region is passed straight
 * to {@link PathFinder#findPath}, and the parent consumes a region only through {@code
 * nodeEvaluator.prepare}. Ghosting therefore needs no evaluator subclass, and the vanilla
 * per-pass caches stay untouched (see docs/MME-僵尸挖掘寻路改造方案.md §5.3, §5.4).</p>
 */
public final class DigPathFinder extends PathFinder {
    /**
     * Extra visited-node room for pass 2. Pass 2 searches a walk range inflated by
     * {@link DigSession#digBudgetCostUnits()} (about 100 cells), and the visited-node cap does not
     * grow with it; without headroom A* runs out of nodes and returns a truncated path that still
     * looks usable.
     */
    private static final float PASS_TWO_VISITED_NODES_MULTIPLIER = 2.0F;

    private final DigSession session;
    @Nullable
    private Mob currentMob;
    /** Only non-null while pass 2 is running, so pass 1 edges never pay a dig cost. */
    @Nullable
    private GhostPathNavigationRegion digPass;
    /** Region of the last dig path handed out, needed to expand it into a plan later. */
    @Nullable
    private GhostPathNavigationRegion lastGhost;
    /** The exact object returned by the last successful pass 2; guards against stale plans. */
    @Nullable
    private Path lastDigPath;

    public DigPathFinder(NodeEvaluator evaluator, int maxVisitedNodes, DigSession session) {
        super(evaluator, maxVisitedNodes);
        this.session = session;
    }

    @Override
    public Path findPath(PathNavigationRegion region, Mob mob, Set<BlockPos> targets,
                         float maxRange, int accuracy, float maxVisitedNodesMultiplier) {
        this.currentMob = mob;
        this.digPass = null;
        this.lastGhost = null;
        this.lastDigPath = null;
        // The dig budget belongs to one search, not to the mob's lifetime
        this.session.beginSearch();

        Path vanilla = super.findPath(region, mob, targets, maxRange, accuracy,
                maxVisitedNodesMultiplier);
        if (vanilla != null && vanilla.canReach()) {
            return vanilla;
        }
        if (!this.session.canDig() || !(mob.level() instanceof ServerLevel serverLevel)) {
            return vanilla;
        }

        GhostPathNavigationRegion ghost = new GhostPathNavigationRegion(
                region, serverLevel, mob, mob.blockPosition().getY());
        this.digPass = ghost;
        // Pass 1 filled ServerLevel's coordinate-keyed PathTypeCache with real-world types;
        // without this switch the ghost view would never be consulted for those positions.
        GhostPathPass.begin();
        try {
            // distance() folds dig costs into walkedDistance, and vanilla A* drops neighbors
            // once walkedDistance reaches maxRange — the mob's follow range. Without headroom
            // any plan whose dig cost plus walking exceeds that range would be silently
            // truncated (a three-dirt pillar alone already costs 37.5 cells > 35). The
            // session budget bounds what a single plan may cost, so the budget converted to
            // flat cells is exactly the headroom the dig pass needs.
            Path dig = super.findPath(ghost, mob, targets,
                    maxRange + this.session.digBudgetCostUnits(), accuracy,
                    maxVisitedNodesMultiplier * PASS_TWO_VISITED_NODES_MULTIPLIER);
            // A single-node path means A* never got past the start (typically the node budget ran
            // out); expanding it would produce an empty plan and mask the failure
            if (dig == null || dig.getNodeCount() <= 1) {
                return vanilla;
            }
            this.lastGhost = ghost;
            this.lastDigPath = dig;
            return dig;
        } finally {
            GhostPathPass.end();
            this.digPass = null;
        }
    }

    /** Edge weight = walking distance + dig cost of the destination node's column (body + cap). */
    @Override
    protected float distance(Node from, Node to) {
        float base = super.distance(from, to);
        GhostPathNavigationRegion ghost = this.digPass;
        Mob mob = this.currentMob;
        if (ghost == null || mob == null) {
            return base;
        }
        return base + digCostUnits(ghost, mob, to);
    }

    /**
     * Sum of the dig costs of every ghosted block in the node's column, body plus cap — the
     * same list {@link DigPlanPlanner} will later turn into the step, so what A* pays for is
     * exactly what the executor removes.
     */
    private float digCostUnits(GhostPathNavigationRegion ghost, Mob mob, Node node) {
        float units = 0.0F;
        for (BlockPos pos : DigPlanPlanner.ghostedColumn(node, ghost, mob)) {
            units += this.session.digCostUnits(pos.getX(), pos.getY(), pos.getZ());
        }
        return units;
    }

    /**
     * Expands the path into a dig plan, or returns {@code null} when it did not come from a
     * successful pass 2. Identity against the stored path keeps stale pass-2 regions from
     * being applied to a later plain walk.
     */
    @Nullable
    public DigPlan planFor(@Nullable Path path) {
        GhostPathNavigationRegion ghost = this.lastGhost;
        Mob mob = this.currentMob;
        if (path == null || ghost == null || mob == null || path != this.lastDigPath) {
            return null;
        }
        return DigPlanPlanner.plan(path, ghost, mob, this.session);
    }
}
