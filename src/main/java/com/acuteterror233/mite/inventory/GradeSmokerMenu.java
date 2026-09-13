package com.acuteterror233.mite.inventory;

import com.acuteterror233.mite.block.MMEMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Grade smoker menu (smoking recipes).
 */
public class GradeSmokerMenu extends AbstractGradeFurnaceMenu {

    public GradeSmokerMenu(int syncId, Inventory playerInventory) {
        super(
                MMEMenuTypes.SMOKER_GRADE_FURNACE,
                RecipeType.SMOKING,
                RecipePropertySet.SMOKER_INPUT,
                RecipeBookType.SMOKER,
                syncId,
                playerInventory
        );
    }

    public GradeSmokerMenu(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(
                MMEMenuTypes.SMOKER_GRADE_FURNACE,
                RecipePropertySet.SMOKER_INPUT,
                RecipeBookType.SMOKER,
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
    }
}
