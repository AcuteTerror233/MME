package com.acuteterror233.mite.world.entity.ai.goal;

import com.acuteterror233.mite.world.entity.animal.MMEPanicCooldown;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.fish.AbstractSchoolingFish;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

/**
 * Panic goal for fish: runs the MME panic system (forced-panic deadline + threat source) on
 * every fish and organizes the escape around the school's lead fish. This goal replaces the
 * vanilla priority-0 {@code PanicGoal} registered by {@code AbstractFish.registerGoals} (the
 * vanilla goal previously won the MOVE flag and blocked this goal's panic broadcast); the swap
 * is performed by {@code AbstractFishMixin}'s {@code registerGoals} TAIL injection, which
 * removes every {@code PanicGoal} and registers this goal at priority 0 instead.
 *
 * <p>Leader-led escape: schooling followers ({@code AbstractSchoolingFish#isFollower}) whose
 * lead fish is panicking follow the leader instead of picking their own escape direction — the
 * school flees as one group behind its head fish (the {@code leader} field is read through the
 * access widener). Leaders and solitary fish pick their own water-aware escape route.</p>
 *
 * <p>Anti-beaching: strict escape candidates require at least two consecutive water blocks
 * starting at the target and a water-valid straight-line midpoint, so the flight avoids
 * surface-hugging shore runs (a relaxed open-water sample is only kept as fallback); the
 * wide ±120° directional jitter keeps repeated flights from following one fixed bearing.</p>
 *
 * <p>Spreading: on a fresh panic event the goal broadcasts the deadline and threat position to
 * all not-yet-panicked fish within {@link #SPREAD_RANGE} blocks. Anti-loop guarantee: broadcast
 * only runs when the deadline has already expired, and every recipient is set to a future
 * deadline before its own {@code start} runs, so recipients hit the early return and never
 * re-broadcast — panic can therefore never ping-pong between fish.</p>
 */
public class FishPanicGoal extends Goal {
    /** Safety re-pick interval (ticks) while the current escape path is still being followed. */
    private static final int SAFETY_REPATH_INTERVAL_TICKS = 40;
    /** Vertical water-block scan offsets (around the fish's own Y) for one sampled column; deeper offsets first. */
    private static final int[] VERTICAL_SCAN_OFFSETS = {0, -1, 1, -2, 2, -3, 3};
    /** Radius (blocks) in which a fresh panic is broadcast to other fish. */
    private static final double SPREAD_RANGE = 16.0D;
    /** Navigation speed modifier while fleeing (leaders) or catching up (followers). */
    private static final double FLEE_SPEED_MODIFIER = 1.6D;
    /** Squared distance (blocks²) below which a follower hovers behind the panicking leader. */
    private static final double FOLLOW_CATCHUP_DISTANCE_SQR = 9.0D;
    /** Minimum horizontal distance of one escape-position sample. */
    private static final double FLEE_DISTANCE_MIN = 10.0D;
    /** Extra random horizontal distance of one escape-position sample (10–20 blocks total). */
    private static final double FLEE_DISTANCE_RANGE = 10.0D;
    /** Maximum angular jitter (radians, per side) around the exact away-from-threat direction (±120°). */
    private static final double FLEE_ANGLE_HALF_JITTER = 2.0D * Math.PI / 3.0D;
    /** Escape-position sampling attempts per re-path. */
    private static final int SAMPLE_ATTEMPTS = 24;
    /** Minimum water-block depth (target column) for a strict escape position. */
    private static final int MIN_ESCAPE_WATER_DEPTH = 2;
    /** Maximum downward water-depth probe length. */
    private static final int MAX_DEPTH_PROBE = 4;

    private final AbstractFish fish;
    /** Countdown to the next escape-position re-pick (leaders/solitary fish). */
    private int repathTimer;
    /** Countdown to the next leader-catch-up re-path (followers). */
    private int followRepathTimer;

