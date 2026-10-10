package com.acuteterror233.mite.world.entity.ai.goal;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

/**
 * AI goal that makes an {@link Animal} seek nearby dropped food items, walk to the
 * closest one and eat exactly one item from it. Eating puts the animal into love mode
 * (vanilla breeding), exactly as if a player had hand-fed it the same item.
 *
 * <p>Behavior rules:</p>
 * <ul>
 *   <li>A candidate is any alive {@link ItemEntity} whose stack the animal considers
 *       food ({@link Animal#isFood}) within {@link #SEEK_RANGE} blocks.</li>
 *   <li>Only adults outside their breeding cooldown seek food, and animals already in
 *       the love phase never seek food ({@code getAge() != 0 || isInLove()} blocks
 *       both {@link #canUse} and {@link #canContinueToUse}).</li>
 *   <li>Each eat consumes exactly one item from the dropped stack; the item entity is
 *       discarded only when its stack empties.</li>
 * </ul>
 */
public class SeekDroppedFoodGoal extends Goal {
    /** Radius (blocks) around the animal in which dropped food is detected. */
    private static final double SEEK_RANGE = 10.0D;
    /** Squared distance considered close enough to eat the targeted item. */
    private static final double EAT_DISTANCE_SQR = 1.25D * 1.25D;
    /** Total ticks a chase may last before the animal gives up on the target. */
    private static final int GIVE_UP_TICKS = 600;
    /** Interval (ticks) between path recalculations when navigation stalls. */
    private static final int REPATH_INTERVAL = 20;
    /** Ticks between food searches while the goal is idle. */
    private static final int RECHECK_INTERVAL = 10;

    private final Animal animal;
    private final double speedModifier;
    @Nullable
    private ItemEntity targetItem;
    private int pursuitTicks;
    private int scanCooldown;

    /**
     * @param animal        the animal that will hunt for dropped food
     * @param speedModifier movement speed multiplier while pathing to the food
     */
    public SeekDroppedFoodGoal(Animal animal, double speedModifier) {
        this.animal = animal;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Runs only on the server, for adults outside their breeding cooldown that are not
     * already in love, when dropped food the animal likes exists nearby.
     */
    @Override
    public boolean canUse() {
        if (this.animal.level().isClientSide()
                || this.animal.getAge() != 0
                || this.animal.isInLove()) {
            return false;
        }
        if (this.scanCooldown > 0) {
            this.scanCooldown--;
            return false;
        }
        this.scanCooldown = RECHECK_INTERVAL;
        this.targetItem = this.findNearestFood();
        return this.targetItem != null;
    }

    /** Continues while the target is alive, still food, and the animal is still eligible to breed. */
    @Override
    public boolean canContinueToUse() {
        return this.targetItem != null
                && this.targetItem.isAlive()
                && this.animal.getAge() == 0
                && !this.animal.isInLove()
                && this.animal.isFood(this.targetItem.getItem());
    }

    @Override
    public void start() {
        this.pursuitTicks = 0;
        this.animal.getNavigation().moveTo(this.targetItem, this.speedModifier);
    }

    @Override
    public void stop() {
        this.animal.getNavigation().stop();
        this.targetItem = null;
    }

    /** Faces the target, eats on arrival, re-paths on stalls, and gives up after {@link #GIVE_UP_TICKS}. */
    @Override
    public void tick() {
        if (this.targetItem == null) {
            return;
        }
        this.animal.getLookControl().setLookAt(this.targetItem, 30.0F, 30.0F);
        if (this.animal.distanceToSqr(this.targetItem) < EAT_DISTANCE_SQR) {
            this.eat();
            return;
        }
        if (++this.pursuitTicks > GIVE_UP_TICKS) {
            this.targetItem = null;
            return;
        }
        if (this.pursuitTicks % REPATH_INTERVAL == 0 && this.animal.getNavigation().isDone()) {
            this.animal.getNavigation().moveTo(this.targetItem, this.speedModifier);
        }
    }

    /**
     * Consumes exactly one item from the dropped stack, then enters love mode like a
     * player feeding ({@code setInLove(null)} — no love cause is recorded). The item
     * entity is discarded only when the stack empties.
     */
    private void eat() {
        ItemStack stack = this.targetItem.getItem();
        stack.shrink(1);
        if (stack.isEmpty()) {
            this.targetItem.discard();
        }
        this.animal.playSound(SoundEvents.GENERIC_EAT.value(), 0.6F, this.animal.getRandom().nextFloat() * 0.4F + 0.8F);
        this.animal.gameEvent(GameEvent.EAT);
        this.animal.setInLove(null);
        this.targetItem = null;
    }

    /** @return the closest alive dropped item within {@link #SEEK_RANGE} the animal considers food, or {@code null}. */
    @Nullable
    private ItemEntity findNearestFood() {
        List<ItemEntity> items = this.animal.level().getEntitiesOfClass(
                ItemEntity.class,
                this.animal.getBoundingBox().inflate(SEEK_RANGE),
                item -> item.isAlive() && !item.getItem().isEmpty() && this.animal.isFood(item.getItem())
        );
        ItemEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (ItemEntity item : items) {
            double distance = this.animal.distanceToSqr(item);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = item;
            }
        }
        return nearest;
    }
}
