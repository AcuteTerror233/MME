package com.acuteterror233.mite.mixin.entity.player;

import com.acuteterror233.mite.atinterface.FoodDataExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin implements FoodDataExtension {
    @Shadow
    @Final
    private static int DEFAULT_TICK_TIMER;
    @Shadow
    @Final
    private static float DEFAULT_EXHAUSTION_LEVEL;
    @Unique
    private int maxFoodLevel = 6;
    @Unique
    private int healTickTimer = 0;
    @Shadow
    private int foodLevel;
    @Shadow
    private float saturationLevel;
    @Shadow
    private float exhaustionLevel;
    @Shadow
    private int tickTimer;

    @Unique
    @Override
    public int MME$GetMaxFoodLevel() {
        return this.maxFoodLevel;
    }

    @Unique
    @Override
    public void MME$SetMaxFoodLevel(int maxFoodLevel) {
        this.maxFoodLevel = maxFoodLevel;
    }
    /**
     * @author AcuteTerror233
     * @reason 修改了未满的判断
     */
    @Overwrite
    public boolean needsFood() {
        return this.foodLevel < maxFoodLevel;
    }

    @Shadow
    public abstract void addExhaustion(float exhaustion);

    /**
     * @author AcuteTerror233
     * @reason 添加最大食物等级判断
     */
    @Overwrite
    private void add(int nutrition, float saturation) {
        this.foodLevel = Mth.clamp(nutrition + this.foodLevel, 0, maxFoodLevel);
        this.saturationLevel = Mth.clamp(saturation + this.saturationLevel, 0.0F, (float) this.foodLevel);
    }

    /**
     * @author AcuteTerror233
     * @reason 添加最大食物等级判断
     */
    @Overwrite
    public void tick(ServerPlayer player) {
        ServerLevel serverWorld = player.serverLevel();
        Difficulty difficulty = serverWorld.getDifficulty();
        if (this.exhaustionLevel > 4.0F) {
            this.exhaustionLevel -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        boolean bl = serverWorld.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        if (this.foodLevel > 0) {
            if (player.isSleeping()) {
                this.healTickTimer += 8;
            }else {
                this.healTickTimer++;
            }
            if (this.healTickTimer >= 1280 && bl){
                player.heal(1.0F);
                this.healTickTimer = 0;
            }
            if (this.tickTimer >= 900){
                this.tickTimer = 0;
                if (player.gameMode() != GameType.CREATIVE) {
                    if (this.saturationLevel > 0) {
                        this.saturationLevel--;
                    } else {
                        this.foodLevel--;
                    }
                }
            }
        }else {
            this.healTickTimer = 0;
            if (this.tickTimer >= 300) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.hurtServer(serverWorld, player.damageSources().starve(), 1.0F);
                }
                this.tickTimer = 0;
            }
        }
        this.tickTimer++;
    }

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        this.foodLevel = nbt.getIntOr("maxFoodLevel", 6);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putInt("maxFoodLevel", this.maxFoodLevel);
    }

    /**
     * @author AcuteTerror233
     * @reason 添加最大食物等级判断
     */
    @Overwrite
    public void setFoodLevel(int i) {
        this.foodLevel = i > maxFoodLevel ? maxFoodLevel : i;
    }

    /**
     * @author AcuteTerror233
     * @reason 添加最大食物等级判断
     */
    @Overwrite
    public void setSaturation(float f) {
        this.saturationLevel = f > maxFoodLevel ? maxFoodLevel : f;
    }
}