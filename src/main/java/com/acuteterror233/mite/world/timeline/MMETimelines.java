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
import net.minecraft.util.ARGB;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.ColorModifier;
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
    ResourceKey<Timeline> BLOOD_MOON = key("blood_moon");
    /** Strong red haze added to the daytime sky on the blood-moon day (RGB24, ADD_RGB argument). */
    int BLOOD_MOON_DAY_SKY_TINT = 0x5A1206;
    /** Deep crimson glow added to the night sky during the blood moon (RGB24, ADD_RGB argument). */
    int BLOOD_MOON_NIGHT_SKY_TINT = 0x8C0C04;
    /** Cold blue glow added to the night sky during the blue moon (RGB24, ADD_RGB argument). */
    int BLUE_MOON_SKY_TINT = 0x1A2C5A;
    static ResourceKey<Timeline> key(String string) {
        return ResourceKey.create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }

    /**
     * Datazen bootstrap: builds and registers the special-moon timeline.
     *
     * @param context bootstrap context providing registry lookups and the registration target
     */
    static void bootstrap(BootstrapContext<Timeline> context) {
        HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
        Holder.Reference<WorldClock> overworldClock = clocks.getOrThrow(WorldClocks.OVERWORLD);

        Timeline.Builder specialMoon = Timeline.builder(overworldClock)
                .setPeriodTicks(SpecialMoonPhase.BLUE_MOON.startTick() + 24000)
                .addTrack(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE,
                track ->{
                    int bmdst = SpecialMoonPhase.BLOOD_MOON.startTick();
                    int bmst = (bmdst + 13000);
                    track.addKeyframe(0, SpecialMoonPhase.NORMAL_MOON);

                    track.addKeyframe(bmst - 8 * 24000, SpecialMoonPhase.HARVEST_MOON);
                    track.addKeyframe((bmst + 11000) - 8 * 24000, SpecialMoonPhase.NORMAL_MOON);

                    track.addKeyframe(bmdst, SpecialMoonPhase.BLOOD_MOON_DAY);
                    track.addKeyframe(bmst, SpecialMoonPhase.BLOOD_MOON);
                    track.addKeyframe(bmst + 11000, SpecialMoonPhase.NORMAL_MOON);

                    track.addKeyframe(bmdst * 2 - 8 * 24000, SpecialMoonPhase.HARVEST_MOON);
                    track.addKeyframe(bmdst * 2 + 24000 - 8 * 24000, SpecialMoonPhase.NORMAL_MOON);

                    track.addKeyframe(bmdst * 2, SpecialMoonPhase.BLOOD_MOON_DAY);
                    track.addKeyframe(bmdst * 2 + 13000, SpecialMoonPhase.BLOOD_MOON);
                    track.addKeyframe(bmdst * 2 + 24000, SpecialMoonPhase.NORMAL_MOON);

                    track.addKeyframe(bmdst * 3 - 8 * 24000, SpecialMoonPhase.HARVEST_MOON);
                    track.addKeyframe(bmdst * 3 + 24000 - 8 * 24000, SpecialMoonPhase.NORMAL_MOON);

                    track.addKeyframe(bmdst * 3, SpecialMoonPhase.BLOOD_MOON_DAY);
                    track.addKeyframe(bmdst * 3 + 13000, SpecialMoonPhase.BLOOD_MOON);
                    track.addKeyframe(bmdst * 3 + 24000, SpecialMoonPhase.NORMAL_MOON);

                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 13000, SpecialMoonPhase.BLUE_MOON);
                }).addModifierTrack(
                        EnvironmentAttributes.SKY_LIGHT_LEVEL,
                        FloatModifier.ADD,
                        track -> {
                            int bmdst = SpecialMoonPhase.BLOOD_MOON.startTick();
                            int bmst = bmdst + 13000;
                            track.addKeyframe(0, 0F);

                            track.addKeyframe(bmdst - 1000, 0F);
                            track.addKeyframe(bmdst, -4F);
                            track.addKeyframe(bmdst + 23000, -4F);
                            track.addKeyframe(bmdst + 24000, 0F);

                            track.addKeyframe(bmdst * 2 - 1000, 0F);
                            track.addKeyframe(bmdst * 2, -4F);
                            track.addKeyframe(bmdst * 2 + 23000, -4F);
                            track.addKeyframe(bmdst * 2 + 24000, 0F);

                            track.addKeyframe(bmdst * 3 - 1000, 0F);
                            track.addKeyframe(bmdst * 3, -4F);
                            track.addKeyframe(bmdst * 3 + 23000, -4F);
                            track.addKeyframe(bmdst * 3 + 24000, 0F);

                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 12000, 0F);
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 13000, 6F);
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 23000, 6F);
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 24000, 0F);
                        }
                ).addModifierTrack(
                        EnvironmentAttributes.SKY_LIGHT_FACTOR,
                        FloatModifier.MULTIPLY,
                        track -> {
                            int bmdst = SpecialMoonPhase.BLOOD_MOON.startTick();
                            int bmst = bmdst + 13000;
                            track.addKeyframe(0, 1F);

                            track.addKeyframe(bmdst - 1000, 1F);
                            track.addKeyframe(bmdst, 0.1F);
                            track.addKeyframe(bmdst + 23000, 0.1F);
                            track.addKeyframe(bmdst + 24000, 1F);

                            track.addKeyframe(bmdst * 2 - 1000, 1F);
                            track.addKeyframe(bmdst * 2, 0.1F);
                            track.addKeyframe(bmdst * 2 + 23000, 0.1F);
                            track.addKeyframe(bmdst * 2 + 24000, 1F);

                            track.addKeyframe(bmdst * 3 - 1000, 1F);
                            track.addKeyframe(bmdst * 3, 0.1F);
                            track.addKeyframe(bmdst * 3 + 23000, 0.1F);
                            track.addKeyframe(bmdst * 3 + 24000, 1F);

                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 12000, 1F);
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 13000, 3F);
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 23000, 3F);
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 24000, 1F);
                        }
                ).addModifierTrack(
                        EnvironmentAttributes.SKY_COLOR,
                        ColorModifier.ADD_RGB,
                        track -> {

                            int bmdst = SpecialMoonPhase.BLOOD_MOON.startTick();
                            int bmst = bmdst + 13000;
                            track.addKeyframe(0, ARGB.vector3fFromRGB24(0));

                            track.addKeyframe(bmdst - 1000, ARGB.vector3fFromRGB24(0));
                            track.addKeyframe(bmdst, ARGB.vector3fFromRGB24(BLOOD_MOON_DAY_SKY_TINT));
                            track.addKeyframe(bmdst + 23000, ARGB.vector3fFromRGB24(BLOOD_MOON_NIGHT_SKY_TINT));
                            track.addKeyframe(bmdst + 24000, ARGB.vector3fFromRGB24(0));

                            track.addKeyframe(bmdst * 2 - 1000, ARGB.vector3fFromRGB24(0));
                            track.addKeyframe(bmdst * 2, ARGB.vector3fFromRGB24(BLOOD_MOON_DAY_SKY_TINT));
                            track.addKeyframe(bmdst * 2 + 23000, ARGB.vector3fFromRGB24(BLOOD_MOON_NIGHT_SKY_TINT));
                            track.addKeyframe(bmdst * 2 + 24000, ARGB.vector3fFromRGB24(0));

                            track.addKeyframe(bmdst * 3 - 1000, ARGB.vector3fFromRGB24(0));
                            track.addKeyframe(bmdst * 3, ARGB.vector3fFromRGB24(BLOOD_MOON_DAY_SKY_TINT));
                            track.addKeyframe(bmdst * 3 + 23000, ARGB.vector3fFromRGB24(BLOOD_MOON_NIGHT_SKY_TINT));
                            track.addKeyframe(bmdst * 3 + 24000, ARGB.vector3fFromRGB24(0));

                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 12000, ARGB.vector3fFromRGB24(0));
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 13000, ARGB.vector3fFromRGB24(BLUE_MOON_SKY_TINT));
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 23000, ARGB.vector3fFromRGB24(BLUE_MOON_SKY_TINT));
                            track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() + 24000, ARGB.vector3fFromRGB24(0));
                        }
                );
        context.register(SPECIAL_MOON, specialMoon.build());
        context.register(BLOOD_MOON, Timeline.builder(overworldClock).setPeriodTicks(32 * 24000).build());

        // NOTE: the blue moon's phase keyframes cannot go into a separate timeline - the
        // SPECIAL_MOON_PHASE attribute only supports the override modifier, and override layers
        // replace the whole value, so the last timeline in the tag would erase the blood moon's
        // phase wherever it samples NORMAL_MOON. Merge them into the special_moon phase track
        // above (the period must then cover the blue moon's day) and, if wanted, give each moon
        // its own additive visual tracks (ADD_RGB sky tints compose across timelines safely).
