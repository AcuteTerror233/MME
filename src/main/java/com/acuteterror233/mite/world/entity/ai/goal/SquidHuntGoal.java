package com.acuteterror233.mite.world.entity.ai.goal;

import com.acuteterror233.mite.interfaces.SquidExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

/**
 * Hunting behaviour for vanilla squids with two attack target kinds:
 *
 * <ol>
 *   <li><b>Players</b> — the squid jets toward the nearest valid player and, on contact, applies
 *       {@link MobEffects#SLOWNESS} level V for {@value #SLOWNESS_TICKS} ticks without dealing
 *       any damage; afterwards the squid ignores all players for {@value #PLAYER_COOLDOWN_TICKS}
 *       ticks (30 seconds). Players riding a boat are skipped so the boat attack takes over
 *       instead, and creative/spectator players are never targeted.</li>
 *   <li><b>Boats</b> — the squid latches onto the nearest boat, brakes its horizontal
 *       movement every tick (a rowed boat travels much slower while it is latched; the
 *       boat keeps floating normally) and bites it every {@value #BOAT_BITE_INTERVAL}
 *       ticks until it breaks. Boat hunting is not affected by the player cooldown.</li>
 * </ol>
 *
 * <p>Propulsion reuses the vanilla squid movement field via {@link SquidExtension}: the vector
 * written here replaces the squid's {@code deltaMovement} during its jet phase, exactly like the
 * vanilla random-movement goal, so the chase feels native. Players take priority over boats
 * whenever the player cooldown is inactive; boat hunting is also allowed on peaceful difficulty
 * while player grabs require a hostile difficulty, mirroring vanilla targeting rules.</p>
 */
public class SquidHuntGoal extends Goal {
    /** Search radius (squared) for both target kinds. */
    private static final double TARGET_RANGE_SQR = 8.0D * 8.0D;
    /** Goal gives up when the target drifts beyond 1.5x the search radius. */
    private static final double LEASH_RANGE_SQR = 12.0D * 12.0D;
    /** Contact distance (squared) at which a player is grabbed. */
    private static final double GRAB_RANGE_SQR = 1.8D * 1.8D;
    /** Contact distance (squared) at which a boat is latched. */
    private static final double LATCH_RANGE_SQR = 2.4D * 2.4D;
    /** After grabbing a player the squid ignores all players for this many ticks (30 s). */
    private static final int PLAYER_COOLDOWN_TICKS = 600;
    /** Duration of the Slowness V applied by a grab, in ticks (5 s). */
    private static final int SLOWNESS_TICKS = 15 * 20;
    /** Slowness V: amplifier index 4 = level V. */
    private static final int SLOWNESS_AMPLIFIER = 4;
    /** Propulsion vector magnitude while chasing (vanilla random movement uses ~0.1). */
    private static final double CHASE_SPEED = 0.2D;
    /** Damage per bite on a latched boat (a boat has 8 health). */
    private static final float BOAT_DAMAGE = 3.0F;
    /** Ticks between consecutive boat bites. */
    private static final int BOAT_BITE_INTERVAL = 20;
    /**
     * Horizontal velocity multiplier applied to a latched boat each tick (hard brake — a rowed
     * boat crawls at roughly a tenth of its normal cruise speed while latched).
     */
    private static final double BOAT_SLOWDOWN_FACTOR = 0.5D;

    private final Squid squid;
    private final SquidExtension extension;

    @Nullable
    private Player playerTarget;
    @Nullable
    private AbstractBoat boatTarget;
    /** Game time until which player targeting is suppressed after a grab. */
    private long playerCooldownUntil;
    /** Countdown to the next boat bite while latched. */
    private int boatBiteTimer;

