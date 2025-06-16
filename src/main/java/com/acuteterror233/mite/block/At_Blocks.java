package com.acuteterror233.mite.block;

import com.acuteterror233.mite.At_mite;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class At_Blocks {
    public static final Block ADAMANTIUM_ORE = register(
            "adamantium_ore",
            AdamantiumOreBlock::new,
            AbstractBlock.Settings.create());

    public static final BlockEntityType<AdamantiumOreBlockEntity> ADAMANTIUM_ORE_BLOCK_ENTITY =
            register("adamantium_ore_block_entity", AdamantiumOreBlockEntity::new, ADAMANTIUM_ORE);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(At_mite.MOD_ID, name), FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(At_mite.MOD_ID, name)), factory, settings);
        Items.register(block);
        return block;
    }

    public static void init() {
    }
}
