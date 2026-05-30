package com.acuteterror233.mite.renderer.entity.layers;

import com.acuteterror233.mite.MME;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
/**
 * 相位蜘蛛眼睛发光层渲染。
 */
public class PhaseSpiderEyesLayer<M extends SpiderModel> extends EyesLayer<LivingEntityRenderState, M> {
    private static final RenderType SPIDER_EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/phase_spider_eyes.png"));

    public PhaseSpiderEyesLayer(RenderLayerParent<LivingEntityRenderState, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public RenderType renderType() {
        return SPIDER_EYES;
    }
}

