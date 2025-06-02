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

public class adamantium {
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "adamantium"));
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "adamantium_chainmail"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "ancient_metal_material"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(At_mite.MOD_ID, "ancient_metal_chainmail_armor_material"));

    public static final ArmorMaterial ADAMANTIUM_ARMOR_MATERIAL = new ArmorMaterial(
            30,
            new HashMap<EquipmentType, Integer>() {
                {
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
            ADAMANTIUM_ARMOR_MATERIAL_KEY
        );

    public static final ArmorMaterial ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<EquipmentType, Integer>() {
                {
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
            ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY
        );
    public static final ArmorMaterial ANCIENT_METAL_ARMOR_MATERIAL = new ArmorMaterial(
            30,
            new HashMap<EquipmentType, Integer>() {
                {
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
                ANCIENT_METAL_MATERIAL_KEY
        );
        public static final ArmorMaterial ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL = new ArmorMaterial(
            15,
            new HashMap<EquipmentType, Integer>() {
                {
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
                ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL_KEY
        );
    }




