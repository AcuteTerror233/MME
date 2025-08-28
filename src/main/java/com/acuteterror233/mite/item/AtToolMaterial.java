package com.acuteterror233.mite.item;

import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.ItemTags;

public class AtToolMaterial {
    public static final ToolMaterial ADAMANTIUM = new ToolMaterial(AtTags.INCORRECT_FOR_ADAMANTIUM_TOOL, 1024, 12.0F, 0.0F, 22, ItemTags.GOLD_TOOL_MATERIALS);
    public static final ToolMaterial ANCIENT_METAL = new ToolMaterial(AtTags.INCORRECT_FOR_ANCIENT_METAL_TOOL, 96, 9.0F, 4.0F, 15, ItemTags.NETHERITE_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = new ToolMaterial(AtTags.INCORRECT_FOR_COPPER_TOOL, 16, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial MITHRIL = new ToolMaterial(AtTags.INCORRECT_FOR_MITHRIL_TOOL, 256, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial RUSTED_IRON = new ToolMaterial(AtTags.INCORRECT_FOR_RUSTED_IRON_TOOL, 12, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial SILVER = new ToolMaterial(AtTags.INCORRECT_FOR_SILVER_TOOL, 16, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial FLINT = new ToolMaterial(AtTags.INCORRECT_FOR_FLINT_TOOL, 16, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
}