    /**
     * Creates the goal for one fish.
     *
     * @param fish the owning fish
     */
    public FishPanicGoal(AbstractFish fish) {
        this.fish = fish;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Panics while this fish's forced-panic deadline is in the future, while its school's lead
     * fish is panicking, or on a fresh threat.
     */
    @Override
    public boolean canUse() {
        if (((MMEPanicCooldown) this.fish).mme$getForcedPanicUntil() > this.fish.tickCount) {
            return true;
        }
        if (this.mme$panickingLeader() != null) {
            return true;
        }
        LivingEntity attacker = this.fish.getLastHurtByMob();
        return attacker != null && attacker.isAlive();
    }

    /** Keeps fleeing while the own deadline is in the future or the panicking leader still flees. */
    @Override
    public boolean canContinueToUse() {
        if (((MMEPanicCooldown) this.fish).mme$getForcedPanicUntil() > this.fish.tickCount) {
            return true;
        }
        return this.mme$panickingLeader() != null;
    }

    /**
     * On a fresh panic event, records the attacker as the threat, extends the deadline to one
     * minute and broadcasts panic (with the threat position) to nearby not-yet-panicked fish;
     * a start triggered by an earlier broadcast hits the early return and never re-broadcasts.
     * Movement starts in the first {@code tick} via the zeroed re-path timers.
     */
    @Override
    public void start() {
        this.repathTimer = 0;
        this.followRepathTimer = 0;
        MMEPanicCooldown duck = (MMEPanicCooldown) this.fish;
        int now = this.fish.tickCount;
        if (this.fish.level().isClientSide() || duck.mme$getForcedPanicUntil() > now) {
            return;
        }
        int untilTick = now + MMEPanicCooldown.FORCED_PANIC_DURATION_TICKS;
        duck.mme$setForcedPanicUntil(untilTick);
        LivingEntity attacker = this.fish.getLastHurtByMob();
        if (attacker != null && attacker.isAlive()) {
            duck.mme$setPanicSourcePos(attacker.position());
        }
        Vec3 source = duck.mme$getPanicSourcePos();
        if (source == null) {
            return;
        }
        List<AbstractFish> nearby = this.fish.level().getEntitiesOfClass(
                AbstractFish.class,
                this.fish.getBoundingBox().inflate(SPREAD_RANGE),
                target -> target.isAlive() && ((MMEPanicCooldown) target).mme$getForcedPanicUntil() <= now
        );
        for (AbstractFish recipient : nearby) {
            MMEPanicCooldown cooldown = (MMEPanicCooldown) recipient;
            cooldown.mme$setForcedPanicUntil(untilTick);
            cooldown.mme$setPanicSourcePos(source);
        }
    }

    /**
     * Followers trail their panicking lead fish through open water; leaders and solitary fish
     * re-pick their own escape position only when the current path is finished (the fish arrived
     * or got stuck) or the safety interval elapsed — at multiplied swim speeds a fixed short
     * re-path interval would interrupt the flee every few blocks and destroy the away bias.
     */
    @Override
    public void tick() {
        AbstractSchoolingFish leader = this.mme$panickingLeader();
        if (leader != null) {
            this.mme$followLeader(leader);
        } else if (this.fish.getNavigation().isDone() || --this.repathTimer <= 0) {
            this.repathTimer = SAFETY_REPATH_INTERVAL_TICKS;
            this.mme$navigateAway();
        }
    }

    /** Stops the swim navigation when the panic ends. */
    @Override
    public void stop() {
        this.fish.getNavigation().stop();
    }

    /**
     * @return this fish's school lead fish when this fish is a schooling follower whose leader
     *         is alive and still inside its forced-panic deadline, otherwise {@code null}
     *         (the fish then escapes on its own).
     */
    @Nullable
    private AbstractSchoolingFish mme$panickingLeader() {
        if (this.fish instanceof AbstractSchoolingFish schooling && schooling.isFollower()) {
            AbstractSchoolingFish leader = schooling.leader;
            if (leader != null && leader.isAlive()
                    && ((MMEPanicCooldown) leader).mme$getForcedPanicUntil() > leader.tickCount) {
                return leader;
            }
        }
        return null;
    }

    /**
     * Keeps a panicking follower with its lead fish: beyond the catch-up distance it swims
     * toward the leader at flee speed; within it the fish hovers (the leader's own flight
     * drags the school along without followers overshooting on fresh paths every tick).
     *
     * @param leader the panicking school lead fish
     */
    private void mme$followLeader(AbstractSchoolingFish leader) {
        if (this.fish.distanceToSqr(leader) <= FOLLOW_CATCHUP_DISTANCE_SQR && this.fish.getNavigation().isDone()) {
            return;
        }
        if (this.fish.getNavigation().isDone() || --this.followRepathTimer <= 0) {
            this.followRepathTimer = SAFETY_REPATH_INTERVAL_TICKS;
            this.fish.getNavigation().moveTo(leader.getX(), leader.getY(), leader.getZ(), FLEE_SPEED_MODIFIER);
        }
    }

    /** Navigates to a water position away from the recorded threat (random water spot without one). */
    private void mme$navigateAway() {
        Vec3 source = ((MMEPanicCooldown) this.fish).mme$getPanicSourcePos();
        Vec3 target = this.mme$findWaterPos(source);
        if (target != null) {
            this.fish.getNavigation().moveTo(target.x, target.y, target.z, FLEE_SPEED_MODIFIER);
        }
    }

    /**
     * Samples water-valid escape positions biased away from {@code source}: up to
     * {@link #SAMPLE_ATTEMPTS} attempts, each within ±120° of the exact away direction at a
     * random 10–20 block distance. A strict candidate needs {@link #MIN_ESCAPE_WATER_DEPTH}
     * consecutive water blocks starting at it, including the candidate itself (no
     * surface-hugging shore runs), and a water-valid straight-line midpoint (no flight paths
     * crossing land); the first relaxed candidate (open water, no depth/path guarantee) is kept
     * as fallback. Without a source the direction is fully random.
     *
     * @param source the threat position, or {@code null} for direction-unbiased flight
     * @return an accepted escape position, or {@code null} when no sample was water-valid
     */
    @Nullable
    private Vec3 mme$findWaterPos(@Nullable Vec3 source) {
        boolean biased = source != null;
        double awayAngle = biased
                ? Math.atan2(this.fish.getZ() - source.z, this.fish.getX() - source.x)
                : 0.0D;
        double jitterSpan = biased ? FLEE_ANGLE_HALF_JITTER * 2.0D : Math.PI * 2.0D;
        Vec3 relaxedFallback = null;
        for (int i = 0; i < SAMPLE_ATTEMPTS; i++) {
            double angle = awayAngle + (this.fish.getRandom().nextDouble() - 0.5D) * jitterSpan;
            double distance = FLEE_DISTANCE_MIN + this.fish.getRandom().nextDouble() * FLEE_DISTANCE_RANGE;
            double x = this.fish.getX() + Math.cos(angle) * distance;
            double z = this.fish.getZ() + Math.sin(angle) * distance;
            double midX = this.fish.getX() + Math.cos(angle) * (distance * 0.5D);
            double midZ = this.fish.getZ() + Math.sin(angle) * (distance * 0.5D);
            for (int dy : VERTICAL_SCAN_OFFSETS) {
                BlockPos pos = BlockPos.containing(x, this.fish.getY() + dy, z);
                if (!this.fish.level().getFluidState(pos).is(FluidTags.WATER)
                        || !this.fish.getNavigation().isStableDestination(pos)) {
                    continue;
                }
                Vec3 candidate = new Vec3(x, this.fish.getY() + dy, z);
                if (relaxedFallback == null) {
                    relaxedFallback = candidate;
                }
                if (this.mme$waterDepth(pos) < MIN_ESCAPE_WATER_DEPTH) {
                    continue;
                }
                if (!this.mme$hasWaterColumn(midX, this.fish.getY() + dy, midZ)) {
                    continue;
                }
                return candidate;
            }
        }
        return relaxedFallback;
    }

    /**
     * @param pos the position to probe (must itself be water)
     * @return the number of consecutive water blocks starting at {@code pos} and probing
     *         downward, capped at {@link #MAX_DEPTH_PROBE}
     */
    private int mme$waterDepth(BlockPos pos) {
        int depth = 0;
        BlockPos cursor = pos;
        for (int i = 0; i < MAX_DEPTH_PROBE; i++) {
            if (!this.fish.level().getFluidState(cursor).is(FluidTags.WATER)) {
                break;
            }
            depth++;
            cursor = cursor.below();
        }
        return depth;
    }

    /**
     * @param x the column's x coordinate
     * @param y the fish's reference y coordinate
     * @param z the column's z coordinate
     * @return whether any block in the scanned vertical band around {@code (x, y, z)} is water
     */
    private boolean mme$hasWaterColumn(double x, double y, double z) {
        for (int dy : VERTICAL_SCAN_OFFSETS) {
            BlockPos pos = BlockPos.containing(x, y + dy, z);
            if (this.fish.level().getFluidState(pos).is(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }
}
