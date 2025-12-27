package com.acuteterror233.mite.item;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.component.AtDataComponentTypes;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemKeys;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class VanillaItemModify {
    /**
     * 按照物品注册名划分的最大堆叠映射表。
     * Key: 物品标识符 Identifier
     * Value: 设置函数 UnaryOperator<Item.Settings>
     */
    public static final Map<Identifier, UnaryOperator<Item.Settings>> ITEM_SETTINGS_MODIFY = Map.<Identifier, UnaryOperator<Item.Settings>>ofEntries(
            // 最大堆叠为 1 的物品标识
            Map.entry(Identifier.ofVanilla("heart_of_the_sea"), settings -> settings.maxCount(1)),
            Map.entry(Identifier.ofVanilla("nether_star"), settings -> settings.maxCount(1)),

            // 最大堆叠为 8 的物品标识
            Map.entry(Identifier.ofVanilla("flint"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("leather"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("rabbit_hide"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("honeycomb"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("turtle_scute"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("armadillo_scute"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("blaze_rod"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("breeze_rod"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("shulker_shell"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("blaze_powder"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("sugar"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("rabbit_foot"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("glistering_melon_slice"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("white_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("orange_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("magenta_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("light_blue_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("yellow_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("lime_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("pink_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("gray_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("light_gray_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("cyan_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("purple_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("blue_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("brown_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("green_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("red_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("black_wool"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("inc_sac"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("glow_inc_sac"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("prismarine_shard"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("popped_chorus_fruit"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("echo_shard"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("magma_cream"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("phantom_membrane"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("ghast_tear"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("fermented_spider_eye"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("acacia_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("birch_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("crimson_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("dark_oak_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("pale_oak_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("jungle_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("oak_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("spruce_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("warped_planks"), settings -> settings.maxCount(8)),
            Map.entry(Identifier.ofVanilla("mangrove_planks"), settings -> settings.maxCount(8)),

            // 最大堆叠为 16 的物品标识
            Map.entry(Identifier.ofVanilla("iron_ingot"), settings -> settings.maxCount(16).component(AtDataComponentTypes.CRAFTING_TIME, 30)),
            Map.entry(Identifier.ofVanilla("copper_ingot"), settings -> settings.maxCount(16).component(AtDataComponentTypes.CRAFTING_TIME, 10)),
            Map.entry(Identifier.ofVanilla("gold_ingot"), settings -> settings.maxCount(16).component(AtDataComponentTypes.CRAFTING_TIME, 20)),
            Map.entry(Identifier.ofVanilla("netherite_ingot"), settings -> settings.maxCount(16).component(AtDataComponentTypes.CRAFTING_TIME, 200)),
            Map.entry(Identifier.ofVanilla("coal"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("charcoal"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("emerald"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("diamond"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("amethyst_shard"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("netherite_scrap"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("wheat"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("clay_ball"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("ender_eye"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("bowl"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("brick"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("nether_brick"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("resin_brick"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("book"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("glass_bottle"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("nether_wart"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("experience_bottle"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("wind_charge"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("lead"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("firework_rocket"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("slime_ball"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("raw_copper"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("raw_gold"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("raw_iron"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("resin_clump"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("torchflower_seeds"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("pitcher_pod"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("glow_berries"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("item_frame"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("glow_item_frame"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("painting"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("fire_charge"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("name_tag"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("apple"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("golden_apple"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("enchanted_golden_apple"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("chorus_fruit"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("carrot"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("golden_carrot"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("potato"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("baked_potato"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("poisonous_potato"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("beetroot"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("beef"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cooked_beef"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("porkchop"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cooked_porkchop"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("mutton"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cooked_mutton"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("chicken"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cooked_chicken"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("rabbit"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cooked_rabbit"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cod"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cooked_cod"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("salmon"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("cooked_salmon"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("tropical_fish"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("pufferfish"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("bread"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("pumpkin_pie"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("rotten_flesh"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("spider_eye"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("prismarine_crystals"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("nautilus_shell"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("disc_fragment_5"), settings -> settings.maxCount(16)),
            Map.entry(Identifier.ofVanilla("firework_star"), settings -> settings.maxCount(16)),

            // 最大堆叠为 32 的物品标识
            Map.entry(Identifier.ofVanilla("lapis_lazuli"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("quartz"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("iron_nugget"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("gold_nugget"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("stick"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("bone"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("bone_meal"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("string"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("feather"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("cnowball"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("paper"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("redstone"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("glowstone_dust"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("cookie"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("dried_kelp"), settings -> settings.maxCount(32)),
            Map.entry(ItemKeys.MELON_SEEDS.getValue(), settings -> settings.maxCount(32)),
            Map.entry(ItemKeys.PUMPKIN_SEEDS.getValue(), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("wheat_seeds"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("cocoa_beans"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("beetroot_seeds"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("sweet_berries"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("seagrass"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("melon_slice"), settings -> settings.maxCount(32)),
            Map.entry(Identifier.ofVanilla("gunpowder"), settings -> settings.maxCount(32)),

            Map.entry(Identifier.ofVanilla("anvil"), settings -> settings.maxDamage(AtBlocks.maxDamageAnvil(ToolMaterial.IRON.durability()))),
            Map.entry(Identifier.ofVanilla("chipped_anvil"), settings -> settings.maxDamage(AtBlocks.maxDamageAnvil(ToolMaterial.IRON.durability()))),
            Map.entry(Identifier.ofVanilla("damaged_anvil"), settings -> settings.maxDamage(AtBlocks.maxDamageAnvil(ToolMaterial.IRON.durability()))),

            Map.entry(Identifier.ofVanilla("stone_sword"), settings -> new Item.Settings()),
            Map.entry(Identifier.ofVanilla("stone_pickaxe"), settings -> new Item.Settings()),

            Map.entry(Identifier.ofVanilla("diamond_sword"), settings -> new Item.Settings()),
            Map.entry(Identifier.ofVanilla("diamond_pickaxe"), settings -> new Item.Settings()),

            Map.entry(Identifier.ofVanilla("wood_pickaxe"), settings -> new Item.Settings()),
            Map.entry(Identifier.ofVanilla("wood_sword"), settings -> new Item.Settings())

//            Map.entry(Identifier.ofVanilla("wheat_seeds"), settings -> settings.food(new FoodComponent.Builder(), ))
    );
    /**
     * 物品工厂修改映射表
     * key: 物品标识
     * value: 工厂
     */
    public static final Map<Identifier, Function<Item.Settings, Item>> ITEM_FACTORY_MODIFY = Map.ofEntries(
            Map.entry(Identifier.ofVanilla("stone_shovel"), Item::new),
            Map.entry(Identifier.ofVanilla("stone_axe"), Item::new),
            Map.entry(Identifier.ofVanilla("stone_hoe"), Item::new),

            Map.entry(Identifier.ofVanilla("diamond_shovel"), Item::new),
            Map.entry(Identifier.ofVanilla("diamond_axe"), Item::new),
            Map.entry(Identifier.ofVanilla("diamond_hoe"), Item::new),

            Map.entry(Identifier.ofVanilla("wood_axe"), Item::new),
            Map.entry(Identifier.ofVanilla("wood_hoe"), Item::new),

            Map.entry(Identifier.ofVanilla("diamond"), settings -> new GamItem(settings, 500)),
            Map.entry(Identifier.ofVanilla("emerald"), settings -> new GamItem(settings, 250)),
            Map.entry(Identifier.ofVanilla("lapis_lazuli"), settings -> new GamItem(settings, 50)),
            Map.entry(Identifier.ofVanilla("quartz"), settings -> new GamItem(settings, 50)),
            Map.entry(Identifier.ofVanilla("amethyst_shard"), settings -> new GamItem(settings, 75)),
            Map.entry(Identifier.ofVanilla("echo_shard"), settings -> new GamItem(settings, 1000))
    );
    public static final Map<Identifier, UnaryOperator<Item.Settings>> IN_IDENTIFIER_ITEM_SETTINGS_MODIFY = Map.ofEntries(
            Map.entry(Identifier.ofVanilla("iron_block"), settings -> settings.component(AtDataComponentTypes.CRAFTING_TIME, 270)),
            Map.entry(Identifier.ofVanilla("gold_block"), settings -> settings.component(AtDataComponentTypes.CRAFTING_TIME, 180)),
            Map.entry(Identifier.ofVanilla("copper_block"), settings -> settings.component(AtDataComponentTypes.CRAFTING_TIME, 90)),
            Map.entry(Identifier.ofVanilla("netherite_block"), settings -> settings.component(AtDataComponentTypes.CRAFTING_TIME, 1800))
    );

    /**
     * 方块物品设置修改映射表
     * key: 方块类型
     * value: 设置
     */
    public static final Map<Class<?>, UnaryOperator<Item.Settings>> IN_CLASS_BLOCK_ITEM_SETTINGS_MODIFY = Map.ofEntries(
            // 最大堆叠为 1 的方块类型
            Map.entry(FenceGateBlock.class, settings -> settings.maxCount(1)),
            Map.entry(BedBlock.class, settings -> settings.maxCount(1)),
            Map.entry(HeavyCoreBlock.class, settings -> settings.maxCount(1)),
            Map.entry(ShulkerBoxBlock.class, settings -> settings.maxCount(1)),

            // 最大堆叠为 8 的方块类型
            Map.entry(SlabBlock.class, settings -> settings.maxCount(8)),
            Map.entry(WallBlock.class, settings -> settings.maxCount(8)),
            Map.entry(TerracottaBlock.class, settings -> settings.maxCount(8)),
            Map.entry(TintedParticleLeavesBlock.class, settings -> settings.maxCount(8)),
            Map.entry(TransparentBlock.class, settings -> settings.maxCount(8)),
            Map.entry(KelpPlantBlock.class, settings -> settings.maxCount(8)),
            Map.entry(PointedDripstoneBlock.class, settings -> settings.maxCount(8)),
            Map.entry(LadderBlock.class, settings -> settings.maxCount(8)),
            Map.entry(CandleBlock.class, settings -> settings.maxCount(8)),
            Map.entry(LanternBlock.class, settings -> settings.maxCount(8)),
            Map.entry(SignBlock.class, settings -> settings.maxCount(8)),
            Map.entry(HangingSignBlock.class, settings -> settings.maxCount(8)),
            Map.entry(StainedGlassBlock.class, settings -> settings.maxCount(8)),
            Map.entry(FenceBlock.class, settings -> settings.maxCount(8)),
            Map.entry(OxidizableSlabBlock.class, settings -> settings.maxCount(8)),
            Map.entry(MangroveLeavesBlock.class, settings -> settings.maxCount(8)),
            Map.entry(UntintedParticleLeavesBlock.class, settings -> settings.maxCount(8)),
            Map.entry(SnifferEggBlock.class, settings -> settings.maxCount(8)),
            Map.entry(LilyPadBlock.class, settings -> settings.maxCount(8)),
            Map.entry(LightningRodBlock.class, settings -> settings.maxCount(8)),
            Map.entry(TintedGlassBlock.class, settings -> settings.maxCount(8)),
            Map.entry(RepeaterBlock.class, settings -> settings.maxCount(8)),
            Map.entry(ComparatorBlock.class, settings -> settings.maxCount(8)),
            Map.entry(TripwireHookBlock.class, settings -> settings.maxCount(8)),

            // 最大堆叠为 16 的方块类型
            Map.entry(PaneBlock.class, settings -> settings.maxCount(16)),
            Map.entry(RailBlock.class, settings -> settings.maxCount(16)),
            Map.entry(PressurePlateBlock.class, settings -> settings.maxCount(16)),
            Map.entry(SaplingBlock.class, settings -> settings.maxCount(16)),
            Map.entry(AbstractCoralBlock.class, settings -> settings.maxCount(16)),
            Map.entry(NetherWartBlock.class, settings -> settings.maxCount(16)),
            Map.entry(FlowerbedBlock.class, settings -> settings.maxCount(16)),
            Map.entry(ScaffoldingBlock.class, settings -> settings.maxCount(16)),
            Map.entry(SnowBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TorchBlock.class, settings -> settings.maxCount(16)),
            Map.entry(MossBlock.class, settings -> settings.maxCount(16)),
            Map.entry(RedstoneTorchBlock.class, settings -> settings.maxCount(16)),
            Map.entry(PoweredRailBlock.class, settings -> settings.maxCount(16)),
            Map.entry(DetectorRailBlock.class, settings -> settings.maxCount(16)),
            Map.entry(SugarCaneBlock.class, settings -> settings.maxCount(16)),
            Map.entry(BambooBlock.class, settings -> settings.maxCount(16)),
            Map.entry(StainedGlassPaneBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TrapdoorBlock.class, settings -> settings.maxCount(16)),
            Map.entry(ConcretePowderBlock.class, settings -> settings.maxCount(16)),
            Map.entry(PaleMossCarpetBlock.class, settings -> settings.maxCount(16)),
            Map.entry(HangingMossBlock.class, settings -> settings.maxCount(16)),
            Map.entry(PropaguleBlock.class, settings -> settings.maxCount(16)),
            Map.entry(ShortPlantBlock.class, settings -> settings.maxCount(16)),
            Map.entry(ShortDryGrassBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TallDryGrassBlock.class, settings -> settings.maxCount(16)),
            Map.entry(BushBlock.class, settings -> settings.maxCount(16)),
            Map.entry(DryVegetationBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TorchflowerBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TallFlowerBlock.class, settings -> settings.maxCount(16)),
            Map.entry(WeepingVinesBlock.class, settings -> settings.maxCount(16)),
            Map.entry(VineBlock.class, settings -> settings.maxCount(16)),
            Map.entry(GlowLichenBlock.class, settings -> settings.maxCount(16)),
            Map.entry(HangingRootsBlock.class, settings -> settings.maxCount(16)),
            Map.entry(FrogspawnBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TurtleEggBlock.class, settings -> settings.maxCount(16)),
            Map.entry(CoralBlock.class, settings -> settings.maxCount(16)),
            Map.entry(DeadCoralBlock.class, settings -> settings.maxCount(16)),
            Map.entry(CoralFanBlock.class, settings -> settings.maxCount(16)),
            Map.entry(DeadCoralFanBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TwistingVinesBlock.class, settings -> settings.maxCount(16)),
            Map.entry(TallPlantBlock.class, settings -> settings.maxCount(16)),
            Map.entry(KelpBlock.class, settings -> settings.maxCount(16)),
            Map.entry(SeaPickleBlock.class, settings -> settings.maxCount(16)),
            Map.entry(FlowerPotBlock.class, settings -> settings.maxCount(16)),
            Map.entry(WeightedPressurePlateBlock.class, settings -> settings.maxCount(16)),
            Map.entry(LeverBlock.class, settings -> settings.maxCount(16)),
            Map.entry(ChainBlock.class, settings -> settings.maxCount(16)),

            // 最大堆叠为 32 的方块类型
            Map.entry(FungusBlock.class, settings -> settings.maxCount(32)),
            Map.entry(ButtonBlock.class, settings -> settings.maxCount(32)),
            Map.entry(CarpetBlock.class, settings -> settings.maxCount(32)),
            Map.entry(MushroomPlantBlock.class, settings -> settings.maxCount(32)),
            Map.entry(FlowerBlock.class, settings -> settings.maxCount(32)),
            Map.entry(RootsBlock.class, settings -> settings.maxCount(32)),
            Map.entry(SproutsBlock.class, settings -> settings.maxCount(32)),
            Map.entry(StemBlock.class, settings -> settings.maxCount(32)),
            Map.entry(CactusFlowerBlock.class, settings -> settings.maxCount(32)),
            Map.entry(EyeblossomBlock.class, settings -> settings.maxCount(32)),
            Map.entry(WitherRoseBlock.class, settings -> settings.maxCount(32)),
            Map.entry(LeafLitterBlock.class, settings -> settings.maxCount(32))
    );

}
