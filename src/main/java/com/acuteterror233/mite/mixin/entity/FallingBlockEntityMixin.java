package com.acuteterror233.mite.mixin.entity;

import com.acuteterror233.mite.atinterface.FallingBlockEntityExtension;
import com.acuteterror233.mite.block.AnvilBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
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

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity implements FallingBlockEntityExtension {
    @Unique public BlockEntity BlockEntity;
    @Shadow private float fallHurtAmount;
    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    @Nullable
    public NbtCompound blockEntityData;
    @Override
    public void setBlockEntity(BlockEntity blockEntity) {
        this.BlockEntity = blockEntity;
    }
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Falling;onLanding(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/FallingBlockEntity;)V"))
    public void tick(CallbackInfo ci) {
        if (this.BlockEntity != null) {
            this.blockEntityData = this.BlockEntity.createNbt(this.getWorld().getRegistryManager());
        }
    }
    @Inject(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"))
    public void handleFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        int i = (int) (MathHelper.ceil(fallDistance - 1.0) * this.fallHurtAmount);
        ((AnvilBlockEntity)this.BlockEntity).setDamage(i);
    }
}