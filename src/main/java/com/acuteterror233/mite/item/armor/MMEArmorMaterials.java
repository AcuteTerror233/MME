package com.acuteterror233.mite.item.armor;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.item.MMEToolMaterials;
import com.acuteterror233.mite.item.equipment.MMEArmorMaterial;
import com.acuteterror233.mite.registry.tag.MMETags;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class MMEArmorMaterials {
    public static final ResourceKey<EquipmentAsset> NETHERITE_MATERIAL_KEY = EquipmentAssets.NETHERITE;
    public static final ResourceKey<EquipmentAsset> ADAMANTIUM_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "adamantium"));
    public static final ResourceKey<EquipmentAsset> ADAMANTIUM_CHAINMAIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "adamantium_chainmail"));
    public static final ResourceKey<EquipmentAsset> MITHRIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "mithril"));
    public static final ResourceKey<EquipmentAsset> MITHRIL_CHAINMAIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "mithril_chainmail"));
    public static final ResourceKey<EquipmentAsset> ANCIENT_METAL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal"));
    public static final ResourceKey<EquipmentAsset> ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "ancient_metal_chainmail"));
    public static final ResourceKey<EquipmentAsset> IRON_MATERIAL_KEY = EquipmentAssets.IRON;
    public static final ResourceKey<EquipmentAsset> IRON_CHAINMAIL_MATERIAL_KEY = EquipmentAssets.CHAINMAIL;
    public static final ResourceKey<EquipmentAsset> RUSTED_IRON_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "rusted_iron"));
    public static final ResourceKey<EquipmentAsset> RUSTED_IRON_CHAINMAIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "rusted_iron_chainmail"));
    public static final ResourceKey<EquipmentAsset> COPPER_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "copper"));
    public static final ResourceKey<EquipmentAsset> COPPER_CHAINMAIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "copper_chainmail"));
    public static final ResourceKey<EquipmentAsset> SILVER_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "silver"));
    public static final ResourceKey<EquipmentAsset> SILVER_CHAINMAIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "silver_chainmail"));
    public static final ResourceKey<EquipmentAsset> GOLD_CHAINMAIL_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "gold_chainmail"));
    public static final ResourceKey<EquipmentAsset> GOLD_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "gold"));

    public static final MMEArmorMaterial NETHERITE_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.NETHERITE.durability(),
            createDefenseMap(2.6F, 3.8F, 3.4F, 2.2F, 3.8F),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            2.0F,
            1.0F,
            ItemTags.NETHERITE_TOOL_MATERIALS,
            NETHERITE_MATERIAL_KEY
    );
    public static final MMEArmorMaterial ADAMANTIUM_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.ADAMANTIUM.durability(),
            createDefenseMap(2.1F, 3.3F, 2.9F, 1.7F, 3.3F),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            1.0F,
            0.75F,
            MMETags.ADAMANTIUM_TOOL_MATERIALS,
            ADAMANTIUM_MATERIAL_KEY
    );
    public static final MMEArmorMaterial ADAMANTIUM_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.ADAMANTIUM.durability() * 0.75),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            1.0F,
            0.75F,
            MMETags.ADAMANTIUM_TOOL_MATERIALS,
            ADAMANTIUM_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial MITHRIL_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.MITHRIL.durability(),
            createDefenseMap(1.9F, 3.0F, 2.6F, 1.5F, 3.0F),
            18,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            0.0F,
            0.5F,
            MMETags.MITHRIL_TOOL_MATERIALS,
            MITHRIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial MITHRIL_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.MITHRIL.durability() * 0.75),
            createDefenseMap(1.5F, 2.3F, 2.0F, 1.2F, 2.3F),
            18,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            0.0F,
            0.5F,
            MMETags.MITHRIL_TOOL_MATERIALS,
            MITHRIL_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial ANCIENT_METAL_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.ANCIENT_METAL.durability(),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            17,
            SoundEvents.ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            MMETags.ANCIENT_METAL_TOOL_MATERIALS,
            ANCIENT_METAL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial ANCIENT_METAL_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.ANCIENT_METAL.durability() * 0.75),
            createDefenseMap(1.3F, 2.0F, 1.8F, 1.0F, 2.0F),
            17,
            SoundEvents.ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            MMETags.ANCIENT_METAL_TOOL_MATERIALS,
            ANCIENT_METAL_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial IRON_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.IRON.durability(),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            13,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            ItemTags.IRON_TOOL_MATERIALS,
            IRON_MATERIAL_KEY
    );
    public static final MMEArmorMaterial IRON_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.IRON.durability() * 0.75),
            createDefenseMap(1.3F, 2.0F, 1.8F, 1.0F, 2.0F),
            13,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            ItemTags.IRON_TOOL_MATERIALS,
            IRON_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial RUSTED_IRON_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.RUSTED_IRON.durability(),
            createDefenseMap(1.3F, 2.0F, 1.8F, 1.0F, 2.0F),
            9,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            RUSTED_IRON_MATERIAL_KEY
    );
    public static final MMEArmorMaterial RUSTED_IRON_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.RUSTED_IRON.durability() * 0.75),
            createDefenseMap(0.83F, 1.3F, 1.2F, 0.67F, 1.3F),
            9,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            null,
            RUSTED_IRON_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial COPPER_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.COPPER.durability(),
            createDefenseMap(1.5F, 2.3F, 2.0F, 1.2F, 2.3F),
            7,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            MMETags.COPPER_TOOL_MATERIALS,
            COPPER_MATERIAL_KEY
    );
    public static final MMEArmorMaterial COPPER_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.COPPER.durability() * 0.75),
            createDefenseMap(1.0F, 1.7F, 1.5F, 0.83F, 1.7F),
            7,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            MMETags.COPPER_TOOL_MATERIALS,
            COPPER_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial SILVER_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.SILVER.durability(),
            createDefenseMap(1.5F, 2.3F, 2.0F, 1.2F, 2.3F),
            7,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            MMETags.SILVER_TOOL_MATERIALS,
            SILVER_MATERIAL_KEY
    );
    public static final MMEArmorMaterial SILVER_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.SILVER.durability() * 0.75),
            createDefenseMap(1.0F, 2.7F, 1.5F, 0.83F, 2.7F),
            7,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            0.0F,
            0.0F,
            MMETags.SILVER_TOOL_MATERIALS,
            SILVER_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial GOLD_CHAINMAIL_MATERIAL = new MMEArmorMaterial(
            MMEToolMaterials.GOLD.durability(),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            21,
            SoundEvents.ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            ItemTags.GOLD_TOOL_MATERIALS,
            GOLD_CHAINMAIL_MATERIAL_KEY
    );
    public static final MMEArmorMaterial GOLD_MATERIAL = new MMEArmorMaterial(
            (int) (MMEToolMaterials.GOLD.durability() * 0.75),
            createDefenseMap(1.7F, 2.7F, 2.3F, 1.3F, 2.7F),
            21,
            SoundEvents.ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            ItemTags.GOLD_TOOL_MATERIALS,
            GOLD_MATERIAL_KEY
    );
    private static Map<ArmorType, Float> createDefenseMap(float bootsDefense, float leggingsDefense, float chestplateDefense, float helmetDefense, float bodyDefense) {
        return Maps.newEnumMap(
                Map.of(
                        ArmorType.BOOTS,
                        bootsDefense,
                        ArmorType.LEGGINGS,
                        leggingsDefense,
                        ArmorType.CHESTPLATE,
                        chestplateDefense,
                        ArmorType.HELMET,
                        helmetDefense,
                        ArmorType.BODY,
                        bodyDefense
                )
        );
    }
}




