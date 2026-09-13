package com.acuteterror233.mite.block.state.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * MME mod custom block state properties.
 * Defines new properties such as disease level ({@code DISEASE_LEVEL}) and fertility ({@code FERTILE}).
 */
public class MMEBlockStateProperties {
    public static final IntegerProperty DISEASE_LEVEL = IntegerProperty.create("disease_level", 0, 2);
    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");
}
