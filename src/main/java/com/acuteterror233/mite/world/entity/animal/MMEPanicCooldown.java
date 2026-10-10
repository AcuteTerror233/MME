package com.acuteterror233.mite.world.entity.animal;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Duck interface implemented by {@code Animal} (via mixin) exposing the extended panic
 * state used by the MME panic system.
 *
 * <p>While the forced-panic deadline is in the future, {@code PanicGoal#shouldPanic}
 * (mixin-injected) keeps returning {@code true}, so the animal keeps fleeing until the
 * deadline expires instead of stopping after its first random flight path. The recorded
 * panic source position steers flight away from the threat.</p>
 */
public interface MMEPanicCooldown {
    /** Extended panic duration: one minute. */
    int FORCED_PANIC_DURATION_TICKS = 1200;

    /** @return the entity tick until which this animal is forced into panic. */
    int mme$getForcedPanicUntil();

    /**
     * @param untilTick the entity tick until which this animal is forced into panic;
     *                   values in the past disable the forced panic.
     */
    void mme$setForcedPanicUntil(int untilTick);

    /** @return the position of the threat this animal is fleeing from, or {@code null} for vanilla random flight. */
    @Nullable
    Vec3 mme$getPanicSourcePos();

    /**
     * @param pos the position of the threat this animal flees from; {@code null} falls
     *            back to vanilla random flight direction.
     */
    void mme$setPanicSourcePos(@Nullable Vec3 pos);
}
