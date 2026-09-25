package com.acuteterror233.mite.item;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.*;
import com.acuteterror233.mite.component.MMEDataComponents;
import com.acuteterror233.mite.item.armor.MMEArmorMaterials;
import com.acuteterror233.mite.item.equipment.MMEArmorMaterial;
import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * MME mod item registry.
 * Defines and registers all custom items (tools, weapons, armor, materials, food, etc.) and their creative mode tabs.
 */
public class MMEItems {
    public static final Item ADAMANTIUM_ORE = registerBlockItem(
            MMEBlocks.ADAMANTIUM_ORE, MMEBlockItemIds.ADAMANTIUM_ORE,
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item MITHRIL_ORE = registerBlockItem(
            MMEBlocks.MITHRIL_ORE, MMEBlockItemIds.MITHRIL_ORE,
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item SILVER_ORE = registerBlockItem(
            MMEBlocks.SILVER_ORE, MMEBlockItemIds.SILVER_ORE,
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 2)
    );
    public static final Item DEEPSLATE_ADAMANTIUM_ORE = registerBlockItem(
            MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE, MMEBlockItemIds.DEEPSLATE_ADAMANTIUM_ORE,
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item DEEPSLATE_MITHRIL_ORE = registerBlockItem(
            MMEBlocks.DEEPSLATE_MITHRIL_ORE, MMEBlockItemIds.DEEPSLATE_MITHRIL_ORE,
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item DEEPSLATE_SILVER_ORE = registerBlockItem(
            MMEBlocks.DEEPSLATE_SILVER_ORE, MMEBlockItemIds.DEEPSLATE_SILVER_ORE,
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Item ADAMANTIUM_BLOCK = registerBlockItem(
            MMEBlocks.ADAMANTIUM_BLOCK, MMEBlockItemIds.ADAMANTIUM_BLOCK,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 1350)
    );
    public static final Item ANCIENT_METAL_BLOCK = registerBlockItem(
            MMEBlocks.ANCIENT_METAL_BLOCK, MMEBlockItemIds.ANCIENT_METAL_BLOCK,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 540)
    );
    public static final Item MITHRIL_BLOCK = registerBlockItem(
            MMEBlocks.MITHRIL_BLOCK, MMEBlockItemIds.MITHRIL_BLOCK,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 810)
    );
    public static final Item SILVER_BLOCK = registerBlockItem(
            MMEBlocks.SILVER_BLOCK, MMEBlockItemIds.SILVER_BLOCK,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 90)
    );

    public static final Item CLAY_FURNACE = registerBlockItem(MMEBlocks.CLAY_FURNACE, MMEBlockItemIds.CLAY_FURNACE, new Item.Properties().component(MMEDataComponents.MAX_COMBUSTION_GRADE, 1));
    public static final Item HARDENED_CLAY_FURNACE = registerBlockItem(MMEBlocks.TERRACOTTA_FURNACE, MMEBlockItemIds.TERRACOTTA_FURNACE, new Item.Properties().component(MMEDataComponents.MAX_COMBUSTION_GRADE, 1));
    public static final Item NETHERRACK_FURNACE = registerBlockItem(MMEBlocks.NETHERRACK_FURNACE, MMEBlockItemIds.NETHERRACK_FURNACE, new Item.Properties().component(MMEDataComponents.MAX_COMBUSTION_GRADE, 4));
    public static final Item OBSIDIAN_FURNACE = registerBlockItem(MMEBlocks.OBSIDIAN_FURNACE, MMEBlockItemIds.OBSIDIAN_FURNACE, new Item.Properties().component(MMEDataComponents.MAX_COMBUSTION_GRADE, 3));
    public static final Item SANDSTONE_FURNACE = registerBlockItem(MMEBlocks.SANDSTONE_FURNACE, MMEBlockItemIds.SANDSTONE_FURNACE, new Item.Properties().component(MMEDataComponents.MAX_COMBUSTION_GRADE, 1));

    public static final Item MANTLE = registerBlockItem(MMEBlocks.MANTLE, MMEBlockItemIds.MANTLE, new Item.Properties());

