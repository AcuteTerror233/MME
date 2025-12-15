package com.acuteterror233.mite.mixin.item;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

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

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ToolMaterial;<init>(Lnet/minecraft/registry/tag/TagKey;IFFILnet/minecraft/registry/tag/TagKey;)V", ordinal = 1))
    private static void STONE(Args args) {
        args.setAll(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 0, 0F, 0F, 1, null);
    }
    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ToolMaterial;<init>(Lnet/minecraft/registry/tag/TagKey;IFFILnet/minecraft/registry/tag/TagKey;)V", ordinal = 3))
    private static void DIAMOND(Args args) {
        args.setAll(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 0, 0F, 0F, 1, null);
    }

//    @Overwrite
//    private Item.Settings applyBaseSettings(Item.Settings settings) {
//        return settings.repairable(this.repairItems).enchantable(this.enchantmentValue);
//    }

}
