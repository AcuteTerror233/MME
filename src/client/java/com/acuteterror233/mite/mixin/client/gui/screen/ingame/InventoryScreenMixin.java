package com.acuteterror233.mite.mixin.client.gui.screen.ingame;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.atinterface.InventoryMenuExtension;
import com.acuteterror233.mite.screen.slot.PlayerCraftingResultSlot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractRecipeBookScreen<InventoryMenu> {
    @Unique
    private static final ResourceLocation CRAFTING_PROGRESS_TEXTURE = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "container/inventory/inventory_progress");
    public InventoryScreenMixin(InventoryMenu handler, RecipeBookComponent<?> recipeBook, Inventory inventory, Component title) {
        super(handler, recipeBook, inventory, title);
    }
    @Inject(method = "renderBg", at = @At("RETURN"))
    protected void renderBg(GuiGraphics context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
        int x = this.leftPos;
        int y = this.topPos;
        double v = ((InventoryMenuExtension)this.menu).MME$GetCraftingTime();
        int l = (int) (v * 18);
        context.blitSprite(RenderType::guiTextured, CRAFTING_PROGRESS_TEXTURE, 18, 15, 0, 0, x + 135, y + 28, l, 15);
    }
    @Override
    protected void renderTooltip(GuiGraphics drawContext, int x, int y) {
        if (this.hoveredSlot instanceof PlayerCraftingResultSlot && !((InventoryMenuExtension)this.menu).MME$IsAllowCrafting()){
            if (this.hoveredSlot.hasItem()) {
                ItemStack itemStack = this.hoveredSlot.getItem();
                if (this.menu.getCarried().isEmpty()) {
                    List<Component> texts = this.getTooltipFromContainerItem(itemStack);
                    texts.add(Component.translatable("mme.craftingTable.noAllowedCrafting"));
                    drawContext.renderTooltip(
                            this.font, texts, itemStack.getTooltipImage(), x, y, itemStack.get(DataComponents.TOOLTIP_STYLE)
                    );
                }
            }
        }else {
            super.renderTooltip(drawContext, x, y);
        }
    }
}
