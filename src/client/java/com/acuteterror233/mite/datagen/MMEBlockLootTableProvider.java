package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.item.MMEItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class MMEBlockLootTableProvider extends FabricBlockLootTableProvider {
    public MMEBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        add(MMEBlocks.ADAMANTIUM_ORE, block -> createOreDrop(block, MMEItems.RAW_ADAMANTIUM));
        add(MMEBlocks.MITHRIL_ORE, block -> createOreDrop(block, MMEItems.RAW_MITHRIL));
        add(MMEBlocks.SILVER_ORE, block -> createOreDrop(block, MMEItems.RAW_SILVER));

        add(MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE, block -> createOreDrop(block, MMEItems.RAW_ADAMANTIUM));
        add(MMEBlocks.DEEPSLATE_MITHRIL_ORE, block -> createOreDrop(block, MMEItems.RAW_MITHRIL));
        add(MMEBlocks.DEEPSLATE_SILVER_ORE, block -> createOreDrop(block, MMEItems.RAW_SILVER));

        add(MMEBlocks.ADAMANTIUM_DOOR, this::createDoorTable);
        add(MMEBlocks.ANCIENT_METAL_DOOR, this::createDoorTable);
        add(MMEBlocks.MITHRIL_DOOR, this::createDoorTable);
        add(MMEBlocks.SILVER_DOOR, this::createDoorTable);
        add(MMEBlocks.GOLDEN_DOOR, this::createDoorTable);

        add(MMEBlocks.EMERALD_ENCHANTING_TABLE, block -> createNameableBlockEntityTable(MMEBlocks.EMERALD_ENCHANTING_TABLE));

        dropSelf(MMEBlocks.ANCIENT_METAL_BLOCK);
        dropSelf(MMEBlocks.MITHRIL_BLOCK);
        dropSelf(MMEBlocks.SILVER_BLOCK);

        dropSelf(MMEBlocks.ADAMANTIUM_BARS);
        dropSelf(MMEBlocks.MITHRIL_BARS);
        dropSelf(MMEBlocks.COPPER_BARS);
        dropSelf(MMEBlocks.SILVER_BARS);
        dropSelf(MMEBlocks.ANCIENT_METAL_BARS);
        dropSelf(MMEBlocks.GOLDEN_BARS);

        dropSelf(MMEBlocks.ADAMANTIUM_CRAFTING_TABLE);
        dropSelf(MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE);
        dropSelf(MMEBlocks.MITHRIL_CRAFTING_TABLE);
        dropSelf(MMEBlocks.GOLD_CRAFTING_TABLE);
        dropSelf(MMEBlocks.IRON_CRAFTING_TABLE);
        dropSelf(MMEBlocks.COPPER_CRAFTING_TABLE);
        dropSelf(MMEBlocks.FLINT_CRAFTING_TABLE);
        dropSelf(MMEBlocks.OBSIDIAN_CRAFTING_TABLE);

        dropSelf(MMEBlocks.NETHERITE_ANVIL);
        dropSelf(MMEBlocks.CHIPPED_NETHERITE_ANVIL);
        dropSelf(MMEBlocks.DAMAGED_NETHERITE_ANVIL);
        dropSelf(MMEBlocks.ADAMANTIUM_ANVIL);
        dropSelf(MMEBlocks.CHIPPED_ADAMANTIUM_ANVIL);
        dropSelf(MMEBlocks.DAMAGED_ADAMANTIUM_ANVIL);
        dropSelf(MMEBlocks.MITHRIL_ANVIL);
        dropSelf(MMEBlocks.CHIPPED_MITHRIL_ANVIL);
        dropSelf(MMEBlocks.DAMAGED_MITHRIL_ANVIL);
        dropSelf(MMEBlocks.ANCIENT_METAL_ANVIL);
        dropSelf(MMEBlocks.CHIPPED_ANCIENT_METAL_ANVIL);
        dropSelf(MMEBlocks.DAMAGED_ANCIENT_METAL_ANVIL);
        dropSelf(MMEBlocks.GOLDEN_ANVIL);
        dropSelf(MMEBlocks.CHIPPED_GOLDEN_ANVIL);
        dropSelf(MMEBlocks.DAMAGED_GOLDEN_ANVIL);
        dropSelf(MMEBlocks.COPPER_ANVIL);
        dropSelf(MMEBlocks.CHIPPED_COPPER_ANVIL);
        dropSelf(MMEBlocks.DAMAGED_COPPER_ANVIL);
        dropSelf(MMEBlocks.SILVER_ANVIL);
        dropSelf(MMEBlocks.CHIPPED_SILVER_ANVIL);
        dropSelf(MMEBlocks.DAMAGED_SILVER_ANVIL);

        dropSelf(MMEBlocks.MITHRIL_NUL_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_QUAS_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_POR_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_AN_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_NOX_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_FLAM_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_VAS_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_DES_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_ORT_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_TYM_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_CORP_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_LOR_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_MANI_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_JUX_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_YLEM_RUNESTORE);
        dropSelf(MMEBlocks.MITHRIL_SANCT_RUNESTORE);

        dropSelf(MMEBlocks.ADAMANTIUM_NUL_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_QUAS_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_POR_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_AN_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_NOX_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_FLAM_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_VAS_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_DES_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_ORT_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_TYM_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_CORP_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_LOR_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_MANI_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_JUX_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_YLEM_RUNESTORE);
        dropSelf(MMEBlocks.ADAMANTIUM_SANCT_RUNESTORE);
    }
}
