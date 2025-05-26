package com.acuteterror233.mite.mixin;

import com.acuteterror233.mite.newinterface.HungerManagerExtension;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin implements HungerManagerExtension {
    @Unique
    private int MaxFoodLevel = 6;
    @Unique
    private int healTickTimer = 0;
    @Unique
    @Override
    public void setMaxFoodLevel(int maxFoodLevel) {
        MaxFoodLevel = maxFoodLevel;
    }
    @Unique
    @Override
    public int getMaxFoodLevel() {
        return MaxFoodLevel;
    }
    @Shadow
    private int foodLevel;
    @Shadow
    private float saturationLevel;
    @Shadow @Final
    private static int DEFAULT_FOOD_TICK_TIMER;
    @Shadow @Final
    private static float DEFAULT_EXHAUSTION;
    @Shadow
    private float exhaustion;
    @Shadow
    private int foodTickTimer;
    @Shadow
    public abstract void addExhaustion(float exhaustion);
    /**
     * @author
     * @reason
     */
    @Overwrite
    private void addInternal(int nutrition, float saturation) {
        this.foodLevel = MathHelper.clamp(nutrition + this.foodLevel, 0, MaxFoodLevel);
        this.saturationLevel = MathHelper.clamp(saturation + this.saturationLevel, 0.0F, (float)this.foodLevel);
    }
    /**
     * @author
     * @reason
     */
        @Overwrite
        public void update(ServerPlayerEntity player) {
            ServerWorld serverWorld = player.getServerWorld();
            Difficulty difficulty = serverWorld.getDifficulty();

        // 检查并处理食物消耗
        if (this.exhaustion > 4.0F) {
            this.exhaustion -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        // 检查游戏规则是否允许自然恢复
        boolean bl = serverWorld.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        // 根据食物水平、饱和度和游戏规则处理玩家健康恢复
        if (bl && this.saturationLevel > 0.0F && player.canFoodHeal() && this.foodLevel >= MaxFoodLevel) {
            this.foodTickTimer++;
            this.healTickTimer++;
            if (this.foodTickTimer >= 80) {
                float f = Math.min(this.saturationLevel, 6.0F);
                this.addExhaustion(f);
                this.foodTickTimer = 0;
            }
        } else if (bl && this.foodLevel >= MaxFoodLevel - 2  && player.canFoodHeal()) {
            this.foodTickTimer++;
            this.healTickTimer++;
            if (this.foodTickTimer >= 80) {
                this.addExhaustion(6.0F);
                this.foodTickTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            this.foodTickTimer++;
            if (this.foodTickTimer >= 640) {
                player.damage(serverWorld, player.getDamageSources().starve(), 1.0F);
                this.foodTickTimer = 0;
            }
        } else {
            this.foodTickTimer = 0;
            this.healTickTimer = 0;
        }
        if (this.healTickTimer >= 1280){
            player.heal(1.0F);
            this.healTickTimer = 0;
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    public void readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.foodLevel = nbt.getInt("maxfoodlevel", 6);
    }
    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("maxfoodlevel", this.MaxFoodLevel);
    }
}
