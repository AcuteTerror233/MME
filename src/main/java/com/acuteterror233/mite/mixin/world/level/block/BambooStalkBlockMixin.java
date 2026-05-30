package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BambooStalkBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BambooStalkBlock.class)
/**
 * Mixin for {@code BambooStalkBlock} — 修改竹子生长行为。
 */
public class BambooStalkBlockMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    public int randomTick(RandomSource randomSource, int i) {
        return randomSource.nextInt(i * 2);
    }
}
