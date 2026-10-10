package com.acuteterror233.mite.mixin.client.gui;

import com.acuteterror233.mite.hotkey.MMEAutoModes;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * While Auto Harvest / Auto Use mode is active, the operating item's hotbar slot gets a green
 * background tint and outline (matching the MITE visual cue that the slot is "automated").
 */
@Mixin(Hud.class)
public abstract class HudHotbarMixin {
    /** Translucent green background tint (ARGB). */
    @Unique
    private static final int MME$BACKGROUND = 0x4000FF00;
    /** Opaque green outline color (ARGB). */
    @Unique
    private static final int MME$OUTLINE = 0xFF00FF00;

    @Inject(method = "extractItemHotbar", at = @At("TAIL"))
    private void mme$highlightAutoModeSlot(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        int slot = MMEAutoModes.highlightSlot();
        if (slot < 0) {
            return;
        }
        // Same geometry as the vanilla selected-slot highlight: 24x23 frame around a 20px slot.
        int x = graphics.guiWidth() / 2 - 92 + slot * 20;
        int y = graphics.guiHeight() - 23;
        graphics.fill(x, y, x + 24, y + 23, MME$BACKGROUND);
        graphics.outline(x, y, 24, 23, MME$OUTLINE);
    }
}
