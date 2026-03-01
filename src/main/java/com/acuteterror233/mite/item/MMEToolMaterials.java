package com.acuteterror233.mite.item;

import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;

public class MMEToolMaterials {
    public static final ToolMaterial NETHERITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 256, 18.0F, 8.0F, 24, ItemTags.NETHERITE_TOOL_MATERIALS);
    public static final ToolMaterial ADAMANTIUM = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_ADAMANTIUM_TOOL, 128, 16.0F, 7.0F, 21, MMEItemTags.ADAMANTIUM_TOOL_MATERIALS);
    public static final ToolMaterial MITHRIL = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_MITHRIL_TOOL, 64, 14.0F, 6.0F, 18, MMEItemTags.MITHRIL_TOOL_MATERIALS);
    public static final ToolMaterial ANCIENT_METAL = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_ANCIENT_METAL_TOOL, 32, 12.0F, 5.0F, 15, MMEItemTags.ANCIENT_METAL_TOOL_MATERIALS);
    public static final ToolMaterial RUSTED_IRON = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_RUSTED_IRON_TOOL, 8, 8.0F, 3.0F, 10, MMEItemTags.RUSTED_IRON_TOOL_MATERIALS);
    public static final ToolMaterial IRON = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 16, 10.0F, 4.0F, 12, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 6, 6.0F, 3.0F, 10, MMEItemTags.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial SILVER = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 6, 6.0F, 3.0F, 10, MMEItemTags.SILVER_TOOL_MATERIALS);
    public static final ToolMaterial GOLD = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 4, 2.0F, 2.0F, 22, ItemTags.GOLD_TOOL_MATERIALS);
    public static final ToolMaterial FLINT = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 3, 4.0F, 1.0F, 5, MMEItemTags.FLINT_TOOL_MATERIALS);
    public static final ToolMaterial OBSIDIAN = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 5, 4.0F, 1.0F, 5, MMEItemTags.OBSIDIAN_TOOL_MATERIALS);
    public static final ToolMaterial WOOD = new ToolMaterial(MMEBlockTags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 4, 4.0F, -1.0F, 5, ItemTags.WOODEN_TOOL_MATERIALS);
}