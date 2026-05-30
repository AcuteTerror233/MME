package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.renderer.entity.layers.PhaseSpiderEyesLayer;
import com.acuteterror233.mite.world.entity.monster.PhaseSpider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 相位蜘蛛渲染器，带传送粒子效果。
 */
public class PhaseSpiderRenderer extends SpiderRenderer<PhaseSpider> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/spider/phase_spider.png");

    public PhaseSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.CAVE_SPIDER);
        this.shadowRadius = 0.56F;
        this.addLayer(new PhaseSpiderEyesLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return TEXTURE;
    }
}
