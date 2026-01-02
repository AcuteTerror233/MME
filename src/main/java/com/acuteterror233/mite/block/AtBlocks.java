package com.acuteterror233.mite.block;

import com.acuteterror233.mite.Mme;
import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.block.entity.GradeFurnaceBlockEntity;
import com.acuteterror233.mite.block.entity.RunePortalBlockEntity;
import com.acuteterror233.mite.component.AtDataComponentTypes;
import com.acuteterror233.mite.item.AtToolMaterials;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.screen.AtAnvilScreenHandler;
import com.acuteterror233.mite.screen.GradeCraftingTableScreenHandler;
import com.acuteterror233.mite.screen.GradeFurnaceScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 方块注册中心：定义并注册模组中的全部方块、方块实体与界面处理器。
 *
 * <p>统一封装多种 register 重载，
 * 以便在创建方块时同步完成物品注册与自定义 {@link Item.Settings} 配置。</p>
 */
public class AtBlocks {
    public static final Block ADAMANTIUM_ORE = register(        //艾德曼矿
            "adamantium_ore",
            AbstractBlock.Settings.create().strength(5.0f, 3.0f).requiresTool(),
            new Item.Settings().component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block MITHRIL_ORE = register(           //秘银矿
            "mithril_ore",
            AbstractBlock.Settings.create().strength(4.0f, 3.0f).requiresTool(),
            new Item.Settings().component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block SILVER_ORE = register(            //银矿
            "silver_ore",
            AbstractBlock.Settings.create().strength(3.0f, 3.0f).requiresTool(),
            new Item.Settings().component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Block DEEPSLATE_ADAMANTIUM_ORE = register(        //深层艾德曼矿
            "deepslate_adamantium_ore",
            AbstractBlock.Settings.create().strength(4.5f, 3.0f).requiresTool(),
            new Item.Settings().component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block DEEPSLATE_MITHRIL_ORE = register(           //深层秘银矿
            "deepslate_mithril_ore",
            AbstractBlock.Settings.create().strength(4.5f, 3.0f).requiresTool(),
            new Item.Settings().component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block DEEPSLATE_SILVER_ORE = register(            //深层银矿
            "deepslate_silver_ore",
            AbstractBlock.Settings.create().strength(4.5f, 3.0f).requiresTool(),
            new Item.Settings().component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Block ADAMANTIUM_BARS = register(       //艾德曼栏杆
            "adamantium_bars", PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS).strength(10f, 10f).nonOpaque().requiresTool().requiresTool()
    );
    public static final Block MITHRIL_BARS = register(          //秘银栏杆
            "mithril_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(8f, 8f).nonOpaque().requiresTool().requiresTool()
    );
    public static final Block ANCIENT_METAL_BARS = register(    //远古金属栏杆
            "ancient_metal_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(8f, 8f).nonOpaque().requiresTool().requiresTool()
    );
    public static final Block GOLDEN_BARS = register(             //金栏杆
            "golden_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(2f, 2f).nonOpaque().requiresTool().requiresTool()
    );
    public static final Block SILVER_BARS = register(           //银栏杆
            "silver_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(4f, 4f).nonOpaque().requiresTool().requiresTool()
    );
    public static final Block COPPER_BARS = register(           //铜栏杆
            "copper_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(4f, 4f).nonOpaque().requiresTool().requiresTool()
    );

    public static final Block ADAMANTIUM_BLOCK = register(      //艾德曼块
            "adamantium_block", AbstractBlock.Settings.copy(Blocks.BEDROCK).requiresTool(), new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 1350)
    );
    public static final Block ANCIENT_METAL_BLOCK = register(   //远古金属块
            "ancient_metal_block", AbstractBlock.Settings.create().strength(35f, 35f).requiresTool(), new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 540)
    );
    public static final Block MITHRIL_BLOCK = register(         //秘银块
            "mithril_block", AbstractBlock.Settings.create().strength(40f, 40f).requiresTool(), new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 810)
    );
    public static final Block SILVER_BLOCK = register(          //银块
            "silver_block", AbstractBlock.Settings.create().strength(10f, 10f).requiresTool(), new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 90)
    );

    public static final Block CLAY_FURNACE = register(
            "clay_furnace", settings -> new GradeFurnaceBlock(settings, 1), AbstractBlock.Settings.copy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block HARDENED_CLAY_FURNACE = register(
            "hardened_clay_furnace", settings -> new GradeFurnaceBlock(settings, 1), AbstractBlock.Settings.copy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block NETHERRACK_FURNACE = register(
            "netherrack_furnace", settings -> new GradeFurnaceBlock(settings, 4), AbstractBlock.Settings.copy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block OBSIDIAN_FURNACE = register(
            "obsidian_furnace", settings -> new GradeFurnaceBlock(settings, 3), AbstractBlock.Settings.copy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block SANDSTONE_FURNACE = register(
            "sandstone_furnace", settings -> new GradeFurnaceBlock(settings, 1), AbstractBlock.Settings.copy(Blocks.FURNACE).strength(0.5F)
    );

    public static final Block MANTLE = register(                //地幔
            "mantle", AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );

    public static final Block ADAMANTIUM_DOOR = register(
            "adamantium_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().requiresTool().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block ANCIENT_METAL_DOOR = register(
            "ancient_metal_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().requiresTool().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block MITHRIL_DOOR = register(
            "mithril_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().requiresTool().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block SILVER_DOOR = register(
            "silver_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().requiresTool().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block GOLDEN_DOOR = register(
            "golden_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().requiresTool().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );

    public static final Block DAMAGED_ADAMANTIUM_ANVIL = register(
            "damaged_adamantium_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block CHIPPED_ADAMANTIUM_ANVIL = register(
            "chipped_adamantium_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block ADAMANTIUM_ANVIL = register(
            "adamantium_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block DAMAGED_MITHRIL_ANVIL = register(
            "damaged_mithril_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.MITHRIL_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.MITHRIL.durability()))
    );
    public static final Block CHIPPED_MITHRIL_ANVIL = register(
            "chipped_mithril_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.MITHRIL_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.MITHRIL.durability()))
    );
    public static final Block MITHRIL_ANVIL = register(
            "mithril_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.MITHRIL_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.MITHRIL.durability()))
    );
    public static final Block DAMAGED_ANCIENT_METAL_ANVIL = register(
            "damaged_ancient_metal_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block CHIPPED_ANCIENT_METAL_ANVIL = register(
            "chipped_ancient_metal_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block ANCIENT_METAL_ANVIL = register(
            "ancient_metal_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block DAMAGED_GOLDEN_ANVIL = register(
            "damaged_golden_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.GOLD_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block CHIPPED_GOLDEN_ANVIL = register(
            "chipped_golden_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.GOLD_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block GOLDEN_ANVIL = register(
            "golden_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.GOLD_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block DAMAGED_SILVER_ANVIL = register(
            "damaged_silver_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.SILVER.durability()))
    );
    public static final Block CHIPPED_SILVER_ANVIL = register(
            "chipped_silver_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.SILVER.durability()))
    );
    public static final Block SILVER_ANVIL = register(
            "silver_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.SILVER.durability()))
    );
    public static final Block DAMAGED_COPPER_ANVIL = register(
            "damaged_copper_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.COPPER.durability()))
    );
    public static final Block CHIPPED_COPPER_ANVIL = register(
            "chipped_copper_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.COPPER.durability()))
    );
    public static final Block COPPER_ANVIL = register(
            "copper_anvil",
            settings -> new AtAnvilBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.COPPER.durability()))
    );
    public static final Map<Block, Block> ANVIL_MAP = new HashMap<>(){{
        put(ADAMANTIUM_ANVIL, CHIPPED_ADAMANTIUM_ANVIL);
        put(CHIPPED_ADAMANTIUM_ANVIL, DAMAGED_ADAMANTIUM_ANVIL);
        put(DAMAGED_ADAMANTIUM_ANVIL, Blocks.AIR);
        put(MITHRIL_ANVIL, CHIPPED_MITHRIL_ANVIL);
        put(CHIPPED_MITHRIL_ANVIL, DAMAGED_MITHRIL_ANVIL);
        put(DAMAGED_MITHRIL_ANVIL, Blocks.AIR);
        put(ANCIENT_METAL_ANVIL, CHIPPED_ANCIENT_METAL_ANVIL);
        put(CHIPPED_ANCIENT_METAL_ANVIL, DAMAGED_ANCIENT_METAL_ANVIL);
        put(DAMAGED_ANCIENT_METAL_ANVIL, Blocks.AIR);
        put(Blocks.ANVIL, Blocks.CHIPPED_ANVIL);
        put(Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
        put(Blocks.DAMAGED_ANVIL, Blocks.AIR);
        put(GOLDEN_ANVIL, CHIPPED_GOLDEN_ANVIL);
        put(CHIPPED_GOLDEN_ANVIL, DAMAGED_GOLDEN_ANVIL);
        put(DAMAGED_GOLDEN_ANVIL, Blocks.AIR);
        put(SILVER_ANVIL, CHIPPED_SILVER_ANVIL);
        put(CHIPPED_SILVER_ANVIL, DAMAGED_SILVER_ANVIL);
        put(DAMAGED_SILVER_ANVIL, Blocks.AIR);
        put(COPPER_ANVIL, CHIPPED_COPPER_ANVIL);
        put(CHIPPED_COPPER_ANVIL, DAMAGED_COPPER_ANVIL);
        put(DAMAGED_COPPER_ANVIL, Blocks.AIR);
    }};

    public static final Block UNDERGROUND_PORTAL = register(
            "underground_portal",
            UndergroundPortalBlock::new,
            AbstractBlock.Settings.copy(Blocks.NETHER_PORTAL)
    );
    public static final Block HOME_PORTAL = register(
            "home_portal",
            HomePortalBlock::new,
            AbstractBlock.Settings.copy(Blocks.NETHER_PORTAL)
    );
    public static final Block RUNE_PORTAL = register(
            "rune_portal",
            RunePortalBlock::new,
            AbstractBlock.Settings.copy(Blocks.NETHER_PORTAL)
    );

    public static final Block MITHRIL_NUL_RUNESTORE = register(
            "mithril_nul_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_QUAS_RUNESTORE = register(
            "mithril_quas_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_POR_RUNESTORE = register(
            "mithril_por_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_AN_RUNESTORE = register(
            "mithril_an_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_NOX_RUNESTORE = register(
            "mithril_nox_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_FLAM_RUNESTORE = register(
            "mithril_flam_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_VAS_RUNESTORE = register(
            "mithril_vas_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_DES_RUNESTORE = register(
            "mithril_des_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_ORT_RUNESTORE = register(
            "mithril_ort_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_TYM_RUNESTORE = register(
            "mithril_tym_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_CORP_RUNESTORE = register(
            "mithril_corp_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_LOR_RUNESTORE = register(
            "mithril_lor_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_MANI_RUNESTORE = register(
            "mithril_mani_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_JUX_RUNESTORE = register(
            "mithril_jux_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_YLEM_RUNESTORE = register(
            "mithril_ylem_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_SANCT_RUNESTORE = register(
            "mithril_sanct_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );

    public static final Block ADAMANTIUM_NUL_RUNESTORE = register(
            "adamantium_nul_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_QUAS_RUNESTORE = register(
            "adamantium_quas_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_POR_RUNESTORE = register(
            "adamantium_por_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_AN_RUNESTORE = register(
            "adamantium_an_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_NOX_RUNESTORE = register(
            "adamantium_nox_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_FLAM_RUNESTORE = register(
            "adamantium_flam_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_VAS_RUNESTORE = register(
            "adamantium_vas_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_DES_RUNESTORE = register(
            "adamantium_des_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_ORT_RUNESTORE = register(
            "adamantium_ort_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_TYM_RUNESTORE = register(
            "adamantium_tym_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_CORP_RUNESTORE = register(
            "adamantium_corp_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_LOR_RUNESTORE = register(
            "adamantium_lor_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_MANI_RUNESTORE = register(
            "adamantium_mani_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_JUX_RUNESTORE = register(
            "adamantium_jux_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_YLEM_RUNESTORE = register(
            "adamantium_ylem_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_SANCT_RUNESTORE = register(
            "adamantium_sanct_runestore",
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN)
    );

    public static final Block ADAMANTIUM_CRAFTING_TABLE = register(
            "adamantium_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block MITHRIL_CRAFTING_TABLE = register(
            "mithril_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.MITHRIL_NOT_ALLOWED_MATERIAL, ADAMANTIUM_CRAFTING_TABLE),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block ANCIENT_METAL_CRAFTING_TABLE = register(
            "ancient_metal_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, MITHRIL_CRAFTING_TABLE),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block IRON_CRAFTING_TABLE = register(
            "iron_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.IRON_NOT_ALLOWED_MATERIAL, ANCIENT_METAL_CRAFTING_TABLE),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block COPPER_CRAFTING_TABLE = register(
            "copper_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, IRON_CRAFTING_TABLE),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block SILVER_CRAFTING_TABLE = register(
            "silver_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, IRON_CRAFTING_TABLE),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block GOLD_CRAFTING_TABLE = register(
            "gold_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.GOLD_NOT_ALLOWED_MATERIAL),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block FLINT_CRAFTING_TABLE = register(
            "flint_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.GOLD_NOT_ALLOWED_MATERIAL, COPPER_CRAFTING_TABLE, SILVER_CRAFTING_TABLE),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );
    public static final Block OBSIDIAN_CRAFTING_TABLE = register(
            "obsidian_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, AtTags.GOLD_NOT_ALLOWED_MATERIAL, COPPER_CRAFTING_TABLE, SILVER_CRAFTING_TABLE),
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
    );

    /**
     * 计算铁砧物品的最大耐久。
     *
     * @param damage 基础耐久（通常来自工具材料）
     * @return 最大耐久值
     */
    public static int maxDamageAnvil(int damage) {
        return damage * 31;
    }

    public static Block register(String id, AbstractBlock.Settings settings) {
        return register(id, Block::new, settings);
    }
    public static Block register(String id, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
        return register(id, Block::new, blockSettings, itemSettings);
    }
    public static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return register(id, factory, settings, new Item.Settings());
    }
    public static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, BiFunction<Block, Item.Settings, Item> factory1) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Mme.MOD_ID, id)), factory, settings);
        Items.register(block, factory1);
        return block;
    }
    public static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Mme.MOD_ID, id)), factory, settings);
        Items.register(block, itemSettings.useBlockPrefixedTranslationKey());
        return block;
    }

    /**
     * 注册方块实体类型。
     */
    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.of(Mme.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static final BlockEntityType<AnvilBlockEntity> ANVIL_BLOCK_ENTITY =
            register(
                    "anvil"
                    , AnvilBlockEntity::new
                    , ADAMANTIUM_ANVIL
                    , CHIPPED_ADAMANTIUM_ANVIL
                    , DAMAGED_ADAMANTIUM_ANVIL
                    , SILVER_ANVIL
                    , CHIPPED_SILVER_ANVIL
                    , DAMAGED_SILVER_ANVIL
                    , COPPER_ANVIL
                    , CHIPPED_COPPER_ANVIL
                    , DAMAGED_COPPER_ANVIL
                    , Blocks.ANVIL
                    , Blocks.CHIPPED_ANVIL
                    , Blocks.DAMAGED_ANVIL
                    , GOLDEN_ANVIL
                    , CHIPPED_GOLDEN_ANVIL
                    , DAMAGED_GOLDEN_ANVIL
                    , MITHRIL_ANVIL
                    , CHIPPED_MITHRIL_ANVIL
                    , DAMAGED_MITHRIL_ANVIL
                    , ANCIENT_METAL_ANVIL
                    , CHIPPED_ANCIENT_METAL_ANVIL
                    , DAMAGED_ANCIENT_METAL_ANVIL
            );
    public static final BlockEntityType<RunePortalBlockEntity> RUNE_PORTAL_BLOCK_ENTITY =
            register(
                    "rune_portal"
                    , RunePortalBlockEntity::new
                    , RUNE_PORTAL
            );
    public static final BlockEntityType<GradeFurnaceBlockEntity> GRADE_FURNACE_BLOCK_ENTITY =
            register(
                    "grade_furnace"
                    , GradeFurnaceBlockEntity::new
                    , CLAY_FURNACE
                    , HARDENED_CLAY_FURNACE
                    , SANDSTONE_FURNACE
                    , OBSIDIAN_FURNACE
                    , NETHERRACK_FURNACE
                    , Blocks.FURNACE
                    , Blocks.SMOKER
            );

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static final ScreenHandlerType<AtAnvilScreenHandler> ATANVIL = register("at_anvil_screen_handler", AtAnvilScreenHandler::new);
    public static final ScreenHandlerType<GradeCraftingTableScreenHandler> CRAFTING_TABLE = register("cs", GradeCraftingTableScreenHandler::new);
    public static final ScreenHandlerType<GradeFurnaceScreenHandler> GRADE_FURNACE = register("grade_furnace", GradeFurnaceScreenHandler::new);

    /**
     * 预留初始化入口：当前无运行期逻辑，仅用于保持与其它注册流程一致。
     */
    public static void init() {

    }
}
