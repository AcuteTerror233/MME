package com.acuteterror233.mite.screen.slot;

import com.acuteterror233.mite.screen.AbstractLevelCraftingScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;

public class NewCraftingResultSlot extends CraftingResultSlot {
    boolean isCrafting = false;
    private final AbstractLevelCraftingScreenHandler handler;
    public NewCraftingResultSlot(PlayerEntity player, RecipeInputInventory input, Inventory inventory, AbstractLevelCraftingScreenHandler ScreenHandler, int index, int x, int y) {
        super(player, input, inventory, index, x, y);
        this.handler = ScreenHandler;
    }
    @Override
    public ItemStack takeStack(int amount) {
        this.isCrafting = !getStack().isEmpty() && this.handler.isAllowCrafting();
        return ItemStack.EMPTY;
    }
    public boolean isCrafting() {
        return this.isCrafting;
    }
    public void ClearCraftingState() {
        this.isCrafting = false;
    }
    public void onQuickTransfer(ItemStack newItem, ItemStack original) {
        this.takeStack(0);
    }
}
