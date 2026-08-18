package com.acuteterror233.mite.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link RunePortalCoordinateGenerator} 纯逻辑部分的 JUnit 测试。
 * 只覆盖依赖 Minecraft 注册表的薄封装之外的静态纯函数
 * （validateArguments / buildHashInput / sha256 / bytesToUnitDouble / computeAngle / computeDistance / computeCoordinate）。
 */
class RunePortalCoordinateGeneratorTest {

    private static final Identifier STONE = Identifier.fromNamespaceAndPath("minecraft", "stone");
    private static final Identifier DIRT = Identifier.fromNamespaceAndPath("minecraft", "dirt");
    private static final Identifier COBBLESTONE = Identifier.fromNamespaceAndPath("minecraft", "cobblestone");
    private static final Identifier BEDROCK = Identifier.fromNamespaceAndPath("minecraft", "bedrock");
    private static final Identifier GRANITE = Identifier.fromNamespaceAndPath("minecraft", "granite");
    private static final Identifier DEEPSLATE = Identifier.fromNamespaceAndPath("minecraft", "deepslate");

    // ─── validateArguments ───

    @Test
    void validateArguments_rejectsNullList() {
        assertThrows(IllegalArgumentException.class,
                () -> RunePortalCoordinateGenerator.validateArguments(null, 0, 100));
    }

    @Test
    void validateArguments_rejectsListWithLessThanFourElements() {
        assertThrows(IllegalArgumentException.class,
                () -> RunePortalCoordinateGenerator.validateArguments(List.of(STONE, DIRT, COBBLESTONE), 0, 100));
    }

    @Test
    void validateArguments_rejectsNegativeMinDistance() {
        assertThrows(IllegalArgumentException.class,
                () -> RunePortalCoordinateGenerator.validateArguments(fourKeys(), -1, 100));
    }

    @Test
    void validateArguments_rejectsMaxSmallerThanMin() {
        assertThrows(IllegalArgumentException.class,
                () -> RunePortalCoordinateGenerator.validateArguments(fourKeys(), 200, 100));
    }

    @Test
    void validateArguments_rejectsMaxBeyondLimit() {
        assertThrows(IllegalArgumentException.class,
                () -> RunePortalCoordinateGenerator.validateArguments(fourKeys(), 0,
                        RunePortalCoordinateGenerator.MAX_DISTANCE_LIMIT + 1));
    }

    @Test
    void validateArguments_acceptsBoundaryValues() {
        RunePortalCoordinateGenerator.validateArguments(fourKeys(), 0,
                RunePortalCoordinateGenerator.MAX_DISTANCE_LIMIT);
    }

    // ─── buildHashInput ───

