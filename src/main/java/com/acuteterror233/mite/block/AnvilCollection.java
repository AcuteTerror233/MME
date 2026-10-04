package com.acuteterror233.mite.block;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.material.MetalMaterial;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Immutable map from each {@link AnvilState} to its per-state object (block, item, or id pair),
 * letting one material's anvil family be registered and handled as a unit.
 */
public record AnvilCollection<T>(ImmutableMap<AnvilState, T> variants) {

    /** @return the variant for the given damage state. */
    public T get(AnvilState state) {
        return variants.get(state);
    }

    /** @return the undamaged variant. */
    public T intact() {
        return get(AnvilState.INTACT);
    }

    /** @return the chipped variant. */
    public T chipped() {
        return get(AnvilState.CHIPPED);
    }

    /** @return the damaged variant. */
    public T damaged() {
        return get(AnvilState.DAMAGED);
    }

    /** Applies {@code consumer} to every variant. */
    public void forEach(Consumer<T> consumer) {
        variants.values().forEach(consumer);
    }

    /** @return all variants in {@link AnvilState} declaration order. */
    public ImmutableList<T> asList() {
        return variants.values().asList();
    }

    /** Builds a collection by applying {@code function} to every {@link AnvilState}. */
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
     *
     * @param ids        id pair per damage state (block and item share the path)
     * @param metal      the anvil material, enforced by the anvil menu
     * @param properties shared block properties for all three states
     * @return the registered block collection
     */
    public static AnvilCollection<Block> registerBlocks(
            AnvilCollection<BlockItemId> ids,
            MetalMaterial metal,
            BlockBehaviour.Properties properties
    ) {
        Block damaged = MMEBlocks.register(ids.damaged(),
                settings -> new MMEAnvilBlock(settings, metal, Blocks.AIR), properties);
        Block chipped = MMEBlocks.register(ids.chipped(),
                settings -> new MMEAnvilBlock(settings, metal, damaged), properties);
        Block intact = MMEBlocks.register(ids.intact(),
                settings -> new MMEAnvilBlock(settings, metal, chipped), properties);
        return new AnvilCollection<>(ImmutableMap.of(
                AnvilState.INTACT, intact,
                AnvilState.CHIPPED, chipped,
                AnvilState.DAMAGED, damaged
        ));
    }

    /** Registers one block item per anvil state, using the per-state item properties from {@code properties}. */
    public static AnvilCollection<Item> registerItems(
            AnvilCollection<Block> blocks,
            AnvilCollection<BlockItemId> ids,
            Function<AnvilState, Item.Properties> properties
    ) {
        return create(state -> MMEItems.registerBlockItem(blocks.get(state), ids.get(state), properties.apply(state)));
    }
}
