package com.acuteterror233.mite.world.entity.ai.pathing;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;

import java.util.List;

/**
 * Per-mob digging context shared by one path-planning session: the tick budget and the
 * cost multiplier that converts a block's dig time into "equivalent flat ground cells".
 *
 * <p>The cost model (docs/MME-僵尸挖掘寻路改造方案.md §6):</p>
 * <pre>
 * digCost(pos) = digTicks(pos) / {@value #MINING_COST_UNIT_TICKS}   // unit: flat cells
 *              x difficultyFactor(easy 0.8 / normal 1.0 / hard 1.2)
 *              x moonFactor(full moon 0.8 / otherwise 1.0 / new moon 1.2)
 * </pre>
 *
 * <p>The factor is frozen at construction (difficulty and moon phase do not change during a
 * single path search), while {@link #digSpentTicks} accumulates only when the executor
 * actually destroys a block, so the budget converges on real progress rather than on
 * planning estimates.</p>
 */
public final class DigSession {
    /** Ticks per "equivalent flat cell": one dirt block bare-handed is 1500 t = 12.5 cells. */
    private static final float MINING_COST_UNIT_TICKS = 120.0F;
    /** Session dig budget (ticks). 12000 t is roughly eight bare-handed dirt blocks. */
    private static final int DEFAULT_DIG_BUDGET_TICKS = 12000;
    private static final float FULL_MOON_BRIGHTNESS = 0.9F;
    private static final float NEW_MOON_BRIGHTNESS = 0.1F;

    private final ServerLevel level;
    private final Mob mob;
    private final float costFactor;
    private final int digBudgetTicks;
    private int digSpentTicks;
    /** Game time until which dig planning is refused after a route turned out to be unreachable. */
    private long cooldownUntilGameTime = Long.MIN_VALUE;

    public DigSession(ServerLevel level, Mob mob) {
        this.level = level;
        this.mob = mob;
        this.costFactor = difficultyFactor(level.getDifficulty())
                * moonFactor(level.getMoonBrightness(mob.blockPosition()));
        this.digBudgetTicks = DEFAULT_DIG_BUDGET_TICKS;
    }

    /**
     * Starts a planning round, re-arming the dig budget.
     *
     * <p>The session is created once per navigation — {@code createPathFinder} runs a single time
     * from the {@code PathNavigation} constructor — so the budget spans the mob's whole life unless
     * it is reset here. Without this call a zombie that had dug its 12000 ticks (eight bare-handed
     * dirt blocks) once would silently never plan a tunnel again.</p>
     */
    public void beginSearch() {
        this.digSpentTicks = 0;
    }

    /**
     * @return whether this search may plan a dig pass: out of the give-up cooldown and within the
     *         per-search budget
     */
    public boolean canDig() {
        return this.level.getGameTime() >= this.cooldownUntilGameTime
                && this.digSpentTicks < this.digBudgetTicks;
    }

    /**
     * Refuses dig planning for a while after the executor gave up on an unreachable route, so the
     * next search does not immediately rebuild the same tunnel.
     *
     * @param ticks cooldown length in ticks
     */
    public void suspendDigging(int ticks) {
        this.cooldownUntilGameTime = this.level.getGameTime() + ticks;
    }

    /** Dig time of a single block, converted to equivalent flat cells (with cost factor). */
    public float digCostUnits(int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        int ticks = DigToolPolicy.digTicks(this.level, pos, this.level.getBlockState(pos),
                this.mob.getMainHandItem());
        return ticks * this.costFactor / MINING_COST_UNIT_TICKS;
    }

    /**
     * The whole dig budget expressed in equivalent flat cells: the walk-range headroom a dig
     * pass must add on top of the mob's follow range. Vanilla A* prunes neighbors once
     * their {@code walkedDistance} reaches {@code maxRange}, and {@code DigPathFinder}'s
     * edge weight folds dig costs into that same walked distance, so without this headroom
     * any plan whose dig cost plus walking exceeds the follow range would be silently
     * truncated (a three-dirt pillar alone already costs 37.5 cells > 35).
     */
    public float digBudgetCostUnits() {
        return this.digBudgetTicks * this.costFactor / MINING_COST_UNIT_TICKS;
    }

    /** Raw dig ticks of a block set, without the cost factor (for plan display / verification). */
    public int sumDigTicks(List<BlockPos> blocks) {
        int sum = 0;
        for (BlockPos pos : blocks) {
            sum += DigToolPolicy.digTicks(this.level, pos, this.level.getBlockState(pos),
                    this.mob.getMainHandItem());
        }
        return sum;
    }

    /** Called by the executor after a block is actually removed, so the budget converges. */
    public void onDug(int ticks) {
        this.digSpentTicks += ticks;
    }

    private static float difficultyFactor(Difficulty difficulty) {
        return switch (difficulty) {
            case PEACEFUL, EASY -> 0.8F;
            case NORMAL -> 1.0F;
            case HARD -> 1.2F;
        };
    }

    private static float moonFactor(float moonBrightness) {
        if (moonBrightness >= FULL_MOON_BRIGHTNESS) {
            return 0.8F;
        }
        if (moonBrightness <= NEW_MOON_BRIGHTNESS) {
            return 1.2F;
        }
        return 1.0F;
    }
}
