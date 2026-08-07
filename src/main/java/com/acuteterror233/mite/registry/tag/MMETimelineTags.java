package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.timeline.Timeline;

public interface MMETimelineTags {
    TagKey<Timeline> IN_UNDERGROUND = create("in_underground");

    private static TagKey<Timeline> create(String string) {
        return TagKey.create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }
}
