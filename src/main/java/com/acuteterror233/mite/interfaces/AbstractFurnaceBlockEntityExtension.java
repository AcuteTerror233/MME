package com.acuteterror233.mite.interfaces;

/**
 * Furnace block entity extension interface (duck interface).
 * Stores the combustion grade of the fuel currently burning so {@code serverTick} can compare it
 * against the ingredient's {@code required_combustion_grade} and the furnace's {@code max_combustion_grade};
 * persisted as the {@code combustion_grade} NBT key.
 * Implemented by {@code AbstractFurnaceBlockEntityMixin}; injected into vanilla
 * {@code AbstractFurnaceBlockEntity} via classTweaker {@code transitive-inject-interface}.
 */
public interface AbstractFurnaceBlockEntityExtension {
    /** @return combustion grade of the fuel currently (or most recently) burning. */
    int MME$getCombustionGrade();
    /** Sets the combustion grade of the fuel currently burning. */
    void MME$setCombustionGrade(Integer cg);
}
