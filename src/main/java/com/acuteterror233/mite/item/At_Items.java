package com.acuteterror233.mite.item;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.atinterface.ItemSettingsExtension;
import com.acuteterror233.mite.block.At_Blocks;
import com.acuteterror233.mite.item.armor.At_ArmorMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class At_Items {
    public static final Item ADAMANTIUM_HELMET = register(
        "adamantium_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHESTPLATE = register(
        "adamantium_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_LEGGINGS = register(
        "adamantium_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ADAMANTIUM_BOOTS = register(
        "adamantium_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_BOOTS = register(
        "adamantium_chainmail_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_CHESTPLATE = register(
        "adamantium_chainmail_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register(
        "adamantium_chainmail_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_LEGGINGS = register(
        "adamantium_chainmail_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_HELMET = register(
        "ancient_metal_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHESTPLATE = register(
        "ancient_metal_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_LEGGINGS = register(
        "ancient_metal_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_BOOTS = register(
        "ancient_metal_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_BOOTS = register(
        "ancient_metal_chainmail_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_CHESTPLATE = register(
        "ancient_metal_chainmail_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_HELMET = register(
        "ancient_metal_chainmail_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_LEGGINGS = register(
        "ancient_metal_chainmail_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_HELMET =  register(
        "copper_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item COPPER_CHESTPLATE = register(
        "copper_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_LEGGINGS = register(
        "copper_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_BOOTS = register(
        "copper_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_BOOTS = register(
        "copper_chainmail_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_CHESTPLATE = register(
        "copper_chainmail_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_CHAINMAIL_HELMET = register(
        "copper_chainmail_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item COPPER_CHAINMAIL_LEGGINGS = register(
        "copper_chainmail_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_HELMET = register(
        "mithril_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item MITHRIL_CHESTPLATE = register(
        "mithril_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item MITHRIL_LEGGINGS = register(
        "mithril_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_BOOTS = register(
        "mithril_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_HELMET  = register(
        "mithril_chainmail_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_CHESTPLATE  = register(
        "mithril_chainmail_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item MITHRIL_CHAINMAIL_LEGGINGS  = register(
        "mithril_chainmail_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_CHAINMAIL_BOOTS  = register(
        "mithril_chainmail_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item RUSTED_IRON_HELMET = register(
        "rusted_iron_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item RUSTED_IRON_CHESTPLATE = register(
        "rusted_iron_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_LEGGINGS = register(
        "rusted_iron_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_BOOTS = register(
        "rusted_iron_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_HELMET = register(
        "rusted_iron_chainmail_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_CHESTPLATE = register(
        "rusted_iron_chainmail_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_LEGGINGS = register(
        "rusted_iron_chainmail_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_BOOTS = register(
        "rusted_iron_chainmail_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item SILVER_HELMET = register(
        "silver_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
     public static final Item SILVER_CHESTPLATE = register(
        "silver_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item SILVER_LEGGINGS = register(
        "silver_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item SILVER_BOOTS = register(
        "silver_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item SILVER_CHAINMAIL_HELMET = register(
        "silver_chainmail_helmet",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item SILVER_CHAINMAIL_CHESTPLATE = register(
        "silver_chainmail_chestplate",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item SILVER_CHAINMAIL_LEGGINGS = register(
        "silver_chainmail_leggings",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item SILVER_CHAINMAIL_BOOTS = register(
        "silver_chainmail_boots",
        ((ItemSettingsExtension)new Item.Settings()).At_armor(At_ArmorMaterials.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );

    public static final Item BANANA = register(
            "banana",
            new Item.Settings().food(new FoodComponent(2, 1.0F, false)));
    public static final Item BLUEBERRIE = register(
            "blueberry",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item CHEESE = register(
            "cheese",
            new Item.Settings().food(new FoodComponent(3, 3.0F, false)));
    public static final Item CHOCOLATE = register(
            "chocolate",
            new Item.Settings().food(new FoodComponent(3, 3.0F, false)));
    public static final Item DOUGH = register(
            "dough",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item LEMON = register(
            "lemon",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item ONION = register(
            "onion",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item ORANGE = register(
            "orange",
            new Item.Settings().food(new FoodComponent(2, 1.0F, false)));
    public static final Item WORM_COOKED = register(
            "worm_cooked",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item WORM_RAW = register(
            "worm_raw",
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item BEEF_STEW = register(
            "beef_stew",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item BOWL_MILK = register(
            "bowl_milk",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item BOWL_SALAD = register(
            "bowl_salad",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item BOWL_WATER = register(
            "bowl_water",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item CEREAL = register(
            "cereal",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item CHICKEN_SOUP = register(
            "chicken_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item CREAM_OF_MUSHROOM_SOUP = register(
            "cream_of_mushroom_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item CREAM_OF_VEGETABLE_SOUP = register(
            "cream_of_vegetable_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item ICE_CREAM = register(
            "ice_cream",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item MASHED_POTATO = register(
            "mashed_potato",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item MUSHROOM_STEW = register(
            "mushroom_stew",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item PORRIDGE = register(
            "porridge",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item PUMPKIN_SOUP = register(
            "pumpkin_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item SORBET = register(
            "sorbet",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));
    public static final Item VEGETABLE_SOUP = register(
            "vegetable_soup",
            new Item.Settings().food(new FoodComponent(5, 5.0F, false)).useRemainder(Items.BOWL));


    public static final Item ADAMANTIUM_AXE = register("adamantium_axe",
            Settings -> new AxeItem(At_ToolMaterial.ADAMANTIUM, 8.0F, -3.0F, Settings),
            new Item.Settings());
    public static final Item ADAMANTIUM_BATTLE_AXE = register("adamantium_battle_axe",
            Settings -> new At_BattleAxeItem(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F, Settings),
            new Item.Settings());
    public static final Item ADAMANTIUM_DAGGER = register("adamantium_dagger",
            ((ItemSettingsExtension)new Item.Settings()).dagger(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F));
    public static final Item ADAMANTIUM_HATCHET = register("adamantium_hatchet",
            Settings -> new At_HandAxeItem(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F, Settings),
            new Item.Settings());
    public static final Item ADAMANTIUM_HOE = register("adamantium_hoe",
            Settings -> new HoeItem(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F, Settings),
            new Item.Settings());
    public static final Item ADAMANTIUM_KNIFE = register("adamantium_knife",
            ((ItemSettingsExtension)new Item.Settings()).dagger(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F));
    public static final Item ADAMANTIUM_MATTOCK = register("adamantium_mattock",
            Settings -> new At_MattockItem(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F,Settings),
            new Item.Settings());
    public static final Item ADAMANTIUM_PICKAXE = register("adamantium_pickaxe",
            new Item.Settings().pickaxe(At_ToolMaterial.ADAMANTIUM,  5.0F, -3.0F));
    public static final Item ADAMANTIUM_SCYTHE = register("adamantium_scythe",
            Settings -> new HoeItem(At_ToolMaterial.ADAMANTIUM, 0.0F, -3.0F, Settings),
            new Item.Settings());
    public static final Item ADAMANTIUM_SHEARS = register("adamantium_shears",
            ShearsItem::new,
            ((ItemSettingsExtension)new Item.Settings()).shears(At_ToolMaterial.ADAMANTIUM,  5.0F, -3.0F));
    public static final Item ADAMANTIUM_SHOVEL = register("adamantium_shovel",
            Settings -> new ShovelItem(ToolMaterial.WOOD, 1.5F, -3.0F, Settings),
            new Item.Settings());
    public static final Item ADAMANTIUM_SWORD = register("adamantium_sword",
            new Item.Settings().sword(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F));
    public static final Item ADAMANTIUM_WAR_HAMMER = register("adamantium_war_hammer",
            ((ItemSettingsExtension)new Item.Settings()).war_hammer(At_ToolMaterial.ADAMANTIUM, 5.0F, -3.0F));

    public static final Item ANCIENT_METAL_AXE = register("ancient_metal_axe");
    public static final Item ANCIENT_METAL_BATTLE_AXE = register("ancient_metal_battle_axe");
    public static final Item ANCIENT_METAL_DAGGER = register("ancient_metal_dagger");
    public static final Item ANCIENT_METAL_HATCHET = register("ancient_metal_hatchet");
    public static final Item ANCIENT_METAL_HOE = register("ancient_metal_hoe");
    public static final Item ANCIENT_METAL_KNIFE = register("ancient_metal_knife");
    public static final Item ANCIENT_METAL_MATTOCK = register("ancient_metal_mattock");
    public static final Item ANCIENT_METAL_PICKAXE = register("ancient_metal_pickaxe");
    public static final Item ANCIENT_METAL_SCYTHE = register("ancient_metal_scythe");
    public static final Item ANCIENT_METAL_SHEARS = register("ancient_metal_shears");
    public static final Item ANCIENT_METAL_SHOVEL = register("ancient_metal_shovel");
    public static final Item ANCIENT_METAL_SWORD = register("ancient_metal_sword");
    public static final Item ANCIENT_METAL_WAR_HAMMER = register("ancient_metal_war_hammer");
    public static final Item COPPER_AXE = register("copper_axe");
    public static final Item COPPER_BATTLE_AXE = register("copper_battle_axe");
    public static final Item COPPER_DAGGER = register("copper_dagger");
    public static final Item COPPER_HATCHET = register("copper_hatchet");
    public static final Item COPPER_HOE = register("copper_hoe");
    public static final Item COPPER_KNIFE = register("copper_knife");
    public static final Item COPPER_MATTOCK = register("copper_mattock");
    public static final Item COPPER_PICKAXE = register("copper_pickaxe");
    public static final Item COPPER_SCYTHE = register("copper_scythe");
    public static final Item COPPER_SHEARS = register("copper_shears");
    public static final Item COPPER_SHOVEL = register("copper_shovel");
    public static final Item COPPER_SWORD = register("copper_sword");
    public static final Item COPPER_WAR_HAMMER = register("copper_war_hammer");
    public static final Item FLINT_AXE = register("flint_axe");
    public static final Item FLINT_HATCHET = register("flint_hatchet");
    public static final Item FLINT_KNIFE = register("flint_knife");
    public static final Item FLINT_SHOVEL = register("flint_shovel");
    public static final Item GOLD_AXE = register("gold_axe");
    public static final Item GOLD_BATTLE_AXE = register("gold_battle_axe");
    public static final Item GOLD_DAGGER = register("gold_dagger");
    public static final Item GOLD_HATCHET = register("gold_hatchet");
    public static final Item GOLD_HOE = register("gold_hoe");
    public static final Item GOLD_KNIFE = register("gold_knife");
    public static final Item GOLD_MATTOCK = register("gold_mattock");
    public static final Item GOLD_PICKAXE = register("gold_pickaxe");
    public static final Item GOLD_SCYTHE = register("gold_scythe");
    public static final Item GOLD_SHEARS = register("gold_shears");
    public static final Item GOLD_SHOVEL = register("gold_shovel");
    public static final Item GOLD_SWORD = register("gold_sword");
    public static final Item GOLD_WAR_HAMMER = register("gold_war_hammer");
    public static final Item IRON_BATTLE_AXE = register("iron_battle_axe");
    public static final Item IRON_DAGGER = register("iron_dagger");
    public static final Item IRON_HATCHET = register("iron_hatchet");
    public static final Item IRON_KNIFE = register("iron_knife");
    public static final Item IRON_MATTOCK = register("iron_mattock");
    public static final Item IRON_SCYTHE = register("iron_scythe");
    public static final Item IRON_WAR_HAMMER = register("iron_war_hammer");
    public static final Item MITHRIL_AXE = register("mithril_axe");
    public static final Item MITHRIL_BATTLE_AXE = register("mithril_battle_axe");
    public static final Item MITHRIL_DAGGER = register("mithril_dagger");
    public static final Item MITHRIL_HATCHET = register("mithril_hatchet");
    public static final Item MITHRIL_HOE = register("mithril_hoe");
    public static final Item MITHRIL_KNIFE = register("mithril_knife");
    public static final Item MITHRIL_MATTOCK = register("mithril_mattock");
    public static final Item MITHRIL_PICKAXE = register("mithril_pickaxe");
    public static final Item MITHRIL_SCYTHE = register("mithril_scythe");
    public static final Item MITHRIL_SHEARS = register("mithril_shears");
    public static final Item MITHRIL_SHOVEL = register("mithril_shovel");
    public static final Item MITHRIL_SWORD = register("mithril_sword");
    public static final Item MITHRIL_WAR_HAMMER = register("mithril_war_hammer");
    public static final Item OBSIDIAN_AXE = register("obsidian_axe");
    public static final Item OBSIDIAN_HATCHET = register("obsidian_hatchet");
    public static final Item OBSIDIAN_KNIFE = register("obsidian_knife");
    public static final Item OBSIDIAN_SHOVEL = register("obsidian_shovel");
    public static final Item RUSTED_IRON_AXE = register("rusted_iron_axe");
    public static final Item RUSTED_IRON_BATTLE_AXE = register("rusted_iron_battle_axe");
    public static final Item RUSTED_IRON_DAGGER = register("rusted_iron_dagger");
    public static final Item RUSTED_IRON_HATCHET = register("rusted_iron_hatchet");
    public static final Item RUSTED_IRON_HOE = register("rusted_iron_hoe");
    public static final Item RUSTED_IRON_KNIFE = register("rusted_iron_knife");
    public static final Item RUSTED_IRON_MATTOCK = register("rusted_iron_mattock");
    public static final Item RUSTED_IRON_PICKAXE = register("rusted_iron_pickaxe");
    public static final Item RUSTED_IRON_SCYTHE = register("rusted_iron_scythe");
    public static final Item RUSTED_IRON_SHEARS = register("rusted_iron_shears");
    public static final Item RUSTED_IRON_SHOVEL = register("rusted_iron_shovel");
    public static final Item RUSTED_IRON_SWORD = register("rusted_iron_sword");
    public static final Item RUSTED_IRON_WAR_HAMMER = register("rusted_iron_war_hammer");
    public static final Item SILVER_AXE = register("silver_axe");
    public static final Item SILVER_BATTLE_AXE = register("silver_battle_axe");
    public static final Item SILVER_DAGGER = register("silver_dagger");
    public static final Item SILVER_HATCHET = register("silver_hatchet");
    public static final Item SILVER_HOE = register("silver_hoe");
    public static final Item SILVER_KNIFE = register("silver_knife");
    public static final Item SILVER_MATTOCK = register("silver_mattock");
    public static final Item SILVER_PICKAXE = register("silver_pickaxe");
    public static final Item SILVER_SCYTHE = register("silver_scythe");
    public static final Item SILVER_SHEARS = register("silver_shears");
    public static final Item SILVER_SHOVEL = register("silver_shovel");
    public static final Item SILVER_SWORD = register("silver_sword");
    public static final Item SILVER_WAR_HAMMER = register("silver_war_hammer");
    public static final Item STONE_DAGGER = register("stone_dagger");
    public static final Item WOOD_CLUB = register("wood_club");
    public static final Item WOOD_CUDGEL = register("wood_cudgel");

    public static final Item ADAMANTIUM_ARROW = register("adamantium_arrow");
    public static final Item ANCIENT_METAL_ARROW = register("ancient_metal_arrow");
    public static final Item COPPER_ARROW = register("copper_arrow");
    public static final Item FLINT_ARROW = register("flint_arrow");
    public static final Item GOLD_ARROW = register("gold_arrow");
    public static final Item IRON_ARROW = register("iron_arrow");
    public static final Item MITHRIL_ARROW = register("mithril_arrow");
    public static final Item OBSIDIAN_ARROW = register("obsidian_arrow");
    public static final Item SILVER_ARROW = register("silver_arrow");
    public static final Item RUSTED_IRON_ARROW = register("rusted_iron_arrow");
    public static final Item ADAMANTIUM_CHAINS = register("adamantium_chains");
    public static final Item ANCIENT_METAL_CHAINS = register("ancient_metal_chains");
    public static final Item RUSTED_IRON_CHAINS = register("rusted_iron_chains");
    public static final Item COPPER_CHAINS = register("copper_chains");
    public static final Item GOLD_CHAINS = register("gold_chains");
    public static final Item IRON_CHAINS = register("iron_chains");
    public static final Item MITHRIL_CHAINS = register("mithril_chains");
    public static final Item SILVER_CHAINS = register("silver_chains");
    public static final Item ADAMANTIUM_COINS = register("adamantium_coins");
    public static final Item ANCIENT_METAL_COINS = register("ancient_metal_coins");
    public static final Item COPPER_COINS = register("copper_coins");
    public static final Item GOLD_COINS = register("gold_coins");
    public static final Item IRON_COINS = register("iron_coins");
    public static final Item MITHRIL_COINS = register("mithril_coins");
    public static final Item SILVER_COINS = register("silver_coins");

    public static final Item ADAMANTIUM_BUCKET = register("adamantium_bucket", settings -> new At_BucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_ADAMANTIUM_BUCKET = register(
            "water_adamantium_bucket", settings -> new At_BucketItem(Fluids.WATER, settings, ADAMANTIUM_BUCKET), new Item.Settings().recipeRemainder(ADAMANTIUM_BUCKET).maxCount(1)
    );
    public static final Item LAVA_ADAMANTIUM_BUCKET = register(
            "lava_adamantium_bucket", settings -> new At_BucketItem(Fluids.LAVA, settings, ADAMANTIUM_BUCKET), new Item.Settings().recipeRemainder(ADAMANTIUM_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_ADAMANTIUM_BUCKET = register(
            "powder_snow_adamantium_bucket",
            settings -> new At_PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_ADAMANTIUM_BUCKET = register(
            "milk_adamantium_bucket",
            new Item.Settings().recipeRemainder(ADAMANTIUM_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(ADAMANTIUM_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_ADAMANTIUM_BUCKET = register(
            "pufferfish_adamantium_bucket",
            settings -> new At_EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_ADAMANTIUM_BUCKET = register(
            "salmon_adamantium_bucket",
            settings -> new At_EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_ADAMANTIUM_BUCKET = register(
            "cod_adamantium_bucket",
            settings -> new At_EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_ADAMANTIUM_BUCKET = register(
            "tropical_fish_adamantium_bucket",
            settings -> new At_EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_ADAMANTIUM_BUCKET = register(
            "axolotl_adamantium_bucket",
            settings -> new At_EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_ADAMANTIUM_BUCKET = register(
            "tadpole_adamantium_bucket",
            settings -> new At_EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, ADAMANTIUM_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item ANCIENT_METAL_BUCKET = register("ancient_metal_bucket", settings -> new At_BucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_ANCIENT_METAL_BUCKET = register(
            "water_ancient_metal_bucket", settings -> new At_BucketItem(Fluids.WATER, settings, ANCIENT_METAL_BUCKET), new Item.Settings().recipeRemainder(ANCIENT_METAL_BUCKET).maxCount(1)
    );
    public static final Item LAVA_ANCIENT_METAL_BUCKET = register(
            "lava_ancient_metal_bucket", settings -> new At_BucketItem(Fluids.LAVA, settings, ANCIENT_METAL_BUCKET), new Item.Settings().recipeRemainder(ANCIENT_METAL_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_ANCIENT_METAL_BUCKET = register(
            "powder_snow_ancient_metal_bucket",
            settings -> new At_PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_ANCIENT_METAL_BUCKET = register(
            "milk_ancient_metal_bucket",
            new Item.Settings().recipeRemainder(ANCIENT_METAL_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(ANCIENT_METAL_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_ANCIENT_METAL_BUCKET = register(
            "pufferfish_ancient_metal_bucket",
            settings -> new At_EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_ANCIENT_METAL_BUCKET = register(
            "salmon_ancient_metal_bucket",
            settings -> new At_EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_ANCIENT_METAL_BUCKET = register(
            "cod_ancient_metal_bucket",
            settings -> new At_EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_ANCIENT_METAL_BUCKET = register(
            "tropical_fish_ancient_metal_bucket",
            settings -> new At_EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_ANCIENT_METAL_BUCKET = register(
            "axolotl_ancient_metal_bucket",
            settings -> new At_EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_ANCIENT_METAL_BUCKET = register(
            "tadpole_ancient_metal_bucket",
            settings -> new At_EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, ANCIENT_METAL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COPPER_BUCKET = register("copper_bucket", settings -> new At_BucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_COPPER_BUCKET = register(
            "water_copper_bucket", settings -> new At_BucketItem(Fluids.WATER, settings, COPPER_BUCKET), new Item.Settings().recipeRemainder(COPPER_BUCKET).maxCount(1)
    );
    public static final Item LAVA_COPPER_BUCKET = register(
            "lava_copper_bucket", settings -> new At_BucketItem(Fluids.LAVA, settings, COPPER_BUCKET), new Item.Settings().recipeRemainder(COPPER_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_COPPER_BUCKET = register(
            "powder_snow_copper_bucket",
            settings -> new At_PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_COPPER_BUCKET = register(
            "milk_copper_bucket",
            new Item.Settings().recipeRemainder(COPPER_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(COPPER_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_COPPER_BUCKET = register(
            "pufferfish_copper_bucket",
            settings -> new At_EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_COPPER_BUCKET = register(
            "salmon_copper_bucket",
            settings -> new At_EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_COPPER_BUCKET = register(
            "cod_copper_bucket",
            settings -> new At_EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_COPPER_BUCKET = register(
            "tropical_fish_copper_bucket",
            settings -> new At_EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_COPPER_BUCKET = register(
            "axolotl_copper_bucket",
            settings -> new At_EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_COPPER_BUCKET = register(
            "tadpole_copper_bucket",
            settings -> new At_EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, COPPER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item GOLD_BUCKET = register("gold_bucket", settings -> new At_BucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_GOLD_BUCKET = register(
            "water_gold_bucket", settings -> new At_BucketItem(Fluids.WATER, settings, GOLD_BUCKET), new Item.Settings().recipeRemainder(GOLD_BUCKET).maxCount(1)
    );
    public static final Item LAVA_GOLD_BUCKET = register(
            "lava_gold_bucket", settings -> new At_BucketItem(Fluids.LAVA, settings, GOLD_BUCKET), new Item.Settings().recipeRemainder(GOLD_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_GOLD_BUCKET = register(
            "powder_snow_gold_bucket",
            settings -> new At_PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_GOLD_BUCKET = register(
            "milk_gold_bucket",
            new Item.Settings().recipeRemainder(GOLD_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(GOLD_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_GOLD_BUCKET = register(
            "pufferfish_gold_bucket",
            settings -> new At_EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_GOLD_BUCKET = register(
            "salmon_gold_bucket",
            settings -> new At_EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_GOLD_BUCKET = register(
            "cod_gold_bucket",
            settings -> new At_EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_GOLD_BUCKET = register(
            "tropical_fish_gold_bucket",
            settings -> new At_EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_GOLD_BUCKET = register(
            "axolotl_gold_bucket",
            settings -> new At_EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_GOLD_BUCKET = register(
            "tadpole_gold_bucket",
            settings -> new At_EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, GOLD_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item MITHRIL_BUCKET = register("mithril_bucket", settings -> new At_BucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_MITHRIL_BUCKET = register(
            "water_mithril_bucket", settings -> new At_BucketItem(Fluids.WATER, settings, MITHRIL_BUCKET), new Item.Settings().recipeRemainder(MITHRIL_BUCKET).maxCount(1)
    );
    public static final Item LAVA_MITHRIL_BUCKET = register(
            "lava_mithril_bucket", settings -> new At_BucketItem(Fluids.LAVA, settings, MITHRIL_BUCKET), new Item.Settings().recipeRemainder(MITHRIL_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_MITHRIL_BUCKET = register(
            "powder_snow_mithril_bucket",
            settings -> new At_PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_MITHRIL_BUCKET = register(
            "milk_mithril_bucket",
            new Item.Settings().recipeRemainder(MITHRIL_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(MITHRIL_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_MITHRIL_BUCKET = register(
            "pufferfish_mithril_bucket",
            settings -> new At_EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_MITHRIL_BUCKET = register(
            "salmon_mithril_bucket",
            settings -> new At_EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_MITHRIL_BUCKET = register(
            "cod_mithril_bucket",
            settings -> new At_EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_MITHRIL_BUCKET = register(
            "tropical_fish_mithril_bucket",
            settings -> new At_EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_MITHRIL_BUCKET = register(
            "axolotl_mithril_bucket",
            settings -> new At_EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_MITHRIL_BUCKET = register(
            "tadpole_mithril_bucket",
            settings -> new At_EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, MITHRIL_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SILVER_BUCKET = register("silver_bucket", settings -> new At_BucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_SILVER_BUCKET = register(
            "water_silver_bucket", settings -> new At_BucketItem(Fluids.WATER, settings, SILVER_BUCKET), new Item.Settings().recipeRemainder(SILVER_BUCKET).maxCount(1)
    );
    public static final Item LAVA_SILVER_BUCKET = register(
            "lava_silver_bucket", settings -> new At_BucketItem(Fluids.LAVA, settings, SILVER_BUCKET), new Item.Settings().recipeRemainder(SILVER_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_SILVER_BUCKET = register(
            "powder_snow_silver_bucket",
            settings -> new At_PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_SILVER_BUCKET = register(
            "milk_silver_bucket",
            new Item.Settings().recipeRemainder(SILVER_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(SILVER_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_SILVER_BUCKET = register(
            "pufferfish_silver_bucket",
            settings -> new At_EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_SILVER_BUCKET = register(
            "salmon_silver_bucket",
            settings -> new At_EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_SILVER_BUCKET = register(
            "cod_silver_bucket",
            settings -> new At_EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_SILVER_BUCKET = register(
            "tropical_fish_silver_bucket",
            settings -> new At_EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_SILVER_BUCKET = register(
            "axolotl_silver_bucket",
            settings -> new At_EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_SILVER_BUCKET = register(
            "tadpole_silver_bucket",
            settings -> new At_EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, SILVER_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item NETHERITE_BUCKET = register("netherite_bucket", settings -> new At_BucketItem(Fluids.EMPTY, settings, null), new Item.Settings().maxCount(16));
    public static final Item WATER_NETHERITE_BUCKET = register(
            "water_netherite_bucket", settings -> new At_BucketItem(Fluids.WATER, settings, NETHERITE_BUCKET), new Item.Settings().recipeRemainder(NETHERITE_BUCKET).maxCount(1)
    );
    public static final Item LAVA_NETHERITE_BUCKET = register(
            "lava_netherite_bucket", settings -> new At_BucketItem(Fluids.LAVA, settings, NETHERITE_BUCKET), new Item.Settings().recipeRemainder(NETHERITE_BUCKET).maxCount(1)
    );
    public static final Item POWDER_SNOW_NETHERITE_BUCKET = register(
            "powder_snow_netherite_bucket",
            settings -> new At_PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).useItemPrefixedTranslationKey()
    );
    public static final Item MILK_NETHERITE_BUCKET = register(
            "milk_netherite_bucket",
            new Item.Settings().recipeRemainder(NETHERITE_BUCKET).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(NETHERITE_BUCKET).maxCount(1)
    );
    public static final Item PUFFERFISH_NETHERITE_BUCKET = register(
            "pufferfish_netherite_bucket",
            settings -> new At_EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item SALMON_NETHERITE_BUCKET = register(
            "salmon_netherite_bucket",
            settings -> new At_EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item COD_NETHERITE_BUCKET = register(
            "cod_netherite_bucket",
            settings -> new At_EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TROPICAL_FISH_NETHERITE_BUCKET = register(
            "tropical_fish_netherite_bucket",
            settings -> new At_EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item AXOLOTL_NETHERITE_BUCKET = register(
            "axolotl_netherite_bucket",
            settings -> new At_EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item TADPOLE_NETHERITE_BUCKET = register(
            "tadpole_netherite_bucket",
            settings -> new At_EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, settings, NETHERITE_BUCKET),
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
    );
    public static final Item MITHRIL_BOW = register("mithril_bow", BowItem::new, new Item.Settings().maxDamage(128).enchantable(1));
    public static final Item ANCIENT_METAL_BOW = register("ancient_metal_bow", BowItem::new, new Item.Settings().maxDamage(64).enchantable(1));

    public static final Item ADAMANTIUM_FISHING_ROD = register("adamantium_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(128).enchantable(1));
    public static final Item ANCIENT_METAL_FISHING_ROD = register("ancient_metal_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(96).enchantable(1));
    public static final Item COPPER_FISHING_ROD = register("copper_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(8).enchantable(1));
    public static final Item FLINT_FISHING_ROD = register("flint_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(4).enchantable(1));
    public static final Item GOLD_FISHING_ROD = register("gold_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(4).enchantable(1));
    public static final Item IRON_FISHING_ROD = register("iron_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(32).enchantable(1));
    public static final Item MITHRIL_FISHING_ROD = register("mithril_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(64).enchantable(1));
    public static final Item OBSIDIAN_FISHING_ROD = register("obsidian_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(32).enchantable(1));
    public static final Item SILVER_FISHING_ROD = register("silver_fishing_rod", FishingRodItem::new, new Item.Settings().maxDamage(8).enchantable(1));

    public static final Item ADAMANTIUM_INGOT = register("adamantium_ingot");
    public static final Item ANCIENT_METAL_INGOT = register("ancient_metal_ingot");
    public static final Item MITHRIL_INGOT = register("mithril_ingot");
    public static final Item SILVER_INGOT = register("silver_ingot");

    public static final Item ADAMANTIUM_NUGGET = register("adamantium_nugget");
    public static final Item ANCIENT_METAL_NUGGET = register("ancient_metal_nugget");
    public static final Item MITHRIL_NUGGET = register("mithril_nugget");
    public static final Item SILVER_NUGGET = register("silver_nugget");

    public static final Item DIAMOND_SHARD = register("diamond_shard");
    public static final Item EMERALD_SHARD = register("emerald_shard");
    public static final Item FLINT_SHARD = register("flint_shard");
    public static final Item GLASS_SHARD = register("glass_shard");
    public static final Item OBSIDIAN_SHARD = register("obsidian_shard");
    public static final Item QUARTZ_SHARD = register("quartz_shard");

    public static final ItemGroup AT_MINT_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ADAMANTIUM_HELMET))
            .displayName(Text.translatable("itemGroup.at_mite.item_group"))
            .entries((context, entries) -> {
                    entries.add(At_Blocks.ADAMANTIUM_ORE);
                    entries.add(At_Blocks.ADAMANTIUM_BLOCK);
                    entries.add(At_Blocks.ADAMANTIUM_BARS);
                    entries.add(At_Blocks.ANCIENT_METAL_BARS);
                    entries.add(At_Blocks.ANCIENT_METAL_BLOCK);
                    entries.add(At_Blocks.GOLD_BARS);
                    entries.add(At_Blocks.MANTLE);
                    entries.add(At_Blocks.MITHRIL_BARS);
                    entries.add(At_Blocks.MITHRIL_BLOCK);
                    entries.add(At_Blocks.MITHRIL_ORE);
                    entries.add(At_Blocks.SILVER_BARS);
                    entries.add(At_Blocks.SILVER_BLOCK);
                    entries.add(At_Blocks.SILVER_ORE);
                    entries.add(At_Blocks.COPPER_BARS);
                    entries.add(At_Blocks.ADAMANTIUM_DOOR);
                    entries.add(At_Blocks.ANCIENT_METAL_DOOR);
                    entries.add(At_Blocks.MITHRIL_DOOR);
                    entries.add(At_Blocks.SILVER_DOOR);
                    entries.add(At_Blocks.GOLD_DOOR);
                    entries.add(DIAMOND_SHARD);
                    entries.add(EMERALD_SHARD);
                    entries.add(FLINT_SHARD);
                    entries.add(GLASS_SHARD);
                    entries.add(OBSIDIAN_SHARD);
                    entries.add(QUARTZ_SHARD);
                    entries.add(ADAMANTIUM_INGOT);
                    entries.add(ANCIENT_METAL_INGOT);
                    entries.add(MITHRIL_INGOT);
                    entries.add(SILVER_INGOT);
                    entries.add(ADAMANTIUM_NUGGET);
                    entries.add(ANCIENT_METAL_NUGGET);
                    entries.add(MITHRIL_NUGGET);
                    entries.add(SILVER_NUGGET);
                    entries.add(ADAMANTIUM_FISHING_ROD);
                    entries.add(ANCIENT_METAL_FISHING_ROD);
                    entries.add(COPPER_FISHING_ROD);
                    entries.add(FLINT_FISHING_ROD);
                    entries.add(GOLD_FISHING_ROD);
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
                    entries.add(GOLD_AXE);
                    entries.add(GOLD_BATTLE_AXE);
                    entries.add(GOLD_DAGGER);
                    entries.add(GOLD_HATCHET);
                    entries.add(GOLD_HOE);
                    entries.add(GOLD_KNIFE);
                    entries.add(GOLD_MATTOCK);
                    entries.add(GOLD_PICKAXE);
                    entries.add(GOLD_SCYTHE);
                    entries.add(GOLD_SHEARS);
                    entries.add(GOLD_SHOVEL);
                    entries.add(GOLD_SWORD);
                    entries.add(GOLD_WAR_HAMMER);
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
                    entries.add(GOLD_ARROW);
                    entries.add(IRON_ARROW);
                    entries.add(MITHRIL_ARROW);
                    entries.add(OBSIDIAN_ARROW);
                    entries.add(SILVER_ARROW);
                    entries.add(RUSTED_IRON_ARROW);
                    entries.add(ADAMANTIUM_CHAINS);
                    entries.add(GOLD_CHAINS);
                    entries.add(IRON_CHAINS);
                    entries.add(MITHRIL_CHAINS);
                    entries.add(SILVER_CHAINS);
                    entries.add(ANCIENT_METAL_CHAINS);
                    entries.add(COPPER_CHAINS);
                    entries.add(RUSTED_IRON_CHAINS);
                    entries.add(ADAMANTIUM_COINS);
                    entries.add(ANCIENT_METAL_COINS);
                    entries.add(COPPER_COINS);
                    entries.add(GOLD_COINS);
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

    private static Item register(String path) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, path));
        return Items.register(registryKey, Item::new, new Item.Settings());
    }
    private static Item register(String path, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, path));
        return Items.register(registryKey, Item::new, settings);
    }
    private static Item register(String path, Function<Item.Settings, Item> factory,Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }
    public static void init(){
        Registry.register(Registries.ITEM_GROUP, Identifier.of("tutorial", "test_group"), AT_MINT_GROUP);
    }
}
