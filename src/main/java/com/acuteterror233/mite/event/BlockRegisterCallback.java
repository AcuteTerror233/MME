package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;

public interface BlockRegisterCallback {
    Event<BlockRegisterCallback> EVENT = EventFactory.createArrayBacked(BlockRegisterCallback.class,
            (listeners) -> (Class<?> registryKey, Item.Settings settings) ->{
                for (BlockRegisterCallback listener : listeners) {
                    ActionResult result = listener.interact(registryKey, settings);
                    if (result != ActionResult.PASS) return result;
                }
                return ActionResult.PASS;
            });
    ActionResult interact(Class<?> registryKey, Item.Settings settings);
}
