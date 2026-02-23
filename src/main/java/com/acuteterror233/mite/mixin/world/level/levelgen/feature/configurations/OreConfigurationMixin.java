package com.acuteterror233.mite.mixin.world.level.levelgen.feature.configurations;

import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(OreConfiguration.class)
public class OreConfigurationMixin {
    @Mutable
    @Final
    @Shadow
    public float discardChanceOnAirExposure;
    @Inject(method = "<init>(Ljava/util/List;IF)V", at = @At("TAIL"))
    private void init(List<OreConfiguration.TargetBlockState> list, int i, float f, CallbackInfo ci) {
        if (f == 0) {
            this.discardChanceOnAirExposure = 0.5F;
        }
    }
}
