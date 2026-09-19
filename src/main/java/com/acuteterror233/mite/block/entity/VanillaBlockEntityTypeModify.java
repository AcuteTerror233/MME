package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class VanillaBlockEntityTypeModify {
    public static final Map<Identifier, UnaryOperator<Block[]>> IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY = createBlockEntityTypeModifyMapByIdentifier();

    private static Map<Identifier, UnaryOperator<Block[]>> createBlockEntityTypeModifyMapByIdentifier() {
        Map<Identifier, UnaryOperator<Block[]>> map = new HashMap<>();

        map.put(Identifier.withDefaultNamespace("furnace"), blocks -> {
            ArrayList<Block> list = new ArrayList<>(Arrays.asList(blocks));
            list.add(MMEBlocks.CLAY_FURNACE);
            list.add(MMEBlocks.NETHERRACK_FURNACE);
            list.add(MMEBlocks.OBSIDIAN_FURNACE);
            list.add(MMEBlocks.SANDSTONE_FURNACE);
            list.add(MMEBlocks.HARDENED_CLAY_FURNACE);
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
