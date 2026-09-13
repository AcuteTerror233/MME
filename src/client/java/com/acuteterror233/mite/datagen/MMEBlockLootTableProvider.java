package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.item.MMEItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;


/**
 * MME block loot table data provider.
 * Generates loot table JSON for MME custom blocks.
 */
public class MMEBlockLootTableProvider extends FabricBlockLootSubProvider {
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
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SWEET_BERRY_BUSH)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)))
                                        .add(LootItem.lootTableItem(MMEItems.BLUE_BERRIE))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 3.0f)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(registries.getOrThrow(Enchantments.FORTUNE)))
                                )
                                .withPool(
                                        LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SWEET_BERRY_BUSH)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2)))
                                        .add(LootItem.lootTableItem(MMEItems.BLUE_BERRIE))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(registries.getOrThrow(Enchantments.FORTUNE)))
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

        add(MMEBlocks.EMERALD_ENCHANTING_TABLE, block -> createNameableBlockEntityTable(MMEBlocks.EMERALD_ENCHANTING_TABLE));

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
    }
}
