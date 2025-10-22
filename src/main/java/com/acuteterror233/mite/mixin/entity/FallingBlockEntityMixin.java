package com.acuteterror233.mite.mixin.entity;

import com.acuteterror233.mite.atinterface.FallingBlockEntityExtension;
import com.acuteterror233.mite.block.AnvilBlockEntity;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity implements FallingBlockEntityExtension {
    @Shadow
    public boolean dropItem;
    @Shadow
    @Nullable
    public NbtCompound blockEntityData;
    @Unique
    private int Damage;
    @Unique
    private int MaxDamage;
    @Shadow
    private float fallHurtAmount;
    @Shadow
    private BlockState blockState;

    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void setDamage(int damage, int maxDamage) {
        this.MaxDamage = maxDamage;
        this.Damage = damage;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkLoadingManager;sendToOtherNearbyPlayers(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/packet/Packet;)V"))
    public void tick(CallbackInfo ci) {
        if (blockState.getBlock() instanceof AnvilBlock && this.blockState.hasBlockEntity()) {
            BlockEntity blockEntity = this.getWorld().getBlockEntity(getBlockPos());
            if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity) {
                anvilBlockEntity.setMaxDamage(this.MaxDamage);
                anvilBlockEntity.setDamage(this.Damage);
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/FallingBlockEntity;dropItem(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;", shift = At.Shift.BEFORE), cancellable = true)
    public void ticka(CallbackInfo ci) {
        ItemStack stack = blockState.getBlock().asItem().getDefaultStack();
        stack.setDamage(this.Damage);
        this.dropStack((ServerWorld) getWorld(), stack);
        this.discard();
        ci.cancel();
    }

    @Inject(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"))
    public void handleFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        Predicate<Entity> predicate = EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(EntityPredicates.VALID_LIVING_ENTITY);
        List<Entity> list = this.getWorld().getOtherEntities(this, this.getBoundingBox(), predicate);
        if (!list.isEmpty()) {
            int i = (int) (MathHelper.ceil(fallDistance - 1.0) * this.fallHurtAmount);
            this.Damage = Math.clamp(this.Damage + i, 0, this.MaxDamage);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("MaxDamage", this.MaxDamage);
        nbt.putInt("Damage", this.Damage);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.MaxDamage = nbt.getInt("MaxDamage", 0);
        this.Damage = nbt.getInt("Damage", 0);
    }
}