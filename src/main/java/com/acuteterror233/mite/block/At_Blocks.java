package com.acuteterror233.mite.block;

import com.acuteterror233.mite.At_mite;
import net.minecraft.block.*;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class At_Blocks {
    public static final Block ADAMANTIUM_ORE = register("adamantium_ore",
            AbstractBlock.Settings.create());

    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        Items.register(block);
        return block;
    }
    private static Block register(String name, AbstractBlock.Settings settings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), settings);
        Items.register(block);
        return block;
    }

    public static void init() {

    }
}
