package com.acuteterror233.mite.mixin.world.level.storage.loot.functions;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code ApplyBonusCount.OreDrops} — replaces the vanilla binomial ore-drops bonus with
 * MME's flat doubling roll used by the bonus enchantments (e.g. Harvest).
 */
@Mixin(ApplyBonusCount.OreDrops.class)
public class ApplyBonusCountMixin {

    /**
     * @author AcuteTerror233
     * @reason Limited Fortune Enchantment
     *
     * <p>Doubles the drop count when {@code nextInt(max(1, 10 - 2 * level)) <= 2}; at level 4 and
     * above the bound reaches 2 or below, so the roll always succeeds and the multiplier is capped
     * at ×2. Level 0 leaves the count untouched.</p>
     *
     * @param randomSource the loot random source
     * @param count        the base drop count
     * @param level        the enchantment level driving the bonus
     * @return the resulting drop count
     */
    @Overwrite
    public int calculateNewCount(RandomSource randomSource, int count, int level) {
        if (level > 0) {
            if (randomSource.nextInt(Math.max(1,10 - level * 2)) <= 2) {
                count*=2;
            }
        }
        return count;
    }
}
