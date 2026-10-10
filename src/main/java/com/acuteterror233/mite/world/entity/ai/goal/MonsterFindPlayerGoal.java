package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

/**
 * A {@link NearestAttackableTargetGoal} that lets a {@link Monster} acquire and keep a
 * player target without line of sight.
 *
 * <p>Mechanism: the vanilla player-targeting goal requires sensing the player before
 * acquisition ({@code TargetingConditions.checkLineOfSight}) and drops the target after
 * 60 ticks without sight ({@code TargetGoal.mustSee}). This goal reuses the same
 * nearest-player search but calls {@link TargetingConditions#ignoreLineOfSight()} on this
 * goal's conditions object so acquisition passes through walls, and passes
 * {@code mustSee = false} so the target is kept as long as it stays alive within follow
 * range. Applied to zombies (paired with {@code ZombieDigGoal} tunneling), skeletons,
 * creepers and spiders.</p>
 */
public class MonsterFindPlayerGoal extends NearestAttackableTargetGoal<Player> {
    /**
     * Creates the goal with plain no-line-of-sight player targeting.
     *
     * @param monster the owning monster
     */
    public MonsterFindPlayerGoal(Monster monster) {
        super(monster, Player.class, false);
        this.targetConditions.ignoreLineOfSight();
    }
}
