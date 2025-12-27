package com.acuteterror233.mite.mixin.entity.passive;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin {
    @Inject(method = "getExperienceToDrop", at = @At("RETURN"), cancellable = true)
    protected void getExperienceToDrop(ServerWorld world, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
