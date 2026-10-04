package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Vanilla register modifier: Fabric events raised while vanilla bootstraps its registries, letting MME
 * intercept and replace registered items, blocks, block items, and block entity types.
 * Raised by {@code ItemsMixin} / {@code BlocksMixin} / {@code BlockEntityTypeMixin} at vanilla
 * registration time; listeners are wired in {@code BootstrapMixin} to swap vanilla entries for MME
 * counterparts without forgoing vanilla registration order.
 */
public final class VanillaRegisterModify {

    /**
     * Item registration event
     * Triggers listeners during item registration, allowing modification of registered items
     */
    public static final Event<ItemRegister> ITEM_REGISTER = EventFactory.createArrayBacked(ItemRegister.class, (listeners) -> (key, factory, settings) -> {
        for (ItemRegister listener : listeners) {
            Item modify = listener.Modify(key, factory, settings);
            if (settings != null) return modify;
        }
        return null;
    });

    /**
     * Block registration event
     * Triggers listeners during block registration, allowing modification of registered blocks
     */
    public static final Event<BlockRegister> BLOCK_REGISTER = EventFactory.createArrayBacked(BlockRegister.class, (listeners) -> (key, factory, settings) -> {
        for (BlockRegister listener : listeners) {
            Block modify = listener.Modify(key, factory, settings);
            if (modify != null) return modify;
        }
        return null;
    });

    /**
     * Block item registration event
     * Triggers listeners during block item registration, allowing modification of registered items
     */
    public static final Event<BlockItemRegister> BLOCK_ITEM_REGISTER = EventFactory.createArrayBacked(BlockItemRegister.class, (listeners) -> (block, factory, settings) -> {
        for (BlockItemRegister listener : listeners) {
            Item modify = listener.Modify(block, factory, settings);
            if (modify != null) return modify;
        }
        return null;
    });

    /**
     * Block entity type registration event
     * Triggers listeners during block entity type registration, allowing the valid-blocks list to be modified
     */
    public static final Event<BlockEntityTypeRegister> BLOCK_ENTITY_TYPE = EventFactory.createArrayBacked(BlockEntityTypeRegister.class, (listeners) -> (key, blocks) -> {
        for (BlockEntityTypeRegister listener : listeners) {
            Block[] modify = listener.ModifyValidBlocks(key, blocks);
            if (modify != null) return modify;
        }
        return null;
    });



    /**
     * Listener invoked for each vanilla item registration.
     *
     * @param key      resource key of the item being registered
     * @param factory  vanilla factory that builds the item from its properties
     * @param settings vanilla item properties about to be used
     * @return the item to register instead of the vanilla one, or {@code null} to keep the vanilla item
     */
    @FunctionalInterface
    public interface ItemRegister {
        Item Modify (ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties settings);
    }
    /**
     * Listener invoked for each vanilla block registration.
     *
     * @param key      resource key of the block being registered
     * @param factory  vanilla factory that builds the block from its properties
     * @param settings vanilla block properties about to be used
     * @return the block to register instead of the vanilla one, or {@code null} to keep the vanilla block
     */
    @FunctionalInterface
    public interface BlockRegister {
        Block Modify (ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings);
    }
    /**
     * Listener invoked for each vanilla block item registration.
     *
     * @param block    the block the item is created for
     * @param factory  vanilla factory that builds the block item
     * @param settings vanilla item properties about to be used
     * @return the item to register instead of the vanilla one, or {@code null} to keep the vanilla item
     */
    @FunctionalInterface
    public interface BlockItemRegister {
        Item Modify (Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties settings);
    }
    /**
     * Listener invoked for each vanilla block entity type registration.
     *
     * @param key    resource key of the block entity type being registered
     * @param blocks blocks valid for this block entity type
     * @return the valid-blocks array to register instead, or {@code null} to keep the vanilla list
     */
    @FunctionalInterface
    public interface BlockEntityTypeRegister {
        Block[] ModifyValidBlocks(ResourceKey<BlockEntityType<?>> key, Block[] blocks);
    }
}
