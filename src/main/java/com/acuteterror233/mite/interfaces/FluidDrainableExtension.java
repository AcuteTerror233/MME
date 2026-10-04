package com.acuteterror233.mite.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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

/**
 * Fluid drainable extension interface (duck interface).
 * Lets fluid-holding blocks be drained by bucket items and return the matching MME bucket variant.
 * Implemented by {@code LiquidBlockMixin} / {@code BubbleColumnBlockMixin} / {@code PowderSnowBlockMixin};
 * injected into the vanilla hosts {@code LiquidBlock}, {@code BubbleColumnBlock}, {@code PowderSnowBlock}
 * and {@code SimpleWaterloggedBlock} via classTweaker {@code transitive-inject-interface}.
 */
public interface FluidDrainableExtension {
    /**
     * Picks up the fluid block at {@code pos} into a bucket.
     *
     * @param drainer the entity doing the draining, may be {@code null}
     * @param world   world containing the block
     * @param pos     position of the fluid block
     * @param state   current state of the fluid block
     * @param item    the empty bucket item used for pickup
     * @return the filled MME bucket stack when the fluid was taken, otherwise an empty stack
     */
    default ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item item){
        throw new AssertionError("Implemented in Mixin");
    }

    /** @return the sound played when this fluid is collected into a bucket; empty if the fluid has none. */
    default Optional<SoundEvent> MME$GetBucketFillSound(){
        throw new AssertionError("Implemented in Mixin");
    }

    /**
     * Resolves the MME bucket variant for the given fluid within the bucket's own namespace.
     *
     * @param fluid  fluid being collected (only water and lava have dedicated variants)
     * @param bucket the empty bucket item
     * @return the item from the same namespace with {@code _bucket} replaced by {@code _water_bucket}
     *         or {@code _lava_bucket}; the original bucket for any other fluid
     */
    default Item getFluidBucket(FlowingFluid fluid, Item bucket) {
        String namespace = BuiltInRegistries.ITEM.getKey(bucket).getNamespace();
        if (fluid == Fluids.WATER) {
            return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(namespace, BuiltInRegistries.ITEM.getKey(bucket).getPath().replace("_bucket", "_water_bucket")));
        } else if (fluid == Fluids.LAVA) {
            return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(namespace, BuiltInRegistries.ITEM.getKey(bucket).getPath().replace("_bucket", "_lava_bucket")));
        } else {
            return bucket;
        }
    }

}
