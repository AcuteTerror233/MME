package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record BlockItemId(ResourceKey<Block> blockKey, ResourceKey<Item> itemKey) {
    public static BlockItemId create(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(MME.MOD_ID, name);
        return new BlockItemId(
                ResourceKey.create(Registries.BLOCK, id),
                ResourceKey.create(Registries.ITEM, id)
        );
    }

    public ResourceKey<Item> asItem() {
        return itemKey;
    }
}
