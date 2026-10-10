package com.acuteterror233.mite.mixin.world.entity.ai.goal;

import com.acuteterror233.mite.world.entity.animal.MMEPanicCooldown;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Mixin for {@code PanicGoal} — Extends panic duration to one minute, spreads panic
 * to nearby animals, and steers flight away from the panic source.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code shouldPanic} injection (HEAD): while the animal's forced-panic deadline
 *       ({@link MMEPanicCooldown}) is in the future, panic is kept active, so the animal
 *       keeps fleeing until the deadline instead of stopping after its first flight path.
 *       The vanilla {@code canUse} loop then seamlessly re-picks escape positions.</li>
 *   <li>{@code findRandomPosition} injection (HEAD): when a panic source position is
 *       recorded, the escape target is picked with {@link LandRandomPos#getPosAway} so the
 *       animal flees away from the threat; if no away-position is reachable the vanilla
 *       random fallback runs.</li>
 *   <li>{@code start} injection (TAIL): on a fresh panic event (deadline already expired)
 *       the deadline is reset to {@link #PANIC_DURATION_TICKS}, the last attacker is
 *       recorded as the panic source, and panic (with the source position) is broadcast to
 *       all not-yet-panicked {@link Animal}s within {@link #SPREAD_RANGE} blocks.</li>
 * </ul>
 *
 * <p>Anti-loop guarantee: broadcast only runs on a fresh panic event, and every broadcast
 * recipient is skipped while already panicked — recipients never re-broadcast (their
 * deadline is in the future, so their own {@code start} hits the early return). Panic can
 * therefore never ping-pong between animals.</p>
 */
@Mixin(PanicGoal.class)
public abstract class PanicGoalMixin {
    /** Extended panic duration: one minute. */
    @Unique
    private static final int PANIC_DURATION_TICKS = 1200;
    /** Radius (blocks) in which a fresh panic is broadcast to other animals. */
    @Unique
    private static final double SPREAD_RANGE = 16.0D;
    /** Horizontal distance of the away-from-source escape target. */
    @Unique
    private static final int FLEE_XZ_RANGE = 16;
    /** Vertical distance of the away-from-source escape target. */
    @Unique
    private static final int FLEE_Y_RANGE = 8;

    @Shadow
    @Final
    protected PathfinderMob mob;

    @Shadow
    protected double posX;

    @Shadow
    protected double posY;

    @Shadow
    protected double posZ;

    /** Keeps vanilla panic active while the MME forced-panic deadline is in the future. */
    @Inject(method = "shouldPanic", at = @At("HEAD"), cancellable = true)
    protected void mme$forcePanic(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof MMEPanicCooldown duck && duck.mme$getForcedPanicUntil() > this.mob.tickCount) {
            cir.setReturnValue(true);
        }
    }

    /**
     * Picks an escape position away from the recorded panic source; without a source the
     * vanilla random position logic runs untouched. If no away-position is reachable the
     * vanilla logic also runs as fallback.
     */
    @Inject(method = "findRandomPosition", at = @At("HEAD"), cancellable = true)
    protected void mme$findAwayPosition(CallbackInfoReturnable<Boolean> cir) {
        if (!(this.mob instanceof MMEPanicCooldown duck)) {
            return;
        }
        Vec3 source = duck.mme$getPanicSourcePos();
        if (source == null) {
            return;
        }
        Vec3 away = LandRandomPos.getPosAway(this.mob, FLEE_XZ_RANGE, FLEE_Y_RANGE, source);
        if (away != null) {
            this.posX = away.x;
            this.posY = away.y;
            this.posZ = away.z;
            cir.setReturnValue(true);
        }
    }

    /**
     * On a fresh panic event, extends the deadline to one minute, records the last
     * attacker as the panic source, and broadcasts panic (with the source position) to
     * nearby not-yet-panicked animals. Starts triggered by an earlier broadcast hit the
     * early return and never re-broadcast.
     */
    @Inject(method = "start", at = @At("TAIL"))
    public void mme$onPanicStart(CallbackInfo ci) {
        if (this.mob.level().isClientSide() || !(this.mob instanceof MMEPanicCooldown duck)) {
            return;
        }
        int now = this.mob.tickCount;
        if (duck.mme$getForcedPanicUntil() > now) {
            return;
        }
        int untilTick = now + PANIC_DURATION_TICKS;
        duck.mme$setForcedPanicUntil(untilTick);
        LivingEntity attacker = this.mob.getLastHurtByMob();
        if (attacker != null && attacker.isAlive()) {
            duck.mme$setPanicSourcePos(attacker.position());
        }
        Vec3 source = duck.mme$getPanicSourcePos();
        if (source == null) {
            return;
        }
        List<Animal> nearby = this.mob.level().getEntitiesOfClass(
                Animal.class,
                this.mob.getBoundingBox().inflate(SPREAD_RANGE),
                animal -> animal.isAlive() && ((MMEPanicCooldown) animal).mme$getForcedPanicUntil() <= now
        );
        for (Animal animal : nearby) {
            MMEPanicCooldown recipient = (MMEPanicCooldown) animal;
            recipient.mme$setForcedPanicUntil(untilTick);
            recipient.mme$setPanicSourcePos(source);
        }
    }
}
