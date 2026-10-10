package com.acuteterror233.mite.world.entity.ai.sickness;

/**
 * Kinds of unmet husbandry needs that accumulate into animal sickness. Each need is
 * tracked as an independent progress value; any single need reaching the sick threshold
 * makes the animal sick.
 */
public enum SicknessType {
    /** Daylight (or, at night, bright-enough artificial light) is missing. */
    LIGHT,
    /** No reachable water source to drink from. */
    WATER,
    /** Too many animals crowded around the animal. */
    CROWD,
    /** No grass available for grazing (large animals only). */
    GRASS;

    /** Number of tracked sickness needs. */
    public static final int COUNT = values().length;
}
