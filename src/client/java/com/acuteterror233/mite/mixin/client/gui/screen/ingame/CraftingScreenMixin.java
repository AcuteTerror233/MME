package com.acuteterror233.mite.mixin.client.gui.screen.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends RecipeBookScreen<CraftingScreenHandler> {
    public CraftingScreenMixin(CraftingScreenHandler handler, RecipeBookWidget<?> recipeBook, PlayerInventory inventory, Text title) {
        super(handler, recipeBook, inventory, title);
    }
    @Override
    protected void drawMouseoverTooltip(DrawContext drawContext, int x, int y) {
        if (this.focusedSlot instanceof CraftingResultSlot){
            if (this.focusedSlot.hasStack()) {
                ItemStack itemStack = this.focusedSlot.getStack();
                if (this.handler.getCursorStack().isEmpty()) {
                    List<Text> texts = this.getTooltipFromItem(itemStack);
                    texts.add(Text.translatable("mme.craftingTable.noAllowedCrafting"));
                    drawContext.drawTooltip(
                            this.textRenderer, texts, itemStack.getTooltipData(), x, y, itemStack.get(DataComponentTypes.TOOLTIP_STYLE)
                    );
                }
            }
        }else {
            super.drawMouseoverTooltip(drawContext, x, y);
        }
    }
}
