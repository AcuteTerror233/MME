package com.acuteterror233.mite.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

/**
 * GameTests for the free anvil repair ({@code AnvilMenuMixin#mme$freeRepairCost}).
 *
 * <p>The tests drive a real server-side {@link AnvilMenu}: a damaged sword plus repair material
 * is placed into the input slots, the result is taken through the normal click path, and the
 * assertions verify that the player's experience stays untouched while durability restoration and
 * material consumption still work. A rename-only operation must keep charging the vanilla cost.</p>
 */
public class AnvilFreeRepairGameTest {
    /** Experience levels preloaded onto the mock player. */
    private static final int PLAYER_LEVELS = 30;
    /** Anvil position inside the test structure (relative coordinates). */
    private static final BlockPos ANVIL_REL_POS = new BlockPos(1, 2, 1);

    /**
     * Material repair must be free: the displayed cost is 0, taking the result leaves the player's
     * XP untouched, the sword is fully repaired and exactly one ingot is consumed.
     */
    @GameTest
    public void freeRepairKeepsExperience(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.experienceLevel = PLAYER_LEVELS;
        AnvilMenu menu = openAnvilMenu(helper, player);

        // Damage equals one material's restore amount (maxDamage/4 = 62): exactly one ingot fully repairs it
        ItemStack sword = new ItemStack(Items.IRON_SWORD);
        sword.setDamageValue(sword.getMaxDamage() / 4);
        menu.setItem(AnvilMenu.INPUT_SLOT, 0, sword);
        menu.setItem(AnvilMenu.ADDITIONAL_SLOT, 0, new ItemStack(Items.IRON_INGOT, 4));

        // createResult has already run through slotsChanged: the repair must display cost 0
        int cost = menu.getCost();
        if (cost != 0) {
            helper.fail("Material repair cost should be 0, got " + cost);
            return;
        }

        // Take the result through the regular click path (left-click PICKUP on the result slot)
        menu.clicked(AnvilMenu.RESULT_SLOT, 0, ContainerInput.PICKUP, player);

        // The player's XP must be unchanged
        if (player.experienceLevel != PLAYER_LEVELS) {
            helper.fail("Player XP changed after repair: " + PLAYER_LEVELS + " -> " + player.experienceLevel);
            return;
        }
        // The repaired sword must be fully restored and carried off the result slot
        ItemStack carried = menu.getCarried();
        if (carried.isEmpty() || !carried.is(Items.IRON_SWORD) || carried.getDamageValue() != 0) {
            helper.fail("Expected a fully repaired iron sword on the cursor, got: " + carried);
            return;
        }
        // Exactly one ingot consumed as repair material
        int ingotsLeft = menu.getSlot(AnvilMenu.ADDITIONAL_SLOT).getItem().getCount();
        if (ingotsLeft != 3) {
            helper.fail("Expected 3 ingots left after the repair, got " + ingotsLeft);
            return;
        }
        helper.succeed();
    }

    /**
     * Rename-only operations keep the vanilla cost (1 level): the free-repair injection must not
     * zero the cost of non-repair operations.
     */
    @GameTest
    public void renameStillCostsVanillaExperience(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.experienceLevel = PLAYER_LEVELS;
        AnvilMenu menu = openAnvilMenu(helper, player);

        menu.setItem(AnvilMenu.INPUT_SLOT, 0, new ItemStack(Items.IRON_SWORD));
        boolean renamed = menu.setItemName("Sharp Blade");
        if (!renamed) {
            helper.fail("setItemName returned false for a valid name");
            return;
        }

        int cost = menu.getCost();
        if (cost != 1) {
            helper.fail("Rename-only cost should stay at the vanilla value 1, got " + cost);
            return;
        }
        helper.succeed();
    }

    /** Places a vanilla anvil and opens an {@link AnvilMenu} on it for the mock player. */
    private static AnvilMenu openAnvilMenu(GameTestHelper helper, Player player) {
        ServerLevel level = helper.getLevel();
        helper.setBlock(ANVIL_REL_POS, Blocks.ANVIL);
        return new AnvilMenu(0, player.getInventory(), ContainerLevelAccess.create(level, helper.absolutePos(ANVIL_REL_POS)));
    }
}
