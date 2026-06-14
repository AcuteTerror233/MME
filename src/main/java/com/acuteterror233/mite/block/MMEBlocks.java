package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.component.MMEDataComponentTypes;
import com.acuteterror233.mite.item.MMEToolMaterials;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
            "adamantium_ore",
            BlockBehaviour.Properties.of().strength(5.0f, 10.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block MITHRIL_ORE = register(           //秘银矿
            "mithril_ore",
            BlockBehaviour.Properties.of().strength(4.0f, 5.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block SILVER_ORE = register(            //银矿
            "silver_ore",
            BlockBehaviour.Properties.of().strength(3.0f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
    );

    public static final Block DEEPSLATE_ADAMANTIUM_ORE = register(        //深层艾德曼矿
            "deepslate_adamantium_ore",
            BlockBehaviour.Properties.of().strength(5.5f, 10.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Block DEEPSLATE_MITHRIL_ORE = register(           //深层秘银矿
            "deepslate_mithril_ore",
            BlockBehaviour.Properties.of().strength(4.5f, 5.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Block DEEPSLATE_SILVER_ORE = register(            //深层银矿
            "deepslate_silver_ore",
            BlockBehaviour.Properties.of().strength(4.5f, 3.0f).requiresCorrectToolForDrops(),
            new Item.Properties().component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
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
            "clay_furnace", settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block HARDENED_CLAY_FURNACE = register(
            "hardened_clay_furnace", settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block NETHERRACK_FURNACE = register(
            "netherrack_furnace", settings -> new GradeFurnaceBlock(settings, 4), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block OBSIDIAN_FURNACE = register(
            "obsidian_furnace", settings -> new GradeFurnaceBlock(settings, 3), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block SANDSTONE_FURNACE = register(
            "sandstone_furnace", settings -> new GradeFurnaceBlock(settings, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );

    public static final Block MANTLE = register(                //地幔
            "mantle",
            MagmaBlock::new,
            BlockBehaviour.Properties
                    .ofFullCopy(Blocks.BEDROCK)
                    .lightLevel(blockStatex -> 3)
                    .isValidSpawn((blockStatex, blockGetter, blockPos, entityType) -> entityType.fireImmune())
                    .hasPostProcess(Blocks::always)
                    .emissiveRendering(Blocks::always)
    );

    public static final Block DAMAGED_NETHERITE_ANVIL = register(
            "damaged_netherite_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.NETHERITE.durability()))
    );
    public static final Block CHIPPED_NETHERITE_ANVIL = register(
            "chipped_netherite_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL, DAMAGED_NETHERITE_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.NETHERITE.durability()))
    );
    public static final Block NETHERITE_ANVIL = register(
            "netherite_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL, CHIPPED_NETHERITE_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.NETHERITE.durability()))
    );
    public static final Block DAMAGED_ADAMANTIUM_ANVIL = register(
            "damaged_adamantium_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block CHIPPED_ADAMANTIUM_ANVIL = register(
            "chipped_adamantium_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, DAMAGED_ADAMANTIUM_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block ADAMANTIUM_ANVIL = register(
            "adamantium_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, CHIPPED_ADAMANTIUM_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block DAMAGED_MITHRIL_ANVIL = register(
            "damaged_mithril_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block CHIPPED_MITHRIL_ANVIL = register(
            "chipped_mithril_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, DAMAGED_MITHRIL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block MITHRIL_ANVIL = register(
            "mithril_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, CHIPPED_MITHRIL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.MITHRIL.durability()))
    );
    public static final Block DAMAGED_ANCIENT_METAL_ANVIL = register(
            "damaged_ancient_metal_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block CHIPPED_ANCIENT_METAL_ANVIL = register(
            "chipped_ancient_metal_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, DAMAGED_ANCIENT_METAL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block ANCIENT_METAL_ANVIL = register(
            "ancient_metal_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, CHIPPED_ANCIENT_METAL_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block DAMAGED_GOLDEN_ANVIL = register(
            "damaged_golden_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block CHIPPED_GOLDEN_ANVIL = register(
            "chipped_golden_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, DAMAGED_GOLDEN_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block GOLDEN_ANVIL = register(
            "golden_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, CHIPPED_GOLDEN_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block DAMAGED_SILVER_ANVIL = register(
            "damaged_silver_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block CHIPPED_SILVER_ANVIL = register(
            "chipped_silver_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, DAMAGED_SILVER_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block SILVER_ANVIL = register(
            "silver_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, CHIPPED_SILVER_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.SILVER.durability()))
    );
    public static final Block DAMAGED_COPPER_ANVIL = register(
            "damaged_copper_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, Blocks.AIR),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );
    public static final Block CHIPPED_COPPER_ANVIL = register(
            "chipped_copper_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, DAMAGED_COPPER_ANVIL),
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL),
            new Item.Properties().durability(maxDamageAnvil(MMEToolMaterials.COPPER.durability()))
    );
    public static final Block COPPER_ANVIL = register(
            "copper_anvil",
            settings -> new MMEAnvilBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, CHIPPED_COPPER_ANVIL),
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
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, MMEItemTags.ADAMANTIUM_CRAFTING_TABLE_EXCEPTIONS, 0.18f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block MITHRIL_CRAFTING_TABLE = register(
            "mithril_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, MMEItemTags.MITHRIL_CRAFTING_TABLE_EXCEPTIONS, 0.15f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block ANCIENT_METAL_CRAFTING_TABLE = register(
            "ancient_metal_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, MMEItemTags.ANCIENT_METAL_CRAFTING_TABLE_EXCEPTIONS, 0.12f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block IRON_CRAFTING_TABLE = register(
            "iron_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.IRON_NOT_ALLOWED_MATERIAL, MMEItemTags.IRON_CRAFTING_TABLE_EXCEPTIONS, 0.09f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block COPPER_CRAFTING_TABLE = register(
            "copper_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, MMEItemTags.COPPER_CRAFTING_TABLE_EXCEPTIONS, 0.06f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block SILVER_CRAFTING_TABLE = register(
            "silver_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, MMEItemTags.SILVER_CRAFTING_TABLE_EXCEPTIONS, 0.06f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block GOLD_CRAFTING_TABLE = register(
            "gold_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.GOLD_CRAFTING_TABLE_EXCEPTIONS, 0.03f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block FLINT_CRAFTING_TABLE = register(
            "flint_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.FLINT_CRAFTING_TABLE_EXCEPTIONS, 0.03f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );
    public static final Block OBSIDIAN_CRAFTING_TABLE = register(
            "obsidian_crafting_table",
            settings -> new GradeCraftingTableBlock(settings, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, MMEItemTags.OBSIDIAN_CRAFTING_TABLE_EXCEPTIONS, 0.03f),
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );

    public static final Block EMERALD_ENCHANTING_TABLE = register(
            "emerald_enchanting_table",
            EmeraldEnchantingTableBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE)
    );

    public static final Block BLUE_BERRY_BUSH = registerNoItem(
            "blue_berry_bush",
            BlueBerryBushBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)
    );

    public static int maxDamageAnvil(int damage) {
        return damage * 40 * 2 / 3;
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
    public static Block registerNoItem(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return Blocks.register(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id)), factory, settings);
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

    public static void init() {
        MMEBlockEntityTypes.init();
        MMEMenuTypes.init();
    }
}
