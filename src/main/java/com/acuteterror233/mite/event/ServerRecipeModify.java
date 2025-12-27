package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.recipe.RecipeEntry;

import java.util.List;

public interface ServerRecipeModify {
    Event<ServerRecipeModify> EVENT = EventFactory.createArrayBacked(ServerRecipeModify.class,
            (listeners) -> (List<RecipeEntry<?>> list) -> {
                for (ServerRecipeModify listener : listeners) {
                    listener.interact(list);
                }
            });
    void interact(List<RecipeEntry<?>> list);
}
