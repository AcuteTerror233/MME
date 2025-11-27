package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.ActionResult;

public interface ItemRegisterCallback {
    Event<ItemRegisterCallback> EVENT = EventFactory.createArrayBacked(ItemRegisterCallback.class,
            (listeners) -> (RegistryKey<Item> registryKey, Item.Settings settings) ->{
                for (ItemRegisterCallback listener : listeners) {
                    ActionResult result = listener.interact(registryKey, settings);
                    if (result != ActionResult.PASS) return result;
                }
                return ActionResult.PASS;
            });
    ActionResult interact(RegistryKey<Item> registryKey, Item.Settings settings);
}
