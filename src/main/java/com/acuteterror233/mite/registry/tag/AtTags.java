package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.At_mite;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class AtTags {

    //用于判断工具是否不可挖这个方块
    public static final TagKey<Block> INCORRECT_FOR_ADAMANTIUM_TOOL = ofBlock("incorrect_for_adamantium_tool");
    public static final TagKey<Block> INCORRECT_FOR_MITHRIL_TOOL = ofBlock("incorrect_for_mithril_tool");
    public static final TagKey<Block> INCORRECT_FOR_ANCIENT_METAL_TOOL = ofBlock("incorrect_for_ancient_metal_tool");
    public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = ofBlock("incorrect_for_copper_tool");
    public static final TagKey<Block> INCORRECT_FOR_RUSTED_IRON_TOOL = ofBlock("incorrect_for_rusted_iron_tool");
    public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = ofBlock("incorrect_for_silver_tool");
    public static final TagKey<Block> INCORRECT_FOR_FLINT_TOOL = ofBlock("incorrect_for_flint_tool");

    public static final TagKey<Block> BLOCK_COUNT_1 = ofBlock("block_count_1");
    public static final TagKey<Block> BLOCK_COUNT_8 = ofBlock("block_count_8");
    public static final TagKey<Block> BLOCK_COUNT_16 = ofBlock("block_count_16");
    public static final TagKey<Block> BLOCK_COUNT_32 = ofBlock("block_count_32");
    public static final TagKey<Block> GLASS = ofBlock("glass");
    public static final TagKey<Block> GLASS_PANE = ofBlock("glass_pane");
    public static final TagKey<Block> BARS = ofBlock("bars");

    public static final TagKey<Item> ITEM_COUNT_1 = ofItem("item_count_1");
    public static final TagKey<Item> ITEM_COUNT_8 = ofItem("item_count_8");
    public static final TagKey<Item> ITEM_COUNT_16 = ofItem("item_count_16");
    public static final TagKey<Item> ITEM_COUNT_32 = ofItem("item_count_32");
    public static final TagKey<Item> NUGGET = ofItem("nugget");
    public static final TagKey<Item> SHARD = ofItem("shard");
    public static final TagKey<Item> INGOT = ofItem("ingot");
    public static final TagKey<Item> CHAINS = ofItem("chains");
    public static final TagKey<Item> COINS = ofItem("coins");


    public static final TagKey<Item> WATER_BUCKET = ofItem("water_bucket");
    public static final TagKey<Item> FISHING_RODS = ofItem("fishing_rods");

    //用于砧判断是否不可维修
    public static final TagKey<Item> ADAMANTIUM_NONREPAIRABLE = ofItem("adamantium_nonrepairable");
    public static final TagKey<Item> MITHRIL_NONREPAIRABLE = ofItem("mithril_nonrepairable");
    public static final TagKey<Item> ANCIENT_METAL_NONREPAIRABLE = ofItem("ancient_metal_nonrepairable");
    public static final TagKey<Item> RUSTED_IRON_NONREPAIRABLE = ofItem("rusted_iron_nonrepairable");
    public static final TagKey<Item> SILVER_NONREPAIRABLE = ofItem("silver_nonrepairable");
    public static final TagKey<Item> COPPER_NONREPAIRABLE = ofItem("copper_nonrepairable");

    //可维修系列工具含护甲
    public static final TagKey<Item> ADAMANTIUM_SERIES_REPAIRABLE = ofItem("adamantium_series_repairable");
    public static final TagKey<Item> MITHRIL_SERIES_REPAIRABLE = ofItem("mithril_series_repairable");
    public static final TagKey<Item> ANCIENT_METAL_SERIES_REPAIRABLE = ofItem("ancient_metal_series_repairable");
    public static final TagKey<Item> RUSTED_IRON_SERIES_REPAIRABLE = ofItem("rusted_iron_series_repairable");
    public static final TagKey<Item> SILVER_SERIES_REPAIRABLE = ofItem("silver_series_repairable");
    public static final TagKey<Item> COPPER_SERIES_REPAIRABLE = ofItem("copper_series_repairable");

    public static final TagKey<Item> ADAMANTIUM_TOOL_MATERIALS = ofItem("adamantium_tool_materials");
    public static final TagKey<Item> MITHRIL_TOOL_MATERIALS = ofItem("mithril_tool_materials");
    public static final TagKey<Item> ANCIENT_METAL_TOOL_MATERIALS = ofItem("ancient_metal_tool_materials");
    public static final TagKey<Item> RUSTED_IRON_TOOL_MATERIALS = ofItem("rusted_iron_tool_materials");
    public static final TagKey<Item> SILVER_TOOL_MATERIALS = ofItem("silver_tool_materials");
    public static final TagKey<Item> COPPER_TOOL_MATERIALS = ofItem("copper_tool_materials");
    public static final TagKey<Item> FLINT_TOOL_MATERIALS = ofItem("flint_tool_materials");

    private static TagKey<Block> ofBlock(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, id));
    }
    private static TagKey<Item> ofItem(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, id));
    }

}
