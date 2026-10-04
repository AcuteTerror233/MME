package com.acuteterror233.mite.inventory.slot;

import com.acuteterror233.mite.inventory.TimedCraftingSession;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Timed crafting result slot.
 * When the metal material is enabled, clicking the result slot starts the timed synthesis (the item is auto-collected upon completion) instead of taking it directly;
 * when no metal is enabled (vanilla path), the vanilla {@link ResultSlot} behavior is preserved.
 */
public class TimedCraftingResultSlot extends ResultSlot {
    private final Supplier<TimedCraftingSession> session;

    /**
     * Creates the result slot.
     *
     * @param player    player viewing the menu
     * @param input     crafting grid backing the slot
     * @param container underlying result container
     * @param session   supplies the per-menu timed crafting session lazily
     * @param x         slot x position
     * @param y         slot y position
     */
    public TimedCraftingResultSlot(Player player, CraftingContainer input, Container container, Supplier<TimedCraftingSession> session, int x, int y) {
        super(player, input, container, 0, x, y);
        this.session = session;
    }

    /**
     * Metal mode: a take attempt (click/shift-click) (re)starts timed synthesis and returns an empty
     * stack instead of the item; vanilla mode: delegates to {@link ResultSlot#remove}.
     */
    @Override
    public @NotNull ItemStack remove(int amount) {
        TimedCraftingSession timedCrafting = this.session.get();
        if (!timedCrafting.hasMetal()) {
            return super.remove(amount);
        }
        timedCrafting.evaluateRunning();
        return ItemStack.EMPTY;
    }

    /** Metal mode: re-evaluates the running state on quick-craft; vanilla mode: delegates to super. */
    @Override
    public void onQuickCraft(ItemStack newItem, ItemStack original) {
        TimedCraftingSession timedCrafting = this.session.get();
        if (!timedCrafting.hasMetal()) {
            super.onQuickCraft(newItem, original);
            return;
        }
        timedCrafting.evaluateRunning();
    }
}
