package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public interface MMEBlockTags {
    TagKey<Block> INCORRECT_FOR_ADAMANTIUM_TOOL = key("incorrect_for_adamantium_tool");
    TagKey<Block> INCORRECT_FOR_MITHRIL_TOOL = key("incorrect_for_mithril_tool");
    TagKey<Block> INCORRECT_FOR_ANCIENT_METAL_TOOL = key("incorrect_for_ancient_metal_tool");
    TagKey<Block> INCORRECT_FOR_RUSTED_IRON_TOOL = key("incorrect_for_rusted_iron_tool");
    TagKey<Block> INCORRECT_FOR_COPPER_OR_SILVER_TOOL = key("incorrect_for_copper_or_silver_tool");
    TagKey<Block> INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL = key("incorrect_for_flint_or_obsidian_tool");

    TagKey<Block> NEEDS_ADAMANTIUM_TOOL = key("needs_adamantium_tool");
    TagKey<Block> NEEDS_MITHRIL_TOOL = key("needs_mithril_tool");
    TagKey<Block> NEEDS_ANCIENT_METAL_TOOL = key("needs_ancient_metal_tool");
    TagKey<Block> NEEDS_SILVER_OR_COPPER_TOOL = key("needs_silver_or_copper_tool");

    TagKey<Block> GLASS = key("glass");
    TagKey<Block> GLASS_PANE = key("glass_pane");

    TagKey<Block> INTACT_ANVIL = key("intact_anvil");
    TagKey<Block> CHIPPED_ANVIL = key("chipped_anvil");
    TagKey<Block> DAMAGED_ANVIL = key("damaged_anvil");

    TagKey<Block> NETHERITE_ANVIL = key("netherite_anvil");
    TagKey<Block> ADAMANTIUM_ANVIL = key("adamantium_anvil");
    TagKey<Block> MITHRIL_ANVIL = key("mithril_anvil");
    TagKey<Block> ANCIENT_METAL_ANVIL = key("damaged_anvil");
    TagKey<Block> COPPER_ANVIL = key("copper_anvil");
    TagKey<Block> GOLDEN_ANVIL = key("golden_anvil");
    TagKey<Block> SILVER_ANVIL = key("silver_anvil");
    TagKey<Block> IRON_ANVIL = key("iron_anvil");

    TagKey<Block> PORTAL = key("portal");
    TagKey<Block> MITHRIL_RUNESTORE = key("mithril_runestore");
    TagKey<Block> ADAMANTIUM_RUNESTORE = key("adamantium_runestore");
    TagKey<Block> RUNESTORE = key("runestore");
    TagKey<Block> CRAFTING_TABLE = key("crafting_table");
    TagKey<Block> ENCHANTING_TABLE = key("enchanting_table");
    TagKey<Block> MINEABLE_WITH_MATTOCK = key("mineable_with_mattock");

    static TagKey<Block> key(String id) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
