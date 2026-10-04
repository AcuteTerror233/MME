package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

/**
 * MME mod entity type tag definitions.
 * Used for categorizing entity behaviors (e.g., butchering, manure production).
 */
public interface MMEEntityTypeTags {
    /** Entities affected by the butchering mechanic (yield their butcher drops). */
    TagKey<EntityType<?>> SENSITIVE_TO_BUTCHERING = key("sensitive_to_butchering");
    /** Entities that produce manure. */
    TagKey<EntityType<?>> PRODUCE_MANURE = key("produce_manure");

    /** Creates a {@link TagKey} in the entity type registry under the MME namespace. */
    private static TagKey<EntityType<?>> key(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
