package com.acuteterror233.mite;

import com.acuteterror233.mite.block.At_Blocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class At_miteClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.ADAMANTIUM_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.ANCIENT_METAL_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.GOLD_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.MITHRIL_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.SILVER_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.ADAMANTIUM_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.ANCIENT_METAL_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.MITHRIL_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.SILVER_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(At_Blocks.GOLD_DOOR, RenderLayer.getCutout());
	}
}