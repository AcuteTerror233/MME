package com.acuteterror233.mite.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Blocks;

/**
 * GameTests for the universal falling-block damage ({@code FallingBlockEntityMixin#mme$enableFallDamage}).
 *
 * <p>Every falling block now carries anvil-style fall damage at half the anvil height bonus
 * ({@code 1.0F} per fallen block), while anvils keep the vanilla {@code 2.0F} bonus. The tests
 * drop a sand block and an anvil onto two stationary pigs from the same height and compare the
 * damage taken.</p>
 */
public class FallingBlockDamageGameTest {
    /**
     * Relative positions of the two stationary subjects (sand target and anvil target).
     * Relative y=0 is the top of the empty-test-structure platform, which is where a
     * {@code setNoAi} pig must rest so the falling block's landing AABB still overlaps it.
     */
    private static final BlockPos SAND_PIG_REL_POS = new BlockPos(1, 0, 1);
    private static final BlockPos ANVIL_PIG_REL_POS = new BlockPos(3, 0, 1);
    /** Drop height above each subject, identical for both blocks to keep the comparison fair. */
    private static final BlockPos DROP_REL_POS = new BlockPos(1, 4, 1);
    private static final BlockPos ANVIL_DROP_REL_POS = new BlockPos(3, 4, 1);

    /** A sand block falling onto an entity must hurt it with the halved height bonus. */
    @GameTest(maxTicks = 120)
    public void sandFallingBlockHurtsEntities(GameTestHelper helper) {
        Animal pig = helper.spawn(EntityTypes.PIG, SAND_PIG_REL_POS);
        pig.setNoAi(true);
        helper.setBlock(DROP_REL_POS, Blocks.SAND);
        helper.startSequence().thenWaitUntil(() ->
                        helper.assertTrue(pig.getHealth() < pig.getMaxHealth(), "sand has not hurt the pig yet"))
                .thenExecute(() -> {
                    float damage = pig.getMaxHealth() - pig.getHealth();
                    if (damage < 1.0F) {
                        helper.fail("Falling sand must hurt the pig, damage taken: " + damage);
                        return;
                    }
                    helper.succeed();
                }).thenSucceed();
    }

    /** From the same drop height the anvil bonus must exceed the halved ordinary-block bonus. */
    @GameTest(maxTicks = 120)
    public void anvilDamageExceedsSandDamage(GameTestHelper helper) {
        Animal sandPig = helper.spawn(EntityTypes.PIG, SAND_PIG_REL_POS);
        sandPig.setNoAi(true);
        Animal anvilPig = helper.spawn(EntityTypes.PIG, ANVIL_PIG_REL_POS);
        anvilPig.setNoAi(true);
        helper.setBlock(DROP_REL_POS, Blocks.SAND);
        helper.setBlock(ANVIL_DROP_REL_POS, Blocks.ANVIL);
        helper.startSequence().thenWaitUntil(() -> {
            if (sandPig.getHealth() >= sandPig.getMaxHealth()
                    || anvilPig.getHealth() >= anvilPig.getMaxHealth()) {
                helper.assertTrue(false, "falling blocks have not landed on both pigs yet");
                return;
            }
        }).thenExecute(() -> {
            float sandDamage = sandPig.getMaxHealth() - sandPig.getHealth();
            float anvilDamage = anvilPig.getMaxHealth() - anvilPig.getHealth();
            if (anvilDamage <= sandDamage) {
                helper.fail("Anvil damage (" + anvilDamage + ") must exceed sand damage (" + sandDamage + ")");
                return;
            }
            helper.succeed();
        }).thenSucceed();
    }
}
