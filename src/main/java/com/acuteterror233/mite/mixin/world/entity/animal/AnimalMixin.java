package com.acuteterror233.mite.mixin.world.entity.animal;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.tag.MMEEntityTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Animal} — Adds periodic manure production.
 *
 * <p>Every animal gets a manure countdown initialized to a random 24000–36000 ticks at
 * construction. Once it reaches zero, adult animals tagged
 * {@code MMEEntityTypeTags#PRODUCE_MANURE} drop one MANURE item (with an
 * {@code ENTITY_PLACE} game event) and the countdown restarts.</p>
 */
@Mixin(Animal.class)
public abstract class AnimalMixin extends AgeableMob {
    /** Ticks remaining until the next manure drop (random 24000–36000). */
    @Unique
    public int manureTime;

    protected AnimalMixin(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
    }

    /** Initializes the manure countdown at construction. */
    @Inject(method = "<init>", at = @At("TAIL"))
    protected void init(EntityType<? extends AgeableMob> entityType, Level level, CallbackInfo ci) {
        this.manureTime = this.random.nextInt(12000) + 24000;
    }

    /**
     * Counts down each tick on the server; adult {@code PRODUCE_MANURE} animals drop
     * one manure when the timer expires and the countdown restarts.
     */
    @Inject(method = "aiStep", at = @At("TAIL"))
    protected void aiStep(CallbackInfo ci) {
        if (this.level() instanceof ServerLevel serverLevel && this.getType().builtInRegistryHolder().is(MMEEntityTypeTags.PRODUCE_MANURE) && this.isAlive() && !this.isBaby() && --this.manureTime <= 0) {
            this.spawnAtLocation(serverLevel, MMEItems.MANURE);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.manureTime = this.random.nextInt(12000) + 24000;
        }
    }
}
