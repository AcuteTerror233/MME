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
 * 符文传送门坐标生成器。
 * 计算符文传送门在目标维度的对应坐标。
 *
 * <p>设计意图：用"结构内容哈希"替代随机数，实现确定性的远距离坐标生成——
 * 同一结构 + 同一世界种子 → 唯一且固定的目标点，且点落在指定圆环内按面积均匀分布。
 *
 * <p>结构：入口 {@link #getRunePortalCoordinate} 只做校验、seed 解析、key 提取三件薄事，
 * 其余全部下沉为 package-private 静态纯函数，便于 JUnit 直接测试。
 * 对外行为与旧实现逐字节兼容（哈希输入规则：前 3 + 末 4 个方块的注册表 key 以 {@code |} 拼接 + seed）。
 */
public class RunePortalCoordinateGenerator {
    /** 参数上限：防止 min/max 平方时 int 溢出（sqrt 前会转 double，但语义上限制输入范围）。 */
    static final int MAX_DISTANCE_LIMIT = 1_000_000;

    /**
     * ThreadLocal 保证线程安全；每次使用前必须 {@code reset()}（见 {@link #sha256(byte[])}），
     * 切勿改为共享静态实例而不 reset。
     */
    private static final ThreadLocal<MessageDigest> SHA_256 = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    });

    /**
     * 生成在全方向均匀分布的坐标。
     */
    public static BlockPos getRunePortalCoordinate(List<BlockState> list, Level world, BlockPos originalPos, int minDistance, int maxDistance) {
        validateArguments(list, minDistance, maxDistance);
        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(originalPos, "originalPos");
        long seed = resolveSeed(world);
        List<Identifier> keys = extractKeys(list);
        return computeCoordinate(seed, keys, originalPos, minDistance, maxDistance);
    }

    // ─── 依赖 Minecraft 的薄封装（不单测；纯逻辑部分已下沉） ───

    /**
     * 解析世界种子：有 server 时用 worldgen 种子（稳定），否则回退维度 id 哈希。
     */
    static long resolveSeed(Level world) {
        MinecraftServer server = world.getServer();
        return server != null
                ? server.getWorldGenSettings().options().seed()
                : world.dimension().identifier().hashCode();
    }

    /**
     * 提取结构中参与哈希的 4 个方块注册表 key（前 3 + 末 1）。
     * 调用方保证 list.size() >= 4（validateArguments 已挡）。
     */
    static List<Identifier> extractKeys(List<BlockState> list) {
        List<Identifier> keys = new ArrayList<>(4);
        keys.add(list.getFirst().getBlock().builtInRegistryHolder().key().identifier());
        keys.add(list.get(1).getBlock().builtInRegistryHolder().key().identifier());
        keys.add(list.get(2).getBlock().builtInRegistryHolder().key().identifier());
        keys.add(list.getLast().getBlock().builtInRegistryHolder().key().identifier());
        return List.copyOf(keys);
    }

    // ─── 纯逻辑（零 Minecraft 注册表依赖，全部可测） ───

    /**
     * 参数校验。边界：list null 或 size<4 → IAE；minDistance<0 → IAE；
     * maxDistance<minDistance → IAE；maxDistance>上限 → IAE。
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
     * 构建哈希输入：{@code key0|key1|key2|key3|seed} 的 UTF-8 字节。
     * 规则与旧实现一致（顺序敏感、seed 十进制）。
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
     * 标准 SHA-256。每次调用 reset（线程安全约定见 {@link #SHA_256}）。
     */
    static byte[] sha256(byte[] input) {
        MessageDigest digest = SHA_256.get();
        digest.reset();
        return digest.digest(input);
    }

    /**
     * 将哈希的 8 个字节（大端）映射到 [0,1) 均匀分布。
     */
    static double bytesToUnitDouble(byte[] hash, int offset) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = (value << 8) | (hash[offset + i] & 0xFF);
        }
        return (value & ((1L << 53) - 1)) / (double) (1L << 53);
    }

    /**
     * 角度：全圆周均匀分布 [0, 2π)。
     */
    static double computeAngle(byte[] hash) {
        return bytesToUnitDouble(hash, 0) * 2 * Math.PI;
    }

    /**
     * 距离：平方根变换使目标在圆环内按面积均匀分布；min==max 时恒等于 min。
     */
    static double computeDistance(byte[] hash, int minDistance, int maxDistance) {
        double minDistanceSq = (double) minDistance * minDistance;
        double maxDistanceSq = (double) maxDistance * maxDistance;
        return Math.sqrt(bytesToUnitDouble(hash, 8) * (maxDistanceSq - minDistanceSq) + minDistanceSq);
    }

    /**
     * 核心编排（纯函数）：hash → 角度/距离 → BlockPos。
     * 仅依赖 keys + seed + originalPos，可脱离 Minecraft 环境测试。
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
