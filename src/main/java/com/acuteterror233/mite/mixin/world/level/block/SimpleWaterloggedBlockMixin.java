package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.interfaces.FluidDrainableExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
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
 * <p>When the block state is waterlogged, taking the fluid clears the {@code WATERLOGGED} flag and
 * returns a water bucket; if the de-waterlogged state can no longer survive (e.g. kelp), the block
 * breaks with drops. Non-waterlogged states yield an empty stack. Executed on the interacting
 * side (real pickups happen server-side); the survival re-check keeps host blocks consistent.</p>
 */
@Mixin(SimpleWaterloggedBlock.class)
public interface SimpleWaterloggedBlockMixin extends FluidDrainableExtension {
    /**
     * Duck-interface implementation: drains the water out of a waterlogged block state.
     *
     * @param drainer the entity taking the fluid, if any
     * @param world   the level containing the block
     * @param pos     the block position
     * @param state   the (possibly waterlogged) block state
     * @param bucket  the (empty) bucket item used for pickup
     * @return a water bucket when the block was waterlogged (becoming unwaterlogged), otherwise empty
     */
    @Override
    default ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item bucket) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            world.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), Block.UPDATE_ALL);
            if (!state.canSurvive(world, pos)) {
                world.destroyBlock(pos, true);
            }
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