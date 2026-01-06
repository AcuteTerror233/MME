package com.acuteterror233.mite.mixin.client.gui;

import com.acuteterror233.mite.atinterface.FoodDataExtension;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @ModifyConstant(method = "renderFood", constant = @Constant(intValue = 10))
    private int modifyFoodRenderCount(int original, GuiGraphics context, Player player, int top, int right) {
        FoodData hungerManager = player.getFoodData();
        int maxFoodLevel = ((FoodDataExtension) hungerManager).MME$GetMaxFoodLevel();
        return maxFoodLevel / 2;
    }
}