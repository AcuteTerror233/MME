package com.acuteterror233.mite.inventory.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Player crafting result slot.
 * Extends {@link ResultSlot}, handles the result of the 2×2 crafting grid in the player inventory.
 */
public class PlayerCraftingResultSlot extends ResultSlot {
    boolean isCrafting = false;
    private final InventoryMenu handler;

    public PlayerCraftingResultSlot(Player player, CraftingContainer input, Container inventory, InventoryMenu ScreenHandler, int index, int x, int y) {
        super(player, input, inventory, index, x, y);
        this.handler = ScreenHandler;
    }

    @Override
    public @NotNull ItemStack remove(int amount) {
        this.isCrafting = !getItem().isEmpty() && this.handler.MME$IsAllowCrafting();
        return ItemStack.EMPTY;
    }

    public boolean isCrafting() {
        return this.isCrafting;
    }

    public void ClearCraftingState() {
        this.isCrafting = false;
    }

    public void onQuickCraft(ItemStack newItem, ItemStack original) {
        this.remove(0);
    }
}
