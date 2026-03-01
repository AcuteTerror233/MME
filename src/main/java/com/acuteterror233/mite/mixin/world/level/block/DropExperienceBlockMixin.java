package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DropExperienceBlock.class)
public class DropExperienceBlockMixin {
    @Mutable
    @Shadow
    @Final
    private IntProvider xpRange;
    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(IntProvider intProvider, BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.xpRange = ConstantInt.of(this.xpRange.getMaxValue() * 2);
    }
}
