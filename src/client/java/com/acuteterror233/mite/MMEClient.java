package com.acuteterror233.mite;

import com.acuteterror233.mite.block.MMEMenuTypes;
import com.acuteterror233.mite.gui.screen.inventory.*;
import com.acuteterror233.mite.renderer.entity.*;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;

/**
 * MME 模组客户端入口，实现 {@link ClientModInitializer}。
 * 注册客户端渲染器、GUI 界面、模型生成和颜色提供器。
 */
public class MMEClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        MenuScreens.register(MMEMenuTypes.GRADE_ANVIL, GradeAnvilScreen::new);
        MenuScreens.register(MMEMenuTypes.GRADE_CRAFTING_TABLE, GradeCraftingTableScreen::new);
        MenuScreens.register(MMEMenuTypes.GRADE_FURNACE, GradeFurnaceScreen::new);
        MenuScreens.register(MMEMenuTypes.SMOKER_GRADE_FURNACE, GradeSmokerScreen::new);
        MenuScreens.register(MMEMenuTypes.BLAST_GRADE_FURNACE, GradeBlastFurnaceScreen::new);
        MenuScreens.register(MMEMenuTypes.MME_ENCHANTMENT, MMEEnchantmentScreen::new);

        EntityRenderers.register(MMEEntityTypes.GHOUL, GhoulRenderer::new);
        EntityRenderers.register(MMEEntityTypes.SHADOW, ShadowRenderer::new);
        EntityRenderers.register(MMEEntityTypes.WIGHT, WightRenderer::new);
        EntityRenderers.register(MMEEntityTypes.INVISIBLE_STALKER, InvisibleStalkerRenderer::new);
        EntityRenderers.register(MMEEntityTypes.DEMON_SPIDER, DemonSpiderRenderer::new);
        EntityRenderers.register(MMEEntityTypes.PHASE_SPIDER, PhaseSpiderRenderer::new);
        EntityRenderers.register(MMEEntityTypes.INFERNAL_CREEPER, InfernalCreeperRenderer::new);
        EntityRenderers.register(MMEEntityTypes.FIRE_ELEMENTAL, FireElementalRenderer::new);
        EntityRenderers.register(MMEEntityTypes.VAMPIRE_BAT, VampireBatRenderer::new);
        EntityRenderers.register(MMEEntityTypes.NIGHTWING, NightwingRenderer::new);
        EntityRenderers.register(MMEEntityTypes.GIANT_VAMPIRE_BAT, GiantVampireBatRenderer::new);
	}
}
