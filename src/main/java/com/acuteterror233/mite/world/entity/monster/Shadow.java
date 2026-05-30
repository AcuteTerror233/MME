package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.acuteterror233.mite.world.entity.DamageSourcePredicates;
import com.acuteterror233.mite.world.entity.ai.goal.DestroyTorchGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 暗影实体，继承僵尸行为。
 * 对银器武器以外伤害免疫。
 */
public class Shadow extends Zombie {
    public Shadow(EntityType<? extends Shadow> entityType, Level level) {
        super(entityType, level);
    }

    private static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hurt = super.doHurtTarget(level, target);
        if (hurt && target instanceof LivingEntity living) {
            float chance = 1.0F;
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                ItemStack armor = living.getItemBySlot(slot);
                if (armor.is(MMEItemTags.SILVER_ARMOR)) {
                    chance -= 0.2F;
                }
            }
            if (chance > 0.0F && this.random.nextFloat() < chance) {
                living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 600, 0), this);
            }
        }
        return hurt;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if (!DamageSourcePredicates.isSilverWeaponOrEnchanted(damageSource) && damageSource.getEntity() instanceof LivingEntity) {
            return false;
        }
        return super.hurtServer(level, damageSource, amount);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new DestroyTorchGoal(this, 0.8));
    }
}
