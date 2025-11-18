package com.acuteterror233.mite.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;

public class NewCraftingResultSlot extends CraftingResultSlot {
    boolean isCrafting = false;
    public NewCraftingResultSlot(PlayerEntity player, RecipeInputInventory input, Inventory inventory, int index, int x, int y) {
        super(player, input, inventory, index, x, y);
    }
    @Override
    public ItemStack takeStack(int amount) {
        if (!getStack().isEmpty()) {
            this.isCrafting = true;
        }
        return ItemStack.EMPTY;
    }
    public boolean isCrafting() {
        return this.isCrafting;
    }
    public void ClearCraftingState() {
        this.isCrafting = false;
    }
}
