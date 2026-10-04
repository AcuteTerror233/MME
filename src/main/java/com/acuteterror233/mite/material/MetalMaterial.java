package com.acuteterror233.mite.material;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

/**
 * Metal material abstraction: unifies the functional parameters of grade crafting tables and anvils.
 * A metal may have crafting functionality only (non-null {@code crafting}), anvil functionality only
 * (non-null {@code anvil}), or both.
 */
public final class MetalMaterial {
    private final String name;
    @Nullable
    private final CraftingFunction crafting;
    @Nullable
    private final AnvilFunction anvil;

    private MetalMaterial(String name, @Nullable CraftingFunction crafting, @Nullable AnvilFunction anvil) {
        this.name = name;
        this.crafting = crafting;
        this.anvil = anvil;
    }

    /** A metal with crafting functionality only. */
    public static MetalMaterial craftingOnly(String name, CraftingFunction crafting) {
        return new MetalMaterial(name, crafting, null);
    }

    /** A metal with anvil functionality only. */
    public static MetalMaterial anvilOnly(String name, AnvilFunction anvil) {
        return new MetalMaterial(name, null, anvil);
    }

    /** A metal with both crafting and anvil functionality. */
    public static MetalMaterial of(String name, CraftingFunction crafting, AnvilFunction anvil) {
        return new MetalMaterial(name, crafting, anvil);
    }

    public String name() {
        return this.name;
    }

    @Nullable
    public CraftingFunction crafting() {
        return this.crafting;
    }

    @Nullable
    public AnvilFunction anvil() {
        return this.anvil;
    }

    public boolean hasCrafting() {
        return this.crafting != null;
    }

    public boolean hasAnvil() {
        return this.anvil != null;
    }

    /**
     * Crafting table function parameters.
     *
     * @param disabledMaterials  tag of disallowed materials: crafting is denied when any input carries
     *                           this tag unless the result belongs to the exception tag
     * @param exceptions         tag of exempt results: results in this tag ignore the material restriction
     * @param speedBonus         crafting speed bonus (added to the experience bonus denominator)
     * @param exhaustionPerCraft extra exhaustion applied when a craft completes
     *                           (used for bare-hand crafting; grade crafting tables use 0)
     */
    public record CraftingFunction(
            TagKey<Item> disabledMaterials,
            TagKey<Item> exceptions,
            float speedBonus,
            float exhaustionPerCraft
    ) {
        public CraftingFunction(TagKey<Item> disabledMaterials, TagKey<Item> exceptions, float speedBonus) {
            this(disabledMaterials, exceptions, speedBonus, 0.0f);
        }
    }

    /** Anvil function parameters: tag of materials not allowed for repair. */
    public record AnvilFunction(TagKey<Item> notAllowedRepairMaterials) {
    }
}
