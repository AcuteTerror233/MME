package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GhoulRenderer extends ZombieRenderer {
    private static final ResourceLocation GHOUL_LOCATION = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/zombie/ghoul.png");

    public GhoulRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ZombieRenderState renderState) {
        return GHOUL_LOCATION;
    }
}
