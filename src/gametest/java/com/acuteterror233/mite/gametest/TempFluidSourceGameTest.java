package com.acuteterror233.mite.gametest;

import com.acuteterror233.mite.item.MMETempFluidSources;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * GameTests for the temporary fluid source tracker ({@code MMETempFluidSources}):
 * a bucket-placed source block is silently removed {@code LIFETIME_TICKS} ticks after
 * placement, while a record canceled through {@code discard} (the place-source hotkey
 * path) leaves the source permanent.
 */
public class TempFluidSourceGameTest {
    /** Source position inside the test structure (relative coordinates). */
    private static final BlockPos SOURCE_REL_POS = new BlockPos(1, 1, 1);
    /** Ticks to let the server run before asserting — comfortably above the source lifetime. */
    private static final int WAIT_TICKS = MMETempFluidSources.LIFETIME_TICKS + 5;

    /**
     * A recorded temporary source is removed after its lifetime: the position must no longer
     * hold a source fluid block (it may already be re-filled by spreading flowing water, so
     * only the source state itself is asserted, not air).
     */
    @GameTest(maxTicks = 60)
    public void temporarySourceEvaporates(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        helper.setBlock(SOURCE_REL_POS, Blocks.WATER.defaultBlockState());
        MMETempFluidSources.record(level, helper.absolutePos(SOURCE_REL_POS));
        helper.startSequence().thenIdle(WAIT_TICKS).thenExecute(() -> {
            if (helper.getBlockState(SOURCE_REL_POS).getFluidState().isSource()) {
                helper.fail("Temporary source must be removed after " + MMETempFluidSources.LIFETIME_TICKS + " ticks");
                return;
            }
            helper.succeed();
        }).thenSucceed();
    }

    /** A discarded record (place-source hotkey path) leaves the source permanent. */
    @GameTest(maxTicks = 60)
    public void discardedSourceStaysPermanent(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        helper.setBlock(SOURCE_REL_POS, Blocks.WATER.defaultBlockState());
        BlockPos absolute = helper.absolutePos(SOURCE_REL_POS);
        MMETempFluidSources.record(level, absolute);
        MMETempFluidSources.discard(level, absolute);
        helper.startSequence().thenIdle(WAIT_TICKS).thenExecute(() -> {
            BlockState state = helper.getBlockState(SOURCE_REL_POS);
            if (!(state.getBlock() instanceof LiquidBlock) || !state.getFluidState().isSource()) {
                helper.fail("Discarded source must stay permanent");
                return;
            }
            helper.succeed();
        }).thenSucceed();
    }

    /**
     * Pouring a bucket onto a pre-existing (permanent) source must not doom that source:
     * vanilla {@code emptyContents} succeeds through the delegating hit-face frame that
     * writes the adjacent position, so the untouched source itself must never be recorded
     * as temporary and cleaned up one second later.
     */
    @GameTest(maxTicks = 60)
    public void pourOntoExistingSourceKeepsIt(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        helper.setBlock(SOURCE_REL_POS, Blocks.WATER.defaultBlockState());
        BlockPos absolute = helper.absolutePos(SOURCE_REL_POS);
        BlockHitResult hit = new BlockHitResult(
                new Vec3(absolute.getX() + 0.5, absolute.getY() + 0.5, absolute.getZ() + 0.5),
                Direction.UP, absolute, false);
        if (!((BucketItem) Items.WATER_BUCKET).emptyContents(null, level, absolute, hit)) {
            helper.fail("Pouring onto an existing source must succeed via the delegating frame");
            return;
        }
        helper.startSequence().thenIdle(WAIT_TICKS).thenExecute(() -> {
            BlockState state = helper.getBlockState(SOURCE_REL_POS);
            if (!(state.getBlock() instanceof LiquidBlock) || !state.getFluidState().isSource()) {
                helper.fail("Pre-existing source must survive a pour onto it");
                return;
            }
            helper.succeed();
        }).thenSucceed();
    }
}
