package com.acuteterror233.mite.world.level;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum SpecialMoonPhase implements StringRepresentable {
    NORMAL_MOON(0, 0, "normal_moon"),
    STAR_MOON(1, 16, "star_moon"),
    HARVEST_MOON(2, 40, "harvest_moon"),
    BLOOD_MOON(3, 48, "blood_moon"),
    PHANTOM_MOON(4, 80, "phantom_moon"),
    BLUE_MOON(5, 88, "blue_moon"),
    HUNT_MOON(6, 120, "hunt_moon"),
    FROST_MOON(7, 152, "frost_moon"),
    TURBID_MOON(8, 184, "turbid_moon");


    public static final Codec<SpecialMoonPhase> CODEC = StringRepresentable.fromEnum(SpecialMoonPhase::values);
    public static final int PHASE_LENGTH = 24000;
    private final int index;
    private final int startTick;
    private final String name;

    SpecialMoonPhase(final int index, final int startTick, final String name) {
        this.index = index;
        this.startTick = startTick;
        this.name = name;
    }

    public int index() {
        return this.index;
    }

    public int startTick() {
        return this.startTick * PHASE_LENGTH -12000;
    }

    @Override
    public @NonNull String getSerializedName() {
        return name;
    }
}
