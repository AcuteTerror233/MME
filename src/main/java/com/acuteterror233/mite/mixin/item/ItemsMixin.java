package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.event.VanillaRegisterModify;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
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
    public static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
        return null;
    }

    @Inject(method = "register(Lnet/minecraft/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;",at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings, CallbackInfoReturnable<Item> cir) {
        RegistryKey<Item> itemKey = keyOf(block.getRegistryEntry().registryKey());
        Item modify = VanillaRegisterModify.BLOCK_ITEM_REGISTER.invoker().Modify(block, factory, settings.useBlockPrefixedTranslationKey().registryKey(itemKey));
        if (modify != null){
            Item.BLOCK_ITEMS.put(block, modify);
            cir.setReturnValue(Registry.register(Registries.ITEM, itemKey, modify));
        }
    }
    @Inject(method = "register(Lnet/minecraft/registry/RegistryKey;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings, CallbackInfoReturnable<Item> cir) {
        Item modify = VanillaRegisterModify.ITEM_REGISTER.invoker().Modify(key, factory, settings.registryKey(key));
        if (modify != null) {
            cir.setReturnValue(Registry.register(Registries.ITEM, key, modify));
        }
    }
}