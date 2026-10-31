package com.acuteterror233.mite.world.entity.ai.goal;

import com.acuteterror233.mite.world.entity.ai.sickness.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

/**
 * Move-to-block goal that walks a light-starved animal toward a lit spot: open sky during
 * daylight (sky darkening at most {@link AnimalSicknessLogic#DAYLIGHT_SKY_DARKEN_MAX}),
 * or any position whose local raw brightness reaches {@link SicknessRules#nightLightBrightness}
 * (artificial light counts, regardless of the time of day). Standing in the light satisfies
 * the need via the detection loop; the goal only starts once the light progress reaches
 * {@code rules.goalTriggerThreshold}.
 */
public class SeekLightGoal extends MoveToBlockGoal {
    private final Animal animal;
    private final SicknessRules rules;

    /**
     * @param animal the light-starved animal
     * @param rules  shared sickness configuration
     */
    public SeekLightGoal(Animal animal, SicknessRules rules) {
        super(animal, rules.needGoalSpeed, 8, 4);
        this.animal = animal;
        this.rules = rules;
    }

    @Override
    public boolean canUse() {
        SicknessState state = ((SicknessCap) animal).mme$sicknessState();
        if (state.progress[SicknessType.LIGHT.ordinal()] < rules.goalTriggerThreshold) {
            return false;
        }
        return super.canUse();
    }

    /**
     * A position qualifies when it sees open clear sky or reaches the night-light brightness
     * threshold; both branches require a real {@link Level}, since the reader is not queried.
     *
     * @param level the level reader used for the sky/brightness queries
     * @param pos   the candidate position
     * @return whether {@code pos} provides enough light
     */
    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!(level instanceof Level actualLevel)) {
            return false;
        }
        return actualLevel.getSkyDarken() <= AnimalSicknessLogic.DAYLIGHT_SKY_DARKEN_MAX && actualLevel.canSeeSky(pos)
                || actualLevel.getMaxLocalRawBrightness(pos) >= rules.nightLightBrightness;
    }
}
