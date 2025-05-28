package com.acuteterror233.mite.mixin.entity.player;

import com.acuteterror233.mite.atinterface.HungerManagerExtension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow public int experienceLevel;
    @Shadow protected HungerManager hungerManager;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public int getNextLevelExperience() {
        int ret;
        if (this.experienceLevel >= 30) {
            ret = 112 + (this.experienceLevel - 30) * 9;
        } else {
            ret = this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2;
        }
        return ret * 2;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (this.experienceLevel > 35) {
            setMax(20);
        } else if (this.experienceLevel > 30) {
            setMax(18);
        } else if (this.experienceLevel > 25) {
            setMax(16);
        } else if (this.experienceLevel > 20) {
            setMax(14);
        } else if (this.experienceLevel > 15) {
            setMax(12);
        } else if (this.experienceLevel > 10) {
            setMax(10);
        } else if (this.experienceLevel > 5) {
            setMax(8);
        } else {
            setMax(6);
        }
    }
    @Unique
    public void setMax(int max) {
        getAttributes().getCustomInstance(EntityAttributes.MAX_HEALTH).setBaseValue(max);
        ((HungerManagerExtension) hungerManager).setMaxFoodLevel(max);
    }
    @Overwrite
    public static DefaultAttributeContainer.Builder createPlayerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1F)
                .add(EntityAttributes.ATTACK_SPEED)
                .add(EntityAttributes.LUCK)
                .add(EntityAttributes.BLOCK_INTERACTION_RANGE, 2.5)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.0)
                .add(EntityAttributes.BLOCK_BREAK_SPEED)
                .add(EntityAttributes.SUBMERGED_MINING_SPEED)
                .add(EntityAttributes.SNEAKING_SPEED)
                .add(EntityAttributes.MINING_EFFICIENCY)
                .add(EntityAttributes.SWEEPING_DAMAGE_RATIO)
                .add(EntityAttributes.MAX_HEALTH, 6);
    }
}
