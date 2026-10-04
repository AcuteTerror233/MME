package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.interfaces.FluidDrainableExtension;
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

/**
 * Mixin for {@code PowderSnowBlock} — makes powder snow bucketable through the MME
 * {@link FluidDrainableExtension} duck interface.
 *
 * <p>Taking the fluid removes the block immediately (with break particles on the server) and
 * returns a "powder_snow_"-prefixed variant of the supplied bucket item, mirroring vanilla's
 * {@code BucketPickup} behavior. The fill sound is vanilla's powder-snow bucket sound. Executed on
 * the interacting side; the particle event is server-only.</p>
 */
@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin implements FluidDrainableExtension {
    /**
     * Duck-interface implementation: scoops the powder snow block into a bucket.
     *
     * @param drainer the entity taking the fluid, if any
     * @param world   the level containing the block
     * @param pos     the powder snow position
     * @param state   the powder snow block state
     * @param item    the (empty) bucket item used for pickup
     * @return the filled powder-snow bucket
     */
    @Override
    public ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item item) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        if (!world.isClientSide()) {
            world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
        }
        return new ItemStack(BuiltInRegistries.ITEM.getValue(BuiltInRegistries.ITEM.getKey(item).withPrefix("powder_snow_")));
    }

    /**
     * Duck-interface implementation: the sound played when powder snow is scooped into a bucket.
     *
     * @return the vanilla powder-snow bucket fill sound
     */
    @Override
    public Optional<SoundEvent> MME$GetBucketFillSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_POWDER_SNOW);
    }
}
