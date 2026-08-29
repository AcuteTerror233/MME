package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.event.VanillaRegisterModify;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mixin for {@code Items} — 修改原版物品注册。
 */
@Mixin(Items.class)
public class ItemsMixin {

    @Inject(method = "registerBlock(Lnet/minecraft/references/BlockItemId;Lnet/minecraft/world/level/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;",at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(BlockItemId id, Block block, BiFunction<Block, Item.Properties, Item> itemFactory, Item.Properties properties, CallbackInfoReturnable<Item> cir) {
        ResourceKey<Item> itemKey = id.item();
        Item modify = VanillaRegisterModify.BLOCK_ITEM_REGISTER.invoker().Modify(block, itemFactory, properties.useBlockDescriptionPrefix().setId(itemKey));
        if (modify != null){
            Item.BY_BLOCK.put(block, modify);
            cir.setReturnValue(Registry.register(BuiltInRegistries.ITEM, itemKey, modify));
        }
    }
    @Inject(method = "registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(ResourceKey<Item> id, Function<Item.Properties, Item> itemFactory, Item.Properties properties, CallbackInfoReturnable<Item> cir) {
        Item modify = VanillaRegisterModify.ITEM_REGISTER.invoker().Modify(id, itemFactory, properties.setId(id));
        if (modify != null) {
            if (modify instanceof BlockItem blockItem) {
                blockItem.registerBlocks(Item.BY_BLOCK, modify);
            }
            cir.setReturnValue(Registry.register(BuiltInRegistries.ITEM, id, modify));
        }
    }
}