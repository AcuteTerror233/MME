package com.acuteterror233.mite.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.ScreenHandlerContext;

public class CraftingTableScreenHandler extends AbstractCraftingTableScreenHandler {
    public CraftingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }
    public CraftingTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, context);
    }
    @Override
    public boolean CraftingCheck(RecipeInputInventory inventory) {
        return true;
    }
}
