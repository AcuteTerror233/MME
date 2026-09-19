package com.acuteterror233.mite.block;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

/**
 * MME mod block registry.
 * Registers blocks only; the corresponding block items are registered separately in {@code MMEItems}.
 */
public class MMEBlocks {
    public static final Block ADAMANTIUM_ORE = register(        // Adamantium Ore
            MMEBlockItemIds.ADAMANTIUM_ORE,
            BlockBehaviour.Properties.of().strength(5.0f, 10.0f).requiresCorrectToolForDrops()
    );
    public static final Block MITHRIL_ORE = register(           // Mithril Ore
            MMEBlockItemIds.MITHRIL_ORE,
            BlockBehaviour.Properties.of().strength(4.0f, 5.0f).requiresCorrectToolForDrops()
    );
    public static final Block SILVER_ORE = register(            // Silver Ore
            MMEBlockItemIds.SILVER_ORE,
            BlockBehaviour.Properties.of().strength(3.0f, 3.0f).requiresCorrectToolForDrops()
    );

    public static final Block DEEPSLATE_ADAMANTIUM_ORE = register(        // Deepslate Adamantium Ore
            MMEBlockItemIds.DEEPSLATE_ADAMANTIUM_ORE,
            BlockBehaviour.Properties.of().strength(5.5f, 10.0f).requiresCorrectToolForDrops()
    );
    public static final Block DEEPSLATE_MITHRIL_ORE = register(           // Deepslate Mithril Ore
            MMEBlockItemIds.DEEPSLATE_MITHRIL_ORE,
            BlockBehaviour.Properties.of().strength(4.5f, 5.0f).requiresCorrectToolForDrops()
    );
    public static final Block DEEPSLATE_SILVER_ORE = register(            // Deepslate Silver Ore
            MMEBlockItemIds.DEEPSLATE_SILVER_ORE,
            BlockBehaviour.Properties.of().strength(4.5f, 3.0f).requiresCorrectToolForDrops()
    );

