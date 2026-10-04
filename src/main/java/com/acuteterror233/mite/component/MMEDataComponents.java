package com.acuteterror233.mite.component;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

import java.util.function.UnaryOperator;

/**
 * MME mod data component type registration.
 * Defines custom item data components (such as crafting time).
 */
public class MMEDataComponents {
    /** Extra crafting time (seconds, non-negative) an ingredient contributes to the timed crafting duration. */
    public static final DataComponentType<Integer> CRAFTING_TIME = register("crafting_time", builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    /** Minimum combustion grade the furnace's burning fuel must reach to smelt this ingredient (default 1). */
    public static final DataComponentType<Integer> REQUIRED_COMBUSTION_GRADE = register("required_combustion_grade", builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    /** Highest combustion grade of fuel this furnace item may burn (default 1). */
    public static final DataComponentType<Integer> MAX_COMBUSTION_GRADE = register("max_combustion_grade", builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    /** Combustion grade of a fuel item; assigned from {@link MME#CORRESPONDING_COMBUSTION_GRADE} (default 1). */
    public static final DataComponentType<Integer> COMBUSTION_GRADE = register("combustion_grade", builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    /** Nutrition profile (fiber, protein, sugar) attached to food items and aggregated into the player's food data. */
    public static final DataComponentType<FoodNutrition> FOOD_NUTRITION = register("food_nutrition", builder -> builder.persistent(FoodNutrition.DIRECT_CODEC).networkSynchronized(FoodNutrition.DIRECT_STREAM_CODEC));

    /** Builds a data component type with the codecs configured by {@code builderOperator} and registers it under the MME namespace. */
    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, id), builderOperator.apply(DataComponentType.builder()).build());
    }
}
