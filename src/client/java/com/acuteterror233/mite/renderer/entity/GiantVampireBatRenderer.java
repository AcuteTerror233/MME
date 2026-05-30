package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.world.entity.monster.GiantVampireBat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BatRenderState;

/**
 * 巨型吸血鬼蝙蝠渲染器。
 */
public class GiantVampireBatRenderer extends VampireBatRenderer<GiantVampireBat>{
    public GiantVampireBatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BatRenderState livingEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
        super.render(livingEntityRenderState, poseStack, multiBufferSource, i);
    }
}
