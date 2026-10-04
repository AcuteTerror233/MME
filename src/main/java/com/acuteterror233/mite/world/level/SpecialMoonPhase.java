package com.acuteterror233.mite.world.level;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

/**
 * Central enum for the MME special moon cycle. The Overworld runs a repeating
 * 8-day (192000 tick) schedule ({@code mme:special_moon} timeline, see {@code MMETimelines})
 * that swaps the current phase stored in the {@code SPECIAL_MOON_PHASE} environment
 * attribute; each special moon rises at {@link #startTick()} and lasts one night
 * (12000 ticks) before reverting to {@link #NORMAL_MOON}.
 *
 * <p>Gameplay hooks read the attribute via mixins: spawn equipment probability
 * ({@code MobMixin}), spawn buffs (Strength/Speed/Invisibility), crop growth and disease
 * ({@code CropBlockMixin}), skylight (timeline keyframes), snow/freezing under FROST_MOON,
 * double loot under HUNT_MOON and blocked sleeping under BLOOD_MOON.</p>
 */
public enum SpecialMoonPhase implements StringRepresentable {
    /** Baseline phase: none of the special-moon gameplay modifiers apply. */
    NORMAL_MOON(0, 0, "normal_moon"),
    /** Star moon: slightly altered skylight (level −1, factor ×1.3); mob spawn equipment ×0.6 and mob XP ×2. */
    STAR_MOON(1, 16, "star_moon"),
    /** Harvest moon: crops grow at ×2 speed. */
    HARVEST_MOON(2, 40, "harvest_moon"),
    /** Blood moon: very dark night (skylight −4, factor ×0.1); spawned mobs get Strength + Speed and ×2 equipment,
     *  sleeping is unsafe, crop disease chance ×10, mob XP ×2. */
    BLOOD_MOON(3, 48, "blood_moon"),
    /** Phantom moon: mob spawn equipment chance ×0.1. */
    PHANTOM_MOON(4, 80, "phantom_moon"),
    /** Blue moon: exceptionally bright night (skylight +6, factor ×3); crop growth ×2.5 with disease immunity,
     *  but mob spawn equipment ×0.1. */
    BLUE_MOON(5, 88, "blue_moon"),
    /** Hunt moon: mob loot tables roll twice (double drops) and mob XP ×2. */
    HUNT_MOON(6, 120, "hunt_moon"),
    /** Frost moon: precipitation turns to snow and water freezes even in warm biomes; mob follow range ×2, crop growth ×0.75. */
    FROST_MOON(7, 152, "frost_moon"),
    /** Turbid moon: near-pitch-black night (skylight −2, factor ×0.02); newly spawned mobs are briefly invisible,
     *  crop growth ×0.5 and mob XP ×1.5. */
    TURBID_MOON(8, 184, "turbid_moon");


    /** Codec serializing the phase by its serialized name ({@link #getSerializedName()}). */
    public static final Codec<SpecialMoonPhase> CODEC = StringRepresentable.fromEnum(SpecialMoonPhase::values);
    /** One in-game day in ticks; the unit of {@code startTick} passed to the constructor. */
    public static final int PHASE_LENGTH = 24000;
    private final int index;
    private final int startTick;
    private final String name;

    SpecialMoonPhase(final int index, final int startTick, final String name) {
        this.index = index;
        this.startTick = startTick;
        this.name = name;
    }

    /** @return ordinal position of this phase within the cycle (0 = normal moon). */
    public int index() {
        return this.index;
    }

    /**
     * @return the absolute Overworld day-time tick (within one 192000-tick cycle) at which this
     *         phase rises; the phase stays active for 12000 ticks (one night) afterwards.
     */
    public int startTick() {
        return this.startTick * PHASE_LENGTH -12000;
    }

    /** @return the lower-case serialization name used by {@link #CODEC} (e.g. {@code "blood_moon"}). */
    @Override
    public @NonNull String getSerializedName() {
        return name;
    }
}
