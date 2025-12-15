package com.acuteterror233.mite.mixin.recipe;

import com.acuteterror233.mite.event.ServerRecipeManagerPrepareCallback;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.recipe.PreparedRecipes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerRecipeManager.class)
public class ServerRecipeManagerMixin {
    @Inject(method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Lnet/minecraft/recipe/PreparedRecipes;", at = @At("TAIL"), cancellable = true)
    private static void prepare(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<PreparedRecipes> cir, @Local List<RecipeEntry<?>> list) {
        ServerRecipeManagerPrepareCallback.EVENT.invoker().interact(list);
        cir.setReturnValue(PreparedRecipes.of(list));
    }
}
