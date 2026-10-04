package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * MME mod enchantment tag definitions.
 * Used for grouping enchantments (e.g., mutually exclusive enchantment sets).
 */
public interface MMEEnchantmentTags {
    /** Enchantments mutually exclusive with the equipment-drops enchantment family. */
    TagKey<Enchantment> EQUIPMENT_DROPS_EXCLUSIVE = key("equipment_drops_exclusive");

    /** Creates a {@link TagKey} in the enchantment registry under the MME namespace. */
    private static TagKey<Enchantment> key(String id) {
        return TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
