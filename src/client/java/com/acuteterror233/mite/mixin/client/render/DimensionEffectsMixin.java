package com.acuteterror233.mite.mixin.client.render;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.render.Underground;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DimensionEffects.class)
public class DimensionEffectsMixin {
    @Final @Shadow private static Object2ObjectMap<Identifier, DimensionEffects> BY_IDENTIFIER;
    @Inject(method = "<clinit>",at = @At("TAIL"))
    private static void init(CallbackInfo ci){
        BY_IDENTIFIER.put(Identifier.of(At_mite.MOD_ID,"underground"),new Underground());
    }
}