    /**
     * Creates the hunt goal for one squid instance.
     *
     * @param squid the owning squid
     */
    public SquidHuntGoal(Squid squid) {
        this.squid = squid;
        this.extension = (SquidExtension) squid;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.squid.isInWater() || !(this.squid.level() instanceof ServerLevel serverLevel)) {
            return false;
        }
        this.findTargets(serverLevel);
        return this.playerTarget != null || this.boatTarget != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.squid.isInWater()) {
            return false;
        }
        if (this.playerTarget != null) {
            return this.isPlayerValid(this.playerTarget) && this.squid.distanceToSqr(this.playerTarget) <= LEASH_RANGE_SQR;
        }
        if (this.boatTarget != null) {
            return this.boatTarget.isAlive() && this.squid.distanceToSqr(this.boatTarget) <= LEASH_RANGE_SQR;
        }
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.boatBiteTimer = 0;
    }

    @Override
    public void stop() {
        this.playerTarget = null;
        this.boatTarget = null;
        this.extension.mme$setHuntMovement(Vec3.ZERO);
    }

    @Override
    public void tick() {
        ServerLevel level = (ServerLevel) this.squid.level();
        long gameTime = level.getGameTime();
        if (this.playerTarget != null) {
            if (!this.isPlayerValid(this.playerTarget)) {
                this.playerTarget = null;
                return;
            }
            if (this.squid.distanceToSqr(this.playerTarget) <= GRAB_RANGE_SQR) {
                this.grabPlayer(this.playerTarget, gameTime);
            } else {
                this.steerTowards(this.playerTarget.getEyePosition());
            }
        } else if (this.boatTarget != null) {
            if (!this.boatTarget.isAlive()) {
                this.boatTarget = null;
                return;
            }
            if (this.squid.distanceToSqr(this.boatTarget) <= LATCH_RANGE_SQR) {
                this.attackBoat(level, this.boatTarget);
            } else {
                this.steerTowards(this.boatTarget.position());
            }
        }
    }

    /**
     * Selects the next hunt target: the nearest valid player if the cooldown is inactive, or
     * otherwise the nearest alive boat.
     */
    private void findTargets(ServerLevel level) {
        this.playerTarget = null;
        this.boatTarget = null;
        if (level.getDifficulty() != Difficulty.PEACEFUL && level.getGameTime() >= this.playerCooldownUntil) {
            this.playerTarget = level.getNearestPlayer(
                    this.squid.getX(), this.squid.getY(), this.squid.getZ(), Math.sqrt(TARGET_RANGE_SQR),
                    entity -> entity instanceof Player player && this.isPlayerValid(player));
        }
        if (this.playerTarget == null) {
            List<AbstractBoat> boats = level.getEntitiesOfClass(
                    AbstractBoat.class, this.squid.getBoundingBox().inflate(Math.sqrt(TARGET_RANGE_SQR)), AbstractBoat::isAlive);
            double bestDistanceSqr = Double.MAX_VALUE;
            for (AbstractBoat boat : boats) {
                double distanceSqr = this.squid.distanceToSqr(boat);
                if (distanceSqr < bestDistanceSqr) {
                    bestDistanceSqr = distanceSqr;
                    this.boatTarget = boat;
                }
            }
        }
    }

    /**
     * Checks whether a player remains a valid grab target: alive, not spectator/creative, not
     * riding a boat (the boat is targeted instead) and within the search radius.
     */
    private boolean isPlayerValid(Player player) {
        return player.isAlive()
                && !player.isSpectator()
                && !player.isCreative()
                && !(player.getVehicle() instanceof AbstractBoat)
                && this.squid.distanceToSqr(player) <= TARGET_RANGE_SQR;
    }

    /**
     * Grab: applies Slowness V (no damage) and starts the 30 s no-player cooldown, then
     * releases the target so the goal can hand movement back to the idle goals.
     *
     * <p>The Slowness V alone is intentional: it scales the water travel input below the
     * buoyancy threshold, so the victim cannot swim up and risks drowning — the grab is a
     * real drowning threat, not just a debuff.</p>
     */
    private void grabPlayer(Player player, long gameTime) {
        player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, SLOWNESS_TICKS, SLOWNESS_AMPLIFIER));
        this.playerCooldownUntil = gameTime + PLAYER_COOLDOWN_TICKS;
        this.squid.playSound(SoundEvents.SQUID_SQUIRT, 1.0F, 1.0F);
        this.playerTarget = null;
        this.boatTarget = null;
        this.extension.mme$setHuntMovement(Vec3.ZERO);
    }

    /**
     * Attacking: brakes the latched boat every tick by damping its horizontal velocity
     * (vertical physics stay untouched so the boat keeps floating) and bites it at a fixed
     * interval until it breaks.
     */
    private void attackBoat(ServerLevel level, AbstractBoat boat) {
        boat.setDeltaMovement(boat.getDeltaMovement().multiply(BOAT_SLOWDOWN_FACTOR, 1.0D, BOAT_SLOWDOWN_FACTOR));
        if (++this.boatBiteTimer >= BOAT_BITE_INTERVAL) {
            this.boatBiteTimer = 0;
            boat.hurtServer(level, this.squid.damageSources().mobAttack(this.squid), BOAT_DAMAGE);
            this.squid.playSound(SoundEvents.SQUID_SQUIRT, 1.0F, 0.8F);
        }
    }

    /** Points the propulsion vector straight at the target position. */
    private void steerTowards(Vec3 targetPos) {
        this.extension.mme$setHuntMovement(targetPos.subtract(this.squid.position()).normalize().scale(CHASE_SPEED));
    }
}
