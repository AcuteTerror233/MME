package com.acuteterror233.mite.mixin.item;

import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Inject(method = "<init>", at = @At("HEAD"))
    private static void init(DyeColor color, Item.Settings settings, CallbackInfo ci) {
        settings.maxCount(16);
    }
}
