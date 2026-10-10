package com.acuteterror233.mite.mixin.world.entity.animal.squid;

import com.acuteterror233.mite.interfaces.SquidExtension;
import com.acuteterror233.mite.world.entity.ai.goal.SquidHuntGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AgeableWaterCreature;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Gives vanilla squids two attack targets: players (no-damage grab applying Slowness V, then a
 * 30 s per-squid no-player cooldown) and boats (latched, horizontally braked and bitten until
 * they break). See {@link SquidHuntGoal} for the behaviour details. Additionally boosts the
 * vanilla jet propulsion so squids swim noticeably faster (idle drift and hunts alike).
 */
@Mixin(Squid.class)
public abstract class SquidMixin extends AgeableWaterCreature implements SquidExtension {

    /** Multiplier applied to the propulsion vector during the squid's water jet phase. */
    @Unique
    private static final double JET_SPEED_FACTOR = 1.6D;

    protected SquidMixin(EntityType<? extends AgeableWaterCreature> entityType, Level level) {
        super(entityType, level);
    }

    /** Vanilla private propulsion vector; {@code aiStep} applies it during the squid's jet phase. */
    @Shadow
    private Vec3 movementVector;

    @Unique
    @Override
    public void mme$setHuntMovement(Vec3 movement) {
        this.movementVector = movement;
    }

    /**
     * Registers the hunt goal at priority 0, ahead of the vanilla random-movement goal (same
     * priority, registered later), so the hunt wins the MOVE flag while it is active and the
     * idle behaviour resumes untouched once it ends.
     */
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void mme$addHuntGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(0, new SquidHuntGoal((Squid) (Object) this));
    }

    /**
     * Boosts the water jet: in {@code aiStep} the first {@code setDeltaMovement(Vec3)} call is
     * the jet phase writing the raw propulsion vector (the second one is the 0.9 recovery drag),
     * so scaling its argument by {@value #JET_SPEED_FACTOR} speeds up every jet — the vanilla
     * idle drift, the squirt boost and the hunt chase alike.
     */
    @ModifyArg(method = "aiStep", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/animal/squid/Squid;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V",
            ordinal = 0))
    private Vec3 mme$boostJetSpeed(Vec3 movement) {
        return movement.scale(JET_SPEED_FACTOR);
    }
}
