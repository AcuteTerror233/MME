package com.acuteterror233.mite.interfaces;

import com.acuteterror233.mite.world.food.FoodNutrition;

/**
 * 食物数据扩展接口。
 * 为 {@link net.minecraft.world.food.FoodData} 添加营养相关的访问器。
 */
public interface FoodDataExtension {
    default int MME$GetMaxFoodLevel() {
        throw new AssertionError("Implemented in Mixin");
    }
    default void MME$SetMaxFoodLevel(int maxFoodLevel) {
        throw new AssertionError("Implemented in Mixin");
    }
    default void MME$AddFoodNutrition(FoodNutrition foodNutrition) {
        throw new AssertionError("Implemented in Mixin");
    }
    default FoodNutrition MME$GetFoodNutrition() {
        throw new AssertionError("Implemented in Mixin");
    }
}
