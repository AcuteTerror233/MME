package com.acuteterror233.mite.interfaces;

import com.acuteterror233.mite.world.food.FoodNutrition;

/**
 * Food data extension interface.
 * Adds nutrition-related accessors to {@link net.minecraft.world.food.FoodData}.
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
