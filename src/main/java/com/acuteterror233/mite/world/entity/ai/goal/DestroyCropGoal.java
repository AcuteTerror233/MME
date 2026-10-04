package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Destroy crop AI goal.
 * Makes the mob seek out crop blocks ({@link CropBlock}, any growth stage) and trample them:
 * the mob jumps on the crop for ~3 seconds (hop particles/sounds), then removes the block.
 */
public class DestroyCropGoal extends MoveToBlockGoal {
    private final Mob removerMob;
    private int ticksSinceReachedGoal;

    /**
     * @param mob   mob that will trample the crops
     * @param speed movement speed modifier while pathing to the crop
     * @param range horizontal search radius (blocks) for valid crops
     */
    public DestroyCropGoal(PathfinderMob mob, double speed, int range) {
        super(mob, speed, 24, range);
        this.removerMob = mob;
    }

    /** Only runs when {@code mobGriefing} is enabled, and only after the cooldown expires. */
    @Override
    public boolean canUse() {
        if (!getServerLevel(this.removerMob).getGameRules().get(GameRules.MOB_GRIEFING)) {
            return false;
        }
        if (this.nextStartTick > 0) {
            this.nextStartTick--;
            return false;
        }
        if (this.findNearestBlock()) {
            this.nextStartTick = reducedTickDelay(20);
            return true;
        }
        this.nextStartTick = this.nextStartTick(this.mob);
        return false;
    }

    /** Stops the goal and resets the mob's fall distance to 1.0 so the landing hop does not accumulate. */
    @Override
    public void stop() {
        super.stop();
        this.removerMob.fallDistance = 1.0F;
    }

    @Override
    public void start() {
        super.start();
        this.ticksSinceReachedGoal = 0;
    }

    private void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
        levelAccessor.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.HOSTILE, 0.5F, 0.9F + removerMob.getRandom().nextFloat() * 0.2F);
    }

    private void playBreakSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.getRandom().nextFloat() * 0.2F);
    }

    @Override
    public double acceptedDistance() {
        return 2;
    }

    /** While trampling: hop on the crop (alternating vertical impulses), emit dirt particles and sounds, then remove the crop after 60+ ticks. */
    @Override
    public void tick() {
        BlockPos blockPos = this.getMoveToTarget();
        if (!blockPos.closerToCenterThan(this.mob.position(), this.acceptedDistance())) {
            this.tryTicks++;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, this.speedModifier);
            }
        } else {
            this.tryTicks--;
            Level level = this.removerMob.level();
            BlockPos blockPos1 = this.removerMob.blockPosition();
            BlockPos blockPos2 = this.getCropPos(blockPos1.above(), level);
            RandomSource randomSource = this.removerMob.getRandom();

            if (blockPos2 != null) {
                if (this.ticksSinceReachedGoal > 0) {
                    Vec3 vec3 = this.removerMob.getDeltaMovement();
                    this.removerMob.setDeltaMovement(vec3.x, 0.3, vec3.z);
                    if (!level.isClientSide()) {
                        ((ServerLevel) level)
                                .sendParticles(
                                        new ItemParticleOption(ParticleTypes.ITEM, Blocks.DIRT.asItem()),
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
                    if (!level.isClientSide()) {
                        for (int i = 0; i < 20; i++) {
                            double d = randomSource.nextGaussian() * 0.02;
                            double e = randomSource.nextGaussian() * 0.02;
                            double f = randomSource.nextGaussian() * 0.02;
                            ((ServerLevel) level).sendParticles(ParticleTypes.POOF, blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5, 1, d, e, f, 0.15F);
                        }
                        this.playBreakSound(level, blockPos2);
                    }
                }

                this.ticksSinceReachedGoal++;
            }
        }
    }

    /** @return the position of a crop at/around {@code blockPos} (self, 4 horizontal neighbors, or 2 below), or {@code null}. */
    @Nullable
    private BlockPos getCropPos(BlockPos blockPos, BlockGetter blockGetter) {
        if (blockGetter.getBlockState(blockPos).getBlock() instanceof CropBlock) {
            return blockPos;
        }
        BlockPos[] neighbors = {blockPos.below(), blockPos.west(), blockPos.east(), blockPos.north(), blockPos.south(), blockPos.below().below()};
        for (BlockPos neighbor : neighbors) {
            if (blockGetter.getBlockState(neighbor).getBlock() instanceof CropBlock) {
                return neighbor;
            }
        }
        return null;
    }

    /** A crop counts as a valid target when loaded and with 2 blocks of clear air above it. */
    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        ChunkAccess chunkAccess = levelReader.getChunk(
                SectionPos.blockToSectionCoord(blockPos.getX()),
                SectionPos.blockToSectionCoord(blockPos.getZ()),
                ChunkStatus.FULL,
                false
        );
        if (chunkAccess == null) {
            return false;
        }
        return chunkAccess.getBlockState(blockPos).getBlock() instanceof CropBlock
                && chunkAccess.getBlockState(blockPos.above()).isAir()
                && chunkAccess.getBlockState(blockPos.above(2)).isAir();
    }
}