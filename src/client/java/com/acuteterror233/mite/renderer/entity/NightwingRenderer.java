package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.Nightwing;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class NightwingRenderer extends VampireBatRenderer<Nightwing>{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/bat/nightwing.png");
    public NightwingRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BatRenderState renderState) {
        return TEXTURE;
    }
}
