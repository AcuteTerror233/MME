package com.acuteterror233.mite.mixin.world.item;

import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code DyeItem} — Modifies dye usage behavior.
 * Reduces the dye stack limit from the vanilla 64 to 16 (applies to every DyeItem instance).
 */
@Mixin(DyeItem.class)
public abstract class DyeItemMixin {
    /** Constructor hook: rewrites the stack size on the item properties before the item is built. */
    @Inject(method = "<init>", at = @At("HEAD"))
    private static void init(Item.Properties settings, CallbackInfo ci) {
        settings.stacksTo(16);
    }
}
