package com.acuteterror233.mite.item;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.component.MMEDataComponentTypes;
import com.acuteterror233.mite.item.armor.MMEArmorMaterials;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 原版物品属性修改器。
 * 批量修改原版物品的堆叠上限、食物属性、燃料值等。
 */
public final class VanillaItemModify {
    public static final Map<Class<?>, UnaryOperator<Item.Properties>> IN_CLASS_BLOCK_ITEM_SETTINGS_MODIFY = createBlockItemSettingsModifyMapByClass();
    public static final Map<Identifier, UnaryOperator<Item.Properties>> IN_IDENTIFIER_BLOCK_ITEM_SETTINGS_MODIFY = createBlockItemSettingsModifyMapByIdentifier();
    public static final Map<Identifier, Function<Item.Properties, Item>> ITEM_FACTORY_MODIFY = createItemFactoryModifyMap();
    public static final Map<Identifier, UnaryOperator<Item.Properties>> ITEM_SETTINGS_MODIFY = createItemSettingsModifyMap();

    /**
     * 按照物品注册名划分的最大堆叠映射表。
     * Key: 物品标识符 Identifier
     * Value: 设置函数 UnaryOperator<Item.Settings>
     */
    private static Map<Identifier, UnaryOperator<Item.Properties>> createItemSettingsModifyMap() {
        Map<Identifier, UnaryOperator<Item.Properties>> result = new HashMap<>();

        // 最大堆叠为 1 的物品标识
        result.put(Identifier.withDefaultNamespace("heart_of_the_sea"), settings -> settings.stacksTo(1));
        result.put(Identifier.withDefaultNamespace("nether_star"), settings -> settings.stacksTo(1));

        // 最大堆叠为 8 的物品标识
        result.put(Identifier.withDefaultNamespace("flint"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("rabbit_hide"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("honeycomb"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("turtle_scute"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("armadillo_scute"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("blaze_rod"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("breeze_rod"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("shulker_shell"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("blaze_powder"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("rabbit_foot"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("glistering_melon_slice"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("ink_sac"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("glow_ink_sac"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("prismarine_shard"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("popped_chorus_fruit"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("echo_shard"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("magma_cream"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("phantom_membrane"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("ghast_tear"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("fermented_spider_eye"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("acacia_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("birch_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("crimson_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("dark_oak_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("pale_oak_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("jungle_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("oak_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("spruce_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("warped_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("mangrove_planks"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("oak_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("spruce_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("birch_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("jungle_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("acacia_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("dark_oak_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("pale_oak_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("crimson_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("warped_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("mangrove_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("bamboo_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("cherry_planks"), properties -> properties.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("sugar"), settings -> settings
                .stacksTo(8)
                .food(new FoodProperties(0, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, new FoodNutrition(0, 0, 4800))
        );

        // 最大堆叠为 16 的物品标识
        result.put(Identifier.withDefaultNamespace("iron_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 10));
        result.put(Identifier.withDefaultNamespace("copper_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 5));
        result.put(Identifier.withDefaultNamespace("gold_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 5));
        result.put(Identifier.withDefaultNamespace("netherite_ingot"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 70));
        result.put(Identifier.withDefaultNamespace("leather"), settings -> settings.stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 2));
        result.put(Identifier.withDefaultNamespace("coal"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("charcoal"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("emerald"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("diamond"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("amethyst_shard"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("netherite_scrap"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("wheat"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("clay_ball"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("ender_eye"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("bowl"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("brick"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("nether_brick"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("resin_brick"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("book"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("glass_bottle"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("experience_bottle"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("wind_charge"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("lead"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("firework_rocket"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("slime_ball"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("resin_clump"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("torchflower_seeds"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("pitcher_pod"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("item_frame"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("glow_item_frame"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("painting"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("fire_charge"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("name_tag"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("chorus_fruit"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("tropical_fish"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("pufferfish"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("prismarine_crystals"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("nautilus_shell"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("disc_fragment_5"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("firework_star"), settings -> settings.stacksTo(16));
        result.put(Identifier.withDefaultNamespace("glow_berries"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(4800).build())
        );
        result.put(Identifier.withDefaultNamespace("rabbit"), settings -> settings
                .stacksTo(16)
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(24000).build())
        );
        result.put(Identifier.withDefaultNamespace("cooked_rabbit"), settings -> settings
                .stacksTo(16)
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(48000).build())
        );
        result.put(Identifier.withDefaultNamespace("beetroot"), settings -> settings
                .stacksTo(16)
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).build())
        );
        result.put(Identifier.withDefaultNamespace("mutton"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(3, 3, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(24000).build())
        );
        result.put(Identifier.withDefaultNamespace("cooked_mutton"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(6, 6, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(48000).build())
        );
        result.put(Identifier.withDefaultNamespace("pumpkin_pie"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(6, 10, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(48000).fiber(48000).sugar(4800).build())
        );
        result.put(Identifier.withDefaultNamespace("potato"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 3, false))
        );
        result.put(Identifier.withDefaultNamespace("baked_potato"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(2, 6, false))
        );
        result.put(Identifier.withDefaultNamespace("poisonous_potato"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(0, 2, false), Consumables.POISONOUS_POTATO)
        );
        result.put(Identifier.withDefaultNamespace("carrot"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(2, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).build())
        );
        result.put(Identifier.withDefaultNamespace("golden_carrot"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(2, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).build())
        );
        result.put(Identifier.withDefaultNamespace("spider_eye"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 0, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
        );
        result.put(Identifier.withDefaultNamespace("nether_wart"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 1, false))
        );
        result.put(Identifier.withDefaultNamespace("rotten_flesh"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 2, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
        );
        result.put(Identifier.withDefaultNamespace("chicken"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(3, 3, false), Consumables
                        .defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 1200), 0.5F))
                        .build()
                )
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(24000).build())
        );
        result.put(Identifier.withDefaultNamespace("cooked_chicken"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(6, 6, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(48000).build())
        );
        result.put(Identifier.withDefaultNamespace("beef"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(5, 5, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(40000).build())
        );
        result.put(Identifier.withDefaultNamespace("cooked_beef"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(10, 10, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(80000).build())
        );
        result.put(Identifier.withDefaultNamespace("cod"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(3, 3, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(24000).build())
        );
        result.put(Identifier.withDefaultNamespace("cooked_cod"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(6, 6, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(48000).build())
        );
        result.put(Identifier.withDefaultNamespace("salmon"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(3, 3, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(24000).build())
        );
        result.put(Identifier.withDefaultNamespace("cooked_salmon"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(6, 6, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(48000).build())
        );
        result.put(Identifier.withDefaultNamespace("porkchop"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(4, 4, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(32000).build())
        );
        result.put(Identifier.withDefaultNamespace("cooked_porkchop"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(8, 8, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(64000).build())
        );
        result.put(Identifier.withDefaultNamespace("bread"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(2, 8, false))
        );
        result.put(Identifier.withDefaultNamespace("apple"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 2, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
        );
        result.put(Identifier.withDefaultNamespace("golden_apple"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 2, true), Consumables.defaultFood()
                        .onConsume(
                                new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(MobEffects.REGENERATION, 1200, 1), new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0)))
                        ).build()
                )
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
        );
        result.put(Identifier.withDefaultNamespace("enchanted_golden_apple"), settings -> settings
                .stacksTo(16)
                .food(new FoodProperties(1, 2, true), Consumables.defaultFood()
                        .onConsume(
                                new ApplyStatusEffectsConsumeEffect(
                                        List.of(
                                                new MobEffectInstance(MobEffects.REGENERATION, 2400, 1),
                                                new MobEffectInstance(MobEffects.RESISTANCE, 6000, 0),
                                                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0),
                                                new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3)
                                        )
                                )
                        ).build()
                )
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
        );
        result.put(Identifier.withDefaultNamespace("raw_copper"), settings -> settings
                .stacksTo(8)
                .component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
        );
        result.put(Identifier.withDefaultNamespace("raw_gold"), settings -> settings
                .stacksTo(8)
                .component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
        );
        result.put(Identifier.withDefaultNamespace("raw_iron"), settings -> settings
                .stacksTo(8)
                .component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2)
        );

        // 最大堆叠为 32 的物品标识
        result.put(Identifier.withDefaultNamespace("lapis_lazuli"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("quartz"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("stick"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("arrow"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("tipped_arrow"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("spectral_arrow"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("bone"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("bone_meal"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("string"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("feather"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("snowball"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("paper"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("redstone"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("glowstone_dust"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("cocoa_beans"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("seagrass"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("gunpowder"), settings -> settings.stacksTo(32));
        result.put(Identifier.withDefaultNamespace("copper_nugget"), settings -> settings.stacksTo(32).component(MMEDataComponentTypes.CRAFTING_TIME, 1));
        result.put(Identifier.withDefaultNamespace("sweet_berries"), settings -> settings
                .stacksTo(32)
                .food(new FoodProperties(1, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().sugar(4800).build())
        );
        result.put(Identifier.withDefaultNamespace("dried_kelp"), settings -> settings
                .stacksTo(32)
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(4800).build())
        );
        result.put(Identifier.withDefaultNamespace("beetroot_seeds"), settings -> settings
                .stacksTo(32)
                .food(new FoodProperties(1, 1, false))
        );
        result.put(Identifier.withDefaultNamespace("melon_slice"), settings -> settings
                .stacksTo(32)
                .food(new FoodProperties(1, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, new FoodNutrition(0, 8000, 4800))
        );
        result.put(Identifier.withDefaultNamespace("cookie"), settings -> settings
                .stacksTo(32)
                .food(new FoodProperties(1, 3, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, new FoodNutrition(0, 0, 1200))
        );
        result.put(Identifier.withDefaultNamespace("melon_seeds"), settings -> settings
                .stacksTo(32)
                .food(new FoodProperties(1, 1, false))
        );
        result.put(Identifier.withDefaultNamespace("pumpkin_seeds"), settings -> settings
                .stacksTo(32)
                .food(new FoodProperties(2, 1, false))
        );
        result.put(Identifier.withDefaultNamespace("wheat_seeds"), settings -> settings
                .stacksTo(32)
                .food(new FoodProperties(0, 1, false))
        );

        result.put(Identifier.withDefaultNamespace("stone_sword"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("stone_shovel"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("stone_pickaxe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("stone_axe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("stone_hoe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("stone_spear"), settings -> new Item.Properties());

        result.put(Identifier.withDefaultNamespace("diamond_sword"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("diamond_shovel"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("diamond_pickaxe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("diamond_axe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("diamond_hoe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("diamond_spear"), settings -> new Item.Properties());

        result.put(Identifier.withDefaultNamespace("wooden_sword"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("wooden_shovel"), settings -> MMEItems.getShovelSettings(MMEToolMaterials.WOOD));
        result.put(Identifier.withDefaultNamespace("wooden_pickaxe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("wooden_axe"), settings -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("wooden_hoe"), settings -> new Item.Properties());

        result.put(Identifier.withDefaultNamespace("netherite_sword"), settings -> MMEItems.getSwordSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
        result.put(Identifier.withDefaultNamespace("netherite_shovel"), settings -> MMEItems.getShovelSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
        result.put(Identifier.withDefaultNamespace("netherite_pickaxe"), settings -> MMEItems.getPickaxeSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
        result.put(Identifier.withDefaultNamespace("netherite_axe"), settings -> MMEItems.getAxeSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
        result.put(Identifier.withDefaultNamespace("netherite_hoe"), settings -> MMEItems.getHoeSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));

        result.put(Identifier.withDefaultNamespace("netherite_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.HELMET).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
        result.put(Identifier.withDefaultNamespace("netherite_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
        result.put(Identifier.withDefaultNamespace("netherite_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
        result.put(Identifier.withDefaultNamespace("netherite_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.BOOTS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));

        result.put(Identifier.withDefaultNamespace("iron_sword"), settings -> MMEItems.getSwordSettings(MMEToolMaterials.IRON).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("iron_shovel"), settings -> MMEItems.getShovelSettings(MMEToolMaterials.IRON).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("iron_pickaxe"), settings -> MMEItems.getPickaxeSettings(MMEToolMaterials.IRON).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("iron_axe"), settings -> MMEItems.getAxeSettings(MMEToolMaterials.IRON).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("iron_hoe"), settings -> MMEItems.getHoeSettings(MMEToolMaterials.IRON).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("shears"), settings -> MMEItems.getShearsSettings(MMEToolMaterials.IRON));

        result.put(Identifier.withDefaultNamespace("golden_sword"), settings -> MMEItems.getSwordSettings(MMEToolMaterials.GOLD).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("golden_shovel"), settings -> MMEItems.getShovelSettings(MMEToolMaterials.GOLD).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("golden_pickaxe"), settings -> MMEItems.getPickaxeSettings(MMEToolMaterials.GOLD).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("golden_axe"), settings -> MMEItems.getAxeSettings(MMEToolMaterials.GOLD).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("golden_hoe"), settings -> MMEItems.getHoeSettings(MMEToolMaterials.GOLD).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("copper_sword"), settings -> MMEItems.getSwordSettings(MMEToolMaterials.COPPER).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_shovel"), settings -> MMEItems.getShovelSettings(MMEToolMaterials.COPPER).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_pickaxe"), settings -> MMEItems.getPickaxeSettings(MMEToolMaterials.COPPER).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_axe"), settings -> MMEItems.getAxeSettings(MMEToolMaterials.COPPER).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_hoe"), settings -> MMEItems.getHoeSettings(MMEToolMaterials.COPPER).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        // 原版矛（石矛、钻石矛除外）
        result.put(Identifier.withDefaultNamespace("wooden_spear"), settings -> MMEItems.VanillaSpearSettings(settings, MMEToolMaterials.WOOD, 4, 0.65F));
        result.put(Identifier.withDefaultNamespace("copper_spear"), settings -> MMEItems.VanillaSpearSettings(settings, MMEToolMaterials.COPPER, 4, 0.85F));
        result.put(Identifier.withDefaultNamespace("iron_spear"), settings -> MMEItems.VanillaSpearSettings(settings, MMEToolMaterials.IRON, 4, 0.95F));
        result.put(Identifier.withDefaultNamespace("golden_spear"), settings -> MMEItems.VanillaSpearSettings(settings, MMEToolMaterials.GOLD, 4, 0.95F));
        result.put(Identifier.withDefaultNamespace("netherite_spear"), settings -> MMEItems.VanillaSpearSettings(settings, MMEToolMaterials.NETHERITE, 4, 1.15F));

        result.put(Identifier.withDefaultNamespace("copper_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.HELMET).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.BOOTS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("iron_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.HELMET).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("iron_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("iron_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("iron_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_MATERIAL, ArmorType.BOOTS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("chainmail_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.HELMET).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("chainmail_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("chainmail_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("chainmail_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.IRON_CHAINMAIL_MATERIAL, ArmorType.BOOTS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("golden_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.HELMET).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("golden_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("golden_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("golden_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.GOLD_MATERIAL, ArmorType.BOOTS).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("leather_helmet"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.HELMET));
        result.put(Identifier.withDefaultNamespace("leather_chestplate"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.CHESTPLATE));
        result.put(Identifier.withDefaultNamespace("leather_leggings"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.LEGGINGS));
        result.put(Identifier.withDefaultNamespace("leather_boots"), settings -> MMEItems.getArmorSettings(MMEArmorMaterials.LEATHER_MATERIAL, ArmorType.BOOTS));

        result.put(Identifier.withDefaultNamespace("fishing_rod"), properties -> new Item.Properties());
        result.put(Identifier.withDefaultNamespace("bow"), properties -> properties.durability(64));
        result.put(Identifier.withDefaultNamespace("crossbow"), properties -> properties.durability(128));
        result.put(Identifier.withDefaultNamespace("mushroom_stew"), properties -> properties
                .food(new FoodProperties(4, 2, false))
        );
        result.put(Identifier.withDefaultNamespace("milk_bucket"), properties -> properties
                .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
        );
        result.put(Identifier.withDefaultNamespace("egg"), properties -> properties
                .food(new FoodProperties(3, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
        );
        result.put(Identifier.withDefaultNamespace("blue_egg"), properties -> properties
                .food(new FoodProperties(3, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
        );
        result.put(Identifier.withDefaultNamespace("brown_egg"), properties -> properties
                .food(new FoodProperties(3, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
        );
        result.put(Identifier.withDefaultNamespace("beetroot_soup"), properties -> properties
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(112000).build())
        );
        result.put(Identifier.withDefaultNamespace("rabbit_stew"), properties -> properties
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(48000).protein(48000).build())
        );
        result.put(Identifier.withDefaultNamespace("suspicious_stew"), properties -> properties
                .food(new FoodProperties(3, 3, true))
        );
        result.put(Identifier.withDefaultNamespace("chorus_fruit"), properties -> properties
                .food(new FoodProperties(1, 1, false))
                .component(MMEDataComponentTypes.FOOD_NUTRITION, FoodNutrition.builder().fiber(12000).build())
        );
        result.put(Identifier.withDefaultNamespace("honey_bottle"), properties -> properties
                .food(new FoodProperties(0, 2, true))
        );
        return result;
    }

    /**
     * 物品工厂修改映射表
     * key: 物品标识
     * value: 工厂
     */

    private static Map<Identifier, Function<Item.Properties, Item>> createItemFactoryModifyMap() {
        Map<Identifier, Function<Item.Properties, Item>> result = new HashMap<>();

        // 工具类物品
        result.put(Identifier.withDefaultNamespace("stone_shovel"), Item::new);
        result.put(Identifier.withDefaultNamespace("stone_axe"), Item::new);
        result.put(Identifier.withDefaultNamespace("stone_hoe"), Item::new);
        result.put(Identifier.withDefaultNamespace("stone_spear"), Item::new);

        result.put(Identifier.withDefaultNamespace("diamond_shovel"), Item::new);
        result.put(Identifier.withDefaultNamespace("diamond_axe"), Item::new);
        result.put(Identifier.withDefaultNamespace("diamond_hoe"), Item::new);
        result.put(Identifier.withDefaultNamespace("diamond_spear"), Item::new);

        result.put(Identifier.withDefaultNamespace("wooden_axe"), Item::new);
        result.put(Identifier.withDefaultNamespace("wooden_hoe"), Item::new);

        result.put(Identifier.withDefaultNamespace("iron_shovel"), MMEShovelItem::new);
        result.put(Identifier.withDefaultNamespace("iron_axe"), MMEAxeItem::new);
        result.put(Identifier.withDefaultNamespace("iron_hoe"), MMEHoeItem::new);

        result.put(Identifier.withDefaultNamespace("golden_shovel"), MMEShovelItem::new);
        result.put(Identifier.withDefaultNamespace("golden_axe"), MMEAxeItem::new);
        result.put(Identifier.withDefaultNamespace("golden_hoe"), MMEHoeItem::new);

        result.put(Identifier.withDefaultNamespace("copper_shovel"), MMEShovelItem::new);
        result.put(Identifier.withDefaultNamespace("copper_axe"), MMEAxeItem::new);
        result.put(Identifier.withDefaultNamespace("copper_hoe"), MMEHoeItem::new);

        result.put(Identifier.withDefaultNamespace("netherite_shovel"), MMEShovelItem::new);
        result.put(Identifier.withDefaultNamespace("netherite_axe"), MMEAxeItem::new);
        result.put(Identifier.withDefaultNamespace("netherite_hoe"), MMEHoeItem::new);

        result.put(Identifier.withDefaultNamespace("diamond_helmet"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("diamond_helmet")))));
        result.put(Identifier.withDefaultNamespace("diamond_chestplate"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("diamond_chestplate")))));
        result.put(Identifier.withDefaultNamespace("diamond_leggings"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("diamond_leggings")))));
        result.put(Identifier.withDefaultNamespace("diamond_boots"), settings -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("diamond_boots")))));

        result.put(Identifier.withDefaultNamespace("diamond"), settings -> new GamItem(settings, 500));
        result.put(Identifier.withDefaultNamespace("emerald"), settings -> new GamItem(settings, 250));
        result.put(Identifier.withDefaultNamespace("lapis_lazuli"), settings -> new GamItem(settings, 50));
        result.put(Identifier.withDefaultNamespace("quartz"), settings -> new GamItem(settings, 50));
        result.put(Identifier.withDefaultNamespace("amethyst_shard"), settings -> new GamItem(settings, 75));
        result.put(Identifier.withDefaultNamespace("echo_shard"), settings -> new GamItem(settings, 1000));

        result.put(Identifier.withDefaultNamespace("iron_nugget"), settings -> new NuggetItem(settings.stacksTo(32), 200));
        result.put(Identifier.withDefaultNamespace("gold_nugget"), settings -> new NuggetItem(settings.stacksTo(32), 500));
        result.put(Identifier.withDefaultNamespace("copper_nugget"), settings -> new NuggetItem(settings.stacksTo(32), 50));
        result.put(Identifier.withDefaultNamespace("fishing_rod"), Item::new);
        result.put(Identifier.withDefaultNamespace("bowl"), BowlItem::new);
        return Map.copyOf(result);
    }
    private static Map<Identifier, UnaryOperator<Item.Properties>> createBlockItemSettingsModifyMapByIdentifier() {
        Map<Identifier, UnaryOperator<Item.Properties>> result = new HashMap<>();

        result.put(Identifier.withDefaultNamespace("iron_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 90));
        result.put(Identifier.withDefaultNamespace("gold_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 45));
        result.put(Identifier.withDefaultNamespace("copper_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 45));
        result.put(Identifier.withDefaultNamespace("netherite_block"), settings -> settings.component(MMEDataComponentTypes.CRAFTING_TIME, 630));

        result.put(Identifier.withDefaultNamespace("iron_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("copper_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("gold_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("diamond_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("lapis_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("redstone_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("coal_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("emerald_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("deepslate_iron_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_copper_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_gold_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("nether_gold_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_diamond_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_lapis_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_redstone_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_coal_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_emerald_ore"), settings -> settings.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("nether_quartz_ore"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("ancient_debris"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));

        result.put(Identifier.withDefaultNamespace("brown_mushroom"), properties -> properties.food(new FoodProperties(1, 1.0F, false)));
        result.put(Identifier.withDefaultNamespace("red_mushroom"), properties -> properties.food(new FoodProperties(1, 1.0F, false),
                Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 1200), 1F))
                        .build()));

        result.put(Identifier.withDefaultNamespace("leaf_litter"), properties -> properties.food(new FoodProperties(0, 0.0F, true),
                Consumables.defaultFood()
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 0.5F))
                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 1200, 0), 0.5F))
                        .build()));

        result.put(Identifier.withDefaultNamespace("anvil"), settings -> settings
                .durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.IRON.durability()))
        );
        result.put(Identifier.withDefaultNamespace("chipped_anvil"), settings -> settings
                .durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.IRON.durability()))
        );
        result.put(Identifier.withDefaultNamespace("damaged_anvil"), settings -> settings
                .durability(MMEBlocks.maxDamageAnvil(MMEToolMaterials.IRON.durability()))
        );
        result.put(Identifier.withDefaultNamespace("sand"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("red_sand"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("cobblestone"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("cobbled_deepslate"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("stone"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("sandstone"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("red_sandstone"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("quartz_block"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("stone_bricks"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("nether_bricks"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("polished_blackstone_bricks"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_bricks"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("deepslate_tiles"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("basalt"), properties -> properties.component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("white_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("orange_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("magenta_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("light_blue_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("yellow_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("lime_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("pink_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("gray_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("light_gray_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("cyan_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("purple_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("blue_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("brown_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("green_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("red_wool"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("black_wool"), settings -> settings.stacksTo(8));

        result.put(Identifier.withDefaultNamespace("white_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("orange_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("magenta_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("light_blue_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("yellow_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("lime_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("pink_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("gray_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("light_gray_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("cyan_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("purple_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("blue_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("brown_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("green_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("red_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));
        result.put(Identifier.withDefaultNamespace("black_terracotta"), settings -> settings.stacksTo(8).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

        result.put(Identifier.withDefaultNamespace("acacia_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("bamboo_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("birch_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("cherry_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("crimson_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("dark_oak_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("jungle_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("mangrove_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("oak_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("pale_oak_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("spruce_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("warped_shelf"), settings -> settings.stacksTo(8));
        result.put(Identifier.withDefaultNamespace("copper_bars"), settings -> settings.stacksTo(16));
        return result;
    }


    /**
     * 方块物品设置修改映射表
     * key: 方块类型
     * value: 设置
     */

    private static Map<Class<?>, UnaryOperator<Item.Properties>> createBlockItemSettingsModifyMapByClass() {
        Map<Class<?>, UnaryOperator<Item.Properties>> result = new HashMap<>();

        // 最大堆叠为 1 的方块类型
        result.put(FenceGateBlock.class, settings -> settings.stacksTo(1));
        result.put(BedBlock.class, settings -> settings.stacksTo(1));
        result.put(HeavyCoreBlock.class, settings -> settings.stacksTo(1));
        result.put(ShulkerBoxBlock.class, settings -> settings.stacksTo(1));

        // 最大堆叠为 8 的方块类型
        result.put(SlabBlock.class, settings -> settings.stacksTo(8));
        result.put(WallBlock.class, settings -> settings.stacksTo(8));
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
        result.put(LilyPadBlock.class, settings -> settings.stacksTo(8));
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
        result.put(NetherFungusBlock.class, settings -> settings.stacksTo(32));
        result.put(ButtonBlock.class, settings -> settings.stacksTo(32));
        result.put(CarpetBlock.class, settings -> settings.stacksTo(32));
        result.put(MushroomBlock.class, settings -> settings.stacksTo(32));
        result.put(FlowerBlock.class, settings -> settings.stacksTo(32));
        result.put(NetherRootsBlock.class, settings -> settings.stacksTo(32));
        result.put(NetherSproutsBlock.class, settings -> settings.stacksTo(32));
        result.put(StemBlock.class, settings -> settings.stacksTo(32));
        result.put(CactusFlowerBlock.class, settings -> settings.stacksTo(32));
        result.put(EyeblossomBlock.class, settings -> settings.stacksTo(32));
        result.put(WitherRoseBlock.class, settings -> settings.stacksTo(32));
        result.put(LeafLitterBlock.class, settings -> settings.stacksTo(32));

        return result;
    }


}