    @Test
    void buildHashInput_producesExactlyPipeJoinedIdsWithDecimalSeed() {
        byte[] expected = "minecraft:stone|minecraft:dirt|minecraft:cobblestone|minecraft:bedrock|42"
                .getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected,
                RunePortalCoordinateGenerator.buildHashInput(fourKeys(), 42L));
    }

    @Test
    void buildHashInput_handlesNegativeSeed() {
        byte[] expected = "minecraft:stone|minecraft:dirt|minecraft:cobblestone|minecraft:bedrock|-7"
                .getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expected,
                RunePortalCoordinateGenerator.buildHashInput(fourKeys(), -7L));
    }

    @Test
    void buildHashInput_isOrderSensitive() {
        List<Identifier> swapped = List.of(DIRT, STONE, COBBLESTONE, BEDROCK);
        assertFalseArrayEquals(
                RunePortalCoordinateGenerator.buildHashInput(fourKeys(), 42L),
                RunePortalCoordinateGenerator.buildHashInput(swapped, 42L));
    }

    // ─── sha256 ───

    @Test
    void sha256_matchesKnownAnswerVectors() {
        assertArrayEquals(toBytes("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"),
                RunePortalCoordinateGenerator.sha256(new byte[0]));
        assertArrayEquals(toBytes("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"),
                RunePortalCoordinateGenerator.sha256("abc".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void sha256_isRepeatableAcrossCalls() {
        byte[] input = RunePortalCoordinateGenerator.buildHashInput(fourKeys(), 42L);
        byte[] first = RunePortalCoordinateGenerator.sha256(input);
        byte[] second = RunePortalCoordinateGenerator.sha256(input);
        assertArrayEquals(first, second);
    }

    @Test
    void sha256_goldenForKeysAndSeed() {
        assertArrayEquals(toBytes("09cb0ed8243ccf1892fb8231c4426c53f4e073d62d6ccb90d017417b1bd5065e"),
                RunePortalCoordinateGenerator.sha256(RunePortalCoordinateGenerator.buildHashInput(fourKeys(), 42L)));
    }

    // ─── bytesToUnitDouble ───

    @Test
    void bytesToUnitDouble_zeroBytesMapsToZero() {
        assertEquals(0.0, RunePortalCoordinateGenerator.bytesToUnitDouble(new byte[16], 0), 0.0);
    }

    @Test
    void bytesToUnitDouble_maximumIsBelowOne() {
        byte[] allFF = new byte[16];
        for (int i = 0; i < allFF.length; i++) {
            allFF[i] = (byte) 0xFF;
        }
        double maxRepresentable = (double) ((1L << 53) - 1) / (1L << 53);
        assertEquals(maxRepresentable, RunePortalCoordinateGenerator.bytesToUnitDouble(allFF, 0),
                0.0);
        assertTrue(RunePortalCoordinateGenerator.bytesToUnitDouble(allFF, 0) < 1.0);
    }

    @Test
    void bytesToUnitDouble_respectsOffset() {
        byte[] input = new byte[16];
        for (int i = 8; i < 16; i++) {
            input[i] = (byte) 0xFF;
        }
        assertEquals(0.0, RunePortalCoordinateGenerator.bytesToUnitDouble(input, 0), 0.0);
        assertEquals((double) ((1L << 53) - 1) / (1L << 53),
                RunePortalCoordinateGenerator.bytesToUnitDouble(input, 8), 0.0);
    }

    // ─── computeAngle ───

    @Test
    void computeAngle_ofZeroHashIsZero() {
        assertEquals(0.0, RunePortalCoordinateGenerator.computeAngle(new byte[16]), 0.0);
    }

    @Test
    void computeAngle_isInFullTurnRange() {
        double angle = RunePortalCoordinateGenerator.computeAngle(
                RunePortalCoordinateGenerator.sha256(RunePortalCoordinateGenerator.buildHashInput(fourKeys(), 42L)));
        assertTrue(angle >= 0.0);
        assertTrue(angle < 2 * Math.PI);
    }

    // ─── computeDistance ───

    @Test
    void computeDistance_equalMinAndMaxPinsToThatValue() {
        double distance = RunePortalCoordinateGenerator.computeDistance(new byte[16], 120, 120);
        assertEquals(120.0, distance, 0.0);
    }

    @Test
    void computeDistance_appliesSquareRootForAreaUniformity() {
        byte[] hash = new byte[16];
        hash[9] = 0x08; // 0x0008_0000_0000_0000 = 2^51 → 0.25 → sqrt(0.25 * (100² - 0²) + 0) == 50
        assertEquals(50.0, RunePortalCoordinateGenerator.computeDistance(hash, 0, 100), 1e-12);
    }

    @Test
    void computeDistance_staysWithinBounds() {
        double distance = RunePortalCoordinateGenerator.computeDistance(
                RunePortalCoordinateGenerator.sha256(RunePortalCoordinateGenerator.buildHashInput(fourKeys(), 42L)),
                500, 1500);
        assertTrue(distance >= 500.0);
        assertTrue(distance <= 1500.0);
    }

    // ─── computeCoordinate ───

    @Test
    void computeCoordinate_goldenVectorKeysAndSeed42() {
        BlockPos result = RunePortalCoordinateGenerator.computeCoordinate(42L, fourKeys(),
                new BlockPos(100, 64, 200), 500, 1500);
        assertNotNull(result);
        assertEquals(-693, result.getX());
        assertEquals(64, result.getY());
        assertEquals(1358, result.getZ());
    }

    @Test
    void computeCoordinate_goldenVectorDifferentKeysAndSeed() {
        BlockPos result = RunePortalCoordinateGenerator.computeCoordinate(123L,
                List.of(STONE, GRANITE, DEEPSLATE, BEDROCK),
                new BlockPos(0, 0, 0), 0, 100);
        assertNotNull(result);
        assertEquals(49, result.getX());
        assertEquals(0, result.getY());
        assertEquals(24, result.getZ());
    }

    @Test
    void computeCoordinate_isDeterministic() {
        BlockPos origin = new BlockPos(-30, 88, 1234);
        BlockPos first = RunePortalCoordinateGenerator.computeCoordinate(99L, fourKeys(), origin, 100, 900);
        BlockPos second = RunePortalCoordinateGenerator.computeCoordinate(99L, fourKeys(), origin, 100, 900);
        assertEquals(first, second);
    }

    @Test
    void computeCoordinate_preservesYAndRespectsDistanceRing() {
        BlockPos origin = new BlockPos(7, 45, -9);
        BlockPos result = RunePortalCoordinateGenerator.computeCoordinate(7L, fourKeys(), origin, 500, 1500);
        assertEquals(origin.getY(), result.getY());
        double dx = result.getX() - origin.getX();
        double dz = result.getZ() - origin.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        assertTrue(horizontal >= 500.0 - 1.0, "too close: " + horizontal);
        assertTrue(horizontal <= 1500.0 + 1.0, "too far: " + horizontal);
    }

    @Test
    void computeCoordinate_rejectsTooFewKeys() {
        assertThrows(IllegalArgumentException.class, () -> RunePortalCoordinateGenerator.computeCoordinate(
                42L, List.of(STONE, DIRT, COBBLESTONE), new BlockPos(0, 0, 0), 0, 100));
    }

    // ─── helpers ───

    private static List<Identifier> fourKeys() {
        return List.of(STONE, DIRT, COBBLESTONE, BEDROCK);
    }

    private static byte[] toBytes(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    private static void assertFalseArrayEquals(byte[] first, byte[] second) {
        assertEquals(first.length, second.length);
        boolean differ = false;
        for (int i = 0; i < first.length && !differ; i++) {
            differ = first[i] != second[i];
        }
        assertTrue(differ, "expected byte arrays to differ");
    }
}