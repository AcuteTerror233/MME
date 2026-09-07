package com.acuteterror233.mite.mixin.world.entity.item;

import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code PrimedTnt} — 修改激活的 TNT 行为。
 */
@Mixin(PrimedTnt.class)
public class PrimedTntMixin {
    @Mutable @Shadow private float explosionPower = 1.5F;
}
