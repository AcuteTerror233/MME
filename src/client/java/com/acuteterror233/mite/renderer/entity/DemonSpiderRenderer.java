package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.DemonSpider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DemonSpiderRenderer extends SpiderRenderer<DemonSpider> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/spider/demon_spider.png");

    public DemonSpiderRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return TEXTURE;
    }
}
