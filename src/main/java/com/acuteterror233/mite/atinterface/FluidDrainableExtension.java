package com.acuteterror233.mite.atinterface;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface FluidDrainableExtension {
    ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item item);

    Optional<SoundEvent> MME$GetBucketFillSound();

    default Item getFluidBucket(FlowingFluid fluid, Item bucket) {
        if (fluid == Fluids.WATER) {
            return BuiltInRegistries.ITEM.getValue(BuiltInRegistries.ITEM.getKey(bucket).withPrefix("water_"));
        } else if (fluid == Fluids.LAVA) {
            return BuiltInRegistries.ITEM.getValue(BuiltInRegistries.ITEM.getKey(bucket).withPrefix("lava_"));
        } else {
            return bucket;
        }
    }

}
