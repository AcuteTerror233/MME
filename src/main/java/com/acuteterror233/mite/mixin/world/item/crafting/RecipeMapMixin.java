package com.acuteterror233.mite.mixin.world.item.crafting;

import com.acuteterror233.mite.MME;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.item.crafting.RecipeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/**
 * Mixin for {@code RecipeMap} — Excludes MME-blacklisted recipes from loading.
 * When the vanilla recipe loader streams all recipe holders into a new {@code RecipeMap}, any id in
 * {@code MME.FILTER_RECIPE_SET} is filtered out so those recipes never exist in-game (vanilla
 * recipes replaced by MME systems are removed this way).
 */
@Mixin(RecipeMap.class)
public class RecipeMapMixin {
    /** Filters the streamed recipe holders down to ids not present in the MME filter set. */
    @ModifyExpressionValue(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/HolderLookup;listElements()Ljava/util/stream/Stream;"))
    private static <T> Stream<Holder.Reference<T>> listElements(Stream<Holder.Reference<T>> original){
        return original.filter(r -> !MME.FILTER_RECIPE_SET.contains(r.key().identifier()));
    }
}
