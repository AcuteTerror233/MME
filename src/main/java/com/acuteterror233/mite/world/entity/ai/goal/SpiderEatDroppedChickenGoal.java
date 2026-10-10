package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

/**
 * AI goal that makes a {@link Spider} seek nearby dropped chicken (raw or cooked),
 * walk to the closest stack and devour it whole in a single bite — every item in the
 * stack is consumed and the item entity is discarded.
 *
 * <p>Behavior rules:</p>
 * <ul>
 *   <li>A candidate is any alive {@link ItemEntity} holding {@link Items#CHICKEN} or
 *       {@link Items#COOKED_CHICKEN} within {@link #SEEK_RANGE} blocks.</li>
 *   <li>Attack-related behavior outranks eating: the vanilla attack goal (same
 *       priority 4, registered first) preempts this goal, so a spider hunting prey
 *       never stops to graze.</li>
 *   <li>After devouring a stack the spider waits {@link #EAT_COOLDOWN} ticks before
 *       seeking more chicken.</li>
 * </ul>
 */
public class SpiderEatDroppedChickenGoal extends Goal {
    /** Radius (blocks) around the spider in which dropped chicken is detected. */
    private static final double SEEK_RANGE = 10.0D;
    /** Squared distance considered close enough to devour the targeted item. */
    private static final double EAT_DISTANCE_SQR = 1.25D * 1.25D;
    /** Total ticks a chase may last before the spider gives up on the target. */
    private static final int GIVE_UP_TICKS = 600;
    /** Interval (ticks) between path recalculations when navigation stalls. */
    private static final int REPATH_INTERVAL = 20;
    /** Ticks between chicken searches while the goal is idle. */
    private static final int RECHECK_INTERVAL = 10;
    /** Cooldown (ticks) after devouring a stack before seeking more chicken. */
    private static final int EAT_COOLDOWN = 40;

    private final Spider spider;
    private final double speedModifier;
    @Nullable
    private ItemEntity targetItem;
    private int pursuitTicks;
    private int scanCooldown;
    private int eatCooldown;

    /**
     * @param spider        the spider that will hunt for dropped chicken
     * @param speedModifier movement speed multiplier while pathing to the chicken
     */
    public SpiderEatDroppedChickenGoal(Spider spider, double speedModifier) {
        this.spider = spider;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /** Runs only on the server, after the eat cooldown, when dropped chicken exists nearby. */
    @Override
    public boolean canUse() {
        if (this.spider.level().isClientSide()) {
            return false;
        }
        if (this.eatCooldown > 0) {
            this.eatCooldown--;
            return false;
        }
        if (this.scanCooldown > 0) {
            this.scanCooldown--;
            return false;
        }
        this.scanCooldown = RECHECK_INTERVAL;
        this.targetItem = this.findNearestChicken();
        return this.targetItem != null;
    }

    /** Continues while the target is still an alive dropped chicken stack. */
    @Override
    public boolean canContinueToUse() {
        return this.targetItem != null
                && this.targetItem.isAlive()
                && this.isChickenItem(this.targetItem.getItem());
    }

    @Override
    public void start() {
        this.pursuitTicks = 0;
        this.spider.getNavigation().moveTo(this.targetItem, this.speedModifier);
    }

    @Override
    public void stop() {
        this.spider.getNavigation().stop();
        this.targetItem = null;
    }

    /** Faces the target, devours on arrival, re-paths on stalls, and gives up after {@link #GIVE_UP_TICKS}. */
    @Override
    public void tick() {
        if (this.targetItem == null) {
            return;
        }
        this.spider.getLookControl().setLookAt(this.targetItem, 30.0F, 30.0F);
        if (this.spider.distanceToSqr(this.targetItem) < EAT_DISTANCE_SQR) {
            this.devour();
            return;
        }
        if (++this.pursuitTicks > GIVE_UP_TICKS) {
            this.targetItem = null;
            return;
        }
        if (this.pursuitTicks % REPATH_INTERVAL == 0 && this.spider.getNavigation().isDone()) {
            this.spider.getNavigation().moveTo(this.targetItem, this.speedModifier);
        }
    }

    /**
     * Devours the entire dropped stack in one bite — the item entity (with all its
     * items) is discarded. Eating grants no effects; a short cooldown follows.
     */
    private void devour() {
        this.targetItem.discard();
        this.spider.playSound(SoundEvents.GENERIC_EAT.value(), 0.6F, this.spider.getRandom().nextFloat() * 0.4F + 0.8F);
        this.spider.gameEvent(GameEvent.EAT);
        this.targetItem = null;
        this.eatCooldown = EAT_COOLDOWN;
    }

    /** @return whether the stack is raw or cooked chicken. */
    private boolean isChickenItem(ItemStack stack) {
        return stack.is(Items.CHICKEN) || stack.is(Items.COOKED_CHICKEN);
    }

    /** @return the closest alive dropped chicken stack within {@link #SEEK_RANGE}, or {@code null}. */
    @Nullable
    private ItemEntity findNearestChicken() {
        List<ItemEntity> items = this.spider.level().getEntitiesOfClass(
                ItemEntity.class,
                this.spider.getBoundingBox().inflate(SEEK_RANGE),
                item -> item.isAlive() && !item.getItem().isEmpty() && this.isChickenItem(item.getItem())
        );
        ItemEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (ItemEntity item : items) {
            double distance = this.spider.distanceToSqr(item);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = item;
            }
        }
        return nearest;
    }
}
