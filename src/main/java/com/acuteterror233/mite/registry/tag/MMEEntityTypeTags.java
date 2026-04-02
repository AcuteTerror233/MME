package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public interface MMEEntityTypeTags {
    TagKey<EntityType<?>> SENSITIVE_TO_BUTCHERING = key("sensitive_to_butchering");
    TagKey<EntityType<?>> PRODUCE_MANURE = key("produce_manure");

    private static TagKey<EntityType<?>> key(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
