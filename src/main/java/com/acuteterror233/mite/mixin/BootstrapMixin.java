package com.acuteterror233.mite.mixin;

import com.acuteterror233.mite.block.VanillaBLockModify;
import com.acuteterror233.mite.event.VanillaRegisterModify;
import com.acuteterror233.mite.item.VanillaItemModify;
import net.minecraft.Bootstrap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
    @Inject(method = "initialize",at = @At("HEAD"))
    private static void initialize(CallbackInfo ci) {
        VanillaRegisterModify.BLOCK_REGISTER.register((key, factory, settings) -> {
            Function<AbstractBlock.Settings, Block> customFactory = VanillaBLockModify.BLOCK_FACTORY_MODIFY.get(key.getValue());
            UnaryOperator<AbstractBlock.Settings> settingsFactory = VanillaBLockModify.BLOCK_SETTINGS_MODIFY.get(key.getValue());
            if (settingsFactory != null && customFactory != null){
                return customFactory.apply(settingsFactory.apply(settings).registryKey(key));
            }else if (customFactory != null){
                return customFactory.apply(settings);
            }else if (settingsFactory != null){
                return factory.apply(settingsFactory.apply(settings).registryKey(key));
            }
            return null;
        });
        VanillaRegisterModify.ITEM_REGISTER.register((key, factory, settings) -> {
            Function<Item.Settings, Item> customFactory = VanillaItemModify.ITEM_FACTORY_MODIFY.get(key.getValue());
            UnaryOperator<Item.Settings> settingsFactory = VanillaItemModify.ITEM_SETTINGS_MODIFY.get(key.getValue());
            if (customFactory != null && settingsFactory != null){
                return customFactory.apply(settingsFactory.apply(settings).registryKey(key));
            }else if (customFactory != null){
                return customFactory.apply(settings);
            }else if (settingsFactory != null){
                return factory.apply(settingsFactory.apply(settings).registryKey(key));
            }
            return null;
        });
        VanillaRegisterModify.BLOCK_ITEM_REGISTER.register((block, factory, settings) -> {
            UnaryOperator<Item.Settings> inClassSettingsFactory = VanillaItemModify.IN_CLASS_BLOCK_ITEM_SETTINGS_MODIFY.get(block.getClass());
            UnaryOperator<Item.Settings> inIdentifierSettingsFactory = VanillaItemModify.IN_IDENTIFIER_ITEM_SETTINGS_MODIFY.get(block.getRegistryEntry().registryKey());
            if (inClassSettingsFactory != null) {
                return factory.apply(block, inClassSettingsFactory.apply(settings));
            }if (inIdentifierSettingsFactory != null){
                return factory.apply(block, inIdentifierSettingsFactory.apply(settings));
            }
            return null;
        });
    }
}
