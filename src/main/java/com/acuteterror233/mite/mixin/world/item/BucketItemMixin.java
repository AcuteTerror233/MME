package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.item.MMETempFluidSources;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code BucketItem} — MITE fluid bucket rework:
 * <ul>
 *   <li>Empty buckets raytrace fluid blocks of <em>any</em> level, not only source blocks,
 *       so flowing fluid can be scooped too.</li>
 *   <li>Placing a fluid bucket creates a <em>temporary</em> source: every successful
 *       {@code emptyContents} placement (player use and dispenser placement alike) is recorded
 *       and its source block silently removed again
 *       {@code MMETempFluidSources.LIFETIME_TICKS} ticks later, so buckets never leave
 *       permanent fluid behind. Permanent sources are only created through the place-source
 *       hotkey ({@code SourceBucketLogic}), which discards the record right after placing.</li>
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
     * Records every source block successfully placed through {@code emptyContents} as a
     * temporary source. The placement position is re-checked for a real source because
     * {@code emptyContents} can return {@code true} for a delegating call frame whose own
     * position argument holds nothing (the hit-face recursion path), and because the
     * waterlogging path never creates a source block at all.
     *
     * @param entity the placing entity, {@code null} for dispenser placement
     * @param level  the level placed into
     * @param pos    the position the source was (attempted to be) placed at
     * @param hit    the raytrace hit result, {@code null} for direct placements
     * @param cir    return value callback carrying the placement success flag
     */
    @Inject(method = "emptyContents", at = @At("RETURN"))
    private void mme$recordTemporarySource(@Nullable LivingEntity entity, Level level, BlockPos pos, @Nullable BlockHitResult hit, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && level instanceof ServerLevel serverLevel) {
            BlockState state = serverLevel.getBlockState(pos);
            if (state.getBlock() instanceof LiquidBlock && state.getFluidState().isSource()) {
                MMETempFluidSources.record(serverLevel, pos);
            }
        }
    }
}
