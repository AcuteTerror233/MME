package com.acuteterror233.mite.block;

import com.acuteterror233.mite.item.MMEItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Function;


public record RunestoneCollection<T>(ImmutableMap<Rune, T> variants) {

    public T get(Rune rune) {
        return variants.get(rune);
    }

    public T getByIndex(int index) {
        return variants.values().asList().get(index);
    }

    public void forEach(Consumer<T> consumer) {
        variants.values().forEach(consumer);
    }

    public ImmutableList<T> asList() {
        return variants.values().asList();
    }

    public static <T> RunestoneCollection<T> create(Function<Rune, T> function) {
        ImmutableMap.Builder<Rune, T> builder = ImmutableMap.builderWithExpectedSize(Rune.values().length);
        for (Rune rune : Rune.values()) {
            builder.put(rune, function.apply(rune));
        }
        return new RunestoneCollection<>(builder.build());
    }

    public static RunestoneCollection<Block> registerBlocks(
            RunestoneCollection<BlockItemId> blockItemIds,
            Function<Rune, BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, Block> build
    ) {
        return create(rune -> MMEBlocks.register(blockItemIds.get(rune).blockKey(), build, properties.apply(rune)));
    }

    public static RunestoneCollection<Item> registerItems(
            RunestoneCollection<ResourceKey<Item>> key,
            Function<Rune, Item.Properties> properties,
            Function<Item.Properties, Item> build
    ){
        return create(rune -> MMEItems.register(key.get(rune), build, properties.apply(rune)));
    }

    public static RunestoneCollection<Item> registerBlockItems(
            RunestoneCollection<Block> blocks,
            RunestoneCollection<BlockItemId> key,
            Function<Rune, Item.Properties> properties
    ) {
        return create(rune -> MMEItems.registerBlockItem(blocks.get(rune), key.get(rune), properties.apply(rune)));
    }

    public void addToCreativeTab(FabricCreativeModeTabOutput output) {
        for (T block : variants.values()) {
            output.accept((ItemLike) block);
        }
    }
}
