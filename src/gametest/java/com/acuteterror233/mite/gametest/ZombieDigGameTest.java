package com.acuteterror233.mite.gametest;

import com.acuteterror233.mite.world.entity.ai.pathing.DigPlan;
import com.acuteterror233.mite.world.entity.ai.pathing.ZombieDigNavigation;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;

import java.util.HashSet;
import java.util.Set;

/**
 * GameTests for the dig-aware zombie navigation ({@code ZombieDigNavigation} +
 * {@code GhostPathNavigationRegion} + {@code DigPathFinder}).
 *
 * <p>Each test resolves one route synchronously with {@code moveTo} and then inspects the
 * plan handed back by {@link ZombieDigNavigation#getPlan()}, so no AI ticking or timer is
 * involved: the assertions describe what A* planned, not how the mob behaved.</p>
 *
 * <p>Covers G1 (open path plans no digging), G2 (dirt wall is tunnelled), G3 (a one-block
 * step is walked, never dug), G4/G5 (a stone wall is only diggable while the zombie holds a
 * pickaxe) and G7 (pillared targets, two and three blocks high: the ghost search plans the
 * route through the pillar, so the plan removes it bottom-up — cap row included — and no
 * special case is needed). G6 (wall patched
 * mid-dig) stays in the manual matrix because it needs a live target and tick-by-tick
 * goal arbitration.</p>
 *
 * <p>Geometry is fully self-contained: the default {@code fabric-gametest-api-v1:empty}
 * structure is 8×8×8 of pure air, and the world around it is not guaranteed to be sealed,
 * so a wall built across the structure alone can always be walked around. Every test
 * therefore lays a stone floor and barrier walls that close a one-block-wide corridor
 * inside the structure, then uses that corridor as the only possible route. Barrier blocks
 * are unbreakable ({@code destroyTime = -1}), so {@code DigToolPolicy} rejects them and the
 * ghost region never opens them.</p>
 */
public class ZombieDigGameTest {
    /** Relative y of the corridor floor block; the mob stands on top of it. */
    private static final int FLOOR_Y = 0;
    /** Relative y of the mob's feet, the bottom wall row and the bottom barrier row. */
    private static final int STAND_Y = 1;
    /** Relative y of the mob's head, the top wall row and the top barrier row. */
    private static final int HEAD_Y = 2;
    /** Corridor z; barrier side walls sit at {@code CORRIDOR_Z ± 1}. */
    private static final int CORRIDOR_Z = 3;
    private static final int MIN_X = 0;
    private static final int MAX_X = 7;
    /** Mob start x, inside the corridor. */
    private static final int ZOMBIE_X = 1;
    /** x of the wall under test. */
    private static final int WALL_X = 3;
    /** x of the target, behind the wall. */
    private static final int TARGET_X = 5;
    private static final double SPEED = 1.0D;

    private static final BlockPos ZOMBIE_POS = new BlockPos(ZOMBIE_X, STAND_Y, CORRIDOR_Z);
    private static final BlockPos TARGET_POS = new BlockPos(TARGET_X, STAND_Y, CORRIDOR_Z);

    /** G1: an unobstructed path must stay a plain vanilla walk with no dig steps. */
    @GameTest(maxTicks = 60)
    public void openPathPlansNoDigging(GameTestHelper helper) {
        buildCorridor(helper);
        Zombie zombie = spawnZombie(helper);
        afterLanding(helper, zombie, () -> {
            planRoute(helper, zombie);
            assertPlainPath(helper, navigationOf(zombie), "An unobstructed corridor must be walked");
            helper.succeed();
        });
    }

    /** G2: a two-high dirt wall forces a dig plan whose blocks all belong to the wall. */
    @GameTest(maxTicks = 60)
    public void dirtWallIsTunnelled(GameTestHelper helper) {
        buildCorridor(helper);
        Set<BlockPos> wall = buildWall(helper, Blocks.DIRT, true);
        Zombie zombie = spawnZombie(helper);
        afterLanding(helper, zombie, () -> {
            planRoute(helper, zombie);
            assertTunnel(helper, navigationOf(zombie), wall, "dirt wall");
            helper.succeed();
        });
    }

