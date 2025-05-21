package com.acuteterror233.mite.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.*;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin implements HungerManagerExtension {
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
    @Overwrite
    private void addInternal(int nutrition, float saturation) {
        this.foodLevel = MathHelper.clamp(nutrition + this.foodLevel, 0, MaxFoodLevel);
        this.saturationLevel = MathHelper.clamp(saturation + this.saturationLevel, 0.0F, (float)this.foodLevel);
    }

    @Overwrite
    public void update(ServerPlayerEntity player) {
        int level = player.experienceLevel;
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
        if (bl && this.saturationLevel > 0.0F && player.canFoodHeal() && this.foodLevel >= MaxFoodLevel) {
            this.foodTickTimer++;
            if (this.foodTickTimer >= 10) {
                float f = Math.min(this.saturationLevel, 6.0F);
                player.heal(f / 6.0F);
                this.addExhaustion(f);
                this.foodTickTimer = 0;
            }
        } else if (bl && this.foodLevel >= MaxFoodLevel - 2  && player.canFoodHeal()) {
            this.foodTickTimer++;
            if (this.foodTickTimer >= 80) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.foodTickTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            this.foodTickTimer++;
            if (this.foodTickTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.damage(serverWorld, player.getDamageSources().starve(), 1.0F);
                }

                this.foodTickTimer = 0;
            }
        } else {
            this.foodTickTimer = 0;
        }
    }

    @Shadow
    public abstract void addExhaustion(float exhaustion);
}
