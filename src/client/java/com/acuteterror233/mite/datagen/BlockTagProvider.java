package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.AtBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.DOORS)
                .add(AtBlocks.GOLD_DOOR)
                .add(AtBlocks.SILVER_DOOR)
                .add(AtBlocks.MITHRIL_DOOR)
                .add(AtBlocks.ANCIENT_METAL_DOOR)
                .add(AtBlocks.ADAMANTIUM_DOOR);
    }
}
