package com.acuteterror233.mite.mixin.world.entity.animal.fish;

import com.acuteterror233.mite.world.entity.ai.goal.FishPanicGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code AbstractFish} — Multiplies the swimming speed of every fish (cod, salmon,
 * pufferfish, tropical fish, tadpole) by 8.
 *
 * <p>{@code AbstractFish.travelInWater} hardcodes a {@code 0.01} {@code moveRelative}
 * acceleration and ignores the movement-speed attribute underwater, so that constant is the
 * single source of truth for fish swim speed: scaling it by 8 scales the terminal water
 * speed (accel * drag / (1 - drag)) by exactly 8 for every {@link AbstractFish} subclass.</p>
 *
 * <p>It also replaces the vanilla {@code PanicGoal} registered at priority 0 with the MME
 * {@link FishPanicGoal} at the same priority, so the MME panic system (forced-panic deadline +
 * threat source position) fully owns fish panic.</p>
 */
@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends Mob {

    protected AbstractFishMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    /** Swim-speed multiplier applied to the hardcoded water-travel acceleration. */
    private static final float SWIM_SPEED_MULTIPLIER = 8.0F;

    /** Scales the hardcoded {@code moveRelative} acceleration of {@code travelInWater} by 8x. */
    @ModifyConstant(method = "travelInWater", constant = @Constant(floatValue = 0.01F))
    private float mme$scaleSwimAccel(float speed) {
        return speed * SWIM_SPEED_MULTIPLIER;
    }

    /**
     * Replaces the vanilla {@code PanicGoal} (registered by {@code AbstractFish.registerGoals}
     * at priority 0, which wins the MOVE flag and blocks the MME goal's panic broadcast) with
     * the {@link FishPanicGoal} at priority 0: the MME goal fully owns fish panic behavior.
     */
    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void mme$addFishPanicGoal(CallbackInfo ci) {
        this.goalSelector.removeAllGoals(goal -> goal instanceof PanicGoal);
        this.goalSelector.addGoal(0, new FishPanicGoal((AbstractFish) (Object) this));
    }
}
