package com.acuteterror233.mite.interfaces;

/**
 * Timed crafting menu extension interface.
 * Provides timed crafting state access for menus that integrate the metal material timed crafting system.
 * Injected into vanilla InventoryMenu / CraftingMenu via classTweaker transitive-inject-interface,
 * for the client Screen to read the crafting state synchronized from the server.
 */
public interface TimedCraftingMenuExtension extends MetalMenuExtension {
    /** @return whether the current grid may synthesize (server-computed; the client reads the synced allow bit). */
    default boolean MME$IsAllowCrafting() {
        throw new AssertionError("Implemented in Mixin");
    }

    /** @return crafting progress as a fraction in [0, 1]. */
    default double MME$GetCraftingTime() {
        throw new AssertionError("Implemented in Mixin");
    }

    /** @return whether a metal material is mounted on this menu. */
    default boolean MME$HasMetal() {
        throw new AssertionError("Implemented in Mixin");
    }
}
