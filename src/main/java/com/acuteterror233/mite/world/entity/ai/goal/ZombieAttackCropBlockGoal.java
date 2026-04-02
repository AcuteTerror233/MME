package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public final class ZombieAttackCropBlockGoal{
    ZombieAttackCropBlockGoal(){

    }
    public static class Wheat extends RemoveBlockGoal {
        public Wheat(final PathfinderMob pathfinderMob, final double d, final int i) {
            super(Blocks.WHEAT, pathfinderMob, d, i);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
            levelAccessor.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }
    public static class Potatoes extends RemoveBlockGoal {
        public Potatoes(final PathfinderMob pathfinderMob, final double d, final int i) {
            super(Blocks.POTATOES, pathfinderMob, d, i);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
            levelAccessor.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }

    public static class Carrots extends RemoveBlockGoal {
        public Carrots(final PathfinderMob pathfinderMob, final double d, final int i) {
            super(Blocks.CARROTS, pathfinderMob, d, i);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
            levelAccessor.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }

    public static class Beetroots extends RemoveBlockGoal {
        public Beetroots(final PathfinderMob pathfinderMob, final double d, final int i) {
            super(Blocks.BEETROOTS, pathfinderMob, d, i);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
            levelAccessor.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }

    public static class MelonStem extends RemoveBlockGoal {
        public MelonStem(final PathfinderMob pathfinderMob, final double d, final int i) {
            super(Blocks.MELON_STEM, pathfinderMob, d, i);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
            levelAccessor.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }

    public static class PumpkinStem extends RemoveBlockGoal {
        public PumpkinStem(final PathfinderMob pathfinderMob, final double d, final int i) {
            super(Blocks.PUMPKIN_STEM, pathfinderMob, d, i);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
            levelAccessor.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }
}
