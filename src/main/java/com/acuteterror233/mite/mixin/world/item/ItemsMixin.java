package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.event.VanillaRegisterModify;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(Items.class)
public class ItemsMixin {
    @Shadow
    public static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> blockKey) {
        return null;
    }

    @Inject(method = "registerBlock(Lnet/minecraft/world/level/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;",at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties settings, CallbackInfoReturnable<Item> cir) {
        ResourceKey<Item> itemKey = blockIdToItemId(block.builtInRegistryHolder().key());
        Item modify = VanillaRegisterModify.BLOCK_ITEM_REGISTER.invoker().Modify(block, factory, settings.useBlockDescriptionPrefix().setId(itemKey));
        if (modify != null){
            Item.BY_BLOCK.put(block, modify);
            cir.setReturnValue(Registry.register(BuiltInRegistries.ITEM, itemKey, modify));
        }
    }
    @Inject(method = "registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties settings, CallbackInfoReturnable<Item> cir) {
        Item modify = VanillaRegisterModify.ITEM_REGISTER.invoker().Modify(key, factory, settings.setId(key));
        if (modify != null) {
            cir.setReturnValue(Registry.register(BuiltInRegistries.ITEM, key, modify));
        }
    }
}