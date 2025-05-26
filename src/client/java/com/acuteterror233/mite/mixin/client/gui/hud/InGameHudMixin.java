package com.acuteterror233.mite.mixin.client.gui.hud;

import com.acuteterror233.mite.newinterface.HungerManagerExtension;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow @Final private static Identifier FOOD_EMPTY_HUNGER_TEXTURE;
    @Shadow @Final private static Identifier FOOD_HALF_HUNGER_TEXTURE;
    @Shadow @Final private static Identifier FOOD_FULL_HUNGER_TEXTURE;
    @Shadow @Final private static Identifier FOOD_EMPTY_TEXTURE;
    @Shadow @Final private static Identifier FOOD_HALF_TEXTURE;
    @Shadow @Final private static Identifier FOOD_FULL_TEXTURE;
    @Shadow @Final private Random random = Random.create();
    @Shadow private int ticks;
    /**
     * @author
     * @reason
     */
    @Overwrite
    private void renderFood(DrawContext context, PlayerEntity player, int top, int right) {
        HungerManager hungerManager = player.getHungerManager();
        int MaxFoodLevel = ((HungerManagerExtension)hungerManager).getMaxFoodLevel();
        int FoodLevel = hungerManager.getFoodLevel();
        for(int j = 0; j < MaxFoodLevel / 2; ++j) {
            int k = top;
            Identifier identifier;
            Identifier identifier2;
            Identifier identifier3;
            if (player.hasStatusEffect(StatusEffects.HUNGER)) {
                identifier = FOOD_EMPTY_HUNGER_TEXTURE;
                identifier2 = FOOD_HALF_HUNGER_TEXTURE;
                identifier3 = FOOD_FULL_HUNGER_TEXTURE;
            } else {
                identifier = FOOD_EMPTY_TEXTURE;
                identifier2 = FOOD_HALF_TEXTURE;
                identifier3 = FOOD_FULL_TEXTURE;
            }

            if (player.getHungerManager().getSaturationLevel() <= 0.0F && this.ticks % (FoodLevel * 3 + 1) == 0) {
                k = top + (this.random.nextInt(3) - 1);
            }

            int l = right - j * 8 - 9;
            context.drawGuiTexture(RenderLayer::getGuiTextured, identifier, l, k, 9, 9);
            if (j * 2 + 1 < FoodLevel) {
                context.drawGuiTexture(RenderLayer::getGuiTextured, identifier3, l, k, 9, 9);
            }

            if (j * 2 + 1 == FoodLevel) {
                context.drawGuiTexture(RenderLayer::getGuiTextured, identifier2, l, k, 9, 9);
            }
        }
    }
    /**
     * 渲染玩家的生命值条
     *
     * @param context 绘制上下文，用于渲染图形
     * @param player 玩家实体，用于获取生命值和其他状态
     * @param x 生命值条的起始绘制X坐标
     * @param y 生命值条的起始绘制Y坐标
     * @param lines 每行生命值图标的数量
     * @param regeneratingHeartIndex 正在再生的心脏索引
     * @param maxHealth 玩家的最大生命值
     * @param lastHealth 上一帧玩家的生命值
     * @param health 玩家当前的生命值
     * @param absorption 玩家的吸收生命值
     * @param blinking 是否闪烁生命值条的当前生命值部分
     */
//    private void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
//        // 根据玩家状态获取心脏类型
//        InGameHud.HeartType heartType = InGameHud.HeartType.fromPlayerState(player);
//        // 获取玩家世界是否为 hardcore 模式
//        boolean bl = player.getWorld().getLevelProperties().isHardcore();
//        // 计算最大生命值对应的心脏数量
//        int i = MathHelper.ceil((double)maxHealth / (double)2.0F);
//        // 计算吸收生命值对应的心脏数量
//        int j = MathHelper.ceil((double)absorption / (double)2.0F);
//        // 计算总的心脏数量
//        int k = i * 2;
//
//        // 遍历每个心脏，从最后一个开始
//        for(int l = i + j - 1; l >= 0; --l) {
//            // 计算当前心脏的行号和列号
//            int m = l / 10;
//            int n = l % 10;
//            // 计算当前心脏的绘制位置
//            int o = x + n * 8;
//            int p = y - m * lines;
//            // 如果玩家生命值加上吸收生命值小于等于4，随机调整绘制位置
//            if (lastHealth + absorption <= 4) {
//                p += this.random.nextInt(2);
//            }
//
//            // 如果当前心脏是玩家生命值的一部分且是正在再生的心脏，调整绘制位置
//            if (l < i && l == regeneratingHeartIndex) {
//                p -= 2;
//            }
//
//            // 绘制生命值容器
//            this.drawHeart(context, InGameHud.HeartType.CONTAINER, o, p, bl, blinking, false);
//            // 计算当前心脏对应的生命值
//            int q = l * 2;
//            // 判断当前心脏是否是吸收生命值的一部分
//            boolean bl2 = l >= i;
//            if (bl2) {
//                int r = q - k;
//                if (r < absorption) {
//                    boolean bl3 = r + 1 == absorption;
//                    // 绘制吸收生命值的心脏
//                    this.drawHeart(context, heartType == InGameHud.HeartType.WITHERED ? heartType : InGameHud.HeartType.ABSORBING, o, p, bl, false, bl3);
//                }
//            }
//
//            // 如果是闪烁模式且当前生命值未达到，则绘制当前生命值的心脏
//            if (blinking && q < health) {
//                boolean bl4 = q + 1 == health;
//                this.drawHeart(context, heartType, o, p, bl, true, bl4);
//            }
//
//            // 如果当前生命值未达到上一帧的生命值，则绘制上一帧生命值的心脏
//            if (q < lastHealth) {
//                boolean bl4 = q + 1 == lastHealth;
//                this.drawHeart(context, heartType, o, p, bl, false, bl4);
//            }
//        }
//    }
}
