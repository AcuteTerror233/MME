package com.acuteterror233.mite.block;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.item.AtToolMaterials;
import com.acuteterror233.mite.screen.AtAnvilScreenHandler;
import com.acuteterror233.mite.screen.CraftingTableScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

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
            "adamantium_ore", AbstractBlock.Settings.create().strength(4.0f, 4.0f)
    );
    public static final Block ADAMANTIUM_BARS = register(       //艾德曼栏杆
            "adamantium_bars", PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS).strength(10f, 10f).nonOpaque()
    );
    public static final Block ADAMANTIUM_BLOCK = register(      //艾德曼块
            "adamantium_block", AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );
    public static final Block ANCIENT_METAL_BARS = register(    //远古金属栏杆
            "ancient_metal_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(8f, 8f).nonOpaque()
    );
    public static final Block ANCIENT_METAL_BLOCK = register(   //远古金属块
            "ancient_metal_block", AbstractBlock.Settings.create().strength(35f, 35f)
    );
    public static final Block GOLDEN_BARS = register(             //金栏杆
            "golden_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(2f, 2f).nonOpaque()
    );
    public static final Block MANTLE = register(                //地幔
            "mantle", AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );
    public static final Block MITHRIL_BARS = register(          //秘银栏杆
            "mithril_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(8f, 8f).nonOpaque()
    );
    public static final Block MITHRIL_BLOCK = register(         //秘银块
            "mithril_block", AbstractBlock.Settings.create().strength(40f, 40f)
    );
    public static final Block MITHRIL_ORE = register(           //秘银矿
            "mithril_ore", AbstractBlock.Settings.create().strength(4.0f, 4.0f)
    );
    public static final Block SILVER_BARS = register(           //银栏杆
            "silver_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(4f, 4f).nonOpaque()
    );
    public static final Block SILVER_BLOCK = register(          //银块
            "silver_block", AbstractBlock.Settings.create().strength(10f, 10f)
    );
    public static final Block SILVER_ORE = register(            //银矿
            "silver_ore", AbstractBlock.Settings.create().strength(4.0f, 4.0f)
    );
    public static final Block COPPER_BARS = register(           //铜栏杆
            "copper_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(4f, 4f).nonOpaque()
    );
    public static final Block ADAMANTIUM_DOOR = register(
            "adamantium_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block ANCIENT_METAL_DOOR = register(
            "ancient_metal_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block MITHRIL_DOOR = register(
            "mithril_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block SILVER_DOOR = register(
            "silver_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block GOLDEN_DOOR = register(
            "golden_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );
    public static final Block DAMAGED_ADAMANTIUM_ANVIL = register(
            "damaged_adamantium_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block CHIPPED_ADAMANTIUM_ANVIL = register(
            "chipped_adamantium_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block ADAMANTIUM_ANVIL = register(
            "adamantium_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ADAMANTIUM.durability()))
    );
    public static final Block DAMAGED_MITHRIL_ANVIL = register(
            "damaged_mithril_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.MITHRIL.durability()))
    );
    public static final Block CHIPPED_MITHRIL_ANVIL = register(
            "chipped_mithril_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.MITHRIL.durability()))
    );
    public static final Block MITHRIL_ANVIL = register(
            "mithril_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.MITHRIL.durability()))
    );
    public static final Block DAMAGED_ANCIENT_METAL_ANVIL = register(
            "damaged_ancient_metal_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block CHIPPED_ANCIENT_METAL_ANVIL = register(
            "chipped_ancient_metal_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block ANCIENT_METAL_ANVIL = register(
            "ancient_metal_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.ANCIENT_METAL.durability()))
    );
    public static final Block DAMAGED_GOLDEN_ANVIL = register(
            "damaged_golden_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block CHIPPED_GOLDEN_ANVIL = register(
            "chipped_golden_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block GOLDEN_ANVIL = register(
            "golden_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block DAMAGED_SILVER_ANVIL = register(
            "damaged_silver_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.SILVER.durability()))
    );
    public static final Block CHIPPED_SILVER_ANVIL = register(
            "chipped_silver_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.SILVER.durability()))
    );
    public static final Block SILVER_ANVIL = register(
            "silver_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.SILVER.durability()))
    );
    public static final Block DAMAGED_COPPER_ANVIL = register(
            "damaged_copper_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.COPPER.durability()))
    );
    public static final Block CHIPPED_COPPER_ANVIL = register(
            "chipped_copper_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.COPPER.durability()))
    );
    public static final Block COPPER_ANVIL = register(
            "copper_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.copy(Blocks.ANVIL),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterials.COPPER.durability()))
    );
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
            AdamantiumCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block ANCIENT_METAL_CRAFTING_TABLE = register(
            "ancient_metal_crafting_table",
            AncientMetalCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block MITHRIL_CRAFTING_TABLE = register(
            "mithril_crafting_table",
            MithrilCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block IRON_CRAFTING_TABLE = register(
            "iron_crafting_table",
            IronCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block COPPER_CRAFTING_TABLE = register(
            "copper_crafting_table",
            CopperCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block SILVER_CRAFTING_TABLE = register(
            "silver_crafting_table",
            SilverCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block GOLD_CRAFTING_TABLE = register(
            "gold_crafting_table",
            GoldCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block FLINT_CRAFTING_TABLE = register(
            "flint_crafting_table",
            FlintCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
    );
    public static final Block OBSIDIAN_CRAFTING_TABLE = register(
            "obsidian_crafting_table",
            ObsidianCraftingTable::new,
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.5F)
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

    /**
     * 注册方块并使用传入的 {@code factory1} 注册对应物品。
     */
    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, BiFunction<Block, Item.Settings, Item> factory1) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        Items.register(block, factory1);
        return block;
    }

    /**
     * 注册方块并注册默认方块物品。
     */
    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        Items.register(block);
        return block;
    }

    /**
     * 注册简单方块（使用默认工厂）并注册默认方块物品。
     */
    private static Block register(String name, AbstractBlock.Settings settings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), settings);
        Items.register(block);
        return block;
    }

    /**
     * 注册方块并以指定 {@link Item.Settings} 注册其方块物品。
     *
     * @param name 注册名
     * @param factory 方块构造工厂
     * @param settings 方块属性
     * @param itemSettings1 物品属性（会启用方块前缀翻译键）
     */
    public static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, Item.Settings itemSettings1) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, name));
        BiFunction<Block, Item.Settings, Item> biFunction = (BlockItem::new);
        Items.register(key, itemSettings -> biFunction.apply(block, itemSettings), itemSettings1.useBlockPrefixedTranslationKey());
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
        Identifier id = Identifier.of(At_mite.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
    public static final BlockEntityType<AnvilBlockEntity> ANVIL_BLOCK_ENTITY =
            register(
                    "anvil"
                    , AnvilBlockEntity::new
                    , ADAMANTIUM_ANVIL
                    , CHIPPED_ADAMANTIUM_ANVIL
                    , Blocks.ANVIL
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
    /**
     * 注册界面处理器（ScreenHandler）。
     */
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    /**
     * 预留初始化入口：当前无运行期逻辑，仅用于保持与其它注册流程一致。
     */
    public static void init() {

    }
    public static final ScreenHandlerType<AtAnvilScreenHandler> ATANVILSCREENHANDLER = register("at_anvil_screen_handler", AtAnvilScreenHandler::new);
    public static final ScreenHandlerType<CraftingTableScreenHandler> CRAFTINGTABLESCREENHANDLER = register("cs", CraftingTableScreenHandler::new);

}
