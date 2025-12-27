package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.item.AtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    public BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        addDrop(AtBlocks.ADAMANTIUM_ORE, block -> oreDrops(block, AtItems.RAW_ADAMANTIUM));
        addDrop(AtBlocks.MITHRIL_ORE, block -> oreDrops(block, AtItems.RAW_MITHRIL));
        addDrop(AtBlocks.SILVER_ORE, block -> oreDrops(block, AtItems.RAW_SILVER));

        addDrop(AtBlocks.ADAMANTIUM_DOOR, this::doorDrops);
        addDrop(AtBlocks.ANCIENT_METAL_DOOR, this::doorDrops);
        addDrop(AtBlocks.MITHRIL_DOOR, this::doorDrops);
        addDrop(AtBlocks.SILVER_DOOR, this::doorDrops);
        addDrop(AtBlocks.GOLDEN_DOOR, this::doorDrops);

        addDrop(AtBlocks.ANCIENT_METAL_BLOCK);
        addDrop(AtBlocks.MITHRIL_BLOCK);
        addDrop(AtBlocks.SILVER_BLOCK);

        addDrop(AtBlocks.ADAMANTIUM_BARS);
        addDrop(AtBlocks.MITHRIL_BARS);
        addDrop(AtBlocks.COPPER_BARS);
        addDrop(AtBlocks.SILVER_BARS);
        addDrop(AtBlocks.ANCIENT_METAL_BARS);
        addDrop(AtBlocks.GOLDEN_BARS);

        addDrop(AtBlocks.ADAMANTIUM_CRAFTING_TABLE);
        addDrop(AtBlocks.ANCIENT_METAL_CRAFTING_TABLE);
        addDrop(AtBlocks.MITHRIL_CRAFTING_TABLE);
        addDrop(AtBlocks.GOLD_CRAFTING_TABLE);
        addDrop(AtBlocks.IRON_CRAFTING_TABLE);
        addDrop(AtBlocks.COPPER_CRAFTING_TABLE);
        addDrop(AtBlocks.FLINT_CRAFTING_TABLE);
        addDrop(AtBlocks.OBSIDIAN_CRAFTING_TABLE);

        addDrop(AtBlocks.ADAMANTIUM_ANVIL);
        addDrop(AtBlocks.CHIPPED_ADAMANTIUM_ANVIL);
        addDrop(AtBlocks.DAMAGED_ADAMANTIUM_ANVIL);
        addDrop(AtBlocks.MITHRIL_ANVIL);
        addDrop(AtBlocks.CHIPPED_MITHRIL_ANVIL);
        addDrop(AtBlocks.DAMAGED_MITHRIL_ANVIL);
        addDrop(AtBlocks.ANCIENT_METAL_ANVIL);
        addDrop(AtBlocks.CHIPPED_ANCIENT_METAL_ANVIL);
        addDrop(AtBlocks.DAMAGED_ANCIENT_METAL_ANVIL);
        addDrop(AtBlocks.GOLDEN_ANVIL);
        addDrop(AtBlocks.CHIPPED_GOLDEN_ANVIL);
        addDrop(AtBlocks.DAMAGED_GOLDEN_ANVIL);
        addDrop(AtBlocks.COPPER_ANVIL);
        addDrop(AtBlocks.CHIPPED_COPPER_ANVIL);
        addDrop(AtBlocks.DAMAGED_COPPER_ANVIL);
        addDrop(AtBlocks.SILVER_ANVIL);
        addDrop(AtBlocks.CHIPPED_SILVER_ANVIL);
        addDrop(AtBlocks.DAMAGED_SILVER_ANVIL);

        addDrop(AtBlocks.MITHRIL_NUL_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_QUAS_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_POR_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_AN_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_NOX_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_FLAM_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_VAS_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_DES_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_ORT_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_TYM_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_CORP_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_LOR_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_MANI_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_JUX_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_YLEM_RUNESTORE);
        addDrop(AtBlocks.MITHRIL_SANCT_RUNESTORE);

        addDrop(AtBlocks.ADAMANTIUM_NUL_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_QUAS_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_POR_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_AN_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_NOX_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_FLAM_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_VAS_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_DES_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_ORT_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_TYM_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_CORP_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_LOR_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_MANI_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_JUX_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_YLEM_RUNESTORE);
        addDrop(AtBlocks.ADAMANTIUM_SANCT_RUNESTORE);
    }
}
