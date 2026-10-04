package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.FireElemental;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Fire Elemental renderer.
 */
public class FireElementalRenderer extends HumanoidMobRenderer<FireElemental, ZombieRenderState, ZombieModel<ZombieRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/fire_elemental.png");

    /** Creates the renderer with the default vanilla zombie model layer. */
    public FireElementalRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.ZOMBIE);
    }

    /** Creates the renderer with a custom model layer, allowing subclasses to swap the baked geometry. */
    public FireElementalRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation) {
        super(context, new ZombieModel<>(context.bakeLayer(modelLayerLocation)), 0.5F);
    }


    /** {@inheritDoc} Uses the vanilla zombie render state. */
    @Override
    public @NotNull ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    /** {@inheritDoc} Returns the fire elemental texture. */
    @Override
    public @NotNull Identifier getTextureLocation(ZombieRenderState livingEntityRenderState) {
        return TEXTURE;
    }
}
