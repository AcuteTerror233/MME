package com.acuteterror233.mite.registry.tag;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.timeline.Timeline;

/**
 * MME mod timeline tag definitions ({@code Timeline} registry).
 * Timelines are vanilla world states; MME tags them to drive dimension environment behavior.
 */
public interface MMETimelineTags {
    /** Timelines matching the underground state; consumed by MME dimension type registration. */
    TagKey<Timeline> IN_UNDERGROUND = create("in_underground");

    /** Creates a {@link TagKey} in the timeline registry under the MME namespace. */
    private static TagKey<Timeline> create(String string) {
        return TagKey.create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }
}
