package com.acuteterror233.mite;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.gui.screen.inventory.GradeAnvilScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeCraftingTableScreen;
import com.acuteterror233.mite.gui.screen.inventory.GradeFurnaceScreen;
import com.acuteterror233.mite.gui.screen.inventory.MMEEnchantmentScreen;
import com.acuteterror233.mite.renderer.entity.*;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

/**
 * MME 模组客户端入口，实现 {@link ClientModInitializer}。
 * 注册客户端渲染器、GUI 界面、模型生成和颜色提供器。
 */
public class MMEClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        DimensionRenderingRegistry.registerCloudRenderer(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY, context -> {});

        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.UNDERGROUND_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.HOME_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.RUNE_PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MMEBlocks.BLUE_BERRY_BUSH, RenderType.cutout());
        MenuScreens.register(MMEBlocks.GRADE_ANVIL, GradeAnvilScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_CRAFTING_TABLE, GradeCraftingTableScreen::new);
        MenuScreens.register(MMEBlocks.GRADE_FURNACE, GradeFurnaceScreen::new);
        MenuScreens.register(MMEBlocks.MME_ENCHANTMENT, MMEEnchantmentScreen::new);

        EntityRendererRegistry.register(MMEEntityTypes.GHOUL, GhoulRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.SHADOW, ShadowRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.WIGHT, WightRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.INVISIBLE_STALKER, InvisibleStalkerRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.DEMON_SPIDER, DemonSpiderRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.PHASE_SPIDER, PhaseSpiderRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.INFERNAL_CREEPER, InfernalCreeperRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.FIRE_ELEMENTAL, FireElementalRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.VAMPIRE_BAT, VampireBatRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.NIGHTWING, NightwingRenderer::new);
        EntityRendererRegistry.register(MMEEntityTypes.GIANT_VAMPIRE_BAT, GiantVampireBatRenderer::new);
	}
}
