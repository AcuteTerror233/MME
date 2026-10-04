package com.acuteterror233.mite.mixin.client.gui.screen.ingame;

import com.acuteterror233.mite.interfaces.TimedCraftingMenuExtension;
import com.acuteterror233.mite.inventory.slot.TimedCraftingResultSlot;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Mixin for {@code CraftingScreen} — Renders the metal material timed crafting progress bar
 * and the forbidden-crafting tooltip on the vanilla crafting screen.
 * When no metal material is mounted (vanilla path), the vanilla rendering is fully preserved.
 */
@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends AbstractRecipeBookScreen<CraftingMenu> {
    @Unique
    private static final Identifier CRAFTING_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("container/furnace/burn_progress");

    public CraftingScreenMixin(CraftingMenu handler, RecipeBookComponent<?> recipeBook, Inventory inventory, Component title) {
        super(handler, recipeBook, inventory, title);
    }

    /**
     * Draws the timed-crafting progress bar (up to 24 px wide, reusing the furnace burn-progress
     * texture) below the crafting grid after the vanilla background extraction finishes.
     *
     * @param graphics     screen graphics extractor
     * @param mouseX      mouse x position
     * @param mouseY      mouse y position
     * @param a  partial tick time
     */
    @Inject(method = "extractBackground", at = @At("RETURN"))
    private void mme$renderProgress(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        TimedCraftingMenuExtension extension = (TimedCraftingMenuExtension) this.menu;
        if (!extension.MME$HasMetal()) {
            return;
        }
        double v = extension.MME$GetCraftingTime();
        int l = (int) (v * 24);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CRAFTING_PROGRESS_TEXTURE, 24, 16, 0, 0, this.leftPos + 90, this.topPos + 34, l, 16);
    }

    /**
     * Appends the "not allowed to craft" warning while the hovered slot is the timed-crafting
     * result slot, a metal material is mounted, and the menu currently rejects crafting.
     */
    @Override
    protected @NotNull List<Component> getTooltipFromContainerItem(@NonNull ItemStack itemStack) {
        List<Component> list = getTooltipFromItem(this.minecraft, itemStack);
        TimedCraftingMenuExtension extension = (TimedCraftingMenuExtension) this.menu;
        if (this.hoveredSlot instanceof TimedCraftingResultSlot
                && extension.MME$HasMetal()
                && !extension.MME$IsAllowCrafting()) {
            list.add(Component.translatable("mme.craftingTable.noAllowedCrafting"));
        }
        return list;
    }
}
