package com.acuteterror233.mite.world.entity.ai.goal;

import com.acuteterror233.mite.world.entity.ai.sickness.SicknessCap;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessRules;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessState;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Move-to-block goal that walks a hungry large animal to the nearest edible grass and
 * eats it: grass blocks are turned into dirt (sheep, pig), short/tall grass plants are
 * removed (cow, mooshroom, including the other half of a tall plant).
 */
public class GrazeGoal extends MoveToBlockGoal {
    private final Animal animal;
    private final SicknessRules rules;
    private final SicknessRules.GrassKind kind;
    private boolean eaten;

    /** @param animal the hungry animal; @param rules shared config; @param kind what this animal eats. */
    public GrazeGoal(Animal animal, SicknessRules rules, SicknessRules.GrassKind kind) {
        super(animal, rules.needGoalSpeed, 8, 4);
        this.animal = animal;
        this.rules = rules;
        this.kind = kind;
    }

    @Override
    public boolean canUse() {
        this.eaten = false;
        SicknessState state = ((SicknessCap) animal).mme$sicknessState();
        long now = animal.level().getOverworldClockTime();
        if (now < state.grazeUntil || state.progress[SicknessType.GRASS.ordinal()] < rules.goalTriggerThreshold) {
            return false;
        }
        return super.canUse();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (kind == SicknessRules.GrassKind.GRASS_BLOCK) {
            return state.is(Blocks.GRASS_BLOCK);
        }
        return state.is(Blocks.SHORT_GRASS) || state.is(Blocks.TALL_GRASS);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.eaten && this.isReachedTarget()) {
            this.eaten = true;
            eatTargetBlock();
        }
    }

    /** Consumes the target block and quenches the grazing need. */
    private void eatTargetBlock() {
        Level level = animal.level();
        BlockPos pos = this.blockPos;
        BlockState state = level.getBlockState(pos);
        if (kind == SicknessRules.GrassKind.GRASS_BLOCK) {
            if (!state.is(Blocks.GRASS_BLOCK)) {
                return;
            }
            level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
        } else {
            if (state.is(Blocks.SHORT_GRASS)) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            } else if (state.is(Blocks.TALL_GRASS)) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                BlockPos other = level.getBlockState(pos.above()).is(Blocks.TALL_GRASS) ? pos.above()
                        : level.getBlockState(pos.below()).is(Blocks.TALL_GRASS) ? pos.below() : null;
                if (other != null) {
                    level.setBlock(other, Blocks.AIR.defaultBlockState(), 3);
                }
            } else {
                return;
            }
        }
        ((SicknessCap) animal).mme$sicknessState().grazeUntil =
                level.getOverworldClockTime() + rules.grazeQuenchTicks;
        level.playSound(null, animal.getX(), animal.getY(), animal.getZ(),
                SoundEvents.GENERIC_EAT.value(), SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    @Override
    public double acceptedDistance() {
        return 2.5;
    }
}
