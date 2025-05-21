package com.acuteterror233.mite.mixin.playerentity;

import com.google.common.math.IntMath;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow public int experienceLevel;
    @Shadow public abstract void addScore(int score);
    @Shadow public float experienceProgress;
    @Shadow public int totalExperience;
    @Shadow private int lastPlayedLevelUpSoundTime;

    @Shadow protected HungerManager hungerManager;

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
    @Overwrite
    /**
     * 增加指定的经验值，并更新经验进度、总经验和等级。
     *
     * @param experience 要增加的经验值，必须为非负整数
     */
    public void addExperience(int experience) {
        this.addScore(experience);
        // 更新经验进度，基于当前等级所需经验进行归一化
        this.experienceProgress = this.experienceProgress + (float)experience / this.getNextLevelExperience();
        // 累加总经验并限制在有效整数范围内
        this.totalExperience = MathHelper.clamp(this.totalExperience + experience, 0, Integer.MAX_VALUE);

        /**
         * 处理经验进度不足0的情况（可能由于扣除经验导致）
         * 逐级降低等级并调整经验进度
         */
        while (this.experienceProgress < 0.0F) {
            float f = this.experienceProgress * this.getNextLevelExperience();
            if (this.experienceLevel > 0) {
                this.addExperienceLevels(-1);
                this.experienceProgress = 1.0F + f / this.getNextLevelExperience();
            } else {
                this.addExperienceLevels(-1);
                this.experienceProgress = 0.0F;
            }
        }

        /**
         * 处理经验进度达到或超过1的情况（升级）
         * 将溢出部分转换为下一级经验并提升等级
         */
        while (this.experienceProgress >= 1.0F) {
            this.experienceProgress = (this.experienceProgress - 1.0F) * this.getNextLevelExperience();
            this.addExperienceLevels(1);
            this.experienceProgress = this.experienceProgress / this.getNextLevelExperience();
        }
    }

    @Overwrite
    public void addExperienceLevels(int levels) {
        this.experienceLevel = IntMath.saturatedAdd(this.experienceLevel, levels);
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experienceProgress = 0.0F;
            this.totalExperience = 0;
        }
        if (levels > 0 && this.experienceLevel % 5 == 0 && this.lastPlayedLevelUpSoundTime < this.age - 100.0F) {
            float f = this.experienceLevel > 30 ? 1.0F : this.experienceLevel / 30.0F;
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), f * 0.75F, 1.0F);
            this.lastPlayedLevelUpSoundTime = this.age;
        }
    }
}
