package com.acuteterror233.mite.world.entity.ai.pathing;

import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;

/**
 * Stateless diggability and dig-duration rules shared by the path planner and the dig goal.
 *
 * <p>Diggability mirrors the vanilla harvest rule: a block may be dug when the zombie would
 * still receive its drops, i.e. {@code !requiresCorrectToolForDrops() || isCorrectToolForDrops}.
 * Blocks that need a correct tool (stone, ores) stay locked to bare hands, while dirt, sand,
 * wood and leaves are always diggable.</p>
 *
 * <p>Dig duration is linear in block hardness and inversely proportional to the held tool's
 * mining speed, with bare hands (speed 1.0) taking {@value #BASE_TICKS_PER_HARDNESS} ticks per
 * hardness point, so dirt (0.5) needs 1500 ticks &asymp; 75 s.</p>
 */
public final class DigToolPolicy {
    /** Bare-hand ticks per hardness point: dirt (0.5) takes 1500 t = 75 s, above one minute. */
    public static final float BASE_TICKS_PER_HARDNESS = 3000.0F;
    /** Lower bound of a single block's dig time, in ticks. */
    public static final int MIN_DIG_TICKS = 20;
    /** Hardness above which a block is never dug (excludes obsidian at 50 and stronger). */
    public static final float HARDNESS_CAP = 5.0F;

    private DigToolPolicy() {
    }

    /**
     * Full runtime gate: mob griefing must be enabled and the block must pass
     * {@link #isDiggable}.
     */
    public static boolean canDig(ServerLevel level, BlockPos pos, BlockState state, ItemStack tool) {
        return level.getGameRules().get(GameRules.MOB_GRIEFING)
                && isDiggable(level, pos, state, tool);
    }

    /**
     * Block-side gate without the game rule check, so it can be exercised without a level:
     * never dig tag-forbidden blocks, block entities (chests, furnaces — an item-duplication
     * vector), unbreakable or too-hard blocks, or blocks that need a tool the zombie lacks.
     */
    public static boolean isDiggable(BlockGetter level, BlockPos pos, BlockState state, ItemStack tool) {
        return isDiggable(
                state.is(MMEBlockTags.ZOMBIE_DIG_FORBIDDEN),
                state.hasBlockEntity(),
                state.getDestroySpeed(level, pos),
                state.requiresCorrectToolForDrops(),
                tool.isCorrectToolForDrops(state));
    }

    /**
     * Pure core of {@link #isDiggable(BlockGetter, BlockPos, BlockState, ItemStack)}: the
     * tag/component lookups are done by the caller, so this overload can be unit tested
     * without a bound registry, tag set or data-component map.
     */
    public static boolean isDiggable(boolean tagForbidden, boolean hasBlockEntity, float hardness,
                                     boolean requiresCorrectTool, boolean toolCanHarvest) {
        if (tagForbidden || hasBlockEntity) {
            return false;
        }
        if (hardness < 0.0F || hardness > HARDNESS_CAP) {
            return false;
        }
        return !requiresCorrectTool || toolCanHarvest;
    }

    /** Ticks needed to dig the given block with the given held stack. */
    public static int digTicks(BlockGetter level, BlockPos pos, BlockState state, ItemStack tool) {
        return digTicks(state.getDestroySpeed(level, pos), tool.getDestroySpeed(state));
    }

    /** Pure arithmetic core of {@link #digTicks(BlockGetter, BlockPos, BlockState, ItemStack)}. */
    public static int digTicks(float hardness, float toolSpeed) {
        float clampedHardness = Math.max(0.1F, hardness);
        float clampedSpeed = Math.max(1.0F, toolSpeed);
        return Math.max(MIN_DIG_TICKS, (int) (clampedHardness * BASE_TICKS_PER_HARDNESS / clampedSpeed));
    }
}
