package com.acuteterror233.mite.world.entity;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class MMEEntityTypeIds {
    public static final ResourceKey<EntityType<?>> GHOUL = create("ghoul");
    public static final ResourceKey<EntityType<?>> SHADOW = create("shadow");
    public static final ResourceKey<EntityType<?>> WIGHT = create("wight");
    public static final ResourceKey<EntityType<?>> INVISIBLE_STALKER = create("invisible_stalker");
    public static final ResourceKey<EntityType<?>> DEMON_SPIDER = create("demon_spider");
    public static final ResourceKey<EntityType<?>> PHASE_SPIDER = create("phase_spider");
    public static final ResourceKey<EntityType<?>> INFERNAL_CREEPER = create("infernal_creeper");
    public static final ResourceKey<EntityType<?>> FIRE_ELEMENTAL = create("fire_elemental");
    public static final ResourceKey<EntityType<?>> VAMPIRE_BAT = create("vampire_bat");
    public static final ResourceKey<EntityType<?>> NIGHTWING = create("nightwing");
    public static final ResourceKey<EntityType<?>> GIANT_VAMPIRE_BAT = create("giant_vampire_bat");

    private static ResourceKey<EntityType<?>> create(final String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, name));
    }
}
