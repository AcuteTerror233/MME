package com.acuteterror233.mite.interfaces;

/**
 * 营火方块实体扩展接口。
 * 为营火方块实体提供额外的访问器方法。
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
