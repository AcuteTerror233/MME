package com.acuteterror233.mite.mixin.world.level.storage.loot.providers.number.ints;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ContextIntProviders.class)
public class ContextIntProvidersMixin {
    @Redirect(method = "bootstrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/worldgen/BootstrapContext;register(Lnet/minecraft/resources/ResourceKey;Ljava/lang/Object;)Lnet/minecraft/core/Holder$Reference;"))
    private static <T> Holder.Reference<T> bootstrap(BootstrapContext<T> boot, ResourceKey<T> key, T t) {
        return boot.register(key, t);
    }
}
