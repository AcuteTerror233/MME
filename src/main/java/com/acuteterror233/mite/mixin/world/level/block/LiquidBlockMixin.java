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
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

/**
 * Mixin for {@code LiquidBlock} — makes fluid source blocks bucketable through the MME
 * {@link FluidDrainableExtension} duck interface.
 *
 * <p>Vanilla {@code BucketItem} picks up fluids via {@code BucketPickup}; MME's unified
 * drainable path also covers liquid blocks: only a full source block (level 0) can be taken,
 * replacing it with air and returning the matching fluid bucket; flowing fluid returns empty.
 * The pickup sound comes from the underlying fluid. Executed on the interacting side (server for
 * real pickups); no world state changes occur for flowing fluids.</p>
 */
@Mixin(LiquidBlock.class)
public class LiquidBlockMixin implements FluidDrainableExtension {
    /** Shadowed vanilla fluid level property (0 = source block). */
    @Shadow
    @Final
    public static IntegerProperty LEVEL;
    /** Shadowed reference to the fluid this block renders/flows. */
    @Shadow
    @Final
    protected FlowingFluid fluid;

    /**
     * Duck-interface implementation: drains a source block at {@code pos} into a bucket.
     *
     * @param drainer the entity taking the fluid, if any
     * @param world   the level containing the fluid
     * @param pos     the fluid block position
     * @param state   the fluid block state
     * @param bucket  the (empty) bucket item used for pickup
     * @return a bucket of this fluid for a source block (which becomes air), otherwise empty
     */
    @Override
    public ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item bucket) {
        if (state.getValue(LEVEL) == 0) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            return new ItemStack(getFluidBucket(fluid, bucket));
        } else {
            return ItemStack.EMPTY;
        }
    }

    /**
     * Duck-interface implementation: the sound played when this fluid is scooped into a bucket.
     *
     * @return the fluid's pickup sound, if defined
     */
    @Override
    public Optional<SoundEvent> MME$GetBucketFillSound() {
        return this.fluid.getPickupSound();
    }
}
