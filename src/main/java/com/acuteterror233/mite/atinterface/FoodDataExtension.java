package com.acuteterror233.mite.atinterface;

import com.acuteterror233.mite.world.food.FoodNutrition;

/**
 * 食物数据扩展接口。
 * 为 {@link net.minecraft.world.food.FoodData} 添加营养相关的访问器。
 */
public interface FoodDataExtension {
    int MME$GetMaxFoodLevel();
    void MME$SetMaxFoodLevel(int maxFoodLevel);
    void MME$AddFoodNutrition(FoodNutrition foodNutrition);
    FoodNutrition MME$GetFoodNutrition();
}
