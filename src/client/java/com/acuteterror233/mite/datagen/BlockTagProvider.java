package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.At_Blocks;
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
                .add(At_Blocks.GOLD_DOOR)
                .add(At_Blocks.SILVER_DOOR)
                .add(At_Blocks.MITHRIL_DOOR)
                .add(At_Blocks.ANCIENT_METAL_DOOR)
                .add(At_Blocks.ADAMANTIUM_DOOR);
    }
}
