package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

/**
 * Move to lava AI goal.
 * Makes entity seek and move to lava blocks.
 */
public class MoveToLavaGoal extends MoveToBlockGoal {
    private final PathfinderMob mob;
    /** @param mob mob that wants to bathe in lava (e.g. fire elementals); searches 8 blocks out / 2 down. */
    public MoveToLavaGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier, 8, 2);
        this.mob = mob;
    }

    /** @return the currently targeted lava block position. */
    public @NotNull BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    /** Continues while the mob is not yet in lava and the target is still valid. */
    public boolean canContinueToUse() {
        return !this.mob.isInLava() && this.isValidTarget(this.mob.level(), this.blockPos);
    }

    /** Only starts when the mob is not already in lava. */
    public boolean canUse() {
        return !this.mob.isInLava() && super.canUse();
    }

    /** Recomputes the path every 20 ticks. */
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 20 == 0;
    }

    /** A valid target is lava whose surface block is walkable (so the mob can step in). */
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos).is(Blocks.LAVA) && levelReader.getBlockState(blockPos.above()).isPathfindable(PathComputationType.LAND);
    }
}
