package com.acuteterror233.mite.gui.screen.inventory;

import com.acuteterror233.mite.inventory.GradeCraftingTableMenu;
import com.acuteterror233.mite.inventory.slot.CraftingTableResultSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * 等级工作台 GUI 界面。
 * 渲染等级工作台的客户端界面。
 */
@Environment(EnvType.CLIENT)
public class GradeCraftingTableScreen extends AbstractRecipeBookScreen<GradeCraftingTableMenu> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/crafting_table.png");
    private static final Identifier CRAFTING_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("container/furnace/burn_progress");
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
    public void extractBackground(@NonNull GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
        super.extractBackground(context, mouseX, mouseY, deltaTicks);
        int x = this.leftPos;
        int y = this.topPos;
        double v = this.menu.getCraftingTime();
        int l = (int) (v * 24);
        int j = (this.height - this.imageHeight) / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, CRAFTING_PROGRESS_TEXTURE, 24, 16, 0, 0, x + 90, y + 34, l, 16);
    }

    @Override
    protected @NotNull List<Component> getTooltipFromContainerItem(@NonNull ItemStack itemStack) {
        List<Component> list = getTooltipFromItem(this.minecraft, itemStack);
        if (this.hoveredSlot instanceof CraftingTableResultSlot && !this.menu.isAllowCrafting()){
            list.add(Component.translatable("mme.craftingTable.noAllowedCrafting"));
        }
        return list;
    }
}
