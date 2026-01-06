package com.acuteterror233.mite.data;

import com.acuteterror233.mite.MME;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class TemplateAnvilModels {
    public static final TextureSlot BODY = TextureSlot.create("body");
    public static final ModelTemplate TEMPLATE_ANVIL = new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID,"block/template_anvil")),Optional.empty(), BODY, TextureSlot.TOP,TextureSlot.PARTICLE);
    public static TextureMapping TEMPLATE_ANVIL(Block intact, Block top) {
        return new TextureMapping()
                .put(BODY, TextureMapping.getBlockTexture(intact,""))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(intact, ""))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top, "_top"));
    }
}
