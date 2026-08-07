package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * 暗影渲染器。
 */
public class ShadowRenderer extends ZombieRenderer {
    private static final Identifier SHADOW_LOCATION = Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/zombie/shadow.png");

    public ShadowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(ZombieRenderState renderState) {
        return SHADOW_LOCATION;
    }
}
