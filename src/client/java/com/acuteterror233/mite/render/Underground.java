package com.acuteterror233.mite.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class Underground extends DimensionEffects {
    public Underground() {
        super(Float.NaN, true, DimensionEffects.SkyType.NONE, false, false);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color.multiply(sunHeight * 0.94F + 0.06F, sunHeight * 0.94F + 0.06F, sunHeight * 0.91F + 0.09F);
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }
}
