package com.acuteterror233.mite.mixin.world.level.storage.loot.functions;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code ApplyBonusCount} — rewrites the ore-drops fortune formula used by MME's bonus
 * enchantments (e.g. Harvest), capping the multiplier.
 */
@Mixin(ApplyBonusCount.OreDrops.class)
public class ApplyBonusCountMixin {
    /**
     * Overwrites vanilla {@code calculateNewCount}: the binomial draw is clamped to 0..2, so the
     * drop multiplier never exceeds 3× the base count regardless of enchantment power.
     *
     * @param randomSource the loot random source
     * @param i            the base item count being multiplied
     * @param j            the enchantment level driving the bonus
     * @return the adjusted drop count (uncapped when the enchantment level is 0)
     */
    @Overwrite
    public int calculateNewCount(RandomSource randomSource, int i, int j) {
        if (j > 0) {
            int k = randomSource.nextInt(j + 2) - 1;
            if (k < 0) {
                k = 0;
            }else if (k > 2){
                k = 2;
            }

            return i * (k + 1);
        } else {
            return i;
        }
    }
}
