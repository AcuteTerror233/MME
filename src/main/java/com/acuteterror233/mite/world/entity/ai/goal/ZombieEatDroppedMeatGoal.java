package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

/**
 * AI goal that makes a {@link Zombie} seek nearby dropped meat ({@code #minecraft:meat}),
 * walk to the closest stack and devour it whole in a single bite — every item in the
 * stack is consumed and the item entity is discarded.
 *
 * <p>Behavior rules:</p>
 * <ul>
 *   <li>A candidate is any alive {@link ItemEntity} whose stack is tagged
 *       {@code #minecraft:meat} within {@link #SEEK_RANGE} blocks.</li>
 *   <li>Attack-related behavior outranks eating: the hunt goal (priority 2) preempts
 *       this goal (priority 5), so a zombie chasing a player never stops to graze.</li>
 *   <li>After devouring a stack the zombie waits {@link #EAT_COOLDOWN} ticks before
 *       seeking more meat.</li>
 * </ul>
 */
public class ZombieEatDroppedMeatGoal extends Goal {
    /** Radius (blocks) around the zombie in which dropped meat is detected. */
    private static final double SEEK_RANGE = 10.0D;
    /** Squared distance considered close enough to devour the targeted item. */
    private static final double EAT_DISTANCE_SQR = 1.25D * 1.25D;
    /** Total ticks a chase may last before the zombie gives up on the target. */
    private static final int GIVE_UP_TICKS = 600;
    /** Interval (ticks) between path recalculations when navigation stalls. */
    private static final int REPATH_INTERVAL = 20;
    /** Ticks between meat searches while the goal is idle. */
    private static final int RECHECK_INTERVAL = 10;
    /** Cooldown (ticks) after devouring a stack before seeking more meat. */
    private static final int EAT_COOLDOWN = 40;

    private final Zombie zombie;
    private final double speedModifier;
    @Nullable
    private ItemEntity targetItem;
    private int pursuitTicks;
    private int scanCooldown;
    private int eatCooldown;

    /**
     * @param zombie        the zombie that will hunt for dropped meat
     * @param speedModifier movement speed multiplier while pathing to the meat
     */
    public ZombieEatDroppedMeatGoal(Zombie zombie, double speedModifier) {
        this.zombie = zombie;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /** Runs only on the server, after the eat cooldown, when dropped meat exists nearby. */
    @Override
    public boolean canUse() {
        if (this.zombie.level().isClientSide()) {
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
        this.targetItem = this.findNearestMeat();
        return this.targetItem != null;
    }

    /** Continues while the target is still an alive dropped meat stack. */
    @Override
    public boolean canContinueToUse() {
        return this.targetItem != null
                && this.targetItem.isAlive()
                && this.targetItem.getItem().is(ItemTags.MEAT);
    }

    @Override
    public void start() {
        this.pursuitTicks = 0;
        this.zombie.getNavigation().moveTo(this.targetItem, this.speedModifier);
    }

    @Override
    public void stop() {
        this.zombie.getNavigation().stop();
        this.targetItem = null;
    }

    /** Faces the target, devours on arrival, re-paths on stalls, and gives up after {@link #GIVE_UP_TICKS}. */
    @Override
    public void tick() {
        if (this.targetItem == null) {
            return;
        }
        this.zombie.getLookControl().setLookAt(this.targetItem, 30.0F, 30.0F);
        if (this.zombie.distanceToSqr(this.targetItem) < EAT_DISTANCE_SQR) {
            this.devour();
            return;
        }
        if (++this.pursuitTicks > GIVE_UP_TICKS) {
            this.targetItem = null;
            return;
        }
        if (this.pursuitTicks % REPATH_INTERVAL == 0 && this.zombie.getNavigation().isDone()) {
            this.zombie.getNavigation().moveTo(this.targetItem, this.speedModifier);
        }
    }

    /**
     * Devours the entire dropped stack in one bite — the item entity (with all its
     * items) is discarded. The zombie heals by the food's nutrition value; a short
     * cooldown follows.
     */
    private void devour() {
        ItemStack stack = this.targetItem.getItem();
        FoodProperties food = stack.get(DataComponents.FOOD);
        this.targetItem.discard();
        if (food != null && food.nutrition() > 0) {
            this.zombie.heal((float) food.nutrition());
        }
        this.zombie.playSound(SoundEvents.GENERIC_EAT.value(), 0.6F, this.zombie.getRandom().nextFloat() * 0.4F + 0.8F);
        this.zombie.gameEvent(GameEvent.EAT);
        this.targetItem = null;
        this.eatCooldown = EAT_COOLDOWN;
    }

    /** @return the closest alive dropped meat stack within {@link #SEEK_RANGE}, or {@code null}. */
    @Nullable
    private ItemEntity findNearestMeat() {
        List<ItemEntity> items = this.zombie.level().getEntitiesOfClass(
                ItemEntity.class,
                this.zombie.getBoundingBox().inflate(SEEK_RANGE),
                item -> item.isAlive() && !item.getItem().isEmpty() && item.getItem().is(ItemTags.MEAT)
        );
        ItemEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (ItemEntity item : items) {
            double distance = this.zombie.distanceToSqr(item);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = item;
            }
        }
        return nearest;
    }
}
