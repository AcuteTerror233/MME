package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Paired registry keys for a block and its block item sharing one path
 * (e.g. {@code mme:adamantium_ore} for both).
 */
public record BlockItemId(ResourceKey<Block> blockKey, ResourceKey<Item> itemKey) {
    /** Creates the key pair under the MME namespace for the given path. */
    public static BlockItemId create(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(MME.MOD_ID, name);
        return new BlockItemId(
                ResourceKey.create(Registries.BLOCK, id),
                ResourceKey.create(Registries.ITEM, id)
        );
    }

    /** @return the paired block item key. */
    public ResourceKey<Item> asItem() {
        return itemKey;
    }
}
