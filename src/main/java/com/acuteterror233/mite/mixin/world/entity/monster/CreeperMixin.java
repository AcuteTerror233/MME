package com.acuteterror233.mite.mixin.world.entity.monster;

import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code Creeper} — Widens the creeper explosion radius field for runtime
 * modification.
 *
 * <p>Marks the {@code explosionRadius} shadow field {@code @Mutable} so other code can
 * reassign it; the initializer value is documentation only (vanilla default is 3).</p>
 */
@Mixin(Creeper.class)
public class CreeperMixin {
    /** Shadow of the explosion radius; {@code @Mutable} permits reassignment. */
    @Shadow @Mutable
    public int explosionRadius = 2;
}
