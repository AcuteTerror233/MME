package com.acuteterror233.mite.inventory;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * 抽象等级熔炉菜单，继承 {@link AbstractFurnaceMenu}。
 * 限制燃料槽仅接受对应燃烧等级的燃料。
 * 子类需提供具体的 {@link RecipeType}、{@link RecipePropertySet}、{@link RecipeBookType}。
 */
public abstract class AbstractGradeFurnaceMenu extends AbstractFurnaceMenu {
    private final ContainerData propertyDelegate;
    private final Container inventory;

    protected AbstractGradeFurnaceMenu(
            MenuType<?> menuType,
            RecipeType<? extends AbstractCookingRecipe> recipeType,
            ResourceKey<RecipePropertySet> recipePropertySet,
            RecipeBookType recipeBookType,
            int syncId,
            Inventory playerInventory
    ) {
        this(menuType, recipeType, recipePropertySet, recipeBookType, syncId, playerInventory, new SimpleContainer(3), new SimpleContainerData(6));
    }

    protected AbstractGradeFurnaceMenu(
            MenuType<?> menuType,
            RecipeType<? extends AbstractCookingRecipe> recipeType,
            ResourceKey<RecipePropertySet> recipePropertySet,
            RecipeBookType recipeBookType,
            int syncId,
            Inventory playerInventory,
            Container inventory,
            ContainerData propertyDelegate
    ) {
        super(
                menuType,
                recipeType,
                recipePropertySet,
                recipeBookType,
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
    }

    public boolean isFuelGradeAvailable() {
        FuelGradeRegistry fuelGradeRegistry = ((GetFuelGradeRegistryExtension) this.level).MME$GetFuelGradeRegistry();
        int fuelGrade = 1;
        if (fuelGradeRegistry.hasFuelGrade(inventory.getItem(1))) {
            fuelGrade = fuelGradeRegistry.getFuelGrade(inventory.getItem(1));
        }
        return fuelGrade <= this.propertyDelegate.get(5);
    }
}
