package com.acuteterror233.mite.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

/**
 * 符文传送门坐标生成器。
 * 计算符文传送门在目标维度的对应坐标。
 */
public class RunePortalCoordinateGenerator {
    private static final ThreadLocal<MessageDigest> SHA_256 = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    });

    /**
     * 生成在全方向均匀分布的坐标
     */
    public static BlockPos getRunePortalCoordinate(List<BlockState> list, Level world ,BlockPos originalPos, int minDistance, int maxDistance) {
        Objects.requireNonNull(list, "list");
        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(originalPos, "originalPos");
        if (list.size() < 4) {
            throw new IllegalArgumentException("list must contain at least 4 block states");
        }
        if (minDistance < 0) {
            throw new IllegalArgumentException("minDistance must be >= 0");
        }
        if (maxDistance < minDistance) {
            throw new IllegalArgumentException("maxDistance must be >= minDistance");
        }

        int origX = originalPos.getX();
        int origZ = originalPos.getZ();
        MinecraftServer server = world.getServer();
        long seed = server != null
                ? server.getWorldData().worldGenOptions().seed()
                : world.dimension().location().hashCode();

        MessageDigest digest = SHA_256.get();
        digest.reset();

        // 使用顺序敏感的混合，并统一用稳定 key 避免本地化文本影响
        String input = list.getFirst().getBlock().builtInRegistryHolder().key().location() + "|"
                + list.get(1).getBlock().builtInRegistryHolder().key().location() + "|"
                + list.get(2).getBlock().builtInRegistryHolder().key().location() + "|"
                + list.getLast().getBlock().builtInRegistryHolder().key().location() + "|"
                + seed;
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        double randomAngle = bytesToUnitDouble(hash, 0);
        double randomRadius = bytesToUnitDouble(hash, 8);

        // 生成完整圆周的角度 (0 到 2π)
        double angle = randomAngle * 2 * Math.PI;

        // 为了在圆环内均匀分布，使用平方根变换
        double minDistanceSq = (double) minDistance * minDistance;
        double maxDistanceSq = (double) maxDistance * maxDistance;
        double distance = Math.sqrt(randomRadius * (maxDistanceSq - minDistanceSq) + minDistanceSq);

        // 计算最终坐标 - 支持所有象限
        int newX = (int) Math.round(origX + distance * Math.cos(angle));
        int newZ = (int) Math.round(origZ + distance * Math.sin(angle));

        return new BlockPos(newX, originalPos.getY(), newZ);
    }

    private static double bytesToUnitDouble(byte[] bytes, int offset) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = (value << 8) | (bytes[offset + i] & 0xFF);
        }
        return (value & ((1L << 53) - 1)) / (double) (1L << 53);
    }
}