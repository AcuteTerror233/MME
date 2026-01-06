package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MMETags {

    //用于判断工具是否不可挖这个方块
    public static final TagKey<Block> INCORRECT_FOR_ADAMANTIUM_TOOL = ofBlock("incorrect_for_adamantium_tool");
    public static final TagKey<Block> INCORRECT_FOR_MITHRIL_TOOL = ofBlock("incorrect_for_mithril_tool");
    public static final TagKey<Block> INCORRECT_FOR_ANCIENT_METAL_TOOL = ofBlock("incorrect_for_ancient_metal_tool");
    public static final TagKey<Block> INCORRECT_FOR_RUSTED_IRON_TOOL = ofBlock("incorrect_for_rusted_iron_tool");
    public static final TagKey<Block> INCORRECT_FOR_COPPER_OR_SILVER_TOOL = ofBlock("incorrect_for_copper_or_silver_tool");
    public static final TagKey<Block> INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL = ofBlock("incorrect_for_flint_or_obsidian_tool");

    public static final TagKey<Block> NEEDS_ADAMANTIUM_TOOL = ofBlock("needs_adamantium_tool");
    public static final TagKey<Block> NEEDS_MITHRIL_TOOL = ofBlock("needs_mithril_tool");
    public static final TagKey<Block> NEEDS_ANCIENT_METAL_TOOL = ofBlock("needs_ancient_metal_tool");
    public static final TagKey<Block> NEEDS_SILVER_OR_COPPER_TOOL = ofBlock("needs_silver_or_copper_tool");

    public static final TagKey<Block> GLASS = ofBlock("glass");
    public static final TagKey<Block> GLASS_PANE = ofBlock("glass_pane");
    public static final TagKey<Block> BARS = ofBlock("bars");
    public static final TagKey<Block> INTACT_ANVIL = ofBlock("intact_anvil");
    public static final TagKey<Block> CHIPPED_ANVIL = ofBlock("chipped_anvil");
    public static final TagKey<Block> DAMAGED_ANVIL = ofBlock("damaged_anvil");

    public static final TagKey<Block> ADAMANTIUM_ANVIL = ofBlock("adamantium_anvil");
    public static final TagKey<Block> MITHRIL_ANVIL = ofBlock("mithril_anvil");
    public static final TagKey<Block> ANCIENT_METAL_ANVIL = ofBlock("damaged_anvil");
    public static final TagKey<Block> COPPER_ANVIL = ofBlock("copper_anvil");
    public static final TagKey<Block> GOLDEN_ANVIL = ofBlock("golden_anvil");
    public static final TagKey<Block> SILVER_ANVIL = ofBlock("silver_anvil");
    public static final TagKey<Block> IRON_ANVIL = ofBlock("iron_anvil");

    public static final TagKey<Block> PORTAL = ofBlock("portal");
    public static final TagKey<Block> MITHRIL_RUNESTORE = ofBlock("mithril_runestore");
    public static final TagKey<Block> ADAMANTIUM_RUNESTORE = ofBlock("adamantium_runestore");
    public static final TagKey<Block> RUNESTORE = ofBlock("runestore");

    public static final TagKey<Block> CRAFTING_TABLE = ofBlock("crafting_table");

    public static final TagKey<Item> NUGGET = ofItem("nugget");
    public static final TagKey<Item> SHARD = ofItem("shard");
    public static final TagKey<Item> INGOT = ofItem("ingot");
    public static final TagKey<Item> CHAINS = ofItem("chains");
    public static final TagKey<Item> COINS = ofItem("coins");

    public static final TagKey<Item> BUCKET = ofItem("bucket");
    public static final TagKey<Item> WATER_BUCKET = ofItem("water_bucket");
    public static final TagKey<Item> MILK_BUCKET = ofItem("milk_bucket");
    public static final TagKey<Item> LAVA_BUCKET = ofItem("lava_bucket");

    public static final TagKey<Item> FISHING_RODS = ofItem("fishing_rods");

    public static final TagKey<Item> BATTLE_AXE = ofItem("battle_axe");
    public static final TagKey<Item> HATCHET = ofItem("hatchet");
    public static final TagKey<Item> DAGGER = ofItem("dagger");
    public static final TagKey<Item> WAR_HAMMER = ofItem("war_hammer");
    public static final TagKey<Item> MATTOCK = ofItem("mattock");
    public static final TagKey<Item> SCYTHE = ofItem("scythe");
    public static final TagKey<Item> SHEARS = ofItem("shears");

    public static final TagKey<Item> ADAMANTIUM_SERIES_REPAIRABLE = ofItem("adamantium_series_repairable");
    public static final TagKey<Item> MITHRIL_SERIES_REPAIRABLE = ofItem("mithril_series_repairable");
    public static final TagKey<Item> ANCIENT_METAL_SERIES_REPAIRABLE = ofItem("ancient_metal_series_repairable");
    public static final TagKey<Item> RUSTED_IRON_SERIES_REPAIRABLE = ofItem("rusted_iron_series_repairable");
    public static final TagKey<Item> IRON_SERIES_REPAIRABLE = ofItem("iron_series_repairable");
    public static final TagKey<Item> SILVER_SERIES_REPAIRABLE = ofItem("silver_series_repairable");
    public static final TagKey<Item> COPPER_SERIES_REPAIRABLE = ofItem("copper_series_repairable");
    public static final TagKey<Item> GOLD_SERIES_REPAIRABLE = ofItem("gold_series_repairable");

    public static final TagKey<Item> ADAMANTIUM_TOOL_MATERIALS = ofItem("adamantium_tool_materials");
    public static final TagKey<Item> MITHRIL_TOOL_MATERIALS = ofItem("mithril_tool_materials");
    public static final TagKey<Item> ANCIENT_METAL_TOOL_MATERIALS = ofItem("ancient_metal_tool_materials");
    public static final TagKey<Item> RUSTED_IRON_TOOL_MATERIALS = ofItem("rusted_iron_tool_materials");
    public static final TagKey<Item> SILVER_TOOL_MATERIALS = ofItem("silver_tool_materials");
    public static final TagKey<Item> COPPER_TOOL_MATERIALS = ofItem("copper_tool_materials");
    public static final TagKey<Item> FLINT_TOOL_MATERIALS = ofItem("flint_tool_materials");
    public static final TagKey<Item> OBSIDIAN_TOOL_MATERIALS = ofItem("obsidian_tool_materials");

    public static final TagKey<Item> ADAMANTIUM_NOT_ALLOWED_MATERIAL = ofItem("adamantium_not_allowed_material");
    public static final TagKey<Item> MITHRIL_NOT_ALLOWED_MATERIAL = ofItem("mithril_not_allowed_material");
    public static final TagKey<Item> ANCIENT_METAL_NOT_ALLOWED_MATERIAL = ofItem("ancient_metal_not_allowed_material");
    public static final TagKey<Item> IRON_NOT_ALLOWED_MATERIAL = ofItem("iron_not_allowed_material");
    public static final TagKey<Item> COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL = ofItem("copper_or_silver_not_allowed_material");
    public static final TagKey<Item> GOLD_NOT_ALLOWED_MATERIAL = ofItem("gold_not_allowed_material");

    public static final TagKey<Item> STRING = ofItem("string");

    private static TagKey<Block> ofBlock(String id) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }

    private static TagKey<Item> ofItem(String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }

}
