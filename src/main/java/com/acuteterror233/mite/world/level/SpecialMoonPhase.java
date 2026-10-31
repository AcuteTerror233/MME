package com.acuteterror233.mite.world.level;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

/**
 * Central enum for the MME special moon cycle. The Overworld runs the {@code mme:special_moon}
 * timeline (see {@code MMETimelines}) on a 129-day period (3096000 ticks): it schedules a short
 * harvest-moon window before each of the three blood-moon days (day 32/64/96), the blood-moon
 * day itself, the blood-moon night, and a single blue-moon night on day 128; all remaining time
 * is {@link #NORMAL_MOON}. The active phase is written to the {@code SPECIAL_MOON_PHASE}
 * environment attribute and synced to clients.
 *
 * <p>The star, phantom, hunt, frost and turbid phases are declared here and their gameplay
 * modifiers are implemented in the listed mixins, but the current timeline does not schedule
 * them, so they never become active in normal play.</p>
 *
 * <p>Gameplay hooks read the attribute via mixins: weather ({@code ServerLevelMixin}), spawn
 * buffs and equipment probability ({@code MobMixin}), moon-scaled mob XP
 * ({@code EnchantmentHelperMixin}), crop growth and disease ({@code CropBlockMixin}),
 * snow/freezing ({@code LevelMixin}, {@code ClientLevelMixin}, {@code BiomeMixin},
 * {@code SnowAndFreezeFeatureMixin}), double loot under HUNT_MOON
 * ({@code LivingEntityMixin}), follow range under FROST_MOON ({@code TargetGoalMixin}) and
 * blocked sleeping under BLOOD_MOON ({@code MME}).</p>
 */
public enum SpecialMoonPhase implements StringRepresentable {
    /** Baseline phase: none of the special-moon gameplay modifiers apply. */
    NORMAL_MOON(0, 0, "normal_moon"),
    /** Star moon: mob spawn equipment chance ×0.6; mob XP is left unchanged. Not scheduled by the current timeline. */
    STAR_MOON(1, 16, "star_moon"),
    /** Harvest moon: crops grow at ×2 speed; scheduled in a short window before each blood-moon day. */
    HARVEST_MOON(2, 24, "harvest_moon"),
    /** Blood moon: dark night (skylight −4, factor ×0.1, crimson sky tint); spawned mobs get Strength + Speed for
     *  12000 ticks and a ×2 equipment chance, sleeping is unsafe, crop disease chance is ×10, mob XP is unchanged. */
    BLOOD_MOON(3, 32, "blood_moon"),
    /** Phantom moon: mob spawn equipment chance ×0.1. Not scheduled by the current timeline. */
    PHANTOM_MOON(4, 80, "phantom_moon"),
    /** Blue moon: exceptionally bright night (skylight +6, factor ×3, cold blue sky tint); crop growth ×2.5 with
     *  disease immunity, forced clear weather, but mob spawn equipment ×0.1. Closes the 129-day cycle. */
    BLUE_MOON(5, 128, "blue_moon"),
    /** Hunt moon: mob loot tables roll twice (double drops); mob XP is left unchanged. Not scheduled by the current timeline. */
    HUNT_MOON(6, 120, "hunt_moon"),
    /** Frost moon: forced rain whose precipitation turns to snow, water freezes even in warm biomes; mob follow
     *  range ×2, crop growth ×0.75. Not scheduled by the current timeline. */
    FROST_MOON(7, 152, "frost_moon"),
    /** Turbid moon: newly spawned mobs are briefly invisible (600 ticks), crop growth ×0.5 and mob XP ×1.5.
     *  Not scheduled by the current timeline. */
    TURBID_MOON(8, 184, "turbid_moon"),

    /** Daytime half of the blood-moon day: forces rain and thunder with no clear weather and adds a strong red
     *  sky tint; shares its day slot with {@link #BLOOD_MOON}. */
    BLOOD_MOON_DAY(9, 32, "blood_moon_day");


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

    /** @return dense enum index (0 = normal moon, 9 = blood-moon day); the client uses it as the quad base vertex ({@code index() * 4}). */
    public int index() {
        return this.index;
    }

    /**
     * @return the phase's scheduled day expressed in ticks ({@code dayIndex * PHASE_LENGTH}), used as
     *         the anchor for the {@code mme:special_moon} timeline keyframes. Phases that share a day
     *         (blood-moon day and blood-moon night) return the same value, and
     *         {@link #BLOOD_MOON}'s value also serves as the length of one blood-moon cycle in the
     *         mob equipment scaling.
     */
    public int startTick() {
        return this.startTick * PHASE_LENGTH;
    }

    /** @return the lower-case serialization name used by {@link #CODEC} (e.g. {@code "blood_moon"}). */
    @Override
    public @NonNull String getSerializedName() {
        return name;
    }
}
