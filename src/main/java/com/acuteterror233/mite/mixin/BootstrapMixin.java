package com.acuteterror233.mite.mixin;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.event.BlockRegisterCallback;
import com.acuteterror233.mite.event.ItemRegisterCallback;
import com.acuteterror233.mite.event.ServerRecipeManagerPrepareCallback;
import net.minecraft.Bootstrap;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
    @Inject(method = "initialize",at = @At("HEAD"))
    private static void initialize(CallbackInfo ci) {
        ItemRegisterCallback.EVENT.register((registryKey, settings)->{
            Identifier value = registryKey.getValue();
            Integer stackLimit = At_mite.ITEM_STACK_LIMITS.get(value);
            if (stackLimit != null) {
                settings.maxCount(stackLimit);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
        BlockRegisterCallback.EVENT.register((registryKey, settings) ->{
            Integer stackLimit = At_mite.BLOCK_STACK_LIMITS.get(registryKey);
            if (stackLimit != null) {
                settings.maxCount(stackLimit);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
        ServerRecipeManagerPrepareCallback.EVENT.register((list) -> {
            list.removeIf(recipeEntry -> At_mite.FILTER_RECIPE_SET.contains(recipeEntry.id().getValue()));
            return ActionResult.SUCCESS;
        });
    }
}
