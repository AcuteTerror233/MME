package com.acuteterror233.mite.interfaces;

import net.minecraft.world.phys.Vec3;

/**
 * Duck interface implemented on squid entities by {@code SquidMixin} to expose the private
 * vanilla propulsion vector to {@code SquidHuntGoal}: {@code Squid.aiStep} applies this vector
 * as the squid's {@code deltaMovement} during its jet phase, exactly like the vanilla
 * random-movement and flee goals do.
 */
public interface SquidExtension {
    /**
     * Overwrites the squid's propulsion vector; pass {@link Vec3#ZERO} to stop steering and
     * let the vanilla idle goals take over again.
     *
     * @param movement the desired movement vector in blocks per tick
     */
    void mme$setHuntMovement(Vec3 movement);
}
