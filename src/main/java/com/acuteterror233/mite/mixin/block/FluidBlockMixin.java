package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.FluidDrainableExtension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(FluidBlock.class)
public class FluidBlockMixin implements FluidDrainableExtension {
    @Shadow
    @Final
    public static IntProperty LEVEL;
    @Shadow
    @Final
    protected FlowableFluid fluid;

    @Override
    public ItemStack newtryDrainFluid(@Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state, Item item) {
        if (state.get(LEVEL) == 0) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
            return new ItemStack(getItemStack(fluid, item));
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return this.fluid.getBucketFillSound();
    }
}
