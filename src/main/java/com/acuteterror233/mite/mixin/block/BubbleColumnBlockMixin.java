package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.FluidDrainableExtension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin implements FluidDrainableExtension {
    @Override
    public ItemStack newtryDrainFluid(@Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state, Item item) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
        return new ItemStack(getItemStack(Fluids.WATER, item));
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Fluids.WATER.getBucketFillSound();
    }
}
