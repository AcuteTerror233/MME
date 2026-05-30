package com.acuteterror233.mite.item.equipment;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.Map;

/**
 * MME 模组盔甲材料数据记录。
 * 封装盔甲材料的耐久倍数、护甲值、附魔能力、修理物品、声音和属性修饰符。
 */
public record MMEArmorMaterial(
        int durability,
        Map<ArmorType, Float> defense,
        int enchantmentValue,
        Holder<SoundEvent> equipSound,
        float toughness,
        float knockbackResistance,
        TagKey<Item> repairIngredient,
        ResourceKey<EquipmentAsset> assetId
) {
    public ItemAttributeModifiers createAttributeModifiers(ArmorType equipmentType) {
        float i = this.defense.getOrDefault(equipmentType, 0.0F);
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup attributeModifierSlot = EquipmentSlotGroup.bySlot(equipmentType.getSlot());
        ResourceLocation identifier = ResourceLocation.withDefaultNamespace("armor." + equipmentType.getName());
        builder.add(Attributes.ARMOR, new AttributeModifier(identifier, i, AttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        builder.add(
                Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(identifier, this.toughness, AttributeModifier.Operation.ADD_VALUE),
                attributeModifierSlot
        );
        if (this.knockbackResistance > 0.0F) {
            builder.add(
                    Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(identifier, this.knockbackResistance, AttributeModifier.Operation.ADD_VALUE),
                    attributeModifierSlot
            );
        }

        return builder.build();
    }
}