package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class VanillaRegisterModify {

    /**
     * 物品注册事件
     * 用于在物品注册过程中触发监听器，允许对注册的物品进行修改
     *
     * @param key 物品的标识符
     * @param factory 物品创建工厂
     * @param settings 物品设置参数
     * @return 修改后的物品实例，如果没有修改则返回null
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
     *
     * @param key 方块的标识符
     * @param factory 方块创建工厂
     * @param settings 方块设置参数
     * @return 修改后的方块实例，如果没有修改则返回null
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
     *
     * @param block 关联的方块实例
     * @param factory 物品创建工厂
     * @param settings 物品设置参数
     * @return 修改后的物品实例，如果没有修改则返回null
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
        Item Modify (RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings);
    }
    @FunctionalInterface
    public interface BlockRegister {
        Block Modify (RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings);
    }
    @FunctionalInterface
    public interface BlockItemRegister {
        Item Modify (Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings);
    }
}
