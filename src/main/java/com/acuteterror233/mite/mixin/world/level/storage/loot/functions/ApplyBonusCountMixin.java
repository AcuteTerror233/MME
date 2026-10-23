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
     * @author AcuteTerror233
     * @reason Limited Fortune Enchantment
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
