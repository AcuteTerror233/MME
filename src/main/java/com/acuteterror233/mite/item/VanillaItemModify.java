package com.acuteterror233.mite.item;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.component.MMEDataComponentTypes;
import com.acuteterror233.mite.item.armor.MMEArmorMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.Items;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class VanillaItemModify {
    public static Map<Class<?>, UnaryOperator<Item.Properties>> IN_CLASS_BLOCK_ITEM_SETTINGS_MODIFY = createBlockItemSettingsModifyMap();
    public static final Map<ResourceLocation, UnaryOperator<Item.Properties>> IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY = createBlockItemSettingsModifyMapByIdentifier();
    public static final Map<ResourceLocation, Function<Item.Properties, Item>> ITEM_FACTORY_MODIFY = createItemFactoryModifyMap();
    public static final Map<ResourceLocation, UnaryOperator<Item.Properties>> ITEM_SETTINGS_MODIFY = createItemSettingsModifyMap();
    /**
     * 按照物品注册名划分的最大堆叠映射表。
     * Key: 物品标识符 Identifier
     * Value: 设置函数 UnaryOperator<Item.Settings>
     */

    private static Map<ResourceLocation, UnaryOperator<Item.Properties>> createItemSettingsModifyMap() {
        Map<ResourceLocation, UnaryOperator<Item.Properties>> result = new HashMap<>();

        // 最大堆叠为 1 的物品标识
        result.put(ResourceLocation.withDefaultNamespace("heart_of_the_sea"), settings -> settings.stacksTo(1));
        result.put(ResourceLocation.withDefaultNamespace("nether_star"), settings -> settings.stacksTo(1));

        // 最大堆叠为 8 的物品标识
        result.put(ResourceLocation.withDefaultNamespace("flint"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("leather"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("rabbit_hide"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("honeycomb"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("turtle_scute"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("armadillo_scute"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("blaze_rod"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("breeze_rod"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("shulker_shell"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("blaze_powder"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("sugar"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("rabbit_foot"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("glistering_melon_slice"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("white_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("orange_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("magenta_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("light_blue_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("yellow_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("lime_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("pink_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("gray_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("light_gray_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("cyan_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("purple_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("blue_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("brown_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("green_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("red_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("black_wool"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("inc_sac"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("glow_inc_sac"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("prismarine_shard"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("popped_chorus_fruit"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("echo_shard"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("magma_cream"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("phantom_membrane"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("ghast_tear"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("fermented_spider_eye"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("acacia_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("birch_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("crimson_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("dark_oak_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("pale_oak_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("jungle_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("oak_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("spruce_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("warped_planks"), settings -> settings.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("mangrove_planks"), settings -> settings.stacksTo(8));

        // 最大堆叠为 16 的物品标识
        result.put(ResourceLocation.withDefaultNamespace("iron_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 30));
        result.put(ResourceLocation.withDefaultNamespace("copper_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 10));
        result.put(ResourceLocation.withDefaultNamespace("gold_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 20));
        result.put(ResourceLocation.withDefaultNamespace("netherite_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 200));
        result.put(ResourceLocation.withDefaultNamespace("coal"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("charcoal"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("emerald"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("diamond"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("amethyst_shard"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("netherite_scrap"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("wheat"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("clay_ball"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("ender_eye"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("bowl"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("brick"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("nether_brick"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("resin_brick"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("book"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("glass_bottle"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("nether_wart"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("experience_bottle"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("wind_charge"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("lead"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("firework_rocket"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("slime_ball"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("raw_copper"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("raw_gold"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("raw_iron"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("resin_clump"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("torchflower_seeds"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("pitcher_pod"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("glow_berries"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("item_frame"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("glow_item_frame"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("painting"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("fire_charge"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("name_tag"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("apple"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("golden_apple"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("enchanted_golden_apple"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("chorus_fruit"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("carrot"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("golden_carrot"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("potato"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("baked_potato"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("poisonous_potato"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("beetroot"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("beef"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cooked_beef"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("porkchop"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cooked_porkchop"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("mutton"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cooked_mutton"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("chicken"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cooked_chicken"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("rabbit"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cooked_rabbit"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cod"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cooked_cod"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("salmon"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("cooked_salmon"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("tropical_fish"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("pufferfish"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("bread"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("pumpkin_pie"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("rotten_flesh"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("spider_eye"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("prismarine_crystals"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("nautilus_shell"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("disc_fragment_5"), settings -> settings.stacksTo(16));
        result.put(ResourceLocation.withDefaultNamespace("firework_star"), settings -> settings.stacksTo(16));

        // 最大堆叠为 32 的物品标识
        result.put(ResourceLocation.withDefaultNamespace("lapis_lazuli"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("quartz"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("stick"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("bone"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("bone_meal"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("string"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("feather"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("cnowball"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("paper"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("redstone"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("glowstone_dust"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("cookie"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("dried_kelp"), settings -> settings.stacksTo(32));
        result.put(Items.MELON_SEEDS.location(), settings -> settings.stacksTo(32).food(new FoodProperties.Builder().nutrition(1).saturationModifier(1.0F).alwaysEdible().build()));
        result.put(Items.PUMPKIN_SEEDS.location(), settings -> settings.stacksTo(32).food(new FoodProperties.Builder().nutrition(1).saturationModifier(1.0F).alwaysEdible().build()));
        result.put(ResourceLocation.withDefaultNamespace("wheat_seeds"), settings -> settings.stacksTo(32).food(new FoodProperties.Builder().nutrition(1).saturationModifier(1.0F).alwaysEdible().build()));
        result.put(ResourceLocation.withDefaultNamespace("cocoa_beans"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("beetroot_seeds"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("sweet_berries"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("seagrass"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("melon_slice"), settings -> settings.stacksTo(32));
        result.put(ResourceLocation.withDefaultNamespace("gunpowder"), settings -> settings.stacksTo(32));

        result.put(ResourceLocation.withDefaultNamespace("anvil"), settings -> settings.durability(MMEBlocks.maxDamageAnvil(ToolMaterial.IRON.durability())));
        result.put(ResourceLocation.withDefaultNamespace("chipped_anvil"), settings -> settings.durability(MMEBlocks.maxDamageAnvil(ToolMaterial.IRON.durability())));
        result.put(ResourceLocation.withDefaultNamespace("damaged_anvil"), settings -> settings.durability(MMEBlocks.maxDamageAnvil(ToolMaterial.IRON.durability())));

        result.put(ResourceLocation.withDefaultNamespace("stone_sword"), settings -> new Item.Properties());
        result.put(ResourceLocation.withDefaultNamespace("stone_pickaxe"), settings -> new Item.Properties());
        result.put(ResourceLocation.withDefaultNamespace("diamond_sword"), settings -> new Item.Properties());
        result.put(ResourceLocation.withDefaultNamespace("diamond_pickaxe"), settings -> new Item.Properties());
        result.put(ResourceLocation.withDefaultNamespace("wooden_pickaxe"), settings -> new Item.Properties());
        result.put(ResourceLocation.withDefaultNamespace("wooden_sword"), settings -> new Item.Properties());
        result.put(ResourceLocation.withDefaultNamespace("netherite_sword"), settings -> MMEItems.getSwordSettings(MMEToolMaterials.NETHERITE));
        result.put(ResourceLocation.withDefaultNamespace("netherite_pickaxe"), settings -> MMEItems.getPickaxeSettings(MMEToolMaterials.NETHERITE));

        result.put(ResourceLocation.withDefaultNamespace("netherite_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.HELMET));
        result.put(ResourceLocation.withDefaultNamespace("netherite_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.CHESTPLATE));
        result.put(ResourceLocation.withDefaultNamespace("netherite_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.LEGGINGS));
        result.put(ResourceLocation.withDefaultNamespace("netherite_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.BOOTS));

        result.put(ResourceLocation.withDefaultNamespace("iron_sword"), settings -> MMEItems.getSwordSettings(MMEToolMaterials.IRON));
        result.put(ResourceLocation.withDefaultNamespace("iron_shovel"), settings -> MMEItems.getShovelSettings(MMEToolMaterials.IRON));
        result.put(ResourceLocation.withDefaultNamespace("iron_pickaxe"), settings -> MMEItems.getPickaxeSettings(MMEToolMaterials.IRON));
        result.put(ResourceLocation.withDefaultNamespace("iron_axe"), settings -> MMEItems.getAxeSettings(MMEToolMaterials.IRON));
        result.put(ResourceLocation.withDefaultNamespace("iron_hoe"), settings -> MMEItems.getHoeSettings(MMEToolMaterials.IRON));

        result.put(ResourceLocation.withDefaultNamespace("golden_sword"), settings -> MMEItems.getSwordSettings(MMEToolMaterials.GOLD));
        result.put(ResourceLocation.withDefaultNamespace("golden_shovel"), settings -> MMEItems.getShovelSettings(MMEToolMaterials.GOLD));
        result.put(ResourceLocation.withDefaultNamespace("golden_pickaxe"), settings -> MMEItems.getPickaxeSettings(MMEToolMaterials.GOLD));
        result.put(ResourceLocation.withDefaultNamespace("golden_axe"), settings -> MMEItems.getAxeSettings(MMEToolMaterials.GOLD));
        result.put(ResourceLocation.withDefaultNamespace("golden_hoe"), settings -> MMEItems.getHoeSettings(MMEToolMaterials.GOLD));

        result.put(ResourceLocation.withDefaultNamespace("iron_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.HELMET));
        result.put(ResourceLocation.withDefaultNamespace("iron_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.CHESTPLATE));
        result.put(ResourceLocation.withDefaultNamespace("iron_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.LEGGINGS));
        result.put(ResourceLocation.withDefaultNamespace("iron_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.BOOTS));

        result.put(ResourceLocation.withDefaultNamespace("chainmail_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.HELMET));
        result.put(ResourceLocation.withDefaultNamespace("chainmail_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE));
        result.put(ResourceLocation.withDefaultNamespace("chainmail_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS));
        result.put(ResourceLocation.withDefaultNamespace("chainmail_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.BOOTS));

        result.put(ResourceLocation.withDefaultNamespace("golden_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.HELMET));
        result.put(ResourceLocation.withDefaultNamespace("golden_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.CHESTPLATE));
        result.put(ResourceLocation.withDefaultNamespace("golden_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.LEGGINGS));
        result.put(ResourceLocation.withDefaultNamespace("golden_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.BOOTS));

        result.put(ResourceLocation.withDefaultNamespace("leather_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.HELMET));
        result.put(ResourceLocation.withDefaultNamespace("leather_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.CHESTPLATE));
        result.put(ResourceLocation.withDefaultNamespace("leather_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.LEGGINGS));
        result.put(ResourceLocation.withDefaultNamespace("leather_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.BOOTS));

        result.put(ResourceLocation.withDefaultNamespace("oak_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("spruce_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("birch_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("jungle_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("acacia_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("dark_oak_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("pale_oak_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("crimson_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("warped_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("mangrove_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("bamboo_planks"), properties -> properties.stacksTo(8));
        result.put(ResourceLocation.withDefaultNamespace("cherry_planks"), properties -> properties.stacksTo(8));

        result.put(ResourceLocation.withDefaultNamespace("fishing_rod"), properties -> new Item.Properties());
        result.put(ResourceLocation.withDefaultNamespace("bow"), properties -> properties.durability(64));
        result.put(ResourceLocation.withDefaultNamespace("crossbow"), properties -> properties.durability(128));
        return result;
    }

    /**
     * 物品工厂修改映射表
     * key: 物品标识
     * value: 工厂
     */

    private static Map<ResourceLocation, Function<Item.Properties, Item>> createItemFactoryModifyMap() {
        Map<ResourceLocation, Function<Item.Properties, Item>> result = new HashMap<>();

        // 工具类物品
        result.put(ResourceLocation.withDefaultNamespace("stone_shovel"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("stone_shovel")))));
        result.put(ResourceLocation.withDefaultNamespace("stone_axe"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("stone_axe")))));
        result.put(ResourceLocation.withDefaultNamespace("stone_hoe"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("stone_hoe")))));

        result.put(ResourceLocation.withDefaultNamespace("diamond_shovel"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("diamond_shovel")))));
        result.put(ResourceLocation.withDefaultNamespace("diamond_axe"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("diamond_axe")))));
        result.put(ResourceLocation.withDefaultNamespace("diamond_hoe"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("diamond_hoe")))));

        result.put(ResourceLocation.withDefaultNamespace("wooden_axe"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("wooden_axe")))));
        result.put(ResourceLocation.withDefaultNamespace("wooden_hoe"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("wooden_hoe")))));
        result.put(ResourceLocation.withDefaultNamespace("wooden_shovel"), settings -> new Item(MMEItems.getShovelSettings(MMEToolMaterials.WOOD).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("wooden_shovel")))));

        result.put(ResourceLocation.withDefaultNamespace("iron_shovel"), MMEShovelItem::new);
        result.put(ResourceLocation.withDefaultNamespace("iron_axe"), MMEAxeItem::new);
        result.put(ResourceLocation.withDefaultNamespace("iron_hoe"), MMEHoeItem::new);

        result.put(ResourceLocation.withDefaultNamespace("golden_shovel"), MMEShovelItem::new);
        result.put(ResourceLocation.withDefaultNamespace("golden_axe"), MMEAxeItem::new);
        result.put(ResourceLocation.withDefaultNamespace("golden_hoe"), MMEHoeItem::new);

        result.put(ResourceLocation.withDefaultNamespace("netherite_shovel"), settings -> new MMEShovelItem(MMEItems.getShovelSettings(MMEToolMaterials.NETHERITE).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("netherite_shovel")))));
        result.put(ResourceLocation.withDefaultNamespace("netherite_axe"), settings -> new MMEAxeItem(MMEItems.getAxeSettings(MMEToolMaterials.NETHERITE).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("netherite_axe")))));
        result.put(ResourceLocation.withDefaultNamespace("netherite_hoe"), settings -> new MMEHoeItem(MMEItems.getHoeSettings(MMEToolMaterials.NETHERITE).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("netherite_hoe")))));

        // 钻石装备
        result.put(ResourceLocation.withDefaultNamespace("diamond_helmet"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("diamond_helmet")))));
        result.put(ResourceLocation.withDefaultNamespace("diamond_chestplate"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("diamond_chestplate")))));
        result.put(ResourceLocation.withDefaultNamespace("diamond_leggings"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("diamond_leggings")))));
        result.put(ResourceLocation.withDefaultNamespace("diamond_boots"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("diamond_boots")))));

        result.put(ResourceLocation.withDefaultNamespace("diamond"), settings -> new GamItem(settings, 500));
        result.put(ResourceLocation.withDefaultNamespace("emerald"), settings -> new GamItem(settings, 250));
        result.put(ResourceLocation.withDefaultNamespace("lapis_lazuli"), settings -> new GamItem(settings, 50));
        result.put(ResourceLocation.withDefaultNamespace("quartz"), settings -> new GamItem(settings, 50));
        result.put(ResourceLocation.withDefaultNamespace("amethyst_shard"), settings -> new GamItem(settings, 75));
        result.put(ResourceLocation.withDefaultNamespace("echo_shard"), settings -> new GamItem(settings, 1000));

        result.put(ResourceLocation.withDefaultNamespace("iron_nugget"), settings -> new NuggetItem(settings.stacksTo(32), 200));
        result.put(ResourceLocation.withDefaultNamespace("gold_nugget"), settings -> new NuggetItem(settings.stacksTo(32), 500));
        result.put(ResourceLocation.withDefaultNamespace("fishing_rod"), Item::new);
        return Map.copyOf(result);
    }
    private static Map<ResourceLocation, UnaryOperator<Item.Properties>> createBlockItemSettingsModifyMapByIdentifier() {
        Map<ResourceLocation, UnaryOperator<Item.Properties>> result = new HashMap<>();

        result.put(ResourceLocation.withDefaultNamespace("iron_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 270));
        result.put(ResourceLocation.withDefaultNamespace("gold_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 180));
        result.put(ResourceLocation.withDefaultNamespace("copper_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 90));
        result.put(ResourceLocation.withDefaultNamespace("netherite_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 1800));

        result.put(ResourceLocation.withDefaultNamespace("iron_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("copper_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("gold_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(ResourceLocation.withDefaultNamespace("deepslate_iron_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("deepslate_copper_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("deepslate_gold_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(ResourceLocation.withDefaultNamespace("nether_gold_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(ResourceLocation.withDefaultNamespace("brown_mushroom"), properties -> properties.food(new FoodProperties(2, 0.0F, false)));
        result.put(ResourceLocation.withDefaultNamespace("red_mushroom"), properties -> properties.food(new FoodProperties(2, 0.0F, false),
                Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 1200), 1F))
                        .build()));

        result.put(ResourceLocation.withDefaultNamespace("leaf_litter"), properties -> properties.food(new FoodProperties(1, 0.0F, true),
                Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 0.5F))
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 1200, 0), 0.5F))
                        .build()));

        return result;
    }


    /**
     * 方块物品设置修改映射表
     * key: 方块类型
     * value: 设置
     */

    private static Map<Class<?>, UnaryOperator<Item.Properties>> createBlockItemSettingsModifyMap() {
        Map<Class<?>, UnaryOperator<Item.Properties>> result = new HashMap<>();

        // 最大堆叠为 1 的方块类型
        result.put(FenceGateBlock.class, settings -> settings.stacksTo(1));
        result.put(BedBlock.class, settings -> settings.stacksTo(1));
        result.put(HeavyCoreBlock.class, settings -> settings.stacksTo(1));
        result.put(ShulkerBoxBlock.class, settings -> settings.stacksTo(1));

        // 最大堆叠为 8 的方块类型
        result.put(SlabBlock.class, settings -> settings.stacksTo(8));
        result.put(WallBlock.class, settings -> settings.stacksTo(8));
        result.put(TerracottaBlock.class, settings -> settings.stacksTo(8));
        result.put(TintedParticleLeavesBlock.class, settings -> settings.stacksTo(8));
        result.put(TransparentBlock.class, settings -> settings.stacksTo(8));
        result.put(KelpPlantBlock.class, settings -> settings.stacksTo(8));
        result.put(PointedDripstoneBlock.class, settings -> settings.stacksTo(8));
        result.put(LadderBlock.class, settings -> settings.stacksTo(8));
        result.put(CandleBlock.class, settings -> settings.stacksTo(8));
        result.put(LanternBlock.class, settings -> settings.stacksTo(8));
        result.put(StandingSignBlock.class, settings -> settings.stacksTo(8));
        result.put(CeilingHangingSignBlock.class, settings -> settings.stacksTo(8));
        result.put(StainedGlassBlock.class, settings -> settings.stacksTo(8));
        result.put(FenceBlock.class, settings -> settings.stacksTo(8));
        result.put(WeatheringCopperSlabBlock.class, settings -> settings.stacksTo(8));
        result.put(MangroveLeavesBlock.class, settings -> settings.stacksTo(8));
        result.put(UntintedParticleLeavesBlock.class, settings -> settings.stacksTo(8));
        result.put(SnifferEggBlock.class, settings -> settings.stacksTo(8));
        result.put(WaterlilyBlock.class, settings -> settings.stacksTo(8));
        result.put(LightningRodBlock.class, settings -> settings.stacksTo(8));
        result.put(TintedGlassBlock.class, settings -> settings.stacksTo(8));
        result.put(RepeaterBlock.class, settings -> settings.stacksTo(8));
        result.put(ComparatorBlock.class, settings -> settings.stacksTo(8));
        result.put(TripWireHookBlock.class, settings -> settings.stacksTo(8));

        // 最大堆叠为 16 的方块类型
        result.put(IronBarsBlock.class, settings -> settings.stacksTo(16));
        result.put(RailBlock.class, settings -> settings.stacksTo(16));
        result.put(PressurePlateBlock.class, settings -> settings.stacksTo(16));
        result.put(SaplingBlock.class, settings -> settings.stacksTo(16));
        result.put(BaseCoralPlantTypeBlock.class, settings -> settings.stacksTo(16));
        result.put(NetherWartBlock.class, settings -> settings.stacksTo(16));
        result.put(FlowerBedBlock.class, settings -> settings.stacksTo(16));
        result.put(ScaffoldingBlock.class, settings -> settings.stacksTo(16));
        result.put(SnowLayerBlock.class, settings -> settings.stacksTo(16));
        result.put(TorchBlock.class, settings -> settings.stacksTo(16));
        result.put(BonemealableFeaturePlacerBlock.class, settings -> settings.stacksTo(16));
        result.put(RedstoneTorchBlock.class, settings -> settings.stacksTo(16));
        result.put(PoweredRailBlock.class, settings -> settings.stacksTo(16));
        result.put(DetectorRailBlock.class, settings -> settings.stacksTo(16));
        result.put(SugarCaneBlock.class, settings -> settings.stacksTo(16));
        result.put(BambooStalkBlock.class, settings -> settings.stacksTo(16));
        result.put(StainedGlassPaneBlock.class, settings -> settings.stacksTo(16));
        result.put(TrapDoorBlock.class, settings -> settings.stacksTo(16));
        result.put(ConcretePowderBlock.class, settings -> settings.stacksTo(16));
        result.put(MossyCarpetBlock.class, settings -> settings.stacksTo(16));
        result.put(HangingMossBlock.class, settings -> settings.stacksTo(16));
        result.put(MangrovePropaguleBlock.class, settings -> settings.stacksTo(16));
        result.put(TallGrassBlock.class, settings -> settings.stacksTo(16));
        result.put(ShortDryGrassBlock.class, settings -> settings.stacksTo(16));
        result.put(TallDryGrassBlock.class, settings -> settings.stacksTo(16));
        result.put(BushBlock.class, settings -> settings.stacksTo(16));
        result.put(DryVegetationBlock.class, settings -> settings.stacksTo(16));
        result.put(TorchflowerCropBlock.class, settings -> settings.stacksTo(16));
        result.put(TallFlowerBlock.class, settings -> settings.stacksTo(16));
        result.put(WeepingVinesBlock.class, settings -> settings.stacksTo(16));
        result.put(VineBlock.class, settings -> settings.stacksTo(16));
        result.put(GlowLichenBlock.class, settings -> settings.stacksTo(16));
        result.put(HangingRootsBlock.class, settings -> settings.stacksTo(16));
        result.put(FrogspawnBlock.class, settings -> settings.stacksTo(16));
        result.put(TurtleEggBlock.class, settings -> settings.stacksTo(16));
        result.put(CoralPlantBlock.class, settings -> settings.stacksTo(16));
        result.put(BaseCoralPlantBlock.class, settings -> settings.stacksTo(16));
        result.put(CoralFanBlock.class, settings -> settings.stacksTo(16));
        result.put(BaseCoralFanBlock.class, settings -> settings.stacksTo(16));
        result.put(TwistingVinesBlock.class, settings -> settings.stacksTo(16));
        result.put(DoublePlantBlock.class, settings -> settings.stacksTo(16));
        result.put(KelpBlock.class, settings -> settings.stacksTo(16));
        result.put(SeaPickleBlock.class, settings -> settings.stacksTo(16));
        result.put(FlowerPotBlock.class, settings -> settings.stacksTo(16));
        result.put(WeightedPressurePlateBlock.class, settings -> settings.stacksTo(16));
        result.put(LeverBlock.class, settings -> settings.stacksTo(16));
        result.put(ChainBlock.class, settings -> settings.stacksTo(16));

        // 最大堆叠为 32 的方块类型
        result.put(FungusBlock.class, settings -> settings.stacksTo(32));
        result.put(ButtonBlock.class, settings -> settings.stacksTo(32));
        result.put(CarpetBlock.class, settings -> settings.stacksTo(32));
        result.put(MushroomBlock.class, settings -> settings.stacksTo(32));
        result.put(FlowerBlock.class, settings -> settings.stacksTo(32));
        result.put(RootsBlock.class, settings -> settings.stacksTo(32));
        result.put(NetherSproutsBlock.class, settings -> settings.stacksTo(32));
        result.put(StemBlock.class, settings -> settings.stacksTo(32));
        result.put(CactusFlowerBlock.class, settings -> settings.stacksTo(32));
        result.put(EyeblossomBlock.class, settings -> settings.stacksTo(32));
        result.put(WitherRoseBlock.class, settings -> settings.stacksTo(32));
        result.put(LeafLitterBlock.class, settings -> settings.stacksTo(32));

        return result;
    }


}
