package com.acuteterror233.mite.mixin.world.entity.player;

import com.acuteterror233.mite.atinterface.FoodDataExtension;
import com.acuteterror233.mite.world.effect.MMEMobEffects;
import com.acuteterror233.mite.world.food.FoodNutrition;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin implements FoodDataExtension {
    @Unique
    private int maxFoodLevel = 6;
    @Unique
    private int healTickTimer = 0;
    @Mutable
    @Shadow
    private int foodLevel = 6;
    @Shadow
    private float saturationLevel;
    @Shadow
    private float exhaustionLevel;
    @Shadow
    private int tickTimer;
    @Unique
    private float protein = 100000;
    @Unique
    private float fiber = 100000;
    @Unique
    private float sugar = 0;

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
            if (this.healTickTimer >= (1280 * (player.hasEffect(MMEMobEffects.MALNUTRITION) ? 2 : 1)) && bl){
                player.heal(1.0F);
                this.healTickTimer = 0;
            }
            if (this.tickTimer >= (1200 * (player.hasEffect(MMEMobEffects.MALNUTRITION) ? 0.5 : 1))){
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
            if (this.tickTimer > 301){
                this.tickTimer = 0;
            }
            this.healTickTimer = 0;
            if (this.tickTimer >= 300) {
                player.hurtServer(serverWorld, player.damageSources().starve(), 1.0F);
                this.tickTimer = 0;
            }
        }
        updatePlayerEffects(player);
        this.tickTimer++;
    }

    @Unique
    public void updatePlayerEffects(ServerPlayer player) {
        // 处理 fiber 相关逻辑
        handleNutrientEffect(player, this.fiber, MMEMobEffects.MALNUTRITION, () -> this.fiber--);

        // 处理 protein 相关逻辑
        handleNutrientEffect(player, this.protein, MMEMobEffects.MALNUTRITION, () -> this.protein--);

        // 处理 sugar 相关逻辑
        handleSugar(player, this.sugar, MMEMobEffects.INSULIN_RESISTANCE, () -> this.sugar--);
    }

    @Unique
    private void handleNutrientEffect(ServerPlayer player, float nutrientLevel, Holder<MobEffect> effect, Runnable decrementAction) {
        if (!player.hasInfiniteMaterials()){
            // 处理营养不足的情况
            if (nutrientLevel > 0) {
                if (player.hasEffect(effect)) {
                    player.removeEffect(effect);
                }
                decrementAction.run(); // 执行减量操作
            } else if (!player.hasEffect(effect)) {
                // 添加持续时间为永久的效果（-1 表示永久）
                player.addEffect(new MobEffectInstance(effect, -1), player);
            }
        }
    }

    @Unique
    private void handleSugar(ServerPlayer player, float sugar, Holder<MobEffect> effect, Runnable decrementAction) {
        if (!player.hasInfiniteMaterials()){
            // 处理糖的情况
            if (sugar > 0) {
                decrementAction.run(); // 执行减量操作
            }

            // 根据糖分阈值给予不同等级的反胃效果
            if (sugar > 144000) {
                // 糖分大于144000，给予反胃3
                MobEffectInstance nauseaEffect = player.getEffect(MobEffects.NAUSEA);
                if (!player.hasEffect(MobEffects.NAUSEA) || (nauseaEffect != null && nauseaEffect.getAmplifier() < 2)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, -1, 2), player);
                }
            } else if (sugar > 96000) {
                // 糖分大于96000，给予反胃2
                MobEffectInstance nauseaEffect = player.getEffect(MobEffects.NAUSEA);
                if (!player.hasEffect(MobEffects.NAUSEA) || (nauseaEffect != null && nauseaEffect.getAmplifier() < 1)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, -1, 1), player);
                }
            } else if (sugar > 48000) {
                // 糖分大于48000，给予反胃1
                if (!player.hasEffect(MobEffects.NAUSEA)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, -1), player);
                }
                if (!player.hasEffect(effect)){
                    player.addEffect(new MobEffectInstance(effect, -1), player);
                }
            } else {
                // 糖分低于48000，移除反胃效果
                if (player.hasEffect(MobEffects.NAUSEA)) {
                    player.removeEffect(MobEffects.NAUSEA);
                }
                if (player.hasEffect(effect)) {
                    player.removeEffect(effect);
                }
            }
        }
    }


    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        this.maxFoodLevel = nbt.getIntOr("maxFoodLevel", 6);
        this.fiber = nbt.getFloatOr("fiber", 100000);
        this.protein = nbt.getFloatOr("protein", 100000);
        this.sugar = nbt.getFloatOr("sugar", 0);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putInt("maxFoodLevel", this.maxFoodLevel);
        nbt.putFloat("fiber", this.fiber);
        nbt.putFloat("protein", this.protein);
        nbt.putFloat("sugar", this.sugar);
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

    @Override
    public void MME$AddFoodNutrition(FoodNutrition foodNutrition) {
        this.fiber = Math.min(160000, this.fiber + foodNutrition.fiber());
        this.protein = Math.min(160000, this.protein + foodNutrition.protein());
        this.sugar = Math.min(192000, this.sugar + foodNutrition.sugar());
    }
}