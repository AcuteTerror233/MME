package com.acuteterror233.mite.interfaces;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

/**
 * Extension interface for block model generators.
 * Provides custom generation methods for block models in data generation.
 */
public interface BlockModelGeneratorsExtension {
    void MME$registerAnvil(Block Block, Block block1, Block block2);
    void MME$registerCrop(Block block, Property<Integer> property, int... is);
    void MME$registerFarmland();
}
