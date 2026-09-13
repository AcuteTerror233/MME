package com.acuteterror233.mite.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rune portal coordinate generator.
 * Calculates the corresponding coordinates of a rune portal in the target dimension.
 *
 * <p>Design intent: Replace random numbers with "structure content hash" to achieve deterministic long-distance coordinate generation—
 * same structure + same world seed → unique and fixed target point, with points uniformly distributed by area within the specified circular ring.
 *
 * <p>Structure: The entry point {@link #getRunePortalCoordinate} only does three lightweight tasks: validation, seed parsing, and key extraction.
 * Everything else is delegated to package-private static pure functions for easy direct JUnit testing.
 * External behavior is byte-compatible with the old implementation (hash input rules: first 3 + last 4 block registry keys joined with {@code |} + seed).
 */
public class RunePortalCoordinateGenerator {
    /** Parameter upper limit: prevents int overflow when squaring min/max (converts to double before sqrt, but semantically restricts input range). */
    static final int MAX_DISTANCE_LIMIT = 1_000_000;

    /**
     * ThreadLocal ensures thread safety; must {@code reset()} before each use (see {@link #sha256(byte[])}).
     * Never change to a shared static instance without reset.
     */
    private static final ThreadLocal<MessageDigest> SHA_256 = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    });

    /**
     * Generates coordinates uniformly distributed in all directions.
     */
    public static BlockPos getRunePortalCoordinate(List<BlockState> list, Level world, BlockPos originalPos, int minDistance, int maxDistance) {
        validateArguments(list, minDistance, maxDistance);
        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(originalPos, "originalPos");
        long seed = resolveSeed(world);
        List<Identifier> keys = extractKeys(list);
        return computeCoordinate(seed, keys, originalPos, minDistance, maxDistance);
    }

    // ─── Thin wrappers depending on Minecraft (not unit tested; pure logic has been delegated) ───

    /**
     * Resolves world seed: uses worldgen seed when server is available (stable), otherwise falls back to dimension id hash.
     */
    static long resolveSeed(Level world) {
        MinecraftServer server = world.getServer();
        return server != null
                ? server.getWorldGenSettings().options().seed()
                : world.dimension().identifier().hashCode();
    }

    /**
     * Extracts 4 block registry keys participating in the hash from the structure (first 3 + last 1).
     * Caller ensures list.size() >= 4 (validateArguments already guards this).
     */
    static List<Identifier> extractKeys(List<BlockState> list) {
        List<Identifier> keys = new ArrayList<>(4);
        keys.add(list.getFirst().getBlock().builtInRegistryHolder().key().identifier());
        keys.add(list.get(1).getBlock().builtInRegistryHolder().key().identifier());
        keys.add(list.get(2).getBlock().builtInRegistryHolder().key().identifier());
        keys.add(list.getLast().getBlock().builtInRegistryHolder().key().identifier());
        return List.copyOf(keys);
    }

    // ─── Pure logic (zero Minecraft registry dependency, all testable) ───

    /**
     * Parameter validation. Boundaries: list null or size<4 → IAE; minDistance<0 → IAE;
     * maxDistance<minDistance → IAE; maxDistance>upper limit → IAE.
     */
    static void validateArguments(List<?> list, int minDistance, int maxDistance) {
        if (list == null || list.size() < 4) {
            throw new IllegalArgumentException("list must contain at least 4 block states");
        }
        if (minDistance < 0) {
            throw new IllegalArgumentException("minDistance must be >= 0");
        }
        if (maxDistance < minDistance) {
            throw new IllegalArgumentException("maxDistance must be >= minDistance");
        }
        if (maxDistance > MAX_DISTANCE_LIMIT) {
            throw new IllegalArgumentException("maxDistance must be <= " + MAX_DISTANCE_LIMIT);
        }
    }

    /**
     * Builds hash input: UTF-8 bytes of {@code key0|key1|key2|key3|seed}.
     * Rules are consistent with the old implementation (order-sensitive, seed in decimal).
     */
    static byte[] buildHashInput(List<Identifier> keys, long seed) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(keys.get(i));
        }
        sb.append('|').append(seed);
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Standard SHA-256. Resets on each call (thread safety convention see {@link #SHA_256}).
     */
    static byte[] sha256(byte[] input) {
        MessageDigest digest = SHA_256.get();
        digest.reset();
        return digest.digest(input);
    }

    /**
     * Maps 8 bytes of hash (big-endian) to uniform distribution in [0,1).
     */
    static double bytesToUnitDouble(byte[] hash, int offset) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = (value << 8) | (hash[offset + i] & 0xFF);
        }
        return (value & ((1L << 53) - 1)) / (double) (1L << 53);
    }

    /**
     * Angle: uniform distribution over full circle [0, 2π).
     */
    static double computeAngle(byte[] hash) {
        return bytesToUnitDouble(hash, 0) * 2 * Math.PI;
    }

    /**
     * Distance: square root transformation makes targets uniformly distributed by area within the ring; equals min when min==max.
     */
    static double computeDistance(byte[] hash, int minDistance, int maxDistance) {
        double minDistanceSq = (double) minDistance * minDistance;
        double maxDistanceSq = (double) maxDistance * maxDistance;
        return Math.sqrt(bytesToUnitDouble(hash, 8) * (maxDistanceSq - minDistanceSq) + minDistanceSq);
    }

    /**
     * Core orchestration (pure function): hash → angle/distance → BlockPos.
     * Only depends on keys + seed + originalPos, can be tested outside Minecraft environment.
     */
    static BlockPos computeCoordinate(long seed, List<Identifier> keys, BlockPos originalPos, int minDistance, int maxDistance) {
        if (keys == null || keys.size() < 4) {
            throw new IllegalArgumentException("keys must contain at least 4 identifiers");
        }
        byte[] hash = sha256(buildHashInput(keys, seed));
        double angle = computeAngle(hash);
        double distance = computeDistance(hash, minDistance, maxDistance);
        int newX = (int) Math.round(originalPos.getX() + distance * Math.cos(angle));
        int newZ = (int) Math.round(originalPos.getZ() + distance * Math.sin(angle));
        return new BlockPos(newX, originalPos.getY(), newZ);
    }
}
