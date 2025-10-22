package com.acuteterror233.mite.atinterface;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface FluidDrainableExtension {
    ItemStack newtryDrainFluid(@Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state, Item item);

    Optional<SoundEvent> getBucketFillSound();

    default Item getItemStack(FlowableFluid fluid, Item item) {
        if (fluid == Fluids.WATER) {
            return Registries.ITEM.get(Registries.ITEM.getId(item).withPrefixedPath("water_"));
        } else if (fluid == Fluids.LAVA) {
            return Registries.ITEM.get(Registries.ITEM.getId(item).withPrefixedPath("lava_"));
        } else {
            return item;
        }
    }

}