    public static final Block ADAMANTIUM_BLOCK = register(      // Adamantium Block
            MMEBlockItemIds.ADAMANTIUM_BLOCK, BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).requiresCorrectToolForDrops()
    );
    public static final Block ANCIENT_METAL_BLOCK = register(   // Ancient Metal Block
            MMEBlockItemIds.ANCIENT_METAL_BLOCK, BlockBehaviour.Properties.of().strength(35f, 35f).requiresCorrectToolForDrops()
    );
    public static final Block MITHRIL_BLOCK = register(         // Mithril Block
            MMEBlockItemIds.MITHRIL_BLOCK, BlockBehaviour.Properties.of().strength(40f, 40f).requiresCorrectToolForDrops()
    );
    public static final Block SILVER_BLOCK = register(          // Silver Block
            MMEBlockItemIds.SILVER_BLOCK, BlockBehaviour.Properties.of().strength(10f, 10f).requiresCorrectToolForDrops()
    );

    public static final Block CLAY_FURNACE = register(
            MMEBlockItemIds.CLAY_FURNACE, FurnaceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block HARDENED_CLAY_FURNACE = register(
            MMEBlockItemIds.HARDENED_CLAY_FURNACE, FurnaceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block NETHERRACK_FURNACE = register(
            MMEBlockItemIds.NETHERRACK_FURNACE, FurnaceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block OBSIDIAN_FURNACE = register(
            MMEBlockItemIds.OBSIDIAN_FURNACE, FurnaceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );
    public static final Block SANDSTONE_FURNACE = register(
            MMEBlockItemIds.SANDSTONE_FURNACE, FurnaceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
    );

    public static final Block MANTLE = register(                // Mantle
            MMEBlockItemIds.MANTLE,
            MagmaBlock::new,
            BlockBehaviour.Properties
                    .ofFullCopy(Blocks.BEDROCK)
                    .lightLevel(blockStatex -> 3)
                    .isValidSpawn((blockStatex, blockGetter, blockPos, entityType) -> entityType.fireImmune())
                    .postProcess((blockStatex, blockGetter, blockPos) -> blockPos.above())
                    .emissiveRendering(state -> true)
    );

    public static final AnvilCollection<Block> NETHERITE_ANVILS = AnvilCollection.registerBlocks(
            MMEBlockItemIds.NETHERITE_ANVIL, MMEItemTags.NETHERITE_NOT_ALLOWED_MATERIAL, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
    );
    public static final AnvilCollection<Block> ADAMANTIUM_ANVILS = AnvilCollection.registerBlocks(
            MMEBlockItemIds.ADAMANTIUM_ANVIL, MMEItemTags.ADAMANTIUM_NOT_ALLOWED_MATERIAL, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
    );
    public static final AnvilCollection<Block> MITHRIL_ANVILS = AnvilCollection.registerBlocks(
            MMEBlockItemIds.MITHRIL_ANVIL, MMEItemTags.MITHRIL_NOT_ALLOWED_MATERIAL, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
    );
    public static final AnvilCollection<Block> ANCIENT_METAL_ANVILS = AnvilCollection.registerBlocks(
            MMEBlockItemIds.ANCIENT_METAL_ANVIL, MMEItemTags.ANCIENT_METAL_NOT_ALLOWED_MATERIAL, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
    );
    public static final AnvilCollection<Block> GOLDEN_ANVILS = AnvilCollection.registerBlocks(
            MMEBlockItemIds.GOLDEN_ANVIL, MMEItemTags.GOLD_NOT_ALLOWED_MATERIAL, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
    );
    public static final AnvilCollection<Block> SILVER_ANVILS = AnvilCollection.registerBlocks(
            MMEBlockItemIds.SILVER_ANVIL, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
    );
    public static final AnvilCollection<Block> COPPER_ANVILS = AnvilCollection.registerBlocks(
            MMEBlockItemIds.COPPER_ANVIL, MMEItemTags.COPPER_OR_SILVER_NOT_ALLOWED_MATERIAL, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)
    );

    // Backward-compatible aliases — delegate to the collections
    public static final Block NETHERITE_ANVIL = NETHERITE_ANVILS.intact();
    public static final Block CHIPPED_NETHERITE_ANVIL = NETHERITE_ANVILS.chipped();
    public static final Block DAMAGED_NETHERITE_ANVIL = NETHERITE_ANVILS.damaged();
    public static final Block ADAMANTIUM_ANVIL = ADAMANTIUM_ANVILS.intact();
    public static final Block CHIPPED_ADAMANTIUM_ANVIL = ADAMANTIUM_ANVILS.chipped();
    public static final Block DAMAGED_ADAMANTIUM_ANVIL = ADAMANTIUM_ANVILS.damaged();
    public static final Block MITHRIL_ANVIL = MITHRIL_ANVILS.intact();
    public static final Block CHIPPED_MITHRIL_ANVIL = MITHRIL_ANVILS.chipped();
    public static final Block DAMAGED_MITHRIL_ANVIL = MITHRIL_ANVILS.damaged();
    public static final Block ANCIENT_METAL_ANVIL = ANCIENT_METAL_ANVILS.intact();
    public static final Block CHIPPED_ANCIENT_METAL_ANVIL = ANCIENT_METAL_ANVILS.chipped();
    public static final Block DAMAGED_ANCIENT_METAL_ANVIL = ANCIENT_METAL_ANVILS.damaged();
    public static final Block GOLDEN_ANVIL = GOLDEN_ANVILS.intact();
    public static final Block CHIPPED_GOLDEN_ANVIL = GOLDEN_ANVILS.chipped();
    public static final Block DAMAGED_GOLDEN_ANVIL = GOLDEN_ANVILS.damaged();
    public static final Block SILVER_ANVIL = SILVER_ANVILS.intact();
    public static final Block CHIPPED_SILVER_ANVIL = SILVER_ANVILS.chipped();
    public static final Block DAMAGED_SILVER_ANVIL = SILVER_ANVILS.damaged();
    public static final Block COPPER_ANVIL = COPPER_ANVILS.intact();
    public static final Block CHIPPED_COPPER_ANVIL = COPPER_ANVILS.chipped();
    public static final Block DAMAGED_COPPER_ANVIL = COPPER_ANVILS.damaged();

    /** All anvil collections, ordered by material tier. */
    public static final ImmutableList<AnvilCollection<Block>> ANVIL_COLLECTIONS = ImmutableList.of(
            NETHERITE_ANVILS, ADAMANTIUM_ANVILS, MITHRIL_ANVILS, ANCIENT_METAL_ANVILS,
            SILVER_ANVILS, GOLDEN_ANVILS, COPPER_ANVILS
    );

    public static final Block UNDERGROUND_PORTAL = register(
            MMEBlockIds.UNDERGROUND_PORTAL,
            UndergroundPortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );
    public static final Block HOME_PORTAL = register(
            MMEBlockIds.HOME_PORTAL,
            HomePortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );
    public static final Block RUNE_PORTAL = register(
            MMEBlockIds.RUNE_PORTAL,
            RunePortalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
    );

    public static final RunestoneCollection<Block> MITHRIL_RUNESTONES = RunestoneCollection.registerBlocks(
            MMEBlockItemIds.MITHRIL_RUNESTORE, r -> BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN), Block::new
    );
    public static final RunestoneCollection<Block> ADAMANTIUM_RUNESTONES = RunestoneCollection.registerBlocks(
            MMEBlockItemIds.ADAMANTIUM_RUNESTORE, r -> BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN), Block::new
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

    public static final Block BLUE_BERRY_BUSH = register(
            MMEBlockIds.BLUE_BERRY_BUSH,
            BlueBerryBushBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)
    );

    public static int maxDamageAnvil(int damage) {
        return damage * 40 * 2 / 3;
    }

    public static Block register(BlockItemId blockItemId, BlockBehaviour.Properties settings) {
        return register(blockItemId.blockKey(), Block::new, settings);
    }

    public static Block register(ResourceKey<Block> registryKey, BlockBehaviour.Properties settings) {
        return register(registryKey, Block::new, settings);
    }

    public static Block register(BlockItemId blockItemId, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return register(blockItemId.blockKey(), factory, settings);
    }

    public static Block register(ResourceKey<Block> registryKey, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return Blocks.register(registryKey, factory, settings);
    }

    public static void init() {
        MMEBlockEntityTypes.init();
        MMEMenuTypes.init();
    }
}
