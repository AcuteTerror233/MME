package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.interfaces.FluidDrainableExtension;
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

/**
 * Mixin for {@code BubbleColumnBlock} — lets bubble columns be scooped through the MME
 * {@link FluidDrainableExtension} duck interface.
 *
 * <p>Taking the fluid resolves the water bucket for the supplied empty bucket; only when a real
 * filled bucket results is the bubble column replaced with air. Executed on the interacting side
 * (real pickups happen server-side); the fill sound is water's pickup sound.</p>
 */
@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin implements FluidDrainableExtension {
    /**
     * Duck-interface implementation: scoops the bubble column into a water bucket.
     *
     * @param drainer the entity taking the fluid, if any
     * @param world   the level containing the block
     * @param pos     the bubble column position
     * @param state   the bubble column block state
     * @param bucket  the (empty) bucket item used for pickup
     * @return the filled water bucket; the column becomes air when a valid bucket was resolved
     */
    @Override
    public ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item bucket) {
        Item fluidBucket = getFluidBucket(Fluids.WATER, bucket);
        if (!fluidBucket.equals(bucket)){
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        return new ItemStack(fluidBucket);
    }

    /**
     * Duck-interface implementation: the sound played when the bubble column is scooped.
     *
     * @return water's pickup sound, if defined
     */
    @Override
    public Optional<SoundEvent> MME$GetBucketFillSound() {
        return Fluids.WATER.getPickupSound();
    }
}
