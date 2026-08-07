package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * 隐身追踪者渲染器。
 */
public class InvisibleStalkerRenderer extends ZombieRenderer {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/zombie/invisible_stalker.png");

    public InvisibleStalkerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(ZombieRenderState renderState) {
        return TEXTURE;
    }

    @Override
    protected RenderType getRenderType(ZombieRenderState renderState, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }
}
