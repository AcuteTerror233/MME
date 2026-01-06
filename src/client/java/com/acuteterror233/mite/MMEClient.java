package com.acuteterror233.mite;

import com.acuteterror233.mite.block.AtBlocks;
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
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ADAMANTIUM_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ANCIENT_METAL_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.GOLDEN_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.MITHRIL_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.SILVER_BARS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.COPPER_BARS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ADAMANTIUM_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.ANCIENT_METAL_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.MITHRIL_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.SILVER_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.GOLDEN_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.UNDERGROUND_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.HOME_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AtBlocks.RUNE_PORTAL, RenderType.translucent());
        MenuScreens.register(AtBlocks.GRADE_ANVIL, GradeAnvilScreen::new);
        MenuScreens.register(AtBlocks.GRADE_CRAFTING_TABLE, GradeCraftingTableScreen::new);
        MenuScreens.register(AtBlocks.GRADE_FURNACE, GradeFurnaceScreen::new);
	}
}