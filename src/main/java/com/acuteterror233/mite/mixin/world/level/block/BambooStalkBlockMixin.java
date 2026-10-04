package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BambooStalkBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code BambooStalkBlock} — halves bamboo growth speed.
 *
 * <p>The {@code randomTick} redirect inflates the bound passed to {@code RandomSource#nextInt} by
 * a factor of two, so the vanilla "advance growth" roll succeeds only half as often. Client
 * visual state is derived from the server state, so the effect is server-driven; the mixin itself
 * is side-neutral.</p>
 */
@Mixin(BambooStalkBlock.class)
public class BambooStalkBlockMixin {
    /**
     * Redirects the {@code RandomSource#nextInt} call inside {@code randomTick}: doubles the
     * bound so the growth roll fires half as frequently.
     *
     * @param randomSource the level random source
     * @param i            the vanilla bound
     * @return a random value in {@code [0, i * 2)}
     */
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    public int randomTick(RandomSource randomSource, int i) {
        return randomSource.nextInt(i * 2);
    }
}
