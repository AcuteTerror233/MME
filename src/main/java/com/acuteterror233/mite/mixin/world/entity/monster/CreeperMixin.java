package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.world.entity.ai.goal.MonsterFindPlayerGoal;
import com.acuteterror233.mite.world.entity.monster.InfernalCreeper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Creeper} — Widens the creeper explosion radius field for runtime
 * modification, gives creepers no-line-of-sight player tracking, and lets infernal creepers
 * ignite blocks with their explosion.
 *
 * <ul>
 *   <li>The {@code explosionRadius} shadow field is marked {@code @Mutable} so other code
 *       can reassign it; the initializer value is documentation only (vanilla default 3).</li>
 *   <li>{@code registerGoals} injection (TAIL) replaces the vanilla sight-gated
 *       player-targeting goal (the only {@code NearestAttackableTargetGoal} at priority 1)
 *       with {@link MonsterFindPlayerGoal} so creepers acquire and track players without
 *       line of sight.</li>
 *   <li>{@code explodeCreeper} redirect: the explosion invocation is re-issued with the
 *       {@code fire} flag set for {@link InfernalCreeper} instances only, so their blast
 *       ignites blocks; vanilla creepers keep the fire-free explosion.</li>
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

    /**
     * Redirects the {@code Level#explode} invocation inside {@code explodeCreeper} — infernal
     * creepers re-issue it with the {@code fire} flag set so the blast ignites surrounding
     * blocks, while every other creeper keeps the vanilla fire-free call.
     */
    @Redirect(
            method = "explodeCreeper",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
            )
    )
    private void mme$infernalCreeperFieryExplosion(ServerLevel level, Entity source, double x, double y, double z, float radius, Level.ExplosionInteraction interaction) {
        if ((Object) this instanceof InfernalCreeper) {
            level.explode(source, x, y, z, radius, true, interaction);
        } else {
            level.explode(source, x, y, z, radius, interaction);
        }
    }
}
