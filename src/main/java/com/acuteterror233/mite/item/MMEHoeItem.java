package com.acuteterror233.mite.item;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;

/**
 * MME 锄头物品，继承 {@link HoeItem}。
 * 添加自定义锄地行为。
 */
public class MMEHoeItem extends HoeItem{
    public MMEHoeItem(Item.Properties settings) {
        super(null, 0, 0, settings);
    }
}
