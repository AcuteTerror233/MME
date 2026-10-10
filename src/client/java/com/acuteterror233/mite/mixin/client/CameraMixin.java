package com.acuteterror233.mite.mixin.client;

import com.acuteterror233.mite.hotkey.MMEKeybinds;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hold-to-zoom camera: after vanilla's FOV tick finishes, the smoothed zoom factor (0 → 1)
 * scales the FOV down to at most 40% of normal, pulling the camera closer. While zoomed,
 * vanilla's cinematic camera (mouse smoothing) is force-enabled and restored on release.
 */
@Mixin(Camera.class)
public abstract class CameraMixin {
    /** Zoom scales the effective FOV down to 40% when fully engaged. */
    @Unique
    private static final float MME$MIN_FOV_SCALE = 0.4F;
    /** Per-tick interpolation factor of the zoom engagement (0 = disengaged, 1 = fully zoomed). */
    @Unique
    private static final float MME$ZOOM_EASE = 0.45F;

    @Shadow
    private float fovModifier;
    @Shadow
    private float fov;
    @Shadow
    private float calculateFov(float f) {
        throw new AssertionError("Shadowed method body");
    }

    @Unique
    private float mme$zoomProgress;
    /** Whether the zoom was engaged on the previous frame (for cinematic-camera switching). */
    @Unique
    private boolean mme$zoomEngaged;
    /** Vanilla {@code options.smoothCamera} value backed up while the zoom forces it on. */
    @Unique
    private boolean mme$vanillaSmoothCamera;

    @Inject(method = "tickFov", at = @At("TAIL"))
    private void mme$zoomFov(CallbackInfo ci) {
        boolean active = MMEKeybinds.isZoomActive();
        // Cinematic camera: force-enable vanilla mouse smoothing while zoomed, restore on release.
        if (active && !this.mme$zoomEngaged) {
            this.mme$vanillaSmoothCamera = Minecraft.getInstance().options.smoothCamera;
            Minecraft.getInstance().options.smoothCamera = true;
        } else if (!active && this.mme$zoomEngaged) {
            Minecraft.getInstance().options.smoothCamera = this.mme$vanillaSmoothCamera;
        }
        this.mme$zoomEngaged = active;

        float target = active ? 1.0F : 0.0F;
        this.mme$zoomProgress += (target - this.mme$zoomProgress) * MME$ZOOM_EASE;
        if (this.mme$zoomProgress < 1.0E-3F) {
            return;
        }
        float scale = 1.0F - (1.0F - MME$MIN_FOV_SCALE) * Math.min(1.0F, this.mme$zoomProgress);
        this.fovModifier *= scale;
        this.fov = this.calculateFov(this.fovModifier);
    }
}
