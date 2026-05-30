package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * MME 模组附魔标签定义。
 * 用于对附魔进行分组（如互斥附魔组）。
 */
public interface MMEEnchantmentTags {
    TagKey<Enchantment> EQUIPMENT_DROPS_EXCLUSIVE = key("equipment_drops_exclusive");

    private static TagKey<Enchantment> key(String id) {
        return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
