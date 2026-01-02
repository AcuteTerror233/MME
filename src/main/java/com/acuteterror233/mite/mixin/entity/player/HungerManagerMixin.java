package com.acuteterror233.mite.mixin.entity.player;

import com.acuteterror233.mite.atinterface.HungerManagerExtension;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin implements HungerManagerExtension {
    @Shadow
    @Final
    private static int DEFAULT_FOOD_TICK_TIMER;
    @Shadow
    @Final
    private static float DEFAULT_EXHAUSTION;
    @Unique
    private int maxFoodLevel = 6;
    @Unique
    private int healTickTimer = 0;
    @Shadow
    private int foodLevel = maxFoodLevel;
    @Shadow
    private float saturationLevel;
    @Shadow
    private float exhaustion;
    @Shadow
    private int foodTickTimer;

    @Unique
    @Override
    public int getMaxFoodLevel() {
        return this.maxFoodLevel;
    }

    @Unique
    @Override
    public void setMaxFoodLevel(int maxFoodLevel) {
        this.maxFoodLevel = maxFoodLevel;
    }
    /**
     * @author AcuteTerror233
     * @reason 修改了未满的判断
     */
    @Overwrite
    public boolean isNotFull() {
        return this.foodLevel < maxFoodLevel;
    }

    @Shadow
    public abstract void addExhaustion(float exhaustion);

    /**
     * @author AcuteTerror233
     * @reason 添加最大食物等级判断
     */
    @Overwrite
    private void addInternal(int nutrition, float saturation) {
        this.foodLevel = MathHelper.clamp(nutrition + this.foodLevel, 0, maxFoodLevel);
        this.saturationLevel = MathHelper.clamp(saturation + this.saturationLevel, 0.0F, (float) this.foodLevel);
    }

    /**
     * @author AcuteTerror233
     * @reason 添加最大食物等级判断
     */
    @Overwrite
    public void update(ServerPlayerEntity player) {
        ServerWorld serverWorld = player.getServerWorld();
        Difficulty difficulty = serverWorld.getDifficulty();
        if (this.exhaustion > 4.0F) {
            this.exhaustion -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        boolean bl = serverWorld.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
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
            if (this.foodTickTimer >= 900){
                this.foodTickTimer = 0;
                if (player.getGameMode() != GameMode.CREATIVE) {
                    if (this.saturationLevel > 0) {
                        this.saturationLevel--;
                    } else {
                        this.foodLevel--;
                    }
                }
            }
        }else {
            this.healTickTimer = 0;
            if (this.foodTickTimer >= 300) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.damage(serverWorld, player.getDamageSources().starve(), 1.0F);
                }
                this.foodTickTimer = 0;
            }
        }
        this.foodTickTimer++;
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    public void readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.foodLevel = nbt.getInt("maxfoodlevel", 6);
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("maxfoodlevel", this.maxFoodLevel);
    }
}