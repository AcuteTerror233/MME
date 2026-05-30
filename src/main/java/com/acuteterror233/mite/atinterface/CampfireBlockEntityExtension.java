package com.acuteterror233.mite.atinterface;

/**
 * 营火方块实体扩展接口。
 * 为营火方块实体提供额外的访问器方法。
 */
public interface CampfireBlockEntityExtension {
    int MME$GetRemainingIgnitionTime();
    void MME$DecreaseRemainingIgnitionTime();
    void MME$AddRemainingIgnitionTime(int remainingIgnitionTime);
}
