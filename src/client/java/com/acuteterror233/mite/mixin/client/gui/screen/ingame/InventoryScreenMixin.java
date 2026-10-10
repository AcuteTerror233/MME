package com.acuteterror233.mite.mixin.client.gui.screen.ingame;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.interfaces.FoodDataExtension;
import com.acuteterror233.mite.inventory.slot.TimedCraftingResultSlot;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Client-side mixin into {@code InventoryScreen}: draws the timed-crafting progress bar of the
 * crafting-table rework onto the vanilla inventory background and appends a "cannot craft"
 * tooltip line when the hovered result slot is currently rejected.
 */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractRecipeBookScreen<InventoryMenu> {
    @Unique
    private static final Identifier CRAFTING_PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(MME.MOD_ID, "container/inventory/inventory_progress");
    /** Left edge of the protein bar, relative to the screen's left position (gap between the player model and the crafting grid). */
    @Unique
    private static final int PROTEIN_BAR_X = 78;
    /** Left edge of the fiber bar, relative to the screen's left position. */
    @Unique
    private static final int FIBER_BAR_X = 88;
    /** Top edge of both nutrition bars, aligned with the crafting grid rows. */
    @Unique
    private static final int NUTRITION_BAR_Y = 18;
    /** Width of one nutrition bar in pixels. */
    @Unique
    private static final int NUTRITION_BAR_WIDTH = 8;
    /** Height of one nutrition bar in pixels. */
    @Unique
    private static final int NUTRITION_BAR_HEIGHT = 36;
    /** Max stored protein/fiber; mirrors the caps in FoodDataMixin (addProtein/addFiber). */
    @Unique
    private static final float NUTRITION_BAR_MAX = 160000.0F;
    /** Background track color of the nutrition bars. */
    @Unique
    private static final int NUTRITION_TRACK_COLOR = 0xFF1E1E22;
    /** Fill color of the protein bar (meat brown-red). */
    @Unique
    private static final int PROTEIN_FILL_COLOR = 0xFFB85C3B;
    /** Fill color of the fiber bar (plant green). */
    @Unique
    private static final int FIBER_FILL_COLOR = 0xFF6E9E4F;
    public InventoryScreenMixin(InventoryMenu handler, RecipeBookComponent<?> recipeBook, Inventory inventory, Component title) {
        super(handler, recipeBook, inventory, title);
    }
    /**
     * Draws the timed-crafting progress bar (up to 18 px wide) at the crafting grid's right side
     * and the protein/fiber nutrition bars between the player model and the crafting grid,
     * after the vanilla background extraction finishes.
     *
     * @param context     screen graphics extractor
     * @param mouseX      mouse x position
     * @param mouseY      mouse y position
     * @param deltaTicks  partial tick time
     */
    @Inject(method = "extractBackground", at = @At("RETURN"))
    protected void renderBg(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        int x = this.leftPos;
        int y = this.topPos;
        double v = this.menu.MME$GetCraftingTime();
        int l = (int) (v * 18);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, CRAFTING_PROGRESS_TEXTURE, 18, 15, 0, 0, x + 135, y + 28, l, 15);
        extractNutritionBars(context);
    }

    /**
     * Draws the protein and fiber storage bars (bottom-anchored vertical fills) in the empty
     * gap between the player model and the 2x2 crafting grid. Values come from the
     * server-synced client-side food data.
     */
    @Unique
    private void extractNutritionBars(GuiGraphicsExtractor context) {
        if (this.minecraft == null || this.minecraft.player == null) {
            return;
        }
        FoodNutrition nutrition = ((FoodDataExtension) this.minecraft.player.getFoodData()).MME$GetFoodNutrition();
        drawNutritionBar(context, PROTEIN_BAR_X, nutrition.protein(), PROTEIN_FILL_COLOR);
        drawNutritionBar(context, FIBER_BAR_X, nutrition.fiber(), FIBER_FILL_COLOR);
    }

    /** Draws one bottom-anchored nutrition bar at the given relative x. */
    @Unique
    private void drawNutritionBar(GuiGraphicsExtractor context, int barX, float value, int fillColor) {
        int x = this.leftPos + barX;
        int y = this.topPos + NUTRITION_BAR_Y;
        int bottom = y + NUTRITION_BAR_HEIGHT;
        context.fill(x, y, x + NUTRITION_BAR_WIDTH, bottom, NUTRITION_TRACK_COLOR);
        float fraction = Math.clamp(value / NUTRITION_BAR_MAX, 0.0F, 1.0F);
        int fillHeight = Math.round(fraction * (NUTRITION_BAR_HEIGHT - 2));
        if (fillHeight > 0) {
            context.fill(x + 1, bottom - 1 - fillHeight, x + NUTRITION_BAR_WIDTH - 1, bottom - 1, fillColor);
        }
    }

    /**
     * Appends the "not allowed to craft" warning while the hovered slot is the timed-crafting
     * result slot and the menu currently rejects crafting.
     */
    @Override
    protected @NotNull List<Component> getTooltipFromContainerItem(ItemStack itemStack) {
        List<Component> list = getTooltipFromItem(this.minecraft, itemStack);
        if (this.hoveredSlot instanceof TimedCraftingResultSlot && !this.menu.MME$IsAllowCrafting()){
            list.add(Component.translatable("mme.craftingTable.noAllowedCrafting"));
        }
        return list;
    }
}
