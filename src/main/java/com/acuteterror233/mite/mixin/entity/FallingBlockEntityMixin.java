package com.acuteterror233.mite.mixin.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    public boolean dropItem;
    @Shadow
    @Nullable
    public NbtCompound blockEntityData;
    @Shadow
    private float fallHurtAmount;
    @Shadow
    private BlockState blockState;

    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(method = "spawnFromBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", shift = At.Shift.BEFORE))
    private static void spawnFromBlock(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<FallingBlockEntity> cir, @Local FallingBlockEntity fallingBlockEntity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            NbtCompound nbt = blockEntity.createNbt(world.getRegistryManager());
            fallingBlockEntity.blockEntityData = nbt;
        }
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/FallingBlockEntity;dropItem(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"))
    public ItemEntity tick(FallingBlockEntity instance, ServerWorld serverWorld, ItemConvertible itemConvertible) {
        ItemStack stack = this.blockState.getBlock().asItem().getDefaultStack();
        if (stack.isDamageable() && this.blockEntityData != null) {
            stack.setDamage(this.blockEntityData.getInt("damage", 0));
        }
        return this.dropStack((ServerWorld) getWorld(), stack);
    }
}