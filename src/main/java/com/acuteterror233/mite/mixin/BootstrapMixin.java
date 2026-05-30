package com.acuteterror233.mite.mixin;

import com.acuteterror233.mite.block.VanillaBlockModify;
import com.acuteterror233.mite.event.VanillaRegisterModify;
import com.acuteterror233.mite.item.VanillaItemModify;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@Mixin(Bootstrap.class)
/**
 * Mixin for {@code Bootstrap} — 在启动阶段触发原版注册修改。
 */
public class BootstrapMixin {
    @Inject(method = "bootStrap()V", at = @At("HEAD"), remap = false)
    private static void initialize(CallbackInfo ci) {
        registerBlockModifications();
        registerItemModifications();
        registerBlockItemModifications();
    }

    @Unique
    private static void registerBlockModifications() {
        VanillaRegisterModify.BLOCK_REGISTER.register((key, factory, settings) -> {
            Function<BlockBehaviour.Properties, Block> customFactory = VanillaBlockModify.BLOCK_FACTORY_MODIFY.get(key.location());
            UnaryOperator<BlockBehaviour.Properties> settingsModifier = VanillaBlockModify.BLOCK_SETTINGS_MODIFY.get(key.location());

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
            Function<Item.Properties, Item> customFactory = VanillaItemModify.ITEM_FACTORY_MODIFY.get(key.location());
            UnaryOperator<Item.Properties> settingsModifier = VanillaItemModify.ITEM_SETTINGS_MODIFY.get(key.location());

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
            UnaryOperator<Item.Properties> identifierSettingsModifier = VanillaItemModify.IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY.get(block.builtInRegistryHolder().key().location());

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
}
