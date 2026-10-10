package com.acuteterror233.mite.mixin.client.player;

import com.acuteterror233.mite.world.effect.curse.MMECurses;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.context.PacketContextProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Client mixin for {@code LocalPlayer} — CANNOT_SPRINT carriers never start sprinting:
 * every vanilla sprint start path runs behind {@code canStartSprinting()}, so cancelling
 * it denies sprinting without packet flooding.
 */
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements PacketContextProvider {

    public LocalPlayerMixin(ClientLevel level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    /** Denies sprint start for CANNOT_SPRINT carriers. */
    @Inject(method = "canStartSprinting", at = @At("HEAD"), cancellable = true)
    private void mme$denySprint(CallbackInfoReturnable<Boolean> cir) {
        if (hasEffect(MMECurses.CANNOT_SPRINT)) {
            cir.setReturnValue(false);
        }
    }
}
