package com.acuteterror233.mite.mixin.world.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow
    @Nullable
    public CompoundTag blockData;
    @Shadow
    private BlockState blockState;

    public FallingBlockEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }
    @Inject(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", shift = At.Shift.BEFORE))
    private static void fall(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<FallingBlockEntity> cir, @Local FallingBlockEntity fallingBlockEntity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            fallingBlockEntity.blockData = blockEntity.saveWithoutMetadata(world.registryAccess());
        }
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    public ItemEntity tick(FallingBlockEntity instance, ServerLevel serverWorld, ItemLike itemConvertible) {
        ItemStack stack = this.blockState.getBlock().asItem().getDefaultInstance();
        if (stack.isDamageableItem() && this.blockData != null) {
            stack.setDamageValue(this.blockData.getIntOr("damage", 0));
        }
        return this.spawnAtLocation((ServerLevel) level(), stack);
    }
    @Redirect(method = "causeFallDamage",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/AnvilBlock;damage(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState causeFallDamage(BlockState blockstate) {
        return blockstate;
    }
}