    /** G3: a one-block step is walkable in vanilla and must never enter the dig plan. */
    @GameTest(maxTicks = 60)
    public void oneBlockStepIsWalkedNotDug(GameTestHelper helper) {
        buildCorridor(helper);
        buildWall(helper, Blocks.DIRT, false);
        Zombie zombie = spawnZombie(helper);
        afterLanding(helper, zombie, () -> {
            planRoute(helper, zombie);
            assertPlainPath(helper, navigationOf(zombie), "A one-block step must be walked, not dug");
            helper.succeed();
        });
    }

    /** G4: stone needs a correct tool, so a bare-handed zombie cannot plan through it. */
    @GameTest(maxTicks = 60)
    public void stoneWallBlocksBareHandedZombie(GameTestHelper helper) {
        buildCorridor(helper);
        buildWall(helper, Blocks.STONE, true);
        Zombie zombie = spawnZombie(helper);
        afterLanding(helper, zombie, () -> {
            planRoute(helper, zombie);
            assertNoRoute(helper, navigationOf(zombie), "Stone must not be diggable without a pickaxe");
            helper.succeed();
        });
    }

    /** G5: with a working pickaxe the same stone wall becomes a valid tunnel. */
    @GameTest(maxTicks = 60)
    public void stoneWallIsTunnelledWithPickaxe(GameTestHelper helper) {
        buildCorridor(helper);
        Set<BlockPos> wall = buildWall(helper, Blocks.STONE, true);
        Zombie zombie = spawnZombie(helper);
        // Vanilla stone/diamond/wooden tools are stripped to plain items in MME (no TOOL
        // component), so a stone pickaxe could never satisfy isCorrectToolForDrops here.
        zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        afterLanding(helper, zombie, () -> {
            planRoute(helper, zombie);
            assertTunnel(helper, navigationOf(zombie), wall, "stone wall");
            helper.succeed();
        });
    }

    /**
     * G7: a pillared-up target needs no special case — the ghost search routes the mob
     * through the pillar itself, so the plan removes the pillar starting at its bottom
     * block, which drops the target back within reach.
     */
    @GameTest(maxTicks = 60)
    public void pillaredTargetIsUnderminedFromTheBottom(GameTestHelper helper) {
        buildCorridor(helper);
        Set<BlockPos> pillar = new HashSet<>();
        for (int y = STAND_Y; y <= HEAD_Y; y++) {
            BlockPos rel = new BlockPos(TARGET_X, y, CORRIDOR_Z);
            helper.setBlock(rel, Blocks.DIRT);
            pillar.add(helper.absolutePos(rel));
        }
        // Target sits on top of the pillar, one row above the corridor ceiling.
        BlockPos targetPos = new BlockPos(TARGET_X, HEAD_Y + 1, CORRIDOR_Z);
        Zombie zombie = spawnZombie(helper);
        afterLanding(helper, zombie, () -> {
            planRouteTo(helper, zombie, targetPos);
            assertUndermining(helper, navigationOf(zombie), pillar);
            helper.succeed();
        });
    }

    /**
     * G7 companion: the cap rule — a three-high pillar is dismantled completely, cap block
     * included, because the cap row above the body column joins the plan when the sky above
     * it is open. A leftover stump would keep holding the target up.
     */
    @GameTest(maxTicks = 60)
    public void tallPillarCapJoinsThePlan(GameTestHelper helper) {
        buildCorridor(helper);
        Set<BlockPos> pillar = new HashSet<>();
        for (int y = STAND_Y; y <= HEAD_Y + 1; y++) {
            BlockPos rel = new BlockPos(TARGET_X, y, CORRIDOR_Z);
            helper.setBlock(rel, Blocks.DIRT);
            pillar.add(helper.absolutePos(rel));
        }
        // Target sits on top of the three-high pillar, two rows above the corridor ceiling.
        BlockPos targetPos = new BlockPos(TARGET_X, HEAD_Y + 2, CORRIDOR_Z);
        Zombie zombie = spawnZombie(helper);
        afterLanding(helper, zombie, () -> {
            planRouteTo(helper, zombie, targetPos);
            assertUndermining(helper, navigationOf(zombie), pillar);
            assertWholePillarPlanned(helper, navigationOf(zombie), pillar);
            helper.succeed();
        });
    }

