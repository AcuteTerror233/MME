package com.acuteterror233.mite.world.entity.ai.goal;

import com.acuteterror233.mite.world.entity.ai.pathing.DigPlan;
import com.acuteterror233.mite.world.entity.ai.pathing.DigToolPolicy;
import com.acuteterror233.mite.world.entity.ai.pathing.ZombieDigNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

/**
 * Executor for the dig plan produced by {@link ZombieDigNavigation}: it walks the path the
 * planner found, stops at each step the plan marked as blocked, and removes exactly those
 * blocks. There is no straight-line scanning left — every target block comes from the path,
 * so the zombie digs the tunnel A* already paid for.
 *
 * <p>One branch runs per tick: it reads {@link ZombieDigNavigation#currentDigStep()} and
 * digs the step's blocks one by one (feet row first, then head row). While digging,
 * navigation is suspended; when a block is gone the saved path is restored, which is still
 * valid because the plan treated the block as air when it was built.</p>
 *
 * <p>Digging requires standing next to the block: a block is only attacked while it lies
 * within one block (Chebyshev distance) of the zombie's feet or head position
 * ({@value #DIG_REACH_CHEBYSHEV}); anything farther is left to navigation, so the zombie
 * must walk up to every block it removes — no digging across the room. Because this goal
 * owns the MOVE flag it always preempts the melee goal, whose {@code stop()} clears the
 * navigation path; the goal therefore re-issues the search itself whenever navigation has
 * run dry, otherwise nothing would move the zombie and no dig would ever start. A pillared target
 * needs no special case here: the ghost-region search plans the route straight through the
 * pillar, and the plan removes it from the bottom block upward, dropping the target before
 * the top is ever reached.</p>
 *
 * <p>The goal only starts when a step actually exists and its blocks are still diggable, so
 * a plain walk never preempts the melee goal. {@code MOB_GRIEFING} gates the branch.</p>
 */
public class ZombieDigGoal extends Goal {
    /** Maximum Chebyshev block distance (feet or head position) at which digging is allowed. */
    private static final int DIG_REACH_CHEBYSHEV = 1;
    /** Ticks between hand swings and break sound/particle events. */
    private static final int SWING_INTERVAL_TICKS = 10;
    /** Cooldown (before reduced-tick-delay scaling) after a dig ends or is abandoned. */
    private static final int DIG_COOLDOWN_TICKS = 10;
    /** Speed handed back to the navigation when it resumes. */
    private static final double SPEED_MODIFIER = 1.0D;
    /** Ticks between two route re-checks (R3). */
    private static final int REPLAN_INTERVAL_TICKS = 20;
    /** Minimum relative cost advantage required before switching to a new route. */
    private static final float SWITCH_MARGIN = 0.20F;
    /** Ticks between two re-issued searches while the next block is still out of reach. */
    private static final int WALK_RETRY_TICKS = 10;
    /**
     * Ticks without removing a single block before the plan is declared unexecutable. A route whose
     * next block stays out of reach (terrain changed after planning, the mob got shoved away, it
     * fell into a hole) would otherwise be retried forever, leaving the zombie twitching in place.
     */
    private static final int NO_PROGRESS_TIMEOUT_TICKS = 400;
    /** Ticks during which the mob refuses to plan another tunnel after giving up on a route. */
    private static final int DIG_RETRY_COOLDOWN_TICKS = 200;

    private final Zombie zombie;

    @Nullable
    private DigPlan.DigStep currentStep;
    @Nullable
    private BlockPos currentBlock;
    private int blockCursor;
    private int digProgress;
    private int digTicksTotal;
    private int swingTimer;

    private int startCooldown;
    private int replanTimer;
    private int walkRetryTimer;
    /**
     * Ticks spent standing on the same block without removing anything; see
     * {@link #NO_PROGRESS_TIMEOUT_TICKS}. Walking resets it, so only a genuinely stuck mob trips it.
     */
    private int noProgressTicks;
    /** Last block position the mob occupied, used to detect that it stopped moving. */
    @Nullable
    private BlockPos lastPos;

