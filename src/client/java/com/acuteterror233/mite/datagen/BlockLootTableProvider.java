package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.AtBlocks;
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
        addDrop(AtBlocks.ADAMANTIUM_DOOR);
        addDrop(AtBlocks.ANCIENT_METAL_BLOCK);
        addDrop(AtBlocks.MITHRIL_BLOCK);
        addDrop(AtBlocks.SILVER_BLOCK);
        addDrop(AtBlocks.ADAMANTIUM_BARS);
        addDrop(AtBlocks.MITHRIL_BARS);
        addDrop(AtBlocks.COPPER_BARS);
        addDrop(AtBlocks.SILVER_BARS);
        addDrop(AtBlocks.ANCIENT_METAL_BARS);
        addDrop(AtBlocks.GOLDEN_BARS);

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
    }
}
