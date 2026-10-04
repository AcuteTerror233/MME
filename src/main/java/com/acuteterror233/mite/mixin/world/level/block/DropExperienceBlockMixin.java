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

/**
 * Mixin for {@code DropExperienceBlock} — doubles the experience dropped by ore-type blocks.
 *
 * <p>At construction the final {@code xpRange} field is replaced (via {@code @Mutable @Shadow})
 * with a {@link ConstantInt} of twice its original maximum, so every block built on
 * {@code DropExperienceBlock} (ores) yields 2× XP from 0..max drops. Runs during registry setup on
 * both sides; experience itself is only awarded server-side.</p>
 */
@Mixin(DropExperienceBlock.class)
public class DropExperienceBlockMixin {
    /** Shadowed vanilla experience provider; mutable so it can be swapped at construction. */
    @Mutable
    @Shadow
    @Final
    private IntProvider xpRange;

    /**
     * Injected at the tail of both constructors: replaces the vanilla XP provider with one whose
     * maximum is doubled (minimum stays 0).
     *
     * @param intProvider the vanilla XP range passed to the constructor
     * @param properties  block behaviour properties
     * @param ci          injection callback (unused; never cancelled)
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(IntProvider intProvider, BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.xpRange = ConstantInt.of(this.xpRange.maxInclusive() * 2);
    }
}
