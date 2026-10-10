package com.acuteterror233.mite.mixin.world.entity.animal;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.tag.MMEEntityTypeTags;
import com.acuteterror233.mite.world.entity.ai.goal.SeekDroppedFoodGoal;
import com.acuteterror233.mite.world.entity.animal.MMEPanicCooldown;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Mixin for {@code Animal} — Adds periodic manure production.
 *
 * <p>Every animal gets a manure countdown initialized to a random 24000–36000 ticks at
 * construction. Once it reaches zero, adult animals tagged
 * {@code MMEEntityTypeTags#PRODUCE_MANURE} drop one MANURE item (with an
 * {@code ENTITY_PLACE} game event) and the countdown restarts.</p>
 *
 * <p>Construction also registers {@link SeekDroppedFoodGoal} (priority 4): adults
 * outside their breeding cooldown and not in love seek nearby dropped food they
 * like ({@link Animal#isFood}), eat one item and enter love mode.</p>
 */
@Mixin(Animal.class)
public abstract class AnimalMixin extends AgeableMob implements MMEPanicCooldown {
    /** Ticks remaining until the next manure drop (random 24000–36000). */
    @Unique
    public int manureTime;

    /** Entity tick until which this animal is forced into panic (0 = none). */
    @Unique
    private int mme$forcedPanicUntil;

    /** Entity tick of the next predator-sensing scan (throttles entity lookups). */
    @Unique
    private int mme$nextPredatorScanTick;

    /** Position of the threat this animal is fleeing from ({@code null} = vanilla random flight). */
    @Unique
    private Vec3 mme$panicSourcePos;

    /** Radius (blocks) in which a predator that preys on this animal is sensed. */
    @Unique
    private static final double PREDATOR_SENSE_RANGE = 16.0D;

    /** Ticks between predator-sensing scans. */
    @Unique
    private static final int PREDATOR_SCAN_INTERVAL_TICKS = 40;

    protected AnimalMixin(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
    }

    /** Forced-panic deadline used by {@code PanicGoalMixin}. */
    @Override
    public int mme$getForcedPanicUntil() {
        return this.mme$forcedPanicUntil;
    }

    /** Forced-panic deadline used by {@code PanicGoalMixin}. */
    @Override
    public void mme$setForcedPanicUntil(int untilTick) {
        this.mme$forcedPanicUntil = untilTick;
    }

    /** Panic source position used to steer flight away from the threat. */
    @Override
    public Vec3 mme$getPanicSourcePos() {
        return this.mme$panicSourcePos;
    }

    /** Panic source position used to steer flight away from the threat. */
    @Override
    public void mme$setPanicSourcePos(Vec3 pos) {
        this.mme$panicSourcePos = pos;
    }

    /** Initializes the manure countdown at construction. */
    @Inject(method = "<init>", at = @At("TAIL"))
    protected void init(EntityType<? extends AgeableMob> entityType, Level level, CallbackInfo ci) {
        this.manureTime = this.random.nextInt(12000) + 24000;
        this.goalSelector.addGoal(4, new SeekDroppedFoodGoal((Animal) (Object) this, 1.0D));
    }

    /**
     * Counts down each tick on the server; adult {@code PRODUCE_MANURE} animals drop
     * one manure when the timer expires and the countdown restarts. Also senses nearby
     * predators: animals that spot a monster which preys on them are forced into panic.
     */
    @Inject(method = "aiStep", at = @At("TAIL"))
    protected void aiStep(CallbackInfo ci) {
        if (this.level() instanceof ServerLevel serverLevel && this.getType().builtInRegistryHolder().is(MMEEntityTypeTags.PRODUCE_MANURE) && this.isAlive() && !this.isBaby() && --this.manureTime <= 0) {
            this.spawnAtLocation(serverLevel, MMEItems.MANURE);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.manureTime = this.random.nextInt(12000) + 24000;
        }
        if (!this.level().isClientSide() && this.isAlive() && this.tickCount >= this.mme$nextPredatorScanTick) {
            this.mme$nextPredatorScanTick = this.tickCount + PREDATOR_SCAN_INTERVAL_TICKS;
            List<Mob> predators = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(PREDATOR_SENSE_RANGE), this::mme$isPredatorOf);
            if (!predators.isEmpty()) {
                Mob nearest = predators.get(0);
                double nearestDistance = nearest.distanceToSqr(this);
                for (Mob predator : predators) {
                    double distance = predator.distanceToSqr(this);
                    if (distance < nearestDistance) {
                        nearestDistance = distance;
                        nearest = predator;
                    }
                }
                this.mme$setForcedPanicUntil(this.tickCount + MMEPanicCooldown.FORCED_PANIC_DURATION_TICKS);
                this.mme$setPanicSourcePos(nearest.position());
            }
        }
    }

    /**
     * @return whether the mob preys on this animal: MME zombies hunt every animal;
     *         chickens are also hunted by spiders, foxes and ocelots; rabbits also by foxes.
     */
    @Unique
    private boolean mme$isPredatorOf(Mob predator) {
        if (predator instanceof Zombie) {
            return true;
        }
        if ((Object) this instanceof Chicken) {
            return predator instanceof Spider || predator instanceof Fox || predator instanceof Ocelot;
        }
        if ((Object) this instanceof Rabbit) {
            return predator instanceof Fox;
        }
        return false;
    }
}
