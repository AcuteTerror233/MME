package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.world.entity.ai.goal.MonsterFindPlayerGoal;
import com.acuteterror233.mite.world.entity.ai.goal.SpiderEatDroppedChickenGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Spider} — Extends spider behavior: spiders hunt chickens and devour
 * dropped chicken, and track players without line of sight while it is dark.
 *
 * <p>{@code registerGoals} injection (TAIL) replaces the vanilla sight-gated
 * player-targeting goal (priority 2, the vanilla {@code SpiderTargetGoal}) with a
 * {@link MonsterFindPlayerGoal} that keeps the vanilla darkness gate
 * ({@code getLightLevelDependentMagicValue() < 0.5}) but acquires and tracks players
 * through walls. It also adds a {@link NearestAttackableTargetGoal} targeting
 * {@link Chicken} (target priority 4 — below players/golems/retaliation) and a
 * {@link SpiderEatDroppedChickenGoal} (goal priority 4, tied with the vanilla attack
 * goal which runs first while prey is targeted). Spiders kill chickens, then eat the
 * raw chicken they drop.</p>
 */
@Mixin(Spider.class)
public abstract class SpiderMixin extends Monster {
    protected SpiderMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Replaces the vanilla player-targeting goal (priority 2) with a no-line-of-sight
     * variant that keeps the vanilla darkness gate (bright light disables player
     * acquisition, exactly like {@code SpiderTargetGoal}), and adds the chicken-hunting
     * target goal (priority 4) plus the dropped-chicken devouring goal (priority 4).
     */
    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void registerGoals(CallbackInfo ci) {
        Spider spider = (Spider) (Object) this;
        this.targetSelector.getAvailableGoals().removeIf(wrapped ->
                wrapped.getPriority() == 2 && wrapped.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(2, new MonsterFindPlayerGoal(spider) {
            @Override
            public boolean canUse() {
                return spider.getLightLevelDependentMagicValue() < 0.5F && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new SpiderEatDroppedChickenGoal(spider, 1.0D));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(spider, Chicken.class, true));
    }
}
