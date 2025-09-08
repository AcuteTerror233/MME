package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.registry.tag.AtTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
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
        getOrCreateTagBuilder(AtTags.GLASS)
                .add(Blocks.GLASS)
                .add(Blocks.WHITE_STAINED_GLASS)
                .add(Blocks.ORANGE_STAINED_GLASS)
                .add(Blocks.MAGENTA_STAINED_GLASS)
                .add(Blocks.LIGHT_BLUE_STAINED_GLASS)
                .add(Blocks.YELLOW_STAINED_GLASS)
                .add(Blocks.LIME_STAINED_GLASS)
                .add(Blocks.PINK_STAINED_GLASS)
                .add(Blocks.GRAY_STAINED_GLASS)
                .add(Blocks.LIGHT_GRAY_STAINED_GLASS)
                .add(Blocks.CYAN_STAINED_GLASS)
                .add(Blocks.PURPLE_STAINED_GLASS)
                .add(Blocks.BLUE_STAINED_GLASS)
                .add(Blocks.BROWN_STAINED_GLASS)
                .add(Blocks.GREEN_STAINED_GLASS)
                .add(Blocks.RED_STAINED_GLASS)
                .add(Blocks.BLACK_STAINED_GLASS)
                .add(Blocks.TINTED_GLASS);
        getOrCreateTagBuilder(AtTags.GLASS_PANE)
                .add(Blocks.GLASS_PANE)
                .add(Blocks.WHITE_STAINED_GLASS_PANE)
                .add(Blocks.ORANGE_STAINED_GLASS_PANE)
                .add(Blocks.MAGENTA_STAINED_GLASS_PANE)
                .add(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE)
                .add(Blocks.YELLOW_STAINED_GLASS_PANE)
                .add(Blocks.LIME_STAINED_GLASS_PANE)
                .add(Blocks.PINK_STAINED_GLASS_PANE)
                .add(Blocks.GRAY_STAINED_GLASS_PANE)
                .add(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE)
                .add(Blocks.CYAN_STAINED_GLASS_PANE)
                .add(Blocks.PURPLE_STAINED_GLASS_PANE)
                .add(Blocks.BLUE_STAINED_GLASS_PANE)
                .add(Blocks.BROWN_STAINED_GLASS_PANE)
                .add(Blocks.GREEN_STAINED_GLASS_PANE)
                .add(Blocks.RED_STAINED_GLASS_PANE)
                .add(Blocks.BLACK_STAINED_GLASS_PANE);
        getOrCreateTagBuilder(AtTags.BARS)
                .add(Blocks.IRON_BARS)
                .add(AtBlocks.ADAMANTIUM_BARS)
                .add(AtBlocks.ANCIENT_METAL_BARS)
                .add(AtBlocks.GOLD_BARS)
                .add(AtBlocks.MITHRIL_BARS)
                .add(AtBlocks.SILVER_BARS)
                .add(AtBlocks.COPPER_BARS);
        getOrCreateTagBuilder(AtTags.BLOCK_COUNT_1)
                .forceAddTag(BlockTags.DOORS)
                .forceAddTag(BlockTags.FENCE_GATES)
                .add(Blocks.HEAVY_CORE);
        getOrCreateTagBuilder(AtTags.BLOCK_COUNT_8)
                .forceAddTag(BlockTags.PLANKS)
                .forceAddTag(BlockTags.SLABS)
                .forceAddTag(BlockTags.WALLS)
                .forceAddTag(BlockTags.CONCRETE_POWDER)
                .forceAddTag(BlockTags.TERRACOTTA)
                .forceAddTag(BlockTags.LEAVES)
                .forceAddTag(AtTags.GLASS);
        getOrCreateTagBuilder(AtTags.BLOCK_COUNT_16)
                .forceAddTag(AtTags.GLASS_PANE)
                .forceAddTag(AtTags.BARS)
                .forceAddTag(BlockTags.RAILS)
                .forceAddTag(BlockTags.BUTTONS)
                .forceAddTag(BlockTags.PRESSURE_PLATES)
                .forceAddTag(BlockTags.SAPLINGS)
                .forceAddTag(BlockTags.WOOL_CARPETS)
                .add(Blocks.KELP)
                .add(Blocks.NETHER_WART)
                .add(Blocks.SWEET_BERRY_BUSH)
                .add(Blocks.POINTED_DRIPSTONE)
                .add(Blocks.SCULK_VEIN)
                .add(Blocks.MOSS_CARPET)
                .add(Blocks.PALE_MOSS_CARPET)
                .add(Blocks.LADDER)
                .add(Blocks.SCAFFOLDING)
                .add(Blocks.SNOW);
        getOrCreateTagBuilder(AtTags.BLOCK_COUNT_32)
                .forceAddTag(BlockTags.CORALS)
                .add(Blocks.CRIMSON_FUNGUS)
                .add(Blocks.WARPED_FUNGUS)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.RED_MUSHROOM)
                .add(Blocks.SEA_PICKLE);
    }
}
