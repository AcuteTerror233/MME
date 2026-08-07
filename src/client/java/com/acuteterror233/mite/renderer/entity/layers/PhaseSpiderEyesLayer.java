package com.acuteterror233.mite.renderer.entity.layers;

import com.acuteterror233.mite.MME;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.monster.spider.SpiderModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * 相位蜘蛛眼睛发光层渲染。
 */
@Environment(EnvType.CLIENT)
public class PhaseSpiderEyesLayer<M extends SpiderModel> extends EyesLayer<LivingEntityRenderState, M> {
    private static final RenderType SPIDER_EYES = RenderTypes.eyes(Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/phase_spider_eyes.png"));

    public PhaseSpiderEyesLayer(RenderLayerParent<LivingEntityRenderState, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public @NotNull RenderType renderType() {
        return SPIDER_EYES;
    }
}

