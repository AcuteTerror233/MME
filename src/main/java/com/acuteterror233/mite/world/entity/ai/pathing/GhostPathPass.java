package com.acuteterror233.mite.world.entity.ai.pathing;

/**
 * Switch that tells the path-type lookup to bypass {@code ServerLevel}'s shared
 * {@code PathTypeCache} while a ghost pass is running.
 *
 * <p>Measured on 26.3: when {@code mob.level()} is a {@code ServerLevel},
 * {@code PathfindingContext} delegates every path-type query to
 * {@code ServerLevel.getPathTypeCache()}, and that cache is <strong>keyed by block position only</strong>
 * (a 4096-entry direct map). During a two-pass search, pass 1 has already resolved these
 * positions against the real world and cached them as {@code BLOCKED}; pass 2 then hits the
 * stale entry for the same coordinate, {@code getBlockState} on the ghost region is never
 * consulted, and the dig tunnel stays invisible to A* — which surfaces as a pass-2 result
 * holding nothing but the start node plus one unreachable node.</p>
 *
 * <p>A process-wide flag (pathfinding only runs on the server thread) declares "this query
 * must use the ghost view, not the shared cache"; {@code PathfindingContextMixin} reads it at
 * the head of {@code getPathTypeFromState}.</p>
 */
public final class GhostPathPass {
    private static boolean active;

    private GhostPathPass() {
    }

    public static void begin() {
        active = true;
    }

    public static void end() {
        active = false;
    }

    public static boolean isActive() {
        return active;
    }
}
