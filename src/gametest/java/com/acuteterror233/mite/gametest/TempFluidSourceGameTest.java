package com.acuteterror233.mite.gametest;

import com.acuteterror233.mite.item.MMETempFluidSources;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

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
}