    /**
     * G8: a dig plan must survive {@code navigation.stop()}. Goal arbitration gives the dig
     * goal the MOVE flag at priority 1, which stops the melee goal, and the melee goal's
     * {@code stop()} clears the navigation path — that must not wipe the plan the executor
     * is about to work on.
     */
    @GameTest(maxTicks = 60)
    public void planSurvivesGoalArbitration(GameTestHelper helper) {
        buildCorridor(helper);
        buildWall(helper, Blocks.DIRT, true);
        Zombie zombie = spawnZombie(helper);
        afterLanding(helper, zombie, () -> {
            planRoute(helper, zombie);
            ZombieDigNavigation navigation = navigationOf(zombie);
            DigPlan before = navigation.getPlan();
            if (before == null || !before.hasSteps()) {
                helper.fail("A dirt wall must produce a dig plan first, got " + describePlan(before));
            }
            navigation.stop(); // exactly what MeleeAttackGoal.stop() does when the dig goal wins MOVE
            DigPlan after = navigation.getPlan();
            if (after == null || !after.steps().equals(before.steps())) {
                helper.fail("The plan must survive navigation.stop(), before " + describePlan(before)
                        + ", after " + describePlan(after));
            }
            helper.succeed();
        });
    }

    private static Zombie spawnZombie(GameTestHelper helper) {
        return helper.spawn(EntityTypes.ZOMBIE, ZOMBIE_POS);
    }

    /**
     * Waits until the freshly spawned zombie actually touches the ground, then freezes it and
     * runs the assertions. {@code GroundPathNavigation.canUpdatePath()} requires {@code
     * onGround || isInLiquid || isPassenger}, and a just-spawned entity has none of these, so
     * a path request on spawn tick would silently return {@code null}. NoAI cannot be set
     * before landing either: it skips {@code travel}, so the mob would never fall onto the
     * floor.
     */
    private static void afterLanding(GameTestHelper helper, Zombie zombie, Runnable assertions) {
        helper.startSequence()
                .thenIdle(2)
                .thenExecute(() -> {
                    zombie.setNoAi(true);
                    assertions.run();
                })
                .thenSucceed();
    }

    /** Resolves one route synchronously; no AI goal runs before the plan is inspected. */
    private static void planRoute(GameTestHelper helper, Zombie zombie) {
        planRouteTo(helper, zombie, TARGET_POS);
    }

    /** Same as {@link #planRoute}, but towards an explicit relative target position. */
    private static void planRouteTo(GameTestHelper helper, Zombie zombie, BlockPos targetPos) {
        BlockPos target = helper.absolutePos(targetPos);
        navigationOf(zombie).moveTo(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D, SPEED);
    }

    private static ZombieDigNavigation navigationOf(Zombie zombie) {
        return (ZombieDigNavigation) zombie.getNavigation();
    }

    /**
     * Lays the stone floor and closes the corridor with barrier walls, so the wall under test
     * is the only way through. Everything stays inside the 8×8×8 empty structure.
     */
    private static void buildCorridor(GameTestHelper helper) {
        for (int x = MIN_X; x <= MAX_X; x++) {
            helper.setBlock(new BlockPos(x, FLOOR_Y, CORRIDOR_Z), Blocks.STONE);
            for (int y = STAND_Y; y <= HEAD_Y; y++) {
                helper.setBlock(new BlockPos(x, y, CORRIDOR_Z - 1), Blocks.BARRIER);
                helper.setBlock(new BlockPos(x, y, CORRIDOR_Z + 1), Blocks.BARRIER);
            }
        }
        for (int y = STAND_Y; y <= HEAD_Y; y++) {
            helper.setBlock(new BlockPos(MIN_X, y, CORRIDOR_Z), Blocks.BARRIER);
            helper.setBlock(new BlockPos(MAX_X, y, CORRIDOR_Z), Blocks.BARRIER);
        }
    }

    /**
     * Builds the wall across the corridor at {@link #WALL_X}.
     *
     * @param block   wall material
     * @param twoHigh true for a two-block-high wall (unwalkable in vanilla), false for a
     *                one-block step
     * @return absolute positions of every placed wall block
     */
    private static Set<BlockPos> buildWall(GameTestHelper helper, Block block, boolean twoHigh) {
        Set<BlockPos> wall = new HashSet<>();
        int topY = twoHigh ? HEAD_Y : STAND_Y;
        for (int y = STAND_Y; y <= topY; y++) {
            BlockPos rel = new BlockPos(WALL_X, y, CORRIDOR_Z);
            helper.setBlock(rel, block);
            wall.add(helper.absolutePos(rel));
        }
        return wall;
    }

