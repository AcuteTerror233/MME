package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class MMEBlockIds {
    public static final ResourceKey<Block> UNDERGROUND_PORTAL = create("underground_portal");
    public static final ResourceKey<Block> HOME_PORTAL = create("home_portal");
    public static final ResourceKey<Block> RUNE_PORTAL = create("rune_portal");

    public static final ResourceKey<Block> BLUE_BERRY_BUSH = create("blue_berry_bush");

    private static ResourceKey<Block> create(final String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MME.MOD_ID, name));
    }
}
