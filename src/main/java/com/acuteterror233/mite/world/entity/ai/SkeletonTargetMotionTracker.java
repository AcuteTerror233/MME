package com.acuteterror233.mite.world.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Server-side velocity estimator for skeleton ranged-attack targets ("predictive shooting").
 *
 * <p>Both {@code getDeltaMovement()} and the {@code xo/yo/zo} previous-tick position diff are
 * useless for server-side players: movement packets are processed at the start of the server
 * tick (before entity ticking) and {@code Entity.baseTick} immediately re-syncs {@code xo} to
 * the packet-updated position, so the per-tick diff is always zero. The fix is explicit
 * cross-tick sampling: each tick a tracking skeleton records its target's position (at that
 * point the target's movement for this tick has already been applied), and the velocity is the
 * position delta divided by the sampled tick span, smoothed with an exponential moving average.</p>
 *
 * <p>All state is game-thread only (no synchronization); entries are {@link WeakHashMap}-keyed
 * by the target and cleaned up by the garbage collector once no skeleton references them.</p>
 */
public final class SkeletonTargetMotionTracker {
    /** Samples older than this many ticks are stale; the estimate resets instead of extrapolating. */
    private static final int STALE_TICKS = 5;
    /** Position jumps above this many blocks per sampled tick are treated as teleports. */
    private static final double TELEPORT_SPEED_SQR = 100.0D;
    /** Exponential-moving-average weight of a new per-tick velocity sample (0..1). */
    private static final double EMA_FACTOR = 0.5D;

    /** Per-target motion state; keys die with the target entity. */
    private static final Map<LivingEntity, Motion> TRACKED = new WeakHashMap<>();

    /** Mutable per-target sampling state (game thread only). */
    private static final class Motion {
        private long tick;
        private double x;
        private double y;
        private double z;
        private double vx;
        private double vy;
        private double vz;

        /** Re-seeds the state after staleness or a teleport, zeroing the velocity estimate. */
        private void reseed(LivingEntity target, long gameTime) {
            this.tick = gameTime;
            this.x = target.getX();
            this.y = target.getY();
            this.z = target.getZ();
            this.vx = 0.0D;
            this.vy = 0.0D;
            this.vz = 0.0D;
        }
    }

    private SkeletonTargetMotionTracker() {
    }

    /**
     * Records the target's current position; call once per tick for every skeleton that is
     * tracking {@code target}. Multiple skeletons targeting the same entity share one sample
     * per tick (the first caller wins), so the tick span stays exact.
     *
     * @param target    the entity being tracked
     * @param gameTime  the current server game time in ticks
     */
    public static void record(LivingEntity target, long gameTime) {
        Motion motion = TRACKED.get(target);
        if (motion == null) {
            motion = new Motion();
            TRACKED.put(target, motion);
            motion.reseed(target, gameTime);
            return;
        }
        if (motion.tick == gameTime) {
            return;
        }
        long dt = gameTime - motion.tick;
        if (dt > STALE_TICKS) {
            motion.reseed(target, gameTime);
            return;
        }
        double dx = target.getX() - motion.x;
        double dy = target.getY() - motion.y;
        double dz = target.getZ() - motion.z;
        if (dx * dx + dy * dy + dz * dz > TELEPORT_SPEED_SQR * dt * dt) {
            motion.reseed(target, gameTime);
            return;
        }
        double instantPerTick = 1.0D / dt;
        motion.vx += (dx * instantPerTick - motion.vx) * EMA_FACTOR;
        motion.vy += (dy * instantPerTick - motion.vy) * EMA_FACTOR;
        motion.vz += (dz * instantPerTick - motion.vz) * EMA_FACTOR;
        motion.tick = gameTime;
        motion.x = target.getX();
        motion.y = target.getY();
        motion.z = target.getZ();
    }

    /**
     * Returns the smoothed velocity estimate in blocks per tick, or {@link Vec3#ZERO} when the
     * target is untracked or the data is stale.
     *
     * @param target    the entity being tracked
     * @param gameTime  the current server game time in ticks
     */
    public static Vec3 velocity(LivingEntity target, long gameTime) {
        Motion motion = TRACKED.get(target);
        if (motion == null || gameTime - motion.tick > STALE_TICKS) {
            return Vec3.ZERO;
        }
        return new Vec3(motion.vx, motion.vy, motion.vz);
    }
}
