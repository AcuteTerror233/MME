package com.acuteterror233.mite.world.entity.ai.goal;

import com.acuteterror233.mite.world.entity.ai.sickness.SicknessCap;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessRules;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessState;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Move-to-block goal that walks a thirsty animal to the nearest water source. The
 * drink itself is handled by the detection loop once the animal stands next to water.
 */
public class DrinkGoal extends MoveToBlockGoal {
    private final Animal animal;
    private final SicknessRules rules;

    /** @param animal the thirsty animal; @param rules shared sickness configuration. */
    public DrinkGoal(Animal animal, SicknessRules rules) {
        super(animal, rules.needGoalSpeed, rules.waterSearchRange, 4);
        this.animal = animal;
        this.rules = rules;
    }

    @Override
    public boolean canUse() {
        SicknessState state = ((SicknessCap) animal).mme$sicknessState();
        long now = animal.level().getOverworldClockTime();
        if (now < state.waterUntil || state.progress[SicknessType.WATER.ordinal()] < rules.goalTriggerThreshold) {
            return false;
        }
        return super.canUse();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(Blocks.WATER);
    }
}
