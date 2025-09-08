package com.acuteterror233.mite.mixin.client.gui.hud;

import com.acuteterror233.mite.atinterface.HungerManagerExtension;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @ModifyConstant(method = "renderFood", constant = @Constant(intValue = 10))
    private int modifyFoodRenderCount(int original, DrawContext context, PlayerEntity player, int top, int right) {
        HungerManager hungerManager = player.getHungerManager();
        int maxFoodLevel = ((HungerManagerExtension) hungerManager).getMaxFoodLevel();
        return maxFoodLevel / 2;
    }
}