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
 * 可排液扩展接口。
 * 允许方块实体在特定条件下被桶类物品排出液体。
 */
public interface FluidDrainableExtension {
    default ItemStack MME$TakeFluid(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, Item item){
        throw new AssertionError("Implemented in Mixin");
    }

    default Optional<SoundEvent> MME$GetBucketFillSound(){
        throw new AssertionError("Implemented in Mixin");
    }

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
