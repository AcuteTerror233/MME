package com.acuteterror233.mite.data;

import com.acuteterror233.mite.MME;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

/**
 * Anvil model template.
 * Generates block model JSON for anvil variants with different damage states: a shared template
 * whose {@code body}/{@code top} textures are filled per metal, so chipped and damaged anvils
 * reuse the intact anvil's body texture with their own top texture.
 */
public class TemplateAnvilModels {
    /** Texture slot for the anvil body (shared by all damage states of one metal). */
    public static final TextureSlot BODY = TextureSlot.create("body");
    /** Model template pointing at {@code mme:block/template_anvil}, parameterized by {@link #BODY}, {@link TextureSlot#TOP} and {@link TextureSlot#PARTICLE}. */
    public static final ModelTemplate TEMPLATE_ANVIL = new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(MME.MOD_ID,"block/template_anvil")),Optional.empty(), BODY, TextureSlot.TOP,TextureSlot.PARTICLE);
    /**
     * Builds the texture mapping for one anvil damage state: body and particle come from the
     * intact anvil, the top face from the specific damage-state block.
     *
     * @param intact the intact anvil block providing body/particle textures
     * @param top    the damage-state block providing the top texture
     * @return the texture mapping for the anvil template
     */
    public static TextureMapping TEMPLATE_ANVIL(Block intact, Block top) {
        return new TextureMapping()
                .put(BODY, TextureMapping.getBlockTexture(intact,""))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(intact, ""))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top, "_top"));
    }
}
