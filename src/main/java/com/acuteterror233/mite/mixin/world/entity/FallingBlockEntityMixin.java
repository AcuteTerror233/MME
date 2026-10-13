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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code FallingBlockEntity} — Preserves block-entity data across the fall and
 * restores item damage when a falling block lands as an item.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code fall} injection (before {@code setBlockAndUpdate}): when the source block
 *       detaches, any block entity at the source position is snapshotted into
 *       {@code blockData}.</li>
 *   <li>{@code tick} redirect (on {@code spawnAtLocation}): when the falling block drops
 *       as an item instead of placing, damageable items get their damage value restored
 *       from {@code blockData}.</li>
 *   <li>{@code causeFallDamage} redirect (on {@code AnvilBlock#damage}): keeps the anvil
 *       block state unchanged, so falling anvils are never degraded by landing.</li>
 *   <li>{@code <init>} injection (TAIL): every falling block gets anvil-style fall damage at
 *       half the anvil height bonus ({@code 1.0F} per fallen block, cap 40); {@code AnvilBlock#falling}
 *       runs afterwards in {@code FallingBlock#tick} and overwrites anvils back to the vanilla
 *       {@code 2.0F} bonus, so anvils keep their full damage.</li>
 * </ul>
 */
@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow
    @Nullable
    public CompoundTag blockData;
    @Shadow
    private BlockState blockState;
    @Shadow
    public abstract void setHurtsEntities(float fallDamagePerDistance, int fallDamageMax);

    public FallingBlockEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    /**
     * Enables anvil-style fall damage for every falling block at half the anvil height bonus.
     * Runs at construction TAIL: {@code FallingBlock#tick} calls {@code Fallable#falling} after
     * the factory constructor, so {@code AnvilBlock#falling} overwrites anvils back to the
     * vanilla {@code 2.0F}/{@code 40} values while ordinary falling blocks keep this
     * {@code 1.0F}/{@code 40} setup.
     */
    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
    private void mme$enableFallDamage(EntityType<?> type, Level world, CallbackInfo ci) {
        this.setHurtsEntities(1.0F, 40);
    }
    // Snapshot the source block entity's NBT right before the falling block detaches
    @Inject(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", shift = At.Shift.BEFORE))
    private static void fall(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<FallingBlockEntity> cir, @Local FallingBlockEntity fallingBlockEntity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            fallingBlockEntity.blockData = blockEntity.saveWithoutMetadata(world.registryAccess());
        }
    }

    /** Replaces the item drop so damageable blocks keep their saved damage value. */
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    public ItemEntity tick(FallingBlockEntity instance, ServerLevel serverWorld, ItemLike itemConvertible) {
        ItemStack stack = this.blockState.getBlock().asItem().getDefaultInstance();
        if (stack.isDamageableItem() && this.blockData != null) {
            stack.setDamageValue(this.blockData.getIntOr("damage", 0));
        }
        return this.spawnAtLocation((ServerLevel) level(), stack);
    }
    /** Keeps the anvil block state unchanged so falling anvils take no landing damage. */
    @Redirect(method = "causeFallDamage",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/AnvilBlock;damage(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState causeFallDamage(BlockState blockstate) {
        return blockstate;
    }
}