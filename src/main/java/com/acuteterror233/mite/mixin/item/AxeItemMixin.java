package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.item.AtItems;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item$Settings;axe(Lnet/minecraft/item/ToolMaterial;FF)Lnet/minecraft/item/Item$Settings;"))
    private static Item.Settings redirectConstructor(Item.Settings settings, ToolMaterial material, float attackDamage, float attackSpeed) {
        return settings;
    }
}