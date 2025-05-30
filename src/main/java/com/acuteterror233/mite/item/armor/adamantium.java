package com.acuteterror233.mite.item.armor;

import com.acuteterror233.mite.at_mite;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

public class adamantium {
    public static final int BASE_DURABILITY = 15;
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(at_mite.MOD_ID, "adamantium"));
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(at_mite.MOD_ID, "textures/armor/adamantium_chainmail"));

    public static final ArmorMaterial ADAMANTIUM_ARMORMATERIAL = new ArmorMaterial(
            BASE_DURABILITY,
            Map.of(
                    EquipmentType.HELMET, 3,
                    EquipmentType.CHESTPLATE, 8,
                    EquipmentType.LEGGINGS, 6,
                    EquipmentType.BOOTS, 3
            ),
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            ADAMANTIUM_ARMOR_MATERIAL_KEY
    );

    public static final ArmorMaterial ADAMANTIUM_CHAINMAIL_ARMORMATERIAL = new ArmorMaterial(
            BASE_DURABILITY,
            Map.of(
                    EquipmentType.HELMET, 3,
                    EquipmentType.CHESTPLATE, 8,
                    EquipmentType.LEGGINGS, 6,
                    EquipmentType.BOOTS, 3
            ),
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY
    );
}
