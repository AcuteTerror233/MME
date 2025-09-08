package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.atinterface.ItemSettingsExtension;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(Items.class)
public class ItemsMixin {
    @Shadow public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings){return null;}
    @Shadow private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey){return null;}
    @Shadow private static RegistryKey<Item> keyOf(String id){return null;}
    @Overwrite
    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
        if (block == Blocks.IRON_BLOCK){
            settings.recipeRemainder(Items.RAW_IRON_BLOCK);
        }
        return register(
                keyOf(block.getRegistryEntry().registryKey()), itemSettings -> (Item)factory.apply(block, itemSettings), settings.useBlockPrefixedTranslationKey().maxCount(getCountForBlock(block))
        );
    }
    @Unique
    private static int getCountForBlock(Block block) {
        Class<?> blockClass = block.getClass();
        if (At_mite.COUNT1_BLOCK.contains(blockClass)) {
            return 1;
        }
        if (At_mite.COUNT8_BLOCK.contains(blockClass)) {
            return 8;
        }
        if (At_mite.COUNT16_BLOCK.contains(blockClass)) {
            return 16;
        }
        if (At_mite.COUNT32_BLOCK.contains(blockClass)) {
            return 32;
        }
        if (block instanceof FenceBlock){
            return 6;
        }
        return 4;
    }
    @Overwrite
    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return newRegister(id, factory, new Item.Settings());
    }

    @Overwrite
    public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return newRegister(id, factory, settings);
    }

    @Overwrite
    public static Item register(String id, Item.Settings settings) {
        return newRegister(id, Item::new, settings);
    }

    @Overwrite
    public static Item register(String id) {
        return newRegister(id, Item::new, new Item.Settings());
    }

    @Unique
    private static Item newRegister(String id, Function<Item.Settings, Item> factory, Item.Settings settings){
        if (!((ItemSettingsExtension)settings).getUseMaxDamage()){
            if (At_mite.COUNT1_ITEM.contains(id)) {
                settings.maxCount(1);
            }else if (At_mite.COUNT8_ITEM.contains(id)){
                settings.maxCount(8);
            } else if (At_mite.COUNT16_ITEM.contains(id)) {
                settings.maxCount(16);
            } else if (At_mite.COUNT32_ITEM.contains(id)) {
                settings.maxCount(32);
            }
        }
        return register(keyOf(id),factory,settings);
    }
}