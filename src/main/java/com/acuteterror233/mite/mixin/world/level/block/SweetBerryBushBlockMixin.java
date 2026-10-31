package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code SweetBerryBushBlock} — slows sweet berry bush growth by a factor of 150.
 *
 * <p>The {@code randomTick} redirect multiplies the bound passed to {@code RandomSource#nextInt}
 * by 150, so the vanilla berry-growth roll succeeds roughly 1/150 as often (about 1/750 per random
 * tick overall). Server-driven via random ticks.</p>
 */
@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {
    /**
     * Redirects the {@code RandomSource#nextInt} call inside {@code randomTick}: multiplies the
     * bound by 150 so berry growth fires far less frequently.
     *
     * @param instance the level random source
     * @param bound    the vanilla bound
     * @return a random value in {@code [0, bound * 150)}
     */
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    public int randomTick(RandomSource instance, int bound) {
        return instance.nextInt(bound * 150);
    }
}
