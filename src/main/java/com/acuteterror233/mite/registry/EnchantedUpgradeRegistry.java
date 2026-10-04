package com.acuteterror233.mite.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry mapping items to their "enchanted upgrade" counterparts, e.g.
 * golden apple → enchanted golden apple. Queried by {@code EnchantmentMenuMixin} so the
 * enchanting table accepts and applies these upgrades instead of ordinary enchantments.
 */
public final class EnchantedUpgradeRegistry {
    /** Registered input → upgrade target mapping, populated in the static initializer. */
    private static final Map<Item, Item> UPGRADES = new HashMap<>();

    static {
        register(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
    }

    /**
     * Registers {@code output} as the enchanted upgrade of {@code input}.
     *
     * @param input  item that can be upgraded
     * @param output item it upgrades into
     */
    public static void register(Item input, Item output) {
        UPGRADES.put(input, output);
    }

    /**
     * Looks up the enchanted upgrade target of the stack's item.
     *
     * @param stack stack whose item is looked up
     * @return the upgrade target, or {@code null} when the item has no upgrade
     */
    public static Item getUpgrade(ItemStack stack) {
        return UPGRADES.getOrDefault(stack.getItem(), null);
    }
}
