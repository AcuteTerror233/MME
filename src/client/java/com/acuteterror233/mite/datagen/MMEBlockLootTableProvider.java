package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.AtBlocks;
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

        add(AtBlocks.ADAMANTIUM_ORE, block -> createOreDrop(block, MMEItems.RAW_ADAMANTIUM));
        add(AtBlocks.MITHRIL_ORE, block -> createOreDrop(block, MMEItems.RAW_MITHRIL));
        add(AtBlocks.SILVER_ORE, block -> createOreDrop(block, MMEItems.RAW_SILVER));

        add(AtBlocks.DEEPSLATE_ADAMANTIUM_ORE, block -> createOreDrop(block, MMEItems.RAW_ADAMANTIUM));
        add(AtBlocks.DEEPSLATE_MITHRIL_ORE, block -> createOreDrop(block, MMEItems.RAW_MITHRIL));
        add(AtBlocks.DEEPSLATE_SILVER_ORE, block -> createOreDrop(block, MMEItems.RAW_SILVER));

        add(AtBlocks.ADAMANTIUM_DOOR, this::createDoorTable);
        add(AtBlocks.ANCIENT_METAL_DOOR, this::createDoorTable);
        add(AtBlocks.MITHRIL_DOOR, this::createDoorTable);
        add(AtBlocks.SILVER_DOOR, this::createDoorTable);
        add(AtBlocks.GOLDEN_DOOR, this::createDoorTable);

        dropSelf(AtBlocks.ANCIENT_METAL_BLOCK);
        dropSelf(AtBlocks.MITHRIL_BLOCK);
        dropSelf(AtBlocks.SILVER_BLOCK);

        dropSelf(AtBlocks.ADAMANTIUM_BARS);
        dropSelf(AtBlocks.MITHRIL_BARS);
        dropSelf(AtBlocks.COPPER_BARS);
        dropSelf(AtBlocks.SILVER_BARS);
        dropSelf(AtBlocks.ANCIENT_METAL_BARS);
        dropSelf(AtBlocks.GOLDEN_BARS);

        dropSelf(AtBlocks.ADAMANTIUM_CRAFTING_TABLE);
        dropSelf(AtBlocks.ANCIENT_METAL_CRAFTING_TABLE);
        dropSelf(AtBlocks.MITHRIL_CRAFTING_TABLE);
        dropSelf(AtBlocks.GOLD_CRAFTING_TABLE);
        dropSelf(AtBlocks.IRON_CRAFTING_TABLE);
        dropSelf(AtBlocks.COPPER_CRAFTING_TABLE);
        dropSelf(AtBlocks.FLINT_CRAFTING_TABLE);
        dropSelf(AtBlocks.OBSIDIAN_CRAFTING_TABLE);

        dropSelf(AtBlocks.ADAMANTIUM_ANVIL);
        dropSelf(AtBlocks.CHIPPED_ADAMANTIUM_ANVIL);
        dropSelf(AtBlocks.DAMAGED_ADAMANTIUM_ANVIL);
        dropSelf(AtBlocks.MITHRIL_ANVIL);
        dropSelf(AtBlocks.CHIPPED_MITHRIL_ANVIL);
        dropSelf(AtBlocks.DAMAGED_MITHRIL_ANVIL);
        dropSelf(AtBlocks.ANCIENT_METAL_ANVIL);
        dropSelf(AtBlocks.CHIPPED_ANCIENT_METAL_ANVIL);
        dropSelf(AtBlocks.DAMAGED_ANCIENT_METAL_ANVIL);
        dropSelf(AtBlocks.GOLDEN_ANVIL);
        dropSelf(AtBlocks.CHIPPED_GOLDEN_ANVIL);
        dropSelf(AtBlocks.DAMAGED_GOLDEN_ANVIL);
        dropSelf(AtBlocks.COPPER_ANVIL);
        dropSelf(AtBlocks.CHIPPED_COPPER_ANVIL);
        dropSelf(AtBlocks.DAMAGED_COPPER_ANVIL);
        dropSelf(AtBlocks.SILVER_ANVIL);
        dropSelf(AtBlocks.CHIPPED_SILVER_ANVIL);
        dropSelf(AtBlocks.DAMAGED_SILVER_ANVIL);

        dropSelf(AtBlocks.MITHRIL_NUL_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_QUAS_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_POR_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_AN_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_NOX_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_FLAM_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_VAS_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_DES_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_ORT_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_TYM_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_CORP_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_LOR_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_MANI_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_JUX_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_YLEM_RUNESTORE);
        dropSelf(AtBlocks.MITHRIL_SANCT_RUNESTORE);

        dropSelf(AtBlocks.ADAMANTIUM_NUL_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_QUAS_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_POR_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_AN_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_NOX_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_FLAM_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_VAS_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_DES_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_ORT_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_TYM_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_CORP_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_LOR_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_MANI_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_JUX_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_YLEM_RUNESTORE);
        dropSelf(AtBlocks.ADAMANTIUM_SANCT_RUNESTORE);
    }
}
