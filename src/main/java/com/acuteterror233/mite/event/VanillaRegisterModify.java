package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class VanillaRegisterModify {

    /**
     * 物品注册事件
     * 用于在物品注册过程中触发监听器，允许对注册的物品进行修改
     */
    public static final Event<ItemRegister> ITEM_REGISTER = EventFactory.createArrayBacked(ItemRegister.class, (listeners) -> (key, factory, settings) -> {
        for (ItemRegister listener : listeners) {
            Item modify = listener.Modify(key, factory, settings);
            if (settings != null) return modify;
        }
        return null;
    });

    /**
     * 方块注册事件
     * 用于在方块注册过程中触发监听器，允许对注册的方块进行修改
     */
    public static final Event<BlockRegister> BLOCK_REGISTER = EventFactory.createArrayBacked(BlockRegister.class, (listeners) -> (key, factory, settings) -> {
        for (BlockRegister listener : listeners) {
            Block modify = listener.Modify(key, factory, settings);
            if (modify != null) return modify;
        }
        return null;
    });

    /**
     * 方块物品注册事件
     * 用于在方块物品注册过程中触发监听器，允许对注册的物品进行修改
     */
    public static final Event<BlockItemRegister> BLOCK_ITEM_REGISTER = EventFactory.createArrayBacked(BlockItemRegister.class, (listeners) -> (block, factory, settings) -> {
        for (BlockItemRegister listener : listeners) {
            Item modify = listener.Modify(block, factory, settings);
            if (modify != null) return modify;
        }
        return null;
    });


    @FunctionalInterface
    public interface ItemRegister {
        Item Modify (ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties settings);
    }
    @FunctionalInterface
    public interface BlockRegister {
        Block Modify (ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings);
    }
    @FunctionalInterface
    public interface BlockItemRegister {
        Item Modify (Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties settings);
    }
}
