package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.world.entity.ai.goal.MonsterFindPlayerGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Creeper} — Widens the creeper explosion radius field for runtime
 * modification and gives creepers no-line-of-sight player tracking.
 *
 * <ul>
 *   <li>The {@code explosionRadius} shadow field is marked {@code @Mutable} so other code
 *       can reassign it; the initializer value is documentation only (vanilla default 3).</li>
 *   <li>{@code registerGoals} injection (TAIL) replaces the vanilla sight-gated
 *       player-targeting goal (the only {@code NearestAttackableTargetGoal} at priority 1)
 *       with {@link MonsterFindPlayerGoal} so creepers acquire and track players without
 *       line of sight.</li>
 * </ul>
 */
@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster {
    /** Shadow of the explosion radius; {@code @Mutable} permits reassignment. */
    @Shadow @Mutable
    public int explosionRadius = 3;

    protected CreeperMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Replaces the vanilla sight-gated player-targeting goal (the only
     * {@code NearestAttackableTargetGoal} at priority 1) with {@link MonsterFindPlayerGoal}
     * so creepers acquire and track players without line of sight.
     */
    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void mme$noSightPlayerTarget(CallbackInfo ci) {
        this.targetSelector.getAvailableGoals().removeIf(wrapped ->
                wrapped.getPriority() == 1 && wrapped.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(1, new MonsterFindPlayerGoal((Creeper) (Object) this));
    }
}
