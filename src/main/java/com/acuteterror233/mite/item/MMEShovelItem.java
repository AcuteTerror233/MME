package com.acuteterror233.mite.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

/**
 * MME shovel item, extending {@link ShovelItem}.
 * Adds custom shovel behavior (e.g., ground digging checks).
 */
public class MMEShovelItem extends ShovelItem {
    public MMEShovelItem(Item.Properties settings) {
        super(null, 0, 0, settings);
    }
}
