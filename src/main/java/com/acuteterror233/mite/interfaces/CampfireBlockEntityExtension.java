package com.acuteterror233.mite.interfaces;

/**
 * Campfire block entity extension interface (duck interface).
 * Tracks how much ignition time remains before the campfire burns out.
 * Implemented by {@code CampfireBlockEntityMixin}; injected into vanilla {@code CampfireBlockEntity}
 * via classTweaker {@code transitive-inject-interface}.
 */
public interface CampfireBlockEntityExtension {
    /** @return ignition time (ticks) remaining before the campfire burns out. */
    default int MME$GetRemainingIgnitionTime(){
        throw new AssertionError("Implemented in Mixin");
    };
    /** Decrements the remaining ignition time by one tick. */
    default void MME$DecreaseRemainingIgnitionTime(){
        throw new AssertionError("Implemented in Mixin");
    };
    /**
     * Adds ignition time to the campfire.
     *
     * @param remainingIgnitionTime additional ignition time in ticks
     */
    default void MME$AddRemainingIgnitionTime(int remainingIgnitionTime){
        throw new AssertionError("Implemented in Mixin");
    };
}
