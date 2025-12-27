package com.acuteterror233.mite.data;

import com.acuteterror233.mite.Mme;
import net.minecraft.block.Block;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class AtModels {
    public static final TextureKey BODY = TextureKey.of("body");
    public static final Model TEMPLATE_ANVIL = new Model(Optional.of(Identifier.of(Mme.MOD_ID,"block/template_anvil")),Optional.empty(), BODY, TextureKey.TOP,TextureKey.PARTICLE);
    public static TextureMap TEMPLATE_ANVIL(Block intact, Block top) {
        return new TextureMap()
                .put(BODY, TextureMap.getSubId(intact,""))
                .put(TextureKey.PARTICLE, TextureMap.getSubId(intact, ""))
                .put(TextureKey.TOP, TextureMap.getSubId(top, "_top"));
    }
}
