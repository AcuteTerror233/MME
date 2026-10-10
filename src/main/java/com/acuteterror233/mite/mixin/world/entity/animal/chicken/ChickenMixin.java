package com.acuteterror233.mite.mixin.world.entity.animal.chicken;

import com.acuteterror233.mite.world.entity.ai.goal.DrinkGoal;
import com.acuteterror233.mite.world.entity.ai.goal.SeekLightGoal;
import com.acuteterror233.mite.world.entity.ai.goal.SicknessCheckGoal;
import com.acuteterror233.mite.world.entity.ai.sickness.AnimalSicknessLogic;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessCap;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessRules;
import com.acuteterror233.mite.world.entity.ai.sickness.SicknessState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Chicken} — thirst, light and crowding sickness needs (no grazing),
 * egg laying suppressed while sick, sickness persistence, and no drops while sick.
 */
@Mixin(Chicken.class)
public abstract class ChickenMixin extends Animal implements SicknessCap {
    /** Per-chicken sickness bookkeeping. */
    @Unique
    private final SicknessState mme$sickness = new SicknessState();

    @Shadow
    public int eggTime;

    protected ChickenMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    public SicknessState mme$sicknessState() {
        return mme$sickness;
    }

    /** Registers the thirst, light-seeking and check goals after vanilla goals. */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void mme$registerSicknessGoals(EntityType<? extends Chicken> type, Level level, CallbackInfo ci) {
        SicknessRules rules = SicknessRules.get();
        this.goalSelector.addGoal(5, new DrinkGoal((Animal) (Object) this, rules));
        this.goalSelector.addGoal(6, new SeekLightGoal((Animal) (Object) this, rules));
        this.goalSelector.addGoal(9, new SicknessCheckGoal((Animal) (Object) this));
    }

    /** Freezes the egg timer while the chicken is sick: resetting it at HEAD keeps the
     *  vanilla {@code --eggTime <= 0} condition from ever firing, without cancelling the
     *  rest of {@code aiStep} (which also drives {@code serverAiStep}, i.e. all AI). */
    @Inject(method = "aiStep", at = @At("HEAD"))
    private void mme$suppressEggLaying(CallbackInfo ci) {
        if (!this.level().isClientSide() && AnimalSicknessLogic.isSick(this)) {
            this.eggTime = 6000;
        }
    }

    /** Persists sickness data alongside the vanilla save data. */
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void mme$saveSickness(ValueOutput output, CallbackInfo ci) {
        mme$sickness.save(output);
    }

    /** Restores sickness data alongside the vanilla save data. */
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void mme$loadSickness(ValueInput input, CallbackInfo ci) {
        mme$sickness.load(input);
    }

    /**
     * A sick animal yields nothing on death; the vanilla loot path is skipped entirely.
     */
    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        if (AnimalSicknessLogic.isSick(this)) {
            return;
        }
        super.dropAllDeathLoot(level, damageSource);
    }
}
