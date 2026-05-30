package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.world.entity.ai.goal.DestroyTorchGoal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * 隐身追踪者实体，继承僵尸行为。
 * 可破坏火把，完全静音（无声环境音、受伤声、死亡声）。
 */
public class InvisibleStalker extends Zombie {
    public InvisibleStalker(EntityType<? extends InvisibleStalker> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new DestroyTorchGoal(this, 0.8));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {

    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.22F);
    }

    @Override
    protected @NotNull SoundEvent getAmbientSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.EMPTY;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.EMPTY;
    }
}
