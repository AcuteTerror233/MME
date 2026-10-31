package com.acuteterror233.mite.world.entity.ai.pathing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Ground navigation whose path finder plans through diggable terrain.
 *
 * <p>Only the path finder is replaced: the vanilla {@code WalkNodeEvaluator} and all other
 * ground-navigation behaviour stay stock. {@link DigPathFinder} runs vanilla first and only
 * switches to the ghost world when vanilla cannot reach the target, so unobstructed paths
 * cost exactly what they cost in vanilla.</p>
 *
 * <p>Construction order matters: {@code GroundPathNavigation}'s constructor calls {@link
 * #createPathFinder} before this class's constructor body runs, so the session is created
 * inside that override (the parent's {@code mob} and {@code level} fields are already
 * assigned at that point) rather than in the constructor.</p>
 *
 * <p>The navigation is also built client-side, where the level is not a {@link ServerLevel};
 * that case falls back to the stock finder because ghosting needs server-side game rules
 * and block data.</p>
 *
 * <p>Digging state lives here as well: the plan is cached per path, the executor reads it
 * through {@link #currentDigStep()} and reports progress with {@link #onDigStepFinished} /
 * {@link #onBlockDug}. Digging suspends navigation because {@code doStuckDetection} cancels
 * a path after 100 ticks without movement — a bare-handed dirt block takes far longer than
 * that. The suspended path is saved and restored rather than recomputed: the plan already
 * treated the removed blocks as air, so it stays valid after every block (see
 * docs/MME-僵尸挖掘寻路改造方案.md §5.6, §5.7).</p>
 */
public class ZombieDigNavigation extends GroundPathNavigation {
    @Nullable
    private DigSession session;
    @Nullable
    private DigPathFinder digFinder;
    /** True while the executor has halted navigation to dig; cleared on resume. */
    private boolean suspended;
    /** Path saved by {@link #suspendForDig()}; restored on resume so digging never replans. */
    @Nullable
    private Path suspendedPath;
    /** Plan expanded from the current path; expansion walks every node, so it is cached. */
    @Nullable
    private DigPlan activePlan;
    /** Index into {@link DigPlan#steps()} of the first step that is not finished yet. */
    private int digStepCursor;

    public ZombieDigNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        // Let the parent initialise nodeEvaluator / canOpenDoors / canWalkOverFences first,
        // then reuse that very evaluator: creating one here instead would leave
        // PathNavigation.nodeEvaluator null and NPE in getNodeEvaluator() and the setters.
        PathFinder vanilla = super.createPathFinder(maxVisitedNodes);
        if (!(this.level instanceof ServerLevel serverLevel)) {
            return vanilla;
        }
        DigSession digSession = new DigSession(serverLevel, this.mob);
        this.session = digSession;
        this.digFinder = new DigPathFinder(this.nodeEvaluator, maxVisitedNodes, digSession);
        return this.digFinder;
    }

    /**
     * Dig plan for the current path, or {@code null} when the path is a plain vanilla walk.
     * {@code PathNavigation.createPath} only hands back a {@link
     * net.minecraft.world.level.pathfinder.Path}, so the plan is expanded on demand.
     *
     * <p>The cached plan survives {@link #suspendForDig()} and any external {@code stop()} — both
     * only null the path — so goal arbitration cannot wipe a plan mid-dig. Whenever the current
     * path is a different object the plan is rebuilt and the step cursor restarts at the first
     * step, which is exactly what a fresh search needs.</p>
     */
    @Nullable
    public DigPlan getPlan() {
        DigPlan cached = this.activePlan;
        Path path = this.getPath();
        // A cleared path does not invalidate the plan: goal arbitration (the melee goal's
        // stop() calls navigation.stop()) and suspendForDig() both null the path without
        // running a new search, and the plan is the only record of what A* decided. Only a
        // different path object means a new search and therefore a fresh plan.
        if (cached != null && (path == null || cached.path() == path)) {
            return cached;
        }
        DigPathFinder finder = this.digFinder;
        DigPlan fresh = finder == null ? null : finder.planFor(path);
        this.activePlan = fresh;
        this.digStepCursor = 0;
        return fresh;
    }

    /**
     * Step the executor should remove right now, or {@code null} when the plan is exhausted.
     * Steps whose blocks are already air are skipped so a stale plan cannot stall the mob.
     */
    @Nullable
    public DigPlan.DigStep currentDigStep() {
        DigPlan plan = this.getPlan();
        if (plan == null) {
            return null;
        }
        List<DigPlan.DigStep> steps = plan.steps();
        while (this.digStepCursor < steps.size()) {
            DigPlan.DigStep step = steps.get(this.digStepCursor);
            if (hasUndugBlock(step)) {
                return step;
            }
            this.digStepCursor++;
        }
        return null;
    }

    /** Advances the plan cursor once every block of {@code step} has been removed. */
    public void onDigStepFinished(DigPlan.DigStep step) {
        DigPlan plan = this.activePlan;
        if (plan == null) {
            return;
        }
        List<DigPlan.DigStep> steps = plan.steps();
        if (this.digStepCursor < steps.size() && steps.get(this.digStepCursor) == step) {
            this.digStepCursor++;
        }
    }

    /** Reports a destroyed block back to the session so the dig budget tracks real progress. */
    public void onBlockDug(int ticks) {
        DigSession digSession = this.session;
        if (digSession != null) {
            digSession.onDug(ticks);
        }
    }

    /**
     * Halts navigation while the executor digs. {@code stop()} alone would drop the path, so
     * it is saved first; restoring it is what keeps digging O(1) per block instead of
     * re-running a search after every block.
     */
    public void suspendForDig() {
        if (this.suspended) {
            return;
        }
        this.suspended = true;
        this.suspendedPath = this.path;
        stop();
    }

    /** @return whether the executor has halted navigation to dig. */
    public boolean isSuspended() {
        return this.suspended;
    }

    /**
     * Drops the active plan and the path it belongs to, leaving the mob to plain navigation. Used
     * when a route turns out to be unexecutable: keeping the plan would make the executor retry the
     * same unreachable block forever.
     */
    public void abandonPlan() {
        this.activePlan = null;
        this.digStepCursor = 0;
        this.suspended = false;
        this.suspendedPath = null;
        stop();
    }

    /**
     * Keeps this navigation from planning another tunnel for a while.
     *
     * @param ticks cooldown length in ticks
     */
    public void suspendDigging(int ticks) {
        DigSession digSession = this.session;
        if (digSession != null) {
            digSession.suspendDigging(ticks);
        }
    }

    /**
     * Resumes the saved path after a dig. The stuck tracker is reset because the mob stood
     * still for the whole dig: without this the next {@code doStuckDetection} pass would see
     * "no movement in the last 100 ticks" and cancel the restored path immediately.
     */
    public void resumeAfterDig(@Nullable Entity target, double speed) {
        if (!this.suspended) {
            return;
        }
        this.suspended = false;
        Path restore = this.suspendedPath;
        this.suspendedPath = null;
        if (restore != null) {
            this.path = restore;
            resetStuckTracking();
            return;
        }
        if (target != null) {
            moveTo(target, speed);
        }
    }

    /**
     * Remaining cost of the active plan from the path cursor to its end, in the same units as the
     * {@code g} the search accumulated (walking distance plus dig cost). Returns {@code NaN} when
     * there is no active dig plan, i.e. when there is nothing to improve.
     */
    public float remainingCost() {
        DigPlan plan = getPlan();
        if (plan == null) {
            return Float.NaN;
        }
        Path path = plan.path();
        int nodeCount = path.getNodeCount();
        if (nodeCount == 0) {
            return 0.0F;
        }
        int index = Math.max(0, Math.min(path.getNextNodeIndex(), nodeCount - 1));
        return Math.max(path.getEndNode().g - path.getNode(index).g, 0.0F);
    }

    /**
     * Runs a full re-search toward {@code target} and keeps it only when it is at least
     * {@code switchMargin} cheaper than the active plan's remaining cost (R3, §8 of the
     * design doc). The old route is restored exactly when the search fails or the new route
     * is not clearly better, so a zombie never wavers over a marginal improvement.
     *
     * @return true when the route was switched and the caller may rebase its dig progress
     */
    public boolean replanIfBetter(@Nullable Entity target, double speed, float switchMargin) {
        if (target == null) {
            return false;
        }
        float oldRemaining = remainingCost();
        if (Float.isNaN(oldRemaining)) {
            return false;
        }
        Path oldPath = this.path;
        boolean oldSuspended = this.suspended;
        Path oldSuspendedPath = this.suspendedPath;
        DigPlan oldPlan = this.activePlan;
        int oldCursor = this.digStepCursor;

        // createPath() hands back the in-progress path untouched while the target has not
        // moved; clearing it is what forces a real candidate search.
        this.path = null;
        this.suspended = false;
        this.suspendedPath = null;

        moveTo(target, speed);
        if (this.path != null) {
            float newCost = this.path.getEndNode().g;
            if (newCost < oldRemaining * (1.0F - switchMargin)) {
                this.activePlan = null;
                this.digStepCursor = 0;
                return true;
            }
        }

        this.path = oldPath;
        this.suspended = oldSuspended;
        this.suspendedPath = oldSuspendedPath;
        this.activePlan = oldPlan;
        this.digStepCursor = oldCursor;
        return false;
    }

    private boolean hasUndugBlock(DigPlan.DigStep step) {
        for (BlockPos pos : step.blocks()) {
            if (!this.level.getBlockState(pos).isAir()) {
                return true;
            }
        }
        return false;
    }

    private void resetStuckTracking() {
        this.lastStuckCheck = this.tick;
        this.lastStuckCheckPos = getTempMobPos();
        this.timeoutCachedNode = Vec3i.ZERO;
        this.timeoutTimer = 0L;
        this.timeoutLimit = 0.0D;
    }
}
