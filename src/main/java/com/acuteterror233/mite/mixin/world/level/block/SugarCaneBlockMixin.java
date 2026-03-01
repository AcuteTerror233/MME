package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {
    @Shadow
    @Final
    @Mutable
    public static IntegerProperty AGE = BlockStateProperties.AGE_25;

    @ModifyConstant(method = "randomTick", constant = @Constant(intValue = 15))
    private int randomTick(int original) {
        return 25;
    }
}
