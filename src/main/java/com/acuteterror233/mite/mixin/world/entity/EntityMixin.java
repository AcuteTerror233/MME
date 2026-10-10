package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.world.effect.curse.CurseCap;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import com.acuteterror233.mite.world.effect.curse.CurseState;
import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code Entity} — caps the air supply of CANNOT_HOLD_BREATH carriers at
 * {@link CurseLogic#BREATH_CAP_FRACTION} of the normal maximum. Runs on both sides so the
 * drowning prediction stays consistent with the server.
 *
 * <p>The vanilla constructor calls this virtual method to define the air entity-data default,
 * long before the {@code LivingEntity} effect storage exists — so the check reads the duck
 * {@link CurseState} (null while constructing) instead of {@code hasEffect}.</p>
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

    /** Caps the maximum air supply for players carrying the breath curse. */
    @Inject(method = "getMaxAirSupply", at = @At("TAIL"), cancellable = true)
    private void mme$cappedBreath(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof Player player) {
            CurseState state = ((CurseCap) player).mme$curseState();
            if (state != null && state.hasCurse(MMECurses.CANNOT_HOLD_BREATH)) {
                cir.setReturnValue(Math.max(1, (int) (cir.getReturnValue() * CurseLogic.BREATH_CAP_FRACTION)));
            }
        }
    }
}
