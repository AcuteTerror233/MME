package com.acuteterror233.mite;

import com.acuteterror233.mite.hotkey.MMEAutoModes;
import com.acuteterror233.mite.hotkey.MMEKeybinds;
import com.acuteterror233.mite.interfaces.FoodDataExtension;
import com.acuteterror233.mite.network.FoodNutritionPayload;
import com.acuteterror233.mite.network.PlaceSourceLiquidPayload;
import com.acuteterror233.mite.renderer.entity.*;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderers;

/**
 * MME mod client entrypoint, implementing {@link ClientModInitializer}.
 * Registers the entity renderers for all MME custom entities (client-only; the dedicated server
 * never loads this class), the MITE-style hotkey features (zoom camera, auto harvest/use
 * modes), and the client receivers for the MME networking payloads.
 */
public class MMEClient implements ClientModInitializer {
    /** Registers one renderer per MME entity type. */
    @Override
    public void onInitializeClient() {
        MMEKeybinds.init();
        MMEAutoModes.init();

        // Place-source-liquid hotkey: send the placement request to the server on each key press
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            while (MMEKeybinds.PLACE_SOURCE_LIQUID.consumeClick()) {
                if (minecraft.player != null) {
                    ClientPlayNetworking.send(PlaceSourceLiquidPayload.INSTANCE);
                }
            }
        });

        // Nutrition sync: store the server-side protein/fiber/sugar counters on the client food data
        ClientPlayNetworking.registerGlobalReceiver(FoodNutritionPayload.TYPE, (payload, context) -> {
            LocalPlayer player = context.player();
            if (player != null) {
                ((FoodDataExtension) player.getFoodData()).MME$SetFoodNutrition(payload.nutrition());
            }
        });

        EntityRenderers.register(MMEEntityTypes.GHOUL, GhoulRenderer::new);
        EntityRenderers.register(MMEEntityTypes.SHADOW, ShadowRenderer::new);
        EntityRenderers.register(MMEEntityTypes.WIGHT, WightRenderer::new);
        EntityRenderers.register(MMEEntityTypes.INVISIBLE_STALKER, InvisibleStalkerRenderer::new);
        EntityRenderers.register(MMEEntityTypes.DEMON_SPIDER, DemonSpiderRenderer::new);
        EntityRenderers.register(MMEEntityTypes.PHASE_SPIDER, PhaseSpiderRenderer::new);
        EntityRenderers.register(MMEEntityTypes.INFERNAL_CREEPER, InfernalCreeperRenderer::new);
        EntityRenderers.register(MMEEntityTypes.FIRE_ELEMENTAL, FireElementalRenderer::new);
        EntityRenderers.register(MMEEntityTypes.VAMPIRE_BAT, VampireBatRenderer::new);
        EntityRenderers.register(MMEEntityTypes.NIGHTWING, NightwingRenderer::new);
        EntityRenderers.register(MMEEntityTypes.GIANT_VAMPIRE_BAT, GiantVampireBatRenderer::new);
    }
}
