package com.acuteterror233.mite.item;

import com.acuteterror233.mite.Mme;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.component.AtDataComponentTypes;
import com.acuteterror233.mite.item.armor.AtArmorMaterials;
import com.acuteterror233.mite.item.equipment.AtArmorMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;
/**
 * 集中定义并注册模组中的全部物品与物品组（创造模式标签）。
 *
 * <p>本类提供多种重载的 register 辅助方法，
 * 以统一方式完成 {@link Item} 的构造与注册，同时在需要时绑定方块或自定义 {@link Item.Settings}。
 * 另外通过 {@link #AT_MINT_GROUP} 将常用物品收纳到同一物品组便于测试与查找。</p>
 */
public class AtItems {
    public static final Item ADAMANTIUM_HELMET = register(
            "adamantium_helmet",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHESTPLATE = register(
            "adamantium_chestplate",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_LEGGINGS = register(
            "adamantium_leggings",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item ADAMANTIUM_BOOTS = register(
            "adamantium_boots",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_BOOTS = register(
            "adamantium_chainmail_boots",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_CHESTPLATE = register(
            "adamantium_chainmail_chestplate",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register(
            "adamantium_chainmail_helmet",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_LEGGINGS = register(
            "adamantium_chainmail_leggings",
            getArmorSettings(AtArmorMaterials.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_HELMET = register(
            "ancient_metal_helmet",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHESTPLATE = register(
            "ancient_metal_chestplate",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_LEGGINGS = register(
            "ancient_metal_leggings",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_BOOTS = register(
            "ancient_metal_boots",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_BOOTS = register(
            "ancient_metal_chainmail_boots",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_CHESTPLATE = register(
            "ancient_metal_chainmail_chestplate",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_HELMET = register(
            "ancient_metal_chainmail_helmet",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_LEGGINGS = register(
            "ancient_metal_chainmail_leggings",
            getArmorSettings(AtArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_HELMET = register(
            "copper_helmet",
            getArmorSettings(AtArmorMaterials.COPPER_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item COPPER_CHESTPLATE = register(
            "copper_chestplate",
            getArmorSettings(AtArmorMaterials.COPPER_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_LEGGINGS = register(
            "copper_leggings",
            getArmorSettings(AtArmorMaterials.COPPER_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_BOOTS = register(
            "copper_boots",
            getArmorSettings(AtArmorMaterials.COPPER_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_BOOTS = register(
            "copper_chainmail_boots",
            getArmorSettings(AtArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_CHESTPLATE = register(
            "copper_chainmail_chestplate",
            getArmorSettings(AtArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_CHAINMAIL_HELMET = register(
            "copper_chainmail_helmet",
            getArmorSettings(AtArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item COPPER_CHAINMAIL_LEGGINGS = register(
            "copper_chainmail_leggings",
            getArmorSettings(AtArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_HELMET = register(
            "mithril_helmet",
            getArmorSettings(AtArmorMaterials.MITHRIL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item MITHRIL_CHESTPLATE = register(
            "mithril_chestplate",
            getArmorSettings(AtArmorMaterials.MITHRIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item MITHRIL_LEGGINGS = register(
            "mithril_leggings",
            getArmorSettings(AtArmorMaterials.MITHRIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_BOOTS = register(
            "mithril_boots",
            getArmorSettings(AtArmorMaterials.MITHRIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_HELMET = register(
            "mithril_chainmail_helmet",
            getArmorSettings(AtArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_CHESTPLATE = register(
            "mithril_chainmail_chestplate",
            getArmorSettings(AtArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item MITHRIL_CHAINMAIL_LEGGINGS = register(
            "mithril_chainmail_leggings",
            getArmorSettings(AtArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_CHAINMAIL_BOOTS = register(
            "mithril_chainmail_boots",
            getArmorSettings(AtArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item RUSTED_IRON_HELMET = register(
            "rusted_iron_helmet",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item RUSTED_IRON_CHESTPLATE = register(
            "rusted_iron_chestplate",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_LEGGINGS = register(
            "rusted_iron_leggings",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_BOOTS = register(
            "rusted_iron_boots",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_HELMET = register(
            "rusted_iron_chainmail_helmet",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_CHESTPLATE = register(
            "rusted_iron_chainmail_chestplate",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_LEGGINGS = register(
            "rusted_iron_chainmail_leggings",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_BOOTS = register(
            "rusted_iron_chainmail_boots",
            getArmorSettings(AtArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item SILVER_HELMET = register(
            "silver_helmet",
            getArmorSettings(AtArmorMaterials.SILVER_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item SILVER_CHESTPLATE = register(
            "silver_chestplate",
            getArmorSettings(AtArmorMaterials.SILVER_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item SILVER_LEGGINGS = register(
            "silver_leggings",
            getArmorSettings(AtArmorMaterials.SILVER_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item SILVER_BOOTS = register(
            "silver_boots",
            getArmorSettings(AtArmorMaterials.SILVER_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item SILVER_CHAINMAIL_HELMET = register(
            "silver_chainmail_helmet",
            getArmorSettings(AtArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item SILVER_CHAINMAIL_CHESTPLATE = register(
            "silver_chainmail_chestplate",
            getArmorSettings(AtArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item SILVER_CHAINMAIL_LEGGINGS = register(
            "silver_chainmail_leggings",
            getArmorSettings(AtArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item SILVER_CHAINMAIL_BOOTS = register(
            "silver_chainmail_boots",
            getArmorSettings(AtArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );
    public static final Item GOLDEN_CHAINMAIL_HELMET = register(
            "golden_chainmail_helmet",
            getArmorSettings(AtArmorMaterials.GOLD_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item GOLDEN_CHAINMAIL_CHESTPLATE = register(
            "golden_chainmail_chestplate",
            getArmorSettings(AtArmorMaterials.GOLD_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
    );
    public static final Item GOLDEN_CHAINMAIL_LEGGINGS = register(
            "golden_chainmail_leggings",
            getArmorSettings(AtArmorMaterials.GOLD_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
    );
    public static final Item GOLDEN_CHAINMAIL_BOOTS = register(
            "golden_chainmail_boots",
            getArmorSettings(AtArmorMaterials.GOLD_CHAINMAIL_ARMOR_MATERIAL, EquipmentType.BOOTS)
    );

    public static final Item BANANA = register(
            "banana",
            new Item.Settings().food(new FoodComponent(2, 1.0F, false)).maxCount(16));
    public static final Item BLUEBERRIE = register(
            "blueberry",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)).maxCount(16));
    public static final Item CHEESE = register(
            "cheese",
            new Item.Settings().food(new FoodComponent(3, 3.0F, false)).maxCount(16));
    public static final Item CHOCOLATE = register(
            "chocolate",
            new Item.Settings().food(new FoodComponent(3, 3.0F, false)).maxCount(16));
    public static final Item DOUGH = register(
            "dough",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)).maxCount(16));
    public static final Item LEMON = register(
            "lemon",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)).maxCount(16));
    public static final Item ONION = register(
            "onion",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)).maxCount(16));
    public static final Item ORANGE = register(
            "orange",
            new Item.Settings().food(new FoodComponent(2, 1.0F, false)).maxCount(16));
    public static final Item WORM_COOKED = register(
            "worm_cooked",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)).maxCount(16));
    public static final Item WORM_RAW = register(
            "worm_raw",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)).maxCount(16));
    public static final Item FLOUR = register(
            "flour",
            new Item.Settings().maxCount(16));
    public static final Item BEEF_STEW = register(
            "beef_stew",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item BOWL_MILK = register(
            "bowl_milk",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item BOWL_SALAD = register(
            "bowl_salad",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item BOWL_WATER = register(
            "bowl_water",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item CEREAL = register(
            "cereal",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item CHICKEN_SOUP = register(
            "chicken_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item CREAM_OF_MUSHROOM_SOUP = register(
            "cream_of_mushroom_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item CREAM_OF_VEGETABLE_SOUP = register(
            "cream_of_vegetable_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item ICE_CREAM = register(
            "ice_cream",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item MASHED_POTATO = register(
            "mashed_potato",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item MUSHROOM_STEW = register(
            "mushroom_stew",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item PORRIDGE = register(
            "porridge",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item PUMPKIN_SOUP = register(
            "pumpkin_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item SORBET = register(
            "sorbet",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));
    public static final Item VEGETABLE_SOUP = register(
            "vegetable_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL).recipeRemainder(Items.BOWL).maxCount(4));


    public static final Item ADAMANTIUM_AXE = registerAxeItem("adamantium_axe", getAxeSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_BATTLE_AXE = registerAxeItem("adamantium_battle_axe", getBattleAxeSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_HATCHET = registerAxeItem("adamantium_hatchet", getHandAxeSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SWORD = register("adamantium_sword", getSwordSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_DAGGER = register("adamantium_dagger", getDaggerSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_KNIFE = register("adamantium_knife");
    public static final Item ADAMANTIUM_PICKAXE = register("adamantium_pickaxe", getPickaxeSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_WAR_HAMMER = register("adamantium_war_hammer", getWarHammerSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SHOVEL = registerShovelItem("adamantium_shovel", getShovelSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_HOE = registerHoeItem("adamantium_hoe", getHoeSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_MATTOCK = registerHoeItem("adamantium_mattock", getMattockSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SCYTHE = register("adamantium_scythe", getScytheSettings(AtToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SHEARS = register("adamantium_shears", getShearsSettings(AtToolMaterials.ADAMANTIUM));

    public static final Item ANCIENT_METAL_AXE = registerAxeItem("ancient_metal_axe", getAxeSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_BATTLE_AXE = registerAxeItem("ancient_metal_battle_axe", getBattleAxeSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HATCHET = registerAxeItem("ancient_metal_hatchet", getHandAxeSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SWORD = register("ancient_metal_sword", getSwordSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_DAGGER = register("ancient_metal_dagger", getDaggerSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_KNIFE = register("ancient_metal_knife");
    public static final Item ANCIENT_METAL_PICKAXE = register("ancient_metal_pickaxe", getPickaxeSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_WAR_HAMMER = register("ancient_metal_war_hammer", getWarHammerSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHOVEL = registerShovelItem("ancient_metal_shovel", getShovelSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HOE = registerHoeItem("ancient_metal_hoe", getHoeSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_MATTOCK = registerHoeItem("ancient_metal_mattock", getMattockSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SCYTHE = registerAxeItem("ancient_metal_scythe", getScytheSettings(AtToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHEARS = registerAxeItem("ancient_metal_shears", getShearsSettings(AtToolMaterials.ANCIENT_METAL));

    public static final Item COPPER_AXE = registerAxeItem("copper_axe", getAxeSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_BATTLE_AXE = registerAxeItem("copper_battle_axe", getBattleAxeSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_HATCHET = registerAxeItem("copper_hatchet", getHandAxeSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_SWORD = register("copper_sword", getSwordSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_DAGGER = register("copper_dagger", getDaggerSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_KNIFE = register("copper_knife");
    public static final Item COPPER_PICKAXE = register("copper_pickaxe", getPickaxeSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_WAR_HAMMER = register("copper_war_hammer", getWarHammerSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_SHOVEL = register("copper_shovel", getShovelSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_HOE = register("copper_hoe", getHoeSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_MATTOCK = register("copper_mattock", getMattockSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_SCYTHE = register("copper_scythe", getScytheSettings(AtToolMaterials.COPPER));
    public static final Item COPPER_SHEARS = register("copper_shears", getShearsSettings(AtToolMaterials.COPPER));

    public static final Item GOLDEN_BATTLE_AXE = registerAxeItem("golden_battle_axe", getBattleAxeSettings(ToolMaterial.GOLD));
    public static final Item GOLDEN_HATCHET = registerAxeItem("golden_hatchet", getHandAxeSettings(ToolMaterial.GOLD));
    public static final Item GOLDEN_DAGGER = register("golden_dagger", getDaggerSettings(ToolMaterial.GOLD));
    public static final Item GOLDEN_KNIFE = register("golden_knife");
    public static final Item GOLDEN_WAR_HAMMER = register("golden_war_hammer", getWarHammerSettings(ToolMaterial.GOLD));
    public static final Item GOLDEN_MATTOCK = register("golden_mattock", getMattockSettings(ToolMaterial.GOLD));
    public static final Item GOLDEN_SCYTHE = register("golden_scythe", getScytheSettings(ToolMaterial.GOLD));
    public static final Item GOLDEN_SHEARS = register("golden_shears", getShearsSettings(ToolMaterial.GOLD));

    public static final Item IRON_BATTLE_AXE = registerAxeItem("iron_battle_axe", getBattleAxeSettings(ToolMaterial.IRON));
    public static final Item IRON_DAGGER = register("iron_dagger", getDaggerSettings(ToolMaterial.IRON));
    public static final Item IRON_HATCHET = registerAxeItem("iron_hatchet", getHandAxeSettings(ToolMaterial.IRON));
    public static final Item IRON_WAR_HAMMER = register("iron_war_hammer", getWarHammerSettings(ToolMaterial.IRON));
    public static final Item IRON_KNIFE = register("iron_knife");
    public static final Item IRON_MATTOCK = register("iron_mattock", getMattockSettings(ToolMaterial.IRON));
    public static final Item IRON_SCYTHE = register("iron_scythe", getScytheSettings(ToolMaterial.IRON));

    public static final Item MITHRIL_AXE = registerAxeItem("mithril_axe", getAxeSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_BATTLE_AXE = registerAxeItem("mithril_battle_axe", getBattleAxeSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_HATCHET = registerAxeItem("mithril_hatchet", getHandAxeSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_SWORD = register("mithril_sword", getSwordSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_DAGGER = register("mithril_dagger", getDaggerSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_KNIFE = register("mithril_knife");
    public static final Item MITHRIL_PICKAXE = register("mithril_pickaxe", getPickaxeSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_WAR_HAMMER = register("mithril_war_hammer", getWarHammerSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_SHOVEL = register("mithril_shovel", getShovelSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_HOE = register("mithril_hoe", getHoeSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_MATTOCK = register("mithril_mattock", getMattockSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_SCYTHE = register("mithril_scythe", getScytheSettings(AtToolMaterials.MITHRIL));
    public static final Item MITHRIL_SHEARS = register("mithril_shears", getShearsSettings(AtToolMaterials.MITHRIL));

    public static final Item RUSTED_IRON_AXE = registerAxeItem("rusted_iron_axe", getAxeSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_BATTLE_AXE = registerAxeItem("rusted_iron_battle_axe", getBattleAxeSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HATCHET = registerAxeItem("rusted_iron_hatchet", getHandAxeSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SWORD = register("rusted_iron_sword", getSwordSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_DAGGER = register("rusted_iron_dagger", getDaggerSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_KNIFE = register("rusted_iron_knife");
    public static final Item RUSTED_IRON_PICKAXE = register("rusted_iron_pickaxe", getPickaxeSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_WAR_HAMMER = register("rusted_iron_war_hammer", getWarHammerSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHOVEL = register("rusted_iron_shovel", getShovelSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HOE = register("rusted_iron_hoe", getHoeSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_MATTOCK = register("rusted_iron_mattock", getMattockSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SCYTHE = register("rusted_iron_scythe", getScytheSettings(AtToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHEARS = register("rusted_iron_shears", getShearsSettings(AtToolMaterials.RUSTED_IRON));

    public static final Item SILVER_AXE = registerAxeItem("silver_axe", getAxeSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_BATTLE_AXE = registerAxeItem("silver_battle_axe", getBattleAxeSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_HATCHET = registerAxeItem("silver_hatchet", getHandAxeSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_DAGGER = register("silver_dagger", getDaggerSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_KNIFE = register("silver_knife");
    public static final Item SILVER_SWORD = register("silver_sword", getSwordSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_PICKAXE = register("silver_pickaxe", getPickaxeSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_WAR_HAMMER = register("silver_war_hammer", getWarHammerSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_SHOVEL = register("silver_shovel", getShovelSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_HOE = register("silver_hoe", getHoeSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_MATTOCK = register("silver_mattock", getMattockSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_SCYTHE = register("silver_scythe", getScytheSettings(AtToolMaterials.SILVER));
    public static final Item SILVER_SHEARS = register("silver_shears", getShearsSettings(AtToolMaterials.SILVER));

    public static final Item OBSIDIAN_AXE = registerAxeItem("obsidian_axe", getAxeSettings(AtToolMaterials.OBSIDIAN));
    public static final Item OBSIDIAN_HATCHET = registerAxeItem("obsidian_hatchet", getHandAxeSettings(AtToolMaterials.OBSIDIAN));
    public static final Item OBSIDIAN_KNIFE = register("obsidian_knife", getDaggerSettings(AtToolMaterials.OBSIDIAN));
    public static final Item OBSIDIAN_SHOVEL = register("obsidian_shovel", getShovelSettings(AtToolMaterials.OBSIDIAN));

    public static final Item FLINT_AXE = registerAxeItem("flint_axe", getAxeSettings(AtToolMaterials.FLINT));
    public static final Item FLINT_HATCHET = registerAxeItem("flint_hatchet", getHandAxeSettings(AtToolMaterials.FLINT));
    public static final Item FLINT_KNIFE = register("flint_knife", getDaggerSettings(AtToolMaterials.FLINT));
    public static final Item FLINT_SHOVEL = register("flint_shovel", getShovelSettings(AtToolMaterials.FLINT));

    public static final Item STONE_DAGGER = register("stone_dagger");
    public static final Item WOOD_CLUB = register("wood_club", getDaggerSettings(ToolMaterial.WOOD));
    public static final Item WOOD_CUDGEL = register("wood_cudgel", getSwordSettings(ToolMaterial.WOOD));

    public static final Item ADAMANTIUM_ARROW = register("adamantium_arrow",
            new Item.Settings().maxCount(16));
    public static final Item ANCIENT_METAL_ARROW = register("ancient_metal_arrow",
            new Item.Settings().maxCount(16));
    public static final Item COPPER_ARROW = register("copper_arrow",
            new Item.Settings().maxCount(16));
    public static final Item FLINT_ARROW = register("flint_arrow",
            new Item.Settings().maxCount(16));
    public static final Item GOLDEN_ARROW = register("golden_arrow",
            new Item.Settings().maxCount(16));
    public static final Item IRON_ARROW = register("iron_arrow",
            new Item.Settings().maxCount(16));
    public static final Item MITHRIL_ARROW = register("mithril_arrow",
            new Item.Settings().maxCount(16));
    public static final Item OBSIDIAN_ARROW = register("obsidian_arrow",
            new Item.Settings().maxCount(16));
    public static final Item SILVER_ARROW = register("silver_arrow",
            new Item.Settings().maxCount(16));
    public static final Item RUSTED_IRON_ARROW = register("rusted_iron_arrow",
            new Item.Settings().maxCount(16));

    public static final Item ADAMANTIUM_CHAINS = register("adamantium_chains",
            new Item.Settings().maxCount(16));
    public static final Item ANCIENT_METAL_CHAINS = register("ancient_metal_chains",
            new Item.Settings().maxCount(16));
    public static final Item RUSTED_IRON_CHAINS = register("rusted_iron_chains",
            new Item.Settings().maxCount(16));
    public static final Item COPPER_CHAINS = register("copper_chains",
            new Item.Settings().maxCount(16));
    public static final Item GOLDEN_CHAINS = register("golden_chains",
            new Item.Settings().maxCount(16));
    public static final Item IRON_CHAINS = register("iron_chains",
            new Item.Settings().maxCount(16));
    public static final Item MITHRIL_CHAINS = register("mithril_chains",
            new Item.Settings().maxCount(16));
    public static final Item SILVER_CHAINS = register("silver_chains",
            new Item.Settings().maxCount(16));
    public static final Item ADAMANTIUM_COINS = register("adamantium_coins",
            new Item.Settings().maxCount(32));
    public static final Item ANCIENT_METAL_COINS = register("ancient_metal_coins",
            new Item.Settings().maxCount(32));
    public static final Item COPPER_COINS = register("copper_coins",
            new Item.Settings().maxCount(32));
    public static final Item GOLDEN_COINS = register("golden_coins",
            new Item.Settings().maxCount(32));
    public static final Item IRON_COINS = register("iron_coins",
            new Item.Settings().maxCount(32));
    public static final Item MITHRIL_COINS = register("mithril_coins",
            new Item.Settings().maxCount(32));
    public static final Item SILVER_COINS = register("silver_coins",
            new Item.Settings().maxCount(32));

    public static final Item ADAMANTIUM_BUCKET = register("adamantium_bucket", settings -> new AtBucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_ADAMANTIUM_BUCKET = register(
            "water_adamantium_bucket", settings -> new AtBucketItem(Fluids.WATER, settings, ADAMANTIUM_BUCKET), new Item.Settings().recipeRemainder(ADAMANTIUM_BUCKET).maxCount(1)
    );
    public static final Item LAVA_ADAMANTIUM_BUCKET = register(
            "lava_adamantium_bucket", settings -> new AtBucketItem(Fluids.LAVA, settings, ADAMANTIUM_BUCKET), new Item.Settings().recipeRemainder(ADAMANTIUM_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_ADAMANTIUM_BUCKET = register(
            "powder_snow_adamantium_bucket",
            settings -> new AtPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_ADAMANTIUM_BUCKET = register(
            "milk_adamantium_bucket",
            new Item.Settings().recipeRemainder(ADAMANTIUM_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(ADAMANTIUM_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_ADAMANTIUM_BUCKET = register(
            "pufferfish_adamantium_bucket",
            settings -> new AtEntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_ADAMANTIUM_BUCKET = register(
            "salmon_adamantium_bucket",
            settings -> new AtEntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_ADAMANTIUM_BUCKET = register(
            "cod_adamantium_bucket",
            settings -> new AtEntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_ADAMANTIUM_BUCKET = register(
            "tropical_fish_adamantium_bucket",
            settings -> new AtEntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_ADAMANTIUM_BUCKET = register(
            "axolotl_adamantium_bucket",
            settings -> new AtEntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_ADAMANTIUM_BUCKET = register(
            "tadpole_adamantium_bucket",
            settings -> new AtEntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item ANCIENT_METAL_BUCKET = register("ancient_metal_bucket", settings -> new AtBucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_ANCIENT_METAL_BUCKET = register(
            "water_ancient_metal_bucket", settings -> new AtBucketItem(Fluids.WATER, settings, ANCIENT_METAL_BUCKET), new Item.Settings().recipeRemainder(ANCIENT_METAL_BUCKET).maxCount(1)
    );
    public static final Item LAVA_ANCIENT_METAL_BUCKET = register(
            "lava_ancient_metal_bucket", settings -> new AtBucketItem(Fluids.LAVA, settings, ANCIENT_METAL_BUCKET), new Item.Settings().recipeRemainder(ANCIENT_METAL_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_ANCIENT_METAL_BUCKET = register(
            "powder_snow_ancient_metal_bucket",
            settings -> new AtPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_ANCIENT_METAL_BUCKET = register(
            "milk_ancient_metal_bucket",
            new Item.Settings().recipeRemainder(ANCIENT_METAL_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(ANCIENT_METAL_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_ANCIENT_METAL_BUCKET = register(
            "pufferfish_ancient_metal_bucket",
            settings -> new AtEntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_ANCIENT_METAL_BUCKET = register(
            "salmon_ancient_metal_bucket",
            settings -> new AtEntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_ANCIENT_METAL_BUCKET = register(
            "cod_ancient_metal_bucket",
            settings -> new AtEntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_ANCIENT_METAL_BUCKET = register(
            "tropical_fish_ancient_metal_bucket",
            settings -> new AtEntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_ANCIENT_METAL_BUCKET = register(
            "axolotl_ancient_metal_bucket",
            settings -> new AtEntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_ANCIENT_METAL_BUCKET = register(
            "tadpole_ancient_metal_bucket",
            settings -> new AtEntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COPPER_BUCKET = register("copper_bucket", settings -> new AtBucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_COPPER_BUCKET = register(
            "water_copper_bucket", settings -> new AtBucketItem(Fluids.WATER, settings, COPPER_BUCKET), new Item.Settings().recipeRemainder(COPPER_BUCKET).maxCount(1)
    );
    public static final Item LAVA_COPPER_BUCKET = register(
            "lava_copper_bucket", settings -> new AtBucketItem(Fluids.LAVA, settings, COPPER_BUCKET), new Item.Settings().recipeRemainder(COPPER_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_COPPER_BUCKET = register(
            "powder_snow_copper_bucket",
            settings -> new AtPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_COPPER_BUCKET = register(
            "milk_copper_bucket",
            new Item.Settings().recipeRemainder(COPPER_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(COPPER_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_COPPER_BUCKET = register(
            "pufferfish_copper_bucket",
            settings -> new AtEntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_COPPER_BUCKET = register(
            "salmon_copper_bucket",
            settings -> new AtEntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_COPPER_BUCKET = register(
            "cod_copper_bucket",
            settings -> new AtEntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_COPPER_BUCKET = register(
            "tropical_fish_copper_bucket",
            settings -> new AtEntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_COPPER_BUCKET = register(
            "axolotl_copper_bucket",
            settings -> new AtEntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_COPPER_BUCKET = register(
            "tadpole_copper_bucket",
            settings -> new AtEntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item GOLD_BUCKET = register("gold_bucket", settings -> new AtBucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_GOLD_BUCKET = register(
            "water_gold_bucket", settings -> new AtBucketItem(Fluids.WATER, settings, GOLD_BUCKET), new Item.Settings().recipeRemainder(GOLD_BUCKET).maxCount(1)
    );
    public static final Item LAVA_GOLD_BUCKET = register(
            "lava_gold_bucket", settings -> new AtBucketItem(Fluids.LAVA, settings, GOLD_BUCKET), new Item.Settings().recipeRemainder(GOLD_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_GOLD_BUCKET = register(
            "powder_snow_gold_bucket",
            settings -> new AtPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_GOLD_BUCKET = register(
            "milk_gold_bucket",
            new Item.Settings().recipeRemainder(GOLD_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(GOLD_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_GOLD_BUCKET = register(
            "pufferfish_gold_bucket",
            settings -> new AtEntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_GOLD_BUCKET = register(
            "salmon_gold_bucket",
            settings -> new AtEntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_GOLD_BUCKET = register(
            "cod_gold_bucket",
            settings -> new AtEntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_GOLD_BUCKET = register(
            "tropical_fish_gold_bucket",
            settings -> new AtEntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_GOLD_BUCKET = register(
            "axolotl_gold_bucket",
            settings -> new AtEntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_GOLD_BUCKET = register(
            "tadpole_gold_bucket",
            settings -> new AtEntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item MITHRIL_BUCKET = register("mithril_bucket", settings -> new AtBucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_MITHRIL_BUCKET = register(
            "water_mithril_bucket", settings -> new AtBucketItem(Fluids.WATER, settings, MITHRIL_BUCKET), new Item.Settings().recipeRemainder(MITHRIL_BUCKET).maxCount(1)
    );
    public static final Item LAVA_MITHRIL_BUCKET = register(
            "lava_mithril_bucket", settings -> new AtBucketItem(Fluids.LAVA, settings, MITHRIL_BUCKET), new Item.Settings().recipeRemainder(MITHRIL_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_MITHRIL_BUCKET = register(
            "powder_snow_mithril_bucket",
            settings -> new AtPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_MITHRIL_BUCKET = register(
            "milk_mithril_bucket",
            new Item.Settings().recipeRemainder(MITHRIL_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(MITHRIL_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_MITHRIL_BUCKET = register(
            "pufferfish_mithril_bucket",
            settings -> new AtEntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_MITHRIL_BUCKET = register(
            "salmon_mithril_bucket",
            settings -> new AtEntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_MITHRIL_BUCKET = register(
            "cod_mithril_bucket",
            settings -> new AtEntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_MITHRIL_BUCKET = register(
            "tropical_fish_mithril_bucket",
            settings -> new AtEntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_MITHRIL_BUCKET = register(
            "axolotl_mithril_bucket",
            settings -> new AtEntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_MITHRIL_BUCKET = register(
            "tadpole_mithril_bucket",
            settings -> new AtEntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SILVER_BUCKET = register("silver_bucket", settings -> new AtBucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_SILVER_BUCKET = register(
            "water_silver_bucket", settings -> new AtBucketItem(Fluids.WATER, settings, SILVER_BUCKET), new Item.Settings().recipeRemainder(SILVER_BUCKET).maxCount(1)
    );
    public static final Item LAVA_SILVER_BUCKET = register(
            "lava_silver_bucket", settings -> new AtBucketItem(Fluids.LAVA, settings, SILVER_BUCKET), new Item.Settings().recipeRemainder(SILVER_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_SILVER_BUCKET = register(
            "powder_snow_silver_bucket",
            settings -> new AtPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_SILVER_BUCKET = register(
            "milk_silver_bucket",
            new Item.Settings().recipeRemainder(SILVER_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(SILVER_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_SILVER_BUCKET = register(
            "pufferfish_silver_bucket",
            settings -> new AtEntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_SILVER_BUCKET = register(
            "salmon_silver_bucket",
            settings -> new AtEntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_SILVER_BUCKET = register(
            "cod_silver_bucket",
            settings -> new AtEntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_SILVER_BUCKET = register(
            "tropical_fish_silver_bucket",
            settings -> new AtEntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_SILVER_BUCKET = register(
            "axolotl_silver_bucket",
            settings -> new AtEntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_SILVER_BUCKET = register(
            "tadpole_silver_bucket",
            settings -> new AtEntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item NETHERITE_BUCKET = register("netherite_bucket", settings -> new AtBucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_NETHERITE_BUCKET = register(
            "water_netherite_bucket", settings -> new AtBucketItem(Fluids.WATER, settings, NETHERITE_BUCKET), new Item.Settings().recipeRemainder(NETHERITE_BUCKET).maxCount(1)
    );
    public static final Item LAVA_NETHERITE_BUCKET = register(
            "lava_netherite_bucket", settings -> new AtBucketItem(Fluids.LAVA, settings, NETHERITE_BUCKET), new Item.Settings().recipeRemainder(NETHERITE_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_NETHERITE_BUCKET = register(
            "powder_snow_netherite_bucket",
            settings -> new AtPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_NETHERITE_BUCKET = register(
            "milk_netherite_bucket",
            new Item.Settings().recipeRemainder(NETHERITE_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(NETHERITE_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_NETHERITE_BUCKET = register(
            "pufferfish_netherite_bucket",
            settings -> new AtEntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_NETHERITE_BUCKET = register(
            "salmon_netherite_bucket",
            settings -> new AtEntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_NETHERITE_BUCKET = register(
            "cod_netherite_bucket",
            settings -> new AtEntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_NETHERITE_BUCKET = register(
            "tropical_fish_netherite_bucket",
            settings -> new AtEntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_NETHERITE_BUCKET = register(
            "axolotl_netherite_bucket",
            settings -> new AtEntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_NETHERITE_BUCKET = register(
            "tadpole_netherite_bucket",
            settings -> new AtEntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item MITHRIL_BOW = register("mithril_bow", BowItem::new, new Item.Settings().maxDamage(128).enchantable(1));
    public static final Item ANCIENT_METAL_BOW = register("ancient_metal_bow", BowItem::new, new Item.Settings().maxDamage(64).enchantable(1));
    public static final Item ADAMANTIUM_FISHING_ROD = register("adamantium_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(128).enchantable(1));
    public static final Item ANCIENT_METAL_FISHING_ROD = register("ancient_metal_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(96).enchantable(1));
    public static final Item COPPER_FISHING_ROD = register("copper_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(8).enchantable(1));
    public static final Item FLINT_FISHING_ROD = register("flint_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(32).enchantable(1));
    public static final Item GOLDEN_FISHING_ROD = register("golden_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(4).enchantable(1));
    public static final Item IRON_FISHING_ROD = register("iron_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(32).enchantable(1));
    public static final Item MITHRIL_FISHING_ROD = register("mithril_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(64).enchantable(1));
    public static final Item OBSIDIAN_FISHING_ROD = register("obsidian_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(32).enchantable(1));
    public static final Item SILVER_FISHING_ROD = register("silver_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(8).enchantable(1));

    public static final Item RAW_ADAMANTIUM = register("raw_adamantium",
            new Item.Settings().maxCount(16).component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item RAW_MITHRIL = register("raw_mithril",
            new Item.Settings().maxCount(16).component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item RAW_SILVER = register("raw_silver",
            new Item.Settings().maxCount(16).component(AtDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

    public static final Item ADAMANTIUM_INGOT = register("adamantium_ingot",
            new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 150).maxCount(16));
    public static final Item MITHRIL_INGOT = register("mithril_ingot",
            new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 90).maxCount(16));
    public static final Item ANCIENT_METAL_INGOT = register("ancient_metal_ingot",
            new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 60).maxCount(16));
    public static final Item SILVER_INGOT = register("silver_ingot",
            new Item.Settings().component(AtDataComponentTypes.CRAFTING_TIME, 10).maxCount(16));

    public static final Item ADAMANTIUM_NUGGET = register("adamantium_nugget",
            new Item.Settings().maxCount(32));
    public static final Item ANCIENT_METAL_NUGGET = register("ancient_metal_nugget",
            new Item.Settings().maxCount(32));
    public static final Item MITHRIL_NUGGET = register("mithril_nugget",
            new Item.Settings().maxCount(32));
    public static final Item SILVER_NUGGET = register("silver_nugget",
            new Item.Settings().maxCount(32));
    public static final Item COPPER_NUGGET = register("copper_nugget",
            new Item.Settings().maxCount(32));

    public static final Item DIAMOND_SHARD = register("diamond_shard",
            new Item.Settings().maxCount(16));
    public static final Item EMERALD_SHARD = register("emerald_shard",
            new Item.Settings().maxCount(16));
    public static final Item FLINT_SHARD = register("flint_shard",
            new Item.Settings().maxCount(16));
    public static final Item GLASS_SHARD = register("glass_shard",
            new Item.Settings().maxCount(16));
    public static final Item OBSIDIAN_SHARD = register("obsidian_shard",
            new Item.Settings().maxCount(16));
    public static final Item QUARTZ_SHARD = register("quartz_shard",
            new Item.Settings().maxCount(16));

    public static final Item SINEW = register("sinew",
            new Item.Settings().maxCount(16));
    public static final ItemGroup AT_MINT_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ADAMANTIUM_HELMET))
            .displayName(Text.translatable("itemGroup.mme.item_group"))
            .entries((context, entries) -> {
                entries.add(SINEW);
                entries.add(AtBlocks.CLAY_FURNACE);
                entries.add(AtBlocks.HARDENED_CLAY_FURNACE);
                entries.add(AtBlocks.NETHERRACK_FURNACE);
                entries.add(AtBlocks.OBSIDIAN_FURNACE);
                entries.add(AtBlocks.SANDSTONE_FURNACE);

                entries.add(AtBlocks.ADAMANTIUM_CRAFTING_TABLE);
                entries.add(AtBlocks.MITHRIL_CRAFTING_TABLE);
                entries.add(AtBlocks.ANCIENT_METAL_CRAFTING_TABLE);
                entries.add(AtBlocks.IRON_CRAFTING_TABLE);
                entries.add(AtBlocks.GOLD_CRAFTING_TABLE);
                entries.add(AtBlocks.SILVER_CRAFTING_TABLE);
                entries.add(AtBlocks.COPPER_CRAFTING_TABLE);
                entries.add(AtBlocks.FLINT_CRAFTING_TABLE);
                entries.add(AtBlocks.OBSIDIAN_CRAFTING_TABLE);

                entries.add(AtBlocks.MITHRIL_NUL_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_QUAS_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_POR_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_AN_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_NOX_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_FLAM_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_VAS_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_DES_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_ORT_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_TYM_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_CORP_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_LOR_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_MANI_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_JUX_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_YLEM_RUNESTORE);
                entries.add(AtBlocks.MITHRIL_SANCT_RUNESTORE);

                entries.add(AtBlocks.ADAMANTIUM_NUL_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_QUAS_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_POR_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_AN_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_NOX_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_FLAM_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_VAS_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_DES_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_ORT_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_TYM_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_CORP_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_LOR_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_MANI_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_JUX_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_YLEM_RUNESTORE);
                entries.add(AtBlocks.ADAMANTIUM_SANCT_RUNESTORE);

                entries.add(AtBlocks.ADAMANTIUM_ANVIL);
                entries.add(chipped(AtBlocks.CHIPPED_ADAMANTIUM_ANVIL));
                entries.add(damaged(AtBlocks.DAMAGED_ADAMANTIUM_ANVIL));
                entries.add(AtBlocks.MITHRIL_ANVIL);
                entries.add(chipped(AtBlocks.CHIPPED_MITHRIL_ANVIL));
                entries.add(damaged(AtBlocks.DAMAGED_MITHRIL_ANVIL));
                entries.add(AtBlocks.ANCIENT_METAL_ANVIL);
                entries.add(chipped(AtBlocks.CHIPPED_ANCIENT_METAL_ANVIL));
                entries.add(damaged(AtBlocks.DAMAGED_ANCIENT_METAL_ANVIL));
                entries.add(AtBlocks.GOLDEN_ANVIL);
                entries.add(chipped(AtBlocks.CHIPPED_GOLDEN_ANVIL));
                entries.add(damaged(AtBlocks.DAMAGED_GOLDEN_ANVIL));
                entries.add(AtBlocks.COPPER_ANVIL);
                entries.add(chipped(AtBlocks.CHIPPED_COPPER_ANVIL));
                entries.add(damaged(AtBlocks.DAMAGED_COPPER_ANVIL));
                entries.add(AtBlocks.SILVER_ANVIL);
                entries.add(chipped(AtBlocks.CHIPPED_SILVER_ANVIL));
                entries.add(damaged(AtBlocks.DAMAGED_SILVER_ANVIL));

                entries.add(Blocks.NETHERITE_BLOCK);
                entries.add(AtBlocks.ADAMANTIUM_BLOCK);
                entries.add(AtBlocks.MITHRIL_BLOCK);
                entries.add(AtBlocks.ANCIENT_METAL_BLOCK);
                entries.add(Blocks.IRON_BLOCK);
                entries.add(Blocks.GOLD_BLOCK);
                entries.add(AtBlocks.SILVER_BLOCK);
                entries.add(Blocks.COPPER_BLOCK);

                entries.add(AtBlocks.ADAMANTIUM_ORE);
                entries.add(AtBlocks.MITHRIL_ORE);
                entries.add(Blocks.IRON_ORE);
                entries.add(Blocks.GOLD_ORE);
                entries.add(AtBlocks.SILVER_ORE);
                entries.add(Blocks.COPPER_ORE);

                entries.add(AtBlocks.ADAMANTIUM_BARS);
                entries.add(AtBlocks.MITHRIL_BARS);
                entries.add(AtBlocks.ANCIENT_METAL_BARS);
                entries.add(Blocks.IRON_BARS);
                entries.add(AtBlocks.GOLDEN_BARS);
                entries.add(AtBlocks.SILVER_BARS);
                entries.add(AtBlocks.COPPER_BARS);
                entries.add(AtBlocks.MANTLE);

                entries.add(AtBlocks.ADAMANTIUM_DOOR);
                entries.add(AtBlocks.ANCIENT_METAL_DOOR);
                entries.add(AtBlocks.MITHRIL_DOOR);
                entries.add(AtBlocks.SILVER_DOOR);
                entries.add(AtBlocks.GOLDEN_DOOR);

                entries.add(DIAMOND_SHARD);
                entries.add(EMERALD_SHARD);
                entries.add(FLINT_SHARD);
                entries.add(GLASS_SHARD);
                entries.add(OBSIDIAN_SHARD);
                entries.add(QUARTZ_SHARD);

                entries.add(RAW_ADAMANTIUM);
                entries.add(RAW_MITHRIL);
                entries.add(RAW_SILVER);

                entries.add(Items.NETHERITE_INGOT);
                entries.add(ADAMANTIUM_INGOT);
                entries.add(MITHRIL_INGOT);
                entries.add(ANCIENT_METAL_INGOT);
                entries.add(Items.IRON_INGOT);
                entries.add(Items.GOLD_INGOT);
                entries.add(SILVER_INGOT);
                entries.add(Items.COPPER_INGOT);

                entries.add(ADAMANTIUM_NUGGET);
                entries.add(ANCIENT_METAL_NUGGET);
                entries.add(MITHRIL_NUGGET);
                entries.add(SILVER_NUGGET);
                entries.add(COPPER_NUGGET);

                entries.add(ADAMANTIUM_FISHING_ROD);
                entries.add(ANCIENT_METAL_FISHING_ROD);
                entries.add(COPPER_FISHING_ROD);
                entries.add(FLINT_FISHING_ROD);
                entries.add(GOLDEN_FISHING_ROD);
                entries.add(IRON_FISHING_ROD);
                entries.add(MITHRIL_FISHING_ROD);
                entries.add(OBSIDIAN_FISHING_ROD);
                entries.add(SILVER_FISHING_ROD);
                entries.add(ADAMANTIUM_HELMET);
                entries.add(ADAMANTIUM_CHESTPLATE);
                entries.add(ADAMANTIUM_LEGGINGS);
                entries.add(ADAMANTIUM_BOOTS);
                entries.add(ADAMANTIUM_CHAINMAIL_HELMET);
                entries.add(ADAMANTIUM_CHAINMAIL_CHESTPLATE);
                entries.add(ADAMANTIUM_CHAINMAIL_LEGGINGS);
                entries.add(ADAMANTIUM_CHAINMAIL_BOOTS);
                entries.add(ANCIENT_METAL_HELMET);
                entries.add(ANCIENT_METAL_CHESTPLATE);
                entries.add(ANCIENT_METAL_LEGGINGS);
                entries.add(ANCIENT_METAL_BOOTS);
                entries.add(ANCIENT_METAL_CHAINMAIL_HELMET);
                entries.add(ANCIENT_METAL_CHAINMAIL_CHESTPLATE);
                entries.add(ANCIENT_METAL_CHAINMAIL_LEGGINGS);
                entries.add(ANCIENT_METAL_CHAINMAIL_BOOTS);
                entries.add(COPPER_HELMET);
                entries.add(COPPER_CHESTPLATE);
                entries.add(COPPER_LEGGINGS);
                entries.add(COPPER_BOOTS);
                entries.add(COPPER_CHAINMAIL_HELMET);
                entries.add(COPPER_CHAINMAIL_CHESTPLATE);
                entries.add(COPPER_CHAINMAIL_LEGGINGS);
                entries.add(COPPER_CHAINMAIL_BOOTS);
                entries.add(MITHRIL_HELMET);
                entries.add(MITHRIL_CHESTPLATE);
                entries.add(MITHRIL_LEGGINGS);
                entries.add(MITHRIL_BOOTS);
                entries.add(MITHRIL_CHAINMAIL_HELMET);
                entries.add(MITHRIL_CHAINMAIL_CHESTPLATE);
                entries.add(MITHRIL_CHAINMAIL_LEGGINGS);
                entries.add(MITHRIL_CHAINMAIL_BOOTS);
                entries.add(RUSTED_IRON_HELMET);
                entries.add(RUSTED_IRON_CHESTPLATE);
                entries.add(RUSTED_IRON_LEGGINGS);
                entries.add(RUSTED_IRON_BOOTS);
                entries.add(RUSTED_IRON_CHAINMAIL_HELMET);
                entries.add(RUSTED_IRON_CHAINMAIL_CHESTPLATE);
                entries.add(RUSTED_IRON_CHAINMAIL_LEGGINGS);
                entries.add(RUSTED_IRON_CHAINMAIL_BOOTS);
                entries.add(SILVER_HELMET);
                entries.add(SILVER_CHESTPLATE);
                entries.add(SILVER_LEGGINGS);
                entries.add(SILVER_BOOTS);
                entries.add(SILVER_CHAINMAIL_HELMET);
                entries.add(SILVER_CHAINMAIL_CHESTPLATE);
                entries.add(SILVER_CHAINMAIL_LEGGINGS);
                entries.add(SILVER_CHAINMAIL_BOOTS);

                entries.add(ADAMANTIUM_AXE);
                entries.add(ADAMANTIUM_BATTLE_AXE);
                entries.add(ADAMANTIUM_DAGGER);
                entries.add(ADAMANTIUM_HATCHET);
                entries.add(ADAMANTIUM_HOE);
                entries.add(ADAMANTIUM_KNIFE);
                entries.add(ADAMANTIUM_MATTOCK);
                entries.add(ADAMANTIUM_PICKAXE);
                entries.add(ADAMANTIUM_SCYTHE);
                entries.add(ADAMANTIUM_SHEARS);
                entries.add(ADAMANTIUM_SHOVEL);
                entries.add(ADAMANTIUM_SWORD);
                entries.add(ADAMANTIUM_WAR_HAMMER);
                entries.add(ANCIENT_METAL_AXE);
                entries.add(ANCIENT_METAL_BATTLE_AXE);
                entries.add(ANCIENT_METAL_DAGGER);
                entries.add(ANCIENT_METAL_HATCHET);
                entries.add(ANCIENT_METAL_HOE);
                entries.add(ANCIENT_METAL_KNIFE);
                entries.add(ANCIENT_METAL_MATTOCK);
                entries.add(ANCIENT_METAL_PICKAXE);
                entries.add(ANCIENT_METAL_SCYTHE);
                entries.add(ANCIENT_METAL_SHEARS);
                entries.add(ANCIENT_METAL_SHOVEL);
                entries.add(ANCIENT_METAL_SWORD);
                entries.add(ANCIENT_METAL_WAR_HAMMER);
                entries.add(COPPER_AXE);
                entries.add(COPPER_BATTLE_AXE);
                entries.add(COPPER_DAGGER);
                entries.add(COPPER_HATCHET);
                entries.add(COPPER_HOE);
                entries.add(COPPER_KNIFE);
                entries.add(COPPER_MATTOCK);
                entries.add(COPPER_PICKAXE);
                entries.add(COPPER_SCYTHE);
                entries.add(COPPER_SHEARS);
                entries.add(COPPER_SHOVEL);
                entries.add(COPPER_SWORD);
                entries.add(COPPER_WAR_HAMMER);
                entries.add(FLINT_AXE);
                entries.add(FLINT_HATCHET);
                entries.add(FLINT_KNIFE);
                entries.add(FLINT_SHOVEL);
                entries.add(GOLDEN_BATTLE_AXE);
                entries.add(GOLDEN_DAGGER);
                entries.add(GOLDEN_HATCHET);
                entries.add(GOLDEN_KNIFE);
                entries.add(GOLDEN_MATTOCK);
                entries.add(GOLDEN_SCYTHE);
                entries.add(GOLDEN_SHEARS);
                entries.add(GOLDEN_WAR_HAMMER);
                entries.add(IRON_BATTLE_AXE);
                entries.add(IRON_DAGGER);
                entries.add(IRON_HATCHET);
                entries.add(IRON_KNIFE);
                entries.add(IRON_MATTOCK);
                entries.add(IRON_SCYTHE);
                entries.add(IRON_WAR_HAMMER);
                entries.add(MITHRIL_AXE);
                entries.add(MITHRIL_BATTLE_AXE);
                entries.add(MITHRIL_DAGGER);
                entries.add(MITHRIL_HATCHET);
                entries.add(MITHRIL_HOE);
                entries.add(MITHRIL_KNIFE);
                entries.add(MITHRIL_MATTOCK);
                entries.add(MITHRIL_PICKAXE);
                entries.add(MITHRIL_SCYTHE);
                entries.add(MITHRIL_SHEARS);
                entries.add(MITHRIL_SHOVEL);
                entries.add(MITHRIL_SWORD);
                entries.add(MITHRIL_WAR_HAMMER);
                entries.add(OBSIDIAN_AXE);
                entries.add(OBSIDIAN_HATCHET);
                entries.add(OBSIDIAN_KNIFE);
                entries.add(OBSIDIAN_SHOVEL);
                entries.add(RUSTED_IRON_AXE);
                entries.add(RUSTED_IRON_BATTLE_AXE);
                entries.add(RUSTED_IRON_DAGGER);
                entries.add(RUSTED_IRON_HATCHET);
                entries.add(RUSTED_IRON_HOE);
                entries.add(RUSTED_IRON_KNIFE);
                entries.add(RUSTED_IRON_MATTOCK);
                entries.add(RUSTED_IRON_PICKAXE);
                entries.add(RUSTED_IRON_SCYTHE);
                entries.add(RUSTED_IRON_SHEARS);
                entries.add(RUSTED_IRON_SHOVEL);
                entries.add(RUSTED_IRON_SWORD);
                entries.add(RUSTED_IRON_WAR_HAMMER);
                entries.add(SILVER_AXE);
                entries.add(SILVER_BATTLE_AXE);
                entries.add(SILVER_DAGGER);
                entries.add(SILVER_HATCHET);
                entries.add(SILVER_HOE);
                entries.add(SILVER_KNIFE);
                entries.add(SILVER_MATTOCK);
                entries.add(SILVER_PICKAXE);
                entries.add(SILVER_SCYTHE);
                entries.add(SILVER_SHEARS);
                entries.add(SILVER_SHOVEL);
                entries.add(SILVER_SWORD);
                entries.add(SILVER_WAR_HAMMER);
                entries.add(STONE_DAGGER);
                entries.add(WOOD_CLUB);
                entries.add(WOOD_CUDGEL);

                entries.add(BANANA);
                entries.add(BLUEBERRIE);
                entries.add(CHEESE);
                entries.add(CHOCOLATE);
                entries.add(FLOUR);
                entries.add(DOUGH);
                entries.add(LEMON);
                entries.add(ONION);
                entries.add(ORANGE);
                entries.add(WORM_COOKED);
                entries.add(WORM_RAW);
                entries.add(PUMPKIN_SOUP);
                entries.add(SORBET);
                entries.add(VEGETABLE_SOUP);
                entries.add(BEEF_STEW);
                entries.add(BOWL_MILK);
                entries.add(BOWL_SALAD);
                entries.add(BOWL_WATER);
                entries.add(CEREAL);
                entries.add(CHICKEN_SOUP);
                entries.add(CREAM_OF_MUSHROOM_SOUP);
                entries.add(CREAM_OF_VEGETABLE_SOUP);
                entries.add(ICE_CREAM);
                entries.add(MASHED_POTATO);
                entries.add(MUSHROOM_STEW);
                entries.add(PORRIDGE);

                entries.add(ADAMANTIUM_ARROW);
                entries.add(ANCIENT_METAL_ARROW);
                entries.add(COPPER_ARROW);
                entries.add(FLINT_ARROW);
                entries.add(GOLDEN_ARROW);
                entries.add(IRON_ARROW);
                entries.add(MITHRIL_ARROW);
                entries.add(OBSIDIAN_ARROW);
                entries.add(SILVER_ARROW);
                entries.add(RUSTED_IRON_ARROW);
                entries.add(ADAMANTIUM_CHAINS);
                entries.add(GOLDEN_CHAINS);
                entries.add(IRON_CHAINS);
                entries.add(MITHRIL_CHAINS);
                entries.add(SILVER_CHAINS);
                entries.add(ANCIENT_METAL_CHAINS);
                entries.add(COPPER_CHAINS);
                entries.add(RUSTED_IRON_CHAINS);
                entries.add(ADAMANTIUM_COINS);
                entries.add(ANCIENT_METAL_COINS);
                entries.add(COPPER_COINS);
                entries.add(GOLDEN_COINS);
                entries.add(IRON_COINS);
                entries.add(MITHRIL_COINS);
                entries.add(SILVER_COINS);

                entries.add(ADAMANTIUM_BUCKET);
                entries.add(WATER_ADAMANTIUM_BUCKET);
                entries.add(LAVA_ADAMANTIUM_BUCKET);
                entries.add(MILK_ADAMANTIUM_BUCKET);
                entries.add(POWDER_SNOW_ADAMANTIUM_BUCKET);
                entries.add(PUFFERFISH_ADAMANTIUM_BUCKET);
                entries.add(SALMON_ADAMANTIUM_BUCKET);
                entries.add(COD_ADAMANTIUM_BUCKET);
                entries.add(TROPICAL_FISH_ADAMANTIUM_BUCKET);
                entries.add(AXOLOTL_ADAMANTIUM_BUCKET);
                entries.add(TADPOLE_ADAMANTIUM_BUCKET);

                entries.add(ANCIENT_METAL_BUCKET);
                entries.add(WATER_ANCIENT_METAL_BUCKET);
                entries.add(LAVA_ANCIENT_METAL_BUCKET);
                entries.add(MILK_ANCIENT_METAL_BUCKET);
                entries.add(POWDER_SNOW_ANCIENT_METAL_BUCKET);
                entries.add(PUFFERFISH_ANCIENT_METAL_BUCKET);
                entries.add(SALMON_ANCIENT_METAL_BUCKET);
                entries.add(COD_ANCIENT_METAL_BUCKET);
                entries.add(TROPICAL_FISH_ANCIENT_METAL_BUCKET);
                entries.add(AXOLOTL_ANCIENT_METAL_BUCKET);
                entries.add(TADPOLE_ANCIENT_METAL_BUCKET);

                entries.add(COPPER_BUCKET);
                entries.add(WATER_COPPER_BUCKET);
                entries.add(LAVA_COPPER_BUCKET);
                entries.add(MILK_COPPER_BUCKET);
                entries.add(POWDER_SNOW_COPPER_BUCKET);
                entries.add(PUFFERFISH_COPPER_BUCKET);
                entries.add(SALMON_COPPER_BUCKET);
                entries.add(COD_COPPER_BUCKET);
                entries.add(TROPICAL_FISH_COPPER_BUCKET);
                entries.add(AXOLOTL_COPPER_BUCKET);
                entries.add(TADPOLE_COPPER_BUCKET);

                entries.add(GOLD_BUCKET);
                entries.add(WATER_GOLD_BUCKET);
                entries.add(LAVA_GOLD_BUCKET);
                entries.add(MILK_GOLD_BUCKET);
                entries.add(POWDER_SNOW_GOLD_BUCKET);
                entries.add(PUFFERFISH_GOLD_BUCKET);
                entries.add(SALMON_GOLD_BUCKET);
                entries.add(COD_GOLD_BUCKET);
                entries.add(TROPICAL_FISH_GOLD_BUCKET);
                entries.add(AXOLOTL_GOLD_BUCKET);
                entries.add(TADPOLE_GOLD_BUCKET);

                entries.add(MITHRIL_BUCKET);
                entries.add(WATER_MITHRIL_BUCKET);
                entries.add(LAVA_MITHRIL_BUCKET);
                entries.add(MILK_MITHRIL_BUCKET);
                entries.add(POWDER_SNOW_MITHRIL_BUCKET);
                entries.add(PUFFERFISH_MITHRIL_BUCKET);
                entries.add(SALMON_MITHRIL_BUCKET);
                entries.add(COD_MITHRIL_BUCKET);
                entries.add(TROPICAL_FISH_MITHRIL_BUCKET);
                entries.add(AXOLOTL_MITHRIL_BUCKET);
                entries.add(TADPOLE_MITHRIL_BUCKET);

                entries.add(SILVER_BUCKET);
                entries.add(WATER_SILVER_BUCKET);
                entries.add(LAVA_SILVER_BUCKET);
                entries.add(MILK_SILVER_BUCKET);
                entries.add(POWDER_SNOW_SILVER_BUCKET);
                entries.add(PUFFERFISH_SILVER_BUCKET);
                entries.add(SALMON_SILVER_BUCKET);
                entries.add(COD_SILVER_BUCKET);
                entries.add(TROPICAL_FISH_SILVER_BUCKET);
                entries.add(AXOLOTL_SILVER_BUCKET);
                entries.add(TADPOLE_SILVER_BUCKET);

                entries.add(NETHERITE_BUCKET);
                entries.add(WATER_NETHERITE_BUCKET);
                entries.add(LAVA_NETHERITE_BUCKET);
                entries.add(MILK_NETHERITE_BUCKET);
                entries.add(POWDER_SNOW_NETHERITE_BUCKET);
                entries.add(PUFFERFISH_NETHERITE_BUCKET);
                entries.add(SALMON_NETHERITE_BUCKET);
                entries.add(COD_NETHERITE_BUCKET);
                entries.add(TROPICAL_FISH_NETHERITE_BUCKET);
                entries.add(AXOLOTL_NETHERITE_BUCKET);
                entries.add(TADPOLE_NETHERITE_BUCKET);

                entries.add(MITHRIL_BOW);
                entries.add(ANCIENT_METAL_BOW);
            })
            .build();

    /**
     * 根据传入的铁砧方块生成“严重损坏”外观的展示物品（伤害值设置为最大值的 2/3）。
     */
    private static ItemStack damaged(Block anvil) {
        ItemStack stack = anvil.asItem().getDefaultStack();
        stack.setDamage((stack.getMaxDamage() / 3) * 2);
        return stack;
    }

    /**
     * 根据传入的铁砧方块生成“轻微损坏”外观的展示物品（伤害值设置为最大值的 1/3）。
     */
    private static ItemStack chipped(Block anvil) {
        ItemStack stack = anvil.asItem().getDefaultStack();
        stack.setDamage(stack.getMaxDamage() / 3);
        return stack;
    }

    public static Item.Settings getArmorSettings(AtArmorMaterial material, EquipmentType type) {
            return new Item.Settings().maxDamage(type.getMaxDamage(material.durability()))
                    .attributeModifiers(material.createAttributeModifiers(type))
                    .enchantable(material.enchantmentValue())
                    .component(
                            AtDataComponentTypes.ORIGINAL_ARMOR,
                            material.defense().getOrDefault(type, 0F)
                    )
                    .component(
                            DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(type.getEquipmentSlot()).equipSound(material.equipSound()).model(material.assetId()).build()
                    )
                    .repairable(material.repairIngredient());
    }

    public static Item.Settings getDaggerSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Settings(), material, 1, 1, 2, -2.0F, 0.25F, 0.25F);
    }
    public static Item.Settings getSwordSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Settings(), material, 2, 1, 3, -2.4F, 0.5F, 0.5F);
    }

    public static Item.Settings getScytheSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Settings(), material, 5, 1, 3, -3.0f , 0.75f, 0.75f);
    }

    public static Item.Settings getAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.AXE_MINEABLE, 3, 2, 4, -2.7F, 0.5F, 0.5F, 5);
    }
    public static Item.Settings getBattleAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.AXE_MINEABLE, 5, 1, 5, -3F, 0.5F, 0.75F, 5);
    }
    public static Item.Settings getHandAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.AXE_MINEABLE, 1, 2, 3, -2.7F, 0.25F, 0.25F, 5);
    }

    public static Item.Settings getHoeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.HOE_MINEABLE, 2, 2, 1, -1.0F, 0.5F, 0.5F, 0);
    }
    public static Item.Settings getMattockSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.HOE_MINEABLE, 4, 2, 2, -1.5F, 0.5F, 0.5F, 0);
    }

    public static Item.Settings getShovelSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.SHOVEL_MINEABLE, 1, 2, 1, -2.8F, 0.75F, 0.75F, 0);
    }

    public static Item.Settings getPickaxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.PICKAXE_MINEABLE, 3, 2, 2, -2.8f , 0.75f, 0.75f, 0);
    }
    public static Item.Settings getWarHammerSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Settings(), material, BlockTags.PICKAXE_MINEABLE, 5, 1, 3, -3.0f , 0.75f, 0.75f, 0);
    }

    public static Item.Settings getShearsSettings(ToolMaterial material) {
        return applyBaseSettings(new Item.Settings(), material, 2)
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())
                .attributeModifiers(CreateAttributeModifiers(0.5f, 0.5f, material.attackDamageBonus(), 0));
    }

    public static Item.Settings applyToolSettings(
            Item.Settings settings,
            ToolMaterial material,
            TagKey<Block> effectiveBlocks,
            int durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange,
            float disableBlockingForSeconds
    ) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return applyBaseSettings(settings, material, durabilityMultiplier)
                .component(
                        DataComponentTypes.TOOL,
                        new ToolComponent(
                                List.of(
                                        ToolComponent.Rule.ofNeverDropping(registryEntryLookup.getOrThrow(material.incorrectBlocksForDrops())),
                                        ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(effectiveBlocks), material.speed())
                                ),
                                1.0F,
                                1,
                                true
                        )
                )
                .attributeModifiers(CreateAttributeModifiers(blockInteractionRange, entityInteractionRange, attackDamage + material.attackDamageBonus(), attackSpeed))
                .component(DataComponentTypes.WEAPON, new WeaponComponent(itemDamagePerAttack, disableBlockingForSeconds));
    }

    public static Item.Settings applySwordSettings(
            Item.Settings settings,
            ToolMaterial material,
            int durabilityMultiplier,
            int itemDamagePerAttack,
            float attackDamage,
            float attackSpeed,
            float blockInteractionRange,
            float entityInteractionRange
    ) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return applyBaseSettings(settings, material, durabilityMultiplier)
                .component(
                        DataComponentTypes.TOOL,
                        new ToolComponent(
                                List.of(
                                        ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(Blocks.COBWEB.getRegistryEntry()), 15.0F),
                                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                                ),
                                1.0F,
                                2,
                                false
                        )
                )
                .attributeModifiers(CreateAttributeModifiers(blockInteractionRange, entityInteractionRange, attackDamage + material.attackDamageBonus(), attackSpeed))
                .component(DataComponentTypes.WEAPON, new WeaponComponent(itemDamagePerAttack));
    }

    public static AttributeModifiersComponent CreateAttributeModifiers(float blockInteractionRange,float entityInteractionRange, float attackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.BLOCK_INTERACTION_RANGE,
                        new EntityAttributeModifier(Mme.BASE_BLOCK_INTERACTION_RANGE, blockInteractionRange, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Mme.BASE_ENTITY_INTERACTION_RANGE, entityInteractionRange, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .build();
    }
    public static Item.Settings applyBaseSettings(Item.Settings settings, ToolMaterial material, int durabilityMultiplier) {
        return settings.maxDamage(material.durability() * durabilityMultiplier).repairable(material.repairItems()).enchantable(material.enchantmentValue());
    }
    
    /**
     * 使用默认 {@link Item} 工厂与默认 {@link Item.Settings} 注册一个简单物品。
     *
     * @param path 物品注册名（不含命名空间）
     * @return 注册完成的物品实例
     */
    private static Item register(String path) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mme.MOD_ID, path));
        return Items.register(registryKey, Item::new, new Item.Settings());
    }

    /**
     * 使用默认 {@link Item} 工厂并传入自定义 {@link Item.Settings} 注册物品。
     */
    private static Item register(String path, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mme.MOD_ID, path));
        return Items.register(registryKey, Item::new, settings);
    }

    /**
     * 使用自定义工厂与自定义 {@link Item.Settings} 注册物品。
     */
    private static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mme.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }
    private static Item registerAxeItem(String path, Item.Settings settings) {
        return register(path, AtAxeItem::new, settings);
    }
    private static Item registerShovelItem(String path, Item.Settings settings) {
        return register(path, AtShovelItem::new, settings);
    }
    private static Item registerHoeItem(String path, Item.Settings settings) {
        return register(path, AtHoeItem::new, settings);
    }
    /**
     * 为方块注册与之对应的方块物品，并可传入自定义 {@link Item.Settings}。
     */
    public static Item register(Block block, Item.Settings settings) {
        return Items.register(block, settings);
    }
    /**
     * 初始化入口：将模组物品组 {@link #AT_MINT_GROUP} 注册到游戏。
     */
    public static void init() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(Mme.MOD_ID, "item_group"), AT_MINT_GROUP);
    }
}
