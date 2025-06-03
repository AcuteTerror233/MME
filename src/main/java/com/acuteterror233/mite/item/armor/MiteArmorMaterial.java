package com.acuteterror233.mite.item.armor;

import com.acuteterror233.mite.At_mite;


import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class MiteArmorMaterial {
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "adamantium"));
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "adamantium_chainmail"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "ancient_metal"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "ancient_metal_chainmail"));
    public static final RegistryKey<EquipmentAsset> COPPER_ARMOR_MATERIAL_KEY =  RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "copper"));
    public static final RegistryKey<EquipmentAsset> COPPER_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "copper_chainmail"));
    public static final RegistryKey<EquipmentAsset> MITHRIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "mithril"));
    public static final RegistryKey<EquipmentAsset> MITHRIL_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "mithril_chainmail"));
    public static final RegistryKey<EquipmentAsset> RUSTED_IRON_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "rusted_iron"));
    public static final RegistryKey<EquipmentAsset> RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "rusted_iron_chainmail"));
    public static final RegistryKey<EquipmentAsset> SILVER_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "silver"));
    public static final RegistryKey<EquipmentAsset> SILVER_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "silver_chainmail"));

    public static final ArmorMaterial ARMOR_MATERIAL_ARMOR_MATERIAL = new ArmorMaterial(
            30,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            ADAMANTIUM_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial ARMOR_MATERIAL_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial ANCIENT_METAL_ARMOR_MATERIAL = new ArmorMaterial(
            30,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
            ANCIENT_METAL_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
                ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial COPPER_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                 0.0F,
                 0.0F,
                 null,
                 COPPER_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial COPPER_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
                COPPER_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial MITHRIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
            MITHRIL_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial MITHRIL_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            MITHRIL_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial RUSTED_IRON_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
                RUSTED_IRON_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
                RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial SILVER_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
                SILVER_ARMOR_MATERIAL_KEY);
    public static final ArmorMaterial SILVER_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2);
                put(EquipmentType.CHESTPLATE, 3);
                put(EquipmentType.LEGGINGS, 2);
                put(EquipmentType.BOOTS, 3);
            }},
                5,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.0F,
                0.0F,
                null,
                SILVER_CHAINMAIL_ARMOR_MATERIAL_KEY);
}




