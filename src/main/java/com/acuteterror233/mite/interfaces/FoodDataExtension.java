package com.acuteterror233.mite.interfaces;

import com.acuteterror233.mite.world.food.FoodNutrition;

/**
 * Food data extension interface (duck interface).
 * Adds nutrition-related accessors to {@link net.minecraft.world.food.FoodData}
 * (capped food level plus protein/fiber/sugar tracking).
 * Implemented by {@code FoodDataMixin}; injected into vanilla {@code FoodData}
 * via classTweaker {@code transitive-inject-interface}.
 */
public interface FoodDataExtension {
    /** @return the maximum food level (hunger) allowed by this food data. */
    default int MME$GetMaxFoodLevel() {
        throw new AssertionError("Implemented in Mixin");
    }
    /** Raises the maximum food level cap. */
    default void MME$SetMaxFoodLevel(int maxFoodLevel) {
        throw new AssertionError("Implemented in Mixin");
    }
    /** Adds the given nutrition profile to the stored nutrient values. */
    default void MME$AddFoodNutrition(FoodNutrition foodNutrition) {
        throw new AssertionError("Implemented in Mixin");
    }
    /** @return the currently stored nutrition profile. */
    default FoodNutrition MME$GetFoodNutrition() {
        throw new AssertionError("Implemented in Mixin");
    }
}
