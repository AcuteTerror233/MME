package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * 食尸鬼渲染器。
 */
public class GhoulRenderer extends ZombieRenderer {
    private static final Identifier GHOUL_LOCATION = Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/zombie/ghoul.png");

    public GhoulRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(ZombieRenderState renderState) {
        return GHOUL_LOCATION;
    }
}
