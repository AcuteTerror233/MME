package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RemoveBlockGoal extends MoveToBlockGoal {
    private final Block blockToRemove;
    private final Mob removerMob;
    private int ticksSinceReachedGoal;

    public RemoveBlockGoal(Block block, PathfinderMob pathfinderMob, double d, int i) {
        super(pathfinderMob, d, 24, i);
        this.blockToRemove = block;
        this.removerMob = pathfinderMob;
    }

    @Override
    public boolean canUse() {
        if (!getServerLevel(this.removerMob).getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
        } else if (this.nextStartTick > 0) {
            this.nextStartTick--;
            return false;
        } else if (this.findNearestBlock()) {
            this.nextStartTick = reducedTickDelay(20);
            return true;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            return false;
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.removerMob.fallDistance = 1.0;
    }

    @Override
    public void start() {
        super.start();
        this.ticksSinceReachedGoal = 0;
    }

    public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
    }

    public void playBreakSound(Level level, BlockPos blockPos) {
    }

    @Override
    public void tick() {
        boolean reachedTarget = false;
        BlockPos blockPos = this.getMoveToTarget();
        if (!blockPos.closerToCenterThan(this.mob.position(), 3)) {
            this.tryTicks++;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, this.speedModifier);
            }
        } else {
            reachedTarget = true;
            this.tryTicks--;
        }
        Level level = this.removerMob.level();
        BlockPos blockPos1 = this.removerMob.blockPosition();
        BlockPos blockPos2 = this.getPosWithBlock(blockPos1.above(), level);
        RandomSource randomSource = this.removerMob.getRandom();
        if (reachedTarget && blockPos2 != null) {
            if (this.ticksSinceReachedGoal > 0) {
                Vec3 vec3 = this.removerMob.getDeltaMovement();
                this.removerMob.setDeltaMovement(vec3.x, 0.3, vec3.z);
                if (!level.isClientSide) {
                    ((ServerLevel)level)
                            .sendParticles(
                                    new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.DIRT)),
                                    blockPos2.getX() + 0.5,
                                    blockPos2.getY() + 0.7,
                                    blockPos2.getZ() + 0.5,
                                    3,
                                    (randomSource.nextFloat() - 0.5) * 0.08,
                                    (randomSource.nextFloat() - 0.5) * 0.08,
                                    (randomSource.nextFloat() - 0.5) * 0.08,
                                    0.15F
                            );
                }
            }

            if (this.ticksSinceReachedGoal % 2 == 0) {
                Vec3 vec3 = this.removerMob.getDeltaMovement();
                this.removerMob.setDeltaMovement(vec3.x, -0.3, vec3.z);
                if (this.ticksSinceReachedGoal % 6 == 0) {
                    this.playDestroyProgressSound(level, this.blockPos);
                }
            }

            if (this.ticksSinceReachedGoal > 60) {
                level.removeBlock(blockPos2, false);
                if (!level.isClientSide) {
                    for (int i = 0; i < 20; i++) {
                        double d = randomSource.nextGaussian() * 0.02;
                        double e = randomSource.nextGaussian() * 0.02;
                        double f = randomSource.nextGaussian() * 0.02;
                        ((ServerLevel)level).sendParticles(ParticleTypes.POOF, blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5, 1, d, e, f, 0.15F);
                    }

                    this.playBreakSound(level, blockPos2);
                }
            }

            this.ticksSinceReachedGoal++;
        }
    }

    @Nullable
    private BlockPos getPosWithBlock(BlockPos blockPos, BlockGetter blockGetter) {
        if (blockGetter.getBlockState(blockPos).is(this.blockToRemove)) {
            return blockPos;
        } else {
            BlockPos[] blockPoss = new BlockPos[]{blockPos.below(), blockPos.west(), blockPos.east(), blockPos.north(), blockPos.south(), blockPos.below().below()};

            for (BlockPos blockPos2 : blockPoss) {
                if (blockGetter.getBlockState(blockPos2).is(this.blockToRemove)) {
                    return blockPos2;
                }
            }

            return null;
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        ChunkAccess chunkAccess = levelReader.getChunk(
                SectionPos.blockToSectionCoord(blockPos.getX()), SectionPos.blockToSectionCoord(blockPos.getZ()), ChunkStatus.FULL, false
        );
        return chunkAccess != null && chunkAccess.getBlockState(blockPos).is(this.blockToRemove)
                && chunkAccess.getBlockState(blockPos.above()).isAir()
                && chunkAccess.getBlockState(blockPos.above(2)).isAir();
    }
}
