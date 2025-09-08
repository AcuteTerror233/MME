package com.acuteterror233.mite.block;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.item.AtToolMaterial;
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
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public class AtBlocks {
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
    public static final Block ADAMANTIUM_ANVIL = register(
            "adamantium_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.ADAMANTIUM.durability()))
    );
    public static final Block CHIPPED_ADAMANTIUM_ANVIL = register(
            "chipped_adamantium_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.ADAMANTIUM.durability()))
    );
    public static final Block DAMAGED_ADAMANTIUM_ANVIL = register(
            "damaged_adamantium_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.ADAMANTIUM.durability()))
    );
    public static final Block MITHRIL_ANVIL = register(
            "mithril_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.MITHRIL.durability()))
    );
    public static final Block CHIPPED_MITHRIL_ANVIL = register(
            "chipped_mithril_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.MITHRIL.durability()))
    );
    public static final Block DAMAGED_MITHRIL_ANVIL = register(
            "damaged_mithril_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.MITHRIL.durability()))
    );
    public static final Block ANCIENT_METAL_ANVIL = register(
            "ancient_metal_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.ANCIENT_METAL.durability()))
    );
    public static final Block CHIPPED_ANCIENT_METAL_ANVIL = register(
            "chipped_ancient_metal_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.ANCIENT_METAL.durability()))
    );
    public static final Block DAMAGED_ANCIENT_METAL_ANVIL = register(
            "damaged_ancient_metal_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.ANCIENT_METAL.durability()))
    );
    public static final Block GOLD_ANVIL = register(
            "gold_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block CHIPPED_GOLD_ANVIL = register(
            "chipped_gold_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block DAMAGED_GOLD_ANVIL = register(
            "damaged_gold_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(ToolMaterial.GOLD.durability()))
    );
    public static final Block SILVER_ANVIL = register(
            "silver_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.SILVER.durability()))
    );
    public static final Block CHIPPED_SILVER_ANVIL = register(
            "chipped_silver_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.SILVER.durability()))
    );
    public static final Block DAMAGED_SILVER_ANVIL = register(
            "damaged_silver_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.SILVER.durability()))
    );
    public static final Block COPPER_ANVIL = register(
            "copper_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.COPPER.durability()))
    );
    public static final Block CHIPPED_COPPER_ANVIL = register(
            "chipped_copper_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.COPPER.durability()))
    );
    public static final Block DAMAGED_COPPER_ANVIL = register(
            "damaged_copper_anvil",
            AtAnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            new Item.Settings().maxDamage(maxDamageAnvil(AtToolMaterial.COPPER.durability()))
    );
    public static int maxDamageAnvil(int damage){
        return damage*31;
    }
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
    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, Item.Settings itemSettings1) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, name));
        BiFunction<Block, Item.Settings, Item> biFunction = (BlockItem::new);
        Items.register(key, itemSettings -> (Item)biFunction.apply(block, itemSettings), itemSettings1.useBlockPrefixedTranslationKey());
        return block;
    }
    public static final BlockEntityType<AnvilBlockEntity> ANVIL_BLOCK_ENTITY =
            register("anvil", AnvilBlockEntity::new, ADAMANTIUM_ANVIL, CHIPPED_ADAMANTIUM_ANVIL, DAMAGED_ADAMANTIUM_ANVIL);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.of(At_mite.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void init() {

    }
}
