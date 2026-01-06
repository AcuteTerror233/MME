package com.acuteterror233.mite.screen;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.registry.tag.MMETags;
import com.acuteterror233.mite.screen.slot.CraftingTableResultSlot;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GradeCraftingTableMenu extends AbstractGradeCraftingMenu {
    private final ContainerLevelAccess context;
    private final Player player;
    private boolean filling;
    public GradeCraftingTableMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL, null, null);
    }
    public GradeCraftingTableMenu(int syncId, Inventory playerInventory, ContainerLevelAccess context, Block[] upperLevelCraftingTable, TagKey<Item> disableMaterialsTag) {
        super(AtBlocks.GRADE_CRAFTING_TABLE, syncId, upperLevelCraftingTable, disableMaterialsTag, 3, 3);
        this.context = context;
        this.player = playerInventory.player;
        this.addResultSlot(this.player, 124, 35);
        this.addCraftingGridSlots(30, 17);
        this.addStandardInventorySlots(playerInventory, 8, 84);
    }
    @Override
    public void slotsChanged(Container inventory) {
        if (!this.filling) {
            this.context.execute((world, pos) -> {
                if (world instanceof ServerLevel serverWorld) {
                    updateResult(this, serverWorld, this.player, this.craftSlots, this.resultSlots, null);
                }
            });
        }
    }

    @Override
    public void beginPlacingRecipe() {
        this.filling = true;
    }

    @Override
    public void finishPlacingRecipe(ServerLevel world, RecipeHolder<CraftingRecipe> recipe) {
        this.filling = false;
        updateResult(this, world, this.player, this.craftSlots, this.resultSlots, recipe);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.context.execute((world, pos) -> this.clearContainer(player, this.craftSlots));
    }
    @Override
    public boolean stillValid(Player player) {
        return this.context.evaluate((world, pos) -> world.getBlockState(pos).is(MMETags.CRAFTING_TABLE) && player.canInteractWithBlock(pos, 4.0) && world.getBlockState(pos.above()).isAir(), true);
    }
    @Override
    protected @NotNull Player owner() {
        return this.player;
    }
    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasItem()) {
            if (slot2 instanceof CraftingTableResultSlot craftingResultSlot){
                craftingResultSlot.remove(0);
                return ItemStack.EMPTY;
            }
            ItemStack itemStack2 = slot2.getItem();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                itemStack2.getItem().onCraftedBy(itemStack2, player);
                if (!this.moveItemStackTo(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickCraft(itemStack2, itemStack);
            } else if (slot >= 10 && slot < 46) {
                if (!this.moveItemStackTo(itemStack2, 1, 10, false)) {
                    if (slot < 37) {
                        if (!this.moveItemStackTo(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setByPlayer(ItemStack.EMPTY);
            } else {
                slot2.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTake(player, itemStack2);
            if (slot == 0) {
                player.drop(itemStack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public @NotNull Slot getResultSlot() {
        return this.slots.getFirst();
    }

    @Override
    public @NotNull List<Slot> getInputGridSlots() {
        return this.slots.subList(1, 10);
    }

    @Override
    public @NotNull RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }
}
