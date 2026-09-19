package com.acuteterror233.mite.mixin;

import com.acuteterror233.mite.block.VanillaBlockModify;
import com.acuteterror233.mite.block.entity.VanillaBlockEntityTypeModify;
import com.acuteterror233.mite.event.VanillaRegisterModify;
import com.acuteterror233.mite.item.VanillaItemModify;
import com.acuteterror233.mite.world.level.storage.loot.providers.number.ints.VanillaCookingModify;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Mixin for {@code Bootstrap} — Triggers vanilla registration modifications at startup.
 */
@Mixin(Bootstrap.class)
public class BootstrapMixin {
    @Inject(method = "bootStrap()V", at = @At("HEAD"), remap = false)
    private static void initialize(CallbackInfo ci) {
        registerBlockModifications();
        registerItemModifications();
        registerBlockItemModifications();
        registerBlockEntityTypeModifications();
        registerCookingModifications();
    }

    @Unique
    private static void registerBlockModifications() {
        VanillaRegisterModify.BLOCK_REGISTER.register((key, factory, settings) -> {
            Function<BlockBehaviour.Properties, Block> customFactory = VanillaBlockModify.BLOCK_FACTORY_MODIFY.get(key.identifier());
            UnaryOperator<BlockBehaviour.Properties> settingsModifier = VanillaBlockModify.BLOCK_SETTINGS_MODIFY.get(key.identifier());

            if (settingsModifier != null && customFactory != null) {
                return customFactory.apply(settingsModifier.apply(settings).setId(key));
            } else if (customFactory != null) {
                return customFactory.apply(settings);
            } else if (settingsModifier != null) {
                return factory.apply(settingsModifier.apply(settings).setId(key));
            }

            return null;
        });
    }

    @Unique
    private static void registerItemModifications() {
        VanillaRegisterModify.ITEM_REGISTER.register((key, factory, settings) -> {
            Function<Item.Properties, Item> customFactory = VanillaItemModify.ITEM_FACTORY_MODIFY.get(key.identifier());
            UnaryOperator<Item.Properties> settingsModifier = VanillaItemModify.ITEM_SETTINGS_MODIFY.get(key.identifier());

            if (customFactory != null && settingsModifier != null) {
                return customFactory.apply(settingsModifier.apply(settings).setId(key));
            } else if (customFactory != null) {
                return customFactory.apply(settings);
            } else if (settingsModifier != null) {
                return factory.apply(settingsModifier.apply(settings).setId(key));
            }

            return null;
        });
    }

    @Unique
    private static void registerBlockItemModifications() {
        VanillaRegisterModify.BLOCK_ITEM_REGISTER.register((block, factory, settings) -> {
            UnaryOperator<Item.Properties> classSettingsModifier = VanillaItemModify.IN_CLASS_BLOCK_ITEM_SETTINGS_MODIFY.get(block.getClass());
            UnaryOperator<Item.Properties> identifierSettingsModifier = VanillaItemModify.IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY.get(block.builtInRegistryHolder().key().identifier());

            if (classSettingsModifier != null && identifierSettingsModifier != null) {
                Item.Properties modifiedSettings = classSettingsModifier.apply(settings);
                modifiedSettings = identifierSettingsModifier.apply(modifiedSettings);
                return factory.apply(block, modifiedSettings);
            } else if (classSettingsModifier != null) {
                return factory.apply(block, classSettingsModifier.apply(settings));
            } else if (identifierSettingsModifier != null) {
                return factory.apply(block, identifierSettingsModifier.apply(settings));
            }

            return null;
        });
    }

    @Unique
    private static void registerBlockEntityTypeModifications() {
        VanillaRegisterModify.BLOCK_ENTITY_TYPE.register(((key, blocks) -> {
            UnaryOperator<Block[]> operator = VanillaBlockEntityTypeModify.IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY.getOrDefault(key.identifier(), null);
            return operator != null ? operator.apply(blocks) : null;
        }));
    }

    @Unique
    private static void registerCookingModifications() {
        VanillaRegisterModify.COOKING.register(((context, key, i) -> {
            Function<BootstrapContext<ContextIntProvider>,  ContextIntProvider> operator = VanillaCookingModify.IN_KEY_COOKING_MODIFY.getOrDefault(key, null);
            return operator != null ? operator.apply(context) : null;
        }));
    }
}