    /** Fails unless the route is a plain, reachable vanilla walk with no dig steps. */
    private static void assertPlainPath(GameTestHelper helper, ZombieDigNavigation navigation,
                                        String subject) {
        Path path = navigation.getPath();
        if (path == null || !path.canReach()) {
            helper.fail(subject + ": expected a reachable vanilla path, got " + describePath(path));
            return;
        }
        DigPlan plan = navigation.getPlan();
        if (plan != null && plan.hasSteps()) {
            helper.fail(subject + ", but the plan digs: " + describePlan(plan));
        }
    }

    /** Fails unless the route is a dig plan whose blocks all belong to {@code wall}. */
    private static void assertTunnel(GameTestHelper helper, ZombieDigNavigation navigation,
                                     Set<BlockPos> wall, String wallName) {
        DigPlan plan = navigation.getPlan();
        if (plan == null || !plan.hasSteps()) {
            helper.fail("A two-high " + wallName + " must produce dig steps, got "
                    + describePlan(plan) + " / " + describePath(navigation.getPath()));
            return;
        }
        Path path = plan.path();
        if (!path.canReach()) {
            helper.fail("The " + wallName + " tunnel must reach the target, got "
                    + describePath(path));
            return;
        }
        for (DigPlan.DigStep step : plan.steps()) {
            for (BlockPos pos : step.blocks()) {
                if (!wall.contains(pos)) {
                    helper.fail("Plan digs " + pos + " which is not part of the " + wallName);
                    return;
                }
            }
        }
    }

    /** Fails when the navigation produced a reachable route or a dig plan. */
    private static void assertNoRoute(GameTestHelper helper, ZombieDigNavigation navigation,
                                      String subject) {
        Path path = navigation.getPath();
        if (path != null && path.canReach()) {
            helper.fail(subject + ", but A* reached the target: " + describePath(path));
            return;
        }
        DigPlan plan = navigation.getPlan();
        if (plan != null && plan.hasSteps()) {
            helper.fail(subject + ", but the plan digs: " + describePlan(plan));
        }
    }

    /**
     * Fails unless the ghost search produced an undermining plan: every dug block belongs
     * to the pillar, and the first step already contains the pillar's bottom block, i.e.
     * the plan removes the pillar from the bottom up instead of reaching for the block
     * right under the target's feet.
     */
    private static void assertUndermining(GameTestHelper helper, ZombieDigNavigation navigation,
                                         Set<BlockPos> pillar) {
        DigPlan plan = navigation.getPlan();
        if (plan == null || !plan.hasSteps()) {
            helper.fail("A pillared target must produce an undermining plan, got "
                    + describePlan(plan) + " / " + describePath(navigation.getPath()));
            return;
        }
        for (DigPlan.DigStep step : plan.steps()) {
            for (BlockPos pos : step.blocks()) {
                if (!pillar.contains(pos)) {
                    helper.fail("Plan digs " + pos + " which is not part of the pillar");
                    return;
                }
            }
        }
        BlockPos bottom = helper.absolutePos(new BlockPos(TARGET_X, STAND_Y, CORRIDOR_Z));
        DigPlan.DigStep first = plan.steps().getFirst();
        if (!first.blocks().contains(bottom)) {
            helper.fail("The plan must start at the pillar's bottom block " + bottom
                    + ", got " + first.blocks());
        }
    }

    /** Fails unless the plan removes every pillar block — a leftover stump would hold the target up. */
    private static void assertWholePillarPlanned(GameTestHelper helper, ZombieDigNavigation navigation,
                                                 Set<BlockPos> pillar) {
        DigPlan plan = navigation.getPlan();
        if (plan == null) {
            return; // assertUndermining already failed the test
        }
        Set<BlockPos> planned = new HashSet<>();
        for (DigPlan.DigStep step : plan.steps()) {
            planned.addAll(step.blocks());
        }
        if (!planned.containsAll(pillar)) {
            Set<BlockPos> missing = new HashSet<>(pillar);
            missing.removeAll(planned);
            helper.fail("The plan must remove the whole pillar, missing " + missing);
        }
    }

    private static String describePlan(DigPlan plan) {
        return plan == null ? "no plan (plain vanilla path)" : plan.steps().size() + " dig steps";
    }

    private static String describePath(Path path) {
        if (path == null) {
            return "no path";
        }
        return "path with " + path.getNodeCount() + " nodes, canReach=" + path.canReach();
    }
}
