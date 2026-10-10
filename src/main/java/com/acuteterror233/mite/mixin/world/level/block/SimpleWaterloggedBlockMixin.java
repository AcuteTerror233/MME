package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.interfaces.FluidDrainableExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

/**
 * Mixin for {@code SimpleWaterloggedBlock} — lets any waterlogged block be drained through the MME
 * {@link FluidDrainableExtension} duck interface.
 *
 * <p>Scooping works on any waterlogged state and never consumes the fluid: the water stays in
 * the block (the {@code WATERLOGGED} flag is kept) and a water bucket is returned. Non-waterlogged
 * states yield an empty stack. The survival re-check of vanilla draining no longer applies
 * because the block state is left untouched.</p>
 */
@Mixin(SimpleWaterloggedBlock.class)
public interface SimpleWaterloggedBlockMixin extends FluidDrainableExtension {
    /**
     * Duck-interface implementation: scoops the water out of a waterlogged block state without
     * un-waterlogging it.
     *
     * @param drainer the entity taking the fluid, if any
     * @param world   the level containing the block
     * @param pos     the block position
     * @param state   the (possibly waterlogged) block state
     * @param bucket  the (empty) bucket item used for pickup
     * @return a water bucket when the block is waterlogged (which stays waterlogged), otherwise empty
     */
    @Override
    default ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item bucket) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            return new ItemStack(getFluidBucket(Fluids.WATER, bucket));
        } else {
            return ItemStack.EMPTY;
        }
    }

    /**
     * Duck-interface implementation: the sound played when water is scooped into a bucket.
     *
     * @return water's pickup sound, if defined
     */
    @Override
    default Optional<SoundEvent> MME$GetBucketFillSound() {
        return Fluids.WATER.getPickupSound();
    }
}