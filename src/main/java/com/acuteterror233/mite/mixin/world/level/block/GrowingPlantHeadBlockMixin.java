package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code GrowingPlantHeadBlock} — halves the growth speed of all growing-plant heads
 * (kelp, vines, twisting/weeping stems, cave vines...).
 *
 * <p>At construction the final {@code growPerTickProbability} field is halved (via
 * {@code @Mutable @Shadow}), so every plant head built on this class advances half as often in
 * {@code randomTick}. Server-driven via random ticks; the mixin is side-neutral.</p>
 */
@Mixin(GrowingPlantHeadBlock.class)
public class GrowingPlantHeadBlockMixin {
    /** Shadowed vanilla per-tick growth probability; mutable so it can be scaled down. */
    @Mutable
    @Shadow
    @Final
    private double growPerTickProbability;

    /**
     * Injected at the tail of the constructor: halves the vanilla growth probability.
     *
     * @param properties vanilla constructor properties
     * @param direction  growth direction of the plant head
     * @param voxelShape collision shape of the head
     * @param bl         whether the plant is scheduled on chunks (scheduleTick behavior)
     * @param d          the vanilla per-tick growth probability
     * @param ci         injection callback (unused; never cancelled)
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(BlockBehaviour.Properties properties, Direction direction, VoxelShape voxelShape, boolean bl, double d, CallbackInfo ci){
        this.growPerTickProbability = d / 2;
    }

}
