package com.acuteterror233.mite.gametest;

import com.acuteterror233.mite.world.entity.ai.sickness.*;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.Animal;

/**
 * GameTests for the untreated-sickness death: a sick animal whose death deadline has
 * passed dies of poisoning during the check, while a freshly sick animal rolls a death
 * delay inside the configured window and stays alive until then.
 */
public class AnimalSicknessGameTest {
    /** Relative spawn position of the tested animal inside the test structure. */
    private static final BlockPos SUBJECT_REL_POS = new BlockPos(1, 1, 1);

    /** An animal whose sickness deadline has passed must die during the check. */
    @GameTest(maxTicks = 60)
    public void expiredSicknessKills(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Animal animal = helper.spawn(EntityTypes.COW, SUBJECT_REL_POS);
        SicknessState state = ((SicknessCap) animal).mme$sicknessState();
        SicknessRules rules = SicknessRules.get();
        // A realistically sick animal still carries progress above the recovery band,
        // otherwise the recovery transition would clear the death sentence first
        state.progress[SicknessType.WATER.ordinal()] = rules.sickThreshold;
        state.sick = true;
        state.deathAt = level.getOverworldClockTime() - 1L;
        helper.startSequence().thenExecute(() -> {
            AnimalSicknessLogic.performCheck(animal, state, level);
            if (!animal.isDeadOrDying()) {
                helper.fail("Sick animal past its death deadline must die");
                return;
            }
            helper.succeed();
        }).thenSucceed();
    }

    /**
     * Falling sick rolls a death delay inside the configured min..max window and does
     * not kill the animal immediately.
     */
    @GameTest(maxTicks = 60)
    public void deathDelayRollsInWindow(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Animal animal = helper.spawn(EntityTypes.COW, SUBJECT_REL_POS);
        SicknessState state = ((SicknessCap) animal).mme$sicknessState();
        SicknessRules rules = SicknessRules.get();
        state.progress[SicknessType.WATER.ordinal()] = rules.sickThreshold;
        AnimalSicknessLogic.performCheck(animal, state, level);
        long now = level.getOverworldClockTime();
        if (!state.sick) {
            helper.fail("A maxed-out need must make the animal sick");
            return;
        }
        long delay = state.deathAt - now;
        if (delay < rules.deathDelayMinTicks || delay > rules.deathDelayMaxTicks) {
            helper.fail("Death delay " + delay + " ticks outside ["
                    + rules.deathDelayMinTicks + ", " + rules.deathDelayMaxTicks + "]");
            return;
        }
        if (animal.isDeadOrDying()) {
            helper.fail("A freshly sick animal must not die immediately");
            return;
        }
        helper.succeed();
    }
}
