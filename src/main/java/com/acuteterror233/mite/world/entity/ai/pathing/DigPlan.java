package com.acuteterror233.mite.world.entity.ai.pathing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.Path;

import java.util.List;

/**
 * The product of one dig-aware search: the path plus the per-node blocks that have to be
 * removed to walk it, in path order.
 *
 * @param path  the path the navigation layer is executing
 * @param steps one {@link DigStep} per path node that needs digging (empty for plain walks)
 */
public record DigPlan(Path path, List<DigStep> steps) {
    /**
     * Blocks to remove at one path node and their raw dig time (ticks, no cost factor).
     * A step is normally one block (feet) or two (feet + head).
     */
    public record DigStep(List<BlockPos> blocks, int totalDigTicks) {
    }

    /** Whether this path needs any digging; a step-less plan means plain navigation. */
    public boolean hasSteps() {
        return !this.steps.isEmpty();
    }
}