    public ZombieDigGoal(Zombie zombie) {
        this.zombie = zombie;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /** Starts when the current plan has a step whose blocks are still diggable. */
    @Override
    public boolean canUse() {
        if (this.startCooldown > 0) {
            this.startCooldown--;
            return false;
        }
        if (!(this.zombie.level() instanceof ServerLevel level)) {
            return false;
        }
        if (!level.getGameRules().get(GameRules.MOB_GRIEFING)) {
            return false;
        }
        LivingEntity target = this.zombie.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        ZombieDigNavigation navigation = this.digNavigation();
        if (navigation == null) {
            return false;
        }
        DigPlan.DigStep step = navigation.currentDigStep();
        return step != null && isDiggable(level, step);
    }

    /** Continues while the target lives and the current step is still workable. */
    @Override
    public boolean canContinueToUse() {
        if (!(this.zombie.level() instanceof ServerLevel level)) {
            return false;
        }
        if (!level.getGameRules().get(GameRules.MOB_GRIEFING)) {
            return false;
        }
        LivingEntity target = this.zombie.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        ZombieDigNavigation navigation = this.digNavigation();
        if (navigation == null) {
            return false;
        }
        DigPlan.DigStep step = navigation.currentDigStep();
        return step != null && isDiggable(level, step);
    }

    @Override
    public void start() {
        this.currentStep = null;
        this.currentBlock = null;
        this.blockCursor = 0;
        this.digProgress = 0;
        this.digTicksTotal = 0;
        this.swingTimer = 0;
        this.replanTimer = 0;
        this.walkRetryTimer = 0;
        this.noProgressTicks = 0;
        this.lastPos = null;
    }

    /** Clears crack overlays, drops the dig state and restores navigation. */
    @Override
    public void stop() {
        Level level = this.zombie.level();
        if (this.currentBlock != null) {
            level.destroyBlockProgress(this.zombie.getId(), this.currentBlock, -1);
        }
        this.currentStep = null;
        this.currentBlock = null;
        this.blockCursor = 0;
        this.digProgress = 0;
        this.digTicksTotal = 0;
        ZombieDigNavigation navigation = this.digNavigation();
        if (navigation != null) {
            navigation.resumeAfterDig(this.zombie.getTarget(), SPEED_MODIFIER);
        }
        this.startCooldown = this.reducedTickDelay(DIG_COOLDOWN_TICKS);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (!(this.zombie.level() instanceof ServerLevel level)) {
            return;
        }
        // R3: re-check the route every 20 ticks
        if (++this.replanTimer >= REPLAN_INTERVAL_TICKS) {
            this.replanTimer = 0;
            attemptReplan(level);
        }
        BlockPos here = this.zombie.blockPosition();
        if (!here.equals(this.lastPos)) {
            // Still moving: any walking, including the approach to the next block, counts as progress
            this.lastPos = here;
            this.noProgressTicks = 0;
        } else if (++this.noProgressTicks > NO_PROGRESS_TIMEOUT_TICKS) {
            giveUp();
            return;
        }
        tickPlanDig(level);
    }

    /**
     * Re-checks the route and rebases the dig state when the navigation adopts a clearly
     * better one. Progress on a block is kept only when that block is part of the new
     * plan's next step; otherwise the in-flight dig is abandoned and its crack overlay
     * cleared, as required by §8 of the design doc.
     */
    private void attemptReplan(ServerLevel level) {
        LivingEntity target = this.zombie.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        ZombieDigNavigation navigation = this.digNavigation();
        // Replanning is a full search followed by a moveTo; running it while the mob is halted to
        // dig would steer its move control away from the block it is chewing on
        if (navigation == null || navigation.isSuspended()
                || !navigation.replanIfBetter(target, SPEED_MODIFIER, SWITCH_MARGIN)) {
            return;
        }
        DigPlan.DigStep step = navigation.currentDigStep();
        if (this.currentBlock != null && step != null && step.blocks().contains(this.currentBlock)) {
            // same block on the new route: keep the accumulated progress
            this.currentStep = step;
            this.blockCursor = step.blocks().indexOf(this.currentBlock);
            return;
        }
        if (this.currentBlock != null) {
            level.destroyBlockProgress(this.zombie.getId(), this.currentBlock, -1);
        }
        this.currentStep = null;
        this.currentBlock = null;
        this.blockCursor = 0;
        this.digProgress = 0;
        this.digTicksTotal = 0;
        this.swingTimer = 0;
    }

    /** Follows the plan: approach, halt navigation, remove the step's blocks, resume. */
    private void tickPlanDig(ServerLevel level) {
        ZombieDigNavigation navigation = this.digNavigation();
        if (navigation == null) {
            return;
        }
        DigPlan.DigStep step = navigation.currentDigStep();
        if (step == null) {
            this.currentStep = null;
            this.currentBlock = null;
            navigation.resumeAfterDig(this.zombie.getTarget(), SPEED_MODIFIER);
            return;
        }
        if (step != this.currentStep) {
            this.currentStep = step;
            this.blockCursor = 0;
            this.currentBlock = null;
            this.digProgress = 0;
            this.digTicksTotal = 0;
            this.swingTimer = 0;
        }
        List<BlockPos> blocks = step.blocks();
        if (this.blockCursor >= blocks.size()) {
            finishStep(navigation);
            return;
        }
        BlockPos pos = blocks.get(this.blockCursor);
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) {
            advanceBlock(navigation, step);
            return;
        }
        if (!pos.equals(this.currentBlock)) {
            this.currentBlock = pos;
            this.digProgress = 0;
            this.digTicksTotal = Math.max(1,
                    DigToolPolicy.digTicks(level, pos, state, this.zombie.getMainHandItem()));
            this.swingTimer = 0;
        }
        if (!withinReach(pos)) {
            // This goal owns MOVE, so taking over preempts the melee goal whose stop() clears
            // the navigation path; nothing else would ever move the zombie again, and the dig
            // would never begin. Re-issue a search toward the target whenever navigation has
            // run dry — it is the same dig route, so the plan it yields stays usable.
            walkTowardTheBlock(navigation);
            return;
        }
        this.walkRetryTimer = 0;
        navigation.suspendForDig();
        this.zombie.getLookControl().setLookAt(Vec3.atCenterOf(pos));
        if (++this.swingTimer % SWING_INTERVAL_TICKS == 0) {
            this.zombie.swing(InteractionHand.MAIN_HAND, SwingAnimation.DEFAULT, true);
            level.levelEvent(2001, pos, Block.getId(state));
        }
        this.digProgress++;
        level.destroyBlockProgress(this.zombie.getId(), pos,
                progressStage(this.digProgress, this.digTicksTotal));
        if (this.digProgress >= this.digTicksTotal) {
            this.noProgressTicks = 0;
            level.destroyBlock(pos, false, this.zombie);
            level.destroyBlockProgress(this.zombie.getId(), pos, -1);
            navigation.onBlockDug(this.digTicksTotal);
            advanceBlock(navigation, step);
        }
    }

