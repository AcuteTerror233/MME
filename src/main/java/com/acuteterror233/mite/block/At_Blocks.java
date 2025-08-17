package com.acuteterror233.mite.block;

import com.acuteterror233.mite.At_mite;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.TallBlockItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public class At_Blocks {
    public static final Block ADAMANTIUM_ORE = register(        //艾德曼矿
            "adamantium_ore", AbstractBlock.Settings.create().strength(4.0f, 4.0f)
    );
    public static final Block ADAMANTIUM_BARS = register(       //艾德曼栏杆
            "adamantium_bars", PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS).strength(10f, 10f).nonOpaque()
    );
    public static final Block ADAMANTIUM_BLOCK = register(      //艾德曼块
            "adamantium_block",AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );
    public static final Block ANCIENT_METAL_BARS = register(    //远古金属栏杆
            "ancient_metal_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(8f,8f).nonOpaque()
    );
    public static final Block ANCIENT_METAL_BLOCK = register(   //远古金属块
            "ancient_metal_block", AbstractBlock.Settings.create().strength(35f,35f)
    );
    public static final Block GOLD_BARS = register(             //金栏杆
            "gold_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(2f,2f).nonOpaque()
    );
    public static final Block MANTLE = register(                //地幔
            "mantle", AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );
    public static final Block MITHRIL_BARS = register(          //秘银栏杆
            "mithril_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(8f,8f).nonOpaque()
    );
    public static final Block MITHRIL_BLOCK = register(         //秘银块
            "mithril_block", AbstractBlock.Settings.create().strength(40f,40f)
    );
    public static final Block MITHRIL_ORE = register(           //秘银矿
            "mithril_ore", AbstractBlock.Settings.create().strength(4.0f, 4.0f)
    );
    public static final Block SILVER_BARS = register(           //银栏杆
            "silver_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(4f,4f).nonOpaque()
    );
    public static final Block SILVER_BLOCK = register(          //银块
            "silver_block", AbstractBlock.Settings.create().strength(10f,10f)
    );
    public static final Block SILVER_ORE = register(            //银矿
            "silver_ore", AbstractBlock.Settings.create().strength(4.0f, 4.0f)
    );
    public static final Block COPPER_BARS = register(           //铜栏杆
            "copper_bars", PaneBlock::new, AbstractBlock.Settings.copy(ADAMANTIUM_BARS).strength(4f,4f).nonOpaque()
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
    public static final Block GOLD_DOOR = register(
             "gold_door",
            settings -> new DoorBlock(BlockSetType.IRON, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).strength(5.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY),
            TallBlockItem::new
    );

    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, BiFunction<Block, Item.Settings, Item> factory1) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        Items.register(block,factory1);
        return block;
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        Items.register(block);
        return block;
    }
    private static Block register(String name, AbstractBlock.Settings settings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), settings);
        Items.register(block);
        return block;
    }

    public static void init() {

    }
}
