package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CocoaBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code CocoaBlock} — halves cocoa pod growth speed.
 *
 * <p>The {@code randomTick} redirect doubles the bound passed to {@code RandomSource#nextInt}, so
 * the vanilla growth roll succeeds half as often. Server-driven via random ticks.</p>
 */
@Mixin(CocoaBlock.class)
public class CocoaBlockMixin {
    /**
     * Redirects the {@code RandomSource#nextInt} call inside {@code randomTick}: doubles the bound
     * so growth fires half as frequently.
     *
     * @param instance the level random source
     * @param bound    the vanilla bound
     * @return a random value in {@code [0, bound * 2)}
     */
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    public int randomTick(RandomSource instance, int bound) {
        return instance.nextInt(bound * 2);
    }
}
