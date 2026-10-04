package com.acuteterror233.mite.world.timeline;


import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.FloatModifier;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.timeline.Timeline;

/**
 * Timeline registry bootstrap for MME. Registers the {@code mme:special_moon} timeline,
 * which drives the {@link SpecialMoonPhase} cycle against the Overworld world clock:
 * <ul>
 *   <li>an attribute track that sets {@code SPECIAL_MOON_PHASE} to each special moon for one
 *       night (12000 ticks) and back to {@link SpecialMoonPhase#NORMAL_MOON};</li>
 *   <li>a SUBTRACT modifier on {@code SKY_LIGHT_LEVEL} and a MULTIPLY modifier on
 *       {@code SKY_LIGHT_FACTOR} that darken/brighten nights per phase.</li>
 * </ul>
 */
public interface MMETimelines {
    /** Resource key of the special-moon timeline in the {@code minecraft:timeline} registry. */
    ResourceKey<Timeline> SPECIAL_MOON = key("special_moon");
    static ResourceKey<Timeline> key(String string) {
        return ResourceKey.create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }

    /**
     * Datagen bootstrap: builds and registers the special-moon timeline.
     *
     * @param context bootstrap context providing registry lookups and the registration target
     */
    static void bootstrap(BootstrapContext<Timeline> context) {
        HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
        Holder.Reference<WorldClock> overworldClock = clocks.getOrThrow(WorldClocks.OVERWORLD);

        // One full cycle = 24000 ticks/day * 8 moon phases worth of schedule (192000 ticks);
        // each phase rises at startTick() and is reverted to NORMAL_MOON after one 12000-tick night.
        Timeline.Builder specialMoonPhases = Timeline.builder(overworldClock).setPeriodTicks(24000 * 192).addTrack(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE,track -> {
            for (SpecialMoonPhase phase : SpecialMoonPhase.values()) {
               if(phase.index() == 0){
                   continue;
               }
               track.addKeyframe(phase.startTick(), phase);
               track.addKeyframe(phase.startTick() +12000, SpecialMoonPhase.NORMAL_MOON);
            }
        // Night skylight deltas: raised toward each phase start (1000-tick fade-in) and back
        // after the night. STAR −1 / BLOOD +4 / BLUE −6 / TURBID +2 (SUBTRACT: positive = darker).
        }).addModifierTrack(
            EnvironmentAttributes.SKY_LIGHT_LEVEL,
            FloatModifier.SUBTRACT,
            track -> {
                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() - 1000, 0F);
                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick(), 1F);
                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() +12000, 0F);
                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() - 1000, 0F);
                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick(), 4F);
                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() +12000, 0F);
                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() - 1000, 0F);
                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick(), -6F);
                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() +12000, 0F);
                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() - 1000, 0F);
                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick(), 2F);
                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() +12000, 0F);
        // Night sky-light factor multipliers (MULTIPLY): STAR ×1.3 (starry-bright), BLOOD ×0.1,
        // BLUE ×3 (very bright), TURBID ×0.02 (near-total darkness); 1000-tick fade in/out around each night.
        }).addModifierTrack(
                EnvironmentAttributes.SKY_LIGHT_FACTOR,
                FloatModifier.MULTIPLY,
                track -> {
                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() - 1000, 1F);
                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick(), 1.3F);
                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() +12000, 1F);
                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() - 1000, 1F);
                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick(), 0.1F);
                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() +12000, 1F);
                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() - 1000, 1F);
                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick(), 3F);
                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() +12000, 1F);
                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() - 1000, 1F);
                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick(), 0.02F);
                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() +12000, 1F);
        });
        context.register(SPECIAL_MOON, specialMoonPhases.build());
    }
}
