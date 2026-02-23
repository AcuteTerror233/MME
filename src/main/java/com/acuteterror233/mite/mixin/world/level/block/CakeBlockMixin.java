package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.atinterface.FoodDataExtension;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.CakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CakeBlock.class)
public class CakeBlockMixin {
    @Redirect(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"))
    private static void eat(FoodData foodData, int i, float f) {
        if (foodData instanceof FoodDataExtension ext) {
            ext.MME$AddFoodNutrition(new FoodNutrition(1600, 0, 0));
        }
        foodData.eat(i, 1.0F);
    }
}
