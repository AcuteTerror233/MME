package com.acuteterror233.mite.inventory;

import com.acuteterror233.mite.block.MMEMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * 常规等级熔炉菜单（烧炼配方）。
 */
public class GradeFurnaceMenu extends AbstractGradeFurnaceMenu {

    public GradeFurnaceMenu(int syncId, Inventory playerInventory) {
        super(
                MMEMenuTypes.GRADE_FURNACE,
                RecipeType.SMELTING,
                RecipePropertySet.FURNACE_INPUT,
                RecipeBookType.FURNACE,
                syncId,
                playerInventory
        );
    }

    public GradeFurnaceMenu(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(
                MMEMenuTypes.GRADE_FURNACE,
                RecipePropertySet.FURNACE_INPUT,
                RecipeBookType.FURNACE,
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
    }
}
