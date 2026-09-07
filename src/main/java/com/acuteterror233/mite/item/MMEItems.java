package com.acuteterror233.mite.item;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.component.MMEDataComponents;
import com.acuteterror233.mite.item.armor.MMEArmorMaterials;
import com.acuteterror233.mite.item.equipment.MMEArmorMaterial;
import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * MME 模组物品注册中心。
 * 定义并注册所有自定义物品（工具、武器、盔甲、材料、食物等）及其创造模式物品栏。
 */
public class MMEItems {
    public static final Item ADAMANTIUM_HELMET = register(
            MMEItemIds.ADAMANTIUM_HELMET,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHESTPLATE = register(
            MMEItemIds.ADAMANTIUM_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_LEGGINGS = register(
            MMEItemIds.ADAMANTIUM_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_BOOTS = register(
            MMEItemIds.ADAMANTIUM_BOOTS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_BOOTS = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.ADAMANTIUM_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4)
    );
    public static final Item ANCIENT_METAL_HELMET = register(
            MMEItemIds.ANCIENT_METAL_HELMET,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHESTPLATE = register(
            MMEItemIds.ANCIENT_METAL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_LEGGINGS = register(
            MMEItemIds.ANCIENT_METAL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_BOOTS = register(
            MMEItemIds.ANCIENT_METAL_BOOTS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_BOOTS = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_HELMET = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.ANCIENT_METAL_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item COPPER_CHAINMAIL_BOOTS = register(
            MMEItemIds.COPPER_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.COPPER_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item COPPER_CHAINMAIL_HELMET = register(
            MMEItemIds.COPPER_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item COPPER_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.COPPER_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item MITHRIL_HELMET = register(
            MMEItemIds.MITHRIL_HELMET,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHESTPLATE = register(
            MMEItemIds.MITHRIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_LEGGINGS = register(
            MMEItemIds.MITHRIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_BOOTS = register(
            MMEItemIds.MITHRIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_HELMET = register(
            MMEItemIds.MITHRIL_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.HELMET).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.MITHRIL_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.MITHRIL_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item MITHRIL_CHAINMAIL_BOOTS = register(
            MMEItemIds.MITHRIL_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.BOOTS).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3)
    );
    public static final Item RUSTED_IRON_HELMET = register(
            MMEItemIds.RUSTED_IRON_HELMET,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.HELMET)
    );
    public static final Item RUSTED_IRON_CHESTPLATE = register(
            MMEItemIds.RUSTED_IRON_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_LEGGINGS = register(
            MMEItemIds.RUSTED_IRON_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_BOOTS = register(
            MMEItemIds.RUSTED_IRON_BOOTS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_HELMET = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_BOOTS = register(
            MMEItemIds.RUSTED_IRON_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item SILVER_HELMET = register(
            MMEItemIds.SILVER_HELMET,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.HELMET)
    );
    public static final Item SILVER_CHESTPLATE = register(
            MMEItemIds.SILVER_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item SILVER_LEGGINGS = register(
            MMEItemIds.SILVER_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item SILVER_BOOTS = register(
            MMEItemIds.SILVER_BOOTS,
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item SILVER_CHAINMAIL_HELMET = register(
            MMEItemIds.SILVER_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item SILVER_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.SILVER_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item SILVER_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.SILVER_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item SILVER_CHAINMAIL_BOOTS = register(
            MMEItemIds.SILVER_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item GOLDEN_CHAINMAIL_HELMET = register(
            MMEItemIds.GOLDEN_CHAINMAIL_HELMET,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item GOLDEN_CHAINMAIL_CHESTPLATE = register(
            MMEItemIds.GOLDEN_CHAINMAIL_CHESTPLATE,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item GOLDEN_CHAINMAIL_LEGGINGS = register(
            MMEItemIds.GOLDEN_CHAINMAIL_LEGGINGS,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item GOLDEN_CHAINMAIL_BOOTS = register(
            MMEItemIds.GOLDEN_CHAINMAIL_BOOTS,
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );

    public static final Item BANANA = register(
            MMEItemIds.BANANA,
            new Item.Properties()
                    .food(new FoodProperties(2, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item BLUE_BERRIE = register(
            MMEItemIds.BLUE_BERRIE,
            Items.createBlockItemWithCustomItemName(MMEBlocks.BLUE_BERRY_BUSH),
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item CHEESE = register(
            MMEItemIds.CHEESE,
            new Item.Properties()
                    .food(new FoodProperties(3, 3.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(24000).build())
                    .stacksTo(16)
    );
    public static final Item CHOCOLATE = register(
            MMEItemIds.CHOCOLATE,
            new Item.Properties()
                    .food(new FoodProperties(3, 3.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item DOUGH = register(
            MMEItemIds.DOUGH,
            new Item.Properties()
                    .food(new FoodProperties(2, 6.0F, false))
                    .stacksTo(16)
    );
    public static final Item LEMON = register(
            MMEItemIds.LEMON,
            new Item.Properties()
                    .food(new FoodProperties(1, 2.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item ONION = register(
            MMEItemIds.ONION,
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).build())
                    .stacksTo(16)
    );
    public static final Item ORANGE = register(
            MMEItemIds.ORANGE,
            new Item.Properties()
                    .food(new FoodProperties(1, 2.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).sugar(4800).build())
                    .stacksTo(16)
    );
    public static final Item WORM_COOKED = register(
            MMEItemIds.WORM_COOKED,
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
                    .stacksTo(16)
    );
    public static final Item WORM_RAW = register(
            MMEItemIds.WORM_RAW,
            new Item.Properties()
                    .food(new FoodProperties(1, 0.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
                    .stacksTo(16)
    );
    public static final Item FLOUR = register(
            MMEItemIds.FLOUR,
            new Item.Properties().stacksTo(16)
    );
    public static final Item BEEF_STEW = register(
            MMEItemIds.BEEF_STEW,
            new Item.Properties()
                    .food(new FoodProperties(16, 16.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(128000).fiber(128000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item BOWL_MILK = register(
            MMEItemIds.BOWL_MILK,
            new Item.Properties()
                    .food(new FoodProperties(1, 0, false), Consumables.MILK_BUCKET)
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(8000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item BOWL_SALAD = register(
            MMEItemIds.BOWL_SALAD,
            new Item.Properties()
                    .food(new FoodProperties(1, 1.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(8000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item BOWL_WATER = register(
            MMEItemIds.BOWL_WATER,
            new Item.Properties()
                    .food(new FoodProperties(0, 0F, true), Consumables.defaultDrink().build())
                    .usingConvertsTo(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CEREAL = register(
            MMEItemIds.CEREAL,
            new Item.Properties()
                    .food(new FoodProperties(2, 5.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(16000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CHICKEN_SOUP = register(
            MMEItemIds.CHICKEN_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(10, 10.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(80000).fiber(80000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CREAM_OF_MUSHROOM_SOUP = register(
            MMEItemIds.CREAM_OF_MUSHROOM_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(5, 3.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(40000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item CREAM_OF_VEGETABLE_SOUP = register(
            MMEItemIds.CREAM_OF_VEGETABLE_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(7, 7.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(56000).fiber(56000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item ICE_CREAM = register(
            MMEItemIds.ICE_CREAM,
            new Item.Properties()
                    .food(new FoodProperties(4, 5.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(32000).sugar(4800).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item MASHED_POTATO = register(
            MMEItemIds.MASHED_POTATO,
            new Item.Properties()
                    .food(new FoodProperties(8, 12.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().protein(64000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item PORRIDGE = register(
            MMEItemIds.PORRIDGE,
            new Item.Properties()
                    .food(new FoodProperties(5, 5.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).sugar(9600).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item PUMPKIN_SOUP = register(
            MMEItemIds.PUMPKIN_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(2, 1.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item SORBET = register(
            MMEItemIds.SORBET,
            new Item.Properties()
                    .food(new FoodProperties(2, 4.0F, false))
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(16000).sugar(9600).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    public static final Item VEGETABLE_SOUP = register(
            MMEItemIds.VEGETABLE_SOUP,
            new Item.Properties()
                    .food(new FoodProperties(6, 6.0F, false), Consumables.defaultDrink().build())
                    .component(MMEDataComponents.FOOD_NUTRITION, FoodNutrition.builder().fiber(48000).build())
                    .usingConvertsTo(Items.BOWL)
                    .craftRemainder(Items.BOWL)
                    .stacksTo(4)
    );
    
    public static final Item NETHERITE_BATTLE_AXE = registerAxeItem(MMEItemIds.NETHERITE_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_HATCHET = registerAxeItem(MMEItemIds.NETHERITE_HATCHET, getHandAxeSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_DAGGER = register(MMEItemIds.NETHERITE_DAGGER, getDaggerSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_KNIFE = register(MMEItemIds.NETHERITE_KNIFE);
    public static final Item NETHERITE_WAR_HAMMER = register(MMEItemIds.NETHERITE_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_MATTOCK = registerHoeItem(MMEItemIds.NETHERITE_MATTOCK, getMattockSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_SCYTHE = register(MMEItemIds.NETHERITE_SCYTHE, getScytheSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item NETHERITE_SHEARS = registerShearsItem(MMEItemIds.NETHERITE_SHEARS, getShearsSettings(MMEToolMaterials.NETHERITE).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    
    public static final Item ADAMANTIUM_AXE = registerAxeItem(MMEItemIds.ADAMANTIUM_AXE, getAxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_BATTLE_AXE = registerAxeItem(MMEItemIds.ADAMANTIUM_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_HATCHET = registerAxeItem(MMEItemIds.ADAMANTIUM_HATCHET, getHandAxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SWORD = register(MMEItemIds.ADAMANTIUM_SWORD, getSwordSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_DAGGER = register(MMEItemIds.ADAMANTIUM_DAGGER, getDaggerSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_KNIFE = register(MMEItemIds.ADAMANTIUM_KNIFE);
    public static final Item ADAMANTIUM_PICKAXE = register(MMEItemIds.ADAMANTIUM_PICKAXE, getPickaxeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_WAR_HAMMER = register(MMEItemIds.ADAMANTIUM_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SHOVEL = registerShovelItem(MMEItemIds.ADAMANTIUM_SHOVEL, getShovelSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_HOE = registerHoeItem(MMEItemIds.ADAMANTIUM_HOE, getHoeSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_MATTOCK = registerHoeItem(MMEItemIds.ADAMANTIUM_MATTOCK, getMattockSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SCYTHE = register(MMEItemIds.ADAMANTIUM_SCYTHE, getScytheSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SHEARS = registerShearsItem(MMEItemIds.ADAMANTIUM_SHEARS, getShearsSettings(MMEToolMaterials.ADAMANTIUM).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item ADAMANTIUM_SPEAR = register(MMEItemIds.ADAMANTIUM_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.ADAMANTIUM, 1.10F, 1.16F, 0.45F, 2.5F, 6.8F, 5.8F, 5.1F, 9.0F, 4.6F).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));

    public static final Item ANCIENT_METAL_AXE = registerAxeItem(MMEItemIds.ANCIENT_METAL_AXE, getAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_BATTLE_AXE = registerAxeItem(MMEItemIds.ANCIENT_METAL_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HATCHET = registerAxeItem(MMEItemIds.ANCIENT_METAL_HATCHET, getHandAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SWORD = register(MMEItemIds.ANCIENT_METAL_SWORD, getSwordSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_DAGGER = register(MMEItemIds.ANCIENT_METAL_DAGGER, getDaggerSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_KNIFE = register(MMEItemIds.ANCIENT_METAL_KNIFE);
    public static final Item ANCIENT_METAL_PICKAXE = register(MMEItemIds.ANCIENT_METAL_PICKAXE, getPickaxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_WAR_HAMMER = register(MMEItemIds.ANCIENT_METAL_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHOVEL = registerShovelItem(MMEItemIds.ANCIENT_METAL_SHOVEL, getShovelSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HOE = registerHoeItem(MMEItemIds.ANCIENT_METAL_HOE, getHoeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_MATTOCK = registerHoeItem(MMEItemIds.ANCIENT_METAL_MATTOCK, getMattockSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SCYTHE = register(MMEItemIds.ANCIENT_METAL_SCYTHE, getScytheSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHEARS = registerShearsItem(MMEItemIds.ANCIENT_METAL_SHEARS, getShearsSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SPEAR = register(MMEItemIds.ANCIENT_METAL_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.ANCIENT_METAL, 1.02F, 1.05F, 0.53F, 2.5F, 7.5F, 6.5F, 5.1F, 10.0F, 4.6F));

    public static final Item COPPER_BATTLE_AXE = registerAxeItem(MMEItemIds.COPPER_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_HATCHET = registerAxeItem(MMEItemIds.COPPER_HATCHET, getHandAxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_DAGGER = register(MMEItemIds.COPPER_DAGGER, getDaggerSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_KNIFE = register(MMEItemIds.COPPER_KNIFE);
    public static final Item COPPER_WAR_HAMMER = register(MMEItemIds.COPPER_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_MATTOCK = registerHoeItem(MMEItemIds.COPPER_MATTOCK, getMattockSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SCYTHE = register(MMEItemIds.COPPER_SCYTHE, getScytheSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SHEARS = registerShearsItem(MMEItemIds.COPPER_SHEARS, getShearsSettings(MMEToolMaterials.COPPER));

    public static final Item GOLDEN_BATTLE_AXE = registerAxeItem(MMEItemIds.GOLDEN_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_HATCHET = registerAxeItem(MMEItemIds.GOLDEN_HATCHET, getHandAxeSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_DAGGER = register(MMEItemIds.GOLDEN_DAGGER, getDaggerSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_KNIFE = register(MMEItemIds.GOLDEN_KNIFE);
    public static final Item GOLDEN_WAR_HAMMER = register(MMEItemIds.GOLDEN_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_MATTOCK = registerHoeItem(MMEItemIds.GOLDEN_MATTOCK, getMattockSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_SCYTHE = register(MMEItemIds.GOLDEN_SCYTHE, getScytheSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_SHEARS = registerShearsItem(MMEItemIds.GOLDEN_SHEARS, getShearsSettings(MMEToolMaterials.GOLD));

    public static final Item IRON_BATTLE_AXE = registerAxeItem(MMEItemIds.IRON_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.IRON));
    public static final Item IRON_DAGGER = register(MMEItemIds.IRON_DAGGER, getDaggerSettings(MMEToolMaterials.IRON));
    public static final Item IRON_HATCHET = registerAxeItem(MMEItemIds.IRON_HATCHET, getHandAxeSettings(MMEToolMaterials.IRON));
    public static final Item IRON_WAR_HAMMER = register(MMEItemIds.IRON_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.IRON));
    public static final Item IRON_KNIFE = register(MMEItemIds.IRON_KNIFE);
    public static final Item IRON_MATTOCK = registerHoeItem(MMEItemIds.IRON_MATTOCK, getMattockSettings(MMEToolMaterials.IRON));
    public static final Item IRON_SCYTHE = register(MMEItemIds.IRON_SCYTHE, getScytheSettings(MMEToolMaterials.IRON));

    public static final Item MITHRIL_AXE = registerAxeItem(MMEItemIds.MITHRIL_AXE, getAxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_BATTLE_AXE = registerAxeItem(MMEItemIds.MITHRIL_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_HATCHET = registerAxeItem(MMEItemIds.MITHRIL_HATCHET, getHandAxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SWORD = register(MMEItemIds.MITHRIL_SWORD, getSwordSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_DAGGER = register(MMEItemIds.MITHRIL_DAGGER, getDaggerSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_KNIFE = register(MMEItemIds.MITHRIL_KNIFE);
    public static final Item MITHRIL_PICKAXE = register(MMEItemIds.MITHRIL_PICKAXE, getPickaxeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_WAR_HAMMER = register(MMEItemIds.MITHRIL_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SHOVEL = registerShovelItem(MMEItemIds.MITHRIL_SHOVEL, getShovelSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_HOE = registerHoeItem(MMEItemIds.MITHRIL_HOE, getHoeSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_MATTOCK = registerHoeItem(MMEItemIds.MITHRIL_MATTOCK, getMattockSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SCYTHE = register(MMEItemIds.MITHRIL_SCYTHE, getScytheSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SHEARS = registerShearsItem(MMEItemIds.MITHRIL_SHEARS, getShearsSettings(MMEToolMaterials.MITHRIL).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item MITHRIL_SPEAR = register(MMEItemIds.MITHRIL_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.MITHRIL, 1.06F, 1.10F, 0.50F, 2.5F, 7.2F, 6.2F, 5.1F, 9.5F, 4.6F).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));

    public static final Item RUSTED_IRON_AXE = registerAxeItem(MMEItemIds.RUSTED_IRON_AXE, getAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_BATTLE_AXE = registerAxeItem(MMEItemIds.RUSTED_IRON_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HATCHET = registerAxeItem(MMEItemIds.RUSTED_IRON_HATCHET, getHandAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SWORD = register(MMEItemIds.RUSTED_IRON_SWORD, getSwordSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_DAGGER = register(MMEItemIds.RUSTED_IRON_DAGGER, getDaggerSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_KNIFE = register(MMEItemIds.RUSTED_IRON_KNIFE);
    public static final Item RUSTED_IRON_PICKAXE = register(MMEItemIds.RUSTED_IRON_PICKAXE, getPickaxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_WAR_HAMMER = register(MMEItemIds.RUSTED_IRON_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHOVEL = registerShovelItem(MMEItemIds.RUSTED_IRON_SHOVEL, getShovelSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HOE = registerHoeItem(MMEItemIds.RUSTED_IRON_HOE, getHoeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_MATTOCK = registerHoeItem(MMEItemIds.RUSTED_IRON_MATTOCK, getMattockSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SCYTHE = register(MMEItemIds.RUSTED_IRON_SCYTHE, getScytheSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHEARS = registerShearsItem(MMEItemIds.RUSTED_IRON_SHEARS, getShearsSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SPEAR = register(MMEItemIds.RUSTED_IRON_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.RUSTED_IRON, 0.90F, 0.88F, 0.63F, 3.6F, 8.6F, 7.6F, 5.1F, 11.8F, 4.6F));

    public static final Item SILVER_AXE = registerAxeItem(MMEItemIds.SILVER_AXE, getAxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_BATTLE_AXE = registerAxeItem(MMEItemIds.SILVER_BATTLE_AXE, getBattleAxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_HATCHET = registerAxeItem(MMEItemIds.SILVER_HATCHET, getHandAxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_DAGGER = register(MMEItemIds.SILVER_DAGGER, getDaggerSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_KNIFE = register(MMEItemIds.SILVER_KNIFE);
    public static final Item SILVER_SWORD = register(MMEItemIds.SILVER_SWORD, getSwordSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_PICKAXE = register(MMEItemIds.SILVER_PICKAXE, getPickaxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_WAR_HAMMER = register(MMEItemIds.SILVER_WAR_HAMMER, getWarHammerSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_SHOVEL = registerShovelItem(MMEItemIds.SILVER_SHOVEL, getShovelSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_HOE = registerHoeItem(MMEItemIds.SILVER_HOE, getHoeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_MATTOCK = registerHoeItem(MMEItemIds.SILVER_MATTOCK, getMattockSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_SCYTHE = register(MMEItemIds.SILVER_SCYTHE, getScytheSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_SHEARS = registerShearsItem(MMEItemIds.SILVER_SHEARS, getShearsSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_SPEAR = register(MMEItemIds.SILVER_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.SILVER, 0.98F, 1.00F, 0.56F, 2.8F, 7.8F, 6.8F, 5.1F, 10.5F, 4.6F));

    public static final Item OBSIDIAN_AXE = registerAxeItem(MMEItemIds.OBSIDIAN_AXE, applyToolSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, BlockTags.MINEABLE_WITH_AXE, 3.4F, 2, 4, -2.7F, 0.5F, 0.5F, 5));
    public static final Item OBSIDIAN_HATCHET = registerAxeItem(MMEItemIds.OBSIDIAN_HATCHET, applyToolSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, BlockTags.MINEABLE_WITH_AXE, 1, 2, 3, -2.7F, 0.25F, 0.25F, 5));
    public static final Item OBSIDIAN_KNIFE = register(MMEItemIds.OBSIDIAN_KNIFE, applySwordSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, 3, 1, 2, -2.0F, 0.25F, 0.25F));
    public static final Item OBSIDIAN_SHOVEL = register(MMEItemIds.OBSIDIAN_SHOVEL, applyToolSettings(new Item.Properties(), MMEToolMaterials.OBSIDIAN, BlockTags.MINEABLE_WITH_SHOVEL, 5, 2, 1, -2.8F, 0.75F, 0.75F, 0));

    public static final Item FLINT_AXE = registerAxeItem(MMEItemIds.FLINT_AXE, applyToolSettings(new Item.Properties(), MMEToolMaterials.FLINT, BlockTags.MINEABLE_WITH_AXE, 3.4F, 2, 4, -2.7F, 0.5F, 0.5F, 5));
    public static final Item FLINT_HATCHET = registerAxeItem(MMEItemIds.FLINT_HATCHET, applyToolSettings(new Item.Properties(), MMEToolMaterials.FLINT, BlockTags.MINEABLE_WITH_AXE, 1, 2, 3, -2.7F, 0.25F, 0.25F, 5));
    public static final Item FLINT_KNIFE = register(MMEItemIds.FLINT_KNIFE, applySwordSettings(new Item.Properties(), MMEToolMaterials.FLINT, 3, 1, 2, -2.0F, 0.25F, 0.25F));
    public static final Item FLINT_SHOVEL = register(MMEItemIds.FLINT_SHOVEL, applyToolSettings(new Item.Properties(), MMEToolMaterials.FLINT, BlockTags.MINEABLE_WITH_SHOVEL, 3, 2, 1, -2.8F, 0.75F, 0.75F, 0));
    public static final Item FLINT_SPEAR = register(MMEItemIds.FLINT_SPEAR, applySpearSettings(new Item.Properties(), MMEToolMaterials.FLINT, 0.72F, 0.76F, 0.70F, 4.8F, 12.0F, 9.8F, 5.1F, 14.2F, 4.6F));

    public static final Item WOODEN_CLUB = register(MMEItemIds.WOODEN_CLUB, applySwordSettings(new Item.Properties(), MMEToolMaterials.WOOD, 2, 1, 4, -2.4F, 0.5F, 0.5F));
    public static final Item WOODEN_CUDGEL = register(MMEItemIds.WOODEN_CUDGEL, applySwordSettings(new Item.Properties(), MMEToolMaterials.WOOD, 1, 1, 3, -2.0F, 0.25F, 0.25F));

    public static final Item ADAMANTIUM_CHAINS = register(MMEItemIds.ADAMANTIUM_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 75));
    public static final Item MITHRIL_CHAINS = register(MMEItemIds.MITHRIL_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 45));
    public static final Item ANCIENT_METAL_CHAINS = register(MMEItemIds.ANCIENT_METAL_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 30));
    public static final Item IRON_CHAINS = register(MMEItemIds.IRON_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 15));
    public static final Item RUSTED_IRON_CHAINS = register(MMEItemIds.RUSTED_IRON_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));
    public static final Item GOLDEN_CHAINS = register(MMEItemIds.GOLDEN_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));
    public static final Item COPPER_CHAINS = register(MMEItemIds.COPPER_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));
    public static final Item SILVER_CHAINS = register(MMEItemIds.SILVER_CHAINS,
            new Item.Properties().stacksTo(16).component(MMEDataComponents.CRAFTING_TIME, 5));

    public static final Item NETHERITE_COINS = register(
            MMEItemIds.NETHERITE_COINS,
            settings -> new CoinsItem(settings, 5000),
            new Item.Properties().stacksTo(32)
    );
    public static final Item ADAMANTIUM_COINS = register(
            MMEItemIds.ADAMANTIUM_COINS,
            settings -> new CoinsItem(settings, 1000),
            new Item.Properties().stacksTo(32)
    );
    public static final Item MITHRIL_COINS = register(
            MMEItemIds.MITHRIL_COINS,
            settings -> new CoinsItem(settings, 500),
            new Item.Properties().stacksTo(32)
    );
    public static final Item ANCIENT_METAL_COINS = register(
            MMEItemIds.ANCIENT_METAL_COINS,
            settings -> new CoinsItem(settings, 300),
            new Item.Properties().stacksTo(32)
    );
    public static final Item IRON_COINS = register(
            MMEItemIds.IRON_COINS,
            settings -> new CoinsItem(settings, 200),
            new Item.Properties().stacksTo(32)
    );
    public static final Item GOLDEN_COINS = register(
            MMEItemIds.GOLDEN_COINS,
            settings -> new CoinsItem(settings, 500),
            new Item.Properties().stacksTo(32)
    );
    public static final Item COPPER_COINS = register(
            MMEItemIds.COPPER_COINS,
            settings -> new CoinsItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );
    public static final Item SILVER_COINS = register(
            MMEItemIds.SILVER_COINS,
            settings -> new CoinsItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );

    public static final Item ADAMANTIUM_BUCKET = register(
            MMEItemIds.ADAMANTIUM_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item ADAMANTIUM_WATER_BUCKET = register(
            MMEItemIds.ADAMANTIUM_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .craftRemainder(ADAMANTIUM_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ADAMANTIUM_LAVA_BUCKET = register(
            MMEItemIds.ADAMANTIUM_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .craftRemainder(ADAMANTIUM_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ADAMANTIUM_POWDER_SNOW_BUCKET = register(
            MMEItemIds.ADAMANTIUM_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item ADAMANTIUM_MILK_BUCKET = register(
            MMEItemIds.ADAMANTIUM_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(ADAMANTIUM_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(ADAMANTIUM_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ADAMANTIUM_PUFFERFISH_BUCKET = register(
            MMEItemIds.ADAMANTIUM_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_SALMON_BUCKET = register(
            MMEItemIds.ADAMANTIUM_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_COD_BUCKET = register(
            MMEItemIds.ADAMANTIUM_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.ADAMANTIUM_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_AXOLOTL_BUCKET = register(
            MMEItemIds.ADAMANTIUM_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_TADPOLE_BUCKET = register(
            MMEItemIds.ADAMANTIUM_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ADAMANTIUM_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.ADAMANTIUM_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, ADAMANTIUM_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_BUCKET = register(MMEItemIds.ANCIENT_METAL_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item ANCIENT_METAL_WATER_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .craftRemainder(ANCIENT_METAL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ANCIENT_METAL_LAVA_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .craftRemainder(ANCIENT_METAL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ANCIENT_METAL_POWDER_SNOW_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item ANCIENT_METAL_MILK_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(ANCIENT_METAL_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(ANCIENT_METAL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item ANCIENT_METAL_PUFFERFISH_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_SALMON_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_COD_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_AXOLOTL_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_TADPOLE_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.ANCIENT_METAL_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_BUCKET = register(
            MMEItemIds.COPPER_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item COPPER_WATER_BUCKET = register(
            MMEItemIds.COPPER_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, COPPER_BUCKET),
            new Item.Properties()
                    .craftRemainder(COPPER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item COPPER_LAVA_BUCKET = register(
            MMEItemIds.COPPER_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, COPPER_BUCKET),
            new Item.Properties()
                    .craftRemainder(COPPER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item COPPER_POWDER_SNOW_BUCKET = register(
            MMEItemIds.COPPER_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item COPPER_MILK_BUCKET = register(
            MMEItemIds.COPPER_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(COPPER_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(COPPER_BUCKET).stacksTo(1)
    );
    public static final Item COPPER_PUFFERFISH_BUCKET = register(
            MMEItemIds.COPPER_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_SALMON_BUCKET = register(
            MMEItemIds.COPPER_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_COD_BUCKET = register(
            MMEItemIds.COPPER_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.COPPER_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_AXOLOTL_BUCKET = register(
            MMEItemIds.COPPER_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_TADPOLE_BUCKET = register(
            MMEItemIds.COPPER_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.COPPER_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, COPPER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_BUCKET = register(
            MMEItemIds.GOLD_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item GOLD_WATER_BUCKET = register(
            MMEItemIds.GOLD_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, GOLD_BUCKET),
            new Item.Properties()
                    .craftRemainder(GOLD_BUCKET)
                    .stacksTo(1)
    );
    public static final Item GOLD_LAVA_BUCKET = register(
            MMEItemIds.GOLD_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, GOLD_BUCKET),
            new Item.Properties()
                    .craftRemainder(GOLD_BUCKET)
                    .stacksTo(1)
    );
    public static final Item GOLD_POWDER_SNOW_BUCKET = register(
            MMEItemIds.GOLD_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item GOLD_MILK_BUCKET = register(
            MMEItemIds.GOLD_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(GOLD_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(GOLD_BUCKET)
                    .stacksTo(1)
    );
    public static final Item GOLD_PUFFERFISH_BUCKET = register(
            MMEItemIds.GOLD_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_SALMON_BUCKET = register(
            MMEItemIds.GOLD_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_COD_BUCKET = register(
            MMEItemIds.GOLD_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.GOLD_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_AXOLOTL_BUCKET = register(
            MMEItemIds.GOLD_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_TADPOLE_BUCKET = register(
            MMEItemIds.GOLD_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.GOLD_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, GOLD_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_BUCKET = register(
            MMEItemIds.MITHRIL_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item MITHRIL_WATER_BUCKET = register(
            MMEItemIds.MITHRIL_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .craftRemainder(MITHRIL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item MITHRIL_LAVA_BUCKET = register(
            MMEItemIds.MITHRIL_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .craftRemainder(MITHRIL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item MITHRIL_POWDER_SNOW_BUCKET = register(
            MMEItemIds.MITHRIL_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item MITHRIL_MILK_BUCKET = register(
            MMEItemIds.MITHRIL_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(MITHRIL_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(MITHRIL_BUCKET)
                    .stacksTo(1)
    );
    public static final Item MITHRIL_PUFFERFISH_BUCKET = register(
            MMEItemIds.MITHRIL_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_SALMON_BUCKET = register(
            MMEItemIds.MITHRIL_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_COD_BUCKET = register(
            MMEItemIds.MITHRIL_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.MITHRIL_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_AXOLOTL_BUCKET = register(
            MMEItemIds.MITHRIL_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_TADPOLE_BUCKET = register(
            MMEItemIds.MITHRIL_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.MITHRIL_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, MITHRIL_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_BUCKET = register(
            MMEItemIds.SILVER_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
    );
    public static final Item SILVER_WATER_BUCKET = register(
            MMEItemIds.SILVER_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, SILVER_BUCKET),
            new Item.Properties()
                    .craftRemainder(SILVER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item SILVER_LAVA_BUCKET = register(
            MMEItemIds.SILVER_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, SILVER_BUCKET),
            new Item.Properties()
                    .craftRemainder(SILVER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item SILVER_POWDER_SNOW_BUCKET = register(
            MMEItemIds.SILVER_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
    );
    public static final Item SILVER_MILK_BUCKET = register(
            MMEItemIds.SILVER_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(SILVER_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(SILVER_BUCKET)
                    .stacksTo(1)
    );
    public static final Item SILVER_PUFFERFISH_BUCKET = register(
            MMEItemIds.SILVER_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_SALMON_BUCKET = register(
            MMEItemIds.SILVER_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_COD_BUCKET = register(
            MMEItemIds.SILVER_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.SILVER_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_AXOLOTL_BUCKET = register(
            MMEItemIds.SILVER_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_TADPOLE_BUCKET = register(
            MMEItemIds.SILVER_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.SILVER_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, SILVER_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item NETHERITE_BUCKET = register(
            MMEItemIds.NETHERITE_BUCKET,
            settings -> new MMEBucketItem(Fluids.EMPTY, settings, null),
            new Item.Properties()
                    .stacksTo(16)
                    .fireResistant()
    );
    public static final Item NETHERITE_WATER_BUCKET = register(
            MMEItemIds.NETHERITE_WATER_BUCKET,
            settings -> new MMEBucketItem(Fluids.WATER, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .craftRemainder(NETHERITE_BUCKET)
                    .stacksTo(1)
                    .fireResistant()
    );
    public static final Item NETHERITE_LAVA_BUCKET = register(
            MMEItemIds.NETHERITE_LAVA_BUCKET,
            settings -> new MMEBucketItem(Fluids.LAVA, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .craftRemainder(NETHERITE_BUCKET)
                    .stacksTo(1)
                    .fireResistant()
    );
    public static final Item NETHERITE_POWDER_SNOW_BUCKET = register(
            MMEItemIds.NETHERITE_POWDER_SNOW_BUCKET,
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .useItemDescriptionPrefix()
                    .fireResistant()
    );
    public static final Item NETHERITE_MILK_BUCKET = register(
            MMEItemIds.NETHERITE_MILK_BUCKET,
            new Item.Properties()
                    .craftRemainder(NETHERITE_BUCKET)
                    .food(new FoodProperties(4, 0, false), Consumables.MILK_BUCKET)
                    .usingConvertsTo(NETHERITE_BUCKET)
                    .stacksTo(1)
                    .fireResistant()
    );
    public static final Item NETHERITE_PUFFERFISH_BUCKET = register(
            MMEItemIds.NETHERITE_PUFFERFISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_SALMON_BUCKET = register(
            MMEItemIds.NETHERITE_SALMON_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_COD_BUCKET = register(
            MMEItemIds.NETHERITE_COD_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_TROPICAL_FISH_BUCKET = register(
            MMEItemIds.NETHERITE_TROPICAL_FISH_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_AXOLOTL_BUCKET = register(
            MMEItemIds.NETHERITE_AXOLOTL_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_TADPOLE_BUCKET = register(
            MMEItemIds.NETHERITE_TADPOLE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_SULFUR_CUBE_BUCKET = register(
            MMEItemIds.NETHERITE_SULFUR_CUBE_BUCKET,
            settings -> new MMEMobBucketItem(EntityTypes.SULFUR_CUBE, Fluids.EMPTY, SoundEvents.BUCKET_EMPTY_SULFUR_CUBE, settings, NETHERITE_BUCKET),
            new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                    .fireResistant()
    );
    public static final Item NETHERITE_FISHING_ROD = register(MMEItemIds.NETHERITE_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(256).enchantable(24));
    public static final Item ADAMANTIUM_FISHING_ROD = register(MMEItemIds.ADAMANTIUM_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(128).enchantable(21));
    public static final Item ANCIENT_METAL_FISHING_ROD = register(MMEItemIds.ANCIENT_METAL_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(96).enchantable(15));
    public static final Item COPPER_FISHING_ROD = register(MMEItemIds.COPPER_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(8).enchantable(10));
    public static final Item FLINT_FISHING_ROD = register(MMEItemIds.FLINT_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(32).enchantable(5));
    public static final Item GOLDEN_FISHING_ROD = register(MMEItemIds.GOLDEN_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(4).enchantable(22));
    public static final Item IRON_FISHING_ROD = register(MMEItemIds.IRON_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(32).enchantable(12));
    public static final Item MITHRIL_FISHING_ROD = register(MMEItemIds.MITHRIL_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(64).enchantable(18));
    public static final Item OBSIDIAN_FISHING_ROD = register(MMEItemIds.OBSIDIAN_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(32).enchantable(5));
    public static final Item SILVER_FISHING_ROD = register(MMEItemIds.SILVER_FISHING_ROD, FishingRodItem::new, new Item.Properties().durability(8).enchantable(10));

    public static final Item RAW_ADAMANTIUM = register(MMEItemIds.RAW_ADAMANTIUM,
            new Item.Properties().stacksTo(8).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item RAW_MITHRIL = register(MMEItemIds.RAW_MITHRIL,
            new Item.Properties().stacksTo(8).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item RAW_SILVER = register(MMEItemIds.RAW_SILVER,
            new Item.Properties().stacksTo(8).component(MMEDataComponents.REQUIRED_COMBUSTION_GRADE, 2));

    public static final Item ADAMANTIUM_INGOT = register(MMEItemIds.ADAMANTIUM_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 50).stacksTo(16));
    public static final Item MITHRIL_INGOT = register(MMEItemIds.MITHRIL_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 30).stacksTo(16));
    public static final Item ANCIENT_METAL_INGOT = register(MMEItemIds.ANCIENT_METAL_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 20).stacksTo(16));
    public static final Item SILVER_INGOT = register(MMEItemIds.SILVER_INGOT,
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 5).stacksTo(16));

    public static final Item NETHERITE_NUGGET = register(
            MMEItemIds.NETHERITE_NUGGET,
            settings -> new NuggetItem(settings, 5000),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 10)
    );
    public static final Item ADAMANTIUM_NUGGET = register(
            MMEItemIds.ADAMANTIUM_NUGGET,
            settings -> new NuggetItem(settings, 1000),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 7)
    );
    public static final Item MITHRIL_NUGGET = register(
            MMEItemIds.MITHRIL_NUGGET,
            settings -> new NuggetItem(settings, 500),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 5)
    );
    public static final Item ANCIENT_METAL_NUGGET = register(
            MMEItemIds.ANCIENT_METAL_NUGGET,
            settings -> new NuggetItem(settings, 300),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 4)
    );
    public static final Item SILVER_NUGGET = register(
            MMEItemIds.SILVER_NUGGET,
            settings -> new NuggetItem(settings, 50),
            new Item.Properties().component(MMEDataComponents.CRAFTING_TIME, 1)
    );

    public static final Item FLINT_SHARD = register(MMEItemIds.FLINT_SHARD,
            new Item.Properties().stacksTo(16));
    public static final Item OBSIDIAN_SHARD = register(MMEItemIds.OBSIDIAN_SHARD,
            new Item.Properties().stacksTo(16));

    public static final Item SINEW = register(MMEItemIds.SINEW,
            new Item.Properties().stacksTo(16));
    public static final Item MANURE = register(
            MMEItemIds.MANURE,
            ManureItem::new,
            new Item.Properties().stacksTo(16)
    );
    public static final Item GHOUL_SPAWN_EGG = register(
            MMEItemIds.GHOUL_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.GHOUL)
    );
    public static final Item SHADOW_SPAWN_EGG = register(
            MMEItemIds.SHADOW_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.SHADOW)
    );
    public static final Item WIGHT_SPAWN_EGG = register(
            MMEItemIds.WIGHT_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.WIGHT)
    );
    public static final Item INVISIBLE_STALKER_SPAWN_EGG = register(
            MMEItemIds.INVISIBLE_STALKER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.INVISIBLE_STALKER)
    );
    public static final Item DEMON_SPIDER_SPAWN_EGG = register(
            MMEItemIds.DEMON_SPIDER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.DEMON_SPIDER)
    );
    public static final Item PHASE_SPIDER_SPAWN_EGG = register(
            MMEItemIds.PHASE_SPIDER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.PHASE_SPIDER)
    );
    public static final Item INFERNAL_CREEPER_SPAWN_EGG = register(
            MMEItemIds.INFERNAL_CREEPER_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.INFERNAL_CREEPER)
    );
    public static final Item FIRE_ELEMENTAL_SPAWN_EGG = register(
            MMEItemIds.FIRE_ELEMENTAL_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.FIRE_ELEMENTAL)
    );
    public static final Item VAMPIRE_BAT_SPAWN_EGG = register(
            MMEItemIds.VAMPIRE_BAT_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.VAMPIRE_BAT)
    );
    public static final Item NIGHTWING_SPAWN_EGG = register(
            MMEItemIds.NIGHTWING_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.NIGHTWING)
    );
    public static final Item GIANT_VAMPIRE_BAT_SPAWN_EGG = register(
            MMEItemIds.GIANT_VAMPIRE_BAT_SPAWN_EGG,
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(MMEEntityTypes.GIANT_VAMPIRE_BAT)
    );

    public static final CreativeModeTab MME_GROUP = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(MMEBlocks.ADAMANTIUM_AN_RUNESTORE))
            .title(Component.translatable("itemGroup.mme.item_group"))
            .displayItems((context, entries) -> {
                entries.accept(MMEBlocks.EMERALD_ENCHANTING_TABLE);

                entries.accept(MMEBlocks.CLAY_FURNACE);
                entries.accept(MMEBlocks.HARDENED_CLAY_FURNACE);
                entries.accept(MMEBlocks.NETHERRACK_FURNACE);
                entries.accept(MMEBlocks.OBSIDIAN_FURNACE);
                entries.accept(MMEBlocks.SANDSTONE_FURNACE);

                entries.accept(MMEBlocks.ADAMANTIUM_CRAFTING_TABLE);
                entries.accept(MMEBlocks.MITHRIL_CRAFTING_TABLE);
                entries.accept(MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE);
                entries.accept(MMEBlocks.IRON_CRAFTING_TABLE);
                entries.accept(MMEBlocks.GOLD_CRAFTING_TABLE);
                entries.accept(MMEBlocks.SILVER_CRAFTING_TABLE);
                entries.accept(MMEBlocks.COPPER_CRAFTING_TABLE);
                entries.accept(MMEBlocks.FLINT_CRAFTING_TABLE);
                entries.accept(MMEBlocks.OBSIDIAN_CRAFTING_TABLE);

                entries.accept(MMEBlocks.MITHRIL_NUL_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_QUAS_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_POR_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_AN_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_NOX_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_FLAM_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_VAS_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_DES_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_ORT_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_TYM_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_CORP_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_LOR_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_MANI_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_JUX_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_YLEM_RUNESTORE);
                entries.accept(MMEBlocks.MITHRIL_SANCT_RUNESTORE);

                entries.accept(MMEBlocks.ADAMANTIUM_NUL_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_QUAS_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_POR_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_AN_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_NOX_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_FLAM_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_VAS_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_DES_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_ORT_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_TYM_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_CORP_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_LOR_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_MANI_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_JUX_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_YLEM_RUNESTORE);
                entries.accept(MMEBlocks.ADAMANTIUM_SANCT_RUNESTORE);

                entries.accept(MMEBlocks.NETHERITE_ANVIL);
                entries.accept(MMEBlocks.CHIPPED_NETHERITE_ANVIL);
                entries.accept(MMEBlocks.DAMAGED_NETHERITE_ANVIL);
                entries.accept(MMEBlocks.ADAMANTIUM_ANVIL);
                entries.accept(MMEBlocks.CHIPPED_ADAMANTIUM_ANVIL);
                entries.accept(MMEBlocks.DAMAGED_ADAMANTIUM_ANVIL);
                entries.accept(MMEBlocks.MITHRIL_ANVIL);
                entries.accept(MMEBlocks.CHIPPED_MITHRIL_ANVIL);
                entries.accept(MMEBlocks.DAMAGED_MITHRIL_ANVIL);
                entries.accept(MMEBlocks.ANCIENT_METAL_ANVIL);
                entries.accept(MMEBlocks.CHIPPED_ANCIENT_METAL_ANVIL);
                entries.accept(MMEBlocks.DAMAGED_ANCIENT_METAL_ANVIL);
                entries.accept(MMEBlocks.GOLDEN_ANVIL);
                entries.accept(MMEBlocks.CHIPPED_GOLDEN_ANVIL);
                entries.accept(MMEBlocks.DAMAGED_GOLDEN_ANVIL);
                entries.accept(MMEBlocks.COPPER_ANVIL);
                entries.accept(MMEBlocks.CHIPPED_COPPER_ANVIL);
                entries.accept(MMEBlocks.DAMAGED_COPPER_ANVIL);
                entries.accept(MMEBlocks.SILVER_ANVIL);
                entries.accept(MMEBlocks.CHIPPED_SILVER_ANVIL);
                entries.accept(MMEBlocks.DAMAGED_SILVER_ANVIL);

                entries.accept(MMEBlocks.ADAMANTIUM_BLOCK);
                entries.accept(MMEBlocks.MITHRIL_BLOCK);
                entries.accept(MMEBlocks.ANCIENT_METAL_BLOCK);
                entries.accept(MMEBlocks.SILVER_BLOCK);

                entries.accept(MMEBlocks.ADAMANTIUM_ORE);
                entries.accept(MMEBlocks.DEEPSLATE_ADAMANTIUM_ORE);
                entries.accept(MMEBlocks.MITHRIL_ORE);
                entries.accept(MMEBlocks.DEEPSLATE_MITHRIL_ORE);
                entries.accept(MMEBlocks.SILVER_ORE);
                entries.accept(MMEBlocks.DEEPSLATE_SILVER_ORE);

                entries.accept(MMEBlocks.MANTLE);

                entries.accept(FLINT_SHARD);
                entries.accept(OBSIDIAN_SHARD);

                entries.accept(RAW_ADAMANTIUM);
                entries.accept(RAW_MITHRIL);
                entries.accept(RAW_SILVER);

                entries.accept(ADAMANTIUM_INGOT);
                entries.accept(MITHRIL_INGOT);
                entries.accept(ANCIENT_METAL_INGOT);
                entries.accept(SILVER_INGOT);

                entries.accept(NETHERITE_NUGGET);
                entries.accept(ADAMANTIUM_NUGGET);
                entries.accept(ANCIENT_METAL_NUGGET);
                entries.accept(MITHRIL_NUGGET);
                entries.accept(SILVER_NUGGET);

                entries.accept(NETHERITE_FISHING_ROD);
                entries.accept(ADAMANTIUM_FISHING_ROD);
                entries.accept(ANCIENT_METAL_FISHING_ROD);
                entries.accept(COPPER_FISHING_ROD);
                entries.accept(FLINT_FISHING_ROD);
                entries.accept(GOLDEN_FISHING_ROD);
                entries.accept(IRON_FISHING_ROD);
                entries.accept(MITHRIL_FISHING_ROD);
                entries.accept(OBSIDIAN_FISHING_ROD);
                entries.accept(SILVER_FISHING_ROD);
                entries.accept(ADAMANTIUM_HELMET);
                entries.accept(ADAMANTIUM_CHESTPLATE);
                entries.accept(ADAMANTIUM_LEGGINGS);
                entries.accept(ADAMANTIUM_BOOTS);
                entries.accept(ADAMANTIUM_CHAINMAIL_HELMET);
                entries.accept(ADAMANTIUM_CHAINMAIL_CHESTPLATE);
                entries.accept(ADAMANTIUM_CHAINMAIL_LEGGINGS);
                entries.accept(ADAMANTIUM_CHAINMAIL_BOOTS);
                entries.accept(MITHRIL_HELMET);
                entries.accept(MITHRIL_CHESTPLATE);
                entries.accept(MITHRIL_LEGGINGS);
                entries.accept(MITHRIL_BOOTS);
                entries.accept(MITHRIL_CHAINMAIL_HELMET);
                entries.accept(MITHRIL_CHAINMAIL_CHESTPLATE);
                entries.accept(MITHRIL_CHAINMAIL_LEGGINGS);
                entries.accept(MITHRIL_CHAINMAIL_BOOTS);
                entries.accept(ANCIENT_METAL_HELMET);
                entries.accept(ANCIENT_METAL_CHESTPLATE);
                entries.accept(ANCIENT_METAL_LEGGINGS);
                entries.accept(ANCIENT_METAL_BOOTS);
                entries.accept(ANCIENT_METAL_CHAINMAIL_HELMET);
                entries.accept(ANCIENT_METAL_CHAINMAIL_CHESTPLATE);
                entries.accept(ANCIENT_METAL_CHAINMAIL_LEGGINGS);
                entries.accept(ANCIENT_METAL_CHAINMAIL_BOOTS);
                entries.accept(RUSTED_IRON_HELMET);
                entries.accept(RUSTED_IRON_CHESTPLATE);
                entries.accept(RUSTED_IRON_LEGGINGS);
                entries.accept(RUSTED_IRON_BOOTS);
                entries.accept(RUSTED_IRON_CHAINMAIL_HELMET);
                entries.accept(RUSTED_IRON_CHAINMAIL_CHESTPLATE);
                entries.accept(RUSTED_IRON_CHAINMAIL_LEGGINGS);
                entries.accept(RUSTED_IRON_CHAINMAIL_BOOTS);
                entries.accept(COPPER_CHAINMAIL_HELMET);
                entries.accept(COPPER_CHAINMAIL_CHESTPLATE);
                entries.accept(COPPER_CHAINMAIL_LEGGINGS);
                entries.accept(COPPER_CHAINMAIL_BOOTS);
                entries.accept(SILVER_HELMET);
                entries.accept(SILVER_CHESTPLATE);
                entries.accept(SILVER_LEGGINGS);
                entries.accept(SILVER_BOOTS);
                entries.accept(SILVER_CHAINMAIL_HELMET);
                entries.accept(SILVER_CHAINMAIL_CHESTPLATE);
                entries.accept(SILVER_CHAINMAIL_LEGGINGS);
                entries.accept(SILVER_CHAINMAIL_BOOTS);
                entries.accept(GOLDEN_CHAINMAIL_HELMET);
                entries.accept(GOLDEN_CHAINMAIL_CHESTPLATE);
                entries.accept(GOLDEN_CHAINMAIL_LEGGINGS);
                entries.accept(GOLDEN_CHAINMAIL_BOOTS);

                entries.accept(NETHERITE_BATTLE_AXE);
                entries.accept(NETHERITE_DAGGER);
                entries.accept(NETHERITE_HATCHET);
                entries.accept(NETHERITE_WAR_HAMMER);
                entries.accept(NETHERITE_KNIFE);
                entries.accept(NETHERITE_MATTOCK);
                entries.accept(NETHERITE_SCYTHE);
                entries.accept(NETHERITE_SHEARS);
                entries.accept(ADAMANTIUM_AXE);
                entries.accept(ADAMANTIUM_BATTLE_AXE);
                entries.accept(ADAMANTIUM_DAGGER);
                entries.accept(ADAMANTIUM_HATCHET);
                entries.accept(ADAMANTIUM_HOE);
                entries.accept(ADAMANTIUM_KNIFE);
                entries.accept(ADAMANTIUM_MATTOCK);
                entries.accept(ADAMANTIUM_PICKAXE);
                entries.accept(ADAMANTIUM_SCYTHE);
                entries.accept(ADAMANTIUM_SHEARS);
                entries.accept(ADAMANTIUM_SHOVEL);
                entries.accept(ADAMANTIUM_SPEAR);
                entries.accept(ADAMANTIUM_SWORD);
                entries.accept(ADAMANTIUM_WAR_HAMMER);
                entries.accept(MITHRIL_AXE);
                entries.accept(MITHRIL_BATTLE_AXE);
                entries.accept(MITHRIL_DAGGER);
                entries.accept(MITHRIL_HATCHET);
                entries.accept(MITHRIL_HOE);
                entries.accept(MITHRIL_KNIFE);
                entries.accept(MITHRIL_MATTOCK);
                entries.accept(MITHRIL_PICKAXE);
                entries.accept(MITHRIL_SCYTHE);
                entries.accept(MITHRIL_SHEARS);
                entries.accept(MITHRIL_SHOVEL);
                entries.accept(MITHRIL_SPEAR);
                entries.accept(MITHRIL_SWORD);
                entries.accept(MITHRIL_WAR_HAMMER);
                entries.accept(ANCIENT_METAL_AXE);
                entries.accept(ANCIENT_METAL_BATTLE_AXE);
                entries.accept(ANCIENT_METAL_DAGGER);
                entries.accept(ANCIENT_METAL_HATCHET);
                entries.accept(ANCIENT_METAL_HOE);
                entries.accept(ANCIENT_METAL_KNIFE);
                entries.accept(ANCIENT_METAL_MATTOCK);
                entries.accept(ANCIENT_METAL_PICKAXE);
                entries.accept(ANCIENT_METAL_SCYTHE);
                entries.accept(ANCIENT_METAL_SHEARS);
                entries.accept(ANCIENT_METAL_SHOVEL);
                entries.accept(ANCIENT_METAL_SPEAR);
                entries.accept(ANCIENT_METAL_SWORD);
                entries.accept(ANCIENT_METAL_WAR_HAMMER);
                entries.accept(IRON_BATTLE_AXE);
                entries.accept(IRON_DAGGER);
                entries.accept(IRON_HATCHET);
                entries.accept(IRON_KNIFE);
                entries.accept(IRON_MATTOCK);
                entries.accept(IRON_SCYTHE);
                entries.accept(IRON_WAR_HAMMER);
                entries.accept(RUSTED_IRON_AXE);
                entries.accept(RUSTED_IRON_BATTLE_AXE);
                entries.accept(RUSTED_IRON_DAGGER);
                entries.accept(RUSTED_IRON_HATCHET);
                entries.accept(RUSTED_IRON_HOE);
                entries.accept(RUSTED_IRON_KNIFE);
                entries.accept(RUSTED_IRON_MATTOCK);
                entries.accept(RUSTED_IRON_PICKAXE);
                entries.accept(RUSTED_IRON_SCYTHE);
                entries.accept(RUSTED_IRON_SHEARS);
                entries.accept(RUSTED_IRON_SHOVEL);
                entries.accept(RUSTED_IRON_SPEAR);
                entries.accept(RUSTED_IRON_SWORD);
                entries.accept(RUSTED_IRON_WAR_HAMMER);
                entries.accept(COPPER_BATTLE_AXE);
                entries.accept(COPPER_DAGGER);
                entries.accept(COPPER_HATCHET);
                entries.accept(COPPER_KNIFE);
                entries.accept(COPPER_MATTOCK);
                entries.accept(COPPER_SCYTHE);
                entries.accept(COPPER_SHEARS);
                entries.accept(COPPER_WAR_HAMMER);
                entries.accept(SILVER_AXE);
                entries.accept(SILVER_BATTLE_AXE);
                entries.accept(SILVER_DAGGER);
                entries.accept(SILVER_HATCHET);
                entries.accept(SILVER_HOE);
                entries.accept(SILVER_KNIFE);
                entries.accept(SILVER_MATTOCK);
                entries.accept(SILVER_PICKAXE);
                entries.accept(SILVER_SCYTHE);
                entries.accept(SILVER_SHEARS);
                entries.accept(SILVER_SHOVEL);
                entries.accept(SILVER_SPEAR);
                entries.accept(SILVER_SWORD);
                entries.accept(SILVER_WAR_HAMMER);
                entries.accept(GOLDEN_BATTLE_AXE);
                entries.accept(GOLDEN_DAGGER);
                entries.accept(GOLDEN_HATCHET);
                entries.accept(GOLDEN_KNIFE);
                entries.accept(GOLDEN_MATTOCK);
                entries.accept(GOLDEN_SCYTHE);
                entries.accept(GOLDEN_SHEARS);
                entries.accept(GOLDEN_WAR_HAMMER);
                entries.accept(OBSIDIAN_AXE);
                entries.accept(OBSIDIAN_HATCHET);
                entries.accept(OBSIDIAN_KNIFE);
                entries.accept(OBSIDIAN_SHOVEL);
                entries.accept(FLINT_AXE);
                entries.accept(FLINT_HATCHET);
                entries.accept(FLINT_KNIFE);
                entries.accept(FLINT_SHOVEL);
                entries.accept(FLINT_SPEAR);
                entries.accept(WOODEN_CLUB);
                entries.accept(WOODEN_CUDGEL);

                entries.accept(BANANA);
                entries.accept(BLUE_BERRIE);
                entries.accept(CHEESE);
                entries.accept(CHOCOLATE);
                entries.accept(FLOUR);
                entries.accept(DOUGH);
                entries.accept(LEMON);
                entries.accept(ONION);
                entries.accept(ORANGE);
                entries.accept(WORM_COOKED);
                entries.accept(WORM_RAW);
                entries.accept(PUMPKIN_SOUP);
                entries.accept(SORBET);
                entries.accept(VEGETABLE_SOUP);
                entries.accept(BEEF_STEW);
                entries.accept(BOWL_MILK);
                entries.accept(BOWL_SALAD);
                entries.accept(BOWL_WATER);
                entries.accept(CEREAL);
                entries.accept(CHICKEN_SOUP);
                entries.accept(CREAM_OF_MUSHROOM_SOUP);
                entries.accept(CREAM_OF_VEGETABLE_SOUP);
                entries.accept(ICE_CREAM);
                entries.accept(MASHED_POTATO);
                entries.accept(PORRIDGE);
                entries.accept(SINEW);
                entries.accept(MANURE);
                entries.accept(GHOUL_SPAWN_EGG);
                entries.accept(SHADOW_SPAWN_EGG);
                entries.accept(WIGHT_SPAWN_EGG);
                entries.accept(INVISIBLE_STALKER_SPAWN_EGG);
                entries.accept(DEMON_SPIDER_SPAWN_EGG);
                entries.accept(PHASE_SPIDER_SPAWN_EGG);
                entries.accept(INFERNAL_CREEPER_SPAWN_EGG);
                entries.accept(FIRE_ELEMENTAL_SPAWN_EGG);
                entries.accept(VAMPIRE_BAT_SPAWN_EGG);
                entries.accept(NIGHTWING_SPAWN_EGG);
                entries.accept(GIANT_VAMPIRE_BAT_SPAWN_EGG);

                entries.accept(ADAMANTIUM_CHAINS);
                entries.accept(GOLDEN_CHAINS);
                entries.accept(IRON_CHAINS);
                entries.accept(MITHRIL_CHAINS);
                entries.accept(SILVER_CHAINS);
                entries.accept(ANCIENT_METAL_CHAINS);
                entries.accept(COPPER_CHAINS);
                entries.accept(RUSTED_IRON_CHAINS);
                entries.accept(NETHERITE_COINS);
                entries.accept(ADAMANTIUM_COINS);
                entries.accept(ANCIENT_METAL_COINS);
                entries.accept(COPPER_COINS);
                entries.accept(GOLDEN_COINS);
                entries.accept(IRON_COINS);
                entries.accept(MITHRIL_COINS);
                entries.accept(SILVER_COINS);

                entries.accept(NETHERITE_BUCKET);
                entries.accept(NETHERITE_WATER_BUCKET);
                entries.accept(NETHERITE_LAVA_BUCKET);
                entries.accept(NETHERITE_MILK_BUCKET);
                entries.accept(NETHERITE_POWDER_SNOW_BUCKET);
                entries.accept(NETHERITE_PUFFERFISH_BUCKET);
                entries.accept(NETHERITE_SALMON_BUCKET);
                entries.accept(NETHERITE_COD_BUCKET);
                entries.accept(NETHERITE_TROPICAL_FISH_BUCKET);
                entries.accept(NETHERITE_AXOLOTL_BUCKET);
                entries.accept(NETHERITE_TADPOLE_BUCKET);
                entries.accept(NETHERITE_SULFUR_CUBE_BUCKET);

                entries.accept(ADAMANTIUM_BUCKET);
                entries.accept(ADAMANTIUM_WATER_BUCKET);
                entries.accept(ADAMANTIUM_LAVA_BUCKET);
                entries.accept(ADAMANTIUM_MILK_BUCKET);
                entries.accept(ADAMANTIUM_POWDER_SNOW_BUCKET);
                entries.accept(ADAMANTIUM_PUFFERFISH_BUCKET);
                entries.accept(ADAMANTIUM_SALMON_BUCKET);
                entries.accept(ADAMANTIUM_COD_BUCKET);
                entries.accept(ADAMANTIUM_TROPICAL_FISH_BUCKET);
                entries.accept(ADAMANTIUM_AXOLOTL_BUCKET);
                entries.accept(ADAMANTIUM_TADPOLE_BUCKET);
                entries.accept(ADAMANTIUM_SULFUR_CUBE_BUCKET);

                entries.accept(MITHRIL_BUCKET);
                entries.accept(MITHRIL_WATER_BUCKET);
                entries.accept(MITHRIL_LAVA_BUCKET);
                entries.accept(MITHRIL_MILK_BUCKET);
                entries.accept(MITHRIL_POWDER_SNOW_BUCKET);
                entries.accept(MITHRIL_PUFFERFISH_BUCKET);
                entries.accept(MITHRIL_SALMON_BUCKET);
                entries.accept(MITHRIL_COD_BUCKET);
                entries.accept(MITHRIL_TROPICAL_FISH_BUCKET);
                entries.accept(MITHRIL_AXOLOTL_BUCKET);
                entries.accept(MITHRIL_TADPOLE_BUCKET);
                entries.accept(MITHRIL_SULFUR_CUBE_BUCKET);

                entries.accept(ANCIENT_METAL_BUCKET);
                entries.accept(ANCIENT_METAL_WATER_BUCKET);
                entries.accept(ANCIENT_METAL_LAVA_BUCKET);
                entries.accept(ANCIENT_METAL_MILK_BUCKET);
                entries.accept(ANCIENT_METAL_POWDER_SNOW_BUCKET);
                entries.accept(ANCIENT_METAL_PUFFERFISH_BUCKET);
                entries.accept(ANCIENT_METAL_SALMON_BUCKET);
                entries.accept(ANCIENT_METAL_COD_BUCKET);
                entries.accept(ANCIENT_METAL_TROPICAL_FISH_BUCKET);
                entries.accept(ANCIENT_METAL_AXOLOTL_BUCKET);
                entries.accept(ANCIENT_METAL_TADPOLE_BUCKET);
                entries.accept(ANCIENT_METAL_SULFUR_CUBE_BUCKET);

                entries.accept(COPPER_BUCKET);
                entries.accept(COPPER_WATER_BUCKET);
                entries.accept(COPPER_LAVA_BUCKET);
                entries.accept(COPPER_MILK_BUCKET);
                entries.accept(COPPER_POWDER_SNOW_BUCKET);
                entries.accept(COPPER_PUFFERFISH_BUCKET);
                entries.accept(COPPER_SALMON_BUCKET);
                entries.accept(COPPER_COD_BUCKET);
                entries.accept(COPPER_TROPICAL_FISH_BUCKET);
                entries.accept(COPPER_AXOLOTL_BUCKET);
                entries.accept(COPPER_TADPOLE_BUCKET);
                entries.accept(COPPER_SULFUR_CUBE_BUCKET);

                entries.accept(SILVER_BUCKET);
                entries.accept(SILVER_WATER_BUCKET);
                entries.accept(SILVER_LAVA_BUCKET);
                entries.accept(SILVER_MILK_BUCKET);
                entries.accept(SILVER_POWDER_SNOW_BUCKET);
                entries.accept(SILVER_PUFFERFISH_BUCKET);
                entries.accept(SILVER_SALMON_BUCKET);
                entries.accept(SILVER_COD_BUCKET);
                entries.accept(SILVER_TROPICAL_FISH_BUCKET);
                entries.accept(SILVER_AXOLOTL_BUCKET);
                entries.accept(SILVER_TADPOLE_BUCKET);
                entries.accept(SILVER_SULFUR_CUBE_BUCKET);

                entries.accept(GOLD_BUCKET);
                entries.accept(GOLD_WATER_BUCKET);
                entries.accept(GOLD_LAVA_BUCKET);
                entries.accept(GOLD_MILK_BUCKET);
                entries.accept(GOLD_POWDER_SNOW_BUCKET);
                entries.accept(GOLD_PUFFERFISH_BUCKET);
                entries.accept(GOLD_SALMON_BUCKET);
                entries.accept(GOLD_COD_BUCKET);
                entries.accept(GOLD_TROPICAL_FISH_BUCKET);
                entries.accept(GOLD_AXOLOTL_BUCKET);
                entries.accept(GOLD_TADPOLE_BUCKET);
                entries.accept(GOLD_SULFUR_CUBE_BUCKET);
            })
            .build();

    public static Item.Properties getArmorSettings(MMEArmorMaterial material, ArmorType type) {
            return new Item.Properties().durability(type.getDurability((int) (material.durability() * 0.45)))
                    .attributes(material.createAttributeModifiers(type))
                    .enchantable(material.enchantmentValue())
                    .component(
                            DataComponents.EQUIPPABLE,
                            Equippable.builder(type.getSlot()).setEquipSound(material.equipSound()).setAsset(material.assetId()).build()
                    )
                    .repairable(material.repairIngredient());

    }

    public static Item.Properties getDaggerSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Properties(), material, 2, 1, 2, -2.0F, 0.25F, 0.25F);
    }
    public static Item.Properties getSwordSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Properties(), material, 5, 1, 3, -2.4F, 0.5F, 0.5F);
    }

    public static Item.Properties getScytheSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, MMEBlockTags.MINEABLE_WITH_SCYTHE, 9, 1, 3, -3.0f , 0.75f, 0.75f);
    }

    public static Item.Properties getAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 5, 2, 4, -2.7F, 0.5F, 0.5F, 5);
    }
    public static Item.Properties getBattleAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 7, 1, 5, -3F, 0.5F, 0.75F, 5);
    }
    public static Item.Properties getHandAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 2, 2, 3, -2.7F, 0.25F, 0.25F, 5);
    }

    public static Item.Properties getHoeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_HOE, 4, 2, 1, -1.0F, 0.5F, 0.5F);
    }
    public static Item.Properties getMattockSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, MMEBlockTags.MINEABLE_WITH_MATTOCK, 6, 2, 2, -1.5F, 0.5F, 0.5F);
    }

    public static Item.Properties getShovelSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_SHOVEL, 3, 2, 1, -2.8F, 0.75F, 0.75F);
    }

    public static Item.Properties getPickaxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_PICKAXE, 3, 2, 2, -2.8f , 0.75f, 0.75f);
    }
    public static Item.Properties getWarHammerSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_PICKAXE, 5, 1, 3, -3.0f , 0.75f, 0.75f);
    }

    public static Item.Properties applySpearSettings(
            Item.Properties settings,
            ToolMaterial toolMaterial,
            float swingDurationSeconds,
            float chargedKnockback,
            float chargeDurationSeconds,
            float lightSpeedStartSeconds,
            float lightSpeedThreshold,
            float heavySpeedStartSeconds,
            float heavySpeedThreshold,
            float relativeSpeedStartSeconds,
            float relativeSpeedThreshold
    ) {
        return applyBaseSettings(settings, toolMaterial, 4)
                .delayedHolderComponent(DataComponents.DAMAGE_TYPE, DamageTypes.SPEAR)
                .component(
                        DataComponents.KINETIC_WEAPON,
                        new KineticWeapon(
                                10,
                                (int)(chargeDurationSeconds * 20.0F),
                                KineticWeapon.Condition.ofAttackerSpeed((int)(lightSpeedStartSeconds * 20.0F), lightSpeedThreshold),
                                KineticWeapon.Condition.ofAttackerSpeed((int)(heavySpeedStartSeconds * 20.0F), heavySpeedThreshold),
                                KineticWeapon.Condition.ofRelativeSpeed((int)(relativeSpeedStartSeconds * 20.0F), relativeSpeedThreshold),
                                0.38F,
                                chargedKnockback,
                                Optional.of(toolMaterial == ToolMaterial.WOOD ? SoundEvents.SPEAR_WOOD_USE : SoundEvents.SPEAR_USE),
                                Optional.of(toolMaterial == ToolMaterial.WOOD ? SoundEvents.SPEAR_WOOD_HIT : SoundEvents.SPEAR_HIT)
                        )
                )
                .component(
                        DataComponents.PIERCING_WEAPON,
                        new PiercingWeapon(
                                true,
                                false,
                                Optional.of(SoundEvents.SPEAR_ATTACK),
                                Optional.of(SoundEvents.SPEAR_HIT)
                        )
                )
                .component(DataComponents.ATTACK_RANGE, new AttackRange(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F))
                .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F)
                .component(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int)(swingDurationSeconds * 20.0F)))
                .attributes(
                        ItemAttributeModifiers.builder()
                                .add(
                                        Attributes.ATTACK_DAMAGE,
                                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 1 + toolMaterial.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND
                                )
                                .add(
                                        Attributes.ATTACK_SPEED,
                                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 1.0F / swingDurationSeconds - 4.0, AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND
                                )
                                .build()
                )
                .component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F))
                .component(DataComponents.WEAPON, new Weapon(1));
    }

    public static Item.Properties getShearsSettings(ToolMaterial material) {
        return applyBaseSettings(new Item.Properties(), material, 7)
                .component(DataComponents.TOOL, ShearsItem.createToolProperties())
                .attributes(CreateAttributeModifiers(0.5f, 0.5f, material.attackDamageBonus(), 0));
    }
    public static Item.Properties applyToolSettings(
            Item.Properties settings,
            ToolMaterial material,
            TagKey<Block> effectiveBlocks,
            float durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange
    ){
        return applyToolSettings(settings, material, effectiveBlocks, durabilityMultiplier, itemDamagePerAttack, attackDamage, attackSpeed, blockInteractionRange, entityInteractionRange, 0.0F);
    }

    public static Item.Properties applyToolSettings(
            Item.Properties settings,
            ToolMaterial material,
            TagKey<Block> effectiveBlocks,
            float durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange,
            float disableBlockingForSeconds
    ) {
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return applyBaseSettings(settings, material, durabilityMultiplier)
                .component(
                        DataComponents.TOOL,
                        new Tool(
                                List.of(
                                        Tool.Rule.deniesDrops(registryEntryLookup.getOrThrow(material.incorrectBlocksForDrops())),
                                        Tool.Rule.minesAndDrops(registryEntryLookup.getOrThrow(effectiveBlocks), material.speed())
                                ),
                                1.0F,
                                1,
                                true
                        )
                )
                .attributes(CreateAttributeModifiers(blockInteractionRange, entityInteractionRange, attackDamage + material.attackDamageBonus(), attackSpeed))
                .component(DataComponents.WEAPON, new Weapon(itemDamagePerAttack, disableBlockingForSeconds));
    }

    public static Item.Properties applySwordSettings(
            Item.Properties settings,
            ToolMaterial material,
            int durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange
    ) {
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return applyBaseSettings(settings, material, durabilityMultiplier)
                .component(
                        DataComponents.TOOL,
                        new Tool(
                                List.of(
                                        Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                                        Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                        Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                                ),
                                1.0F,
                                2,
                                false
                        )
                )
                .attributes(CreateAttributeModifiers(blockInteractionRange, entityInteractionRange, attackDamage + material.attackDamageBonus(), attackSpeed))
                .component(DataComponents.WEAPON, new Weapon(itemDamagePerAttack));
    }

    public static ItemAttributeModifiers CreateAttributeModifiers(float blockInteractionRange,float entityInteractionRange, float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.BLOCK_INTERACTION_RANGE,
                        new AttributeModifier(MME.BASE_BLOCK_INTERACTION_RANGE, blockInteractionRange, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(MME.BASE_ENTITY_INTERACTION_RANGE, entityInteractionRange, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }
    public static Item.Properties applyBaseSettings(Item.Properties settings, ToolMaterial material, float durabilityMultiplier) {
        Item.Properties settings1 = settings.durability((int) (material.durability() * durabilityMultiplier)).repairable(material.repairItems()).enchantable(material.enchantmentValue());
        if (material == ToolMaterial.NETHERITE){
            settings1 = settings1.fireResistant();
        }
        return settings1;
    }
    public static Item.Properties VanillaSpearSettings(Item.Properties settings, ToolMaterial material, float durabilityMultiplier, float swingDurationSeconds){
        return applyBaseSettings(settings, material, durabilityMultiplier).attributes(
                ItemAttributeModifiers.builder()
                        .add(
                                Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 1 + material.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND
                        )
                        .add(
                                Attributes.ATTACK_SPEED,
                                new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 1.0F / swingDurationSeconds - 4.0, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND
                        )
                        .build()
        );
    }

    private static Item register(ResourceKey<Item> registryKey) {
        return Registry.register(BuiltInRegistries.ITEM, registryKey, new Item(new Item.Properties().setId(registryKey)));
    }

    private static Item register(ResourceKey<Item> registryKey, Item.Properties settings) {
        return Registry.register(BuiltInRegistries.ITEM, registryKey, new Item(settings.setId(registryKey)));
    }

    private static Item register(ResourceKey<Item> registryKey, Function<Item.Properties, Item> factory, Item.Properties settings) {
        return Registry.register(BuiltInRegistries.ITEM, registryKey, factory.apply(settings.setId(registryKey)));
    }

    private static Item registerAxeItem(ResourceKey<Item> registryKey, Item.Properties settings) {
        return register(registryKey, MMEAxeItem::new, settings);
    }

    private static Item registerShearsItem(ResourceKey<Item> registryKey, Item.Properties settings) {
        return register(registryKey, ShearsItem::new, settings);
    }

    private static Item registerShovelItem(ResourceKey<Item> registryKey, Item.Properties settings) {
        return register(registryKey, MMEShovelItem::new, settings);
    }

    private static Item registerHoeItem(ResourceKey<Item> registryKey, Item.Properties settings) {
        return register(registryKey, MMEHoeItem::new, settings);
    }

    public static Item register(Block block, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, block.builtInRegistryHolder().key().identifier());
        BlockItem item = new BlockItem(block, settings.useBlockDescriptionPrefix().setId(itemKey));
        item.registerBlocks(Item.BY_BLOCK, item);
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MME.MOD_ID, "item_group"), MME_GROUP);
    }
}