    public static final AnvilCollection<Item> NETHERITE_ANVILS = AnvilCollection.registerItems(
            MMEBlocks.NETHERITE_ANVILS, MMEBlockItemIds.NETHERITE_ANVIL,
            state -> new Item.Properties().durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.NETHERITE.durability()))
    );
    public static final AnvilCollection<Item> ADAMANTIUM_ANVILS = AnvilCollection.registerItems(
            MMEBlocks.ADAMANTIUM_ANVILS, MMEBlockItemIds.ADAMANTIUM_ANVIL,
            state -> new Item.Properties().durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final AnvilCollection<Item> MITHRIL_ANVILS = AnvilCollection.registerItems(
            MMEBlocks.MITHRIL_ANVILS, MMEBlockItemIds.MITHRIL_ANVIL,
            state -> new Item.Properties().durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final AnvilCollection<Item> ANCIENT_METAL_ANVILS = AnvilCollection.registerItems(
            MMEBlocks.ANCIENT_METAL_ANVILS, MMEBlockItemIds.ANCIENT_METAL_ANVIL,
            state -> new Item.Properties().durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final AnvilCollection<Item> GOLDEN_ANVILS = AnvilCollection.registerItems(
            MMEBlocks.GOLDEN_ANVILS, MMEBlockItemIds.GOLDEN_ANVIL,
            state -> new Item.Properties().durability(MMEBlocks.maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final AnvilCollection<Item> SILVER_ANVILS = AnvilCollection.registerItems(
            MMEBlocks.SILVER_ANVILS, MMEBlockItemIds.SILVER_ANVIL,
            state -> new Item.Properties().durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final AnvilCollection<Item> COPPER_ANVILS = AnvilCollection.registerItems(
            MMEBlocks.COPPER_ANVILS, MMEBlockItemIds.COPPER_ANVIL,
            state -> new Item.Properties().durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );

    // Backward-compatible aliases — delegate to the collections
    public static final Item NETHERITE_ANVIL = NETHERITE_ANVILS.intact();
    public static final Item CHIPPED_NETHERITE_ANVIL = NETHERITE_ANVILS.chipped();
    public static final Item DAMAGED_NETHERITE_ANVIL = NETHERITE_ANVILS.damaged();
    public static final Item ADAMANTIUM_ANVIL = ADAMANTIUM_ANVILS.intact();
    public static final Item CHIPPED_ADAMANTIUM_ANVIL = ADAMANTIUM_ANVILS.chipped();
    public static final Item DAMAGED_ADAMANTIUM_ANVIL = ADAMANTIUM_ANVILS.damaged();
    public static final Item MITHRIL_ANVIL = MITHRIL_ANVILS.intact();
    public static final Item CHIPPED_MITHRIL_ANVIL = MITHRIL_ANVILS.chipped();
    public static final Item DAMAGED_MITHRIL_ANVIL = MITHRIL_ANVILS.damaged();
    public static final Item ANCIENT_METAL_ANVIL = ANCIENT_METAL_ANVILS.intact();
    public static final Item CHIPPED_ANCIENT_METAL_ANVIL = ANCIENT_METAL_ANVILS.chipped();
    public static final Item DAMAGED_ANCIENT_METAL_ANVIL = ANCIENT_METAL_ANVILS.damaged();
    public static final Item GOLDEN_ANVIL = GOLDEN_ANVILS.intact();
    public static final Item CHIPPED_GOLDEN_ANVIL = GOLDEN_ANVILS.chipped();
    public static final Item DAMAGED_GOLDEN_ANVIL = GOLDEN_ANVILS.damaged();
    public static final Item SILVER_ANVIL = SILVER_ANVILS.intact();
    public static final Item CHIPPED_SILVER_ANVIL = SILVER_ANVILS.chipped();
    public static final Item DAMAGED_SILVER_ANVIL = SILVER_ANVILS.damaged();
    public static final Item COPPER_ANVIL = COPPER_ANVILS.intact();
    public static final Item CHIPPED_COPPER_ANVIL = COPPER_ANVILS.chipped();
    public static final Item DAMAGED_COPPER_ANVIL = COPPER_ANVILS.damaged();

    public static final RunestoneCollection<Item> MITHRIL_RUNESTONES = RunestoneCollection.registerBlockItems(
            MMEBlocks.MITHRIL_RUNESTONES, MMEBlockItemIds.MITHRIL_RUNESTORE, rune -> new Item.Properties()
    );
    public static final RunestoneCollection<Item> ADAMANTIUM_RUNESTONES = RunestoneCollection.registerBlockItems(
            MMEBlocks.ADAMANTIUM_RUNESTONES, MMEBlockItemIds.ADAMANTIUM_RUNESTORE, rune -> new Item.Properties()
    );

    public static final Item ADAMANTIUM_CRAFTING_TABLE = registerBlockItem(MMEBlocks.ADAMANTIUM_CRAFTING_TABLE, MMEBlockItemIds.ADAMANTIUM_CRAFTING_TABLE, new Item.Properties());
    public static final Item MITHRIL_CRAFTING_TABLE = registerBlockItem(MMEBlocks.MITHRIL_CRAFTING_TABLE, MMEBlockItemIds.MITHRIL_CRAFTING_TABLE, new Item.Properties());
    public static final Item ANCIENT_METAL_CRAFTING_TABLE = registerBlockItem(MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE, MMEBlockItemIds.ANCIENT_METAL_CRAFTING_TABLE, new Item.Properties());
    public static final Item IRON_CRAFTING_TABLE = registerBlockItem(MMEBlocks.IRON_CRAFTING_TABLE, MMEBlockItemIds.IRON_CRAFTING_TABLE, new Item.Properties());
    public static final Item COPPER_CRAFTING_TABLE = registerBlockItem(MMEBlocks.COPPER_CRAFTING_TABLE, MMEBlockItemIds.COPPER_CRAFTING_TABLE, new Item.Properties());
    public static final Item SILVER_CRAFTING_TABLE = registerBlockItem(MMEBlocks.SILVER_CRAFTING_TABLE, MMEBlockItemIds.SILVER_CRAFTING_TABLE, new Item.Properties());
    public static final Item GOLD_CRAFTING_TABLE = registerBlockItem(MMEBlocks.GOLD_CRAFTING_TABLE, MMEBlockItemIds.GOLD_CRAFTING_TABLE, new Item.Properties());
    public static final Item FLINT_CRAFTING_TABLE = registerBlockItem(MMEBlocks.FLINT_CRAFTING_TABLE, MMEBlockItemIds.FLINT_CRAFTING_TABLE, new Item.Properties());
    public static final Item OBSIDIAN_CRAFTING_TABLE = registerBlockItem(MMEBlocks.OBSIDIAN_CRAFTING_TABLE, MMEBlockItemIds.OBSIDIAN_CRAFTING_TABLE, new Item.Properties());

    public static final Item EMERALD_ENCHANTING_TABLE = registerBlockItem(MMEBlocks.EMERALD_ENCHANTING_TABLE, MMEBlockItemIds.EMERALD_ENCHANTING_TABLE, new Item.Properties());

    public static final Item ADAMANTIUM_HELMET = register(
            MMEItemIds.ADAMANTIUM_HELMET,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHESTPLATE = register(
            MMEItemIds.ADAMANTIUM_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_LEGGINGS = register(
            MMEItemIds.ADAMANTIUM_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_BOOTS = register(
            MMEItemIds.ADAMANTIUM_BOOTS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_BOOTS = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ANCIENT_METAL_HELMET = register(
            MMEItemIds.ANCIENT_METAL_HELMET,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHESTPLATE = register(
            MMEItemIds.ANCIENT_METAL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_LEGGINGS = register(
            MMEItemIds.ANCIENT_METAL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_BOOTS = register(
            MMEItemIds.ANCIENT_METAL_BOOTS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_BOOTS = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_HELMET = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item COPPER_CHAINMAIL_BOOTS = register(
            MMEItemIds.COPPER_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.COPPER_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item COPPER_CHAINMAIL_HELMET = register(
            MMEItemIds.COPPER_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item COPPER_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.COPPER_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item MITHRIL_HELMET = register(
            MMEItemIds.MITHRIL_HELMET,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHESTPLATE = register(
            MMEItemIds.MITHRIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_LEGGINGS = register(
            MMEItemIds.MITHRIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_BOOTS = register(
            MMEItemIds.MITHRIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_HELMET = register(
            MMEItemIds.MITHRIL_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.MITHRIL_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.MITHRIL_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_BOOTS = register(
            MMEItemIds.MITHRIL_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item RUSTED_IRON_HELMET = register(
            MMEItemIds.RUSTED_IRON_HELMET,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.HELMET)
    );
    public static final Item RUSTED_IRON_CHESTPLATE = register(
            MMEItemIds.RUSTED_IRON_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_LEGGINGS = register(
            MMEItemIds.RUSTED_IRON_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_BOOTS = register(
            MMEItemIds.RUSTED_IRON_BOOTS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_HELMET = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_BOOTS = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item SILVER_HELMET = register(
            MMEItemIds.SILVER_HELMET,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.HELMET).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item SILVER_CHESTPLATE = register(
            MMEItemIds.SILVER_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.CHESTPLATE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item SILVER_LEGGINGS = register(
            MMEItemIds.SILVER_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.LEGGINGS).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item SILVER_BOOTS = register(
            MMEItemIds.SILVER_BOOTS,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.BOOTS).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item SILVER_CHAINMAIL_HELMET = register(
            MMEItemIds.SILVER_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.HELMET).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item SILVER_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.SILVER_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item SILVER_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.SILVER_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item SILVER_CHAINMAIL_BOOTS = register(
            MMEItemIds.SILVER_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.BOOTS).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    public static final Item GOLDEN_CHAINMAIL_HELMET = register(
            MMEItemIds.GOLDEN_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item GOLDEN_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.GOLDEN_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item GOLDEN_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.GOLDEN_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item GOLDEN_CHAINMAIL_BOOTS = register(
            MMEItemIds.GOLDEN_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );

    public static final Item BANANA = register(
            MMEItemIds.BANANA,
            new Item.Properties()
                    .food(new FoodProperties(2, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item BLUE_BERRIE = register(
            MMEItemIds.BLUE_BERRIE,
            Items.createBlockItemWithCustomItemName(MMEBlocks.BLUE_BERRY_BUSH),
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item CHEESE = register(
            MMEItemIds.CHEESE,
            new Item.Properties()
                    .food(new FoodProperties(3, 3.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(24000).build())
                    .stacksTo(16)
    );
    public static final Item CHOCOLATE = register(
            MMEItemIds.CHOCOLATE,
            new Item.Properties()
                    .food(new FoodProperties(3, 3.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item DOUGH = register(
            MMEItemIds.DOUGH,
            new Item.Properties()
                    .food(new FoodProperties(2, 6.0F, false))
                    .stacksTo(16)
    );
    public static final Item LEMON = register(
            MMEItemIds.LEMON,
            new Item.Properties()
                    .food(new FoodProperties(1, 2.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item ONION = register(
            MMEItemIds.ONION,
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).build())
                    .stacksTo(16)
    );
    public static final Item ORANGE = register(
            MMEItemIds.ORANGE,
            new Item.Properties()
                    .food(new FoodProperties(1, 2.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item WORM_COOKED = register(
            MMEItemIds.WORM_COOKED,
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
                    .stacksTo(16)
    );
    public static final Item WORM_RAW = register(
            MMEItemIds.WORM_RAW,
            new Item.Properties()
                    .food(new FoodProperties(1, 0.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
                    .stacksTo(16)
    );
    public static final Item FLOUR = register(
            MMEItemIds.FLOUR,
            new Item.Properties().stacksTo(16)
    );
    public static final Item BEEF_STEW = register(
            MMEItemIds.BEEF_STEW,
            new Item.Properties()
                    .food(new FoodProperties(16, 16.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(128000).fiber(128000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item BOWL_MILK = register(
            MMEItemIds.BOWL_MILK,
            new Item.Properties()
                    .food(new FoodProperties(1, 0, false), Consumables.MILK_BUCKET)
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item BOWL_SALAD = register(
            MMEItemIds.BOWL_SALAD,
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item BOWL_WATER = register(
            MMEItemIds.BOWL_WATER,
            new Item.Properties()
                    .food(new FoodProperties(0, 0F, true), Consumables.defaultDrink().build())
                    .usingConvertsTo(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CEREAL = register(
            MMEItemIds.CEREAL,
            new Item.Properties()
                    .food(new FoodProperties(2, 5.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(16000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CHICKEN_SOUP = register(
            MMEItemIds.CHICKEN_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(10, 10.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(80000).fiber(80000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CREAM_OF_MUSHROOM_SOUP = register(
            MMEItemIds.CREAM_OF_MUSHROOM_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(5, 3.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(40000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CREAM_OF_VEGETABLE_SOUP = register(
            MMEItemIds.CREAM_OF_VEGETABLE_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(7, 7.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(56000).fiber(56000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item ICE_CREAM = register(
            MMEItemIds.ICE_CREAM,
            new Item.Properties()
                    .food(new FoodProperties(4, 5.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(32000).sugar(4800).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item MASHED_POTATO = register(
            MMEItemIds.MASHED_POTATO,
            new Item.Properties()
                    .food(new FoodProperties(8, 12.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(64000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item PORRIDGE = register(
            MMEItemIds.PORRIDGE,
            new Item.Properties()
                    .food(new FoodProperties(5, 5.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).sugar(9600).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item PUMPKIN_SOUP = register(
            MMEItemIds.PUMPKIN_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(2, 1.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item SORBET = register(
            MMEItemIds.SORBET,
            new Item.Properties()
                    .food(new FoodProperties(2, 4.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).sugar(9600).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item VEGETABLE_SOUP = register(
            MMEItemIds.VEGETABLE_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(6, 6.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(48000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    
    public static final Item NETHERITE_BATTLE_AXE = register(MMEItemIds.NETHERITE_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_HATCHET = register(MMEItemIds.NETHERITE_HATCHET, getHandAxeSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_DAGGER = register(MMEItemIds.NETHERITE_DAGGER, getDaggerSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_KNIFE = register(MMEItemIds.NETHERITE_KNIFE);
    public static final Item NETHERITE_WAR_HAMMER = register(MMEItemIds.NETHERITE_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_MATTOCK = register(MMEItemIds.NETHERITE_MATTOCK, getMattockSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_SCYTHE = register(MMEItemIds.NETHERITE_SCYTHE, getScytheSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_SHEARS = registerShearsItem(MMEItemIds.NETHERITE_SHEARS, getShearsSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    
    public static final Item ADAMANTIUM_AXE = register(MMEItemIds.ADAMANTIUM_AXE, getAxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_BATTLE_AXE = register(MMEItemIds.ADAMANTIUM_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_HATCHET = register(MMEItemIds.ADAMANTIUM_HATCHET, getHandAxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SWORD = register(MMEItemIds.ADAMANTIUM_SWORD, getSwordSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_DAGGER = register(MMEItemIds.ADAMANTIUM_DAGGER, getDaggerSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_KNIFE = register(MMEItemIds.ADAMANTIUM_KNIFE);
    public static final Item ADAMANTIUM_PICKAXE = register(MMEItemIds.ADAMANTIUM_PICKAXE, getPickaxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_WAR_HAMMER = register(MMEItemIds.ADAMANTIUM_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SHOVEL = register(MMEItemIds.ADAMANTIUM_SHOVEL, getShovelSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_HOE = register(MMEItemIds.ADAMANTIUM_HOE, getHoeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_MATTOCK = register(MMEItemIds.ADAMANTIUM_MATTOCK, getMattockSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SCYTHE = register(MMEItemIds.ADAMANTIUM_SCYTHE, getScytheSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SHEARS = registerShearsItem(MMEItemIds.ADAMANTIUM_SHEARS, getShearsSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SPEAR = register(MMEItemIds.ADAMANTIUM_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.ADAMANTIUM, 1.10F, 1.16F, 0.45F, 2.5F, 6.8F, 5.8F, 5.1F, 9.0F, 4.6F).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));

    public static final Item ANCIENT_METAL_AXE = register(MMEItemIds.ANCIENT_METAL_AXE, getAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_BATTLE_AXE = register(MMEItemIds.ANCIENT_METAL_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HATCHET = register(MMEItemIds.ANCIENT_METAL_HATCHET, getHandAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SWORD = register(MMEItemIds.ANCIENT_METAL_SWORD, getSwordSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_DAGGER = register(MMEItemIds.ANCIENT_METAL_DAGGER, getDaggerSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_KNIFE = register(MMEItemIds.ANCIENT_METAL_KNIFE);
    public static final Item ANCIENT_METAL_PICKAXE = register(MMEItemIds.ANCIENT_METAL_PICKAXE, getPickaxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_WAR_HAMMER = register(MMEItemIds.ANCIENT_METAL_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHOVEL = register(MMEItemIds.ANCIENT_METAL_SHOVEL, getShovelSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HOE = register(MMEItemIds.ANCIENT_METAL_HOE, getHoeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_MATTOCK = register(MMEItemIds.ANCIENT_METAL_MATTOCK, getMattockSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SCYTHE = register(MMEItemIds.ANCIENT_METAL_SCYTHE, getScytheSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHEARS = registerShearsItem(MMEItemIds.ANCIENT_METAL_SHEARS, getShearsSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SPEAR = register(MMEItemIds.ANCIENT_METAL_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.ANCIENT_METAL, 1.02F, 1.05F, 0.53F, 2.5F, 7.5F, 6.5F, 5.1F, 10.0F, 4.6F));

    public static final Item COPPER_BATTLE_AXE = register(MMEItemIds.COPPER_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_HATCHET = register(MMEItemIds.COPPER_HATCHET, getHandAxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_DAGGER = register(MMEItemIds.COPPER_DAGGER, getDaggerSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_KNIFE = register(MMEItemIds.COPPER_KNIFE);
    public static final Item COPPER_WAR_HAMMER = register(MMEItemIds.COPPER_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_MATTOCK = register(MMEItemIds.COPPER_MATTOCK, getMattockSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SCYTHE = register(MMEItemIds.COPPER_SCYTHE, getScytheSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SHEARS = registerShearsItem(MMEItemIds.COPPER_SHEARS, getShearsSettings(MMEToolMaterials.COPPER));

    public static final Item GOLDEN_BATTLE_AXE = register(MMEItemIds.GOLDEN_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_HATCHET = register(MMEItemIds.GOLDEN_HATCHET, getHandAxeSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_DAGGER = register(MMEItemIds.GOLDEN_DAGGER, getDaggerSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_KNIFE = register(MMEItemIds.GOLDEN_KNIFE);
    public static final Item GOLDEN_WAR_HAMMER = register(MMEItemIds.GOLDEN_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_MATTOCK = register(MMEItemIds.GOLDEN_MATTOCK, getMattockSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_SCYTHE = register(MMEItemIds.GOLDEN_SCYTHE, getScytheSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_SHEARS = registerShearsItem(MMEItemIds.GOLDEN_SHEARS, getShearsSettings(MMEToolMaterials.GOLD));

    public static final Item IRON_BATTLE_AXE = register(MMEItemIds.IRON_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.IRON));
    public static final Item IRON_DAGGER = register(MMEItemIds.IRON_DAGGER, getDaggerSettings(MMEToolMaterials.IRON));
    public static final Item IRON_HATCHET = register(MMEItemIds.IRON_HATCHET, getHandAxeSettings(MMEToolMaterials.IRON));
    public static final Item IRON_WAR_HAMMER = register(MMEItemIds.IRON_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.IRON));
    public static final Item IRON_KNIFE = register(MMEItemIds.IRON_KNIFE);
    public static final Item IRON_MATTOCK = register(MMEItemIds.IRON_MATTOCK, getMattockSettings(MMEToolMaterials.IRON));
    public static final Item IRON_SCYTHE = register(MMEItemIds.IRON_SCYTHE, getScytheSettings(MMEToolMaterials.IRON));

    public static final Item MITHRIL_AXE = register(MMEItemIds.MITHRIL_AXE, getAxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_BATTLE_AXE = register(MMEItemIds.MITHRIL_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_HATCHET = register(MMEItemIds.MITHRIL_HATCHET, getHandAxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SWORD = register(MMEItemIds.MITHRIL_SWORD, getSwordSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_DAGGER = register(MMEItemIds.MITHRIL_DAGGER, getDaggerSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_KNIFE = register(MMEItemIds.MITHRIL_KNIFE);
    public static final Item MITHRIL_PICKAXE = register(MMEItemIds.MITHRIL_PICKAXE, getPickaxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_WAR_HAMMER = register(MMEItemIds.MITHRIL_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SHOVEL = register(MMEItemIds.MITHRIL_SHOVEL, getShovelSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_HOE = register(MMEItemIds.MITHRIL_HOE, getHoeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_MATTOCK = register(MMEItemIds.MITHRIL_MATTOCK, getMattockSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SCYTHE = register(MMEItemIds.MITHRIL_SCYTHE, getScytheSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SHEARS = registerShearsItem(MMEItemIds.MITHRIL_SHEARS, getShearsSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SPEAR = register(MMEItemIds.MITHRIL_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.MITHRIL, 1.06F, 1.10F, 0.50F, 2.5F, 7.2F, 6.2F, 5.1F, 9.5F, 4.6F).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));

    public static final Item RUSTED_IRON_AXE = register(MMEItemIds.RUSTED_IRON_AXE, getAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_BATTLE_AXE = register(MMEItemIds.RUSTED_IRON_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HATCHET = register(MMEItemIds.RUSTED_IRON_HATCHET, getHandAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SWORD = register(MMEItemIds.RUSTED_IRON_SWORD, getSwordSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_DAGGER = register(MMEItemIds.RUSTED_IRON_DAGGER, getDaggerSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_KNIFE = register(MMEItemIds.RUSTED_IRON_KNIFE);
    public static final Item RUSTED_IRON_PICKAXE = register(MMEItemIds.RUSTED_IRON_PICKAXE, getPickaxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_WAR_HAMMER = register(MMEItemIds.RUSTED_IRON_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHOVEL = register(MMEItemIds.RUSTED_IRON_SHOVEL, getShovelSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HOE = register(MMEItemIds.RUSTED_IRON_HOE, getHoeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_MATTOCK = register(MMEItemIds.RUSTED_IRON_MATTOCK, getMattockSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SCYTHE = register(MMEItemIds.RUSTED_IRON_SCYTHE, getScytheSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHEARS = registerShearsItem(MMEItemIds.RUSTED_IRON_SHEARS, getShearsSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SPEAR = register(MMEItemIds.RUSTED_IRON_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.RUSTED_IRON, 0.90F, 0.88F, 0.63F, 3.6F, 8.6F, 7.6F, 5.1F, 11.8F, 4.6F));

    public static final Item SILVER_AXE = register(MMEItemIds.SILVER_AXE, getAxeSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_BATTLE_AXE = register(MMEItemIds.SILVER_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_HATCHET = register(MMEItemIds.SILVER_HATCHET, getHandAxeSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_DAGGER = register(MMEItemIds.SILVER_DAGGER, getDaggerSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_KNIFE = register(MMEItemIds.SILVER_KNIFE, new Item.Properties().component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_SWORD = register(MMEItemIds.SILVER_SWORD, getSwordSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_PICKAXE = register(MMEItemIds.SILVER_PICKAXE, getPickaxeSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_WAR_HAMMER = register(MMEItemIds.SILVER_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_SHOVEL = register(MMEItemIds.SILVER_SHOVEL, getShovelSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_HOE = register(MMEItemIds.SILVER_HOE, getHoeSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_MATTOCK = register(MMEItemIds.SILVER_MATTOCK, getMattockSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_SCYTHE = register(MMEItemIds.SILVER_SCYTHE, getScytheSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_SHEARS = registerShearsItem(MMEItemIds.SILVER_SHEARS, getShearsSettings(MMEToolMaterials.SILVER).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item SILVER_SPEAR = register(MMEItemIds.SILVER_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.SILVER, 0.98F, 1.00F, 0.56F, 2.8F, 7.8F, 6.8F, 5.1F, 10.5F, 4.6F).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));

    public static final Item OBSIDIAN_AXE = register(MMEItemIds.OBSIDIAN_AXE, applyToolSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, BlockTags.MINEABLE_WITH_AXE, 3.4F, 2, 4, -2.7F, 0.5F, 0.5F, 5));
    public static final Item OBSIDIAN_HATCHET = register(MMEItemIds.OBSIDIAN_HATCHET, applyToolSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, BlockTags.MINEABLE_WITH_AXE, 1, 2, 3, -2.7F, 0.25F, 0.25F, 5));
    public static final Item OBSIDIAN_KNIFE = register(MMEItemIds.OBSIDIAN_KNIFE, applySwordSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, 3, 1, 2, -2.0F, 0.25F, 0.25F));
    public static final Item OBSIDIAN_SHOVEL = register(MMEItemIds.OBSIDIAN_SHOVEL, applyToolSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, BlockTags.MINEABLE_WITH_SHOVEL, 5, 2, 1, -2.8F, 0.75F, 0.75F, 0));

    public static final Item FLINT_AXE = register(MMEItemIds.FLINT_AXE, applyToolSettings(new Item.Properties(), MMEToolMaterials.FLINT, BlockTags.MINEABLE_WITH_AXE, 3.4F, 2, 4, -2.7F, 0.5F, 0.5F, 5));
    public static final Item FLINT_HATCHET = register(MMEItemIds.FLINT_HATCHET, applyToolSettings(new Item.Properties(), MMEToolMaterials.FLINT, BlockTags.MINEABLE_WITH_AXE, 1, 2, 3, -2.7F, 0.25F, 0.25F, 5));
    public static final Item FLINT_KNIFE = register(MMEItemIds.FLINT_KNIFE, applySwordSettings(new Item.Properties(), MMEToolMaterials.FLINT, 3, 1, 2, -2.0F, 0.25F, 0.25F));
    public static final Item FLINT_SHOVEL = register(MMEItemIds.FLINT_SHOVEL, applyToolSettings(new Item.Properties(), MMEToolMaterials.FLINT, BlockTags.MINEABLE_WITH_SHOVEL, 3, 2, 1, -2.8F, 0.75F, 0.75F, 0));
    public static final Item FLINT_SPEAR = register(MMEItemIds.FLINT_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.FLINT, 0.72F, 0.76F, 0.70F, 4.8F, 12.0F, 9.8F, 5.1F, 14.2F, 4.6F));

    public static final Item WOODEN_CLUB = register(MMEItemIds.WOODEN_CLUB, applySwordSettings(new Item.Properties(), MMEToolMaterials.WOOD, 2, 1, 4, -2.4F, 0.5F, 0.5F).cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_LARGE));
    public static final Item WOODEN_CUDGEL = register(MMEItemIds.WOODEN_CUDGEL, applySwordSettings(new Item.Properties(), MMEToolMaterials.WOOD, 1, 1, 3, -2.0F, 0.25F, 0.25F).cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_LARGE));

    public static final Item ADAMANTIUM_CHAINS = register(MMEItemIds.ADAMANTIUM_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 75));
    public static final Item MITHRIL_CHAINS = register(MMEItemIds.MITHRIL_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 45));
    public static final Item ANCIENT_METAL_CHAINS = register(MMEItemIds.ANCIENT_METAL_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 30));
    public static final Item IRON_CHAINS = register(MMEItemIds.IRON_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 15));
    public static final Item RUSTED_IRON_CHAINS = register(MMEItemIds.RUSTED_IRON_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));
    public static final Item GOLDEN_CHAINS = register(MMEItemIds.GOLDEN_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));
    public static final Item COPPER_CHAINS = register(MMEItemIds.COPPER_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));
    public static final Item SILVER_CHAINS = register(MMEItemIds.SILVER_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));

    public static final Item NETHERITE_COINS = register(
            MMEItemIds.NETHERITE_COINS,
            settings -> new CoinsItem(settings, 5000),
            new Item.Properties().stacksTo(32)
    );
    public static final Item ADAMANTIUM_COINS = register(
            MMEItemIds.ADAMANTIUM_COINS,
            settings -> new CoinsItem(settings, 1000),
            new Item.Properties().stacksTo(32)
    );
    public static final Item MITHRIL_COINS = register(
            MMEItemIds.MITHRIL_COINS,
            settings -> new CoinsItem(settings, 500),
            new Item.Properties().stacksTo(32)
    );
    public static final Item ANCIENT_METAL_COINS = register(
            MMEItemIds.ANCIENT_METAL_COINS,
            settings -> new CoinsItem(settings, 300),
            new Item.Properties().stacksTo(32)
    );
    public static final Item IRON_COINS = register(
            MMEItemIds.IRON_COINS,
            settings -> new CoinsItem(settings, 200),
            new Item.Properties().stacksTo(32)
    );
    public static final Item GOLDEN_COINS = register(
            MMEItemIds.GOLDEN_COINS,
            settings -> new CoinsItem(settings, 500),
            new Item.Properties().stacksTo(32)
    );
    public static final Item COPPER_COINS = register(
            MMEItemIds.COPPER_COINS,
            settings -> new CoinsItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );
    public static final Item SILVER_COINS = register(
            MMEItemIds.SILVER_COINS,
            settings -> new CoinsItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );

    public static final Item ADAMANTIUM_BUCKET = register(
            MMEItemIds.ADAMANTIUM_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item ADAMANTIUM_WATER_BUCKET = register(
            MMEItemIds.ADAMANTIUM_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .craftRemainder(ADAMANTIUM_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ADAMANTIUM_LAVA_BUCKET = register(
            MMEItemIds.ADAMANTIUM_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .craftRemainder(ADAMANTIUM_BUCKET)
                    .stacksTo(1)
                    .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)
    );
    public static final Item ADAMANTIUM_POWDER_SNOW_BUCKET = register(
            MMEItemIds.ADAMANTIUM_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item ADAMANTIUM_MILK_BUCKET = register(
            MMEItemIds.ADAMANTIUM_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(ADAMANTIUM_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(ADAMANTIUM_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ADAMANTIUM_PUFFERFISH_BUCKET = register(
            MMEItemIds.ADAMANTIUM_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_SALMON_BUCKET = register(
            MMEItemIds.ADAMANTIUM_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_COD_BUCKET = register(
            MMEItemIds.ADAMANTIUM_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.ADAMANTIUM_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_AXOLOTL_BUCKET = register(
            MMEItemIds.ADAMANTIUM_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_TADPOLE_BUCKET = register(
            MMEItemIds.ADAMANTIUM_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.ADAMANTIUM_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_BUCKET = register(MMEItemIds.ANCIENT_METAL_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item ANCIENT_METAL_WATER_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .craftRemainder(ANCIENT_METAL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ANCIENT_METAL_LAVA_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .craftRemainder(ANCIENT_METAL_BUCKET)
                    .stacksTo(1)
                    .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)
    );
    public static final Item ANCIENT_METAL_POWDER_SNOW_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item ANCIENT_METAL_MILK_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(ANCIENT_METAL_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(ANCIENT_METAL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ANCIENT_METAL_PUFFERFISH_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_SALMON_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_COD_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_AXOLOTL_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_TADPOLE_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_BUCKET = register(
            MMEItemIds.COPPER_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item COPPER_WATER_BUCKET = register(
            MMEItemIds.COPPER_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, COPPER_BUCKET),
            new Item.Properties()
                    .craftRemainder(COPPER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item COPPER_LAVA_BUCKET = register(
            MMEItemIds.COPPER_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, COPPER_BUCKET),
            new Item.Properties()
                    .craftRemainder(COPPER_BUCKET)
                    .stacksTo(1)
                    .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)
    );
    public static final Item COPPER_POWDER_SNOW_BUCKET = register(
            MMEItemIds.COPPER_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item COPPER_MILK_BUCKET = register(
            MMEItemIds.COPPER_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(COPPER_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(COPPER_BUCKET).stacksTo(1)
    );
    public static final Item COPPER_PUFFERFISH_BUCKET = register(
            MMEItemIds.COPPER_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_SALMON_BUCKET = register(
            MMEItemIds.COPPER_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_COD_BUCKET = register(
            MMEItemIds.COPPER_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.COPPER_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_AXOLOTL_BUCKET = register(
            MMEItemIds.COPPER_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_TADPOLE_BUCKET = register(
            MMEItemIds.COPPER_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.COPPER_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_BUCKET = register(
            MMEItemIds.GOLD_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item GOLD_WATER_BUCKET = register(
            MMEItemIds.GOLD_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, GOLD_BUCKET),
            new Item.Properties()
                    .craftRemainder(GOLD_BUCKET)
                    .stacksTo(1)
    );
    public static final Item GOLD_LAVA_BUCKET = register(
            MMEItemIds.GOLD_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, GOLD_BUCKET),
            new Item.Properties()
                    .craftRemainder(GOLD_BUCKET)
                    .stacksTo(1)
                    .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)
    );
    public static final Item GOLD_POWDER_SNOW_BUCKET = register(
            MMEItemIds.GOLD_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item GOLD_MILK_BUCKET = register(
            MMEItemIds.GOLD_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(GOLD_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(GOLD_BUCKET)
                    .stacksTo(1)
    );
    public static final Item GOLD_PUFFERFISH_BUCKET = register(
            MMEItemIds.GOLD_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_SALMON_BUCKET = register(
            MMEItemIds.GOLD_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_COD_BUCKET = register(
            MMEItemIds.GOLD_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.GOLD_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_AXOLOTL_BUCKET = register(
            MMEItemIds.GOLD_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_TADPOLE_BUCKET = register(
            MMEItemIds.GOLD_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.GOLD_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_BUCKET = register(
            MMEItemIds.MITHRIL_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item MITHRIL_WATER_BUCKET = register(
            MMEItemIds.MITHRIL_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .craftRemainder(MITHRIL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item MITHRIL_LAVA_BUCKET = register(
            MMEItemIds.MITHRIL_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .craftRemainder(MITHRIL_BUCKET)
                    .stacksTo(1)
                    .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)
    );
    public static final Item MITHRIL_POWDER_SNOW_BUCKET = register(
            MMEItemIds.MITHRIL_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item MITHRIL_MILK_BUCKET = register(
            MMEItemIds.MITHRIL_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(MITHRIL_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(MITHRIL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item MITHRIL_PUFFERFISH_BUCKET = register(
            MMEItemIds.MITHRIL_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_SALMON_BUCKET = register(
            MMEItemIds.MITHRIL_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_COD_BUCKET = register(
            MMEItemIds.MITHRIL_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.MITHRIL_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_AXOLOTL_BUCKET = register(
            MMEItemIds.MITHRIL_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_TADPOLE_BUCKET = register(
            MMEItemIds.MITHRIL_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.MITHRIL_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_BUCKET = register(
            MMEItemIds.SILVER_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item SILVER_WATER_BUCKET = register(
            MMEItemIds.SILVER_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, SILVER_BUCKET),
            new Item.Properties()
                    .craftRemainder(SILVER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item SILVER_LAVA_BUCKET = register(
            MMEItemIds.SILVER_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, SILVER_BUCKET),
            new Item.Properties()
                    .craftRemainder(SILVER_BUCKET)
                    .stacksTo(1)
                    .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)
    );
    public static final Item SILVER_POWDER_SNOW_BUCKET = register(
            MMEItemIds.SILVER_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item SILVER_MILK_BUCKET = register(
            MMEItemIds.SILVER_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(SILVER_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(SILVER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item SILVER_PUFFERFISH_BUCKET = register(
            MMEItemIds.SILVER_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_SALMON_BUCKET = register(
            MMEItemIds.SILVER_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_COD_BUCKET = register(
            MMEItemIds.SILVER_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.SILVER_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_AXOLOTL_BUCKET = register(
            MMEItemIds.SILVER_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_TADPOLE_BUCKET = register(
            MMEItemIds.SILVER_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.SILVER_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item NETHERITE_BUCKET = register(
            MMEItemIds.NETHERITE_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
                    .fireResistant()
    );
    public static final Item NETHERITE_WATER_BUCKET = register(
            MMEItemIds.NETHERITE_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .craftRemainder(NETHERITE_BUCKET)
                    .stacksTo(1)
                    .fireResistant()
    );
    public static final Item NETHERITE_LAVA_BUCKET = register(
            MMEItemIds.NETHERITE_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .craftRemainder(NETHERITE_BUCKET)
                    .stacksTo(1)
                    .fireResistant()
                    .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)
    );
    public static final Item NETHERITE_POWDER_SNOW_BUCKET = register(
            MMEItemIds.NETHERITE_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
                    .fireResistant()
    );
    public static final Item NETHERITE_MILK_BUCKET = register(
            MMEItemIds.NETHERITE_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(NETHERITE_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(NETHERITE_BUCKET)
                    .stacksTo(1)
                    .fireResistant()
    );
    public static final Item NETHERITE_PUFFERFISH_BUCKET = register(
            MMEItemIds.NETHERITE_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_SALMON_BUCKET = register(
            MMEItemIds.NETHERITE_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_COD_BUCKET = register(
            MMEItemIds.NETHERITE_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.NETHERITE_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_AXOLOTL_BUCKET = register(
            MMEItemIds.NETHERITE_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_TADPOLE_BUCKET = register(
            MMEItemIds.NETHERITE_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.NETHERITE_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_FISHING_ROD = register(MMEItemIds.NETHERITE_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(256).enchantable(24));
    public static final Item ADAMANTIUM_FISHING_ROD = register(MMEItemIds.ADAMANTIUM_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(128).enchantable(21));
    public static final Item ANCIENT_METAL_FISHING_ROD = register(MMEItemIds.ANCIENT_METAL_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(96).enchantable(15));
    public static final Item COPPER_FISHING_ROD = register(MMEItemIds.COPPER_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(8).enchantable(10));
    public static final Item FLINT_FISHING_ROD = register(MMEItemIds.FLINT_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(32).enchantable(5));
    public static final Item GOLDEN_FISHING_ROD = register(MMEItemIds.GOLDEN_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(4).enchantable(22));
    public static final Item IRON_FISHING_ROD = register(MMEItemIds.IRON_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(32).enchantable(12));
    public static final Item MITHRIL_FISHING_ROD = register(MMEItemIds.MITHRIL_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(64).enchantable(18));
    public static final Item OBSIDIAN_FISHING_ROD = register(MMEItemIds.OBSIDIAN_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(32).enchantable(5));
    public static final Item SILVER_FISHING_ROD = register(MMEItemIds.SILVER_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(8).enchantable(10));

    public static final Item RAW_ADAMANTIUM = register(MMEItemIds.RAW_ADAMANTIUM,
            new Item.Properties().stacksTo(8).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item RAW_MITHRIL = register(MMEItemIds.RAW_MITHRIL,
            new Item.Properties().stacksTo(8).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item RAW_SILVER = register(MMEItemIds.RAW_SILVER,
            new Item.Properties().stacksTo(8).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 2));

    public static final Item ADAMANTIUM_INGOT = register(MMEItemIds.ADAMANTIUM_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 50).stacksTo(16));
    public static final Item MITHRIL_INGOT = register(MMEItemIds.MITHRIL_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 30).stacksTo(16));
    public static final Item ANCIENT_METAL_INGOT = register(MMEItemIds.ANCIENT_METAL_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 20).stacksTo(16));
    public static final Item SILVER_INGOT = register(MMEItemIds.SILVER_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 5).stacksTo(16));

    public static final Item NETHERITE_NUGGET = register(
            MMEItemIds.NETHERITE_NUGGET,
            settings -> new NuggetItem(settings, 5000),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 10)
    );
    public static final Item ADAMANTIUM_NUGGET = register(
            MMEItemIds.ADAMANTIUM_NUGGET,
            settings -> new NuggetItem(settings, 1000),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 7)
    );
    public static final Item MITHRIL_NUGGET = register(
            MMEItemIds.MITHRIL_NUGGET,
            settings -> new NuggetItem(settings, 500),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 5)
    );
    public static final Item ANCIENT_METAL_NUGGET = register(
            MMEItemIds.ANCIENT_METAL_NUGGET,
            settings -> new NuggetItem(settings, 300),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 4)
    );
    public static final Item SILVER_NUGGET = register(
            MMEItemIds.SILVER_NUGGET,
            settings -> new NuggetItem(settings, 50),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 1)
    );

    public static final Item FLINT_SHARD = register(MMEItemIds.FLINT_SHARD,
            new Item.Properties().stacksTo(16));
    public static final Item OBSIDIAN_SHARD = register(MMEItemIds.OBSIDIAN_SHARD,
            new Item.Properties().stacksTo(16));

    public static final Item SINEW = register(MMEItemIds.SINEW,
            new Item.Properties().stacksTo(16));
    public static final Item MANURE = register(
            MMEItemIds.MANURE,
            ManureItem::new,
            new Item.Properties().stacksTo(16)
    );
    public static final Item GHOUL_SPAWN_EGG = register(
            MMEItemIds.GHOUL_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.GHOUL)
    );
    public static final Item SHADOW_SPAWN_EGG = register(
            MMEItemIds.SHADOW_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.SHADOW)
    );
    public static final Item WIGHT_SPAWN_EGG = register(
            MMEItemIds.WIGHT_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.WIGHT)
    );
    public static final Item INVISIBLE_STALKER_SPAWN_EGG = register(
            MMEItemIds.INVISIBLE_STALKER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.INVISIBLE_STALKER)
    );
    public static final Item DEMON_SPIDER_SPAWN_EGG = register(
            MMEItemIds.DEMON_SPIDER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.DEMON_SPIDER)
    );
    public static final Item PHASE_SPIDER_SPAWN_EGG = register(
            MMEItemIds.PHASE_SPIDER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.PHASE_SPIDER)
    );
    public static final Item INFERNAL_CREEPER_SPAWN_EGG = register(
            MMEItemIds.INFERNAL_CREEPER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.INFERNAL_CREEPER)
    );
    public static final Item FIRE_ELEMENTAL_SPAWN_EGG = register(
            MMEItemIds.FIRE_ELEMENTAL_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.FIRE_ELEMENTAL)
    );
    public static final Item VAMPIRE_BAT_SPAWN_EGG = register(
            MMEItemIds.VAMPIRE_BAT_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.VAMPIRE_BAT)
    );
    public static final Item NIGHTWING_SPAWN_EGG = register(
            MMEItemIds.NIGHTWING_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.NIGHTWING)
    );
    public static final Item GIANT_VAMPIRE_BAT_SPAWN_EGG = register(
            MMEItemIds.GIANT_VAMPIRE_BAT_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.GIANT_VAMPIRE_BAT)
    );

    public static Item.Properties getArmorSettings(MMEArmorMaterial material, ArmorType type) {
            return new Item.Properties().durability(type.getDurability((int) (material.durability() * 0.45)))
                    .attributes(material.createAttributeModifiers(type))
                    .enchantable(material.enchantmentValue())
                    .component(
                            DataComponents.EQUIPPABLE,
                            Equippable.builder(type.getSlot()).setEquipSound(material.equipSound()).setAsset(material.assetId()).build()
                    )
                    .repairable(material.repairIngredient());

    }

    public static Item.Properties getDaggerSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Properties(), material, 2, 1, 2, -2.0F, 0.25F, 0.25F);
    }
    public static Item.Properties getSwordSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Properties(), material, 5, 1, 3, -2.4F, 0.5F, 0.5F);
    }

    public static Item.Properties getScytheSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, MMEBlockTags.MINEABLE_WITH_SCYTHE, 9, 1, 3, -3.0f , 0.75f, 0.75f);
    }

    public static Item.Properties getAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 5, 2, 4, -2.7F, 0.5F, 0.5F, 5).delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.AXE));
    }
    public static Item.Properties getBattleAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 7, 1, 5, -3F, 0.5F, 0.75F, 5).delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.AXE));
    }
    public static Item.Properties getHandAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 2, 2, 3, -2.7F, 0.25F, 0.25F, 5).delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.AXE));
    }

    public static Item.Properties getHoeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_HOE, 4, 2, 1, -1.0F, 0.5F, 0.5F).delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.HOE));
    }
    public static Item.Properties getMattockSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, MMEBlockTags.MINEABLE_WITH_MATTOCK, 6, 2, 2, -1.5F, 0.5F, 0.5F).delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.HOE));
    }

    public static Item.Properties getShovelSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_SHOVEL, 3, 2, 1, -2.8F, 0.75F, 0.75F).delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.SHOVEL));
    }

    public static Item.Properties getPickaxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_PICKAXE, 3, 2, 2, -2.8f , 0.75f, 0.75f);
    }
    public static Item.Properties getWarHammerSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_PICKAXE, 5, 1, 3, -3.0f , 0.75f, 0.75f);
    }

    public static Item.Properties applySpearSettings(
            Item.Properties settings,
            ToolMaterial toolMaterial,
            float swingDurationSeconds,
            float chargedKnockback,
            float chargeDurationSeconds,
            float lightSpeedStartSeconds,
            float lightSpeedThreshold,
            float heavySpeedStartSeconds,
            float heavySpeedThreshold,
            float relativeSpeedStartSeconds,
            float relativeSpeedThreshold
    ) {
        return applyBaseSettings(settings, toolMaterial, 4)
                .delayedHolderComponent(DataComponents.DAMAGE_TYPE, DamageTypes.SPEAR)
                .component(
                        DataComponents.KINETIC_WEAPON,
                        new KineticWeapon(
                                10,
                                (int)(chargeDurationSeconds * 20.0F),
                                KineticWeapon.Condition.ofAttackerSpeed((int)(lightSpeedStartSeconds * 20.0F), lightSpeedThreshold),
                                KineticWeapon.Condition.ofAttackerSpeed((int)(heavySpeedStartSeconds * 20.0F), heavySpeedThreshold),
                                KineticWeapon.Condition.ofRelativeSpeed((int)(relativeSpeedStartSeconds * 20.0F), relativeSpeedThreshold),
                                0.38F,
                                chargedKnockback,
                                Optional.of(toolMaterial == ToolMaterial.WOOD ? SoundEvents.SPEAR_WOOD_USE : SoundEvents.SPEAR_USE),
                                Optional.of(toolMaterial == ToolMaterial.WOOD ? SoundEvents.SPEAR_WOOD_HIT : SoundEvents.SPEAR_HIT)
                        )
                )
                .component(
                        DataComponents.PIERCING_WEAPON,
                        new PiercingWeapon(
                                true,
                                false,
                                Optional.of(SoundEvents.SPEAR_ATTACK),
                                Optional.of(SoundEvents.SPEAR_HIT)
                        )
                )
                .component(DataComponents.ATTACK_RANGE, new AttackRange(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F))
                .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F)
                .component(DataComponents.ATTACK_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int)(swingDurationSeconds * 20.0F)))
                .attributes(
                        ItemAttributeModifiers.builder()
                                .add(
                                        Attributes.ATTACK_DAMAGE,
                                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 1 + toolMaterial.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND
                                )
                                .add(
                                        Attributes.ATTACK_SPEED,
                                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 1.0F / swingDurationSeconds - 4.0, AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND
                                )
                                .build()
                )
                .component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F))
                .component(DataComponents.WEAPON, new Weapon(1));
    }

    public static Item.Properties getShearsSettings(ToolMaterial material) {
        return applyBaseSettings(new Item.Properties(), material, 7)
                .component(DataComponents.TOOL, ShearsItem.createToolProperties())
                .attributes(CreateAttributeModifiers(0.5f, 0.5f, material.attackDamageBonus(), 0));
    }
    public static Item.Properties applyToolSettings(
            Item.Properties settings,
            ToolMaterial material,
            TagKey<Block> effectiveBlocks,
            float durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange
    ){
        return applyToolSettings(settings, material, effectiveBlocks, durabilityMultiplier, itemDamagePerAttack, attackDamage, attackSpeed, blockInteractionRange, entityInteractionRange, 0.0F);
    }

    public static Item.Properties applyToolSettings(
            Item.Properties settings,
            ToolMaterial material,
            TagKey<Block> effectiveBlocks,
            float durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange,
            float disableBlockingForSeconds
    ) {
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return applyBaseSettings(settings, material, durabilityMultiplier)
                .component(
                        DataComponents.TOOL,
                        new Tool(
                                List.of(
                                        Tool.Rule.deniesDrops(registryEntryLookup.getOrThrow(material.incorrectBlocksForDrops())),
                                        Tool.Rule.minesAndDrops(registryEntryLookup.getOrThrow(effectiveBlocks), material.speed())
                                ),
                                1.0F,
                                1,
                                true
                        )
                )
                .attributes(CreateAttributeModifiers(blockInteractionRange, entityInteractionRange, attackDamage + material.attackDamageBonus(), attackSpeed))
                .component(DataComponents.WEAPON, new Weapon(itemDamagePerAttack, disableBlockingForSeconds));
    }

    public static Item.Properties applySwordSettings(
            Item.Properties settings,
            ToolMaterial material,
            int durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange
    ) {
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return applyBaseSettings(settings, material, durabilityMultiplier)
                .component(
                        DataComponents.TOOL,
                        new Tool(
                                List.of(
                                        Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                                        Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                        Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                                ),
                                1.0F,
                                2,
                                false
                        )
                )
                .attributes(CreateAttributeModifiers(blockInteractionRange, entityInteractionRange, attackDamage + material.attackDamageBonus(), attackSpeed))
                .component(DataComponents.WEAPON, new Weapon(itemDamagePerAttack));
    }

    public static ItemAttributeModifiers CreateAttributeModifiers(float blockInteractionRange,float entityInteractionRange, float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.BLOCK_INTERACTION_RANGE,
                        new AttributeModifier(MME.BASE_BLOCK_INTERACTION_RANGE, blockInteractionRange, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(MME.BASE_ENTITY_INTERACTION_RANGE, entityInteractionRange, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }
    public static Item.Properties applyBaseSettings(Item.Properties settings, ToolMaterial material, float durabilityMultiplier) {
        Item.Properties settings1 = settings.durability((int) (material.durability() * durabilityMultiplier)).repairable(material.repairItems()).enchantable(material.enchantmentValue());
        if (material == ToolMaterial.NETHERITE){
            settings1 = settings1.fireResistant();
        }
        return settings1;
    }
    public static Item.Properties VanillaSpearSettings(Item.Properties settings, ToolMaterial material, float durabilityMultiplier, float swingDurationSeconds){
        return applyBaseSettings(settings, material, durabilityMultiplier).attributes(
                ItemAttributeModifiers.builder()
                        .add(
                                Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 1 + material.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND
                        )
                        .add(
                                Attributes.ATTACK_SPEED,
                                new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 1.0F / swingDurationSeconds - 4.0, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND
                        )
                        .build()
        );
    }

    private static Item registerShearsItem(ResourceKey<Item> registryKey, Item.Properties settings) {
        return register(registryKey, ShearsItem::new, settings);
    }

    public static Item register(ResourceKey<Item> registryKey) {
        return register(registryKey, new Item.Properties());
    }

    public static Item register(ResourceKey<Item> registryKey, Item.Properties settings) {
        return register(registryKey, Item::new, settings);
    }

    public static Item registerBlockItem(Block block, BlockItemId id, Item.Properties settings) {
        Function<Item.Properties, Item> function = properties -> new BlockItem(block, properties.useBlockDescriptionPrefix().setId(id.itemKey()));
        return register(id.itemKey(), function, settings);
    }

    public static Item register(ResourceKey<Item> registryKey, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings.setId(registryKey));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        return Registry.register(BuiltInRegistries.ITEM, registryKey, item);
    }


    /**
     * Chains items into a creative tab, each inserted right after the previous one.
     */
    private static void insertChain(FabricCreativeModeTabOutput output, Item anchor, Item... items) {
        Item prev = anchor;
        for (Item item : items) {
            output.insertAfter(prev, item);
            prev = item;
        }
    }

    public static void init() {
        // Remove disabled vanilla items from all creative tabs
        Set<Identifier> disabledVanillaItems = new HashSet<>();
        disabledVanillaItems.add(Identifier.withDefaultNamespace("wooden_sword"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("wooden_pickaxe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("wooden_axe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("wooden_hoe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("stone_sword"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("stone_shovel"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("stone_pickaxe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("stone_axe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("stone_hoe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("stone_spear"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_sword"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_shovel"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_pickaxe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_axe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_hoe"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_spear"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_helmet"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_chestplate"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_leggings"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("diamond_boots"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("fishing_rod"));
        disabledVanillaItems.add(Identifier.withDefaultNamespace("crafting_table"));

        CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register((tab, output) -> {
            output.getDisplayStacks().removeIf(item -> {
                Identifier id = BuiltInRegistries.ITEM.getKey(item.getItem());
                return disabledVanillaItems.contains(id);
            });
            output.getSearchTabStacks().removeIf(item -> {
                Identifier id = BuiltInRegistries.ITEM.getKey(item.getItem());
                return disabledVanillaItems.contains(id);
            });
        });

        // === Natural Blocks: ores and mantle ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> {
            output.insertAfter(Items.DEEPSLATE_COPPER_ORE, MMEBlocks.SILVER_ORE, MMEBlocks.DEEPSLATE_SILVER_ORE);
            output.insertAfter(Items.DEEPSLATE_GOLD_ORE,
                    MMEBlocks.MITHRIL_ORE, MMEBlocks.DEEPSLATE_MITHRIL_ORE,
                    MMEBlocks.ADAMANTIUM_ORE, MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE);
            output.accept(MMEBlocks.MANTLE);
        });

        // === Building Blocks: metal storage blocks ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> {
            output.insertBefore(Items.IRON_BLOCK, MMEBlocks.SILVER_BLOCK);
            output.insertAfter(Items.DIAMOND_BLOCK,
                    MMEBlocks.ANCIENT_METAL_BLOCK, MMEBlocks.MITHRIL_BLOCK, MMEBlocks.ADAMANTIUM_BLOCK);
        });

        // === Functional Blocks: furnaces, crafting tables, runestones, anvils ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> {
            output.insertAfter(Items.BLAST_FURNACE,
                    MMEBlocks.CLAY_FURNACE, MMEBlocks.TERRACOTTA_FURNACE, MMEBlocks.NETHERRACK_FURNACE,
                    MMEBlocks.OBSIDIAN_FURNACE, MMEBlocks.SANDSTONE_FURNACE);

            // Crafting tables replace the removed vanilla crafting table (anchored before the stonecutter)
            output.insertBefore(Items.STONECUTTER,
                    MMEBlocks.FLINT_CRAFTING_TABLE, MMEBlocks.OBSIDIAN_CRAFTING_TABLE,
                    MMEBlocks.COPPER_CRAFTING_TABLE, MMEBlocks.SILVER_CRAFTING_TABLE,
                    MMEBlocks.IRON_CRAFTING_TABLE, MMEBlocks.GOLD_CRAFTING_TABLE,
                    MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE, MMEBlocks.MITHRIL_CRAFTING_TABLE,
                    MMEBlocks.ADAMANTIUM_CRAFTING_TABLE);

            MMEItems.MITHRIL_RUNESTONES.forEach(output::accept);
            MMEItems.ADAMANTIUM_RUNESTONES.forEach(output::accept);

            // Emerald enchanting table next to the vanilla enchanting table
            output.insertAfter(Items.ENCHANTING_TABLE, MMEBlocks.EMERALD_ENCHANTING_TABLE);
            // Anvils by material around the vanilla anvils
            output.insertBefore(Items.ANVIL,
                    MMEBlocks.COPPER_ANVIL, MMEBlocks.CHIPPED_COPPER_ANVIL, MMEBlocks.DAMAGED_COPPER_ANVIL,
                    MMEBlocks.SILVER_ANVIL, MMEBlocks.CHIPPED_SILVER_ANVIL, MMEBlocks.DAMAGED_SILVER_ANVIL);
            output.insertAfter(Items.DAMAGED_ANVIL,
                    MMEBlocks.GOLDEN_ANVIL, MMEBlocks.CHIPPED_GOLDEN_ANVIL, MMEBlocks.DAMAGED_GOLDEN_ANVIL,
                    MMEBlocks.ANCIENT_METAL_ANVIL, MMEBlocks.CHIPPED_ANCIENT_METAL_ANVIL, MMEBlocks.DAMAGED_ANCIENT_METAL_ANVIL,
                    MMEBlocks.MITHRIL_ANVIL, MMEBlocks.CHIPPED_MITHRIL_ANVIL, MMEBlocks.DAMAGED_MITHRIL_ANVIL,
                    MMEBlocks.ADAMANTIUM_ANVIL, MMEBlocks.CHIPPED_ADAMANTIUM_ANVIL, MMEBlocks.DAMAGED_ADAMANTIUM_ANVIL,
                    MMEBlocks.NETHERITE_ANVIL, MMEBlocks.CHIPPED_NETHERITE_ANVIL, MMEBlocks.DAMAGED_NETHERITE_ANVIL);
        });

        // === Combat: weapons anchored to their vanilla positions ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> {
            // Swords interleaved with vanilla swords by tier
            output.insertAfter(Items.COPPER_SWORD, SILVER_SWORD);
            output.insertAfter(SILVER_SWORD, RUSTED_IRON_SWORD);
            output.insertAfter(Items.GOLDEN_SWORD, ANCIENT_METAL_SWORD);
            output.insertAfter(ANCIENT_METAL_SWORD, MITHRIL_SWORD);
            output.insertAfter(MITHRIL_SWORD, ADAMANTIUM_SWORD);

            // Daggers placed right after the vanilla swords
            insertChain(output, Items.NETHERITE_SWORD,
                    COPPER_DAGGER, SILVER_DAGGER, RUSTED_IRON_DAGGER, IRON_DAGGER, GOLDEN_DAGGER,
                    ANCIENT_METAL_DAGGER, MITHRIL_DAGGER, ADAMANTIUM_DAGGER, NETHERITE_DAGGER);

            // Spears interleaved with vanilla spears by tier
            output.insertBefore(Items.COPPER_SPEAR, FLINT_SPEAR);
            output.insertAfter(Items.COPPER_SPEAR, SILVER_SPEAR);
            output.insertAfter(SILVER_SPEAR, RUSTED_IRON_SPEAR);
            output.insertAfter(Items.GOLDEN_SPEAR, ANCIENT_METAL_SPEAR);
            output.insertAfter(ANCIENT_METAL_SPEAR, MITHRIL_SPEAR);
            output.insertAfter(MITHRIL_SPEAR, ADAMANTIUM_SPEAR);

            // Hatchets placed before the vanilla axes
            output.insertBefore(Items.WOODEN_AXE,
                    FLINT_HATCHET, COPPER_HATCHET, SILVER_HATCHET, RUSTED_IRON_HATCHET, IRON_HATCHET,
                    GOLDEN_HATCHET, ANCIENT_METAL_HATCHET, MITHRIL_HATCHET, ADAMANTIUM_HATCHET,
                    OBSIDIAN_HATCHET, NETHERITE_HATCHET);

            // Axes interleaved with vanilla axes by tier
            output.insertBefore(Items.COPPER_AXE, FLINT_AXE);
            output.insertAfter(Items.COPPER_AXE, SILVER_AXE);
            output.insertAfter(SILVER_AXE, RUSTED_IRON_AXE);
            output.insertAfter(Items.GOLDEN_AXE, ANCIENT_METAL_AXE);
            output.insertAfter(ANCIENT_METAL_AXE, MITHRIL_AXE);
            output.insertAfter(MITHRIL_AXE, ADAMANTIUM_AXE);
            output.insertAfter(ADAMANTIUM_AXE, OBSIDIAN_AXE);

            // Battle axes placed after the vanilla axes
            insertChain(output, Items.NETHERITE_AXE,
                    COPPER_BATTLE_AXE, SILVER_BATTLE_AXE, RUSTED_IRON_BATTLE_AXE, IRON_BATTLE_AXE,
                    GOLDEN_BATTLE_AXE, ANCIENT_METAL_BATTLE_AXE, MITHRIL_BATTLE_AXE,
                    ADAMANTIUM_BATTLE_AXE, NETHERITE_BATTLE_AXE);

            // Scythes and war hammers follow the battle axes
            insertChain(output, NETHERITE_BATTLE_AXE,
                    COPPER_SCYTHE, SILVER_SCYTHE, RUSTED_IRON_SCYTHE, IRON_SCYTHE, GOLDEN_SCYTHE,
                    ANCIENT_METAL_SCYTHE, MITHRIL_SCYTHE, ADAMANTIUM_SCYTHE, NETHERITE_SCYTHE);
            insertChain(output, NETHERITE_SCYTHE,
                    COPPER_WAR_HAMMER, SILVER_WAR_HAMMER, RUSTED_IRON_WAR_HAMMER, IRON_WAR_HAMMER,
                    GOLDEN_WAR_HAMMER, ANCIENT_METAL_WAR_HAMMER, MITHRIL_WAR_HAMMER,
                    ADAMANTIUM_WAR_HAMMER, NETHERITE_WAR_HAMMER);

            // Clubs and flint/obsidian knives placed before the trident
            output.insertBefore(Items.TRIDENT,
                    WOODEN_CLUB, WOODEN_CUDGEL, FLINT_KNIFE, OBSIDIAN_KNIFE);

            // Armor interleaved with the vanilla armor by material
            output.insertAfter(Items.COPPER_BOOTS,
                    COPPER_CHAINMAIL_HELMET, COPPER_CHAINMAIL_CHESTPLATE, COPPER_CHAINMAIL_LEGGINGS,
                    COPPER_CHAINMAIL_BOOTS);
            output.insertAfter(COPPER_CHAINMAIL_BOOTS,
                    SILVER_HELMET, SILVER_CHESTPLATE, SILVER_LEGGINGS, SILVER_BOOTS,
                    SILVER_CHAINMAIL_HELMET, SILVER_CHAINMAIL_CHESTPLATE, SILVER_CHAINMAIL_LEGGINGS,
                    SILVER_CHAINMAIL_BOOTS);
            output.insertAfter(SILVER_CHAINMAIL_BOOTS,
                    RUSTED_IRON_HELMET, RUSTED_IRON_CHESTPLATE, RUSTED_IRON_LEGGINGS, RUSTED_IRON_BOOTS,
                    RUSTED_IRON_CHAINMAIL_HELMET, RUSTED_IRON_CHAINMAIL_CHESTPLATE, RUSTED_IRON_CHAINMAIL_LEGGINGS,
                    RUSTED_IRON_CHAINMAIL_BOOTS);
            output.insertAfter(Items.GOLDEN_BOOTS,
                    GOLDEN_CHAINMAIL_HELMET, GOLDEN_CHAINMAIL_CHESTPLATE, GOLDEN_CHAINMAIL_LEGGINGS,
                    GOLDEN_CHAINMAIL_BOOTS);
            output.insertAfter(GOLDEN_CHAINMAIL_BOOTS,
                    ANCIENT_METAL_HELMET, ANCIENT_METAL_CHESTPLATE, ANCIENT_METAL_LEGGINGS, ANCIENT_METAL_BOOTS,
                    ANCIENT_METAL_CHAINMAIL_HELMET, ANCIENT_METAL_CHAINMAIL_CHESTPLATE, ANCIENT_METAL_CHAINMAIL_LEGGINGS,
                    ANCIENT_METAL_CHAINMAIL_BOOTS);
            output.insertAfter(ANCIENT_METAL_CHAINMAIL_BOOTS,
                    MITHRIL_HELMET, MITHRIL_CHESTPLATE, MITHRIL_LEGGINGS, MITHRIL_BOOTS,
                    MITHRIL_CHAINMAIL_HELMET, MITHRIL_CHAINMAIL_CHESTPLATE, MITHRIL_CHAINMAIL_LEGGINGS,
                    MITHRIL_CHAINMAIL_BOOTS);
            output.insertAfter(MITHRIL_CHAINMAIL_BOOTS,
                    ADAMANTIUM_HELMET, ADAMANTIUM_CHESTPLATE, ADAMANTIUM_LEGGINGS, ADAMANTIUM_BOOTS,
                    ADAMANTIUM_CHAINMAIL_HELMET, ADAMANTIUM_CHAINMAIL_CHESTPLATE, ADAMANTIUM_CHAINMAIL_LEGGINGS,
                    ADAMANTIUM_CHAINMAIL_BOOTS);
        });

        // === Tools & Utilities: MME tools interleaved into the vanilla tool system ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
            // FLINT (inserted before the vanilla wooden shovel)
            output.insertBefore(Items.WOODEN_SHOVEL,
                    FLINT_SHOVEL, FLINT_HATCHET, FLINT_AXE, FLINT_FISHING_ROD);

            // OBSIDIAN
            output.insertAfter(FLINT_FISHING_ROD,
                    OBSIDIAN_SHOVEL, OBSIDIAN_HATCHET, OBSIDIAN_AXE, OBSIDIAN_FISHING_ROD);

            // COPPER (interleaved with the vanilla copper tools)
            output.insertAfter(Items.COPPER_PICKAXE, COPPER_WAR_HAMMER, COPPER_HATCHET);
            output.insertAfter(Items.COPPER_AXE, COPPER_BATTLE_AXE);
            output.insertAfter(Items.COPPER_HOE, COPPER_MATTOCK, COPPER_SHEARS, COPPER_FISHING_ROD);

            // SILVER
            output.insertAfter(COPPER_FISHING_ROD,
                    SILVER_SHOVEL, SILVER_PICKAXE, SILVER_WAR_HAMMER, SILVER_HATCHET, SILVER_AXE,
                    SILVER_BATTLE_AXE, SILVER_HOE, SILVER_MATTOCK, SILVER_SHEARS, SILVER_FISHING_ROD);

            // RUSTED_IRON
            output.insertAfter(SILVER_FISHING_ROD,
                    RUSTED_IRON_SHOVEL, RUSTED_IRON_PICKAXE, RUSTED_IRON_WAR_HAMMER, RUSTED_IRON_HATCHET,
                    RUSTED_IRON_AXE, RUSTED_IRON_BATTLE_AXE, RUSTED_IRON_HOE, RUSTED_IRON_MATTOCK,
                    RUSTED_IRON_SHEARS);

            // IRON (interleaved with the vanilla iron tools; vanilla shears moved in)
            output.insertAfter(Items.IRON_PICKAXE, IRON_WAR_HAMMER, IRON_HATCHET);
            output.insertAfter(Items.IRON_AXE, IRON_BATTLE_AXE);
            output.insertAfter(Items.IRON_HOE, IRON_MATTOCK, IRON_FISHING_ROD);
            output.getDisplayStacks().removeIf(stack -> stack.is(Items.SHEARS));
            output.getSearchTabStacks().removeIf(stack -> stack.is(Items.SHEARS));
            output.insertAfter(IRON_MATTOCK, Items.SHEARS);

            // GOLD (interleaved with the vanilla golden tools)
            output.insertAfter(Items.GOLDEN_PICKAXE, GOLDEN_WAR_HAMMER, GOLDEN_HATCHET);
            output.insertAfter(Items.GOLDEN_AXE, GOLDEN_BATTLE_AXE);
            output.insertAfter(Items.GOLDEN_HOE, GOLDEN_MATTOCK, GOLDEN_SHEARS, GOLDEN_FISHING_ROD);

            // ANCIENT_METAL
            output.insertAfter(GOLDEN_FISHING_ROD,
                    ANCIENT_METAL_SHOVEL, ANCIENT_METAL_PICKAXE, ANCIENT_METAL_WAR_HAMMER,
                    ANCIENT_METAL_HATCHET, ANCIENT_METAL_AXE, ANCIENT_METAL_BATTLE_AXE,
                    ANCIENT_METAL_HOE, ANCIENT_METAL_MATTOCK, ANCIENT_METAL_SHEARS,
                    ANCIENT_METAL_FISHING_ROD);

            // MITHRIL
            output.insertAfter(ANCIENT_METAL_FISHING_ROD,
                    MITHRIL_SHOVEL, MITHRIL_PICKAXE, MITHRIL_WAR_HAMMER, MITHRIL_HATCHET, MITHRIL_AXE,
                    MITHRIL_BATTLE_AXE, MITHRIL_HOE, MITHRIL_MATTOCK, MITHRIL_SHEARS, MITHRIL_FISHING_ROD);

            // ADAMANTIUM
            output.insertAfter(MITHRIL_FISHING_ROD,
                    ADAMANTIUM_SHOVEL, ADAMANTIUM_PICKAXE, ADAMANTIUM_WAR_HAMMER, ADAMANTIUM_HATCHET,
                    ADAMANTIUM_AXE, ADAMANTIUM_BATTLE_AXE, ADAMANTIUM_HOE, ADAMANTIUM_MATTOCK,
                    ADAMANTIUM_SHEARS, ADAMANTIUM_FISHING_ROD);

            // NETHERITE (interleaved with the vanilla netherite tools)
            output.insertAfter(Items.NETHERITE_PICKAXE, NETHERITE_WAR_HAMMER, NETHERITE_HATCHET);
            output.insertAfter(Items.NETHERITE_AXE, NETHERITE_BATTLE_AXE);
            output.insertAfter(Items.NETHERITE_HOE, NETHERITE_MATTOCK, NETHERITE_SHEARS, NETHERITE_FISHING_ROD);

            // Buckets: copper/silver before the vanilla iron buckets, gold and above after
            output.insertBefore(Items.BUCKET,
                    COPPER_BUCKET, COPPER_WATER_BUCKET, COPPER_LAVA_BUCKET, COPPER_MILK_BUCKET,
                    COPPER_POWDER_SNOW_BUCKET, COPPER_PUFFERFISH_BUCKET, COPPER_SALMON_BUCKET, COPPER_COD_BUCKET,
                    COPPER_TROPICAL_FISH_BUCKET, COPPER_AXOLOTL_BUCKET, COPPER_TADPOLE_BUCKET, COPPER_SULFUR_CUBE_BUCKET,
                    SILVER_BUCKET, SILVER_WATER_BUCKET, SILVER_LAVA_BUCKET, SILVER_MILK_BUCKET,
                    SILVER_POWDER_SNOW_BUCKET, SILVER_PUFFERFISH_BUCKET, SILVER_SALMON_BUCKET, SILVER_COD_BUCKET,
                    SILVER_TROPICAL_FISH_BUCKET, SILVER_AXOLOTL_BUCKET, SILVER_TADPOLE_BUCKET, SILVER_SULFUR_CUBE_BUCKET);

            output.insertAfter(Items.MILK_BUCKET,
                    GOLD_BUCKET, GOLD_WATER_BUCKET, GOLD_LAVA_BUCKET, GOLD_MILK_BUCKET,
                    GOLD_POWDER_SNOW_BUCKET, GOLD_PUFFERFISH_BUCKET, GOLD_SALMON_BUCKET, GOLD_COD_BUCKET,
                    GOLD_TROPICAL_FISH_BUCKET, GOLD_AXOLOTL_BUCKET, GOLD_TADPOLE_BUCKET, GOLD_SULFUR_CUBE_BUCKET,
                    ANCIENT_METAL_BUCKET, ANCIENT_METAL_WATER_BUCKET, ANCIENT_METAL_LAVA_BUCKET, ANCIENT_METAL_MILK_BUCKET,
                    ANCIENT_METAL_POWDER_SNOW_BUCKET, ANCIENT_METAL_PUFFERFISH_BUCKET, ANCIENT_METAL_SALMON_BUCKET, ANCIENT_METAL_COD_BUCKET,
                    ANCIENT_METAL_TROPICAL_FISH_BUCKET, ANCIENT_METAL_AXOLOTL_BUCKET, ANCIENT_METAL_TADPOLE_BUCKET, ANCIENT_METAL_SULFUR_CUBE_BUCKET,
                    MITHRIL_BUCKET, MITHRIL_WATER_BUCKET, MITHRIL_LAVA_BUCKET, MITHRIL_MILK_BUCKET,
                    MITHRIL_POWDER_SNOW_BUCKET, MITHRIL_PUFFERFISH_BUCKET, MITHRIL_SALMON_BUCKET, MITHRIL_COD_BUCKET,
                    MITHRIL_TROPICAL_FISH_BUCKET, MITHRIL_AXOLOTL_BUCKET, MITHRIL_TADPOLE_BUCKET, MITHRIL_SULFUR_CUBE_BUCKET,
                    ADAMANTIUM_BUCKET, ADAMANTIUM_WATER_BUCKET, ADAMANTIUM_LAVA_BUCKET, ADAMANTIUM_MILK_BUCKET,
                    ADAMANTIUM_POWDER_SNOW_BUCKET, ADAMANTIUM_PUFFERFISH_BUCKET, ADAMANTIUM_SALMON_BUCKET, ADAMANTIUM_COD_BUCKET,
                    ADAMANTIUM_TROPICAL_FISH_BUCKET, ADAMANTIUM_AXOLOTL_BUCKET, ADAMANTIUM_TADPOLE_BUCKET, ADAMANTIUM_SULFUR_CUBE_BUCKET,
                    NETHERITE_BUCKET, NETHERITE_WATER_BUCKET, NETHERITE_LAVA_BUCKET, NETHERITE_MILK_BUCKET,
                    NETHERITE_POWDER_SNOW_BUCKET, NETHERITE_PUFFERFISH_BUCKET, NETHERITE_SALMON_BUCKET, NETHERITE_COD_BUCKET,
                    NETHERITE_TROPICAL_FISH_BUCKET, NETHERITE_AXOLOTL_BUCKET, NETHERITE_TADPOLE_BUCKET, NETHERITE_SULFUR_CUBE_BUCKET);
        });

        // === Food & Drinks ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> {
            // Fruits after glow berries
            output.insertAfter(Items.GLOW_BERRIES, BLUE_BERRIE, BANANA, ORANGE, LEMON);

            // Onion after beetroot
            output.insertAfter(Items.BEETROOT, ONION);

            // Worms after cooked rabbit
            output.insertAfter(Items.COOKED_RABBIT, WORM_RAW, WORM_COOKED);

            // Ingredients after pumpkin pie
            output.insertAfter(Items.PUMPKIN_PIE, CHEESE, CHOCOLATE, FLOUR, DOUGH);

            // Bowl foods after rabbit stew
            output.insertAfter(Items.RABBIT_STEW,
                    BEEF_STEW, BOWL_MILK, BOWL_SALAD, BOWL_WATER, CEREAL, CHICKEN_SOUP,
                    CREAM_OF_MUSHROOM_SOUP, CREAM_OF_VEGETABLE_SOUP, ICE_CREAM, MASHED_POTATO,
                    PORRIDGE, PUMPKIN_SOUP, SORBET, VEGETABLE_SOUP);
        });


        // === Ingredients: shards, raw ores, ingots, nuggets, chains, coins ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            // Shards before the vanilla amethyst shard
            output.insertBefore(Items.AMETHYST_SHARD, FLINT_SHARD, OBSIDIAN_SHARD);

            // Raw ores by material
            output.insertAfter(Items.RAW_COPPER, RAW_SILVER);
            output.insertAfter(Items.RAW_GOLD, RAW_MITHRIL, RAW_ADAMANTIUM);

            // Nuggets by material
            output.insertAfter(Items.COPPER_NUGGET, SILVER_NUGGET);
            output.insertAfter(Items.GOLD_NUGGET,
                    ANCIENT_METAL_NUGGET, MITHRIL_NUGGET, ADAMANTIUM_NUGGET, NETHERITE_NUGGET);

            // Ingots by material
            output.insertAfter(Items.COPPER_INGOT, SILVER_INGOT);
            output.insertAfter(Items.GOLD_INGOT, ANCIENT_METAL_INGOT, MITHRIL_INGOT, ADAMANTIUM_INGOT);

            // Sinew before the vanilla string
            output.insertBefore(Items.STRING, SINEW);

            // Manure after the vanilla rabbit hide
            output.insertAfter(Items.RABBIT_HIDE, MANURE);

            // Chains after the netherite ingot
            output.insertAfter(Items.NETHERITE_INGOT,
                    COPPER_CHAINS, SILVER_CHAINS, RUSTED_IRON_CHAINS, IRON_CHAINS,
                    GOLDEN_CHAINS, ANCIENT_METAL_CHAINS, MITHRIL_CHAINS, ADAMANTIUM_CHAINS);

            // Coins after the nuggets
            output.insertAfter(NETHERITE_NUGGET,
                    COPPER_COINS, SILVER_COINS, IRON_COINS, GOLDEN_COINS,
                    ANCIENT_METAL_COINS, MITHRIL_COINS, ADAMANTIUM_COINS, NETHERITE_COINS);
        });

        // === Spawn Eggs ===
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> {
            output.accept(GHOUL_SPAWN_EGG);
            output.accept(SHADOW_SPAWN_EGG);
            output.accept(WIGHT_SPAWN_EGG);
            output.accept(INVISIBLE_STALKER_SPAWN_EGG);
            output.accept(DEMON_SPIDER_SPAWN_EGG);
            output.accept(PHASE_SPIDER_SPAWN_EGG);
            output.accept(INFERNAL_CREEPER_SPAWN_EGG);
            output.accept(FIRE_ELEMENTAL_SPAWN_EGG);
            output.accept(VAMPIRE_BAT_SPAWN_EGG);
            output.accept(NIGHTWING_SPAWN_EGG);
            output.accept(GIANT_VAMPIRE_BAT_SPAWN_EGG);
        });
    }
}
