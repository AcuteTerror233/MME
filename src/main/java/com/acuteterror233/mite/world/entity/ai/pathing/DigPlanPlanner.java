package com.acuteterror233.mite.world.entity.ai.pathing;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Expands a ghost-region path into dig steps: for every path node, the solid blocks the
 * zombie's body column covers at that node, filtered to the ones the ghost view actually
 * reported as air (everything else is already passable in the real world).
 *
 * <p>Dig targets come from the path only — there is no straight-line scanning here — so the
 * executor digs exactly the blocks A* paid for while planning.</p>
 *
 * <p>One more row — the "cap" — joins the column when it is diggable and the row above it is
 * really transparent: a three-block pillar loses its top block as well, so a target standing
 * on it falls once the column is gone, while a solid ceiling above naturally rejects the cap
 * (corridor tops are never chewed open). Walls and pillars are completed by the same column
 * rule — there is no pillaring special case. The executor can keep up: standing at the node,
 * the cap sits exactly one row above the head, inside the one-block dig reach.</p>
 */
final class DigPlanPlanner {
    private DigPlanPlanner() {
    }

    static DigPlan plan(Path path, GhostPathNavigationRegion ghost, Mob mob, DigSession session) {
        List<DigPlan.DigStep> steps = new ArrayList<>();
        for (int i = 0; i < path.getNodeCount(); i++) {
            List<BlockPos> blocks = ghostedColumn(path.getNode(i), ghost, mob);
            if (!blocks.isEmpty()) {
                steps.add(new DigPlan.DigStep(blocks, session.sumDigTicks(blocks)));
            }
        }
        return new DigPlan(path, steps);
    }

    /**
     * Blocks the mob must remove to occupy {@code node}: its body column (feet row plus every
     * row the bounding box reaches) plus the cap row above the head, ghosted only.
     */
    static List<BlockPos> ghostedColumn(Node node, GhostPathNavigationRegion ghost, Mob mob) {
        // A zombie is 0.6 wide, so one column is enough; only the height needs more rows.
        int rows = Mth.ceil(mob.getBbHeight());
        List<BlockPos> out = new ArrayList<>(rows + 1);
        for (int dy = 0; dy < rows; dy++) {
            int y = node.y + dy;
            if (ghost.isGhosted(node.x, y, node.z)) {
                out.add(new BlockPos(node.x, y, node.z));
            }
        }
        int capY = node.y + rows;
        if (ghost.isGhosted(node.x, capY, node.z) && !ghost.isReallySolid(node.x, capY + 1, node.z)) {
            out.add(new BlockPos(node.x, capY, node.z));
        }
        return out;
    }
}
