package com.acuteterror233.mite;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.gui.screen.inventory.GradeAnvilScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeCraftingTableScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeFurnaceScreen;
import com.acuteterror233.mite.gui.screen.inventory.MMEEnchantmentScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class MMEClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.UNDERGROUND_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.HOME_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.RUNE_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.BLUE_BERRY_BUSH, RenderType.cutout());
        MenuScreens.register(MMEBlocks.GRADE_ANVIL, GradeAnvilScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_CRAFTING_TABLE, GradeCraftingTableScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_FURNACE, GradeFurnaceScreen::new);
        MenuScreens.register(MMEBlocks.MME_ENCHANTMENT, MMEEnchantmentScreen::new);
	}
}