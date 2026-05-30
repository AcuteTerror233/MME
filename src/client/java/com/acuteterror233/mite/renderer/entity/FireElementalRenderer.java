package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.FireElemental;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 火元素渲染器。
 */
public class FireElementalRenderer extends HumanoidMobRenderer<FireElemental, ZombieRenderState, ZombieModel<ZombieRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/fire_elemental.png");

    public FireElementalRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.ZOMBIE);
    }

    public FireElementalRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation) {
        super(context, new ZombieModel<>(context.bakeLayer(modelLayerLocation)), 0.5F);
    }


    @Override
    public @NotNull ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ZombieRenderState livingEntityRenderState) {
        return TEXTURE;
    }
}
