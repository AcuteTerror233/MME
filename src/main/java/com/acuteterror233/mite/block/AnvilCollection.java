package com.acuteterror233.mite.block;

import com.acuteterror233.mite.item.MMEItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Function;

public record AnvilCollection<T>(ImmutableMap<AnvilState, T> variants) {

    public T get(AnvilState state) {
        return variants.get(state);
    }

    public T intact() {
        return get(AnvilState.INTACT);
    }

    public T chipped() {
        return get(AnvilState.CHIPPED);
    }

    public T damaged() {
        return get(AnvilState.DAMAGED);
    }

    public void forEach(Consumer<T> consumer) {
        variants.values().forEach(consumer);
    }

    public ImmutableList<T> asList() {
        return variants.values().asList();
    }

    public static <T> AnvilCollection<T> create(Function<AnvilState, T> function) {
        ImmutableMap.Builder<AnvilState, T> builder = ImmutableMap.builderWithExpectedSize(AnvilState.values().length);
        for (AnvilState state : AnvilState.values()) {
            builder.put(state, function.apply(state));
        }
        return new AnvilCollection<>(builder.build());
    }

    /**
     * Registers the three anvil blocks for a material, chaining each state to the next
     * more damaged one (intact → chipped → damaged → air).
     */
    public static AnvilCollection<Block> registerBlocks(
            AnvilCollection<BlockItemId> ids,
            TagKey<Item> notAllowedMaterial,
            BlockBehaviour.Properties properties
    ) {
        Block damaged = MMEBlocks.register(ids.damaged(),
                settings -> new MMEAnvilBlock(settings, notAllowedMaterial, Blocks.AIR), properties);
        Block chipped = MMEBlocks.register(ids.chipped(),
                settings -> new MMEAnvilBlock(settings, notAllowedMaterial, damaged), properties);
        Block intact = MMEBlocks.register(ids.intact(),
                settings -> new MMEAnvilBlock(settings, notAllowedMaterial, chipped), properties);
        return new AnvilCollection<>(ImmutableMap.of(
                AnvilState.INTACT, intact,
                AnvilState.CHIPPED, chipped,
                AnvilState.DAMAGED, damaged
        ));
    }

    public static AnvilCollection<Item> registerItems(
            AnvilCollection<Block> blocks,
            AnvilCollection<BlockItemId> ids,
            Function<AnvilState, Item.Properties> properties
    ) {
        return create(state -> MMEItems.registerBlockItem(blocks.get(state), ids.get(state), properties.apply(state)));
    }
}
