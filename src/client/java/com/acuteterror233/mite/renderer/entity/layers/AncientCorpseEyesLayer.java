package com.acuteterror233.mite.renderer.entity.layers;

import com.acuteterror233.mite.MME;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Ancient Corpse glowing red eyes layer rendering.
 */
@Environment(EnvType.CLIENT)
public class AncientCorpseEyesLayer extends EyesLayer<SkeletonRenderState, SkeletonModel<SkeletonRenderState>> {
    private static final RenderType ANCIENT_CORPSE_EYES = RenderTypes.eyes(Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/skeleton_red_eve.png"));

    public AncientCorpseEyesLayer(RenderLayerParent<SkeletonRenderState, SkeletonModel<SkeletonRenderState>> renderLayerParent) {
        super(renderLayerParent);
    }

    /** {@inheritDoc} Renders the ancient corpse's emissive red-eye texture. */
    @Override
    public @NotNull RenderType renderType() {
        return ANCIENT_CORPSE_EYES;
    }
}
