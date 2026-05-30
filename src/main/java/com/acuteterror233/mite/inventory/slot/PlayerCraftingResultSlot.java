package com.acuteterror233.mite.inventory.slot;

import com.acuteterror233.mite.atinterface.InventoryMenuExtension;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * 玩家合成结果栏位。
 * 继承 {@link ResultSlot}，处理玩家物品栏 2×2 合成格的结果。
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
        this.isCrafting = !getItem().isEmpty() && ((InventoryMenuExtension)this.handler).MME$IsAllowCrafting();
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
