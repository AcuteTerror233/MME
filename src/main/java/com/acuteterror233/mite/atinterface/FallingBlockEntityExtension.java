package com.acuteterror233.mite.atinterface;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FallingBlockEntityExtension {

    static FallingBlockEntity spawnFromBlock(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        ((FallingBlockEntityExtension)fallingBlockEntity).setBlockEntity(blockEntity);
        return fallingBlockEntity;
    }
    default void setBlockEntity(BlockEntity blockEntity){}
}
