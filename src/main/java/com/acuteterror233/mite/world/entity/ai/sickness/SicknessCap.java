package com.acuteterror233.mite.world.entity.ai.sickness;

/**
 * Duck interface mixed into {@code Chicken}, {@code Rabbit}, {@code Pig},
 * {@code Sheep} and {@code AbstractCow} to expose their {@link SicknessState}.
 * Cows and mooshrooms share the {@code AbstractCow} implementation.
 */
public interface SicknessCap {
    /** @return the animal's mutable sickness state. */
    SicknessState mme$sicknessState();
}
