package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.VampireBat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 吸血鬼蝙蝠渲染器基类。
 */
public class VampireBatRenderer<T extends VampireBat> extends MobRenderer<T, BatRenderState, BatModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/bat.png");
    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/bat/vampire_bat.png");

    public VampireBatRenderer(EntityRendererProvider.Context context) {
        super(context, new BatModel(context.bakeLayer(ModelLayers.BAT)), 0.25F);
        this.addLayer(new RenderLayer<>(this) {
            @Override
            public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, BatRenderState entityRenderState, float f, float g) {
                RenderType renderType = this.getParentModel().renderType(EYES);
                VertexConsumer buffer = multiBufferSource.getBuffer(renderType);
                getParentModel().renderToBuffer(poseStack, buffer, i, OverlayTexture.NO_OVERLAY);
            }
        });
    }

    @Override
    public @NotNull BatRenderState createRenderState() {
        return new BatRenderState();
    }

    @Override
    public void extractRenderState(T bat, BatRenderState state, float tickDelta) {
        super.extractRenderState(bat, state, tickDelta);
        state.isResting = bat.isResting();
        state.flyAnimationState.copyFrom(bat.flyAnimationState);
        state.restAnimationState.copyFrom(bat.restAnimationState);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BatRenderState renderState) {
        return TEXTURE;
    }
}