package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * A {@link Goal} that lets a {@link Zombie} dig straight through blocking terrain toward
 * its attack target when the path cannot reach the destination (wall between zombie and
 * target, or repeated horizontal collision).
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@link #canUse}: requires {@code MOB_GRIEFING}, a living attack target, and a
 *       blocked state ({@code horizontalCollision} or an unreachable {@link Path}). A
 *       straight-line scan from the zombie toward the target then picks the first block
 *       with a collision shape (feet and head positions of both samplings are checked).</li>
 *   <li>Tool gate: a block that {@code requiresCorrectToolForDrops()} may only be dug
 *       when the zombie's main-hand item can harvest it ({@link
 *       net.minecraft.world.item.ItemStack#isCorrectToolForDrops(BlockState)}). If the
 *       first obstacle is not harvestable the scan gives up immediately — zombies will
 *       never dig stone-class blocks without a proper pickaxe.</li>
 *   <li>Dig duration: bare hand takes {@code hardness * 1200} ticks (one minute per
 *       hardness point, hardness below 1 treated as 1); a held tool speeds this up
 *       proportionally to its mining speed ({@code Item.getDestroySpeed}, e.g. mining
 *       speed 6 digs 6x faster), floored at 20 ticks.</li>
 *   <li>{@link #tick}: walks toward the obstacle first; within 2.25 blocks it stops
 *       navigating, faces the block, swings and plays the break particles/sound every
 *       5 ticks, and reports progress through {@code destroyBlockProgress} so clients
 *       render the crack overlay. At full progress the block is destroyed via
 *       {@code Level.destroyBlock} (drops handled by vanilla loot logic).</li>
 *   <li>Progress is abandoned when the target dies or the block state changes; the crack
 *       overlay is cleared in {@link #stop()} and a short cooldown prevents re-scan spam.</li>
 * </ul>
 */
public class ZombieDigGoal extends Goal {
    /** Maximum straight-line scan distance (in blocks) toward the target. */
    private static final double SCAN_REACH = 8.0D;
    /** Distance to the obstacle center (in blocks) within which digging starts. */
    private static final double BREAK_RANGE = 2.25D;
    /** Ticks between hand swings and break particle/sound events while digging. */
    private static final int SWING_INTERVAL_TICKS = 200;
    /** Cooldown (before reducedTickDelay scaling) after a scan that found no obstacle. */
    private static final int RETARGET_COOLDOWN_TICKS = 20;
    /** Cooldown (before reducedTickDelay scaling) after abandoning or finishing a dig. */
    private static final int DIG_COOLDOWN_TICKS = 10;
    /** Maximum number of sample points along the scan ray. */
    private static final int MAX_SCAN_STEPS = 16;
    /** Bare-hand digging time per hardness point (60 s at 20 tps). */
    private static final float BARE_HAND_TICKS_PER_HARDNESS = 1200.0F;

    private final Zombie zombie;
    @Nullable
    private BlockPos digPos;
    @Nullable
    private BlockState digState;
    private int digProgress;
    private int digTicksTotal;
    private int startCooldown;
    private int swingTimer;
    private int approachTimer;

    public ZombieDigGoal(Zombie zombie) {
        this.zombie = zombie;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Starts digging when griefing is allowed, a target exists, the zombie is physically
     * blocked (collision or unreachable path), and the first obstacle toward the target
     * is harvestable with the currently held tool.
     */
    @Override
    public boolean canUse() {
        if (this.startCooldown > 0) {
            this.startCooldown--;
            return false;
        }
        Level level = this.zombie.level();
        if (!getServerLevel(this.zombie).getGameRules().get(GameRules.MOB_GRIEFING)) {
            return false;
        }
        LivingEntity target = this.zombie.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        Path path = this.zombie.getNavigation().getPath();
        boolean blocked = this.zombie.horizontalCollision || (path != null && !path.canReach());
        if (!blocked) {
            return false;
        }
        BlockPos obstacle = this.findObstacle(target);
        if (obstacle == null) {
            this.startCooldown = this.reducedTickDelay(RETARGET_COOLDOWN_TICKS);
            return false;
        }
        this.digPos = obstacle;
        this.digState = level.getBlockState(obstacle);
        // Bare hand: hardness * 1200 ticks (one minute per hardness point); held tools
        // scale the time down by their mining speed (Item.getDestroySpeed). Hardness
        // below 1 is treated as 1 so soft blocks still take at least one minute bare-hand
        ItemStack held = this.zombie.getMainHandItem();
        float toolSpeed = Math.max(1.0F, held.getItem().getDestroySpeed(held, this.digState));
        float hardness = Math.max(1.0F, this.digState.getDestroySpeed(level, obstacle));
        this.digTicksTotal = Math.max(20, (int) (hardness * BARE_HAND_TICKS_PER_HARDNESS / toolSpeed));
        return true;
    }

    /** Continues while the target lives and the target block is still the same state. */
    @Override
    public boolean canContinueToUse() {
        if (this.digPos == null || this.digState == null) {
            return false;
        }
        LivingEntity target = this.zombie.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return this.digProgress < this.digTicksTotal
                && this.zombie.level().getBlockState(this.digPos) == this.digState;
    }

    @Override
    public void start() {
        this.digProgress = 0;
        this.swingTimer = 0;
        this.approachTimer = 0;
    }

    /** Clears the crack overlay and enters a short cooldown after digging ends. */
    @Override
    public void stop() {
        Level level = this.zombie.level();
        if (this.digPos != null) {
            level.destroyBlockProgress(this.zombie.getId(), this.digPos, -1);
        }
        this.digPos = null;
        this.digState = null;
        this.digProgress = 0;
        this.startCooldown = this.reducedTickDelay(DIG_COOLDOWN_TICKS);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Digs when close enough (stop navigation, face the block, swing, report crack
     * progress, destroy at full progress); otherwise approaches the obstacle.
     */
    @Override
    public void tick() {
        if (this.digPos == null || this.digState == null) {
            return;
        }
        Level level = this.zombie.level();
        if (this.digPos.closerToCenterThan(this.zombie.position(), BREAK_RANGE)) {
            this.zombie.getNavigation().stop();
            this.zombie.getLookControl().setLookAt(Vec3.atCenterOf(this.digPos));
            if (++this.swingTimer % SWING_INTERVAL_TICKS == 0) {
                this.zombie.swing(InteractionHand.MAIN_HAND, SwingAnimation.DEFAULT, true);
                level.levelEvent(2001, this.digPos, Block.getId(this.digState));
            }
            this.digProgress++;
            int stage = Math.min(this.digProgress * 10 / this.digTicksTotal, 9);
            level.destroyBlockProgress(this.zombie.getId(), this.digPos, stage);
            if (this.digProgress >= this.digTicksTotal) {
                level.destroyBlock(this.digPos, false, this.zombie);
            }
        } else if (++this.approachTimer % this.reducedTickDelay(10) == 0) {
            this.zombie.getNavigation().moveTo(
                    (double) this.digPos.getX() + 0.5D,
                    this.digPos.getY(),
                    (double) this.digPos.getZ() + 0.5D,
                    1.0D);
        }
    }

    /**
     * Scans a straight line from the zombie to the target in 0.5-block steps and returns
     * the first block with a collision shape (feet and head levels are both considered).
     * Returns {@code null} when the target is too close, nothing blocks the line, or the
     * first obstacle is not harvestable with the zombie's held tool.
     */
    @Nullable
    private BlockPos findObstacle(LivingEntity target) {
        Vec3 from = this.zombie.position();
        Vec3 to = target.position();
        double distance = from.distanceTo(to);
        if (distance < 1.0D || distance > SCAN_REACH) {
            return null;
        }
        Vec3 delta = to.subtract(from);
        int steps = Math.min(Mth.ceil(distance * 2.0D), MAX_SCAN_STEPS);
        BlockPos ownFeet = this.zombie.blockPosition();
        Level level = this.zombie.level();
        for (int i = 1; i <= steps; i++) {
            Vec3 sample = from.add(delta.scale((double) i / (double) steps));
            BlockPos base = BlockPos.containing(sample);
            BlockPos candidate = this.findBlockingBlock(level, base, ownFeet);
            if (candidate != null) {
                BlockState state = level.getBlockState(candidate);
                return this.canHarvest(state) ? candidate : null;
            }
        }
        return null;
    }

    /**
     * Returns the first blocking block among the given base position and the one above,
     * skipping the zombie's own feet/head cells and non-solid blocks (air, liquids,
     * shapes without collision).
     */
    @Nullable
    private BlockPos findBlockingBlock(Level level, BlockPos base, BlockPos ownFeet) {
        for (BlockPos pos : new BlockPos[]{base, base.above()}) {
            if (pos.equals(ownFeet) || pos.equals(ownFeet.above())) {
                continue;
            }
            BlockState state = level.getBlockState(pos);
            if (state.isAir() || state.liquid() || state.getCollisionShape(level, pos).isEmpty()) {
                continue;
            }
            return pos;
        }
        return null;
    }

    /**
     * Tool gate: blocks that require a correct tool for drops may only be dug when the
     * zombie's main-hand item can harvest them; blocks without such requirement are
     * always diggable.
     */
    private boolean canHarvest(BlockState state) {
        return !state.requiresCorrectToolForDrops()
                || this.zombie.getMainHandItem().isCorrectToolForDrops(state);
    }
}
