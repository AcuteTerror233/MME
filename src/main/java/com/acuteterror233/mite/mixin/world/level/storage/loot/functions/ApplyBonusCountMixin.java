package com.acuteterror233.mite.mixin.world.level.storage.loot.functions;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ApplyBonusCount.OreDrops.class)
/**
 * Mixin for {@code ApplyBonusCount} — 应用 MME 自定义附魔（如收割）的时运加成。
 */
public class ApplyBonusCountMixin {
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
