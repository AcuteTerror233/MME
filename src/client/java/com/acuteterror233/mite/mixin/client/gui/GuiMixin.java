package com.acuteterror233.mite.mixin.client.gui;

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
 * Client-side mixin into {@code Hud} (in-game HUD rendering).
 * <p>
 * Two changes: the food row icon count is derived from MME's extended max food level instead of
 * the vanilla constant, and the portal overlay is replaced by a full-screen tint using the
 * particle texture of the block the player is standing in (falling back to the block above, then
 * to the last known non-air block).
 */
@Mixin(Hud.class)
public abstract class GuiMixin {
    @Final
    @Shadow
    private Minecraft minecraft;
    @Unique
    private BlockState blockState = Blocks.NETHER_PORTAL.defaultBlockState();
    /**
     * {@code @ModifyConstant} hook for {@code extractFood}: replaces the vanilla food-row icon
     * count with {@code maxFoodLevel / 2} (two food points per icon) so the HUD scales with
     * MME's extended max food level.
     *
     * @param original the vanilla constant (10 icons)
     * @param context  HUD graphics extractor
     * @param player   the player whose food level is rendered
     * @param top      top screen coordinate of the food row
     * @param right    right screen coordinate of the food row
     * @return the number of food icons to render
     */
    @ModifyConstant(method = "extractFood", constant = @Constant(intValue = 10))
    private int renderFood(int original, GuiGraphicsExtractor context, Player player, int top, int right) {
        FoodData foodData = player.getFoodData();
        int maxFoodLevel = foodData.MME$GetMaxFoodLevel();
        return maxFoodLevel / 2;
    }
    /**
     * @author AcuteTerror233
     * @reason Modifies PortalOverlay
     */
    @Overwrite
    private void extractPortalOverlay(GuiGraphicsExtractor guiGraphics, float f) {
        if (f < 1.0F) {
            // Ease the overlay strength below full opacity (two smoothsteps plus a floor).
            f *= f;
            f *= f;
            f = f * 0.8F + 0.2F;
        }
        int i = ARGB.white(f);
        // Resolve the occluding block at the player's feet; when both the block and the one above
        // are air, keep the last known block state.
        if (!this.minecraft.level.getBlockState(this.minecraft.player.blockPosition()).isAir()) {
            this.blockState = this.minecraft.level.getBlockState(this.minecraft.player.blockPosition());
        }else if (!this.minecraft.level.getBlockState(this.minecraft.player.blockPosition().above()).isAir()) {
            this.blockState = this.minecraft.level.getBlockState(this.minecraft.player.blockPosition().above());
        }
        TextureAtlasSprite textureAtlasSprite = this.minecraft.getModelManager().getBlockStateModelSet().getParticleMaterial(this.blockState).sprite();
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, textureAtlasSprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), i);
    }
}