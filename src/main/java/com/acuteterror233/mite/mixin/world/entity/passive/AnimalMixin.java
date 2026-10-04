package com.acuteterror233.mite.mixin.world.entity.passive;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code Animal} — Suppresses the base experience reward of all animals:
 * killing any animal yields no experience.
 */
@Mixin(Animal.class)
public abstract class AnimalMixin {
    /** Forces {@code getBaseExperienceReward} to always return 0. */
    @Inject(method = "getBaseExperienceReward", at = @At("RETURN"), cancellable = true)
    protected void getExperienceToDrop(ServerLevel world, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