//        // One full cycle = 24000 ticks/day * 8 moon phases worth of schedule (192000 ticks);
//        // each phase rises at startTick() and is reverted to NORMAL_MOON after one 12000-tick night.
//        Timeline.Builder specialMoonPhases = Timeline.builder(overworldClock).setPeriodTicks(24000 * 192).addTrack(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE,track -> {
//            for (SpecialMoonPhase phase : SpecialMoonPhase.values()) {
//               if(phase.index() == 0){
//                   continue;
//               }
//               track.addKeyframe(phase.startTick(), phase);
//               track.addKeyframe(phase.startTick() +12000, SpecialMoonPhase.NORMAL_MOON);
//            }
//        // Night skylight deltas: raised toward each phase start (1000-tick fade-in) and back
//        // after the night. STAR −1 / BLOOD +4 / BLUE −6 / TURBID +2 (SUBTRACT: positive = darker).
//        }).addModifierTrack(
//            EnvironmentAttributes.SKY_LIGHT_LEVEL,
//            FloatModifier.SUBTRACT,
//            track -> {
//                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() - 1000, 0F);
//                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick(), 1F);
//                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() +12000, 0F);
//                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() - 1000, 0F);
//                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick(), 4F);
//                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() +12000, 0F);
//                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() - 1000, 0F);
//                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick(), -6F);
//                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() +12000, 0F);
//                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() - 1000, 0F);
//                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick(), 2F);
//                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() +12000, 0F);
//        // Night sky-light factor multipliers (MULTIPLY): STAR ×1.3 (starry-bright), BLOOD ×0.1,
//        // BLUE ×3 (very bright), TURBID ×0.02 (near-total darkness); 1000-tick fade in/out around each night.
//        }).addModifierTrack(
//                EnvironmentAttributes.SKY_LIGHT_FACTOR,
//                FloatModifier.MULTIPLY,
//                track -> {
//                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() - 1000, 1F);
//                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick(), 1.3F);
//                    track.addKeyframe(SpecialMoonPhase.STAR_MOON.startTick() +12000, 1F);
//                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() - 1000, 1F);
//                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick(), 0.1F);
//                    track.addKeyframe(SpecialMoonPhase.BLOOD_MOON.startTick() +12000, 1F);
//                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() - 1000, 1F);
//                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick(), 3F);
//                    track.addKeyframe(SpecialMoonPhase.BLUE_MOON.startTick() +12000, 1F);
//                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() - 1000, 1F);
//                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick(), 0.02F);
//                    track.addKeyframe(SpecialMoonPhase.TURBID_MOON.startTick() +12000, 1F);
//        });
//        context.register(SPECIAL_MOON, specialMoonPhases.build());
    }
}
