package com.acuteterror233.mite.item;

import net.minecraft.world.item.AxeItem;

/**
 * MME 斧头物品，继承 {@link AxeItem}。
 * 添加自定义斧头行为（如剥皮判定）。
 */
public class MMEAxeItem extends AxeItem {
    public MMEAxeItem(Properties settings) {
        super(null, 0, 0, settings);
    }
}
