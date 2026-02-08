package com.acuteterror233.mite.mixin.world.entity.item;

import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PrimedTnt.class)
public class PrimedTntMixin {
    @Mutable @Shadow private float explosionPower = 1.5F;
}
