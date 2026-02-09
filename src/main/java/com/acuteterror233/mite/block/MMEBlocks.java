package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.block.entity.GradeFurnaceBlockEntity;
import com.acuteterror233.mite.block.entity.RunePortalBlockEntity;
import com.acuteterror233.mite.component.MMEDataComponentTypes;
import com.acuteterror233.mite.inventory.GradeAnvilMenu;
import com.acuteterror233.mite.inventory.GradeCraftingTableMenu;
import com.acuteterror233.mite.inventory.GradeFurnaceMenu;
import com.acuteterror233.mite.inventory.MMEEnchantmentMenu;
import com.acuteterror233.mite.item.MMEToolMaterials;
import com.acuteterror233.mite.registry.tag.MMETags;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.BiFunction;
import java.util.function.Function;

public class MMEBlocks {
    public static final Block ADAMANTIUM_ORE = register(        //艾德曼矿
            "adamantium_ore",
            BlockBehaviour.Properties.of().strength(5.0f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block MITHRIL_ORE = register(           //秘银矿
            "mithril_ore",
            BlockBehaviour.Properties.of().strength(4.0f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block SILVER_ORE = register(            //银矿
            "silver_ore",
            BlockBehaviour.Properties.of().strength(3.0f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Block DEEPSLATE_ADAMANTIUM_ORE = register(        //深层艾德曼矿
            "deepslate_adamantium_ore",
            BlockBehaviour.Properties.of().strength(4.5f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block DEEPSLATE_MITHRIL_ORE = register(           //深层秘银矿
            "deepslate_mithril_ore",
            BlockBehaviour.Properties.of().strength(4.5f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block DEEPSLATE_SILVER_ORE = register(            //深层银矿
            "deepslate_silver_ore",
            BlockBehaviour.Properties.of().strength(4.5f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Block ADAMANTIUM_BARS = register(       //艾德曼栏杆
            "adamantium_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(10f, 10f).noOcclusion().requiresCorrectToolForDrops().requiresCorrectToolForDrops()
    );
    public static final Block MITHRIL_BARS = register(          //秘银栏杆
            "mithril_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ADAMANTIUM_BARS).strength(8f, 8f).noOcclusion().requiresCorrectToolForDrops().requiresCorrectToolForDrops()
    );
    public static final Block ANCIENT_METAL_BARS = register(    //远古金属栏杆
            "ancient_metal_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ADAMANTIUM_BARS).strength(8f, 8f).noOcclusion().requiresCorrectToolForDrops().requiresCorrectToolForDrops()
    );
    public static final Block GOLDEN_BARS = register(             //金栏杆
            "golden_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ADAMANTIUM_BARS).strength(2f, 2f).noOcclusion().requiresCorrectToolForDrops().requiresCorrectToolForDrops()
    );
    public static final Block SILVER_BARS = register(           //银栏杆
            "silver_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ADAMANTIUM_BARS).strength(4f, 4f).noOcclusion().requiresCorrectToolForDrops().requiresCorrectToolForDrops()
    );
    public static final Block COPPER_BARS = register(           //铜栏杆
            "copper_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ADAMANTIUM_BARS).strength(4f, 4f).noOcclusion().requiresCorrectToolForDrops().requiresCorrectToolForDrops()
    );

    public static final Block ADAMANTIUM_BLOCK = register(      //艾德曼块
            "adamantium_block", BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 1350)
    );
    public static final Block ANCIENT_METAL_BLOCK = register(   //远古金属块
            "ancient_metal_block", BlockBehaviour.Properties.of().strength(35f, 35f).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 540)
    );
    public static final Block MITHRIL_BLOCK = register(         //秘银块
            "mithril_block", BlockBehaviour.Properties.of().strength(40f, 40f).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 810)
    );
    public static final Block SILVER_BLOCK = register(          //银块
            "silver_block", BlockBehaviour.Properties.of().strength(10f, 10f).requiresCorrectToolForDrops(), new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 90)
    );

    public static final Block CLAY_FURNACE = register(
            "clay_furnace", settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block HARDENED_CLAY_FURNACE = register(
            "hardened_clay_furnace", settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block NETHERRACK_FURNACE = register(
            "netherrack_furnace", settings -> new GradeFurnaceBlock(settings, 4), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block OBSIDIAN_FURNACE = register(
            "obsidian_furnace", settings -> new GradeFurnaceBlock(settings, 3), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).strength(0.5F)
    );
    public static final Block SANDSTONE_FURNACE = register(
            "sandstone_furnace", settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).strength(0.5F)
    );

    public static final Block MANTLE = register(                //地幔
            "mantle", BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
    );

    public static final Block ADAMANTIUM_DOOR = register(
            "adamantium_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0F).noOcclusion().requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY),
            DoubleHighBlockItem::new
    );
    public static final Block ANCIENT_METAL_DOOR = register(
            "ancient_metal_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0F).noOcclusion().requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY),
            DoubleHighBlockItem::new
    );
    public static final Block MITHRIL_DOOR = register(
            "mithril_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0F).noOcclusion().requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY),
            DoubleHighBlockItem::new
    );
    public static final Block SILVER_DOOR = register(
            "silver_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0F).noOcclusion().requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY),
            DoubleHighBlockItem::new
    );
    public static final Block GOLDEN_DOOR = register(
            "golden_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0F).noOcclusion().requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY),
            DoubleHighBlockItem::new
    );

    public static final Block DAMAGED_ADAMANTIUM_ANVIL = register(
            "damaged_adamantium_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block CHIPPED_ADAMANTIUM_ANVIL = register(
            "chipped_adamantium_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block ADAMANTIUM_ANVIL = register(
            "adamantium_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block DAMAGED_MITHRIL_ANVIL = register(
            "damaged_mithril_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.MITHRIL_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block CHIPPED_MITHRIL_ANVIL = register(
            "chipped_mithril_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.MITHRIL_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block MITHRIL_ANVIL = register(
            "mithril_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.MITHRIL_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block DAMAGED_ANCIENT_METAL_ANVIL = register(
            "damaged_ancient_metal_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block CHIPPED_ANCIENT_METAL_ANVIL = register(
            "chipped_ancient_metal_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block ANCIENT_METAL_ANVIL = register(
            "ancient_metal_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block DAMAGED_GOLDEN_ANVIL = register(
            "damaged_golden_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.GOLD_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block CHIPPED_GOLDEN_ANVIL = register(
            "chipped_golden_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.GOLD_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block GOLDEN_ANVIL = register(
            "golden_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.GOLD_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block DAMAGED_SILVER_ANVIL = register(
            "damaged_silver_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block CHIPPED_SILVER_ANVIL = register(
            "chipped_silver_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block SILVER_ANVIL = register(
            "silver_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block DAMAGED_COPPER_ANVIL = register(
            "damaged_copper_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );
    public static final Block CHIPPED_COPPER_ANVIL = register(
            "chipped_copper_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );
    public static final Block COPPER_ANVIL = register(
            "copper_anvil",
            settings -> new MMEAnvilBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );

    public static final Block UNDERGROUND_PORTAL = register(
            "underground_portal",
            UndergroundPortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );
    public static final Block HOME_PORTAL = register(
            "home_portal",
            HomePortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );
    public static final Block RUNE_PORTAL = register(
            "rune_portal",
            RunePortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );

    public static final Block MITHRIL_NUL_RUNESTORE = register(
            "mithril_nul_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_QUAS_RUNESTORE = register(
            "mithril_quas_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_POR_RUNESTORE = register(
            "mithril_por_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_AN_RUNESTORE = register(
            "mithril_an_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_NOX_RUNESTORE = register(
            "mithril_nox_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_FLAM_RUNESTORE = register(
            "mithril_flam_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_VAS_RUNESTORE = register(
            "mithril_vas_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_DES_RUNESTORE = register(
            "mithril_des_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_ORT_RUNESTORE = register(
            "mithril_ort_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_TYM_RUNESTORE = register(
            "mithril_tym_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_CORP_RUNESTORE = register(
            "mithril_corp_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_LOR_RUNESTORE = register(
            "mithril_lor_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_MANI_RUNESTORE = register(
            "mithril_mani_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_JUX_RUNESTORE = register(
            "mithril_jux_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_YLEM_RUNESTORE = register(
            "mithril_ylem_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block MITHRIL_SANCT_RUNESTORE = register(
            "mithril_sanct_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );

    public static final Block ADAMANTIUM_NUL_RUNESTORE = register(
            "adamantium_nul_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_QUAS_RUNESTORE = register(
            "adamantium_quas_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_POR_RUNESTORE = register(
            "adamantium_por_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_AN_RUNESTORE = register(
            "adamantium_an_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_NOX_RUNESTORE = register(
            "adamantium_nox_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_FLAM_RUNESTORE = register(
            "adamantium_flam_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_VAS_RUNESTORE = register(
            "adamantium_vas_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_DES_RUNESTORE = register(
            "adamantium_des_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_ORT_RUNESTORE = register(
            "adamantium_ort_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_TYM_RUNESTORE = register(
            "adamantium_tym_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_CORP_RUNESTORE = register(
            "adamantium_corp_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_LOR_RUNESTORE = register(
            "adamantium_lor_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_MANI_RUNESTORE = register(
            "adamantium_mani_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_JUX_RUNESTORE = register(
            "adamantium_jux_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_YLEM_RUNESTORE = register(
            "adamantium_ylem_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );
    public static final Block ADAMANTIUM_SANCT_RUNESTORE = register(
            "adamantium_sanct_runestore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
    );

    public static final Block ADAMANTIUM_CRAFTING_TABLE = register(
            "adamantium_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.ADAMANTIUM_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block MITHRIL_CRAFTING_TABLE = register(
            "mithril_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.MITHRIL_NOT_ALLOWED_MATERIAL, ADAMANTIUM_CRAFTING_TABLE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block ANCIENT_METAL_CRAFTING_TABLE = register(
            "ancient_metal_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, MITHRIL_CRAFTING_TABLE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block IRON_CRAFTING_TABLE = register(
            "iron_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.IRON_NOT_ALLOWED_MATERIAL, ANCIENT_METAL_CRAFTING_TABLE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block COPPER_CRAFTING_TABLE = register(
            "copper_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, IRON_CRAFTING_TABLE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block SILVER_CRAFTING_TABLE = register(
            "silver_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, IRON_CRAFTING_TABLE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block GOLD_CRAFTING_TABLE = register(
            "gold_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.GOLD_NOT_ALLOWED_MATERIAL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block FLINT_CRAFTING_TABLE = register(
            "flint_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.GOLD_NOT_ALLOWED_MATERIAL, COPPER_CRAFTING_TABLE, SILVER_CRAFTING_TABLE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block OBSIDIAN_CRAFTING_TABLE = register(
            "obsidian_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMETags.GOLD_NOT_ALLOWED_MATERIAL, COPPER_CRAFTING_TABLE, SILVER_CRAFTING_TABLE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );

    public static final Block EMERALD_ENCHANTING_TABLE = register(
            "emerald_enchanting_table",
            EmeraldEnchantingTableBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE)
    );

    public static int maxDamageAnvil(int damage) {
        return damage * 31;
    }

    public static Block register(String id, BlockBehaviour.Properties settings) {
        return register(id, Block::new, settings);
    }
    public static Block register(String id, BlockBehaviour.Properties blockSettings, Item.Properties itemSettings) {
        return register(id, Block::new, blockSettings, itemSettings);
    }
    public static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return register(id, factory, settings, new Item.Properties());
    }
    public static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, BiFunction<Block, Item.Properties, Item> factory1) {
        Block block = Blocks.register(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id)), factory, settings);
        Items.registerBlock(block, factory1);
        return block;
    }
    public static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, Item.Properties itemSettings) {
        Block block = Blocks.register(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id)), factory, settings);
        Items.registerBlock(block, itemSettings.useBlockDescriptionPrefix());
        return block;
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static final BlockEntityType<AnvilBlockEntity> ANVIL_BLOCK_ENTITY =
            register(
                    "anvil"
                    , AnvilBlockEntity::new
                    , ADAMANTIUM_ANVIL
                    , CHIPPED_ADAMANTIUM_ANVIL
                    , DAMAGED_ADAMANTIUM_ANVIL
                    , MITHRIL_ANVIL
                    , CHIPPED_MITHRIL_ANVIL
                    , DAMAGED_MITHRIL_ANVIL
                    , ANCIENT_METAL_ANVIL
                    , CHIPPED_ANCIENT_METAL_ANVIL
                    , DAMAGED_ANCIENT_METAL_ANVIL
                    , Blocks.ANVIL
                    , Blocks.CHIPPED_ANVIL
                    , Blocks.DAMAGED_ANVIL
                    , SILVER_ANVIL
                    , CHIPPED_SILVER_ANVIL
                    , DAMAGED_SILVER_ANVIL
                    , COPPER_ANVIL
                    , CHIPPED_COPPER_ANVIL
                    , DAMAGED_COPPER_ANVIL
                    , GOLDEN_ANVIL
                    , CHIPPED_GOLDEN_ANVIL
                    , DAMAGED_GOLDEN_ANVIL
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

    private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, id, new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    public static final MenuType<GradeAnvilMenu> GRADE_ANVIL = register("grade_anvil", GradeAnvilMenu::new);
    public static final MenuType<GradeCraftingTableMenu> GRADE_CRAFTING_TABLE = register("grade_crafting_table", GradeCraftingTableMenu::new);
    public static final MenuType<GradeFurnaceMenu> GRADE_FURNACE = register("grade_furnace", GradeFurnaceMenu::new);
    public static final MenuType<MMEEnchantmentMenu> MME_ENCHANTMENT = register("mme_enchantment", MMEEnchantmentMenu::new);
    public static void init() {

    }
}
