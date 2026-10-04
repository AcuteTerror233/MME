package com.acuteterror233.mite.mixin.world.entity.item;

import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code PrimedTnt} — Allows runtime modification of the primed TNT
 * explosion power.
 *
 * <p>The {@code explosionPower} shadow field is widened with
 * {@code @Mutable} so other code can reassign it; the initializer value is
 * documentation only (vanilla default is 3).</p>
 */
@Mixin(PrimedTnt.class)
public class PrimedTntMixin {
    /** Shadow of the explosion power; {@code @Mutable} permits reassignment. */
    @Mutable @Shadow private float explosionPower = 3F;
}
