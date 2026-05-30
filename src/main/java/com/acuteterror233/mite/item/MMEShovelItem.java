package com.acuteterror233.mite.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

/**
 * MME 锹物品，继承 {@link ShovelItem}。
 * 添加自定义锹行为（如铲地判定）。
 */
public class MMEShovelItem extends ShovelItem {
    public MMEShovelItem(Item.Properties settings) {
        super(null, 0, 0, settings);
    }

}
