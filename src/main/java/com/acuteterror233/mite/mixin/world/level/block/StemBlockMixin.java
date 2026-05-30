package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.StemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StemBlock.class)
/**
 * Mixin for {@code StemBlock} — 修改瓜藤生长行为。
 */
public class StemBlockMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    public int randomTick(RandomSource instance, int bound) {
        return instance.nextInt(bound * 2);
    }
}
