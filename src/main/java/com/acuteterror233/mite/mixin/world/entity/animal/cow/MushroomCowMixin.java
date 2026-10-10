package com.acuteterror233.mite.mixin.world.entity.animal.cow;

import com.acuteterror233.mite.world.entity.ai.sickness.AnimalSicknessLogic;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessCap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code MushroomCow} — refuses suspicious-stew drawing while sick. All other
 * sickness behaviour (needs, goals, drops) is inherited from the {@code AbstractCow}
 * mixin; the milk refusal likewise lives in that mixin's interaction handler.
 */
@Mixin(MushroomCow.class)
public abstract class MushroomCowMixin {
    /** A sick mooshroom gives no stew — the bowl interaction simply fails. */
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void mme$refuseStewWhileSick(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.getItemInHand(hand).is(Items.BOWL)
                && AnimalSicknessLogic.isSick((SicknessCap) (Object) this)) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
