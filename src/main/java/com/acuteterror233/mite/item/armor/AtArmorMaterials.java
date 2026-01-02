package com.acuteterror233.mite.item.armor;

import com.acuteterror233.mite.Mme;
import com.acuteterror233.mite.item.AtToolMaterials;
import com.acuteterror233.mite.item.equipment.AtArmorMaterial;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.google.common.collect.Maps;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * 护甲材料表：定义不同材质的耐久、各部位护甲值、附魔性、装备音效、韧性与击退抗性，
 * 并绑定其资源外观键。
 */
public class AtArmorMaterials {
    public static final RegistryKey<EquipmentAsset> NETHERITE_MATERIAL_KEY = EquipmentAssetKeys.NETHERITE;
    public static final RegistryKey<EquipmentAsset> NETHERITE_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "netherite_chainmail"));
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "adamantium"));
    public static final RegistryKey<EquipmentAsset> ADAMANTIUM_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "adamantium_chainmail"));
    public static final RegistryKey<EquipmentAsset> MITHRIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "mithril"));
    public static final RegistryKey<EquipmentAsset> MITHRIL_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "mithril_chainmail"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "ancient_metal"));
    public static final RegistryKey<EquipmentAsset> ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "ancient_metal_chainmail"));
    public static final RegistryKey<EquipmentAsset> IRON_MATERIAL_KEY = EquipmentAssetKeys.IRON;
    public static final RegistryKey<EquipmentAsset> IRON_CHAINMAIL_MATERIAL_KEY = EquipmentAssetKeys.CHAINMAIL;
    public static final RegistryKey<EquipmentAsset> RUSTED_IRON_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "rusted_iron"));
    public static final RegistryKey<EquipmentAsset> RUSTED_IRON_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "rusted_iron_chainmail"));
    public static final RegistryKey<EquipmentAsset> COPPER_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "copper"));
    public static final RegistryKey<EquipmentAsset> COPPER_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "copper_chainmail"));
    public static final RegistryKey<EquipmentAsset> SILVER_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "silver"));
    public static final RegistryKey<EquipmentAsset> SILVER_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "silver_chainmail"));
    public static final RegistryKey<EquipmentAsset> GOLD_CHAINMAIL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "gold_chainmail"));
    public static final RegistryKey<EquipmentAsset> GOLD_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Mme.MOD_ID, "gold"));

    public static final AtArmorMaterial NETHERITE_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.NETHERITE.durability(),
            createDefenseMap(2.6F, 3.8F, 3.4F, 2.2F, 3.8F),
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            2.0F,
            1.0F,
            ItemTags.NETHERITE_TOOL_MATERIALS,
            NETHERITE_MATERIAL_KEY
    );
    public static final AtArmorMaterial NETHERITE_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.NETHERITE.durability() * 0.75),
            createDefenseMap(2.2F, 3.2F, 2.8F, 1.8F, 3.2F),
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            2.0F,
            1.0F,
            ItemTags.NETHERITE_TOOL_MATERIALS,
            NETHERITE_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial ADAMANTIUM_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.ADAMANTIUM.durability(),
            createDefenseMap(2.1F, 3.3F, 2.9F, 1.7F, 3.3F),
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            1.0F,
            0.75F,
            AtTags.ADAMANTIUM_TOOL_MATERIALS,
            ADAMANTIUM_MATERIAL_KEY
    );
    public static final AtArmorMaterial ADAMANTIUM_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.ADAMANTIUM.durability() * 0.75),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            1.0F,
            0.75F,
            AtTags.ADAMANTIUM_TOOL_MATERIALS,
            ADAMANTIUM_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial MITHRIL_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.MITHRIL.durability(),
            createDefenseMap(1.9F, 3.0F, 2.6F, 1.5F, 3.0F),
            18,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            0.0F,
            0.5F,
            AtTags.MITHRIL_TOOL_MATERIALS,
            MITHRIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial MITHRIL_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.MITHRIL.durability() * 0.75),
            createDefenseMap(1.5F, 2.3F, 2.0F, 1.2F, 2.3F),
            18,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            0.0F,
            0.5F,
            AtTags.MITHRIL_TOOL_MATERIALS,
            MITHRIL_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial ANCIENT_METAL_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.ANCIENT_METAL.durability(),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            17,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            AtTags.ANCIENT_METAL_TOOL_MATERIALS,
            ANCIENT_METAL_MATERIAL_KEY
    );
    public static final AtArmorMaterial ANCIENT_METAL_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.ANCIENT_METAL.durability() * 0.75),
            createDefenseMap(1.3F, 2.0F, 1.8F, 1.0F, 2.0F),
            17,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            AtTags.ANCIENT_METAL_TOOL_MATERIALS,
            ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial IRON_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.IRON.durability(),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            13,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            ItemTags.IRON_TOOL_MATERIALS,
            IRON_MATERIAL_KEY
    );
    public static final AtArmorMaterial IRON_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.IRON.durability() * 0.75),
            createDefenseMap(1.3F, 2.0F, 1.8F, 1.0F, 2.0F),
            13,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            ItemTags.IRON_TOOL_MATERIALS,
            IRON_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial RUSTED_IRON_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.RUSTED_IRON.durability(),
            createDefenseMap(1.3F, 2.0F, 1.8F, 1.0F, 2.0F),
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            RUSTED_IRON_MATERIAL_KEY
    );
    public static final AtArmorMaterial RUSTED_IRON_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.RUSTED_IRON.durability() * 0.75),
            createDefenseMap(0.83F, 1.3F, 1.2F, 0.67F, 1.3F),
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            RUSTED_IRON_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial COPPER_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.COPPER.durability(),
            createDefenseMap(1.5F, 2.3F, 2.0F, 1.2F, 2.3F),
            7,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            AtTags.COPPER_TOOL_MATERIALS,
            COPPER_MATERIAL_KEY
    );
    public static final AtArmorMaterial COPPER_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.COPPER.durability() * 0.75),
            createDefenseMap(1.0F, 1.7F, 1.5F, 0.83F, 1.7F),
            7,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            AtTags.COPPER_TOOL_MATERIALS,
            COPPER_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial SILVER_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.SILVER.durability(),
            createDefenseMap(1.5F, 2.3F, 2.0F, 1.2F, 2.3F),
            7,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            AtTags.SILVER_TOOL_MATERIALS,
            SILVER_MATERIAL_KEY
    );
    public static final AtArmorMaterial SILVER_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.SILVER.durability() * 0.75),
            createDefenseMap(1.0F, 2.7F, 1.5F, 0.83F, 2.7F),
            7,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            AtTags.SILVER_TOOL_MATERIALS,
            SILVER_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial GOLD_CHAINMAIL_MATERIAL = new AtArmorMaterial(
            AtToolMaterials.GOLD.durability(),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            21,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            ItemTags.GOLD_TOOL_MATERIALS,
            GOLD_CHAINMAIL_MATERIAL_KEY
    );
    public static final AtArmorMaterial GOLD_MATERIAL = new AtArmorMaterial(
            (int) (AtToolMaterials.GOLD.durability() * 0.75),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            21,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            ItemTags.GOLD_TOOL_MATERIALS,
            GOLD_MATERIAL_KEY
    );
    private static Map<EquipmentType, Float> createDefenseMap(float bootsDefense, float leggingsDefense, float chestplateDefense, float helmetDefense, float bodyDefense) {
        return Maps.newEnumMap(
                Map.of(
                        EquipmentType.BOOTS,
                        bootsDefense,
                        EquipmentType.LEGGINGS,
                        leggingsDefense,
                        EquipmentType.CHESTPLATE,
                        chestplateDefense,
                        EquipmentType.HELMET,
                        helmetDefense,
                        EquipmentType.BODY,
                        bodyDefense
                )
        );
    }
}




