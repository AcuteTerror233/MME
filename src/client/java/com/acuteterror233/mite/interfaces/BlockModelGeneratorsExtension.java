package com.acuteterror233.mite.interfaces;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

/**
 * Extension interface for block model generators.
 * Provides custom generation methods for block models in data generation.
 */
public interface BlockModelGeneratorsExtension {
    /**
     * Generates the model and blockstate JSON for one metal's three anvil damage states, all
     * textured from the intact anvil via {@code TemplateAnvilModels}.
     *
     * @param Block  the intact anvil block (also provides the shared textures)
     * @param block1 the chipped anvil block
     * @param block2 the damaged anvil block
     */
    void MME$registerAnvil(Block Block, Block block1, Block block2);

    /**
     * Generates crop blockstate JSON dispatching on both the age property and MME's disease
     * level: each (age, diseaseLevel) combination maps to its own texture/model, with the model
     * id per disease state derived from the age's texture stage {@code is[age]}.
     *
     * @param block    the crop block
     * @param property the crop age property
     * @param is       texture stage per age value; length must equal the property's value count
     */
    void MME$registerCrop(Block block, Property<Integer> property, int... is);

    /** Generates farmland blockstate JSON dispatching on moisture and MME's fertile property (four model variants). */
    void MME$registerFarmland();
}
