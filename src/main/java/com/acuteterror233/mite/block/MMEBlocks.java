package com.acuteterror233.mite.block;

import com.acuteterror233.mite.component.MMEDataComponents;
import com.acuteterror233.mite.item.MMEToolMaterials;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * MME 模组方块注册中心。
 * 定义并注册所有自定义方块及其对应的方块实体、菜单类型。
 */
public class MMEBlocks {
    public static final Block ADAMANTIUM_ORE = register(        //艾德曼矿
            MMEBlockItemIds.ADAMANTIUM_ORE,
            BlockBehaviour.Properties.of().strength(5.0f, 10.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block MITHRIL_ORE = register(           //秘银矿
            MMEBlockItemIds.MITHRIL_ORE,
            BlockBehaviour.Properties.of().strength(4.0f, 5.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block SILVER_ORE = register(            //银矿
            MMEBlockItemIds.SILVER_ORE,
            BlockBehaviour.Properties.of().strength(3.0f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Block DEEPSLATE_ADAMANTIUM_ORE = register(        //深层艾德曼矿
            MMEBlockItemIds.DEEPSLATE_ADAMANTIUM_ORE,
            BlockBehaviour.Properties.of().strength(5.5f, 10.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block DEEPSLATE_MITHRIL_ORE = register(           //深层秘银矿
            MMEBlockItemIds.DEEPSLATE_MITHRIL_ORE,
            BlockBehaviour.Properties.of().strength(4.5f, 5.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block DEEPSLATE_SILVER_ORE = register(            //深层银矿
            MMEBlockItemIds.DEEPSLATE_SILVER_ORE,
            BlockBehaviour.Properties.of().strength(4.5f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Block ADAMANTIUM_BLOCK = register(      //艾德曼块
            MMEBlockItemIds.ADAMANTIUM_BLOCK, BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 1350)
    );
    public static final Block ANCIENT_METAL_BLOCK = register(   //远古金属块
            MMEBlockItemIds.ANCIENT_METAL_BLOCK, BlockBehaviour.Properties.of().strength(35f, 35f).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 540)
    );
    public static final Block MITHRIL_BLOCK = register(         //秘银块
            MMEBlockItemIds.MITHRIL_BLOCK, BlockBehaviour.Properties.of().strength(40f, 40f).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 810)
    );
    public static final Block SILVER_BLOCK = register(          //银块
            MMEBlockItemIds.SILVER_BLOCK, BlockBehaviour.Properties.of().strength(10f, 10f).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 90)
    );

    public static final Block CLAY_FURNACE = register(
            MMEBlockItemIds.CLAY_FURNACE, settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block HARDENED_CLAY_FURNACE = register(
            MMEBlockItemIds.HARDENED_CLAY_FURNACE, settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block NETHERRACK_FURNACE = register(
            MMEBlockItemIds.NETHERRACK_FURNACE, settings -> new GradeFurnaceBlock(settings, 4), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block OBSIDIAN_FURNACE = register(
            MMEBlockItemIds.OBSIDIAN_FURNACE, settings -> new GradeFurnaceBlock(settings, 3), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block SANDSTONE_FURNACE = register(
            MMEBlockItemIds.SANDSTONE_FURNACE, settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );

    public static final Block MANTLE = register(                //地幔
            MMEBlockItemIds.MANTLE,
            MagmaBlock::new,
            BlockBehaviour.Properties
                    .ofFullCopy(Blocks.BEDROCK)
                    .lightLevel(blockStatex -> 3)
                    .isValidSpawn((blockStatex, blockGetter, blockPos, entityType) -> entityType.fireImmune())
                    .postProcess((blockStatex, blockGetter, blockPos) -> blockPos.above())
                    .emissiveRendering(state -> true)
    );

    public static final Block DAMAGED_NETHERITE_ANVIL = register(
            MMEBlockItemIds.DAMAGED_NETHERITE_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.NETHERITE.durability()))
    );
    public static final Block CHIPPED_NETHERITE_ANVIL = register(
            MMEBlockItemIds.CHIPPED_NETHERITE_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL, DAMAGED_NETHERITE_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.NETHERITE.durability()))
    );
    public static final Block NETHERITE_ANVIL = register(
            MMEBlockItemIds.NETHERITE_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL, CHIPPED_NETHERITE_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.NETHERITE.durability()))
    );
    public static final Block DAMAGED_ADAMANTIUM_ANVIL = register(
            MMEBlockItemIds.DAMAGED_ADAMANTIUM_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block CHIPPED_ADAMANTIUM_ANVIL = register(
            MMEBlockItemIds.CHIPPED_ADAMANTIUM_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, DAMAGED_ADAMANTIUM_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block ADAMANTIUM_ANVIL = register(
            MMEBlockItemIds.ADAMANTIUM_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, CHIPPED_ADAMANTIUM_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block DAMAGED_MITHRIL_ANVIL = register(
            MMEBlockItemIds.DAMAGED_MITHRIL_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block CHIPPED_MITHRIL_ANVIL = register(
            MMEBlockItemIds.CHIPPED_MITHRIL_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, DAMAGED_MITHRIL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block MITHRIL_ANVIL = register(
            MMEBlockItemIds.MITHRIL_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, CHIPPED_MITHRIL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block DAMAGED_ANCIENT_METAL_ANVIL = register(
            MMEBlockItemIds.DAMAGED_ANCIENT_METAL_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block CHIPPED_ANCIENT_METAL_ANVIL = register(
            MMEBlockItemIds.CHIPPED_ANCIENT_METAL_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, DAMAGED_ANCIENT_METAL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block ANCIENT_METAL_ANVIL = register(
            MMEBlockItemIds.ANCIENT_METAL_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, CHIPPED_ANCIENT_METAL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block DAMAGED_GOLDEN_ANVIL = register(
            MMEBlockItemIds.DAMAGED_GOLDEN_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block CHIPPED_GOLDEN_ANVIL = register(
            MMEBlockItemIds.CHIPPED_GOLDEN_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, DAMAGED_GOLDEN_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block GOLDEN_ANVIL = register(
            MMEBlockItemIds.GOLDEN_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, CHIPPED_GOLDEN_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block DAMAGED_SILVER_ANVIL = register(
            MMEBlockItemIds.DAMAGED_SILVER_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block CHIPPED_SILVER_ANVIL = register(
            MMEBlockItemIds.CHIPPED_SILVER_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, DAMAGED_SILVER_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block SILVER_ANVIL = register(
            MMEBlockItemIds.SILVER_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, CHIPPED_SILVER_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block DAMAGED_COPPER_ANVIL = register(
            MMEBlockItemIds.DAMAGED_COPPER_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );
    public static final Block CHIPPED_COPPER_ANVIL = register(
            MMEBlockItemIds.CHIPPED_COPPER_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, DAMAGED_COPPER_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );
    public static final Block COPPER_ANVIL = register(
            MMEBlockItemIds.COPPER_ANVIL,
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, CHIPPED_COPPER_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );

    public static final Block UNDERGROUND_PORTAL = registerNoItem(
            MMEBlockIds.UNDERGROUND_PORTAL,
            UndergroundPortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );
    public static final Block HOME_PORTAL = registerNoItem(
            MMEBlockIds.HOME_PORTAL,
            HomePortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );
    public static final Block RUNE_PORTAL = registerNoItem(
            MMEBlockIds.RUNE_PORTAL,
            RunePortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );

    public static final Block MITHRIL_NUL_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_NUL_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_QUAS_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_QUAS_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_POR_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_POR_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_AN_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_AN_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_NOX_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_NOX_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_FLAM_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_FLAM_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_VAS_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_VAS_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_DES_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_DES_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_ORT_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_ORT_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_TYM_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_TYM_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_CORP_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_CORP_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_LOR_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_LOR_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_MANI_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_MANI_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_JUX_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_JUX_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_YLEM_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_YLEM_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_SANCT_RUNESTORE = register(
            MMEBlockItemIds.MITHRIL_SANCT_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );

    public static final Block ADAMANTIUM_NUL_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_NUL_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_QUAS_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_QUAS_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_POR_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_POR_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_AN_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_AN_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_NOX_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_NOX_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_FLAM_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_FLAM_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_VAS_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_VAS_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_DES_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_DES_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_ORT_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_ORT_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_TYM_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_TYM_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_CORP_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_CORP_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_LOR_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_LOR_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_MANI_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_MANI_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_JUX_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_JUX_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_YLEM_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_YLEM_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_SANCT_RUNESTORE = register(
            MMEBlockItemIds.ADAMANTIUM_SANCT_RUNESTORE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );

    public static final Block ADAMANTIUM_CRAFTING_TABLE = register(
            MMEBlockItemIds.ADAMANTIUM_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, MMEItemTags.ADAMANTIUM_CRAFTING_TABLE_EXCEPTIONS, 0.18f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block MITHRIL_CRAFTING_TABLE = register(
            MMEBlockItemIds.MITHRIL_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, MMEItemTags.MITHRIL_CRAFTING_TABLE_EXCEPTIONS, 0.15f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block ANCIENT_METAL_CRAFTING_TABLE = register(
            MMEBlockItemIds.ANCIENT_METAL_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, MMEItemTags.ANCIENT_METAL_CRAFTING_TABLE_EXCEPTIONS, 0.12f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block IRON_CRAFTING_TABLE = register(
            MMEBlockItemIds.IRON_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.IRON_NOT_ALLOWED_MATERIAL, MMEItemTags.IRON_CRAFTING_TABLE_EXCEPTIONS, 0.09f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block COPPER_CRAFTING_TABLE = register(
            MMEBlockItemIds.COPPER_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, MMEItemTags.COPPER_CRAFTING_TABLE_EXCEPTIONS, 0.06f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block SILVER_CRAFTING_TABLE = register(
            MMEBlockItemIds.SILVER_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, MMEItemTags.SILVER_CRAFTING_TABLE_EXCEPTIONS, 0.06f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block GOLD_CRAFTING_TABLE = register(
            MMEBlockItemIds.GOLD_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.GOLD_CRAFTING_TABLE_EXCEPTIONS, 0.03f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block FLINT_CRAFTING_TABLE = register(
            MMEBlockItemIds.FLINT_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.FLINT_CRAFTING_TABLE_EXCEPTIONS, 0.03f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block OBSIDIAN_CRAFTING_TABLE = register(
            MMEBlockItemIds.OBSIDIAN_CRAFTING_TABLE,
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.OBSIDIAN_CRAFTING_TABLE_EXCEPTIONS, 0.03f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );

    public static final Block EMERALD_ENCHANTING_TABLE = register(
            MMEBlockItemIds.EMERALD_ENCHANTING_TABLE,
            EmeraldEnchantingTableBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE)
    );

    public static final Block BLUE_BERRY_BUSH = registerNoItem(
            MMEBlockIds.BLUE_BERRY_BUSH,
            BlueBerryBushBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)
    );

    public static int maxDamageAnvil(int damage) {
        return damage * 40 * 2 / 3;
    }

    public static Block register(BlockItemId blockItemId, BlockBehaviour.Properties settings) {
        return register(blockItemId, Block::new, settings);
    }
    public static Block register(BlockItemId blockItemId, BlockBehaviour.Properties blockSettings, Item.Properties itemSettings) {
        return register(blockItemId, Block::new, blockSettings, itemSettings);
    }
    public static Block register(BlockItemId blockItemId, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return register(blockItemId, factory, settings, new Item.Properties());
    }
    public static Block registerNoItem(ResourceKey<Block> registryKey, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return Blocks.register(registryKey, factory, settings);
    }
    public static Block register(BlockItemId blockItemId, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, BiFunction<Block, Item.Properties, Item> factory1) {
        Block block = Blocks.register(blockItemId.blockKey(), factory, settings);
        registerBlockItem(block, blockItemId.itemKey(), factory1, new Item.Properties());
        return block;
    }
    public static Block register(BlockItemId blockItemId, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, Item.Properties itemSettings) {
        Block block = Blocks.register(blockItemId.blockKey(), factory, settings);
        registerBlockItem(block, blockItemId.itemKey(), BlockItem::new, itemSettings);
        return block;
    }

    private static void registerBlockItem(Block block, ResourceKey<Item> itemKey, BiFunction<Block, Item.Properties, Item> itemFactory, Item.Properties itemSettings) {
        Item item = itemFactory.apply(block, itemSettings.useBlockDescriptionPrefix().requiredFeatures(block.requiredFeatures()).setId(itemKey));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
    }

    public static void init() {
        MMEBlockEntityTypes.init();
        MMEMenuTypes.init();
    }
}
