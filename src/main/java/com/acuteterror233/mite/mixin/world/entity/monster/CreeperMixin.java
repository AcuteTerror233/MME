package com.acuteterror233.mite.mixin.world.entity.monster;

import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Creeper.class)
public class CreeperMixin {
    @Shadow @Mutable private int explosionRadius = 2;
}
