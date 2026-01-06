package com.acuteterror233.mite.item;

import com.acuteterror233.mite.registry.tag.MMETags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;

public class MMEToolMaterials {
    public static final ToolMaterial NETHERITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 768, 14.0F, 8.0F, 24, ItemTags.NETHERITE_TOOL_MATERIALS);
    public static final ToolMaterial ADAMANTIUM = new ToolMaterial(MMETags.INCORRECT_FOR_ADAMANTIUM_TOOL, 384, 12.0F, 7.0F, 21, MMETags.ADAMANTIUM_TOOL_MATERIALS);
    public static final ToolMaterial MITHRIL = new ToolMaterial(MMETags.INCORRECT_FOR_MITHRIL_TOOL, 192, 10.0F, 6.0F, 18, MMETags.MITHRIL_TOOL_MATERIALS);
    public static final ToolMaterial ANCIENT_METAL = new ToolMaterial(MMETags.INCORRECT_FOR_ANCIENT_METAL_TOOL, 64, 9.0F, 5.0F, 15, MMETags.ANCIENT_METAL_TOOL_MATERIALS);
    public static final ToolMaterial RUSTED_IRON = new ToolMaterial(MMETags.INCORRECT_FOR_RUSTED_IRON_TOOL, 18, 6.0F, 3.0F, 10, MMETags.RUSTED_IRON_TOOL_MATERIALS);
    public static final ToolMaterial IRON = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 14, 8.0F, 3.0F, 10, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = new ToolMaterial(MMETags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 6, 4.0F, 3.0F, 10, MMETags.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial SILVER = new ToolMaterial(MMETags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 6, 4.0F, 3.0F, 10, MMETags.SILVER_TOOL_MATERIALS);
    public static final ToolMaterial GOLD = new ToolMaterial(MMETags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 4, 2.0F, 2.0F, 5, ItemTags.GOLD_TOOL_MATERIALS);
    public static final ToolMaterial FLINT = new ToolMaterial(MMETags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 4, 2.0F, 2.0F, 5, MMETags.FLINT_TOOL_MATERIALS);
    public static final ToolMaterial OBSIDIAN = new ToolMaterial(MMETags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 6, 2.0F, 2.0F, 5, MMETags.OBSIDIAN_TOOL_MATERIALS);
    public static final ToolMaterial WOOD = new ToolMaterial(MMETags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 6, 2.0F, 2.0F, 5, ItemTags.WOODEN_TOOL_MATERIALS);
}