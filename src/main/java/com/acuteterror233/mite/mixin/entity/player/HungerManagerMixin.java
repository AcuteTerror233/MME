package com.acuteterror233.mite.mixin.entity.player;

import com.acuteterror233.mite.atinterface.HungerManagerExtension;
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
    @Unique private int MaxFoodLevel = 6;
    @Unique private int healTickTimer = 0;
    @Unique @Override public void setMaxFoodLevel(int maxFoodLevel) {
        MaxFoodLevel = maxFoodLevel;
    }
    @Unique @Override public int getMaxFoodLevel() {
        return MaxFoodLevel;
    }

    @Shadow private int foodLevel;
    @Shadow private float saturationLevel;
    @Shadow @Final private static int DEFAULT_FOOD_TICK_TIMER;
    @Shadow @Final private static float DEFAULT_EXHAUSTION;
    @Shadow private float exhaustion;
    @Shadow private int foodTickTimer;
    @Shadow public abstract void addExhaustion(float exhaustion);

    @Overwrite
    private void addInternal(int nutrition, float saturation) {
        this.foodLevel = MathHelper.clamp(nutrition + this.foodLevel, 0, MaxFoodLevel);
        this.saturationLevel = MathHelper.clamp(saturation + this.saturationLevel, 0.0F, (float) this.foodLevel);
    }

    @Overwrite
    public void update(ServerPlayerEntity player) {
        ServerWorld serverWorld = player.getServerWorld();
        Difficulty difficulty = serverWorld.getDifficulty();
        if (this.foodLevel > MaxFoodLevel){
            this.foodLevel = MaxFoodLevel;
        }
        if (this.exhaustion > 4.0F) {
            this.exhaustion -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        this.foodTickTimer++;
        if (foodLevel > 0) {
            this.healTickTimer++;
            if (foodTickTimer >= 1200){
                this.addExhaustion(1.0f);
                this.foodTickTimer = 0;
            }
            boolean bl = serverWorld.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
            if (bl && foodLevel >= 0 && player.canFoodHeal() && this.healTickTimer >= 1280) {
                player.heal(1.0F);
                this.healTickTimer = 0;
            }
        }else {
            this.healTickTimer = 0;
            if (this.foodTickTimer >= 640) {
                player.damage(serverWorld, player.getDamageSources().starve(), 1.0F);
                this.foodTickTimer = 0;
            }
        }
    }

        @Inject(method = "readNbt", at = @At("HEAD"))
        public void readNbt (NbtCompound nbt, CallbackInfo ci){
            this.foodLevel = nbt.getInt("maxfoodlevel", 6);
        }
        @Inject(method = "writeNbt", at = @At("HEAD"))
        public void writeNbt (NbtCompound nbt, CallbackInfo ci){
            nbt.putInt("maxfoodlevel", this.MaxFoodLevel);
        }
    }