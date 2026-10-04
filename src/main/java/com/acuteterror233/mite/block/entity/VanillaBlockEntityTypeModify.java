package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Vanilla block entity type modifier.
 * At vanilla bootstrap time {@code BootstrapMixin} consults this map (through
 * {@code VanillaRegisterModify}) per block entity type id, extending the valid-blocks array so MME
 * block variants (material furnaces, emerald enchanting table) share the vanilla block entity type.
 */
public class VanillaBlockEntityTypeModify {
    /** Valid-blocks extensions keyed by vanilla block entity type id. */
    public static final Map<Identifier, UnaryOperator<Block[]>> IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY = createBlockEntityTypeModifyMapByIdentifier();

    /** Builds the per-id valid-block extensions (furnace gains the material furnace variants; enchanting table gains the emerald table). */
    private static Map<Identifier, UnaryOperator<Block[]>> createBlockEntityTypeModifyMapByIdentifier() {
        Map<Identifier, UnaryOperator<Block[]>> map = new HashMap<>();

        map.put(Identifier.withDefaultNamespace("furnace"), blocks -> {
            ArrayList<Block> list = new ArrayList<>(Arrays.asList(blocks));
            list.add(MMEBlocks.CLAY_FURNACE);
            list.add(MMEBlocks.NETHERRACK_FURNACE);
            list.add(MMEBlocks.OBSIDIAN_FURNACE);
            list.add(MMEBlocks.SANDSTONE_FURNACE);
            list.add(MMEBlocks.TERRACOTTA_FURNACE);
            return list.toArray(new Block[0]);
        });
        map.put(Identifier.withDefaultNamespace("enchanting_table"), blocks -> {
            ArrayList<Block> list = new ArrayList<>(Arrays.asList(blocks));
            list.add(MMEBlocks.EMERALD_ENCHANTING_TABLE);
            return list.toArray(new Block[0]);
        });

        return map;
    }

}
