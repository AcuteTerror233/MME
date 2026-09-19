package com.acuteterror233.mite.mixin.world.item.crafting;

import com.acuteterror233.mite.MME;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.item.crafting.RecipeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(RecipeMap.class)
public class RecipeMapMixin {
    @ModifyExpressionValue(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/HolderLookup;listElements()Ljava/util/stream/Stream;"))
    private static <T> Stream<Holder.Reference<T>> listElements(Stream<Holder.Reference<T>> original){
        return original.filter(r -> !MME.FILTER_RECIPE_SET.contains(r.key().identifier()));
    }
}
