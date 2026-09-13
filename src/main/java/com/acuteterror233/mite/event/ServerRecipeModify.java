package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

/**
 * Server-side recipe modification callback interface.
 * Triggers after recipes are loaded, allowing dynamic recipe modification.
 */
public interface ServerRecipeModify {
    Event<ServerRecipeModify> EVENT = EventFactory.createArrayBacked(ServerRecipeModify.class,
            (listeners) -> (List<RecipeHolder<?>> list) -> {
                for (ServerRecipeModify listener : listeners) {
                    listener.interact(list);
                }
            });
    void interact(List<RecipeHolder<?>> list);
}
