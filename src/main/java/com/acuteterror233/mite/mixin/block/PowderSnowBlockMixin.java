package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.FluidDrainableExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin implements FluidDrainableExtension {
    @Override
    public ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item item) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        if (!world.isClientSide()) {
            world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
        }
        return new ItemStack(BuiltInRegistries.ITEM.getValue(BuiltInRegistries.ITEM.getKey(item).withPrefix("powder_snow_")));
    }

    @Override
    public Optional<SoundEvent> MME$GetBucketFillSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_POWDER_SNOW);
    }
}
