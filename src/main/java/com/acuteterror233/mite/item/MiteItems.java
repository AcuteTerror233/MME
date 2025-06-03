package com.acuteterror233.mite.item;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.item.armor.MiteArmorMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class MiteItems {

    public static final Item ADAMANTIUM_HELMET = register(
        "adamantium_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHESTPLATE = register(
        "adamantium_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_LEGGINGS = register(
        "adamantium_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ADAMANTIUM_BOOTS = register(
        "adamantium_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_BOOTS = register(
        "adamantium_chainmail_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_CHESTPLATE = register(
        "adamantium_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register(
        "adamantium_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_LEGGINGS = register(
        "adamantium_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_HELMET = register(
        "ancient_metal_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHESTPLATE = register(
        "ancient_metal_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_LEGGINGS = register(
        "ancient_metal_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_BOOTS = register(
        "ancient_metal_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_BOOTS = register(
        "ancient_metal_chainmail_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_CHESTPLATE = register(
        "ancient_metal_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_HELMET = register(
        "ancient_metal_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_LEGGINGS = register(
        "ancient_metal_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_HELMET =  register(
        "copper_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item COPPER_CHESTPLATE = register(
        "copper_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_LEGGINGS = register(
        "copper_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_BOOTS = register(
        "copper_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_BOOTS = register(
        "copper_chainmail_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_CHESTPLATE = register(
        "copper_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_CHAINMAIL_HELMET = register(
        "copper_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item COPPER_CHAINMAIL_LEGGINGS = register(
        "copper_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_HELMET = register(
        "mithril_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item MITHRIL_CHESTPLATE = register(
        "mithril_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item MITHRIL_LEGGINGS = register(
        "mithril_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_BOOTS = register(
        "mithril_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_HELMET  = register(
        "mithril_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item MITHRIL_CHAINMAIL_CHESTPLATE  = register(
        "mithril_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item MITHRIL_CHAINMAIL_LEGGINGS  = register(
        "mithril_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item MITHRIL_CHAINMAIL_BOOTS  = register(
        "mithril_chainmail_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.MITHRIL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item RUSTED_IRON_HELMET = register(
        "rusted_iron_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item RUSTED_IRON_CHESTPLATE = register(
        "rusted_iron_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_LEGGINGS = register(
        "rusted_iron_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_BOOTS = register(
        "rusted_iron_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_HELMET = register(
        "rusted_iron_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_CHESTPLATE = register(
        "rusted_iron_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_LEGGINGS = register(
        "rusted_iron_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item RUSTED_IRON_CHAINMAIL_BOOTS = register(
        "rusted_iron_chainmail_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item SILVER_HELMET = register(
        "silver_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
     public static final Item SILVER_CHESTPLATE = register(
        "silver_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item SILVER_LEGGINGS = register(
        "silver_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item SILVER_BOOTS = register(
        "silver_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item SILVER_CHAINMAIL_HELMET = register(
        "silver_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item SILVER_CHAINMAIL_CHESTPLATE = register(
        "silver_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item SILVER_CHAINMAIL_LEGGINGS = register(
        "silver_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item SILVER_CHAINMAIL_BOOTS = register(
        "silver_chainmail_boots",
        Item::new,
        new Item.Settings().armor(MiteArmorMaterial.SILVER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );

    public static final Item BANANA = register(
            "banana",
            Item::new,
            new Item.Settings().food(new FoodComponent(2, 1.0F, false)));
    public static final Item BLUEBERRIE = register(
            "blueberry",
            Item::new,
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item CHEESE = register(
            "cheese",
            Item::new,
            new Item.Settings().food(new FoodComponent(3, 3.0F, false)));
    public static final Item CHOCOLATE = register(
            "chocolate",
            Item::new,
            new Item.Settings().food(new FoodComponent(3, 3.0F, false)));
    public static final Item DOUGH = register(
            "dough",
            Item::new,
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item LEMON = register(
            "lemon",
            Item::new,
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item ONION = register(
            "onion",
            Item::new,
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item ORANGE = register(
            "orange",
            Item::new,
            new Item.Settings().food(new FoodComponent(2, 1.0F, false)));
    public static final Item WORM_COOKED = register(
            "worm_cooked",
            Item::new,
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));
    public static final Item WORM_RAW = register(
            "worm_raw",
            Item::new,
            new Item.Settings().food(new FoodComponent(1, 1.0F, false)));

    public static final ItemGroup AT_MINT_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ADAMANTIUM_HELMET))
            .displayName(Text.translatable("itemGroup.at_mite.item_group"))
            .entries((context, entries) -> {
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
            })
            .build();

    private static Item register(String path, Function<Item.Settings, Item> factory) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, path));
        return Items.register(registryKey, factory, new Item.Settings());
    }
    private static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }
    public static void init(){
        Registry.register(Registries.ITEM_GROUP, Identifier.of("tutorial", "test_group"), AT_MINT_GROUP);
    }
}
