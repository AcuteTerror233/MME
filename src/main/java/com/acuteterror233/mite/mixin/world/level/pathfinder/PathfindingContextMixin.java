package com.acuteterror233.mite.mixin.world.level.pathfinder;

import com.acuteterror233.mite.world.entity.ai.pathing.GhostPathPass;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Bypasses {@code ServerLevel}'s shared {@code PathTypeCache} while a ghost path pass runs.
 *
 * <p>Measured on 26.3: when {@code mob.level()} is a {@code ServerLevel},
 * {@code getPathTypeFromState} only reads {@code ServerLevel.getPathTypeCache()}, and that
 * cache is keyed by block position alone. In a two-pass search, pass 1 has already cached the
 * wall positions as {@code BLOCKED} against the real world; pass 2 hits those stale entries,
 * so the ghost region is never read and the dig tunnel never becomes visible. This injection
 * recomputes the path type straight from the ghost region ({@code currentContext.level()},
 * i.e. {@code GhostPathNavigationRegion}) while the ghost pass runs, with semantics identical
 * to the cache-miss branch of {@code PathTypeCache.compute}.</p>
 *
 * <p>The injection is inert unless {@link GhostPathPass#isActive()} is true; ordinary
 * pathfinding still goes through the vanilla cache, so no non-dig path changes cost or
 * result.</p>
 */
@Mixin(PathfindingContext.class)
public abstract class PathfindingContextMixin {
    /** Reusable position for the per-query lookup; one path search issues tens of thousands. */
    @Unique
    private final BlockPos.MutableBlockPos mme$queryPos = new BlockPos.MutableBlockPos();

    @Inject(method = "getPathTypeFromState", at = @At("HEAD"), cancellable = true)
    private void mme$bypassSharedCacheDuringGhostPass(int x, int y, int z,
                                                      CallbackInfoReturnable<PathType> cir) {
        if (!GhostPathPass.isActive()) {
            return;
        }
        PathfindingContext self = (PathfindingContext) (Object) this;
        cir.setReturnValue(WalkNodeEvaluator.getPathTypeFromState(self.level(),
                this.mme$queryPos.set(x, y, z)));
    }
}
