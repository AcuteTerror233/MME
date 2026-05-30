package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 尸鬼渲染器。
 */
public class WightRenderer extends ZombieRenderer {
    private static final ResourceLocation WIGHT_LOCATION = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/zombie/wight.png");

    public WightRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ZombieRenderState renderState) {
        return WIGHT_LOCATION;
    }
}
