package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.atinterface.ToolMaterialExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.*;

@Mixin(ToolMaterial.class)
public abstract class MixinToolMaterial {

    @Shadow TagKey<Item> repairItems;
    @Shadow int enchantmentValue;
    @Shadow int durability;
    @Overwrite
    private Item.Settings applyBaseSettings(Item.Settings settings) {
        return settings.repairable(this.repairItems).enchantable(this.enchantmentValue);
    }
}
