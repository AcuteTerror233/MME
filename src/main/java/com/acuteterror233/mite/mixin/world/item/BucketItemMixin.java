package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.item.MMETempFluidSources;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code BucketItem} — MITE fluid bucket rework:
 * <ul>
 *   <li>Empty buckets raytrace fluid blocks of <em>any</em> level, not only source blocks,
 *       so flowing fluid can be scooped too.</li>
 *   <li>Placing a fluid bucket creates a <em>temporary</em> source: the single placement
 *       {@code setBlock} call inside {@code emptyContents} is wrapped, so only a source block
 *       actually written by a placement (player use and dispenser placement alike) is recorded
 *       and silently removed again {@code MMETempFluidSources.LIFETIME_TICKS} ticks later.
 *       Wrapping the write itself — instead of hooking {@code emptyContents} returns — keeps
 *       pre-existing source blocks safe: pouring onto a source only succeeds via the
 *       delegating hit-face frame that writes a different position, and a return-value hook
 *       would wrongly doom the untouched source to the cleanup. The waterlogging path
 *       ({@code placeLiquid}) never writes a source block and is not wrapped. Permanent
 *       sources are only created through the place-source hotkey ({@code SourceBucketLogic}),
 *       which discards the record right after placing.</li>
 * </ul>
 *
 * <p>The source state itself is kept (instead of a demoted flowing level) because the vanilla
 * fluid tick deletes a non-source block at once when no same-fluid neighbor feeds it — an
 * isolated pour has none, so a demoted placement could not even spread.</p>
 */
@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    /**
     * Redirects the empty-bucket fluid raytrace from source-only to any fluid level.
     *
     * @param cir return value callback carrying the vanilla clip mode ({@code SOURCE_ONLY} for empty buckets)
     */
    @Inject(method = "getFluidContext", at = @At("RETURN"), cancellable = true)
    private void mme$targetAnyFluidLevel(CallbackInfoReturnable<ClipContext.Fluid> cir) {
        if (cir.getReturnValue() == ClipContext.Fluid.SOURCE_ONLY) {
            cir.setReturnValue(ClipContext.Fluid.ANY);
        }
    }

    /**
     * Wraps the single {@code Level.setBlock} call through which {@code emptyContents} actually
     * writes fluid, recording every written source block as a temporary source. Hooking the
     * write itself skips call frames that succeed without writing their own position argument
     * (the delegating hit-face path), so pouring onto a pre-existing source never marks that
     * source for removal, and the waterlogging branch ({@code placeLiquid}) stays unrecorded.
     *
     * @param level    the level written into
     * @param pos      the position written
     * @param state    the block state written (the placed fluid's legacy block)
     * @param flags    the setBlock flags
     * @param original the wrapped vanilla invocation
     * @return whether the write succeeded
     */
    @WrapOperation(method = "emptyContents", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private boolean mme$recordOnRealPlacement(Level level, BlockPos pos, BlockState state, int flags, Operation<Boolean> original) {
        boolean placed = original.call(level, pos, state, flags);
        if (placed && level instanceof ServerLevel serverLevel
                && state.getBlock() instanceof LiquidBlock && state.getFluidState().isSource()) {
            MMETempFluidSources.record(serverLevel, pos);
        }
        return placed;
    }
}
