package com.acuteterror233.mite.interfaces;

/**
 * Inventory menu extension interface.
 * Provides additional functionality entry points for the player inventory menu.
 */
public interface InventoryMenuExtension {
    default boolean MME$IsAllowCrafting() {
        throw new AssertionError("Implemented in Mixin");
    }
    default double MME$GetCraftingTime() {
        throw new AssertionError("Implemented in Mixin");
    }
}
