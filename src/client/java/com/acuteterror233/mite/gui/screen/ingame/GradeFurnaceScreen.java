package com.acuteterror233.mite.gui.screen.ingame;

import com.acuteterror233.mite.screen.GradeFurnaceScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.recipebook.RecipeBookType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class GradeFurnaceScreen extends AbstractFurnaceScreen<GradeFurnaceScreenHandler> {
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/furnace.png");
    private static final Text TOGGLE_SMELTABLE_TEXT = Text.translatable("gui.recipebook.toggleRecipes.smeltable");
    private static final List<RecipeBookWidget.Tab> TABS = List.of(
            new RecipeBookWidget.Tab(RecipeBookType.FURNACE),
            new RecipeBookWidget.Tab(Items.PORKCHOP, RecipeBookCategories.FURNACE_FOOD),
            new RecipeBookWidget.Tab(Items.STONE, RecipeBookCategories.FURNACE_BLOCKS),
            new RecipeBookWidget.Tab(Items.LAVA_BUCKET, Items.EMERALD, RecipeBookCategories.FURNACE_MISC)
    );
    public GradeFurnaceScreen(GradeFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TOGGLE_SMELTABLE_TEXT, TEXTURE, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE, TABS);
    }
    @Override
    protected void drawMouseoverTooltip(DrawContext drawContext, int x, int y) {
        if (this.focusedSlot instanceof FurnaceFuelSlot && !this.handler.isFuelGradeAvailable()){
            if (this.focusedSlot.hasStack()) {
                ItemStack itemStack = this.focusedSlot.getStack();
                if (this.handler.getCursorStack().isEmpty()) {
                    List<Text> texts = this.getTooltipFromItem(itemStack);
                    texts.add(Text.translatable("mme.gradefurnac.fuelnotavailable"));
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
