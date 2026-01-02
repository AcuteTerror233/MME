package com.acuteterror233.mite.item;

import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

/**
 * 工具材料表：定义各材料的开采标签、耐久、效率、攻击伤害、附魔性与修复材料标签。
 */
public class AtToolMaterials {
    public static final ToolMaterial NETHERITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 768, 14.0F, 8.0F, 24, ItemTags.NETHERITE_TOOL_MATERIALS);
    public static final ToolMaterial ADAMANTIUM = new ToolMaterial(AtTags.INCORRECT_FOR_ADAMANTIUM_TOOL, 384, 12.0F, 7.0F, 21, AtTags.ADAMANTIUM_TOOL_MATERIALS);
    public static final ToolMaterial MITHRIL = new ToolMaterial(AtTags.INCORRECT_FOR_MITHRIL_TOOL, 192, 10.0F, 6.0F, 18, AtTags.MITHRIL_TOOL_MATERIALS);
    public static final ToolMaterial ANCIENT_METAL = new ToolMaterial(AtTags.INCORRECT_FOR_ANCIENT_METAL_TOOL, 64, 9.0F, 5.0F, 15, AtTags.ANCIENT_METAL_TOOL_MATERIALS);
    public static final ToolMaterial RUSTED_IRON = new ToolMaterial(AtTags.INCORRECT_FOR_RUSTED_IRON_TOOL, 18, 6.0F, 3.0F, 10, AtTags.RUSTED_IRON_TOOL_MATERIALS);
    public static final ToolMaterial IRON = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 14, 8.0F, 3.0F, 10, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = new ToolMaterial(AtTags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 6, 4.0F, 3.0F, 10, AtTags.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial SILVER = new ToolMaterial(AtTags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 6, 4.0F, 3.0F, 10, AtTags.SILVER_TOOL_MATERIALS);
    public static final ToolMaterial GOLD = new ToolMaterial(AtTags.INCORRECT_FOR_COPPER_OR_SILVER_TOOL, 4, 2.0F, 2.0F, 5, ItemTags.GOLD_TOOL_MATERIALS);
    public static final ToolMaterial FLINT = new ToolMaterial(AtTags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 4, 2.0F, 2.0F, 5, AtTags.FLINT_TOOL_MATERIALS);
    public static final ToolMaterial OBSIDIAN = new ToolMaterial(AtTags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 6, 2.0F, 2.0F, 5, AtTags.OBSIDIAN_TOOL_MATERIALS);
    public static final ToolMaterial WOOD = new ToolMaterial(AtTags.INCORRECT_FOR_FLINT_OR_OBSIDIAN_TOOL, 6, 2.0F, 2.0F, 5, ItemTags.WOODEN_TOOL_MATERIALS);
}