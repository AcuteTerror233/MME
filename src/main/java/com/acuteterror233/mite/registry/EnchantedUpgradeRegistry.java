package com.acuteterror233.mite.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public final class EnchantedUpgradeRegistry {
    private static final Map<Item, Item> UPGRADES = new HashMap<>();

    static {
        register(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
    }

    public static void register(Item input, Item output) {
        UPGRADES.put(input, output);
    }

    public static Item getUpgrade(ItemStack stack) {
        return UPGRADES.getOrDefault(stack.getItem(), null);
    }
}
