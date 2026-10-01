package com.acuteterror233.mite.renderer.state;

import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class MMERenderStateDataKeys {
    public static RenderStateDataKey<SpecialMoonPhase> SPECIAL_MOON = RenderStateDataKey.create(() -> "special_moon");
}
