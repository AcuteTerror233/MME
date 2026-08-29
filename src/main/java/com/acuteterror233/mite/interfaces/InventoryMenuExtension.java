package com.acuteterror233.mite.interfaces;

/**
 * 物品栏菜单扩展接口。
 * 为玩家物品栏菜单提供额外功能入口。
 */
public interface InventoryMenuExtension {
    default boolean MME$IsAllowCrafting() {
        throw new AssertionError("Implemented in Mixin");
    }
    default double MME$GetCraftingTime() {
        throw new AssertionError("Implemented in Mixin");
    }
}
