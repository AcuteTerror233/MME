package com.acuteterror233.mite.mixin.world.effect;

import net.minecraft.world.effect.PoisonMobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PoisonMobEffect.class)
public class PoisonMobEffectMixin {

    /**
     * @author AcuteTerror233
     * @reason 修改中毒效果间隔
     */
    @Overwrite
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        int k = 200 >> j;
        return k > 0 ? i % k == 0 : true;
    }
}
