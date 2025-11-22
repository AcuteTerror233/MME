package com.acuteterror233.mite.screen;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.screen.slot.NewCraftingResultSlot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;

import java.util.List;
import java.util.function.Function;

public class CraftingTableScreenHandler extends AbstractLevelCraftingScreenHandler{
    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private final Block block;
    private boolean filling;
    public CraftingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, null, null);
    }
    public CraftingTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, Function<RecipeInputInventory, Boolean> testRules, Block block) {
        super(AtBlocks.CRAFTINGTABLESCREENHANDLER, syncId, testRules, 3, 3);
        this.context = context;
        this.player = playerInventory.player;
        this.block = block;
        this.addResultSlot(this.player, 124, 35);
        this.addInputSlots(30, 17);
        this.addPlayerSlots(playerInventory, 8, 84);
    }
    @Override
    public void onContentChanged(Inventory inventory) {
        if (!this.filling) {
            this.context.run((world, pos) -> {
                if (world instanceof ServerWorld serverWorld) {
                    updateResult(this, serverWorld, this.player, this.craftingInventory, this.craftingResultInventory, null);
                }
            });
        }
    }

    @Override
    public void onInputSlotFillStart() {
        this.filling = true;
    }

    @Override
    public void onInputSlotFillFinish(ServerWorld world, RecipeEntry<CraftingRecipe> recipe) {
        this.filling = false;
        updateResult(this, world, this.player, this.craftingInventory, this.craftingResultInventory, recipe);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.craftingInventory));
    }
    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, this.block);
    }
    @Override
    protected PlayerEntity getPlayer() {
        return this.player;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            if (slot2 instanceof NewCraftingResultSlot craftingResultSlot){
                craftingResultSlot.takeStack(0);
                return ItemStack.EMPTY;
            }
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                itemStack2.getItem().onCraftByPlayer(itemStack2, player);
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot >= 10 && slot < 46) {
                if (!this.insertItem(itemStack2, 1, 10, false)) {
                    if (slot < 37) {
                        if (!this.insertItem(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
            if (slot == 0) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.craftingResultInventory && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public Slot getOutputSlot() {
        return this.slots.getFirst();
    }

    @Override
    public List<Slot> getInputSlots() {
        return this.slots.subList(1, 10);
    }

    @Override
    public RecipeBookType getCategory() {
        return RecipeBookType.CRAFTING;
    }
}
