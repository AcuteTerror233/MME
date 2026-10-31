package com.acuteterror233.mite.mixin.client.gui;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.interfaces.FoodDataExtension;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Client-side mixin into {@code Hud} (in-game HUD rendering).
 * <p>
 * Three changes:
 * <ul>
 *   <li>the food row icon count is derived from MME's extended max food level instead of the
 *       vanilla constant;</li>
 *   <li>the protein and fiber stores are drawn as two rows of ten icons flanking the hotbar,
 *       which replaces the old bottom-anchored bars inside the inventory screen;</li>
 *   <li>the portal overlay is replaced by a full-screen tint using the particle texture of the
 *       block the player is standing in (falling back to the block above, then to the last
 *       known non-air block).</li>
 * </ul>
 */
@Mixin(Hud.class)
public abstract class GuiMixin {
    /** Vanilla hotbar width in pixels: nine 20 px slots plus the 2 px border. */
    @Unique
    private static final int HOTBAR_WIDTH = 182;
    /** Gap in pixels between the bottom of a nutrition row and the bottom of the window. */
    @Unique
    private static final int NUTRITION_BOTTOM_MARGIN = 5;
    /** Distance in pixels between a hotbar edge and the nearest nutrition icon. */
    @Unique
    private static final int NUTRITION_GAP = 20;
    /** Icons per nutrition row; ten icons span the full 160000 store. */
    @Unique
    private static final int NUTRITION_ICONS = 10;
    /**
     * Nutrient amount covered by a single icon. Mirrors {@code FoodDataMixin#addProtein} and
     * {@code FoodDataMixin#addFiber}, which both cap the store at 160000.
     */
    @Unique
    private static final float NUTRITION_PER_ICON = 16000.0F;
    /**
     * Horizontal distance between two icon origins. Eight pixels for a nine pixel icon matches
     * the vanilla food row, which overlaps each icon by one pixel so the row reads as one strip.
     */
    @Unique
    private static final int NUTRITION_ICON_SPACING = 8;
    /** Rendered icon edge length in pixels, drawn at the source texture's native size. */
    @Unique
    private static final int NUTRITION_ICON_SIZE = 9;
    /** Edge length of the source textures. */
    @Unique
    private static final int NUTRITION_TEXTURE_SIZE = 9;
    /** Empty protein frame, drawn under every slot. */
    @Unique
    private static final Identifier PROTEIN_EMPTY_SPRITE =
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/hud/worm_air.png");
    /** Full protein icon: a cooked worm. */
    @Unique
    private static final Identifier PROTEIN_FULL_SPRITE =
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/hud/worm_cooked.png");
    /** Half protein icon: a raw worm. */
    @Unique
    private static final Identifier PROTEIN_HALF_SPRITE =
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/hud/worm_raw.png");
    /** Empty fiber frame, drawn under every slot. */
    @Unique
    private static final Identifier FIBER_EMPTY_SPRITE =
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/hud/sweet_berries_air.png");
    /** Full fiber icon: a whole cluster of sweet berries. */
    @Unique
    private static final Identifier FIBER_FULL_SPRITE =
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/hud/sweet_berries.png");
    /** Half fiber icon: a single berry. */
    @Unique
    private static final Identifier FIBER_HALF_SPRITE =
            Identifier.fromNamespaceAndPath(MME.MOD_ID, "textures/hud/sweet_berries_half.png");

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
     * @param top   top screen coordinate of the food row
     * @param right right screen coordinate of the food row, i.e. the hotbar's right edge
     * @return the number of food icons to render
     */
    @ModifyConstant(method = "extractFood", constant = @Constant(intValue = 10))
    private int renderFood(int original, GuiGraphicsExtractor context, Player player, int top, int right) {
        FoodData foodData = player.getFoodData();
        int maxFoodLevel = foodData.MME$GetMaxFoodLevel();
        return maxFoodLevel / 2;
    }

