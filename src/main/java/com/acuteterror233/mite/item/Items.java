package com.acuteterror233.mite.item;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.item.armor.adamantium;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class Items {

    public static final Item ADAMANTIUM_HELMET = register(
        "adamantium_helmet",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_ARMOR_MATERIAL, EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHESTPLATE = register(
        "adamantium_chestplate",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_LEGGINGS = register(
        "adamantium_leggings",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ADAMANTIUM_BOOTS = register(
        "adamantium_boots",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_BOOTS = register(
        "adamantium_chainmail_boots",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_CHESTPLATE = register(
        "adamantium_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register(
        "adamantium_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ADAMANTIUM_CHAINMAIL_LEGGINGS = register(
        "adamantium_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(adamantium.ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_HELMET = register(
        "ancient_metal_helmet",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHESTPLATE = register(
        "ancient_metal_chestplate",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_LEGGINGS = register(
        "ancient_metal_leggings",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item ANCIENT_METAL_BOOTS = register(
        "ancient_metal_boots",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_BOOTS = register(
        "ancient_metal_chainmail_boots",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_CHESTPLATE = register(
        "ancient_metal_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_HELMET = register(
        "ancient_metal_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item ANCIENT_METAL_CHAINMAIL_LEGGINGS = register(
        "ancient_metal_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(adamantium.ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_HELMET =  register(
        "copper_helmet",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
    public static final Item COPPER_CHESTPLATE = register(
        "copper_chestplate",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_LEGGINGS = register(
        "copper_leggings",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_BOOTS = register(
        "copper_boots",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_BOOTS = register(
        "copper_chainmail_boots",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.BOOTS)
    );
    public static final Item COPPER_CHAINMAIL_CHESTPLATE = register(
        "copper_chainmail_chestplate",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.CHESTPLATE)
    );
     public static final Item COPPER_CHAINMAIL_HELMET = register(
        "copper_chainmail_helmet",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.HELMET)
    );
      public static final Item COPPER_CHAINMAIL_LEGGINGS = register(
        "copper_chainmail_leggings",
        Item::new,
        new Item.Settings().armor(adamantium.COPPER_CHAINMAIL_ARMOR_MATERIAL,EquipmentType.LEGGINGS)
    );


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
            })
            .build();

    private static Item register(String path, Function<Item.Settings, Item> factory) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, path));
        return net.minecraft.item.Items.register(registryKey, factory, new Item.Settings());
    }
    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(At_mite.MOD_ID, path));
        return net.minecraft.item.Items.register(registryKey, factory, settings);
    }
    public static void init(){
        Registry.register(Registries.ITEM_GROUP, Identifier.of("tutorial", "test_group"), AT_MINT_GROUP);
    }
}
