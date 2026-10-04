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

/**
 * Mixin for {@code SugarCaneBlock} — raises sugar cane's growth/height cap from 15 to 25.
 *
 * <p>The {@code randomTick} {@code @ModifyConstant} replaces every vanilla {@code 15} constant
 * (the age threshold that triggers placing a new cane block, and the column-height limit) with
 * 25, letting cane columns grow far taller than vanilla. The shadowed {@code AGE} field mirrors
 * the vanilla property (its re-declared initializer is compile-time only and does not alter the
 * target). Server-driven via random ticks.</p>
 */
@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {
    /** Shadowed vanilla cane age property. */
    @Shadow
    @Final
    @Mutable
    public static IntegerProperty AGE = BlockStateProperties.AGE_25;

    /**
     * Constant modifier for {@code randomTick}: swaps the vanilla height/age cap of 15 for 25.
     *
     * @param original the vanilla constant value (15)
     * @return the replacement cap (25)
     */
    @ModifyConstant(method = "randomTick", constant = @Constant(intValue = 15))
    private int randomTick(int original) {
        return 25;
    }
}
