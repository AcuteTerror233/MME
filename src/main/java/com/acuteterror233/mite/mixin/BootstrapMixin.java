package com.acuteterror233.mite.mixin;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.event.BlockRegisterCallback;
import com.acuteterror233.mite.event.ItemRegisterCallback;
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
            if (At_mite.COUNT1_ITEM.contains(value)) {
                settings.maxCount(1);
                return ActionResult.SUCCESS;
            }else if (At_mite.COUNT8_ITEM.contains(value)) {
                settings.maxCount(8);
                return ActionResult.SUCCESS;
            }else if (At_mite.COUNT16_ITEM.contains(value)) {
                settings.maxCount(16);
                return ActionResult.SUCCESS;
            }else if (At_mite.COUNT32_ITEM.contains(value)) {
                settings.maxCount(32);
                return ActionResult.SUCCESS;
            }else {
                return ActionResult.PASS;
            }
        });
        BlockRegisterCallback.EVENT.register((registryKey, settings) ->{
            if (At_mite.COUNT1_BLOCK.contains(registryKey)) {
                settings.maxCount(1);
                return ActionResult.SUCCESS;
            }else if (At_mite.COUNT8_BLOCK.contains(registryKey)) {
                settings.maxCount(8);
                return ActionResult.SUCCESS;
            }else if (At_mite.COUNT16_BLOCK.contains(registryKey)) {
                settings.maxCount(16);
                return ActionResult.SUCCESS;
            } else if (At_mite.COUNT32_BLOCK.contains(registryKey)) {
                settings.maxCount(32);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        });
    }
}
