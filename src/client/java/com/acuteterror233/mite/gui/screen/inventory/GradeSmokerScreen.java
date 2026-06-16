package com.acuteterror233.mite.gui.screen.inventory;

import com.acuteterror233.mite.inventory.GradeSmokerMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
/**
 * 烟熏炉等级熔炉 GUI 界面。
 */
public class GradeSmokerScreen extends AbstractFurnaceScreen<GradeSmokerMenu> {
    private static final ResourceLocation LIT_PROGRESS_TEXTURE = ResourceLocation.withDefaultNamespace("container/smoker/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_TEXTURE = ResourceLocation.withDefaultNamespace("container/smoker/burn_progress");
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/smoker.png");
    private static final Component TOGGLE_SMOKABLE_TEXT = Component.translatable("gui.recipebook.toggleRecipes.smokable");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(new RecipeBookComponent.TabInfo(SearchRecipeBookCategory.SMOKER), new RecipeBookComponent.TabInfo(Items.PORKCHOP, RecipeBookCategories.SMOKER_FOOD));

    public GradeSmokerScreen(GradeSmokerMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TOGGLE_SMOKABLE_TEXT, TEXTURE, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE, TABS);
    }

    @Override
    protected @NotNull List<Component> getTooltipFromContainerItem(ItemStack itemStack) {
        List<Component> list = getTooltipFromItem(this.minecraft, itemStack);
        if (this.hoveredSlot instanceof FurnaceFuelSlot && !this.menu.isFuelGradeAvailable()){
            list.add(Component.translatable("mme.gradefurnac.fuelnotavailable"));
        }
        return list;
    }
}
