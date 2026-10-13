package com.acuteterror233.mite.mixin.world.entity.animal.fish;

import com.acuteterror233.mite.world.entity.animal.MMEPanicCooldown;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

/**
 * Mixin for {@code WaterAnimal} — Removes the experience reward for killing water animals and
 * attaches the {@link MMEPanicCooldown} duck state, letting {@code FishPanicGoal} run the MME
 * panic system (forced-panic deadline + threat source position) on every fish.
 */
@Mixin(WaterAnimal.class)
public abstract class WaterAnimalMixin implements MMEPanicCooldown {

    /** Entity tick until which this fish is forced into panic (0 = none). */
    @Unique
    private int mme$forcedPanicUntil;

    /** Position of the threat this fish is fleeing from ({@code null} = vanilla random flight). */
    @Unique
    private Vec3 mme$panicSourcePos;

    /** Always returns zero: water animals drop no experience. */
    @Overwrite
    protected int getBaseExperienceReward(final ServerLevel level) {
        return 0;
    }

    /** Forced-panic deadline used by {@code FishPanicGoal}. */
    @Override
    public int mme$getForcedPanicUntil() {
        return this.mme$forcedPanicUntil;
    }

    /** Forced-panic deadline used by {@code FishPanicGoal}. */
    @Override
    public void mme$setForcedPanicUntil(int untilTick) {
        this.mme$forcedPanicUntil = untilTick;
    }

    /** Panic source position used to steer flight away from the threat. */
    @Override
    public @Nullable Vec3 mme$getPanicSourcePos() {
        return this.mme$panicSourcePos;
    }

    /** Panic source position used to steer flight away from the threat. */
    @Override
    public void mme$setPanicSourcePos(@Nullable Vec3 pos) {
        this.mme$panicSourcePos = pos;
    }
}
