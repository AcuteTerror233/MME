package com.acuteterror233.mite.mixin.client.gui.screen.ingame;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.inventory.slot.TimedCraftingResultSlot;
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
    public InventoryScreenMixin(InventoryMenu handler, RecipeBookComponent<?> recipeBook, Inventory inventory, Component title) {
        super(handler, recipeBook, inventory, title);
    }
    /**
     * Draws the timed-crafting progress bar (up to 18 px wide) at the crafting grid's right side
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
