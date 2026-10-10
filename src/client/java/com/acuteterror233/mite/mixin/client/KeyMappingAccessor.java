package com.acuteterror233.mite.mixin.client;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes {@code KeyMapping.clickCount} so {@code MMEAutoModes} can detect a fresh mouse click
 * (counter &gt; 0) at the HEAD of {@code handleKeybinds} without consuming it — vanilla keeps
 * processing the click normally afterwards.
 */
@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
    /** @return the pending (unconsumed) click count. */
    @Accessor("clickCount")
    int mme$clickCount();
}
