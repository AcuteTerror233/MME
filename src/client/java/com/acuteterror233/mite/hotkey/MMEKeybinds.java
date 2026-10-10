package com.acuteterror233.mite.hotkey;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

/**
 * Client keybinds for the MITE-style hotkey features. Currently registers the
 * "Zoom Camera" key (hold to pull the camera closer) and the "Place Source Liquid" key
 * (press to place a source of the held bucket's fluid for 100 XP).
 */
public final class MMEKeybinds {
    /** Hold-to-zoom key (default {@code C}, rebindable in the controls screen). */
    public static final KeyMapping ZOOM = KeyMappingHelper.registerKeyMapping(
            new KeyMapping("key.mme.zoom", InputConstants.Type.KEYBOARD, InputConstants.KEY_C, KeyMapping.Category.MISC));

    /** Place-source-liquid key (default {@code R}, rebindable in the controls screen). */
    public static final KeyMapping PLACE_SOURCE_LIQUID = KeyMappingHelper.registerKeyMapping(
            new KeyMapping("key.mme.place_source_liquid", InputConstants.Type.KEYBOARD, InputConstants.KEY_R, KeyMapping.Category.MISC));

    /** No-op hook that triggers static registration. */
    public static void init() {
    }

    /** @return whether the camera zoom should currently be applied (in game and key held). */
    public static boolean isZoomActive() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.player != null && minecraft.gui.screen() == null && ZOOM.isDown();
    }
}
