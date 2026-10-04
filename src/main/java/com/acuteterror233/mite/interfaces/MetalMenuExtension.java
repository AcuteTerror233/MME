package com.acuteterror233.mite.interfaces;

import com.acuteterror233.mite.material.MetalMaterial;
import org.jetbrains.annotations.Nullable;

/**
 * Menu metal material extension interface.
 * Provides access for injecting and reading the {@link MetalMaterial} of a menu.
 * Injected into vanilla InventoryMenu / CraftingMenu / AnvilMenu via classTweaker transitive-inject-interface.
 */
public interface MetalMenuExtension {
    /** Attaches the {@link MetalMaterial} governing this menu's timed crafting, or clears it with {@code null}. */
    default void MME$SetMetalMaterial(@Nullable MetalMaterial metal) {
        throw new AssertionError("Implemented in Mixin");
    }

    /** @return the attached {@link MetalMaterial}, or {@code null} when none is mounted. */
    default @Nullable MetalMaterial MME$GetMetalMaterial() {
        throw new AssertionError("Implemented in Mixin");
    }
}
