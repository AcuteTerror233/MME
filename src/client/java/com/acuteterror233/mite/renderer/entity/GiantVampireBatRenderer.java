package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.world.entity.monster.GiantVampireBat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;

/**
 * 巨型吸血鬼蝙蝠渲染器。
 */
public class GiantVampireBatRenderer extends VampireBatRenderer<GiantVampireBat>{
    public GiantVampireBatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    public void submit(BatRenderState livingEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
        super.submit(livingEntityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }
}
