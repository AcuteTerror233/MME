package com.acuteterror233.mite.mixin.client.gui;

import com.acuteterror233.mite.interfaces.FoodDataExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Mixin for {@code Gui} — 修改游戏界面 HUD 渲染。
 */
@Mixin(Hud.class)
public abstract class GuiMixin {
    @Final
    @Shadow
    private Minecraft minecraft;
    @Unique
    private BlockState blockState = Blocks.NETHER_PORTAL.defaultBlockState();
    @ModifyConstant(method = "extractFood", constant = @Constant(intValue = 10))
    private int renderFood(int original, GuiGraphicsExtractor context, Player player, int top, int right) {
        FoodData foodData = player.getFoodData();
        int maxFoodLevel = ((FoodDataExtension) foodData).MME$GetMaxFoodLevel();
        return maxFoodLevel / 2;
    }
    /**
     * @author AcuteTerror233
     * @reason 修改 PortalOverlay
     */
    @Overwrite
    private void extractPortalOverlay(GuiGraphicsExtractor guiGraphics, float f) {
        if (f < 1.0F) {
            f *= f;
            f *= f;
            f = f * 0.8F + 0.2F;
        }
        int i = ARGB.white(f);
        if (!this.minecraft.level.getBlockState(this.minecraft.player.blockPosition()).isAir()) {
            this.blockState = this.minecraft.level.getBlockState(this.minecraft.player.blockPosition());
        }else if (!this.minecraft.level.getBlockState(this.minecraft.player.blockPosition().above()).isAir()) {
            this.blockState = this.minecraft.level.getBlockState(this.minecraft.player.blockPosition().above());
        }
        TextureAtlasSprite textureAtlasSprite = this.minecraft.getModelManager().getBlockStateModelSet().getParticleMaterial(this.blockState).sprite();
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, textureAtlasSprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), i);
    }
}