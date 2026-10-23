package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class PlayerPanicGoal extends PanicGoal {
    public PlayerPanicGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    @Override
    public boolean shouldPanic() {
        return mob.level().getNearestPlayer(mob, 8) != null || super.shouldPanic();
    }
}
