package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.item.enchantment.MMEEnchantments;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * MME block loot table data provider.
 * Generates loot table JSON for MME custom blocks.
 */
public class MMEBlockLootTableProvider extends FabricBlockLootSubProvider {
    public static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    public static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    public MMEBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        add(MMEBlocks.BLUE_BERRY_BUSH,
                (Block block) -> this.applyExplosionDecay(block,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                        .when(MatchBlock.blockMatches(
                                                blocks,
                                                MMEBlocks.BLUE_BERRY_BUSH,
                                                StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)
                                        ))
                                        .add(LootItem.lootTableItem(MMEItems.BLUE_BERRIE))
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                                )
                                .withPool(
                                        LootPool.lootPool().when(MatchBlock.blockMatches(
                                                blocks,
                                                MMEBlocks.BLUE_BERRY_BUSH,
                                                StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2)
                                        ))
                                        .add(LootItem.lootTableItem(MMEItems.BLUE_BERRIE))
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                                )
                )
        );

        dropSelf(MMEBlocks.CLAY_FURNACE);
        dropSelf(MMEBlocks.HARDENED_CLAY_FURNACE);
        dropSelf(MMEBlocks.NETHERRACK_FURNACE);
        dropSelf(MMEBlocks.OBSIDIAN_FURNACE);
        dropSelf(MMEBlocks.SANDSTONE_FURNACE);

        add(MMEBlocks.ADAMANTIUM_ORE, block -> createOreDrop(block, MMEItems.RAW_ADAMANTIUM));
        add(MMEBlocks.MITHRIL_ORE, block -> createOreDrop(block, MMEItems.RAW_MITHRIL));
        add(MMEBlocks.SILVER_ORE, block -> createOreDrop(block, MMEItems.RAW_SILVER));

        add(MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE, block -> createOreDrop(block, MMEItems.RAW_ADAMANTIUM));
        add(MMEBlocks.DEEPSLATE_MITHRIL_ORE, block -> createOreDrop(block, MMEItems.RAW_MITHRIL));
        add(MMEBlocks.DEEPSLATE_SILVER_ORE, block -> createOreDrop(block, MMEItems.RAW_SILVER));

        add(MMEBlocks.EMERALD_ENCHANTING_TABLE, _ -> createNameableBlockEntityTable(MMEBlocks.EMERALD_ENCHANTING_TABLE));

        dropSelf(MMEBlocks.ANCIENT_METAL_BLOCK);
        dropSelf(MMEBlocks.MITHRIL_BLOCK);
        dropSelf(MMEBlocks.SILVER_BLOCK);

        dropSelf(MMEBlocks.ADAMANTIUM_CRAFTING_TABLE);
        dropSelf(MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE);
        dropSelf(MMEBlocks.MITHRIL_CRAFTING_TABLE);
        dropSelf(MMEBlocks.GOLD_CRAFTING_TABLE);
        dropSelf(MMEBlocks.IRON_CRAFTING_TABLE);
        dropSelf(MMEBlocks.SILVER_CRAFTING_TABLE);
        dropSelf(MMEBlocks.COPPER_CRAFTING_TABLE);
        dropSelf(MMEBlocks.FLINT_CRAFTING_TABLE);
        dropSelf(MMEBlocks.OBSIDIAN_CRAFTING_TABLE);

        MMEBlocks.NETHERITE_ANVILS.forEach(this::dropSelf);
        MMEBlocks.ADAMANTIUM_ANVILS.forEach(this::dropSelf);
        MMEBlocks.MITHRIL_ANVILS.forEach(this::dropSelf);
        MMEBlocks.ANCIENT_METAL_ANVILS.forEach(this::dropSelf);
        MMEBlocks.GOLDEN_ANVILS.forEach(this::dropSelf);
        MMEBlocks.SILVER_ANVILS.forEach(this::dropSelf);
        MMEBlocks.COPPER_ANVILS.forEach(this::dropSelf);

        MMEBlocks.MITHRIL_RUNESTONES.forEach(this::dropSelf);
        MMEBlocks.ADAMANTIUM_RUNESTONES.forEach(this::dropSelf);

        List<Block> Log = List.of(
                Blocks.OAK_LOG,
                Blocks.SPRUCE_LOG,
                Blocks.BIRCH_LOG,
                Blocks.JUNGLE_LOG,
                Blocks.ACACIA_LOG,
                Blocks.DARK_OAK_LOG,
                Blocks.MANGROVE_LOG,
                Blocks.CHERRY_LOG,
                Blocks.PALE_OAK_LOG,
                Blocks.CRIMSON_STEM,
                Blocks.WARPED_STEM,
                Blocks.OAK_WOOD,
                Blocks.SPRUCE_WOOD,
                Blocks.BIRCH_WOOD,
                Blocks.JUNGLE_WOOD,
                Blocks.ACACIA_WOOD,
                Blocks.DARK_OAK_WOOD,
                Blocks.MANGROVE_WOOD,
                Blocks.CHERRY_WOOD,
                Blocks.PALE_OAK_WOOD,
                Blocks.CRIMSON_HYPHAE,
                Blocks.WARPED_HYPHAE,

                Blocks.STRIPPED_OAK_LOG,
                Blocks.STRIPPED_SPRUCE_LOG,
                Blocks.STRIPPED_BIRCH_LOG,
                Blocks.STRIPPED_JUNGLE_LOG,
                Blocks.STRIPPED_ACACIA_LOG,
                Blocks.STRIPPED_DARK_OAK_LOG,
                Blocks.STRIPPED_MANGROVE_LOG,
                Blocks.STRIPPED_CHERRY_LOG,
                Blocks.STRIPPED_PALE_OAK_LOG,
                Blocks.STRIPPED_CRIMSON_STEM,
                Blocks.STRIPPED_WARPED_STEM,
                Blocks.STRIPPED_OAK_WOOD,
                Blocks.STRIPPED_SPRUCE_WOOD,
                Blocks.STRIPPED_BIRCH_WOOD,
                Blocks.STRIPPED_JUNGLE_WOOD,
                Blocks.STRIPPED_ACACIA_WOOD,
                Blocks.STRIPPED_DARK_OAK_WOOD,
                Blocks.STRIPPED_MANGROVE_WOOD,
                Blocks.STRIPPED_CHERRY_WOOD,
                Blocks.STRIPPED_PALE_OAK_WOOD,
                Blocks.STRIPPED_CRIMSON_HYPHAE,
                Blocks.STRIPPED_WARPED_HYPHAE
        );
        Log.forEach(block -> add(block, createLogItemTable(enchantments, items, block, Items.STICK, 2, 4)));

        add(Blocks.STONE,
                createSilkTouchWithPickaxesWithExplosiveItemTable(enchantments, items, Blocks.STONE, Blocks.COBBLESTONE, Blocks.GRAVEL)
        );
        add(Blocks.WHEAT,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.WHEAT)
                                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(enchantments.getOrThrow(MMEEnchantments.HARVESTING), (float) 3 / 18, 3)))
                                        .when(propertyCondition(blocks, Blocks.WHEAT, CropBlock.AGE, 7)
                                                .and(MatchTool.toolMatches(ItemPredicate.Builder.item().of(items, MMEItemTags.SCYTHE)))
                                                .and(propertyCondition(blocks, Blocks.WHEAT, MMEBlockStateProperties.DISEASE_LEVEL, 0))
                                        )
                        ).withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS))
                                        .when(propertyCondition(blocks, Blocks.WHEAT, CropBlock.AGE, 0))
                        )
        );
        add(Blocks.POTATOES,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.POTATO)
                                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(enchantments.getOrThrow(MMEEnchantments.HARVESTING), (float) 5 / 18, 3))
                                        ).when(propertyCondition(blocks, Blocks.POTATOES, PotatoBlock.AGE, 7)
                                                .and(propertyCondition(blocks, Blocks.WHEAT, MMEBlockStateProperties.DISEASE_LEVEL, 0)))
                        ).withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.POISONOUS_POTATO).when(LootItemRandomChanceCondition.randomChance(0.02F)))
                                        .when(propertyCondition(blocks, Blocks.POTATOES, PotatoBlock.AGE, 7)
                                                .and(propertyCondition(blocks, Blocks.WHEAT, MMEBlockStateProperties.DISEASE_LEVEL, 0)))
                        ).withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.POTATO))
                                        .when(propertyCondition(blocks, Blocks.POTATOES, PotatoBlock.AGE, 0))
                        )
        );
        add(Blocks.CARROTS,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.CARROT)
                                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(enchantments.getOrThrow(MMEEnchantments.HARVESTING), (float) 5 / 18, 3))
                                        ).when(propertyCondition(blocks, Blocks.CARROTS, CarrotBlock.AGE, 7)
                                                .and(propertyCondition(blocks, Blocks.WHEAT, MMEBlockStateProperties.DISEASE_LEVEL, 0)))
                        ).withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.CARROT))
                                        .when(propertyCondition(blocks, Blocks.CARROTS, CarrotBlock.AGE, 0))
                        )
        );
        add(Blocks.BEETROOTS,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.BEETROOT)
                                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(enchantments.getOrThrow(MMEEnchantments.HARVESTING), (float) 5 / 18, 3))
                                        ).when(propertyCondition(blocks, Blocks.BEETROOTS, CropBlock.AGE, 3)
                                                .and(propertyCondition(blocks, Blocks.WHEAT, MMEBlockStateProperties.DISEASE_LEVEL, 0)))
                        ).withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS))
                                        .when(propertyCondition(blocks, Blocks.BEETROOTS, CropBlock.AGE, 0))
                        )
        );
        add(Blocks.MELON_STEM,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.MELON_SEEDS))
                                        .when(propertyCondition(blocks, Blocks.MELON_STEM, StemBlock.AGE, 0))
                        )
        );
        add(Blocks.PUMPKIN_STEM,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS))
                                        .when(propertyCondition(blocks, Blocks.PUMPKIN_STEM, StemBlock.AGE, 0))
                        )
        );
        add(Blocks.NETHER_WART,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.NETHER_WART)
                                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(enchantments.getOrThrow(MMEEnchantments.HARVESTING), (float) 6 / 18, 3)))
                                        .when(propertyCondition(blocks, Blocks.NETHER_WART, NetherWartBlock.AGE, 3))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.NETHER_WART))
                                        .when(propertyCondition(blocks, Blocks.NETHER_WART, NetherWartBlock.AGE, 0))
                        )
        );
        add(Blocks.LAPIS_ORE,
                createSilkTouchWithTagWithExplosiveItemTable(
                        enchantments,
                        items,
                        Blocks.LAPIS_ORE,
                        ContextIntProviders.exactly(1),
                        ItemTags.PICKAXES,
                        Items.LAPIS_LAZULI,
                        ContextIntProviders.between(2, 4),
                        Items.LAPIS_LAZULI,
                        ContextIntProviders.between(2, 4)
                )
        );

        add(Blocks.COPPER_ORE,
                createSilkTouchWithPickaxesWithExplosiveItemTable(
                        enchantments,
                        items,
                        Blocks.COPPER_ORE,
                        Items.RAW_COPPER,
                        Items.RAW_COPPER
                )
        );

        add(Blocks.JUNGLE_LEAVES,
                createAdditionalFruitDrops(enchantments, items, Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_SAPLING, JUNGLE_LEAVES_SAPLING_CHANGES, MMEItems.BANANA)
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .when(hasShearsOrSilkTouch(enchantments, items).invert())
                                        .add(
                                                LootItem.lootTableItem(MMEItems.LEMON)
                                                        .apply(ApplyExplosionDecay.explosionDecay())
                                                        .when(
                                                                BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F)
                                                        )
                                        )
                        )
        );
        add(Blocks.ACACIA_LEAVES,
                createAdditionalFruitDrops(enchantments, items, Blocks.ACACIA_LEAVES, Blocks.ACACIA_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES, MMEItems.ORANGE)
        );
        // --- Normal leaves (drops sapling + sticks) ---
        add(Blocks.SPRUCE_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES)
        );
        add(Blocks.BIRCH_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.BIRCH_LEAVES, Blocks.BIRCH_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES)
        );
        add(Blocks.CHERRY_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.CHERRY_LEAVES, Blocks.CHERRY_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES)
        );
        add(Blocks.PALE_OAK_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.PALE_OAK_LEAVES, Blocks.PALE_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES)
        );
        add(Blocks.AZALEA_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.AZALEA_LEAVES, Blocks.AZALEA, NORMAL_LEAVES_SAPLING_CHANCES)
        );
        add(Blocks.FLOWERING_AZALEA_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.FLOWERING_AZALEA_LEAVES, Blocks.FLOWERING_AZALEA, NORMAL_LEAVES_SAPLING_CHANCES)
        );
        // --- Oak/Dark Oak leaves (additional apple drops) ---
        add(Blocks.OAK_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.OAK_LEAVES, Blocks.OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES)
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .when(hasShearsOrSilkTouch(enchantments, items).invert())
                                        .add(
                                                LootItem.lootTableItem(Items.APPLE)
                                                        .apply(ApplyExplosionDecay.explosionDecay())
                                                        .when(
                                                                BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F)
                                                        )
                                        )
                        )
        );
        add(Blocks.DARK_OAK_LEAVES,
                createLeavesDrops(enchantments, items, Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES)
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .when(hasShearsOrSilkTouch(enchantments, items).invert())
                                        .add(
                                                LootItem.lootTableItem(Items.APPLE)
                                                        .apply(ApplyExplosionDecay.explosionDecay())
                                                        .when(
                                                                BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F)
                                                        )
                                        )
                        )
        );
        // --- Mangrove leaves (no sapling, drops sticks directly) ---
        add(Blocks.MANGROVE_LEAVES,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Blocks.MANGROVE_LEAVES)
                                                        .when(hasShearsOrSilkTouch(enchantments, items))
                                                        .otherwise(
                                                                LootItem.lootTableItem(Items.STICK)
                                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                                                                        .apply(ApplyExplosionDecay.explosionDecay())
                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_SAPLING_CHANCES))
                                                        )
                                        )
                        )
        );
        // --- Gravel (fortune drops various nuggets) ---
        add(Blocks.GRAVEL,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Blocks.GRAVEL)
                                                        .when(MatchTool.toolMatches(
                                                                ItemPredicate.Builder.item()
                                                                        .withComponents(
                                                                                DataComponentMatchers.Builder.components()
                                                                                        .partial(
                                                                                                DataComponentPredicates.ENCHANTMENTS,
                                                                                                EnchantmentsPredicate.enchantments(
                                                                                                        List.of(
                                                                                                                new EnchantmentPredicate(enchantments.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))
                                                                                                        )
                                                                                                )
                                                                                        ).build()
                                                                        )
                                                        ))
                                                        .otherwise(
                                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_NUGGET)
                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.000038103947568968F, 0.000076207895137936F, 0.000152415790275873F, 0.000304831580551745F))
                                                                        .otherwise(
                                                                                LootItem.lootTableItem(MMEItems.MITHRIL_NUGGET)
                                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.000076207895137936F, 0.000152415790275873F, 0.000304831580551745F, 0.00060966316110349F))
                                                                                        .otherwise(
                                                                                                LootItem.lootTableItem(MMEItems.OBSIDIAN_SHARD)
                                                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.0020576131687243F, 0.0041152263374486F, 0.0082304526748971F, 0.0164609053497942F))
                                                                                                        .otherwise(
                                                                                                                LootItem.lootTableItem(MMEItems.SILVER_NUGGET)
                                                                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.0185185185185185F, 0.037037037037037F, 0.0740740740740741F, 0.1481481481481481F))
                                                                                                                        .otherwise(
                                                                                                                                LootItem.lootTableItem(Items.COPPER_NUGGET)
                                                                                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.0555555555555556F, 0.1111111111111111F, 0.2222222222222222F, 0.4444444444444444F))
                                                                                                                                        .otherwise(
                                                                                                                                                LootItem.lootTableItem(Items.FLINT)
                                                                                                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.0277777777777778F, 0.0555555555555556F, 0.1111111111111111F, 0.2222222222222222F))
                                                                                                                                                        .otherwise(
                                                                                                                                                                LootItem.lootTableItem(MMEItems.FLINT_SHARD)
                                                                                                                                                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.15625F, 0.3125F, 0.625F, 1))
                                                                                                                                                                        .otherwise(
                                                                                                                                                                                LootItem.lootTableItem(Blocks.GRAVEL)
                                                                                                                                                                                        .when(ExplosionCondition.survivesExplosion())
                                                                                                                                                                        )
                                                                                                                                                        )
                                                                                                                                        )
                                                                                                                        )
                                                                                                        )
                                                                                        )
                                                                        )
                                                        )
                                        )
                        )
        );
        // --- Deepslate Copper Ore (silk touch + fortune) ---
        add(Blocks.DEEPSLATE_COPPER_ORE,
                createSilkTouchWithPickaxesWithExplosiveItemTable(
                        enchantments,
                        items,
                        Blocks.DEEPSLATE_COPPER_ORE,
                        Items.RAW_COPPER,
                        Items.RAW_COPPER
                )
        );
        // --- Campfire (drops charcoal × 2) ---
        add(Blocks.CAMPFIRE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.CHARCOAL)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2)))
                                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()))
                                                        .otherwise(
                                                                LootItem.lootTableItem(Items.CHARCOAL)
                                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2)))
                                                                        .when(ExplosionCondition.survivesExplosion())
                                                        )
                                        )
                        )
        );
        // --- Soul Campfire (drops soul soil × 1) ---
        add(Blocks.SOUL_CAMPFIRE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Blocks.SOUL_SOIL)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()))
                                                        .otherwise(
                                                                LootItem.lootTableItem(Blocks.SOUL_SOIL)
                                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                                        .when(ExplosionCondition.survivesExplosion())
                                                        )
                                        )
                        )
        );
        add(Blocks.RAW_IRON_BLOCK,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.RAW_IRON)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(9)))
                                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()))
                                        )
                        )
        );
        add(Blocks.RAW_COPPER_BLOCK,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.RAW_COPPER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(9)))
                                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()))
                                        )
                        )
        );
        add(Blocks.RAW_GOLD_BLOCK,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.RAW_GOLD)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(9)))
                                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()))
                                        )
                        )
        );
        add(Blocks.GRASS_BLOCK,
                LootTable.lootTable()
                        .withPool(new LootPool.Builder()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.GRASS_BLOCK)
                                        .when(MatchTool.toolMatches(
                                                        ItemPredicate.Builder.item()
                                                                .withComponents(
                                                                        DataComponentMatchers.Builder.components()
                                                                                .partial(
                                                                                        DataComponentPredicates.ENCHANTMENTS,
                                                                                        EnchantmentsPredicate.enchantments(
                                                                                                List.of(
                                                                                                        new EnchantmentPredicate(enchantments.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))
                                                                                                )
                                                                                        )
                                                                                ).build()
                                                                )
                                                )
                                        ).otherwise(LootItem.lootTableItem(MMEItems.WORM_RAW)
                                                .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.05F, 0.0625F, 0.083333336F, 0.1F))
                                        ).otherwise(LootItem.lootTableItem(Items.DIRT)
                                                .when(ExplosionCondition.survivesExplosion())
                                        )
                                )
                        )
        );
    }

    private static LootTable.Builder createAdditionalFruitDrops(HolderGetter<Enchantment> enchantments, HolderGetter<Item> items, Block leaves, Block sapling, float[] leavesSaplingChanges, ItemLike fruit){
        return createLeavesDrops(enchantments, items, leaves, sapling, leavesSaplingChanges)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .when(hasShearsOrSilkTouch(enchantments, items).invert())
                                .add(
                                        LootItem.lootTableItem(fruit)
                                                .apply(ApplyExplosionDecay.explosionDecay())
                                                .when(
                                                        BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F)
                                                )
                                )
                );
    }
    private static LootTable.Builder createLeavesDrops(HolderGetter<Enchantment> enchantments, HolderGetter<Item> items, Block leaves, Block sapling, float[] leavesSaplingChanges) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(
                                        LootItem.lootTableItem(leaves)
                                                .when(hasShearsOrSilkTouch(enchantments, items))
                                                .otherwise(
                                                        LootItem.lootTableItem(sapling)
                                                                .when((ExplosionCondition.survivesExplosion()))
                                                                .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), leavesSaplingChanges))
                                                )
                                )
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .when(hasShearsOrSilkTouch(enchantments, items).invert())
                                .add(
                                        LootItem.lootTableItem(Items.STICK)
                                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                                                .apply(ApplyExplosionDecay.explosionDecay())
                                ).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), leavesSaplingChanges))
                );
    }

    private static AnyOfCondition.@NotNull Builder hasShearsOrSilkTouch(HolderGetter<Enchantment> enchantments, HolderGetter<Item> items) {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(items, Items.SHEARS))
                .or(MatchTool.toolMatches(
                                ItemPredicate.Builder.item()
                                        .withComponents(
                                                DataComponentMatchers.Builder.components()
                                                        .partial(
                                                                DataComponentPredicates.ENCHANTMENTS,
                                                                EnchantmentsPredicate.enchantments(
                                                                        List.of(
                                                                                new EnchantmentPredicate(enchantments.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))
                                                                        )
                                                                )
                                                        )
                                                        .build()
                                        )
                        )
                );
    }

    public static LootItemCondition.Builder propertyCondition(HolderGetter<Block> blocks, Block block, Property<Integer> property, int i) {
        return MatchBlock.blockMatches(blocks, block, StatePropertiesPredicate.Builder.properties().hasProperty(property, i)
        );
    }
    public static LootTable.Builder createLogItemTable(
            HolderGetter<Enchantment> enchantments,
            HolderGetter<Item> items,
            ItemLike silkTouchAndDigItem,
            ItemLike blastingItem,
            int min,
            int max
    ){
        return createSilkTouchWithTagWithExplosiveItemTable(enchantments, items, silkTouchAndDigItem, ContextIntProviders.exactly(1), ItemTags.AXES, silkTouchAndDigItem, ContextIntProviders.exactly(1), blastingItem, ContextIntProviders.between(min, max));
    }
    public static LootTable.Builder createSilkTouchWithPickaxesWithExplosiveItemTable(HolderGetter<Enchantment> enchantments, HolderGetter<Item> items, ItemLike silkTouchItem, ItemLike digItem, ItemLike blastingItem){
        return createSilkTouchWithTagWithExplosiveItemTable(enchantments, items, silkTouchItem, ContextIntProviders.exactly(1), ItemTags.PICKAXES, digItem, ContextIntProviders.exactly(1), blastingItem, ContextIntProviders.exactly(1));
    }
    public static LootTable.Builder createSilkTouchWithTagWithExplosiveItemTable(
            HolderGetter<Enchantment> enchantments,
            HolderGetter<Item> items,
            ItemLike silkTouchItem,
            Holder<ContextIntProvider> count1,
            TagKey<Item> allowedDigTag,
            ItemLike digItem,
            Holder<ContextIntProvider> count2,
            ItemLike blastingItem,
            Holder<ContextIntProvider> count3
    ) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(silkTouchItem)
                                        .apply(SetItemCountFunction.setCount(count1))
                                        .when(MatchTool.toolMatches(
                                                        ItemPredicate.Builder.item()
                                                                .withComponents(
                                                                        DataComponentMatchers.Builder.components()
                                                                                .partial(
                                                                                        DataComponentPredicates.ENCHANTMENTS,
                                                                                        EnchantmentsPredicate.enchantments(
                                                                                                List.of(
                                                                                                        new EnchantmentPredicate(enchantments.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))
                                                                                                )
                                                                                        )
                                                                                ).build()
                                                                )
                                                )
                                        )
                                        .otherwise(
                                                LootItem.lootTableItem(digItem)
                                                        .apply(SetItemCountFunction.setCount(count2))
                                                        .when(MatchTool.toolMatches(
                                                                        ItemPredicate.Builder.item()
                                                                                .of(items, allowedDigTag)
                                                                )
                                                        )
                                        ).otherwise(
                                                LootItem.lootTableItem(blastingItem)
                                                        .apply(SetItemCountFunction.setCount(count3))
                                                        .when(ExplosionCondition.survivesExplosion())
                                        )
                                )
                );
    }
}
