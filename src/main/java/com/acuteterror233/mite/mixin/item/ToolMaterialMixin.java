package com.acuteterror233.mite.mixin.item;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ToolMaterial.class)
public abstract class ToolMaterialMixin {

    @Final
    @Shadow
    private TagKey<Item> repairItems;
    @Final
    @Shadow
    private int enchantmentValue;
    @Final
    @Shadow
    private float attackDamageBonus;

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ToolMaterial;<init>(Lnet/minecraft/registry/tag/TagKey;IFFILnet/minecraft/registry/tag/TagKey;)V"), index = 1)
    private static int ModificationMaterials(int par2) {
        return switch (par2) {
            case 16 -> 8;
            case 250 -> 24;
            case 2031 -> 1024;
            case 59, 1561, 131 -> 0;
            default -> par2;
        };
    }

    @Overwrite
    private Item.Settings applyBaseSettings(Item.Settings settings) {
        return settings.repairable(this.repairItems).enchantable(this.enchantmentValue);
    }

}
