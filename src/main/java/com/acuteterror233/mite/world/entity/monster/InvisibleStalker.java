package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.world.entity.DamageSourcePredicates;
import com.acuteterror233.mite.world.entity.ai.goal.DestroyTorchGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

/**
 * Invisible stalker entity, inherits zombie behavior.
 * Can destroy torches, completely silent (no ambient, hurt, or death sounds).
 */
public class InvisibleStalker extends Zombie {
    public InvisibleStalker(EntityType<? extends InvisibleStalker> entityType, Level level) {
        super(entityType, level);
    }

    /** Adds the torch-destroying goal on top of zombie goals. */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new DestroyTorchGoal(this, 0.8));
    }

    /** Invisible stalkers never spawn with default equipment. */
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {

    }

    /** @return zombie base attributes with slightly faster movement (0.22). */
    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.22F);
    }

    /** Fully silent: no ambient sound. */
    @Override
    protected @NotNull SoundEvent getAmbientSound() {
        return SoundEvents.EMPTY;
    }

    /** Fully silent: no hurt sound. */
    @Override
    protected @NotNull SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.EMPTY;
    }

    /** Fully silent: no death sound. */
    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.EMPTY;
    }

    /** Damage immunity: melee attacks from living attackers with non-silver, unenchanted weapons are ignored. */
    @Override
    public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource damageSource, float amount) {
        if (!DamageSourcePredicates.isSilverWeaponOrEnchanted(damageSource) && damageSource.getEntity() instanceof LivingEntity) {
            return false;
        }
        return super.hurtServer(level, damageSource, amount);
    }
}
