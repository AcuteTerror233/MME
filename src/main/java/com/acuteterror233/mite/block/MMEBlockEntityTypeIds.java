package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MMEBlockEntityTypeIds {
    public static final ResourceKey<BlockEntityType<?>> ANVIL = create("anvil");
    public static final ResourceKey<BlockEntityType<?>> RUNE_PORTAL = create("rune_portal");
    public static final ResourceKey<BlockEntityType<?>> GRADE_FURNACE = create("grade_furnace");

    private static ResourceKey<BlockEntityType<?>> create(final String name) {
        return ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, name));
    }
}
