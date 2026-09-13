package com.acuteterror233.mite.block;

public class MMEBlockItemIds {
    public static final BlockItemId ADAMANTIUM_ORE = BlockItemId.create("adamantium_ore");
    public static final BlockItemId MITHRIL_ORE = BlockItemId.create("mithril_ore");
    public static final BlockItemId SILVER_ORE = BlockItemId.create("silver_ore");

    public static final BlockItemId DEEPSLATE_ADAMANTIUM_ORE = BlockItemId.create("deepslate_adamantium_ore");
    public static final BlockItemId DEEPSLATE_MITHRIL_ORE = BlockItemId.create("deepslate_mithril_ore");
    public static final BlockItemId DEEPSLATE_SILVER_ORE = BlockItemId.create("deepslate_silver_ore");

    public static final BlockItemId ADAMANTIUM_BLOCK = BlockItemId.create("adamantium_block");
    public static final BlockItemId ANCIENT_METAL_BLOCK = BlockItemId.create("ancient_metal_block");
    public static final BlockItemId MITHRIL_BLOCK = BlockItemId.create("mithril_block");
    public static final BlockItemId SILVER_BLOCK = BlockItemId.create("silver_block");

    public static final BlockItemId CLAY_FURNACE = BlockItemId.create("clay_furnace");
    public static final BlockItemId HARDENED_CLAY_FURNACE = BlockItemId.create("hardened_clay_furnace");
    public static final BlockItemId NETHERRACK_FURNACE = BlockItemId.create("netherrack_furnace");
    public static final BlockItemId OBSIDIAN_FURNACE = BlockItemId.create("obsidian_furnace");
    public static final BlockItemId SANDSTONE_FURNACE = BlockItemId.create("sandstone_furnace");

    public static final BlockItemId MANTLE = BlockItemId.create("mantle");

    public static final AnvilCollection<BlockItemId> NETHERITE_ANVIL = AnvilCollection.create(state -> BlockItemId.create(state.getPrefix() + "netherite_anvil"));
    public static final AnvilCollection<BlockItemId> ADAMANTIUM_ANVIL = AnvilCollection.create(state -> BlockItemId.create(state.getPrefix() + "adamantium_anvil"));
    public static final AnvilCollection<BlockItemId> MITHRIL_ANVIL = AnvilCollection.create(state -> BlockItemId.create(state.getPrefix() + "mithril_anvil"));
    public static final AnvilCollection<BlockItemId> ANCIENT_METAL_ANVIL = AnvilCollection.create(state -> BlockItemId.create(state.getPrefix() + "ancient_metal_anvil"));
    public static final AnvilCollection<BlockItemId> GOLDEN_ANVIL = AnvilCollection.create(state -> BlockItemId.create(state.getPrefix() + "golden_anvil"));
    public static final AnvilCollection<BlockItemId> SILVER_ANVIL = AnvilCollection.create(state -> BlockItemId.create(state.getPrefix() + "silver_anvil"));
    public static final AnvilCollection<BlockItemId> COPPER_ANVIL = AnvilCollection.create(state -> BlockItemId.create(state.getPrefix() + "copper_anvil"));

    public static final RunestoneCollection<BlockItemId> MITHRIL_RUNESTORE = RunestoneCollection.create(rune -> BlockItemId.create("mithril_" + rune.getSerializedName() + "_runestore"));
    public static final RunestoneCollection<BlockItemId> ADAMANTIUM_RUNESTORE = RunestoneCollection.create(rune -> BlockItemId.create("adamantium_" + rune.getSerializedName() + "_runestore"));

    public static final BlockItemId ADAMANTIUM_CRAFTING_TABLE = BlockItemId.create("adamantium_crafting_table");
    public static final BlockItemId MITHRIL_CRAFTING_TABLE = BlockItemId.create("mithril_crafting_table");
    public static final BlockItemId ANCIENT_METAL_CRAFTING_TABLE = BlockItemId.create("ancient_metal_crafting_table");
    public static final BlockItemId IRON_CRAFTING_TABLE = BlockItemId.create("iron_crafting_table");
    public static final BlockItemId COPPER_CRAFTING_TABLE = BlockItemId.create("copper_crafting_table");
    public static final BlockItemId SILVER_CRAFTING_TABLE = BlockItemId.create("silver_crafting_table");
    public static final BlockItemId GOLD_CRAFTING_TABLE = BlockItemId.create("gold_crafting_table");
    public static final BlockItemId FLINT_CRAFTING_TABLE = BlockItemId.create("flint_crafting_table");
    public static final BlockItemId OBSIDIAN_CRAFTING_TABLE = BlockItemId.create("obsidian_crafting_table");

    public static final BlockItemId EMERALD_ENCHANTING_TABLE = BlockItemId.create("emerald_enchanting_table");
}
