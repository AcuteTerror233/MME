package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.renderer.entity.layers.RevenantEyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

/**
 * Revenant renderer: vanilla zombie model and skin, told apart by the glowing red-eyes
 * overlay layer.
 */
public class RevenantRenderer extends ZombieRenderer {
    private static final Identifier VANILLA_ZOMBIE_LOCATION = Identifier.withDefaultNamespace("textures/entity/zombie/zombie.png");

    public RevenantRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new RevenantEyesLayer(this));
    }

    /** {@inheritDoc} Returns the vanilla zombie texture. */
    @Override
    public @NotNull Identifier getTextureLocation(@NonNull ZombieRenderState renderState) {
        return VANILLA_ZOMBIE_LOCATION;
    }
}