    /**
     * Draws the protein and fiber rows once the vanilla food row has been extracted.
     *
     * @param graphics   HUD graphics extractor
     * @param player     the player whose stores are rendered
     * @param top        top screen coordinate of the vanilla food row; unused, because the
     *                   nutrition rows line up with the hotbar at the bottom of the window
     * @param right      right screen coordinate of the food row, i.e. the hotbar's right edge
     * @param ci         callback info
     */
    @Inject(method = "extractFood", at = @At("TAIL"))
    private void mme$extractNutrition(GuiGraphicsExtractor graphics, Player player, int top, int right, CallbackInfo ci) {
        // Anchored to the bottom of the window instead of hanging off the food row
        int y = graphics.guiHeight() - NUTRITION_BOTTOM_MARGIN - NUTRITION_ICON_SIZE;
        FoodNutrition nutrition = ((FoodDataExtension) player.getFoodData()).MME$GetFoodNutrition();
        // Protein sits 20 px left of the hotbar and is filled from that edge leftwards, so a
        // draining store empties the row from its left end.
        this.mme$extractNutritionRow(graphics, nutrition.protein(),
                right - HOTBAR_WIDTH - NUTRITION_GAP - NUTRITION_ICON_SIZE, y, -NUTRITION_ICON_SPACING,
                PROTEIN_EMPTY_SPRITE, PROTEIN_FULL_SPRITE, PROTEIN_HALF_SPRITE);
        // Fiber sits 20 px right of the hotbar and is filled from that edge rightwards, so a
        // draining store empties the row from its right end.
        this.mme$extractNutritionRow(graphics, nutrition.fiber(),
                right + NUTRITION_GAP, y, NUTRITION_ICON_SPACING,
                FIBER_EMPTY_SPRITE, FIBER_FULL_SPRITE, FIBER_HALF_SPRITE);
    }

    /**
     * Draws one nutrition row.
     *
     * <p>Every slot draws the empty frame first, then the matching icon on top, exactly like the
     * vanilla food row. Icon {@code i} covers
     * {@code [i * NUTRITION_PER_ICON, (i + 1) * NUTRITION_PER_ICON)}: it is drawn full once the
     * value reaches that upper bound and half once the value is past its midpoint, mirroring the
     * {@code i * 2 + 1} test of the vanilla food row.</p>
     *
     * @param graphics HUD graphics extractor
     * @param value    stored nutrient amount
     * @param startX   x coordinate of icon 0
     * @param top      top screen coordinate of the row
     * @param step     signed distance between two icon origins
     * @param empty    frame for an unfilled slot
     * @param full     icon for a filled slot
     * @param half     icon for a half-filled slot
     */
    @Unique
    private void mme$extractNutritionRow(GuiGraphicsExtractor graphics, float value, int startX, int top,
                                         int step, Identifier empty, Identifier full, Identifier half) {
        for (int i = 0; i < NUTRITION_ICONS; i++) {
            int x = startX + i * step;
            this.mme$blitIcon(graphics, empty, x, top);
            float filled = (i + 1) * NUTRITION_PER_ICON;
            float halfFilled = i * NUTRITION_PER_ICON + NUTRITION_PER_ICON / 2.0F;
            Identifier overlay = value >= filled ? full : value >= halfFilled ? half : null;
            if (overlay != null) {
                this.mme$blitIcon(graphics, overlay, x, top);
            }
        }
    }

    /**
     * Blits one 9x9 nutrition icon at its native size.
     *
     * @param graphics HUD graphics extractor
     * @param sprite   texture to draw
     * @param x        left screen coordinate
     * @param y        top screen coordinate
     */
    @Unique
    private void mme$blitIcon(GuiGraphicsExtractor graphics, Identifier sprite, int x, int y) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, sprite, x, y, 0.0F, 0.0F,
                NUTRITION_ICON_SIZE, NUTRITION_ICON_SIZE,
                NUTRITION_TEXTURE_SIZE, NUTRITION_TEXTURE_SIZE);
    }

    /**
     * @author AcuteTerror233
     * @reason replaces the vanilla portal overlay with a full-screen tint of the particle texture
     * of the block the player occupies (falling back to the block above, then the last non-air
     * block), so entering any portal block dims the screen like Nether portal travel.
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