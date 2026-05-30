package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/**
 * 破坏火把 AI 目标。
 * 继承 {@link RemoveBlockGoal}，专门移除火把方块。
 */
public class DestroyTorchGoal extends RemoveBlockGoal {
    public DestroyTorchGoal(PathfinderMob mob, double speedModifier) {
        super(Blocks.TORCH, mob, speedModifier, 15);
    }

    @Override
    public void playDestroyProgressSound(LevelAccessor levelAccessor, BlockPos blockPos) {
        levelAccessor.playSound(null, blockPos, SoundEvents.STONE_HIT, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
    }

    @Override
    public void playBreakSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        ChunkAccess chunk = levelReader.getChunk(
                SectionPos.blockToSectionCoord(blockPos.getX()),
                SectionPos.blockToSectionCoord(blockPos.getZ()),
                ChunkStatus.FULL, false
        );
        return chunk != null && isTorch(chunk.getBlockState(blockPos))
                && chunk.getBlockState(blockPos.above()).isAir()
                && chunk.getBlockState(blockPos.above(2)).isAir();
    }

    @Override
    protected BlockPos getPosWithBlock(BlockPos blockPos, BlockGetter blockGetter) {
        if (isTorch(blockGetter.getBlockState(blockPos))) {
            return blockPos;
        }
        BlockPos[] offsets = {blockPos.below(), blockPos.west(), blockPos.east(), blockPos.north(), blockPos.south(), blockPos.below().below()};
        for (BlockPos offset : offsets) {
            if (isTorch(blockGetter.getBlockState(offset))) {
                return offset;
            }
        }
        return null;
    }

    private static boolean isTorch(BlockState state) {
        return state.is(Blocks.TORCH)
                || state.is(Blocks.WALL_TORCH)
                || state.is(Blocks.SOUL_TORCH)
                || state.is(Blocks.SOUL_WALL_TORCH);
    }
}
