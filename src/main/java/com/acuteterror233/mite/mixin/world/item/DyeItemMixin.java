package com.acuteterror233.mite.mixin.world.item;

import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DyeItem.class)
/**
 * Mixin for {@code DyeItem} — Modifies dye usage behavior.
 */
public abstract class DyeItemMixin {
    @Inject(method = "<init>", at = @At("HEAD"))
    private static void init(Item.Properties settings, CallbackInfo ci) {
        settings.stacksTo(16);
    }
}
