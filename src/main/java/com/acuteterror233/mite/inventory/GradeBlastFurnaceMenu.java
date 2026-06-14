package com.acuteterror233.mite.inventory;

import com.acuteterror233.mite.block.MMEMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * 高炉等级熔炉菜单（爆破配方）。
 */
public class GradeBlastFurnaceMenu extends AbstractGradeFurnaceMenu {

    public GradeBlastFurnaceMenu(int syncId, Inventory playerInventory) {
        super(
                MMEMenuTypes.BLAST_GRADE_FURNACE,
                RecipeType.BLASTING,
                RecipePropertySet.BLAST_FURNACE_INPUT,
                RecipeBookType.BLAST_FURNACE,
                syncId,
                playerInventory
        );
    }

    public GradeBlastFurnaceMenu(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(
                MMEMenuTypes.BLAST_GRADE_FURNACE,
                RecipeType.BLASTING,
                RecipePropertySet.BLAST_FURNACE_INPUT,
                RecipeBookType.BLAST_FURNACE,
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
    }
}
