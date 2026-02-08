package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.atinterface.FluidDrainableExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin implements FluidDrainableExtension {
    @Override
    public ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item bucket) {
        Item fluidBucket = getFluidBucket(Fluids.WATER, bucket);
        if (!fluidBucket.equals(bucket)){
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        return new ItemStack(fluidBucket);
    }

    @Override
    public Optional<SoundEvent> MME$GetBucketFillSound() {
        return Fluids.WATER.getPickupSound();
    }
}
