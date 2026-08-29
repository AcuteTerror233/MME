package com.acuteterror233.mite.interfaces;

import com.acuteterror233.mite.item.FuelGradeRegistry;

/**
 * 获取燃料等级注册表扩展接口。
 * 允许对象提供燃料等级映射表。
 */
public interface GetFuelGradeRegistryExtension {
    default FuelGradeRegistry MME$GetFuelGradeRegistry() {
        throw new AssertionError("Implemented in Mixin");
    }
}
