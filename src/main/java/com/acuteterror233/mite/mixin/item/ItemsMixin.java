package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.event.BlockRegisterCallback;
import com.acuteterror233.mite.event.ItemRegisterCallback;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(Items.class)
public class ItemsMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;", ordinal = 431))
    private static Item ANVIL(Block block) {
        return register(keyOf(block.getRegistryEntry().registryKey()), (settings -> new BlockItem(block, settings.useBlockPrefixedTranslationKey())), new Item.Settings().maxDamage(AtBlocks.maxDamageAnvil(ToolMaterial.IRON.durability())));
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;", ordinal = 432))
    private static Item CHIPPED_ANVIL(Block block) {
        return register(keyOf(block.getRegistryEntry().registryKey()), (settings -> new BlockItem(block, settings.useBlockPrefixedTranslationKey())), new Item.Settings().maxDamage(AtBlocks.maxDamageAnvil(ToolMaterial.IRON.durability())));
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;", ordinal = 433))
    private static Item DAMAGED_ANVIL(Block block) {
        return register(keyOf(block.getRegistryEntry().registryKey()), (settings -> new BlockItem(block, settings.useBlockPrefixedTranslationKey())), new Item.Settings().maxDamage(AtBlocks.maxDamageAnvil(ToolMaterial.IRON.durability())));
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 17))
    private static Item STONE_SWORD(String string, Item.Settings settings) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;)Lnet/minecraft/item/Item;", ordinal = 5))
    private static Item STONE_SHOVEL(String string, Function<Item.Settings, Item> factory) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 18))
    private static Item STONE_PICKAXE(String string, Item.Settings settings) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;)Lnet/minecraft/item/Item;", ordinal = 6))
    private static Item STONE_AXE(String string, Function<Item.Settings, Item> factory) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;)Lnet/minecraft/item/Item;", ordinal = 7))
    private static Item STONE_HOE(String string, Function<Item.Settings, Item> factory) {
        return register(string, new Item.Settings());
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 17+6))
    private static Item DIAMOND_SWORD(String string, Item.Settings settings) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;)Lnet/minecraft/item/Item;", ordinal = 5+9))
    private static Item DIAMOND_SHOVEL(String string, Function<Item.Settings, Item> factory) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", ordinal = 18+6))
    private static Item DIAMOND_PICKAXE(String string, Item.Settings settings) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;)Lnet/minecraft/item/Item;", ordinal = 6+9))
    private static Item DIAMOND_AXE(String string, Function<Item.Settings, Item> factory) {
        return register(string, new Item.Settings());
    }
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;Ljava/util/function/Function;)Lnet/minecraft/item/Item;", ordinal = 7+9))
    private static Item DIAMOND_HOE(String string, Function<Item.Settings, Item> factory) {
        return register(string, new Item.Settings());
    }
    @Shadow
    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return null;
    }
    @Shadow
    public static Item register(String string, Item.Settings settings) {
        return null;
    }
    @Shadow
    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return null;
    }
    @Shadow
    public static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
        return null;
    }

    @Inject(method = "register(Lnet/minecraft/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;",at = @At(value = "HEAD"))
    private static void onRegister(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings, CallbackInfoReturnable<Item> cir) {
        BlockRegisterCallback.EVENT.invoker().interact(block.getClass(), settings);
    }
    @Inject(method = "register(Lnet/minecraft/registry/RegistryKey;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", at = @At(value = "HEAD"))
    private static void onRegister(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings, CallbackInfoReturnable<Item> cir) {
        ItemRegisterCallback.EVENT.invoker().interact(key, settings);
    }
}