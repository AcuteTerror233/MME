package com.acuteterror233.mite.screen;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.RecipePropertySet;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class GradeFurnaceScreenHandler extends AbstractFurnaceScreenHandler {
    private final PropertyDelegate propertyDelegate;
    private final Inventory inventory;

    public GradeFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(6));
    }

    public GradeFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(
                AtBlocks.GRADE_FURNACE,
                RecipeType.SMELTING,
                RecipePropertySet.FURNACE_INPUT,
                RecipeBookType.FURNACE,
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
    }
    public boolean isFuelGradeAvailable() {
        FuelGradeRegistry fuelGradeRegistry = ((GetFuelGradeRegistryExtension) this.world).getFuelGradeRegistry();
        int fuelGrade = 1;
        if (fuelGradeRegistry.hasFuelGrade(inventory.getStack(1))) {
            fuelGrade = fuelGradeRegistry.getFuelGrade(inventory.getStack(1));
        }
        return  fuelGrade <= this.propertyDelegate.get(5);
    }
}
