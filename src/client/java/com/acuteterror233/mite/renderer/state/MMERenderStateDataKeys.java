package com.acuteterror233.mite.renderer.state;

import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

/**
 * Render-state data keys shared between MME renderers and mixins: values are written during
 * entity/level state extraction and read on the render thread.
 */
public class MMERenderStateDataKeys {
    /** Sky render-state key carrying the current {@link SpecialMoonPhase} extracted by {@code SkyRendererMixin}. */
    public static RenderStateDataKey<SpecialMoonPhase> SPECIAL_MOON = RenderStateDataKey.create(() -> "special_moon");
}
