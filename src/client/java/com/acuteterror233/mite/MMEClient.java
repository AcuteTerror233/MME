package com.acuteterror233.mite;

import com.acuteterror233.mite.renderer.entity.*;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

/**
 * MME mod client entrypoint, implementing {@link ClientModInitializer}.
 * Registers the entity renderers for all MME custom entities (client-only; the dedicated server
 * never loads this class).
 */
public class MMEClient implements ClientModInitializer {
    /** Registers one renderer per MME entity type. */
    @Override
    public void onInitializeClient() {
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