    /**
     * Abandons a route that has not produced a single block for {@link #NO_PROGRESS_TIMEOUT_TICKS}:
     * the plan and its path are dropped, and the navigation refuses to plan another tunnel for
     * {@link #DIG_RETRY_COOLDOWN_TICKS} so the next search cannot rebuild the same dead end.
     */
    private void giveUp() {
        ZombieDigNavigation navigation = this.digNavigation();
        if (navigation != null) {
            navigation.abandonPlan();
            navigation.suspendDigging(DIG_RETRY_COOLDOWN_TICKS);
        }
        this.noProgressTicks = 0;
        this.startCooldown = this.reducedTickDelay(DIG_COOLDOWN_TICKS);
    }

    private void advanceBlock(ZombieDigNavigation navigation, DigPlan.DigStep step) {
        this.blockCursor++;
        this.currentBlock = null;
        this.digProgress = 0;
        this.digTicksTotal = 0;
        if (this.blockCursor >= step.blocks().size()) {
            finishStep(navigation);
        }
    }

    private void finishStep(ZombieDigNavigation navigation) {
        DigPlan.DigStep step = this.currentStep;
        if (step != null) {
            navigation.onDigStepFinished(step);
        }
        this.currentStep = null;
        this.currentBlock = null;
        this.blockCursor = 0;
        this.digProgress = 0;
        this.digTicksTotal = 0;
        navigation.resumeAfterDig(this.zombie.getTarget(), SPEED_MODIFIER);
    }

    private boolean isDiggable(ServerLevel level, DigPlan.DigStep step) {
        for (BlockPos pos : step.blocks()) {
            BlockState state = level.getBlockState(pos);
            if (state.isAir()) {
                continue;
            }
            if (!DigToolPolicy.canDig(level, pos, state, this.zombie.getMainHandItem())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Digging is only allowed next to the block: the Chebyshev distance from the zombie's
     * feet or head position must stay within {@value #DIG_REACH_CHEBYSHEV} block(s). This
     * keeps the executor from chewing through walls from afar and removes the old
     * stand-under-the-pillar shortcut — the zombie has to climb what it digs.
     */
    private boolean withinReach(BlockPos pos) {
        BlockPos feet = this.zombie.blockPosition();
        return chebyshev(pos, feet) <= DIG_REACH_CHEBYSHEV
                || chebyshev(pos, feet.above()) <= DIG_REACH_CHEBYSHEV;
    }

    /**
     * Keeps the zombie moving while the next block is still out of reach. A search is only
     * re-issued when navigation is done and the retry timer has expired, so a zombie that
     * cannot path at all does not pay for two A* passes on every tick.
     */
    private void walkTowardTheBlock(ZombieDigNavigation navigation) {
        if (!navigation.isDone()) {
            this.walkRetryTimer = 0;
            return;
        }
        if (this.walkRetryTimer > 0) {
            this.walkRetryTimer--;
            return;
        }
        LivingEntity target = this.zombie.getTarget();
        if (target != null) {
            navigation.moveTo(target, SPEED_MODIFIER);
        }
        this.walkRetryTimer = WALK_RETRY_TICKS;
    }

    private static int chebyshev(BlockPos a, BlockPos b) {
        return Math.max(Math.abs(a.getX() - b.getX()),
                Math.max(Math.abs(a.getY() - b.getY()), Math.abs(a.getZ() - b.getZ())));
    }

    private static int progressStage(int progress, int total) {
        return Math.min(progress * 10 / total, 9);
    }

    @Nullable
    private ZombieDigNavigation digNavigation() {
        return this.zombie.getNavigation() instanceof ZombieDigNavigation navigation ? navigation : null;
    }
}
