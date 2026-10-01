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

public interface MMETimelines {
    ResourceKey<Timeline> SPECIAL_MOON = key("special_moon");
    static ResourceKey<Timeline> key(String string) {
        return ResourceKey.create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }

    static void bootstrap(BootstrapContext<Timeline> context) {
        HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
        Holder.Reference<WorldClock> overworldClock = clocks.getOrThrow(WorldClocks.OVERWORLD);
        
        Timeline.Builder specialMoonPhases = Timeline.builder(overworldClock).setPeriodTicks(24000 * 192).addTrack(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE,track -> {
            for (SpecialMoonPhase phase : SpecialMoonPhase.values()) {
               if(phase.index() == 0){
                   continue;
               }
               track.addKeyframe(phase.startTick(), phase);
               track.addKeyframe(phase.startTick() +12000, SpecialMoonPhase.NORMAL_MOON);
            }
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
