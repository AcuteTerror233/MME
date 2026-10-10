package com.acuteterror233.mite.world.entity.ai.sickness;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * Core sickness logic shared by the animal mixins: throttled per-animal checks of the
 * four needs (light, water, crowding, grazing), progress accumulation, sickness
 * transitions and the sick-state effects (slowness, smoke particles).
 */
public final class AnimalSicknessLogic {
    /** Sky darkness below which daylight still counts as available light. */
    public static final int DAYLIGHT_SKY_DARKEN_MAX = 3;
    /** Horizontal and vertical radius around the animal in which it can drink. */
    private static final int DRINK_SCAN_RADIUS = 2;

    private AnimalSicknessLogic() {
    }

    /** @return whether the animal is enabled for the sickness system. */
    public static boolean isEnabled(Animal animal) {
        return SicknessRules.get().isEnabled(BuiltInRegistries.ENTITY_TYPE.getKey(animal.getType()));
    }

    /** @return whether the animal currently counts as sick. */
    public static boolean isSick(SicknessCap cap) {
        return cap.mme$sicknessState().sick;
    }

    /**
     * Runs one throttled sickness check for the animal; called every tick from the
     * check goal, the check itself only fires every {@code checkIntervalTicks}.
     */
    public static void runCheck(Animal animal, SicknessState state, ServerLevel level) {
        SicknessRules rules = SicknessRules.get();
        long now = level.getOverworldClockTime();
        if ((now + animal.getId()) % rules.checkIntervalTicks != 0L) {
            return;
        }

        updateProgress(state, SicknessType.LIGHT, hasLight(animal, level), rules);
        updateProgress(state, SicknessType.WATER, hasWater(animal, level, state, now, rules), rules);
        updateCrowding(animal, state, level, rules);
        updateProgress(state, SicknessType.GRASS, hasGraze(animal, state, now, rules), rules);

        boolean anySick = false;
        boolean allRecovered = true;
        for (int value : state.progress) {
            if (value >= rules.sickThreshold) {
                anySick = true;
            }
            if (value >= rules.recoveredThreshold) {
                allRecovered = false;
            }
        }
        if (!state.sick && anySick) {
            state.sick = true;
        } else if (state.sick && allRecovered) {
            state.sick = false;
            animal.removeEffect(MobEffects.SLOWNESS);
        }

        if (state.sick) {
            animal.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, rules.checkIntervalTicks * 2, 0, true, false));
            level.sendParticles(ParticleTypes.SMOKE,
                    animal.getX(), animal.getY(0.8D), animal.getZ(), 2, 0.25D, 0.25D, 0.25D, 0.01D);
        }
    }

    /**
     * @return whether light is available: open sky during (non-thundering) daylight, or
     *         bright-enough local light at night and indoors (artificial sources count).
     */
    private static boolean hasLight(Animal animal, ServerLevel level) {
        SicknessRules rules = SicknessRules.get();
        BlockPos pos = animal.blockPosition();
        return level.getSkyDarken() <= DAYLIGHT_SKY_DARKEN_MAX && level.canSeeSky(pos)
                || level.getMaxLocalRawBrightness(pos) >= rules.nightLightBrightness;
    }

    /**
     * @return whether thirst is quenched: either the quench timer is still running or
     *         water is within drinking reach (2 blocks horizontally, 1 vertically).
     */
    private static boolean hasWater(Animal animal, ServerLevel level, SicknessState state, long now, SicknessRules rules) {
        if (now < state.waterUntil) {
            return true;
        }
        BlockPos feet = animal.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(feet.offset(-DRINK_SCAN_RADIUS, -1, -DRINK_SCAN_RADIUS),
                feet.offset(DRINK_SCAN_RADIUS, 1, DRINK_SCAN_RADIUS))) {
            if (level.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.WATER)) {
                state.waterUntil = now + rules.thirstQuenchTicks;
                return true;
            }
        }
        return false;
    }

    /**
     * @return whether the grazing need is satisfied: small animals have none, large
     *         animals need a recent meal from the graze goal.
     */
    private static boolean hasGraze(Animal animal, SicknessState state, long now, SicknessRules rules) {
        if (rules.grassKind(BuiltInRegistries.ENTITY_TYPE.getKey(animal.getType())) == SicknessRules.GrassKind.NONE) {
            return true;
        }
        return now < state.grazeUntil;
    }

    /** Counts nearby animals (all kinds) and updates the crowding progress. */
    private static void updateCrowding(Animal animal, SicknessState state, ServerLevel level, SicknessRules rules) {
        AABB box = animal.getBoundingBox().inflate(rules.crowdRadius);
        List<Animal> neighbors = level.getEntitiesOfClass(Animal.class, box, other -> other != animal && other.isAlive());
        int count = neighbors.size();
        if (count >= rules.crowdHeavyLimit) {
            state.progress[SicknessType.CROWD.ordinal()] =
                    Math.min(rules.sickThreshold, state.progress[SicknessType.CROWD.ordinal()] + rules.crowdHeavyGainPerCheck);
        } else if (count >= rules.crowdLimit) {
            state.progress[SicknessType.CROWD.ordinal()] =
                    Math.min(rules.sickThreshold, state.progress[SicknessType.CROWD.ordinal()] + rules.progressGainPerCheck);
        } else {
            state.progress[SicknessType.CROWD.ordinal()] =
                    Math.max(0, state.progress[SicknessType.CROWD.ordinal()] - rules.progressRecoverPerCheck);
        }
    }

    /** Moves one need's progress toward or away from the sick threshold. */
    private static void updateProgress(SicknessState state, SicknessType type, boolean satisfied, SicknessRules rules) {
        int index = type.ordinal();
        if (satisfied) {
            state.progress[index] = Math.max(0, state.progress[index] - rules.progressRecoverPerCheck);
        } else {
            state.progress[index] = Math.min(rules.sickThreshold, state.progress[index] + rules.progressGainPerCheck);
        }
    }
}
