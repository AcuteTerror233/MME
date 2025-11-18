package com.acuteterror233.mite;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.gui.screen.ingame.AtAnvilScreen;
import com.acuteterror233.mite.gui.screen.ingame.CraftingTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class At_miteClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ADAMANTIUM_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ANCIENT_METAL_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.GOLDEN_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.MITHRIL_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.SILVER_BARS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.COPPER_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ADAMANTIUM_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ANCIENT_METAL_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.MITHRIL_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.SILVER_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.GOLDEN_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.UNDERGROUND_PORTAL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.HOME_PORTAL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.RUNE_PORTAL, RenderLayer.getTranslucent());
        HandledScreens.register(AtBlocks.ATANVILSCREENHANDLER, AtAnvilScreen::new);
        HandledScreens.register(AtBlocks.CRAFTINGTABLESCREENHANDLER, CraftingTableScreen::new);
	}
}