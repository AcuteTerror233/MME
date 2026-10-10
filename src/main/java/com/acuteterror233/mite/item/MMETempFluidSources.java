package com.acuteterror233.mite.item;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Tracks bucket-placed <em>temporary</em> fluid sources: normal bucket placement (via
 * {@code BucketItem.emptyContents} — player use and dispenser placement alike) creates a source
 * block that is silently removed again after {@value #LIFETIME_TICKS} ticks, while the
 * place-source hotkey ({@code SourceBucketLogic}) discards its record so its source stays
 * permanent.
 *
 * <p>The tracker is intentionally in-memory: entries live for a single second, so persisting
 * them is not worth it. The only degradation is a server restart within that one second leaving
 * the placed source permanent (vanilla behavior). Removal is silent; the surrounding flowing
 * fluid then decays naturally because its support vanished.</p>
 */
public final class MMETempFluidSources {
    /** Ticks a bucket-placed source persists before it is removed (1 second). */
    public static final int LIFETIME_TICKS = 20;

    /** Tracked temporary sources per dimension: position -> game time at which the source is removed. */
    private static final Map<ResourceKey<Level>, Map<BlockPos, Long>> SOURCES = new HashMap<>();

    private MMETempFluidSources() {
    }

    /** Registers the server tick that removes due temporary sources. */
    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(MMETempFluidSources::tick);
    }

    /**
     * Records a freshly placed temporary source.
     *
     * @param level the server level the source was placed in
     * @param pos   the position of the placed source block
     */
    public static void record(ServerLevel level, BlockPos pos) {
        SOURCES.computeIfAbsent(level.dimension(), key -> new HashMap<>())
                .put(pos.immutable(), level.getGameTime() + LIFETIME_TICKS);
    }

    /**
     * Cancels a temporary-source record, making the placement permanent (place-source hotkey).
     *
     * @param level the server level
     * @param pos   the position to stop tracking
     */
    public static void discard(ServerLevel level, BlockPos pos) {
        Map<BlockPos, Long> map = SOURCES.get(level.dimension());
        if (map != null) {
            map.remove(pos.immutable());
        }
    }

    /**
     * Removes due temporary sources each server tick. Sources in unloaded chunks are kept until
     * their chunk loads; sources whose block was replaced by something else in the meantime are
     * dropped silently.
     *
     * @param server the ticking server
     */
    private static void tick(MinecraftServer server) {
        for (ServerLevel level : server.getAllLevels()) {
            Map<BlockPos, Long> map = SOURCES.get(level.dimension());
            if (map == null || map.isEmpty()) {
                continue;
            }
            long now = level.getGameTime();
            Iterator<Map.Entry<BlockPos, Long>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<BlockPos, Long> entry = iterator.next();
                if (entry.getValue() > now) {
                    continue;
                }
                BlockPos pos = entry.getKey();
                if (!level.hasChunkAt(pos)) {
                    continue;
                }
                iterator.remove();
                BlockState state = level.getBlockState(pos);
                if (state.getBlock() instanceof LiquidBlock && state.getFluidState().isSource()) {
                    // NOT level.removeBlock — that rebuilds the block from the position's fluid
                    // state, i.e. re-places the very source block it should clear (no-op)
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }
}
