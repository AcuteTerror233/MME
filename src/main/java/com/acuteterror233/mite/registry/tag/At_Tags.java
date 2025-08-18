package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.At_mite;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class At_Tags {

    public static final TagKey<Block> INCORRECT_FOR_ADAMANTIUM_TOOL = ofBlock("incorrect_for_adamantium_tool");
    public static final TagKey<Block> INCORRECT_FOR_MITHRIL_TOOL = ofBlock("incorrect_for_mithril_tool");
    public static final TagKey<Block> INCORRECT_FOR_ANCIENT_METAL_TOOL = ofBlock("incorrect_for_ancient_metal_tool");
    public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = ofBlock("incorrect_for_copper_tool");
    public static final TagKey<Block> INCORRECT_FOR_RUSTED_IRON_TOOL = ofBlock("incorrect_for_rusted_iron_tool");
    public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = ofBlock("incorrect_for_silver_tool");
    public static final TagKey<Block> INCORRECT_FOR_FLINT_TOOL = ofBlock("incorrect_for_flint_tool");
    public static final TagKey<Item> WATER_BUCKET = ofItem("water_bucket");
    public static final TagKey<Item> FISHING_RODS = ofItem("fishing_rods");

    private static TagKey<Block> ofBlock(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, id));
    }
    private static TagKey<Item> ofItem(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, id));
    }

}
