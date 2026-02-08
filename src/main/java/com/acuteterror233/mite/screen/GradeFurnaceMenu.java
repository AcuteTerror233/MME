package com.acuteterror233.mite.screen;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;

public class GradeFurnaceMenu extends AbstractFurnaceMenu {
    private final ContainerData propertyDelegate;
    private final Container inventory;

    public GradeFurnaceMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(3), new SimpleContainerData(6));
    }

    public GradeFurnaceMenu(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(
                MMEBlocks.GRADE_FURNACE,
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
        FuelGradeRegistry fuelGradeRegistry = ((GetFuelGradeRegistryExtension) this.level).MME$GetFuelGradeRegistry();
        int fuelGrade = 1;
        if (fuelGradeRegistry.hasFuelGrade(inventory.getItem(1))) {
            fuelGrade = fuelGradeRegistry.getFuelGrade(inventory.getItem(1));
        }
        return  fuelGrade <= this.propertyDelegate.get(5);
    }
}
