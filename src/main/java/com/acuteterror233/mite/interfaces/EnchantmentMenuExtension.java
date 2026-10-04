package com.acuteterror233.mite.interfaces;

/**
 * Enchantment menu extension interface.
 * Provides the per-instance enchantment power cap for menus opened by different enchanting
 * table blocks (vanilla table: 20, emerald table: 10, default: 15).
 * Injected into vanilla EnchantmentMenu via classTweaker transitive-inject-interface.
 */
public interface EnchantmentMenuExtension {
    /** Sets the enchantment power cap of this menu. */
    default void MME$SetMaxEnchantmentLevel(int maxLevel) {
        throw new AssertionError("Implemented in Mixin");
    }

    /** @return the enchantment power cap of this menu. */
    default int MME$GetMaxEnchantmentLevel() {
        throw new AssertionError("Implemented in Mixin");
    }
}
