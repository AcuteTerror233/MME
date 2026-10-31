package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

/**
 * A {@link PanicGoal} variant that also panics whenever a player is within 8 blocks, on top
 * of the panic triggers of the superclass. Animals using it flee approaching players instead
 * of waiting to be hit first.
 */
public class PlayerPanicGoal extends PanicGoal {
    /**
     * Creates the goal for one mob.
     *
     * @param mob           the mob that panics
     * @param speedModifier pathfinding speed modifier used while fleeing
     */
    public PlayerPanicGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Also panics while any player is within 8 blocks.</p>
     */
    @Override
    public boolean shouldPanic() {
        return mob.level().getNearestPlayer(mob, 8) != null || super.shouldPanic();
    }
}
