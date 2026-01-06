package com.acuteterror233.mite.mixin.client.render;

import com.acuteterror233.mite.MME;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DimensionSpecialEffects.class)
public class DimensionSpecialEffectsMixin {
    @Final @Shadow private static Object2ObjectMap<ResourceLocation, DimensionSpecialEffects> EFFECTS;
    @Inject(method = "<clinit>",at = @At("TAIL"))
    private static void init(CallbackInfo ci){
        EFFECTS.put(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID,"underground"), new DimensionSpecialEffects.OverworldEffects());
    }
}
