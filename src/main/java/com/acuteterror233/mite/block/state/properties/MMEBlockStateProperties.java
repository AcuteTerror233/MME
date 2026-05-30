package com.acuteterror233.mite.block.state.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * MME 模组自定义方块状态属性。
 * 定义病害等级（{@code DISEASE_LEVEL}）和肥力（{@code FERTILE}）等新属性。
 */
public class MMEBlockStateProperties {
    public static final IntegerProperty DISEASE_LEVEL = IntegerProperty.create("disease_level", 0, 2);
    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");
}
