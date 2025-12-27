package com.acuteterror233.mite.item.armor;

import com.acuteterror233.mite.Mme;
import com.acuteterror233.mite.item.equipment.AtArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.HashMap;

/**
 * 护甲材料表：定义不同材质的耐久、各部位护甲值、附魔性、装备音效、韧性与击退抗性，
 * 并绑定其资源外观键。
 */
public class AtArmorMaterials {
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "adamantium"));
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "adamantium_chainmail"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "ancient_metal"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "ancient_metal_chainmail"));
    public static final RegistryKey<EquipmentAsset> COPPER_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "copper"));
    public static final RegistryKey<EquipmentAsset> COPPER_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "copper_chainmail"));
    public static final RegistryKey<EquipmentAsset> MITHRIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "mithril"));
    public static final RegistryKey<EquipmentAsset> MITHRIL_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "mithril_chainmail"));
    public static final RegistryKey<EquipmentAsset> RUSTED_IRON_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "rusted_iron"));
    public static final RegistryKey<EquipmentAsset> RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "rusted_iron_chainmail"));
    public static final RegistryKey<EquipmentAsset> SILVER_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "silver"));
    public static final RegistryKey<EquipmentAsset> SILVER_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "silver_chainmail"));
    public static final RegistryKey<EquipmentAsset> GOLD_CHAINMAIL_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "gold_chainmail"));

    public static final AtArmorMaterial GOLD_CHAINMAIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.7F);
                put(EquipmentType.CHESTPLATE, 2.7F);
                put(EquipmentType.LEGGINGS, 2.3F);
                put(EquipmentType.BOOTS, 1.3F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            GOLD_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial ADAMANTIUM_ARMOR_MATERIAL = new AtArmorMaterial(
            30,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 2.1F);
                put(EquipmentType.CHESTPLATE, 3.3F);
                put(EquipmentType.LEGGINGS, 2.9F);
                put(EquipmentType.BOOTS, 1.7F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            2.0F,
            0.0F,
            null,
            ADAMANTIUM_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.7F);
                put(EquipmentType.CHESTPLATE, 2.7F);
                put(EquipmentType.LEGGINGS, 2.3F);
                put(EquipmentType.BOOTS, 1.3F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            ADAMANTIUM_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial ANCIENT_METAL_ARMOR_MATERIAL = new AtArmorMaterial(
            30,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.7F);
                put(EquipmentType.CHESTPLATE, 2.7F);
                put(EquipmentType.LEGGINGS, 2.3F);
                put(EquipmentType.BOOTS, 1.3F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            ANCIENT_METAL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.3F);
                put(EquipmentType.CHESTPLATE, 2.0F);
                put(EquipmentType.LEGGINGS, 1.8F);
                put(EquipmentType.BOOTS, 1.0F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            ANCIENT_METAL_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial COPPER_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.5F);
                put(EquipmentType.CHESTPLATE, 2.3F);
                put(EquipmentType.LEGGINGS, 2.0F);
                put(EquipmentType.BOOTS, 1.2F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            COPPER_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial COPPER_CHAINMAIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.0F);
                put(EquipmentType.CHESTPLATE, 1.7F);
                put(EquipmentType.LEGGINGS, 1.5F);
                put(EquipmentType.BOOTS, 0.83F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            COPPER_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial MITHRIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.9F);
                put(EquipmentType.CHESTPLATE, 3.0F);
                put(EquipmentType.LEGGINGS, 2.6F);
                put(EquipmentType.BOOTS, 1.5F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            MITHRIL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial MITHRIL_CHAINMAIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.5F);
                put(EquipmentType.CHESTPLATE, 2.3F);
                put(EquipmentType.LEGGINGS, 2.0F);
                put(EquipmentType.BOOTS, 1.2F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            MITHRIL_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial RUSTED_IRON_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.3F);
                put(EquipmentType.CHESTPLATE, 2.0F);
                put(EquipmentType.LEGGINGS, 1.8F);
                put(EquipmentType.BOOTS, 1.0F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            RUSTED_IRON_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 0.83F);
                put(EquipmentType.CHESTPLATE, 1.3F);
                put(EquipmentType.LEGGINGS, 1.2F);
                put(EquipmentType.BOOTS, 0.67F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            RUSTED_IRON_CHAINMAIL_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial SILVER_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.5F);
                put(EquipmentType.CHESTPLATE, 2.3F);
                put(EquipmentType.LEGGINGS, 2.0F);
                put(EquipmentType.BOOTS, 1.2F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            SILVER_ARMOR_MATERIAL_KEY);
    public static final AtArmorMaterial SILVER_CHAINMAIL_ARMOR_MATERIAL = new AtArmorMaterial(
            15,
            new HashMap<>() {{
                put(EquipmentType.HELMET, 1.0F);
                put(EquipmentType.CHESTPLATE, 2.7F);
                put(EquipmentType.LEGGINGS, 1.5F);
                put(EquipmentType.BOOTS, 0.83F);
            }},
            5,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            SILVER_CHAINMAIL_ARMOR_MATERIAL_KEY);
}




