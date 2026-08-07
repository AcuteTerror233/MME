package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.Nightwing;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * 夜翼渲染器。
 */
public class NightwingRenderer extends VampireBatRenderer<Nightwing>{
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/entity/bat/nightwing.png");
    public NightwingRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(BatRenderState renderState) {
        return TEXTURE;
    }
}
