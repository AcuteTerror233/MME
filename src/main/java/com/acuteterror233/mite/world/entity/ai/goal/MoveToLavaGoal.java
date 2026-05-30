package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

/**
 * 移动到岩浆 AI 目标。
 * 使实体寻找并移动到岩浆方块。
 */
public class MoveToLavaGoal extends MoveToBlockGoal {
    private final PathfinderMob mob;
    public MoveToLavaGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier, 8, 2);
        this.mob = mob;
    }

    public @NotNull BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    public boolean canContinueToUse() {
        return !this.mob.isInLava() && this.isValidTarget(this.mob.level(), this.blockPos);
    }

    public boolean canUse() {
        return !this.mob.isInLava() && super.canUse();
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 20 == 0;
    }

    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos).is(Blocks.LAVA) && levelReader.getBlockState(blockPos.above()).isPathfindable(PathComputationType.LAND);
    }
}
