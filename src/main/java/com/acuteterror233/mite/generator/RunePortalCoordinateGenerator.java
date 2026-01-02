package com.acuteterror233.mite.generator;

import net.minecraft.util.math.BlockPos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 该功能由AI编写
 */
public class RunePortalCoordinateGenerator {
    /**
     * 生成在全方向均匀分布的坐标
     */
    public static BlockPos getRunePortalCoordinate(
            String seed1, String seed2, String seed3, String seed4,
            BlockPos originalCoord,
            int minDistance, int maxDistance) {

        int origX = originalCoord.getX();
        int origZ = originalCoord.getZ();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 使用顺序敏感的混合
            String input = seed1 + seed2 + seed3 + seed4;
            byte[] hash = digest.digest(input.getBytes());

            // 生成多个随机值
            double[] randoms = new double[4];
            for (int i = 0; i < 4; i++) {
                randoms[i] = bytesToDouble(hash, i * 8);
            }

            // 生成完整圆周的角度 (0 到 2π)
            double angle = randoms[0] * 2 * Math.PI;

            // 为了在圆环内均匀分布，使用平方根变换
            double u = randoms[1];
            double distance = Math.sqrt(u * (maxDistance * maxDistance - minDistance * minDistance)
                    + minDistance * minDistance);

            // 计算最终坐标 - 支持所有象限
            int newX = (int) (origX + distance * Math.cos(angle));
            int newZ = (int) (origZ + distance * Math.sin(angle));

            return new BlockPos(newX, originalCoord.getY(), newZ);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    private static double bytesToDouble(byte[] bytes, int offset) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = (value << 8) | (bytes[offset + i] & 0xFF);
        }
        return (value & 0x1FFFFFFFFFFFFFL) / (double) 0x1FFFFFFFFFFFFFL;
    }
}