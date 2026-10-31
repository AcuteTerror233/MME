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
 * Mixin for {@code SugarCaneBlock} — attempts to raise sugar cane's growth age cap from 15 to 25.
 *
 * <p>The {@code randomTick} {@code @ModifyConstant} swaps vanilla's single {@code 15} age constant
 * (the "fully grown → place a new cane block" check) for 25; the vanilla column-height limit
 * ({@code height < 3}) is not modified. The shadowed {@code AGE} field re-declares its initializer
 * as {@code AGE_25}, but a {@code @Shadow} initializer is compile-time only and never reaches the
 * target, so the target's property remains {@code AGE_15} (0..15): age 25 is unreachable and the
 * else branch would attempt {@code setValue(AGE, 16)} at age 15, which is out of range.
 * Server-driven via random ticks.</p>
 */
@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {
    /** Shadowed vanilla cane age property. */
    @Shadow
    @Final
    @Mutable
    public static IntegerProperty AGE = BlockStateProperties.AGE_25;

    /**
     * Constant modifier for {@code randomTick}: swaps the vanilla fully-grown age check of 15 for
     * 25.
     *
     * @param original the vanilla constant value (15)
     * @return the replacement value (25)
     */
    @ModifyConstant(method = "randomTick", constant = @Constant(intValue = 15))
    private int randomTick(int original) {
        return 25;
    }
}
