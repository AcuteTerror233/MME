package com.acuteterror233.mite.mixin.world.entity.animal.equine;


import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code AbstractHorse.MountPanicGoal} — makes a horse panic whenever a player is
 * nearby.
 *
 * <p>{@code shouldPanic} returns {@code true} if a player is within 16 blocks, otherwise it
 * falls back to the vanilla {@code PanicGoal#shouldPanic} result, so a ridden mount stays
 * skittish.</p>
 */
@Mixin(AbstractHorse.MountPanicGoal.class)
public class MountPanicGoalMixin extends PanicGoal {

    public MountPanicGoalMixin(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    /** Forces panic when a player is within 16 blocks, else defers to the vanilla check. */
    @Inject(method = "shouldPanic", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/PanicGoal;shouldPanic()Z"), cancellable = true)
    public void shouldPanic(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(mob.level().getNearestPlayer(mob.getX(), mob.getY(), mob.getZ(), 16, true) != null || super.shouldPanic());
    }

}