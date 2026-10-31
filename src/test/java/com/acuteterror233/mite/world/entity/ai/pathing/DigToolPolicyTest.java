package com.acuteterror233.mite.world.entity.ai.pathing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the pure cores of {@link DigToolPolicy}.
 *
 * <p>Only the primitive-argument overloads are exercised: {@code BlockState.is(tag)} requires
 * a bound tag set and {@code new ItemStack(item)} requires bound item components, neither of
 * which exists in the plain JUnit environment (vanilla {@code Bootstrap.bootStrap()} binds
 * registries only). The block × tool matrix is therefore verified by the GameTest suite with
 * a running server, and the hardness/tool-speed values below mirror the vanilla ones
 * (dirt 0.5, leaves 0.2, oak planks 2.0, stone 1.5 with a tool requirement, obsidian 50).</p>
 */
class DigToolPolicyTest {

    // ─── diggability ───

    @Test
    void softBlocksAreDiggableBareHand() {
        assertTrue(DigToolPolicy.isDiggable(false, false, 0.5F, false, false), "dirt, bare hand");
        assertTrue(DigToolPolicy.isDiggable(false, false, 2.0F, false, false), "oak planks, bare hand");
        assertTrue(DigToolPolicy.isDiggable(false, false, 0.2F, false, false), "leaves, bare hand");
    }

    @Test
    void stoneRequiresAPickaxe() {
        assertFalse(DigToolPolicy.isDiggable(false, false, 1.5F, true, false), "stone, bare hand");
        assertTrue(DigToolPolicy.isDiggable(false, false, 1.5F, true, true), "stone, wooden pickaxe");
    }

    @Test
    void blockEntitiesAreNeverDug() {
        assertFalse(DigToolPolicy.isDiggable(false, true, 2.5F, false, true), "chest, iron pickaxe");
    }

    @Test
    void forbiddenTagBlocksAreNeverDug() {
        assertFalse(DigToolPolicy.isDiggable(true, false, 0.5F, false, false), "tag-forbidden dirt");
    }

    @Test
    void unbreakableAndTooHardBlocksAreNeverDug() {
        assertFalse(DigToolPolicy.isDiggable(false, false, -1.0F, false, true), "bedrock");
        assertFalse(DigToolPolicy.isDiggable(false, false, 50.0F, true, true), "obsidian");
        assertFalse(DigToolPolicy.isDiggable(false, false, 5.1F, false, false), "just above the hardness cap");
    }

    // ─── dig duration ───

    @Test
    void bareHandDirtTakesMoreThanOneMinute() {
        assertTrue(DigToolPolicy.digTicks(0.5F, 1.0F) > 1200, "bare-hand dirt must exceed 60 s");
    }

    @Test
    void digTicksForRepresentativeBlocks() {
        assertEquals(1500, DigToolPolicy.digTicks(0.5F, 1.0F), "dirt, bare hand");
        assertEquals(600, DigToolPolicy.digTicks(0.2F, 1.0F), "leaves, bare hand");
        assertEquals(6000, DigToolPolicy.digTicks(2.0F, 1.0F), "oak planks, bare hand");
        assertEquals(2250, DigToolPolicy.digTicks(1.5F, 2.0F), "stone, wooden pickaxe");
        assertEquals(750, DigToolPolicy.digTicks(1.5F, 6.0F), "stone, iron pickaxe");
    }

    @Test
    void digTicksClampHardnessAndToolSpeed() {
        // Hardness is floored at 0.1, so the result cannot go below 300 ticks at speed 1
        assertEquals(300, DigToolPolicy.digTicks(0.01F, 1.0F));
        // Below MIN_DIG_TICKS the result is clamped up
        assertEquals(DigToolPolicy.MIN_DIG_TICKS, DigToolPolicy.digTicks(0.001F, 1000.0F));
        // Tool speed is floored at 1.0, so a speed of 0 behaves like a bare hand
        assertEquals(3000, DigToolPolicy.digTicks(1.0F, 0.0F));
    }
}
