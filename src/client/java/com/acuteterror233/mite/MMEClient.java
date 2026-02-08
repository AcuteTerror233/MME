package com.acuteterror233.mite;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.gui.screen.inventory.GradeAnvilScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeCraftingTableScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeFurnaceScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class MMEClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.ADAMANTIUM_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.ANCIENT_METAL_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.GOLDEN_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.MITHRIL_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.SILVER_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.COPPER_BARS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.ADAMANTIUM_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.ANCIENT_METAL_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.MITHRIL_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.SILVER_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.GOLDEN_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.UNDERGROUND_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.HOME_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.RUNE_PORTAL, RenderType.translucent());
        MenuScreens.register(MMEBlocks.GRADE_ANVIL, GradeAnvilScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_CRAFTING_TABLE, GradeCraftingTableScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_FURNACE, GradeFurnaceScreen::new);
	}
}