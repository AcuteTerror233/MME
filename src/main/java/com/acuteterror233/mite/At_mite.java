package com.acuteterror233.mite;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.item.AtItems;
import com.acuteterror233.mite.world.gen.feature.OverworldOrePlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 模组主入口，负责完成物品、方块等注册与基础初始化。
 *
 * <p>同时定义了若干方块与物品的最大堆叠数分类集合，
 * 用于在运行时根据类型或标识符调整堆叠规则。</p>
 */
public class At_mite implements ModInitializer {
    public static final String MOD_ID = "at_mite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE = Identifier.ofVanilla("block_interaction_range");
    public static final Identifier BASE_ENTITY_INTERACTION_RANGE = Identifier.ofVanilla("entity_interaction_range");
    /**
     * 按照方块类型划分的最大堆叠：最大堆叠为 1 的方块类型集合。
     */
    public static final Set<Class<?>> COUNT1_BLOCK = new HashSet<>(Arrays.asList(
            FenceGateBlock.class,
            BedBlock.class,
            HeavyCoreBlock.class,
            ShulkerBoxBlock.class
    ));
    /**
     * 按照方块类型划分的最大堆叠：最大堆叠为 8 的方块类型集合。
     */
    public static final Set<Class<?>> COUNT8_BLOCK = new HashSet<>(Arrays.asList(
            SlabBlock.class,
            WallBlock.class,
            DoorBlock.class,
            ConcretePowderBlock.class,
            TerracottaBlock.class,
            TintedParticleLeavesBlock.class,
            TransparentBlock.class,
            KelpBlock.class,
            KelpPlantBlock.class,
            PointedDripstoneBlock.class,
            LadderBlock.class,
            SeaPickleBlock.class,
            CandleBlock.class,
            LanternBlock.class,
            SignBlock.class,
            HangingSignBlock.class,
            ChainBlock.class,
            StainedGlassBlock.class,
            FenceBlock.class,
            OxidizableSlabBlock.class
    ));
    /**
     * 按照方块类型划分的最大堆叠：最大堆叠为 16 的方块类型集合。
     */
    public static final Set<Class<?>> COUNT16_BLOCK = new HashSet<>(Arrays.asList(
            PaneBlock.class,
            RailBlock.class,
            ButtonBlock.class,
            PressurePlateBlock.class,
            SaplingBlock.class,
            CarpetBlock.class,
            AbstractCoralBlock.class,
            NetherWartBlock.class,
            FlowerbedBlock.class,
            ScaffoldingBlock.class,
            SnowBlock.class,
            TorchBlock.class,
            MossBlock.class,
            RedstoneTorchBlock.class,
            PoweredRailBlock.class,
            DetectorRailBlock.class,
            SugarCaneBlock.class,
            BambooBlock.class,
            StainedGlassPaneBlock.class
    ));
    /**
     * 按照方块类型划分的最大堆叠：最大堆叠为 32 的方块类型集合。
     */
    public static final Set<Class<?>> COUNT32_BLOCK = new HashSet<>(Collections.singletonList(
            FungusBlock.class
    ));
    /**
     * 按照物品注册名划分的最大堆叠：最大堆叠为 1 的物品标识集合。
     */
    public static final Set<String> COUNT1_ITEM = new HashSet<>(Arrays.asList(
            "heart_of_the_sea",
            "nether_star"
    ));
    /**
     * 按照物品注册名划分的最大堆叠：最大堆叠为 8 的物品标识集合。
     */
    public static final Set<String> COUNT8_ITEM = new HashSet<>(Arrays.asList(
            "flint",
            "leather",
            "rabbit_hide",
            "honeycomb",
            "turtle_scute",
            "armadillo_scute",
            "blaze_rod",
            "breeze_rod",
            "shulker_shell",
            "blaze_powder",
            "sugar",
            "rabbit_foot",
            "glistering_melon_slice"
    ));
    /**
     * 按照物品注册名划分的最大堆叠：最大堆叠为 16 的物品标识集合。
     */
    public static final Set<String> COUNT16_ITEM = new HashSet<>(Arrays.asList(
            "iron_ingot",
            "copper_ingot",
            "gold_ingot",
            "netherite_ingot",
            "coal",
            "charcoal",
            "emerald",
            "diamond",
            "amethyst_shard",
            "netherite_scrap",
            "wheat",
            "clay_ball",
            "ender_eye",
            "bowl",
            "brick",
            "nether_brick",
            "resin_brick",
            "book",
            "glass_bottle",
            "nether_wart",
            "gunpowder",
            "experience_bottle",
            "wind_charge",
            "lead",
            "firework_rocket",
            "slime_ball",
            "raw_copper",
            "raw_gold",
            "raw_iron",
            "resin_clump"

    ));
    /**
     * 按照物品注册名划分的最大堆叠：最大堆叠为 32 的物品标识集合。
     */
    public static final Set<String> COUNT32_ITEM = new HashSet<>(Arrays.asList(
            "lapis_lazuli",
            "quartz",
            "iron_nugget",
            "gold_nugget",
            "stick",
            "bone",
            "bone_meal",
            "string",
            "feather",
            "cnowball",
            "paper",
            "redstone",
            "glowstone_dust"
    ));

    /**
     * Fabric 模组初始化回调：注册物品分组、方块、以及地下传送门的兴趣点（POI）。
     */
    @Override
    public void onInitialize() {
        LOGGER.info("mext");
        // 物品与方块注册
        AtItems.init();
        AtBlocks.init();
        // 为地下传送门注册 POI，供村民/AI 等逻辑识别
        PointOfInterestHelper.register(Identifier.of(At_mite.MOD_ID, "underground_portal"), 0, 1, AtBlocks.UNDERGROUND_PORTAL);
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                OverworldOrePlacedFeatures.OVERWORLD_ORE_SILVER
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                OverworldOrePlacedFeatures.OVERWORLD_ORE_SILVER_SMALL
        );
        extracted(OrePlacedFeatures.ORE_DIAMOND_BURIED);
        extracted(OrePlacedFeatures.ORE_DIAMOND_LARGE);
        extracted(OrePlacedFeatures.ORE_DIAMOND_MEDIUM);
    }

    private static void extracted(RegistryKey<PlacedFeature> oreDiamond) {
        BiomeModifications.create(oreDiamond.getValue()).add(
                ModificationPhase.REMOVALS,
                BiomeSelectors.foundInOverworld(),
                context -> context.getGenerationSettings().removeFeature(oreDiamond)
        );
    }
}