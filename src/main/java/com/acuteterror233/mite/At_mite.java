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
import net.minecraft.item.ItemKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
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

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID);
        // 物品与方块注册
        AtItems.init();
        AtBlocks.init();

        // 地下传送门注册 POI
        PointOfInterestHelper.register(Identifier.of(At_mite.MOD_ID, "underground_portal"), 0, 1, AtBlocks.UNDERGROUND_PORTAL);

        // 群系添加/删除矿石
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
        removals(OrePlacedFeatures.ORE_DIAMOND_BURIED);
        removals(OrePlacedFeatures.ORE_DIAMOND_LARGE);
        removals(OrePlacedFeatures.ORE_DIAMOND_MEDIUM);
    }
    private static void removals(RegistryKey<PlacedFeature> oreDiamond) {
        BiomeModifications.create(oreDiamond.getValue()).add(
                ModificationPhase.REMOVALS,
                BiomeSelectors.foundInOverworld(),
                context -> context.getGenerationSettings().removeFeature(oreDiamond)
        );
    }

    /**
     * 筛选掉的配方标识符集合。
     */
    public static Set<Identifier> FILTER_RECIPE_SET = Set.of(
            Identifier.ofVanilla("wooden_shovel"),
            Identifier.ofVanilla("wooden_pickaxe"),
            Identifier.ofVanilla("wooden_axe"),
            Identifier.ofVanilla("wooden_hoe"),
            Identifier.ofVanilla("wooden_sword"),
            Identifier.ofVanilla("diamond_shovel"),
            Identifier.ofVanilla("diamond_pickaxe"),
            Identifier.ofVanilla("diamond_axe"),
            Identifier.ofVanilla("diamond_hoe"),
            Identifier.ofVanilla("diamond_sword")
    );

    /**
     * 按照方块类型划分的最大堆叠映射表。
     * Key: 方块类型 Class 对象
     * Value: 最大堆叠数量
     */
    public static final Map<Class<?>, Integer> BLOCK_STACK_LIMITS = Map.<Class<?>, Integer>ofEntries(
        // 最大堆叠为 1 的方块类型
        Map.entry(FenceGateBlock.class, 1),
        Map.entry(BedBlock.class, 1),
        Map.entry(HeavyCoreBlock.class, 1),
        Map.entry(ShulkerBoxBlock.class, 1),

        // 最大堆叠为 8 的方块类型
        Map.entry(SlabBlock.class, 8),
        Map.entry(WallBlock.class, 8),
        Map.entry(TerracottaBlock.class, 8),
        Map.entry(TintedParticleLeavesBlock.class, 8),
        Map.entry(TransparentBlock.class, 8),
        Map.entry(KelpPlantBlock.class, 8),
        Map.entry(PointedDripstoneBlock.class, 8),
        Map.entry(LadderBlock.class, 8),
        Map.entry(CandleBlock.class, 8),
        Map.entry(LanternBlock.class, 8),
        Map.entry(SignBlock.class, 8),
        Map.entry(HangingSignBlock.class, 8),
        Map.entry(StainedGlassBlock.class, 8),
        Map.entry(FenceBlock.class, 8),
        Map.entry(OxidizableSlabBlock.class, 8),
        Map.entry(MangroveLeavesBlock.class, 8),
        Map.entry(UntintedParticleLeavesBlock.class, 8),
        Map.entry(SnifferEggBlock.class, 8),
        Map.entry(LilyPadBlock.class, 8),
        Map.entry(LightningRodBlock.class, 8),
        Map.entry(TintedGlassBlock.class, 8),
        Map.entry(RepeaterBlock.class, 8),
        Map.entry(ComparatorBlock.class, 8),
        Map.entry(TripwireHookBlock.class, 8),

        // 最大堆叠为 16 的方块类型
        Map.entry(PaneBlock.class, 16),
        Map.entry(RailBlock.class, 16),
        Map.entry(PressurePlateBlock.class, 16),
        Map.entry(SaplingBlock.class, 16),
        Map.entry(AbstractCoralBlock.class, 16),
        Map.entry(NetherWartBlock.class, 16),
        Map.entry(FlowerbedBlock.class, 16),
        Map.entry(ScaffoldingBlock.class, 16),
        Map.entry(SnowBlock.class, 16),
        Map.entry(TorchBlock.class, 16),
        Map.entry(MossBlock.class, 16),
        Map.entry(RedstoneTorchBlock.class, 16),
        Map.entry(PoweredRailBlock.class, 16),
        Map.entry(DetectorRailBlock.class, 16),
        Map.entry(SugarCaneBlock.class, 16),
        Map.entry(BambooBlock.class, 16),
        Map.entry(StainedGlassPaneBlock.class, 16),
        Map.entry(TrapdoorBlock.class, 16),
        Map.entry(ConcretePowderBlock.class, 16),
        Map.entry(PaleMossCarpetBlock.class, 16),
        Map.entry(HangingMossBlock.class, 16),
        Map.entry(PropaguleBlock.class, 16),
        Map.entry(ShortPlantBlock.class, 16),
        Map.entry(ShortDryGrassBlock.class, 16),
        Map.entry(TallDryGrassBlock.class, 16),
        Map.entry(BushBlock.class, 16),
        Map.entry(DryVegetationBlock.class, 16),
        Map.entry(TorchflowerBlock.class, 16),
        Map.entry(TallFlowerBlock.class, 16),
        Map.entry(WeepingVinesBlock.class, 16),
        Map.entry(VineBlock.class, 16),
        Map.entry(GlowLichenBlock.class, 16),
        Map.entry(HangingRootsBlock.class, 16),
        Map.entry(FrogspawnBlock.class, 16),
        Map.entry(TurtleEggBlock.class, 16),
        Map.entry(CoralBlock.class, 16),
        Map.entry(DeadCoralBlock.class, 16),
        Map.entry(CoralFanBlock.class, 16),
        Map.entry(DeadCoralFanBlock.class, 16),
        Map.entry(TwistingVinesBlock.class, 16),
        Map.entry(TallPlantBlock.class, 16),
        Map.entry(KelpBlock.class, 16),
        Map.entry(SeaPickleBlock.class, 16),
        Map.entry(FlowerPotBlock.class, 16),
        Map.entry(WeightedPressurePlateBlock.class, 16),
        Map.entry(LeverBlock.class, 16),
        Map.entry(ChainBlock.class, 16),

        // 最大堆叠为 32 的方块类型
        Map.entry(FungusBlock.class, 32),
        Map.entry(ButtonBlock.class, 32),
        Map.entry(CarpetBlock.class, 32),
        Map.entry(MushroomPlantBlock.class, 32),
        Map.entry(FlowerBlock.class, 32),
        Map.entry(RootsBlock.class, 32),
        Map.entry(SproutsBlock.class, 32),
        Map.entry(StemBlock.class, 32),
        Map.entry(CactusFlowerBlock.class, 32),
        Map.entry(EyeblossomBlock.class, 32),
        Map.entry(WitherRoseBlock.class, 32),
        Map.entry(LeafLitterBlock.class, 32)
    );

    /**
     * 按照物品注册名划分的最大堆叠映射表。
     * Key: 物品标识符 Identifier
     * Value: 最大堆叠数量
     */
    public static final Map<Identifier, Integer> ITEM_STACK_LIMITS = Map.<Identifier, Integer>ofEntries(
        // 最大堆叠为 1 的物品标识
        Map.entry(Identifier.ofVanilla("heart_of_the_sea"), 1),
        Map.entry(Identifier.ofVanilla("nether_star"), 1),

        // 最大堆叠为 8 的物品标识
        Map.entry(Identifier.ofVanilla("flint"), 8),
        Map.entry(Identifier.ofVanilla("leather"), 8),
        Map.entry(Identifier.ofVanilla("rabbit_hide"), 8),
        Map.entry(Identifier.ofVanilla("honeycomb"), 8),
        Map.entry(Identifier.ofVanilla("turtle_scute"), 8),
        Map.entry(Identifier.ofVanilla("armadillo_scute"), 8),
        Map.entry(Identifier.ofVanilla("blaze_rod"), 8),
        Map.entry(Identifier.ofVanilla("breeze_rod"), 8),
        Map.entry(Identifier.ofVanilla("shulker_shell"), 8),
        Map.entry(Identifier.ofVanilla("blaze_powder"), 8),
        Map.entry(Identifier.ofVanilla("sugar"), 8),
        Map.entry(Identifier.ofVanilla("rabbit_foot"), 8),
        Map.entry(Identifier.ofVanilla("glistering_melon_slice"), 8),
        Map.entry(Identifier.ofVanilla("white_wool"), 8),
        Map.entry(Identifier.ofVanilla("orange_wool"), 8),
        Map.entry(Identifier.ofVanilla("magenta_wool"), 8),
        Map.entry(Identifier.ofVanilla("light_blue_wool"), 8),
        Map.entry(Identifier.ofVanilla("yellow_wool"), 8),
        Map.entry(Identifier.ofVanilla("lime_wool"), 8),
        Map.entry(Identifier.ofVanilla("pink_wool"), 8),
        Map.entry(Identifier.ofVanilla("gray_wool"), 8),
        Map.entry(Identifier.ofVanilla("light_gray_wool"), 8),
        Map.entry(Identifier.ofVanilla("cyan_wool"), 8),
        Map.entry(Identifier.ofVanilla("purple_wool"), 8),
        Map.entry(Identifier.ofVanilla("blue_wool"), 8),
        Map.entry(Identifier.ofVanilla("brown_wool"), 8),
        Map.entry(Identifier.ofVanilla("green_wool"), 8),
        Map.entry(Identifier.ofVanilla("red_wool"), 8),
        Map.entry(Identifier.ofVanilla("black_wool"), 8),
        Map.entry(Identifier.ofVanilla("inc_sac"), 8),
        Map.entry(Identifier.ofVanilla("glow_inc_sac"), 8),
        Map.entry(Identifier.ofVanilla("prismarine_shard"), 8),
        Map.entry(Identifier.ofVanilla("popped_chorus_fruit"), 8),
        Map.entry(Identifier.ofVanilla("echo_shard"), 8),
        Map.entry(Identifier.ofVanilla("magma_cream"), 8),
        Map.entry(Identifier.ofVanilla("phantom_membrane"), 8),
        Map.entry(Identifier.ofVanilla("ghast_tear"), 8),
        Map.entry(Identifier.ofVanilla("fermented_spider_eye"), 8),
        Map.entry(Identifier.ofVanilla("acacia_planks"), 8),
        Map.entry(Identifier.ofVanilla("birch_planks"), 8),
        Map.entry(Identifier.ofVanilla("crimson_planks"), 8),
        Map.entry(Identifier.ofVanilla("dark_oak_planks"), 8),
        Map.entry(Identifier.ofVanilla("pale_oak_planks"), 8),
        Map.entry(Identifier.ofVanilla("jungle_planks"), 8),
        Map.entry(Identifier.ofVanilla("oak_planks"), 8),
        Map.entry(Identifier.ofVanilla("spruce_planks"), 8),
        Map.entry(Identifier.ofVanilla("warped_planks"), 8),
        Map.entry(Identifier.ofVanilla("mangrove_planks"), 8),

        // 最大堆叠为 16 的物品标识
        Map.entry(Identifier.ofVanilla("iron_ingot"), 16),
        Map.entry(Identifier.ofVanilla("copper_ingot"), 16),
        Map.entry(Identifier.ofVanilla("gold_ingot"), 16),
        Map.entry(Identifier.ofVanilla("netherite_ingot"), 16),
        Map.entry(Identifier.ofVanilla("coal"), 16),
        Map.entry(Identifier.ofVanilla("charcoal"), 16),
        Map.entry(Identifier.ofVanilla("emerald"), 16),
        Map.entry(Identifier.ofVanilla("diamond"), 16),
        Map.entry(Identifier.ofVanilla("amethyst_shard"), 16),
        Map.entry(Identifier.ofVanilla("netherite_scrap"), 16),
        Map.entry(Identifier.ofVanilla("wheat"), 16),
        Map.entry(Identifier.ofVanilla("clay_ball"), 16),
        Map.entry(Identifier.ofVanilla("ender_eye"), 16),
        Map.entry(Identifier.ofVanilla("bowl"), 16),
        Map.entry(Identifier.ofVanilla("brick"), 16),
        Map.entry(Identifier.ofVanilla("nether_brick"), 16),
        Map.entry(Identifier.ofVanilla("resin_brick"), 16),
        Map.entry(Identifier.ofVanilla("book"), 16),
        Map.entry(Identifier.ofVanilla("glass_bottle"), 16),
        Map.entry(Identifier.ofVanilla("nether_wart"), 16),
        Map.entry(Identifier.ofVanilla("experience_bottle"), 16),
        Map.entry(Identifier.ofVanilla("wind_charge"), 16),
        Map.entry(Identifier.ofVanilla("lead"), 16),
        Map.entry(Identifier.ofVanilla("firework_rocket"), 16),
        Map.entry(Identifier.ofVanilla("slime_ball"), 16),
        Map.entry(Identifier.ofVanilla("raw_copper"), 16),
        Map.entry(Identifier.ofVanilla("raw_gold"), 16),
        Map.entry(Identifier.ofVanilla("raw_iron"), 16),
        Map.entry(Identifier.ofVanilla("resin_clump"), 16),
        Map.entry(Identifier.ofVanilla("torchflower_seeds"), 16),
        Map.entry(Identifier.ofVanilla("pitcher_pod"), 16),
        Map.entry(Identifier.ofVanilla("glow_berries"), 16),
        Map.entry(Identifier.ofVanilla("item_frame"), 16),
        Map.entry(Identifier.ofVanilla("glow_item_frame"), 16),
        Map.entry(Identifier.ofVanilla("painting"), 16),
        Map.entry(Identifier.ofVanilla("fire_charge"), 16),
        Map.entry(Identifier.ofVanilla("name_tag"), 16),
        Map.entry(Identifier.ofVanilla("apple"), 16),
        Map.entry(Identifier.ofVanilla("golden_apple"), 16),
        Map.entry(Identifier.ofVanilla("enchanted_golden_apple"), 16),
        Map.entry(Identifier.ofVanilla("chorus_fruit"), 16),
        Map.entry(Identifier.ofVanilla("carrot"), 16),
        Map.entry(Identifier.ofVanilla("golden_carrot"), 16),
        Map.entry(Identifier.ofVanilla("potato"), 16),
        Map.entry(Identifier.ofVanilla("baked_potato"), 16),
        Map.entry(Identifier.ofVanilla("poisonous_potato"), 16),
        Map.entry(Identifier.ofVanilla("beetroot"), 16),
        Map.entry(Identifier.ofVanilla("beef"), 16),
        Map.entry(Identifier.ofVanilla("cooked_beef"), 16),
        Map.entry(Identifier.ofVanilla("porkchop"), 16),
        Map.entry(Identifier.ofVanilla("cooked_porkchop"), 16),
        Map.entry(Identifier.ofVanilla("mutton"), 16),
        Map.entry(Identifier.ofVanilla("cooked_mutton"), 16),
        Map.entry(Identifier.ofVanilla("chicken"), 16),
        Map.entry(Identifier.ofVanilla("cooked_chicken"), 16),
        Map.entry(Identifier.ofVanilla("rabbit"), 16),
        Map.entry(Identifier.ofVanilla("cooked_rabbit"), 16),
        Map.entry(Identifier.ofVanilla("cod"), 16),
        Map.entry(Identifier.ofVanilla("cooked_cod"), 16),
        Map.entry(Identifier.ofVanilla("salmon"), 16),
        Map.entry(Identifier.ofVanilla("cooked_salmon"), 16),
        Map.entry(Identifier.ofVanilla("tropical_fish"), 16),
        Map.entry(Identifier.ofVanilla("pufferfish"), 16),
        Map.entry(Identifier.ofVanilla("bread"), 16),
        Map.entry(Identifier.ofVanilla("pumpkin_pie"), 16),
        Map.entry(Identifier.ofVanilla("rotten_flesh"), 16),
        Map.entry(Identifier.ofVanilla("spider_eye"), 16),
        Map.entry(Identifier.ofVanilla("prismarine_crystals"), 16),
        Map.entry(Identifier.ofVanilla("nautilus_shell"), 16),
        Map.entry(Identifier.ofVanilla("disc_fragment_5"), 16),
        Map.entry(Identifier.ofVanilla("firework_star"), 16),

        // 最大堆叠为 32 的物品标识
        Map.entry(Identifier.ofVanilla("lapis_lazuli"), 32),
        Map.entry(Identifier.ofVanilla("quartz"), 32),
        Map.entry(Identifier.ofVanilla("iron_nugget"), 32),
        Map.entry(Identifier.ofVanilla("gold_nugget"), 32),
        Map.entry(Identifier.ofVanilla("stick"), 32),
        Map.entry(Identifier.ofVanilla("bone"), 32),
        Map.entry(Identifier.ofVanilla("bone_meal"), 32),
        Map.entry(Identifier.ofVanilla("string"), 32),
        Map.entry(Identifier.ofVanilla("feather"), 32),
        Map.entry(Identifier.ofVanilla("cnowball"), 32),
        Map.entry(Identifier.ofVanilla("paper"), 32),
        Map.entry(Identifier.ofVanilla("redstone"), 32),
        Map.entry(Identifier.ofVanilla("glowstone_dust"), 32),
        Map.entry(Identifier.ofVanilla("cookie"), 32),
        Map.entry(Identifier.ofVanilla("dried_kelp"), 32),
        Map.entry(ItemKeys.MELON_SEEDS.getValue(), 32),
        Map.entry(ItemKeys.PUMPKIN_SEEDS.getValue(), 32),
        Map.entry(Identifier.ofVanilla("wheat_seeds"), 32),
        Map.entry(Identifier.ofVanilla("cocoa_beans"), 32),
        Map.entry(Identifier.ofVanilla("beetroot_seeds"), 32),
        Map.entry(Identifier.ofVanilla("sweet_berries"), 32),
        Map.entry(Identifier.ofVanilla("seagrass"), 32),
        Map.entry(Identifier.ofVanilla("melon_slice"), 32),
        Map.entry(Identifier.ofVanilla("gunpowder"), 32)
    );

}