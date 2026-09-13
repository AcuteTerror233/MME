package com.acuteterror233.mite.interfaces;

/**
 * Campfire block entity extension interface.
 * Provides additional accessor methods for campfire block entities.
 */
public interface CampfireBlockEntityExtension {
    default int MME$GetRemainingIgnitionTime(){
        throw new AssertionError("Implemented in Mixin");
    };
    default void MME$DecreaseRemainingIgnitionTime(){
        throw new AssertionError("Implemented in Mixin");
    };
    default void MME$AddRemainingIgnitionTime(int remainingIgnitionTime){
        throw new AssertionError("Implemented in Mixin");
    };
}
