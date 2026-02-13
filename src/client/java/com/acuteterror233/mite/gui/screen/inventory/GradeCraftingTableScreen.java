package com.acuteterror233.mite.gui.screen.inventory;

import com.acuteterror233.mite.inventory.GradeCraftingTableMenu;
import com.acuteterror233.mite.inventory.slot.CraftingTableResultSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class GradeCraftingTableScreen extends AbstractRecipeBookScreen<GradeCraftingTableMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/crafting_table.png");
    private static final ResourceLocation CRAFTING_PROGRESS_TEXTURE = ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");
    public GradeCraftingTableScreen(GradeCraftingTableMenu handler, Inventory inventory, Component title) {
        super(handler, new CraftingRecipeBookComponent(handler), inventory, title);
    }
    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 29;
    }

    @Override
    protected @NotNull ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 5, this.height / 2 - 49);
    }

    @Override
    protected void renderBg(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        double v = this.menu.getCraftingTime();
        int l = (int) (v * 24);
        int j = (this.height - this.imageHeight) / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, CRAFTING_PROGRESS_TEXTURE, 24, 16, 0, 0, x + 90, y + 34, l, 16);
    }
    @Override
    protected void renderTooltip(GuiGraphics drawContext, int x, int y) {
        if (this.hoveredSlot instanceof CraftingTableResultSlot && !this.menu.isAllowCrafting()){
            if (this.hoveredSlot.hasItem()) {
                ItemStack itemStack = this.hoveredSlot.getItem();
                if (this.menu.getCarried().isEmpty()) {
                    List<Component> texts = this.getTooltipFromContainerItem(itemStack);
                    texts.add(Component.translatable("mme.craftingTable.noAllowedCrafting"));
                    drawContext.setTooltipForNextFrame(
                            this.font, texts, itemStack.getTooltipImage(), x, y, itemStack.get(DataComponents.TOOLTIP_STYLE)
                    );
                }
            }
        }else {
            super.renderTooltip(drawContext, x, y);
        }
    }
}
