package com.acuteterror233.mite.hotkey;

import com.acuteterror233.mite.mixin.client.KeyMappingAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Auto Harvest Mode (AHM) / Auto Use Mode (AUM) state machine, mirroring the MITE hotkeys.
 *
 * <ul>
 *   <li>AHM — while continuously mining, a right-click click activates it: the attack key is
 *       then forced down so mining continues hands-free on the <em>same kind</em> of block.
 *       If the crosshair moves away, the mode stays armed but the key is released (mining
 *       pauses); moving the crosshair back onto the same block kind resumes mining.
 *       Any left click or a hotbar slot change stops it.</li>
 *   <li>AUM — while continuously using an item (eating, drinking, drawing a bow...), a left
 *       click activates it: the use key is forced down so the action continues hands-free.
 *       When the current use finishes, the mode ends and further auto-uses are latched out
 *       (no chaining, e.g. eating a second food) until the player issues a fresh right click.
 *       Any further left click or a hotbar slot change also cancels it.</li>
 * </ul>
 *
 * <p>The active mode's hotbar slot is highlighted green by {@code HudHotbarMixin}.</p>
 */
public final class MMEAutoModes {
    /** Operating modes. */
    private enum Mode {
        /** Nothing active. */
        NONE,
        /** Auto harvest: continuous mining of one block kind. */
        HARVEST,
        /** Auto use: continuous item use. */
        USE
    }

    private static Mode mode = Mode.NONE;
    private static Block harvestBlock;
    private static int highlightSlot = -1;
    /**
     * When set, vanilla's hold-to-reuse trigger ({@code startUseItem} on {@code keyUse.isDown()})
     * is suppressed each frame by forcing the use key up, until a fresh right-click press
     * (new click count) is seen. Prevents item chains after a finished AUM use.
     */
    private static boolean useLatch;

    /** No-op hook. */
    public static void init() {
    }

    /** @return the currently highlighted hotbar slot, or -1 when no mode is active. */
    public static int highlightSlot() {
        return highlightSlot;
    }

    /**
     * Per-frame tick, invoked at the HEAD of {@code Minecraft.handleKeybinds} — before vanilla
     * consumes the key click counters, so a fresh click can be detected without stealing it.
     *
     * @param minecraft the client instance
     */
    public static void tick(Minecraft minecraft) {
        if (minecraft.player == null || minecraft.level == null) {
            stop(minecraft);
            return;
        }
        KeyMapping attack = minecraft.options.keyAttack;
        KeyMapping use = minecraft.options.keyUse;
        boolean leftClicked = ((KeyMappingAccessor) attack).mme$clickCount() > 0;
        boolean rightClicked = ((KeyMappingAccessor) use).mme$clickCount() > 0;
        int slot = minecraft.player.getInventory().getSelectedSlot();

        // While latched, hold the use key up so vanilla's continuous-use branch never fires;
        // a fresh right-click press releases the latch and is handled by vanilla normally.
        if (useLatch) {
            if (rightClicked) {
                useLatch = false;
            } else {
                use.setDown(false);
            }
        }

        // A hotbar slot change cancels whatever mode is active.
        if (mode != Mode.NONE && slot != highlightSlot) {
            stop(minecraft);
            return;
        }

        // Activation runs before cancellation so the triggering click never cancels itself.
        boolean activated = false;
        if (mode != Mode.HARVEST && attack.isDown() && rightClicked && minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.BLOCK) {
            if (mode != Mode.NONE) {
                stop(minecraft);
            }
            mode = Mode.HARVEST;
            harvestBlock = minecraft.level.getBlockState(((BlockHitResult) minecraft.hitResult).getBlockPos()).getBlock();
            highlightSlot = slot;
            activated = true;
        } else if (mode != Mode.USE && use.isDown() && minecraft.player.isUsingItem() && leftClicked) {
            if (mode != Mode.NONE) {
                stop(minecraft);
            }
            mode = Mode.USE;
            highlightSlot = slot;
            activated = true;
        }

        if (!activated && leftClicked && mode != Mode.NONE) {
            stop(minecraft);
            return;
        }

        if (mode == Mode.HARVEST) {
            // Stay armed with the crosshair elsewhere; forcing resumes once the crosshair is
            // back on the same kind of block.
            if (minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.BLOCK && minecraft.level.getBlockState(((BlockHitResult) minecraft.hitResult).getBlockPos()).getBlock() == harvestBlock) {
                attack.setDown(true);
            }
        } else if (mode == Mode.USE) {
            if (!minecraft.player.isUsingItem()) {
                // The current use finished: end the mode and latch out chained auto-uses.
                stop(minecraft);
                useLatch = true;
            } else {
                use.setDown(true);
            }
        }
    }

    /** Ends the active mode and releases the forced key state. */
    private static void stop(Minecraft minecraft) {
        if (mode == Mode.HARVEST) {
            minecraft.options.keyAttack.setDown(false);
        }
        if (mode == Mode.USE) {
            minecraft.options.keyUse.setDown(false);
        }
        mode = Mode.NONE;
        harvestBlock = null;
        highlightSlot = -1;
    }
}
