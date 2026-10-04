package com.acuteterror233.mite.mixin.world.entity.monster.cubemob;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.world.entity.monster.cubemob.SulfurCube;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code SulfurCube} — Allows picking up a sulfur cube with any bucket
 * (tag {@code MMEItemTags#BUCKET}), not just a water bucket.
 */
@Mixin(SulfurCube.class)
public class SulfurCubeMixin {

    /** Accepts any bucket tagged {@code MMEItemTags#BUCKET}. */
    @Overwrite
    public boolean canBePickedUpWithBucket(final ItemStack itemStack) {
        return itemStack.is(MMEItemTags.BUCKET);
    }

}
