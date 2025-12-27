package com.acuteterror233.mite.mixin.client.gui.screen.ingame;

import com.acuteterror233.mite.atinterface.PlayerScreenHandlerExtension;
import com.acuteterror233.mite.screen.slot.PlayerCraftingResultSlot;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends RecipeBookScreen<PlayerScreenHandler> {
    @Unique
    private static final Identifier CRAFTING_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    public InventoryScreenMixin(PlayerScreenHandler handler, RecipeBookWidget<?> recipeBook, PlayerInventory inventory, Text title) {
        super(handler, recipeBook, inventory, title);
    }
    @Inject(method = "drawBackground", at = @At("RETURN"))
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
        int x = this.x;
        int y = this.y;
        double v = ((PlayerScreenHandlerExtension)this.handler).getCraftingTime();
        int l = (int) (v * 24);
        context.drawGuiTexture(RenderLayer::getGuiTextured, CRAFTING_PROGRESS_TEXTURE, 24, 16, 0, 0, x + 135, y + 28, l, 16);
    }
    @Override
    protected void drawMouseoverTooltip(DrawContext drawContext, int x, int y) {
        if (this.focusedSlot instanceof PlayerCraftingResultSlot && !((PlayerScreenHandlerExtension)this.handler).isAllowCrafting()){
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
