package com.acuteterror233.mite.interfaces;

import com.acuteterror233.mite.item.FuelGradeRegistry;

/**
 * Fuel grade registry extension interface.
 * Allows objects to provide fuel grade mappings.
 */
public interface GetFuelGradeRegistryExtension {
    default FuelGradeRegistry MME$GetFuelGradeRegistry() {
        throw new AssertionError("Implemented in Mixin");
    }
}
