package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.At_mite;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class At_BlockTags {
    public static final TagKey<Block> INCORRECT_FOR_GOLD_TOOL = of("incorrect_for_gold_tool");

    public static final TagKey<Block> AXE_MINEABLE = of("mineable/axe");

    public static final TagKey<Block> NEEDS_DIAMOND_TOOL = of("needs_diamond_tool");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID,id));
    }
}
