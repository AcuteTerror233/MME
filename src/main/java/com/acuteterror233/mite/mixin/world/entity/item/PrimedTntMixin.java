package com.acuteterror233.mite.mixin.world.entity.item;

import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code PrimedTnt} — Modify primed TNT behavior.
 */
@Mixin(PrimedTnt.class)
public class PrimedTntMixin {
    @Mutable @Shadow private float explosionPower = 3F;
}
