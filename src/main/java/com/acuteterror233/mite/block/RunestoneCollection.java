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


/**
 * Immutable map from each {@link Rune} to its per-rune object (block, item, or id),
 * letting one material's runestone family be registered and handled as a unit.
 */
public record RunestoneCollection<T>(ImmutableMap<Rune, T> variants) {

    /** @return the variant for the given rune. */
    public T get(Rune rune) {
        return variants.get(rune);
    }

    /** @return the variant at {@code index} in {@link Rune} declaration order. */
    public T getByIndex(int index) {
        return variants.values().asList().get(index);
    }

    /** Applies {@code consumer} to every variant. */
    public void forEach(Consumer<T> consumer) {
        variants.values().forEach(consumer);
    }

    /** @return all variants in {@link Rune} declaration order. */
    public ImmutableList<T> asList() {
        return variants.values().asList();
    }

    /** Builds a collection by applying {@code function} to every {@link Rune}. */
    public static <T> RunestoneCollection<T> create(Function<Rune, T> function) {
        ImmutableMap.Builder<Rune, T> builder = ImmutableMap.builderWithExpectedSize(Rune.values().length);
        for (Rune rune : Rune.values()) {
            builder.put(rune, function.apply(rune));
        }
        return new RunestoneCollection<>(builder.build());
    }

    /** Registers one block per rune. */
    public static RunestoneCollection<Block> registerBlocks(
            RunestoneCollection<BlockItemId> blockItemIds,
            Function<Rune, BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, Block> build
    ) {
        return create(rune -> MMEBlocks.register(blockItemIds.get(rune).blockKey(), build, properties.apply(rune)));
    }

    /** Registers one standalone item per rune. */
    public static RunestoneCollection<Item> registerItems(
            RunestoneCollection<ResourceKey<Item>> key,
            Function<Rune, Item.Properties> properties,
            Function<Item.Properties, Item> build
    ){
        return create(rune -> MMEItems.register(key.get(rune), build, properties.apply(rune)));
    }

    /** Registers one block item per rune, pairing each block with its id key. */
    public static RunestoneCollection<Item> registerBlockItems(
            RunestoneCollection<Block> blocks,
            RunestoneCollection<BlockItemId> key,
            Function<Rune, Item.Properties> properties
    ) {
        return create(rune -> MMEItems.registerBlockItem(blocks.get(rune), key.get(rune), properties.apply(rune)));
    }

    /** Adds every variant to the given creative tab output. */
    public void addToCreativeTab(FabricCreativeModeTabOutput output) {
        for (T block : variants.values()) {
            output.accept((ItemLike) block);
        }
    }
}
