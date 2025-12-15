package com.acuteterror233.mite.item;

import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.item.ToolMaterial;

/**
 * 工具材料表：定义各材料的开采标签、耐久、效率、攻击伤害、附魔性与修复材料标签。
 */
public class AtToolMaterials {
    public static final ToolMaterial ADAMANTIUM = new ToolMaterial(AtTags.INCORRECT_FOR_ADAMANTIUM_TOOL, 512, 12.0F, 7.0F, 21, AtTags.ADAMANTIUM_TOOL_MATERIALS);
    public static final ToolMaterial ANCIENT_METAL = new ToolMaterial(AtTags.INCORRECT_FOR_ANCIENT_METAL_TOOL, 64, 9.0F, 5.0F, 15, AtTags.ANCIENT_METAL_TOOL_MATERIALS);
    public static final ToolMaterial MITHRIL = new ToolMaterial(AtTags.INCORRECT_FOR_MITHRIL_TOOL, 256, 8.0F, 6.0F, 18, AtTags.MITHRIL_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = new ToolMaterial(AtTags.INCORRECT_FOR_COPPER_TOOL, 8, 8.0F, 3.0F, 10, AtTags.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial SILVER = new ToolMaterial(AtTags.INCORRECT_FOR_SILVER_TOOL, 8, 8.0F, 3.0F, 10, AtTags.SILVER_TOOL_MATERIALS);
    public static final ToolMaterial RUSTED_IRON = new ToolMaterial(AtTags.INCORRECT_FOR_RUSTED_IRON_TOOL, 18, 8.0F, 3.0F, 10, AtTags.RUSTED_IRON_TOOL_MATERIALS);
    public static final ToolMaterial FLINT = new ToolMaterial(AtTags.INCORRECT_FOR_FLINT_TOOL, 4, 8.0F, 2.0F, 5, AtTags.FLINT_TOOL_MATERIALS);
}
