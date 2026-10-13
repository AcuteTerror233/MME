package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

/**
 * A {@link NearestAttackableTargetGoal} that lets a {@link Monster} acquire and keep a
 * player target without line of sight, always attacking whichever player is nearest.
 *
 * <p>Mechanism: the vanilla player-targeting goal requires sensing the player before
 * acquisition ({@code TargetingConditions.checkLineOfSight}) and drops the target after
 * 60 ticks without sight ({@code TargetGoal.mustSee}). This goal reuses the same
 * nearest-player search but calls {@link TargetingConditions#ignoreLineOfSight()} on this
 * goal's conditions object so acquisition passes through walls, and passes
 * {@code mustSee = false} so the target is kept as long as it stays alive within follow
 * range. While a pursuit is running, {@link #canContinueToUse} periodically re-runs the
 * vanilla {@code findTarget} pass and switches the mob's target when it finds a different
 * (necessarily nearest) player — the vanilla behavior chases the first acquired target
 * until it dies, letting a nearer player walk up unchallenged. Applied to zombies (paired
 * with {@code ZombieDigGoal} tunneling), skeletons, creepers and spiders.</p>
 */
public class MonsterFindPlayerGoal extends NearestAttackableTargetGoal<Player> {
    /** Ticks between nearest-player re-evaluations while a pursuit is running. */
    private static final int RETARGET_INTERVAL_TICKS = 20;
    /** Countdown to the next nearest-player re-evaluation. */
    private int retargetTimer;

    /**
     * Creates the goal with plain no-line-of-sight player targeting.
     *
     * @param monster the owning monster
     */
    public MonsterFindPlayerGoal(Monster monster) {
        super(monster, Player.class, false);
        this.targetConditions.ignoreLineOfSight();
    }

    @Override
    public void start() {
        this.retargetTimer = RETARGET_INTERVAL_TICKS;
        super.start();
    }

    /**
     * Continues the pursuit while periodically re-running the nearest-player search: when
     * the re-evaluation finds a different player, the mob's target is switched, so the mob
     * attacks the closest player instead of chasing the first acquired target to death.
     */
    @Override
    public boolean canContinueToUse() {
        if (--this.retargetTimer <= 0) {
            this.retargetTimer = RETARGET_INTERVAL_TICKS;
            this.findTarget();
            LivingEntity found = this.target;
            if (found != null && found.isAlive() && found != this.mob.getTarget()) {
                this.mob.setTarget(found);
            }
        }
        return super.canContinueToUse();
    }
}
