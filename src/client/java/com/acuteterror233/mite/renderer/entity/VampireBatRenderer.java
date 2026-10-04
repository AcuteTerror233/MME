package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.VampireBat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ambient.BatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for Vampire Bat renderers.
 */
public class VampireBatRenderer<T extends VampireBat> extends MobRenderer<T, BatRenderState, BatModel> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/bat/bat.png");
    private static final RenderType EYES = RenderTypes.eyes(Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/bat/vampire_bat.png"));

    public VampireBatRenderer(EntityRendererProvider.Context context) {
        super(context, new BatModel(context.bakeLayer(ModelLayers.BAT)), 0.25F);
        // Extra emissive pass drawing the vampire bat eye texture over the vanilla bat model.
        this.addLayer(new RenderLayer<>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, BatRenderState entityRenderState, float f, float g) {
                submitNodeCollector.order(1)
                        .submitModel(
                                this.getParentModel(), entityRenderState, poseStack, EYES, i, OverlayTexture.NO_OVERLAY, -1, null, entityRenderState.outlineColor
                        );
            }
        });
    }

    /** {@inheritDoc} Uses the vanilla bat render state. */
    @Override
    public @NotNull BatRenderState createRenderState() {
        return new BatRenderState();
    }

    /** {@inheritDoc} Additionally copies MME's resting/flying animation states from the entity. */
    @Override
    public void extractRenderState(T bat, BatRenderState state, float tickDelta) {
        super.extractRenderState(bat, state, tickDelta);
        state.isResting = bat.isResting();
        state.flyAnimationState.copyFrom(bat.flyAnimationState);
        state.restAnimationState.copyFrom(bat.restAnimationState);
    }

    /** {@inheritDoc} Returns the vanilla bat texture; vampire identity comes from the eyes layer. */
    @Override
    public @NotNull Identifier getTextureLocation(BatRenderState renderState) {
        return TEXTURE;
    }
}