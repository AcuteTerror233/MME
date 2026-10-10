package com.acuteterror233.mite.world.entity.ai.goal;

import com.acuteterror233.mite.world.entity.ai.sickness.AnimalSicknessLogic;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessCap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import java.util.EnumSet;

/**
 * Long-lived, flag-less goal that drives the sickness check loop for one animal.
 * The actual check is throttled inside {@link AnimalSicknessLogic#runCheck}.
 */
public class SicknessCheckGoal extends Goal {
    private final Animal animal;

    /** @param animal the animal this goal keeps checked. */
    public SicknessCheckGoal(Animal animal) {
        this.animal = animal;
        this.setFlags(EnumSet.noneOf(Goal.Flag.class));
    }

    @Override
    public boolean canUse() {
        return animal.isAlive() && animal.level() instanceof ServerLevel;
    }

    @Override
    public boolean canContinueToUse() {
        return animal.isAlive();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (animal.level() instanceof ServerLevel level) {
            AnimalSicknessLogic.runCheck(animal, ((SicknessCap) animal).mme$sicknessState(), level);
        }
    }
}
