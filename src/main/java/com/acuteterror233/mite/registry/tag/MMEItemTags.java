package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface MMEItemTags {
    TagKey<Item> NUGGET = key("nugget");
    TagKey<Item> SHARD = key("shard");
    TagKey<Item> INGOT = key("ingot");
    TagKey<Item> CHAINS = key("chains");
    TagKey<Item> COINS = key("coins");

    TagKey<Item> BUCKET = key("bucket");
    TagKey<Item> WATER_BUCKET = key("water_bucket");
    TagKey<Item> MILK_BUCKET = key("milk_bucket");
    TagKey<Item> LAVA_BUCKET = key("lava_bucket");

    TagKey<Item> FISHING_RODS = key("fishing_rods");

    TagKey<Item> BATTLE_AXE = key("battle_axe");
    TagKey<Item> HATCHET = key("hatchet");
    TagKey<Item> DAGGER = key("dagger");
    TagKey<Item> WAR_HAMMER = key("war_hammer");
    TagKey<Item> MATTOCK = key("mattock");
    TagKey<Item> SCYTHE = key("scythe");
    TagKey<Item> SHEARS = key("shears");

    TagKey<Item> DAGGER_ENCHANTABLE = key("dagger_enchantable");
    TagKey<Item> HARVESTING_ENCHANTABLE =  key("harvesting_enchantable");

    TagKey<Item> NETHERITE_TOOLS =  key("netherite_tools");
    TagKey<Item> ADAMANTIUM_TOOLS = key("adamantium_tools");
    TagKey<Item> MITHRIL_TOOLS = key("mithril_tools");
    TagKey<Item> ANCIENT_METAL_TOOLS = key("ancient_metal_tools");
    TagKey<Item> RUSTED_IRON_TOOLS = key("rusted_iron_tools");
    TagKey<Item> IRON_TOOLS = key("iron_tools");
    TagKey<Item> SILVER_TOOLS = key("silver_tools");
    TagKey<Item> COPPER_TOOLS = key("copper_tools");
    TagKey<Item> GOLDEN_TOOLS = key("golden_tools");

    TagKey<Item> DESTRUCTIBLE_ENDCRYSTAL  = key("destructible_endcrystal");

    TagKey<Item> ADAMANTIUM_TOOL_MATERIALS = key("adamantium_tool_materials");
    TagKey<Item> MITHRIL_TOOL_MATERIALS = key("mithril_tool_materials");
    TagKey<Item> ANCIENT_METAL_TOOL_MATERIALS = key("ancient_metal_tool_materials");
    TagKey<Item> RUSTED_IRON_TOOL_MATERIALS = key("rusted_iron_tool_materials");
    TagKey<Item> SILVER_TOOL_MATERIALS = key("silver_tool_materials");
    TagKey<Item> COPPER_TOOL_MATERIALS = key("copper_tool_materials");
    TagKey<Item> FLINT_TOOL_MATERIALS = key("flint_tool_materials");
    TagKey<Item> OBSIDIAN_TOOL_MATERIALS = key("obsidian_tool_materials");

    TagKey<Item> NETHERITE_NOT_ALLOWED_MATERIAL = key("netherite_not_allowed_material");
    TagKey<Item> ADAMANTIUM_NOT_ALLOWED_MATERIAL = key("adamantium_not_allowed_material");
    TagKey<Item> MITHRIL_NOT_ALLOWED_MATERIAL = key("mithril_not_allowed_material");
    TagKey<Item> ANCIENT_METAL_NOT_ALLOWED_MATERIAL = key("ancient_metal_not_allowed_material");
    TagKey<Item> IRON_NOT_ALLOWED_MATERIAL = key("iron_not_allowed_material");
    TagKey<Item> COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL = key("copper_or_silver_not_allowed_material");
    TagKey<Item> GOLD_NOT_ALLOWED_MATERIAL = key("gold_not_allowed_material");

    TagKey<Item> STRING = key("string");

    private static TagKey<Item> key(String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }

}
