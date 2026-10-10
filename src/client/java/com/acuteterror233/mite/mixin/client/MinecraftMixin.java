package com.acuteterror233.mite.mixin.client;

import com.acuteterror233.mite.hotkey.MMEAutoModes;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Drives the AHM/AUM state machine every frame right before vanilla's keybind handling, so the
 * forced attack/use key states apply within the same frame and vanilla consumes any fresh
 * clicks normally (activating, cancelling or continuing manual play).
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void mme$autoModes(CallbackInfo ci) {
        MMEAutoModes.tick((Minecraft) (Object) this);
    }
}
