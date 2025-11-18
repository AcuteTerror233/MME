package com.acuteterror233.mite.gui.screen.ingame;

import com.acuteterror233.mite.screen.CraftingTableScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractCraftingRecipeBookWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CraftingTableScreen extends RecipeBookScreen<CraftingTableScreenHandler> {
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/crafting_table.png");
    private static final Identifier CRAFTING_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    public CraftingTableScreen(CraftingTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new AbstractCraftingRecipeBookWidget(handler), inventory, title);
    }
    @Override
    protected void init() {
        super.init();
        this.titleX = 29;
    }

    @Override
    protected ScreenPos getRecipeBookButtonPos() {
        return new ScreenPos(this.x + 5, this.height / 2 - 49);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;
        double v = this.handler.getCraftingTime();
        int l = (int) (v * 24);
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, j, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
        context.drawGuiTexture(RenderLayer::getGuiTextured, CRAFTING_PROGRESS_TEXTURE, 24, 16, 0, 0, x + 90, y + 34, l, 16);
    }
}
