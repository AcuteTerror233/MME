package com.acuteterror233.mite.item;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.component.MMEDataComponentTypes;
import com.acuteterror233.mite.item.armor.MMEArmorMaterials;
import com.acuteterror233.mite.item.equipment.MMEArmorMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
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
import java.util.function.Function;

public class MMEItems {
    public static final Item NETHERITE_CHAINMAIL_HELMET = register(
            "netherite_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.NETHERITE_MATERIAL, ArmorType.HELMET)
    );
    public static final Item NETHERITE_CHAINMAIL_CHESTPLATE = register(
            "netherite_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.NETHERITE_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item NETHERITE_CHAINMAIL_LEGGINGS = register(
            "netherite_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.NETHERITE_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item NETHERITE_CHAINMAIL_BOOTS = register(
            "netherite_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.NETHERITE_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ADAMANTIUM_HELMET = register(
            "adamantium_helmet",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ADAMANTIUM_CHESTPLATE = register(
            "adamantium_chestplate",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_LEGGINGS = register(
            "adamantium_leggings",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item ADAMANTIUM_BOOTS = register(
            "adamantium_boots",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_BOOTS = register(
            "adamantium_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_CHESTPLATE = register(
            "adamantium_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register(
            "adamantium_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_LEGGINGS = register(
            "adamantium_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.ADAMANTIUM_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_HELMET = register(
            "ancient_metal_helmet",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHESTPLATE = register(
            "ancient_metal_chestplate",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_LEGGINGS = register(
            "ancient_metal_leggings",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_BOOTS = register(
            "ancient_metal_boots",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_BOOTS = register(
            "ancient_metal_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_CHESTPLATE = register(
            "ancient_metal_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_HELMET = register(
            "ancient_metal_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_LEGGINGS = register(
            "ancient_metal_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.ANCIENT_METAL_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item COPPER_HELMET = register(
            "copper_helmet",
            getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.HELMET)
    );
    public static final Item COPPER_CHESTPLATE = register(
            "copper_chestplate",
            getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item COPPER_LEGGINGS = register(
            "copper_leggings",
            getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item COPPER_BOOTS = register(
            "copper_boots",
            getArmorSettings(MMEArmorMaterials.COPPER_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_BOOTS = register(
            "copper_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_CHESTPLATE = register(
            "copper_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item COPPER_CHAINMAIL_HELMET = register(
            "copper_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item COPPER_CHAINMAIL_LEGGINGS = register(
            "copper_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.COPPER_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item MITHRIL_HELMET = register(
            "mithril_helmet",
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item MITHRIL_CHESTPLATE = register(
            "mithril_chestplate",
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item MITHRIL_LEGGINGS = register(
            "mithril_leggings",
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item MITHRIL_BOOTS = register(
            "mithril_boots",
            getArmorSettings(MMEArmorMaterials.MITHRIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_HELMET = register(
            "mithril_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_CHESTPLATE = register(
            "mithril_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item MITHRIL_CHAINMAIL_LEGGINGS = register(
            "mithril_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item MITHRIL_CHAINMAIL_BOOTS = register(
            "mithril_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.MITHRIL_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item RUSTED_IRON_HELMET = register(
            "rusted_iron_helmet",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.HELMET)
    );
    public static final Item RUSTED_IRON_CHESTPLATE = register(
            "rusted_iron_chestplate",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_LEGGINGS = register(
            "rusted_iron_leggings",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_BOOTS = register(
            "rusted_iron_boots",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_HELMET = register(
            "rusted_iron_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_CHESTPLATE = register(
            "rusted_iron_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_LEGGINGS = register(
            "rusted_iron_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_BOOTS = register(
            "rusted_iron_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.RUSTED_IRON_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item SILVER_HELMET = register(
            "silver_helmet",
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.HELMET)
    );
    public static final Item SILVER_CHESTPLATE = register(
            "silver_chestplate",
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item SILVER_LEGGINGS = register(
            "silver_leggings",
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item SILVER_BOOTS = register(
            "silver_boots",
            getArmorSettings(MMEArmorMaterials.SILVER_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item SILVER_CHAINMAIL_HELMET = register(
            "silver_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item SILVER_CHAINMAIL_CHESTPLATE = register(
            "silver_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item SILVER_CHAINMAIL_LEGGINGS = register(
            "silver_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item SILVER_CHAINMAIL_BOOTS = register(
            "silver_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.SILVER_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );
    public static final Item GOLDEN_CHAINMAIL_HELMET = register(
            "golden_chainmail_helmet",
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.HELMET)
    );
    public static final Item GOLDEN_CHAINMAIL_CHESTPLATE = register(
            "golden_chainmail_chestplate",
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.CHESTPLATE)
    );
    public static final Item GOLDEN_CHAINMAIL_LEGGINGS = register(
            "golden_chainmail_leggings",
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.LEGGINGS)
    );
    public static final Item GOLDEN_CHAINMAIL_BOOTS = register(
            "golden_chainmail_boots",
            getArmorSettings(MMEArmorMaterials.GOLD_CHAINMAIL_MATERIAL, ArmorType.BOOTS)
    );

    public static final Item BANANA = register(
            "banana",
            new Item.Properties().food(new FoodProperties(2, 1.0F, false)).stacksTo(16));
    public static final Item BLUEBERRIE = register(
            "blueberry",
            new Item.Properties().food(new FoodProperties(1, 1.0F, false)).stacksTo(16));
    public static final Item CHEESE = register(
            "cheese",
            new Item.Properties().food(new FoodProperties(3, 3.0F, false)).stacksTo(16));
    public static final Item CHOCOLATE = register(
            "chocolate",
            new Item.Properties().food(new FoodProperties(3, 3.0F, false)).stacksTo(16));
    public static final Item DOUGH = register(
            "dough",
            new Item.Properties().food(new FoodProperties(1, 1.0F, false)).stacksTo(16));
    public static final Item LEMON = register(
            "lemon",
            new Item.Properties().food(new FoodProperties(1, 1.0F, false)).stacksTo(16));
    public static final Item ONION = register(
            "onion",
            new Item.Properties().food(new FoodProperties(1, 1.0F, false)).stacksTo(16));
    public static final Item ORANGE = register(
            "orange",
            new Item.Properties().food(new FoodProperties(2, 1.0F, false)).stacksTo(16));
    public static final Item WORM_COOKED = register(
            "worm_cooked",
            new Item.Properties().food(new FoodProperties(1, 1.0F, false)).stacksTo(16));
    public static final Item WORM_RAW = register(
            "worm_raw",
            new Item.Properties().food(new FoodProperties(1, 1.0F, false)).stacksTo(16));
    public static final Item FLOUR = register(
            "flour",
            new Item.Properties().stacksTo(16));
    public static final Item BEEF_STEW = register(
            "beef_stew",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item BOWL_MILK = register(
            "bowl_milk",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item BOWL_SALAD = register(
            "bowl_salad",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item BOWL_WATER = register(
            "bowl_water",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item CEREAL = register(
            "cereal",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item CHICKEN_SOUP = register(
            "chicken_soup",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item CREAM_OF_MUSHROOM_SOUP = register(
            "cream_of_mushroom_soup",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item CREAM_OF_VEGETABLE_SOUP = register(
            "cream_of_vegetable_soup",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item ICE_CREAM = register(
            "ice_cream",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item MASHED_POTATO = register(
            "mashed_potato",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item MUSHROOM_STEW = register(
            "mushroom_stew",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item PORRIDGE = register(
            "porridge",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item PUMPKIN_SOUP = register(
            "pumpkin_soup",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item SORBET = register(
            "sorbet",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    public static final Item VEGETABLE_SOUP = register(
            "vegetable_soup",
            new Item.Properties().food(new FoodProperties(5, 5.0F, false)).usingConvertsTo(Items.BOWL).craftRemainder(Items.BOWL).stacksTo(4));
    
    public static final Item NETHERITE_BATTLE_AXE = registerAxeItem("netherite_battle_axe", getBattleAxeSettings(MMEToolMaterials.NETHERITE));
    public static final Item NETHERITE_HATCHET = registerAxeItem("netherite_hatchet", getHandAxeSettings(MMEToolMaterials.NETHERITE));
    public static final Item NETHERITE_DAGGER = register("netherite_dagger", getDaggerSettings(MMEToolMaterials.NETHERITE));
    public static final Item NETHERITE_KNIFE = register("netherite_knife");
    public static final Item NETHERITE_WAR_HAMMER = register("netherite_war_hammer", getWarHammerSettings(MMEToolMaterials.NETHERITE));
    public static final Item NETHERITE_MATTOCK = registerHoeItem("netherite_mattock", getMattockSettings(MMEToolMaterials.NETHERITE));
    public static final Item NETHERITE_SCYTHE = register("netherite_scythe", getScytheSettings(MMEToolMaterials.NETHERITE));
    public static final Item NETHERITE_SHEARS = register("netherite_shears", getShearsSettings(MMEToolMaterials.NETHERITE));
    
    public static final Item ADAMANTIUM_AXE = registerAxeItem("adamantium_axe", getAxeSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_BATTLE_AXE = registerAxeItem("adamantium_battle_axe", getBattleAxeSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_HATCHET = registerAxeItem("adamantium_hatchet", getHandAxeSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SWORD = register("adamantium_sword", getSwordSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_DAGGER = register("adamantium_dagger", getDaggerSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_KNIFE = register("adamantium_knife");
    public static final Item ADAMANTIUM_PICKAXE = register("adamantium_pickaxe", getPickaxeSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_WAR_HAMMER = register("adamantium_war_hammer", getWarHammerSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SHOVEL = registerShovelItem("adamantium_shovel", getShovelSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_HOE = registerHoeItem("adamantium_hoe", getHoeSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_MATTOCK = registerHoeItem("adamantium_mattock", getMattockSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SCYTHE = register("adamantium_scythe", getScytheSettings(MMEToolMaterials.ADAMANTIUM));
    public static final Item ADAMANTIUM_SHEARS = register("adamantium_shears", getShearsSettings(MMEToolMaterials.ADAMANTIUM));

    public static final Item ANCIENT_METAL_AXE = registerAxeItem("ancient_metal_axe", getAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_BATTLE_AXE = registerAxeItem("ancient_metal_battle_axe", getBattleAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HATCHET = registerAxeItem("ancient_metal_hatchet", getHandAxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SWORD = register("ancient_metal_sword", getSwordSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_DAGGER = register("ancient_metal_dagger", getDaggerSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_KNIFE = register("ancient_metal_knife");
    public static final Item ANCIENT_METAL_PICKAXE = register("ancient_metal_pickaxe", getPickaxeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_WAR_HAMMER = register("ancient_metal_war_hammer", getWarHammerSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHOVEL = registerShovelItem("ancient_metal_shovel", getShovelSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_HOE = registerHoeItem("ancient_metal_hoe", getHoeSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_MATTOCK = registerHoeItem("ancient_metal_mattock", getMattockSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SCYTHE = registerAxeItem("ancient_metal_scythe", getScytheSettings(MMEToolMaterials.ANCIENT_METAL));
    public static final Item ANCIENT_METAL_SHEARS = registerAxeItem("ancient_metal_shears", getShearsSettings(MMEToolMaterials.ANCIENT_METAL));

    public static final Item COPPER_AXE = registerAxeItem("copper_axe", getAxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_BATTLE_AXE = registerAxeItem("copper_battle_axe", getBattleAxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_HATCHET = registerAxeItem("copper_hatchet", getHandAxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SWORD = register("copper_sword", getSwordSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_DAGGER = register("copper_dagger", getDaggerSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_KNIFE = register("copper_knife");
    public static final Item COPPER_PICKAXE = register("copper_pickaxe", getPickaxeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_WAR_HAMMER = register("copper_war_hammer", getWarHammerSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SHOVEL = register("copper_shovel", getShovelSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_HOE = register("copper_hoe", getHoeSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_MATTOCK = register("copper_mattock", getMattockSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SCYTHE = register("copper_scythe", getScytheSettings(MMEToolMaterials.COPPER));
    public static final Item COPPER_SHEARS = register("copper_shears", getShearsSettings(MMEToolMaterials.COPPER));

    public static final Item GOLDEN_BATTLE_AXE = registerAxeItem("golden_battle_axe", getBattleAxeSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_HATCHET = registerAxeItem("golden_hatchet", getHandAxeSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_DAGGER = register("golden_dagger", getDaggerSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_KNIFE = register("golden_knife");
    public static final Item GOLDEN_WAR_HAMMER = register("golden_war_hammer", getWarHammerSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_MATTOCK = register("golden_mattock", getMattockSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_SCYTHE = register("golden_scythe", getScytheSettings(MMEToolMaterials.GOLD));
    public static final Item GOLDEN_SHEARS = register("golden_shears", getShearsSettings(MMEToolMaterials.GOLD));

    public static final Item IRON_BATTLE_AXE = registerAxeItem("iron_battle_axe", getBattleAxeSettings(MMEToolMaterials.IRON));
    public static final Item IRON_DAGGER = register("iron_dagger", getDaggerSettings(MMEToolMaterials.IRON));
    public static final Item IRON_HATCHET = registerAxeItem("iron_hatchet", getHandAxeSettings(MMEToolMaterials.IRON));
    public static final Item IRON_WAR_HAMMER = register("iron_war_hammer", getWarHammerSettings(MMEToolMaterials.IRON));
    public static final Item IRON_KNIFE = register("iron_knife");
    public static final Item IRON_MATTOCK = register("iron_mattock", getMattockSettings(MMEToolMaterials.IRON));
    public static final Item IRON_SCYTHE = register("iron_scythe", getScytheSettings(MMEToolMaterials.IRON));

    public static final Item MITHRIL_AXE = registerAxeItem("mithril_axe", getAxeSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_BATTLE_AXE = registerAxeItem("mithril_battle_axe", getBattleAxeSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_HATCHET = registerAxeItem("mithril_hatchet", getHandAxeSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_SWORD = register("mithril_sword", getSwordSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_DAGGER = register("mithril_dagger", getDaggerSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_KNIFE = register("mithril_knife");
    public static final Item MITHRIL_PICKAXE = register("mithril_pickaxe", getPickaxeSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_WAR_HAMMER = register("mithril_war_hammer", getWarHammerSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_SHOVEL = register("mithril_shovel", getShovelSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_HOE = register("mithril_hoe", getHoeSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_MATTOCK = register("mithril_mattock", getMattockSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_SCYTHE = register("mithril_scythe", getScytheSettings(MMEToolMaterials.MITHRIL));
    public static final Item MITHRIL_SHEARS = register("mithril_shears", getShearsSettings(MMEToolMaterials.MITHRIL));

    public static final Item RUSTED_IRON_AXE = registerAxeItem("rusted_iron_axe", getAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_BATTLE_AXE = registerAxeItem("rusted_iron_battle_axe", getBattleAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HATCHET = registerAxeItem("rusted_iron_hatchet", getHandAxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SWORD = register("rusted_iron_sword", getSwordSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_DAGGER = register("rusted_iron_dagger", getDaggerSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_KNIFE = register("rusted_iron_knife");
    public static final Item RUSTED_IRON_PICKAXE = register("rusted_iron_pickaxe", getPickaxeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_WAR_HAMMER = register("rusted_iron_war_hammer", getWarHammerSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHOVEL = register("rusted_iron_shovel", getShovelSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_HOE = register("rusted_iron_hoe", getHoeSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_MATTOCK = register("rusted_iron_mattock", getMattockSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SCYTHE = register("rusted_iron_scythe", getScytheSettings(MMEToolMaterials.RUSTED_IRON));
    public static final Item RUSTED_IRON_SHEARS = register("rusted_iron_shears", getShearsSettings(MMEToolMaterials.RUSTED_IRON));

    public static final Item SILVER_AXE = registerAxeItem("silver_axe", getAxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_BATTLE_AXE = registerAxeItem("silver_battle_axe", getBattleAxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_HATCHET = registerAxeItem("silver_hatchet", getHandAxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_DAGGER = register("silver_dagger", getDaggerSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_KNIFE = register("silver_knife");
    public static final Item SILVER_SWORD = register("silver_sword", getSwordSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_PICKAXE = register("silver_pickaxe", getPickaxeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_WAR_HAMMER = register("silver_war_hammer", getWarHammerSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_SHOVEL = register("silver_shovel", getShovelSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_HOE = register("silver_hoe", getHoeSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_MATTOCK = register("silver_mattock", getMattockSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_SCYTHE = register("silver_scythe", getScytheSettings(MMEToolMaterials.SILVER));
    public static final Item SILVER_SHEARS = register("silver_shears", getShearsSettings(MMEToolMaterials.SILVER));

    public static final Item OBSIDIAN_AXE = registerAxeItem("obsidian_axe", getAxeSettings(MMEToolMaterials.OBSIDIAN));
    public static final Item OBSIDIAN_HATCHET = registerAxeItem("obsidian_hatchet", getHandAxeSettings(MMEToolMaterials.OBSIDIAN));
    public static final Item OBSIDIAN_KNIFE = register("obsidian_knife", getDaggerSettings(MMEToolMaterials.OBSIDIAN));
    public static final Item OBSIDIAN_SHOVEL = register("obsidian_shovel", getShovelSettings(MMEToolMaterials.OBSIDIAN));

    public static final Item FLINT_AXE = registerAxeItem("flint_axe", getAxeSettings(MMEToolMaterials.FLINT));
    public static final Item FLINT_HATCHET = registerAxeItem("flint_hatchet", getHandAxeSettings(MMEToolMaterials.FLINT));
    public static final Item FLINT_KNIFE = register("flint_knife", getDaggerSettings(MMEToolMaterials.FLINT));
    public static final Item FLINT_SHOVEL = register("flint_shovel", getShovelSettings(MMEToolMaterials.FLINT));

    public static final Item STONE_DAGGER = register("stone_dagger");
    public static final Item WOODEN_CLUB = register("wooden_club", getSwordSettings(MMEToolMaterials.WOOD));
    public static final Item WOODEN_CUDGEL = register("wooden_cudgel", getDaggerSettings(MMEToolMaterials.WOOD));

    public static final Item ADAMANTIUM_ARROW = register("adamantium_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item ANCIENT_METAL_ARROW = register("ancient_metal_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item COPPER_ARROW = register("copper_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item FLINT_ARROW = register("flint_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item GOLDEN_ARROW = register("golden_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item IRON_ARROW = register("iron_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item MITHRIL_ARROW = register("mithril_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item OBSIDIAN_ARROW = register("obsidian_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item SILVER_ARROW = register("silver_arrow",
            new Item.Properties().stacksTo(16));
    public static final Item RUSTED_IRON_ARROW = register("rusted_iron_arrow",
            new Item.Properties().stacksTo(16));

    public static final Item ADAMANTIUM_CHAINS = register("adamantium_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 75));
    public static final Item MITHRIL_CHAINS = register("mithril_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 45));
    public static final Item ANCIENT_METAL_CHAINS = register("ancient_metal_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 30));
    public static final Item IRON_CHAINS = register("iron_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 15));
    public static final Item RUSTED_IRON_CHAINS = register("rusted_iron_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 5));
    public static final Item GOLDEN_CHAINS = register("golden_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 5));
    public static final Item COPPER_CHAINS = register("copper_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 5));
    public static final Item SILVER_CHAINS = register("silver_chains",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.CRAFTING_TIME, 5));

    public static final Item ADAMANTIUM_COINS = register(
            "adamantium_coins",
            settings -> new CoinsItem(settings, 1000),
            new Item.Properties().stacksTo(32)
    );
    public static final Item MITHRIL_COINS = register(
            "mithril_coins",
            settings -> new CoinsItem(settings, 500),
            new Item.Properties().stacksTo(32)
    );
    public static final Item ANCIENT_METAL_COINS = register(
            "ancient_metal_coins",
            settings -> new CoinsItem(settings, 300),
            new Item.Properties().stacksTo(32)
    );
    public static final Item IRON_COINS = register(
            "iron_coins",
            settings -> new CoinsItem(settings, 200),
            new Item.Properties().stacksTo(32)
    );
    public static final Item GOLD_COINS = register(
            "gold_coins",
            settings -> new CoinsItem(settings, 500),
            new Item.Properties().stacksTo(32)
    );
    public static final Item COPPER_COINS = register(
            "copper_coins",
            settings -> new CoinsItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );
    public static final Item SILVER_COINS = register(
            "silver_coins",
            settings -> new CoinsItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );

    public static final Item ADAMANTIUM_BUCKET = register("adamantium_bucket", settings -> new MMEBucketItem(Fluids.EMPTY, settings, null), new Item.Properties().stacksTo(16));
    public static final Item WATER_ADAMANTIUM_BUCKET = register(
            "water_adamantium_bucket", settings -> new MMEBucketItem(Fluids.WATER, settings, ADAMANTIUM_BUCKET), new Item.Properties().craftRemainder(ADAMANTIUM_BUCKET).stacksTo(1)
    );
    public static final Item LAVA_ADAMANTIUM_BUCKET = register(
            "lava_adamantium_bucket", settings -> new MMEBucketItem(Fluids.LAVA, settings, ADAMANTIUM_BUCKET), new Item.Properties().craftRemainder(ADAMANTIUM_BUCKET).stacksTo(1)
    );
    public static final Item POWDER_SNOW_ADAMANTIUM_BUCKET = register(
            "powder_snow_adamantium_bucket",
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, ADAMANTIUM_BUCKET),
            new Item.Properties().stacksTo(1).useItemDescriptionPrefix()
    );
    public static final Item MILK_ADAMANTIUM_BUCKET = register(
            "milk_adamantium_bucket",
            new Item.Properties().craftRemainder(ADAMANTIUM_BUCKET).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(ADAMANTIUM_BUCKET).stacksTo(1)
    );
    public static final Item PUFFERFISH_ADAMANTIUM_BUCKET = register(
            "pufferfish_adamantium_bucket",
            settings -> new MMEMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SALMON_ADAMANTIUM_BUCKET = register(
            "salmon_adamantium_bucket",
            settings -> new MMEMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COD_ADAMANTIUM_BUCKET = register(
            "cod_adamantium_bucket",
            settings -> new MMEMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TROPICAL_FISH_ADAMANTIUM_BUCKET = register(
            "tropical_fish_adamantium_bucket",
            settings -> new MMEMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item AXOLOTL_ADAMANTIUM_BUCKET = register(
            "axolotl_adamantium_bucket",
            settings -> new MMEMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, ADAMANTIUM_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TADPOLE_ADAMANTIUM_BUCKET = register(
            "tadpole_adamantium_bucket",
            settings -> new MMEMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, ADAMANTIUM_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item ANCIENT_METAL_BUCKET = register("ancient_metal_bucket", settings -> new MMEBucketItem(Fluids.EMPTY, settings, null), new Item.Properties().stacksTo(16));
    public static final Item WATER_ANCIENT_METAL_BUCKET = register(
            "water_ancient_metal_bucket", settings -> new MMEBucketItem(Fluids.WATER, settings, ANCIENT_METAL_BUCKET), new Item.Properties().craftRemainder(ANCIENT_METAL_BUCKET).stacksTo(1)
    );
    public static final Item LAVA_ANCIENT_METAL_BUCKET = register(
            "lava_ancient_metal_bucket", settings -> new MMEBucketItem(Fluids.LAVA, settings, ANCIENT_METAL_BUCKET), new Item.Properties().craftRemainder(ANCIENT_METAL_BUCKET).stacksTo(1)
    );
    public static final Item POWDER_SNOW_ANCIENT_METAL_BUCKET = register(
            "powder_snow_ancient_metal_bucket",
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties().stacksTo(1).useItemDescriptionPrefix()
    );
    public static final Item MILK_ANCIENT_METAL_BUCKET = register(
            "milk_ancient_metal_bucket",
            new Item.Properties().craftRemainder(ANCIENT_METAL_BUCKET).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(ANCIENT_METAL_BUCKET).stacksTo(1)
    );
    public static final Item PUFFERFISH_ANCIENT_METAL_BUCKET = register(
            "pufferfish_ancient_metal_bucket",
            settings -> new MMEMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SALMON_ANCIENT_METAL_BUCKET = register(
            "salmon_ancient_metal_bucket",
            settings -> new MMEMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COD_ANCIENT_METAL_BUCKET = register(
            "cod_ancient_metal_bucket",
            settings -> new MMEMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TROPICAL_FISH_ANCIENT_METAL_BUCKET = register(
            "tropical_fish_ancient_metal_bucket",
            settings -> new MMEMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item AXOLOTL_ANCIENT_METAL_BUCKET = register(
            "axolotl_ancient_metal_bucket",
            settings -> new MMEMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TADPOLE_ANCIENT_METAL_BUCKET = register(
            "tadpole_ancient_metal_bucket",
            settings -> new MMEMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, ANCIENT_METAL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COPPER_BUCKET = register("copper_bucket", settings -> new MMEBucketItem(Fluids.EMPTY, settings, null), new Item.Properties().stacksTo(16));
    public static final Item WATER_COPPER_BUCKET = register(
            "water_copper_bucket", settings -> new MMEBucketItem(Fluids.WATER, settings, COPPER_BUCKET), new Item.Properties().craftRemainder(COPPER_BUCKET).stacksTo(1)
    );
    public static final Item LAVA_COPPER_BUCKET = register(
            "lava_copper_bucket", settings -> new MMEBucketItem(Fluids.LAVA, settings, COPPER_BUCKET), new Item.Properties().craftRemainder(COPPER_BUCKET).stacksTo(1)
    );
    public static final Item POWDER_SNOW_COPPER_BUCKET = register(
            "powder_snow_copper_bucket",
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, COPPER_BUCKET),
            new Item.Properties().stacksTo(1).useItemDescriptionPrefix()
    );
    public static final Item MILK_COPPER_BUCKET = register(
            "milk_copper_bucket",
            new Item.Properties().craftRemainder(COPPER_BUCKET).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(COPPER_BUCKET).stacksTo(1)
    );
    public static final Item PUFFERFISH_COPPER_BUCKET = register(
            "pufferfish_copper_bucket",
            settings -> new MMEMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SALMON_COPPER_BUCKET = register(
            "salmon_copper_bucket",
            settings -> new MMEMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COD_COPPER_BUCKET = register(
            "cod_copper_bucket",
            settings -> new MMEMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TROPICAL_FISH_COPPER_BUCKET = register(
            "tropical_fish_copper_bucket",
            settings -> new MMEMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item AXOLOTL_COPPER_BUCKET = register(
            "axolotl_copper_bucket",
            settings -> new MMEMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, COPPER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TADPOLE_COPPER_BUCKET = register(
            "tadpole_copper_bucket",
            settings -> new MMEMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, COPPER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item GOLD_BUCKET = register("gold_bucket", settings -> new MMEBucketItem(Fluids.EMPTY, settings, null), new Item.Properties().stacksTo(16));
    public static final Item WATER_GOLD_BUCKET = register(
            "water_gold_bucket", settings -> new MMEBucketItem(Fluids.WATER, settings, GOLD_BUCKET), new Item.Properties().craftRemainder(GOLD_BUCKET).stacksTo(1)
    );
    public static final Item LAVA_GOLD_BUCKET = register(
            "lava_gold_bucket", settings -> new MMEBucketItem(Fluids.LAVA, settings, GOLD_BUCKET), new Item.Properties().craftRemainder(GOLD_BUCKET).stacksTo(1)
    );
    public static final Item POWDER_SNOW_GOLD_BUCKET = register(
            "powder_snow_gold_bucket",
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, GOLD_BUCKET),
            new Item.Properties().stacksTo(1).useItemDescriptionPrefix()
    );
    public static final Item MILK_GOLD_BUCKET = register(
            "milk_gold_bucket",
            new Item.Properties().craftRemainder(GOLD_BUCKET).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(GOLD_BUCKET).stacksTo(1)
    );
    public static final Item PUFFERFISH_GOLD_BUCKET = register(
            "pufferfish_gold_bucket",
            settings -> new MMEMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SALMON_GOLD_BUCKET = register(
            "salmon_gold_bucket",
            settings -> new MMEMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COD_GOLD_BUCKET = register(
            "cod_gold_bucket",
            settings -> new MMEMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TROPICAL_FISH_GOLD_BUCKET = register(
            "tropical_fish_gold_bucket",
            settings -> new MMEMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item AXOLOTL_GOLD_BUCKET = register(
            "axolotl_gold_bucket",
            settings -> new MMEMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, GOLD_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TADPOLE_GOLD_BUCKET = register(
            "tadpole_gold_bucket",
            settings -> new MMEMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, GOLD_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item MITHRIL_BUCKET = register("mithril_bucket", settings -> new MMEBucketItem(Fluids.EMPTY, settings, null), new Item.Properties().stacksTo(16));
    public static final Item WATER_MITHRIL_BUCKET = register(
            "water_mithril_bucket", settings -> new MMEBucketItem(Fluids.WATER, settings, MITHRIL_BUCKET), new Item.Properties().craftRemainder(MITHRIL_BUCKET).stacksTo(1)
    );
    public static final Item LAVA_MITHRIL_BUCKET = register(
            "lava_mithril_bucket", settings -> new MMEBucketItem(Fluids.LAVA, settings, MITHRIL_BUCKET), new Item.Properties().craftRemainder(MITHRIL_BUCKET).stacksTo(1)
    );
    public static final Item POWDER_SNOW_MITHRIL_BUCKET = register(
            "powder_snow_mithril_bucket",
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, MITHRIL_BUCKET),
            new Item.Properties().stacksTo(1).useItemDescriptionPrefix()
    );
    public static final Item MILK_MITHRIL_BUCKET = register(
            "milk_mithril_bucket",
            new Item.Properties().craftRemainder(MITHRIL_BUCKET).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(MITHRIL_BUCKET).stacksTo(1)
    );
    public static final Item PUFFERFISH_MITHRIL_BUCKET = register(
            "pufferfish_mithril_bucket",
            settings -> new MMEMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SALMON_MITHRIL_BUCKET = register(
            "salmon_mithril_bucket",
            settings -> new MMEMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COD_MITHRIL_BUCKET = register(
            "cod_mithril_bucket",
            settings -> new MMEMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TROPICAL_FISH_MITHRIL_BUCKET = register(
            "tropical_fish_mithril_bucket",
            settings -> new MMEMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item AXOLOTL_MITHRIL_BUCKET = register(
            "axolotl_mithril_bucket",
            settings -> new MMEMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, MITHRIL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TADPOLE_MITHRIL_BUCKET = register(
            "tadpole_mithril_bucket",
            settings -> new MMEMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, MITHRIL_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SILVER_BUCKET = register("silver_bucket", settings -> new MMEBucketItem(Fluids.EMPTY, settings, null), new Item.Properties().stacksTo(16));
    public static final Item WATER_SILVER_BUCKET = register(
            "water_silver_bucket", settings -> new MMEBucketItem(Fluids.WATER, settings, SILVER_BUCKET), new Item.Properties().craftRemainder(SILVER_BUCKET).stacksTo(1)
    );
    public static final Item LAVA_SILVER_BUCKET = register(
            "lava_silver_bucket", settings -> new MMEBucketItem(Fluids.LAVA, settings, SILVER_BUCKET), new Item.Properties().craftRemainder(SILVER_BUCKET).stacksTo(1)
    );
    public static final Item POWDER_SNOW_SILVER_BUCKET = register(
            "powder_snow_silver_bucket",
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, SILVER_BUCKET),
            new Item.Properties().stacksTo(1).useItemDescriptionPrefix()
    );
    public static final Item MILK_SILVER_BUCKET = register(
            "milk_silver_bucket",
            new Item.Properties().craftRemainder(SILVER_BUCKET).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(SILVER_BUCKET).stacksTo(1)
    );
    public static final Item PUFFERFISH_SILVER_BUCKET = register(
            "pufferfish_silver_bucket",
            settings -> new MMEMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item SALMON_SILVER_BUCKET = register(
            "salmon_silver_bucket",
            settings -> new MMEMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item COD_SILVER_BUCKET = register(
            "cod_silver_bucket",
            settings -> new MMEMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TROPICAL_FISH_SILVER_BUCKET = register(
            "tropical_fish_silver_bucket",
            settings -> new MMEMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item AXOLOTL_SILVER_BUCKET = register(
            "axolotl_silver_bucket",
            settings -> new MMEMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, SILVER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item TADPOLE_SILVER_BUCKET = register(
            "tadpole_silver_bucket",
            settings -> new MMEMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, SILVER_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
    );
    public static final Item NETHERITE_BUCKET = register("netherite_bucket", settings -> new MMEBucketItem(Fluids.EMPTY, settings, null), new Item.Properties().stacksTo(16).fireResistant());
    public static final Item WATER_NETHERITE_BUCKET = register(
            "water_netherite_bucket", settings -> new MMEBucketItem(Fluids.WATER, settings, NETHERITE_BUCKET), new Item.Properties().craftRemainder(NETHERITE_BUCKET).stacksTo(1).fireResistant()
    );
    public static final Item LAVA_NETHERITE_BUCKET = register(
            "lava_netherite_bucket", settings -> new MMEBucketItem(Fluids.LAVA, settings, NETHERITE_BUCKET), new Item.Properties().craftRemainder(NETHERITE_BUCKET).stacksTo(1).fireResistant()
    );
    public static final Item POWDER_SNOW_NETHERITE_BUCKET = register(
            "powder_snow_netherite_bucket",
            settings -> new MMEPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, settings, NETHERITE_BUCKET),
            new Item.Properties().stacksTo(1).useItemDescriptionPrefix().fireResistant()
    );
    public static final Item MILK_NETHERITE_BUCKET = register(
            "milk_netherite_bucket",
            new Item.Properties().craftRemainder(NETHERITE_BUCKET).component(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET).usingConvertsTo(NETHERITE_BUCKET).stacksTo(1).fireResistant()
    );
    public static final Item PUFFERFISH_NETHERITE_BUCKET = register(
            "pufferfish_netherite_bucket",
            settings -> new MMEMobBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).fireResistant()
    );
    public static final Item SALMON_NETHERITE_BUCKET = register(
            "salmon_netherite_bucket",
            settings -> new MMEMobBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).fireResistant()
    );
    public static final Item COD_NETHERITE_BUCKET = register(
            "cod_netherite_bucket",
            settings -> new MMEMobBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).fireResistant()
    );
    public static final Item TROPICAL_FISH_NETHERITE_BUCKET = register(
            "tropical_fish_netherite_bucket",
            settings -> new MMEMobBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).fireResistant()
    );
    public static final Item AXOLOTL_NETHERITE_BUCKET = register(
            "axolotl_netherite_bucket",
            settings -> new MMEMobBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_AXOLOTL, settings, NETHERITE_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).fireResistant()
    );
    public static final Item TADPOLE_NETHERITE_BUCKET = register(
            "tadpole_netherite_bucket",
            settings -> new MMEMobBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_TADPOLE, settings, NETHERITE_BUCKET),
            new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).fireResistant()
    );
    public static final Item MITHRIL_BOW = register("mithril_bow", BowItem::new, new Item.Properties().durability(128).enchantable(1));
    public static final Item ANCIENT_METAL_BOW = register("ancient_metal_bow", BowItem::new, new Item.Properties().durability(64).enchantable(1));
    public static final Item ADAMANTIUM_FISHING_ROD = register("adamantium_fishing_rod", FishingRodItem::new, new Item.Properties().durability(128).enchantable(1));
    public static final Item ANCIENT_METAL_FISHING_ROD = register("ancient_metal_fishing_rod", FishingRodItem::new, new Item.Properties().durability(96).enchantable(1));
    public static final Item COPPER_FISHING_ROD = register("copper_fishing_rod", FishingRodItem::new, new Item.Properties().durability(8).enchantable(1));
    public static final Item FLINT_FISHING_ROD = register("flint_fishing_rod", FishingRodItem::new, new Item.Properties().durability(32).enchantable(1));
    public static final Item GOLDEN_FISHING_ROD = register("golden_fishing_rod", FishingRodItem::new, new Item.Properties().durability(4).enchantable(1));
    public static final Item IRON_FISHING_ROD = register("iron_fishing_rod", FishingRodItem::new, new Item.Properties().durability(32).enchantable(1));
    public static final Item MITHRIL_FISHING_ROD = register("mithril_fishing_rod", FishingRodItem::new, new Item.Properties().durability(64).enchantable(1));
    public static final Item OBSIDIAN_FISHING_ROD = register("obsidian_fishing_rod", FishingRodItem::new, new Item.Properties().durability(32).enchantable(1));
    public static final Item SILVER_FISHING_ROD = register("silver_fishing_rod", FishingRodItem::new, new Item.Properties().durability(8).enchantable(1));

    public static final Item RAW_ADAMANTIUM = register("raw_adamantium",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 4));
    public static final Item RAW_MITHRIL = register("raw_mithril",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 3));
    public static final Item RAW_SILVER = register("raw_silver",
            new Item.Properties().stacksTo(16).component(MMEDataComponentTypes.REQUIRED_COMBUSTION_GRADE, 2));

    public static final Item ADAMANTIUM_INGOT = register("adamantium_ingot",
            new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 150).stacksTo(16));
    public static final Item MITHRIL_INGOT = register("mithril_ingot",
            new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 90).stacksTo(16));
    public static final Item ANCIENT_METAL_INGOT = register("ancient_metal_ingot",
            new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 60).stacksTo(16));
    public static final Item SILVER_INGOT = register("silver_ingot",
            new Item.Properties().component(MMEDataComponentTypes.CRAFTING_TIME, 10).stacksTo(16));

    public static final Item ADAMANTIUM_NUGGET = register(
            "adamantium_nugget",
            settings -> new NuggetItem(settings, 1000),
            new Item.Properties().stacksTo(32)
    );
    public static final Item MITHRIL_NUGGET = register(
            "mithril_nugget",
            settings -> new NuggetItem(settings, 500),
            new Item.Properties().stacksTo(32)
    );
    public static final Item ANCIENT_METAL_NUGGET = register(
            "ancient_metal_nugget",
            settings -> new NuggetItem(settings, 300),
            new Item.Properties().stacksTo(32)
    );
    public static final Item SILVER_NUGGET = register(
            "silver_nugget",
            settings -> new NuggetItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );
    public static final Item COPPER_NUGGET = register(
            "copper_nugget",
            settings -> new NuggetItem(settings, 50),
            new Item.Properties().stacksTo(32)
    );

    public static final Item DIAMOND_SHARD = register("diamond_shard",
            new Item.Properties().stacksTo(16));
    public static final Item EMERALD_SHARD = register("emerald_shard",
            new Item.Properties().stacksTo(16));
    public static final Item FLINT_SHARD = register("flint_shard",
            new Item.Properties().stacksTo(16));
    public static final Item GLASS_SHARD = register("glass_shard",
            new Item.Properties().stacksTo(16));
    public static final Item OBSIDIAN_SHARD = register("obsidian_shard",
            new Item.Properties().stacksTo(16));
    public static final Item QUARTZ_SHARD = register("quartz_shard",
            new Item.Properties().stacksTo(16));

    public static final Item SINEW = register("sinew",
            new Item.Properties().stacksTo(16));

    public static final CreativeModeTab AT_MINT_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ADAMANTIUM_HELMET))
            .title(Component.translatable("itemGroup.mme.item_group"))
            .displayItems((context, entries) -> {
                entries.accept(AtBlocks.CLAY_FURNACE);
                entries.accept(AtBlocks.HARDENED_CLAY_FURNACE);
                entries.accept(AtBlocks.NETHERRACK_FURNACE);
                entries.accept(AtBlocks.OBSIDIAN_FURNACE);
                entries.accept(AtBlocks.SANDSTONE_FURNACE);

                entries.accept(AtBlocks.ADAMANTIUM_CRAFTING_TABLE);
                entries.accept(AtBlocks.MITHRIL_CRAFTING_TABLE);
                entries.accept(AtBlocks.ANCIENT_METAL_CRAFTING_TABLE);
                entries.accept(AtBlocks.IRON_CRAFTING_TABLE);
                entries.accept(AtBlocks.GOLD_CRAFTING_TABLE);
                entries.accept(AtBlocks.SILVER_CRAFTING_TABLE);
                entries.accept(AtBlocks.COPPER_CRAFTING_TABLE);
                entries.accept(AtBlocks.FLINT_CRAFTING_TABLE);
                entries.accept(AtBlocks.OBSIDIAN_CRAFTING_TABLE);

                entries.accept(AtBlocks.MITHRIL_NUL_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_QUAS_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_POR_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_AN_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_NOX_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_FLAM_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_VAS_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_DES_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_ORT_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_TYM_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_CORP_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_LOR_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_MANI_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_JUX_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_YLEM_RUNESTORE);
                entries.accept(AtBlocks.MITHRIL_SANCT_RUNESTORE);

                entries.accept(AtBlocks.ADAMANTIUM_NUL_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_QUAS_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_POR_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_AN_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_NOX_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_FLAM_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_VAS_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_DES_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_ORT_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_TYM_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_CORP_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_LOR_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_MANI_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_JUX_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_YLEM_RUNESTORE);
                entries.accept(AtBlocks.ADAMANTIUM_SANCT_RUNESTORE);

                entries.accept(AtBlocks.ADAMANTIUM_ANVIL);
                entries.accept(chipped(AtBlocks.CHIPPED_ADAMANTIUM_ANVIL));
                entries.accept(damaged(AtBlocks.DAMAGED_ADAMANTIUM_ANVIL));
                entries.accept(AtBlocks.MITHRIL_ANVIL);
                entries.accept(chipped(AtBlocks.CHIPPED_MITHRIL_ANVIL));
                entries.accept(damaged(AtBlocks.DAMAGED_MITHRIL_ANVIL));
                entries.accept(AtBlocks.ANCIENT_METAL_ANVIL);
                entries.accept(chipped(AtBlocks.CHIPPED_ANCIENT_METAL_ANVIL));
                entries.accept(damaged(AtBlocks.DAMAGED_ANCIENT_METAL_ANVIL));
                entries.accept(AtBlocks.GOLDEN_ANVIL);
                entries.accept(chipped(AtBlocks.CHIPPED_GOLDEN_ANVIL));
                entries.accept(damaged(AtBlocks.DAMAGED_GOLDEN_ANVIL));
                entries.accept(AtBlocks.COPPER_ANVIL);
                entries.accept(chipped(AtBlocks.CHIPPED_COPPER_ANVIL));
                entries.accept(damaged(AtBlocks.DAMAGED_COPPER_ANVIL));
                entries.accept(AtBlocks.SILVER_ANVIL);
                entries.accept(chipped(AtBlocks.CHIPPED_SILVER_ANVIL));
                entries.accept(damaged(AtBlocks.DAMAGED_SILVER_ANVIL));

                entries.accept(Blocks.NETHERITE_BLOCK);
                entries.accept(AtBlocks.ADAMANTIUM_BLOCK);
                entries.accept(AtBlocks.MITHRIL_BLOCK);
                entries.accept(AtBlocks.ANCIENT_METAL_BLOCK);
                entries.accept(Blocks.IRON_BLOCK);
                entries.accept(Blocks.GOLD_BLOCK);
                entries.accept(AtBlocks.SILVER_BLOCK);
                entries.accept(Blocks.COPPER_BLOCK);

                entries.accept(AtBlocks.ADAMANTIUM_ORE);
                entries.accept(AtBlocks.DEEPSLATE_ADAMANTIUM_ORE);
                entries.accept(AtBlocks.MITHRIL_ORE);
                entries.accept(AtBlocks.DEEPSLATE_MITHRIL_ORE);
                entries.accept(Blocks.IRON_ORE);
                entries.accept(Blocks.DEEPSLATE_IRON_ORE);
                entries.accept(Blocks.GOLD_ORE);
                entries.accept(Blocks.DEEPSLATE_GOLD_ORE);
                entries.accept(AtBlocks.SILVER_ORE);
                entries.accept(AtBlocks.DEEPSLATE_SILVER_ORE);
                entries.accept(Blocks.COPPER_ORE);
                entries.accept(Blocks.DEEPSLATE_COPPER_ORE);

                entries.accept(AtBlocks.ADAMANTIUM_BARS);
                entries.accept(AtBlocks.MITHRIL_BARS);
                entries.accept(AtBlocks.ANCIENT_METAL_BARS);
                entries.accept(Blocks.IRON_BARS);
                entries.accept(AtBlocks.GOLDEN_BARS);
                entries.accept(AtBlocks.SILVER_BARS);
                entries.accept(AtBlocks.COPPER_BARS);
                entries.accept(AtBlocks.MANTLE);

                entries.accept(AtBlocks.ADAMANTIUM_DOOR);
                entries.accept(AtBlocks.ANCIENT_METAL_DOOR);
                entries.accept(AtBlocks.MITHRIL_DOOR);
                entries.accept(AtBlocks.SILVER_DOOR);
                entries.accept(AtBlocks.GOLDEN_DOOR);

                entries.accept(DIAMOND_SHARD);
                entries.accept(EMERALD_SHARD);
                entries.accept(FLINT_SHARD);
                entries.accept(GLASS_SHARD);
                entries.accept(OBSIDIAN_SHARD);
                entries.accept(QUARTZ_SHARD);

                entries.accept(RAW_ADAMANTIUM);
                entries.accept(RAW_MITHRIL);
                entries.accept(RAW_SILVER);

                entries.accept(Items.NETHERITE_INGOT);
                entries.accept(ADAMANTIUM_INGOT);
                entries.accept(MITHRIL_INGOT);
                entries.accept(ANCIENT_METAL_INGOT);
                entries.accept(Items.IRON_INGOT);
                entries.accept(Items.GOLD_INGOT);
                entries.accept(SILVER_INGOT);
                entries.accept(Items.COPPER_INGOT);

                entries.accept(ADAMANTIUM_NUGGET);
                entries.accept(ANCIENT_METAL_NUGGET);
                entries.accept(MITHRIL_NUGGET);
                entries.accept(SILVER_NUGGET);
                entries.accept(COPPER_NUGGET);

                entries.accept(ADAMANTIUM_FISHING_ROD);
                entries.accept(ANCIENT_METAL_FISHING_ROD);
                entries.accept(COPPER_FISHING_ROD);
                entries.accept(FLINT_FISHING_ROD);
                entries.accept(GOLDEN_FISHING_ROD);
                entries.accept(IRON_FISHING_ROD);
                entries.accept(MITHRIL_FISHING_ROD);
                entries.accept(OBSIDIAN_FISHING_ROD);
                entries.accept(SILVER_FISHING_ROD);
                entries.accept(NETHERITE_CHAINMAIL_HELMET);
                entries.accept(NETHERITE_CHAINMAIL_CHESTPLATE);
                entries.accept(NETHERITE_CHAINMAIL_LEGGINGS);
                entries.accept(NETHERITE_CHAINMAIL_BOOTS);
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
                entries.accept(COPPER_HELMET);
                entries.accept(COPPER_CHESTPLATE);
                entries.accept(COPPER_LEGGINGS);
                entries.accept(COPPER_BOOTS);
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
                entries.accept(RUSTED_IRON_SWORD);
                entries.accept(RUSTED_IRON_WAR_HAMMER);
                entries.accept(COPPER_AXE);
                entries.accept(COPPER_BATTLE_AXE);
                entries.accept(COPPER_DAGGER);
                entries.accept(COPPER_HATCHET);
                entries.accept(COPPER_HOE);
                entries.accept(COPPER_KNIFE);
                entries.accept(COPPER_MATTOCK);
                entries.accept(COPPER_PICKAXE);
                entries.accept(COPPER_SCYTHE);
                entries.accept(COPPER_SHEARS);
                entries.accept(COPPER_SHOVEL);
                entries.accept(COPPER_SWORD);
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
                entries.accept(SILVER_SWORD);
                entries.accept(SILVER_WAR_HAMMER);
                entries.accept(STONE_DAGGER);
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
                entries.accept(WOODEN_CLUB);
                entries.accept(WOODEN_CUDGEL);

                entries.accept(BANANA);
                entries.accept(BLUEBERRIE);
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
                entries.accept(MUSHROOM_STEW);
                entries.accept(PORRIDGE);
                entries.accept(SINEW);

                entries.accept(ADAMANTIUM_ARROW);
                entries.accept(ANCIENT_METAL_ARROW);
                entries.accept(COPPER_ARROW);
                entries.accept(FLINT_ARROW);
                entries.accept(GOLDEN_ARROW);
                entries.accept(IRON_ARROW);
                entries.accept(MITHRIL_ARROW);
                entries.accept(OBSIDIAN_ARROW);
                entries.accept(SILVER_ARROW);
                entries.accept(RUSTED_IRON_ARROW);
                entries.accept(ADAMANTIUM_CHAINS);
                entries.accept(GOLDEN_CHAINS);
                entries.accept(IRON_CHAINS);
                entries.accept(MITHRIL_CHAINS);
                entries.accept(SILVER_CHAINS);
                entries.accept(ANCIENT_METAL_CHAINS);
                entries.accept(COPPER_CHAINS);
                entries.accept(RUSTED_IRON_CHAINS);
                entries.accept(ADAMANTIUM_COINS);
                entries.accept(ANCIENT_METAL_COINS);
                entries.accept(COPPER_COINS);
                entries.accept(GOLD_COINS);
                entries.accept(IRON_COINS);
                entries.accept(MITHRIL_COINS);
                entries.accept(SILVER_COINS);

                entries.accept(NETHERITE_BUCKET);
                entries.accept(WATER_NETHERITE_BUCKET);
                entries.accept(LAVA_NETHERITE_BUCKET);
                entries.accept(MILK_NETHERITE_BUCKET);
                entries.accept(POWDER_SNOW_NETHERITE_BUCKET);
                entries.accept(PUFFERFISH_NETHERITE_BUCKET);
                entries.accept(SALMON_NETHERITE_BUCKET);
                entries.accept(COD_NETHERITE_BUCKET);
                entries.accept(TROPICAL_FISH_NETHERITE_BUCKET);
                entries.accept(AXOLOTL_NETHERITE_BUCKET);
                entries.accept(TADPOLE_NETHERITE_BUCKET);

                entries.accept(ADAMANTIUM_BUCKET);
                entries.accept(WATER_ADAMANTIUM_BUCKET);
                entries.accept(LAVA_ADAMANTIUM_BUCKET);
                entries.accept(MILK_ADAMANTIUM_BUCKET);
                entries.accept(POWDER_SNOW_ADAMANTIUM_BUCKET);
                entries.accept(PUFFERFISH_ADAMANTIUM_BUCKET);
                entries.accept(SALMON_ADAMANTIUM_BUCKET);
                entries.accept(COD_ADAMANTIUM_BUCKET);
                entries.accept(TROPICAL_FISH_ADAMANTIUM_BUCKET);
                entries.accept(AXOLOTL_ADAMANTIUM_BUCKET);
                entries.accept(TADPOLE_ADAMANTIUM_BUCKET);

                entries.accept(MITHRIL_BUCKET);
                entries.accept(WATER_MITHRIL_BUCKET);
                entries.accept(LAVA_MITHRIL_BUCKET);
                entries.accept(MILK_MITHRIL_BUCKET);
                entries.accept(POWDER_SNOW_MITHRIL_BUCKET);
                entries.accept(PUFFERFISH_MITHRIL_BUCKET);
                entries.accept(SALMON_MITHRIL_BUCKET);
                entries.accept(COD_MITHRIL_BUCKET);
                entries.accept(TROPICAL_FISH_MITHRIL_BUCKET);
                entries.accept(AXOLOTL_MITHRIL_BUCKET);
                entries.accept(TADPOLE_MITHRIL_BUCKET);

                entries.accept(ANCIENT_METAL_BUCKET);
                entries.accept(WATER_ANCIENT_METAL_BUCKET);
                entries.accept(LAVA_ANCIENT_METAL_BUCKET);
                entries.accept(MILK_ANCIENT_METAL_BUCKET);
                entries.accept(POWDER_SNOW_ANCIENT_METAL_BUCKET);
                entries.accept(PUFFERFISH_ANCIENT_METAL_BUCKET);
                entries.accept(SALMON_ANCIENT_METAL_BUCKET);
                entries.accept(COD_ANCIENT_METAL_BUCKET);
                entries.accept(TROPICAL_FISH_ANCIENT_METAL_BUCKET);
                entries.accept(AXOLOTL_ANCIENT_METAL_BUCKET);
                entries.accept(TADPOLE_ANCIENT_METAL_BUCKET);

                entries.accept(COPPER_BUCKET);
                entries.accept(WATER_COPPER_BUCKET);
                entries.accept(LAVA_COPPER_BUCKET);
                entries.accept(MILK_COPPER_BUCKET);
                entries.accept(POWDER_SNOW_COPPER_BUCKET);
                entries.accept(PUFFERFISH_COPPER_BUCKET);
                entries.accept(SALMON_COPPER_BUCKET);
                entries.accept(COD_COPPER_BUCKET);
                entries.accept(TROPICAL_FISH_COPPER_BUCKET);
                entries.accept(AXOLOTL_COPPER_BUCKET);
                entries.accept(TADPOLE_COPPER_BUCKET);

                entries.accept(SILVER_BUCKET);
                entries.accept(WATER_SILVER_BUCKET);
                entries.accept(LAVA_SILVER_BUCKET);
                entries.accept(MILK_SILVER_BUCKET);
                entries.accept(POWDER_SNOW_SILVER_BUCKET);
                entries.accept(PUFFERFISH_SILVER_BUCKET);
                entries.accept(SALMON_SILVER_BUCKET);
                entries.accept(COD_SILVER_BUCKET);
                entries.accept(TROPICAL_FISH_SILVER_BUCKET);
                entries.accept(AXOLOTL_SILVER_BUCKET);
                entries.accept(TADPOLE_SILVER_BUCKET);

                entries.accept(GOLD_BUCKET);
                entries.accept(WATER_GOLD_BUCKET);
                entries.accept(LAVA_GOLD_BUCKET);
                entries.accept(MILK_GOLD_BUCKET);
                entries.accept(POWDER_SNOW_GOLD_BUCKET);
                entries.accept(PUFFERFISH_GOLD_BUCKET);
                entries.accept(SALMON_GOLD_BUCKET);
                entries.accept(COD_GOLD_BUCKET);
                entries.accept(TROPICAL_FISH_GOLD_BUCKET);
                entries.accept(AXOLOTL_GOLD_BUCKET);
                entries.accept(TADPOLE_GOLD_BUCKET);

                entries.accept(MITHRIL_BOW);
                entries.accept(ANCIENT_METAL_BOW);
            })
            .build();

    /**
     * 根据传入的铁砧方块生成“严重损坏”外观的展示物品（伤害值设置为最大值的 2/3）。
     */
    private static ItemStack damaged(Block anvil) {
        ItemStack stack = anvil.asItem().getDefaultInstance();
        stack.setDamageValue((stack.getMaxDamage() / 3) * 2);
        return stack;
    }

    /**
     * 根据传入的铁砧方块生成“轻微损坏”外观的展示物品（伤害值设置为最大值的 1/3）。
     */
    private static ItemStack chipped(Block anvil) {
        ItemStack stack = anvil.asItem().getDefaultInstance();
        stack.setDamageValue(stack.getMaxDamage() / 3);
        return stack;
    }

    public static Item.Properties getArmorSettings(MMEArmorMaterial material, ArmorType type) {
            return new Item.Properties().durability(type.getDurability(material.durability()))
                    .attributes(material.createAttributeModifiers(type))
                    .enchantable(material.enchantmentValue())
                    .component(
                            MMEDataComponentTypes.ORIGINAL_ARMOR,
                            material.defense().getOrDefault(type, 0F)
                    )
                    .component(
                            DataComponents.EQUIPPABLE,
                            Equippable.builder(type.getSlot()).setEquipSound(material.equipSound()).setAsset(material.assetId()).build()
                    )
                    .repairable(material.repairIngredient());
    }

    public static Item.Properties getDaggerSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Properties(), material, 1, 1, 2, -2.0F, 0.25F, 0.25F);
    }
    public static Item.Properties getSwordSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Properties(), material, 2, 1, 3, -2.4F, 0.5F, 0.5F);
    }

    public static Item.Properties getScytheSettings(ToolMaterial material) {
        return applySwordSettings(new Item.Properties(), material, 5, 1, 3, -3.0f , 0.75f, 0.75f);
    }

    public static Item.Properties getAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 3, 2, 4, -2.7F, 0.5F, 0.5F, 5);
    }
    public static Item.Properties getBattleAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 5, 1, 5, -3F, 0.5F, 0.75F, 5);
    }
    public static Item.Properties getHandAxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_AXE, 1, 2, 3, -2.7F, 0.25F, 0.25F, 5);
    }

    public static Item.Properties getHoeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_HOE, 2, 2, 1, -1.0F, 0.5F, 0.5F, 0);
    }
    public static Item.Properties getMattockSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_HOE, 4, 2, 2, -1.5F, 0.5F, 0.5F, 0);
    }

    public static Item.Properties getShovelSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_SHOVEL, 1, 2, 1, -2.8F, 0.75F, 0.75F, 0);
    }

    public static Item.Properties getPickaxeSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_PICKAXE, 3, 2, 2, -2.8f , 0.75f, 0.75f, 0);
    }
    public static Item.Properties getWarHammerSettings(ToolMaterial material) {
        return applyToolSettings(new Item.Properties(), material, BlockTags.MINEABLE_WITH_PICKAXE, 5, 1, 3, -3.0f , 0.75f, 0.75f, 0);
    }

    public static Item.Properties getShearsSettings(ToolMaterial material) {
        return applyBaseSettings(new Item.Properties(), material, 2)
                .component(DataComponents.TOOL, ShearsItem.createToolProperties())
                .attributes(CreateAttributeModifiers(0.5f, 0.5f, material.attackDamageBonus(), 0));
    }

    public static Item.Properties applyToolSettings(
            Item.Properties settings,
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
    public static Item.Properties applyBaseSettings(Item.Properties settings, ToolMaterial material, int durabilityMultiplier) {
        Item.Properties settings1 = settings.durability(material.durability() * durabilityMultiplier).repairable(material.repairItems()).enchantable(material.enchantmentValue());
        if (material == ToolMaterial.NETHERITE){
            return settings1.fireResistant();
        }
        return settings1;
    }

    private static Item register(String path) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, path));
        return Items.registerItem(registryKey, Item::new, new Item.Properties());
    }

    private static Item register(String path, Item.Properties settings) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, path));
        return Items.registerItem(registryKey, Item::new, settings);
    }

    private static Item register(String path, Function<Item.Properties, Item> factory, Item.Properties settings) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, path));
        return Items.registerItem(registryKey, factory, settings);
    }

    private static Item registerAxeItem(String path, Item.Properties settings) {
        return register(path, MMEAxeItem::new, settings);
    }

    private static Item registerShovelItem(String path, Item.Properties settings) {
        return register(path, MMEShovelItem::new, settings);
    }

    private static Item registerHoeItem(String path, Item.Properties settings) {
        return register(path, MMEHoeItem::new, settings);
    }

    public static Item register(Block block, Item.Properties settings) {
        return Items.registerBlock(block, settings);
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "item_group"), AT_MINT_GROUP);
    }
}
