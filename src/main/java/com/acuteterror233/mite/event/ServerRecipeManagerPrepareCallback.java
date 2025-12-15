package com.acuteterror233.mite.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.ActionResult;

import java.util.List;

public interface ServerRecipeManagerPrepareCallback {
    Event<ServerRecipeManagerPrepareCallback> EVENT = EventFactory.createArrayBacked(ServerRecipeManagerPrepareCallback.class,
            (listeners) -> (List<RecipeEntry<?>> list) -> {
                for (ServerRecipeManagerPrepareCallback listener : listeners) {
                    ActionResult result = listener.interact(list);
                    if (result != ActionResult.PASS) return result;
                }
                return ActionResult.PASS;
            });
    ActionResult interact(List<RecipeEntry<?>> list);
}
