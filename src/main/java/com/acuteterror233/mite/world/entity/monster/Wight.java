package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.world.entity.DamageSourcePredicates;
import com.acuteterror233.mite.world.entity.ai.goal.DestroyTorchGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Wight entity (the white ghoul variant), inherits zombie behavior.
 * Drains 5 experience points per hit on players, is immune to damage from living attackers
 * without a silver or enchanted weapon, spawns without equipment and gnaws out nearby torches.
 */
public class Wight extends Zombie {
    public Wight(EntityType<? extends Wight> entityType, Level level) {
        super(entityType, level);
    }

    /** On melee hit, drains 5 experience points from the struck player. */
    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hurt = super.doHurtTarget(level, target);
        if (hurt && target instanceof ServerPlayer player) {
            player.giveExperiencePoints(-5);
        }
        return hurt;
    }

    /** Damage immunity: damage from living attackers with non-silver, unenchanted weapons is ignored. */
    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if (!DamageSourcePredicates.isSilverWeaponOrEnchanted(damageSource) && damageSource.getEntity() instanceof LivingEntity) {
            return false;
        }
        return super.hurtServer(level, damageSource, amount);
    }

    /** Wights never spawn with default equipment; they also gnaw out nearby torches ({@link DestroyTorchGoal}). */
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {

    }

    /** Adds the torch-destroying goal on top of zombie goals. */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new DestroyTorchGoal(this, 0.8));
    }

    /** @return zombie base attributes with faster movement (0.28). */
    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.28F);
    }
}
