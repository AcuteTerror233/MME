package com.acuteterror233.mite.gui.screen.inventory;

import com.acuteterror233.mite.inventory.GradeBlastFurnaceMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeBookCategories;

import java.util.List;

@Environment(EnvType.CLIENT)
/**
 * 高炉等级熔炉 GUI 界面。
 */
public class GradeBlastFurnaceScreen extends AbstractFurnaceScreen<GradeBlastFurnaceMenu> {
    private static final ResourceLocation LIT_PROGRESS_TEXTURE = ResourceLocation.withDefaultNamespace("container/blast_furnace/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_TEXTURE = ResourceLocation.withDefaultNamespace("container/blast_furnace/burn_progress");
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/blast_furnace.png");
    private static final Component TOGGLE_BLASTABLE_TEXT = Component.translatable("gui.recipebook.toggleRecipes.blastable");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(SearchRecipeBookCategory.BLAST_FURNACE),
            new RecipeBookComponent.TabInfo(Items.STONE, RecipeBookCategories.BLAST_FURNACE_BLOCKS),
            new RecipeBookComponent.TabInfo(Items.LAVA_BUCKET, Items.EMERALD, RecipeBookCategories.BLAST_FURNACE_MISC)
    );

    public GradeBlastFurnaceScreen(GradeBlastFurnaceMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TOGGLE_BLASTABLE_TEXT, TEXTURE, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE, TABS);
    }
    @Override
    protected void renderTooltip(GuiGraphics drawContext, int x, int y) {
        if (this.hoveredSlot instanceof FurnaceFuelSlot && !this.menu.isFuelGradeAvailable()){
            if (this.hoveredSlot.hasItem()) {
                ItemStack itemStack = this.hoveredSlot.getItem();
                if (this.menu.getCarried().isEmpty()) {
                    List<Component> texts = this.getTooltipFromContainerItem(itemStack);
                    texts.add(Component.translatable("mme.gradefurnac.fuelnotavailable"));
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
