package com.acuteterror233.mite.renderer.entity.layers;

import com.acuteterror233.mite.MME;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Revenant glowing red eyes layer rendering.
 */
@Environment(EnvType.CLIENT)
public class RevenantEyesLayer extends EyesLayer<ZombieRenderState, ZombieModel<ZombieRenderState>> {
    private static final RenderType REVENANT_EYES = RenderTypes.eyes(Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/zombie_red_eve.png"));

    public RevenantEyesLayer(RenderLayerParent<ZombieRenderState, ZombieModel<ZombieRenderState>> renderLayerParent) {
        super(renderLayerParent);
    }

    /** {@inheritDoc} Renders the revenant's emissive red-eye texture. */
    @Override
    public @NotNull RenderType renderType() {
        return REVENANT_EYES;
    }
}
