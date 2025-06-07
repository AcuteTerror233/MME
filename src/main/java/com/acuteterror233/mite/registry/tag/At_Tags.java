package com.acuteterror233.mite.registry.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class At_Tags {

    public static final TagKey<Block> INCORRECT_FOR_ADAMANTIUM_TOOL = ofblock("incorrect_for_adamantium_tool");
    public static final TagKey<Block> INCORRECT_FOR_MITHRIL_TOOL = ofblock("incorrect_for_mithril_tool");
    public static final TagKey<Block> INCORRECT_FOR_ANCIENT_METAL_TOOL = ofblock("incorrect_for_ancient_metal_tool");
    public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = ofblock("incorrect_for_copper_tool");
    public static final TagKey<Block> INCORRECT_FOR_RUSTED_IRON_TOOL = ofblock("incorrect_for_rusted_iron_tool");
    public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = ofblock("incorrect_for_silver_tool");
    public static final TagKey<Block> INCORRECT_FOR_FLINT_TOOL = ofblock("incorrect_for_flint_tool");


    private static TagKey<Block> ofblock(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }
}
