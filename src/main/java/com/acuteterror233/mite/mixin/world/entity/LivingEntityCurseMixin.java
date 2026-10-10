package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.world.effect.curse.CurseCap;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code LivingEntity} — witch-curse support.
 *
 * <p>The tail of {@code removeAllEffects} (the milk cure path) re-applies every curse
 * carried by a player, because curses never fade on their own. The tail of {@code die}
 * lifts all curses inflicted by a dying witch.</p>
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityCurseMixin {

    /** Curses survive effect clears (milk, potions, etc.) — they are re-applied from state. */
    @Inject(method = "removeAllEffects", at = @At("TAIL"))
    private void mme$reapplyCurses(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof CurseCap cap) {
            for (Holder<MobEffect> curse : cap.mme$curseState().curses()) {
                if (!self.hasEffect(curse)) {
                    self.addEffect(CurseLogic.hiddenInstance(curse));
                }
            }
        }
    }

    /** Killing a witch lifts every curse it inflicted. */
    @Inject(method = "die", at = @At("TAIL"))
    private void mme$witchDeathLiftsCurses(DamageSource source, CallbackInfo ci) {
        if ((Object) this instanceof Witch witch) {
            CurseLogic.onWitchDeath(witch);
        }
    }
}
