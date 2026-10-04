package com.acuteterror233.mite.material;

import com.acuteterror233.mite.registry.tag.MMEItemTags;

/**
 * Registry of MME metal materials.
 * Parameters map one-to-one to the legacy grade blocks: HAND for bare-hand crafting,
 * FLINT/OBSIDIAN crafting-only, NETHERITE anvil-only, and the rest both.
 */
public final class MMEMaterials {
    /** Bare-hand (2×2 inventory) crafting: crafting-only, no speed bonus, 0.3 exhaustion per craft. */
    public static final MetalMaterial HAND = MetalMaterial.craftingOnly("hand",
            new MetalMaterial.CraftingFunction(MMEItemTags.HAND_NOT_ALLOWED_MATERIAL, MMEItemTags.FLINT_CRAFTING_TABLE_EXCEPTIONS, 0.0f, 0.3f));
    /** Flint tier (crafting-only): speed bonus 0.03. */
    public static final MetalMaterial FLINT = MetalMaterial.craftingOnly("flint",
            new MetalMaterial.CraftingFunction(MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.FLINT_CRAFTING_TABLE_EXCEPTIONS, 0.03f));
    /** Obsidian tier (crafting-only): speed bonus 0.03, obsidian-specific exception tag. */
    public static final MetalMaterial OBSIDIAN = MetalMaterial.craftingOnly("obsidian",
            new MetalMaterial.CraftingFunction(MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.OBSIDIAN_CRAFTING_TABLE_EXCEPTIONS, 0.03f));
    /** Copper tier (both): speed bonus 0.06, copper/silver restriction tags shared with SILVER. */
    public static final MetalMaterial COPPER = MetalMaterial.of("copper",
            new MetalMaterial.CraftingFunction(MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, MMEItemTags.COPPER_CRAFTING_TABLE_EXCEPTIONS, 0.06f),
            new MetalMaterial.AnvilFunction(MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL));
    /** Silver tier (both): speed bonus 0.06, copper/silver restriction tags shared with COPPER. */
    public static final MetalMaterial SILVER = MetalMaterial.of("silver",
            new MetalMaterial.CraftingFunction(MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, MMEItemTags.SILVER_CRAFTING_TABLE_EXCEPTIONS, 0.06f),
            new MetalMaterial.AnvilFunction(MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL));
    /** Gold tier (both): speed bonus 0.03. */
    public static final MetalMaterial GOLD = MetalMaterial.of("gold",
            new MetalMaterial.CraftingFunction(MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.GOLD_CRAFTING_TABLE_EXCEPTIONS, 0.03f),
            new MetalMaterial.AnvilFunction(MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL));
    /** Iron tier (both): speed bonus 0.09. */
    public static final MetalMaterial IRON = MetalMaterial.of("iron",
            new MetalMaterial.CraftingFunction(MMEItemTags.IRON_NOT_ALLOWED_MATERIAL, MMEItemTags.IRON_CRAFTING_TABLE_EXCEPTIONS, 0.09f),
            new MetalMaterial.AnvilFunction(MMEItemTags.IRON_NOT_ALLOWED_MATERIAL));
    /** Ancient metal tier (both): speed bonus 0.12. */
    public static final MetalMaterial ANCIENT_METAL = MetalMaterial.of("ancient_metal",
            new MetalMaterial.CraftingFunction(MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, MMEItemTags.ANCIENT_METAL_CRAFTING_TABLE_EXCEPTIONS, 0.12f),
            new MetalMaterial.AnvilFunction(MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL));
    /** Mithril tier (both): speed bonus 0.15. */
    public static final MetalMaterial MITHRIL = MetalMaterial.of("mithril",
            new MetalMaterial.CraftingFunction(MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, MMEItemTags.MITHRIL_CRAFTING_TABLE_EXCEPTIONS, 0.15f),
            new MetalMaterial.AnvilFunction(MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL));
    /** Adamantium tier (both): speed bonus 0.18, highest crafting tier. */
    public static final MetalMaterial ADAMANTIUM = MetalMaterial.of("adamantium",
            new MetalMaterial.CraftingFunction(MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, MMEItemTags.ADAMANTIUM_CRAFTING_TABLE_EXCEPTIONS, 0.18f),
            new MetalMaterial.AnvilFunction(MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL));
    /** Netherite tier (anvil-only): no crafting table functionality. */
    public static final MetalMaterial NETHERITE = MetalMaterial.anvilOnly("netherite",
            new MetalMaterial.AnvilFunction(MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL));

    private MMEMaterials() {
    }
}
