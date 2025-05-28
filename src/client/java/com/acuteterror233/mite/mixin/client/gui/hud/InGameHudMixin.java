package com.acuteterror233.mite.mixin.client.gui.hud;

import com.acuteterror233.mite.atinterface.HungerManagerExtension;
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
public abstract class InGameHudMixin {
    @Shadow @Final private static Identifier FOOD_EMPTY_HUNGER_TEXTURE;
    @Shadow @Final private static Identifier FOOD_HALF_HUNGER_TEXTURE;
    @Shadow @Final private static Identifier FOOD_FULL_HUNGER_TEXTURE;
    @Shadow @Final private static Identifier FOOD_EMPTY_TEXTURE;
    @Shadow @Final private static Identifier FOOD_HALF_TEXTURE;
    @Shadow @Final private static Identifier FOOD_FULL_TEXTURE;
    @Shadow @Final private Random random;
    @Shadow private int ticks;

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
}
