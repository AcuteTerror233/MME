package com.acuteterror233.mite.mixin;

import com.acuteterror233.mite.block.VanillaBlockModify;
import com.acuteterror233.mite.event.VanillaRegisterModify;
import com.acuteterror233.mite.item.VanillaItemModify;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
    @Inject(method = "bootStrap",at = @At("HEAD"))
    private static void initialize(CallbackInfo ci) {
        VanillaRegisterModify.BLOCK_REGISTER.register((key, factory, settings) -> {
            Function<BlockBehaviour.Properties, Block> customFactory = VanillaBlockModify.BLOCK_FACTORY_MODIFY.get(key.location());
            UnaryOperator<BlockBehaviour.Properties> settingsFactory = VanillaBlockModify.BLOCK_SETTINGS_MODIFY.get(key.location());
            if (settingsFactory != null && customFactory != null){
                return customFactory.apply(settingsFactory.apply(settings).setId(key));
            }else if (customFactory != null){
                return customFactory.apply(settings);
            }else if (settingsFactory != null){
                return factory.apply(settingsFactory.apply(settings).setId(key));
            }
            return null;
        });
        VanillaRegisterModify.ITEM_REGISTER.register((key, factory, settings) -> {
            Function<Item.Properties, Item> customFactory = VanillaItemModify.ITEM_FACTORY_MODIFY.get(key.location());
            UnaryOperator<Item.Properties> settingsFactory = VanillaItemModify.ITEM_SETTINGS_MODIFY.get(key.location());
            if (customFactory != null && settingsFactory != null){
                return customFactory.apply(settingsFactory.apply(settings).setId(key));
            }else if (customFactory != null){
                return customFactory.apply(settings);
            }else if (settingsFactory != null){
                return factory.apply(settingsFactory.apply(settings).setId(key));
            }
            return null;
        });
        VanillaRegisterModify.BLOCK_ITEM_REGISTER.register((block, factory, settings) -> {
            UnaryOperator<Item.Properties> inClassSettingsFactory = VanillaItemModify.IN_CLASS_BLOCK_ITEM_SETTINGS_MODIFY.get(block.getClass());
            UnaryOperator<Item.Properties> inIdentifierSettingsFactory = VanillaItemModify.IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY.get(block.builtInRegistryHolder().key().location());
            if (inClassSettingsFactory != null) {
                return factory.apply(block, inClassSettingsFactory.apply(settings));
            }if (inIdentifierSettingsFactory != null){
                return factory.apply(block, inIdentifierSettingsFactory.apply(settings));
            }
            return null;
        });
    }
}
