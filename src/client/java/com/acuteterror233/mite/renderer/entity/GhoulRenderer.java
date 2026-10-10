package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

/**
 * Ghoul renderer: vanilla zombie model with the ghoul skin.
 */
public class GhoulRenderer extends ZombieRenderer {
    private static final Identifier GHOUL_LOCATION = Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/zombie/ghoul.png");

    public GhoulRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /** {@inheritDoc} Returns the ghoul texture. */
    @Override
    public @NotNull Identifier getTextureLocation(@NonNull ZombieRenderState renderState) {
        return GHOUL_LOCATION;
    }
}
