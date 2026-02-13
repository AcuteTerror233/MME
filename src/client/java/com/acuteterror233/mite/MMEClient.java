package com.acuteterror233.mite;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.gui.screen.inventory.GradeAnvilScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeCraftingTableScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeFurnaceScreen;
import com.acuteterror233.mite.gui.screen.inventory.MMEEnchantmentScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class MMEClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.putBlock(MMEBlocks.ADAMANTIUM_BARS, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(MMEBlocks.ANCIENT_METAL_BARS, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(MMEBlocks.GOLDEN_BARS, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(MMEBlocks.MITHRIL_BARS, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(MMEBlocks.SILVER_BARS, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(MMEBlocks.COPPER_BARS, ChunkSectionLayer.CUTOUT_MIPPED);
        BlockRenderLayerMap.putBlock(MMEBlocks.ADAMANTIUM_DOOR, ChunkSectionLayer.CUTOUT_MIPPED);
        BlockRenderLayerMap.putBlock(MMEBlocks.ANCIENT_METAL_DOOR, ChunkSectionLayer.CUTOUT_MIPPED);
        BlockRenderLayerMap.putBlock(MMEBlocks.MITHRIL_DOOR, ChunkSectionLayer.CUTOUT_MIPPED);
        BlockRenderLayerMap.putBlock(MMEBlocks.SILVER_DOOR, ChunkSectionLayer.CUTOUT_MIPPED);
        BlockRenderLayerMap.putBlock(MMEBlocks.GOLDEN_DOOR, ChunkSectionLayer.CUTOUT_MIPPED);
        BlockRenderLayerMap.putBlock(MMEBlocks.UNDERGROUND_PORTAL, ChunkSectionLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(MMEBlocks.HOME_PORTAL, ChunkSectionLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(MMEBlocks.RUNE_PORTAL, ChunkSectionLayer.TRANSLUCENT);
        MenuScreens.register(MMEBlocks.GRADE_ANVIL, GradeAnvilScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_CRAFTING_TABLE, GradeCraftingTableScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_FURNACE, GradeFurnaceScreen::new);
        MenuScreens.register(MMEBlocks.MME_ENCHANTMENT, MMEEnchantmentScreen::new);
	}
}