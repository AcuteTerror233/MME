package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.atinterface.ItemSettingsExtension;
import com.acuteterror233.mite.item.equipment.AtArmorMaterial;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.Settings.class)
public abstract class ItemSettingsMixin implements net.fabricmc.fabric.api.item.v1.FabricItem.Settings, ItemSettingsExtension{

    @Shadow public abstract Item.Settings maxDamage(int maxDamage);
    @Shadow public abstract Item.Settings attributeModifiers(AttributeModifiersComponent attributeModifiersComponent);
    @Shadow public abstract <T> Item.Settings component(ComponentType<T> type, T value);

    @Overwrite
    public Item.Settings tool(ToolMaterial material, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
        return material.applyToolSettings(((Item.Settings) (Object) this), effectiveBlocks, attackDamage, attackSpeed, disableBlockingForSeconds);
    }

    @Overwrite
    public Item.Settings pickaxe(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability() * 3);
        this.tool(material, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.75f, material.attackDamageBonus(), attackDamage, attackSpeed));
    }

    @Overwrite
    public Item.Settings axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability() * 3);
        this.tool(material, BlockTags.AXE_MINEABLE, attackDamage, attackSpeed, 5.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.75f, material.attackDamageBonus(), attackDamage, attackSpeed));
    }

    @Overwrite
    public Item.Settings hoe(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability() * 2);
        this.tool(material, BlockTags.HOE_MINEABLE, attackDamage, attackSpeed, 0.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.75f, material.attackDamageBonus(), attackDamage, attackSpeed));
    }

    @Overwrite
    public Item.Settings shovel(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability());
        this.tool(material, BlockTags.SHOVEL_MINEABLE, attackDamage, attackSpeed, 0.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.75f, material.attackDamageBonus(), attackDamage, attackSpeed));
    }
    @Overwrite
    public Item.Settings sword(ToolMaterial material, float attackDamage, float attackSpeed){
        this.maxDamage(material.durability() * 2);
        material.applySwordSettings((Item.Settings)(Object)this, attackDamage, attackSpeed);
        this.attributeModifiers(CreateInteractionAttributeModifiers(0.5f, material.attackDamageBonus(), attackDamage, attackSpeed));
        return ((Item.Settings)(Object)this);
    }

    @Override
    @Unique
    public Item.Settings hand_axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability());
        this.tool(material, BlockTags.AXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.5f, material.attackDamageBonus(), attackDamage, attackSpeed));
    }

    @Override
    @Unique
    public Item.Settings mattock(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability() * 4);
        this.tool(material, BlockTags.SHOVEL_MINEABLE, attackDamage, attackSpeed, 0.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.75f, material.attackDamageBonus(), attackDamage, attackSpeed));

    }

    @Override
    @Unique
    public Item.Settings war_hammer(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability() * 5);
        this.tool(material, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.75f, material.attackDamageBonus(), attackDamage, attackSpeed));

    }

    @Override
    @Unique
    public Item.Settings dagger(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability());
        material.applySwordSettings((Item.Settings)(Object)this, attackDamage, attackSpeed);
        this.attributeModifiers(CreateInteractionAttributeModifiers(0.25f, material.attackDamageBonus(), attackDamage, attackSpeed));
        return ((Item.Settings)(Object)this);
    }

    @Override
    @Unique
    public Item.Settings battle_axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        this.maxDamage(material.durability() * 4);
        this.tool(material, BlockTags.AXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
        return this.attributeModifiers(CreateInteractionAttributeModifiers(0.75f, material.attackDamageBonus(), attackDamage, attackSpeed));

    }
    @Override
    @Unique
    public Item.Settings shears(ToolMaterial material, float attackDamage, float attackSpeed){
        this.maxDamage(material.durability() * 2);
        this.attributeModifiers(CreateInteractionAttributeModifiers(0.5f, material.attackDamageBonus(), attackDamage, attackSpeed));
        this.component(DataComponentTypes.TOOL, ShearsItem.createToolComponent());
        return ((Item.Settings)(Object)this);
    }
    @Override
    @Unique
    public Item.Settings At_armor(AtArmorMaterial material, EquipmentType type) {
        return this.maxDamage(type.getMaxDamage(material.durability()))
                .attributeModifiers(material.createAttributeModifiers(type))
                .enchantable(material.enchantmentValue())
                .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(type.getEquipmentSlot()).equipSound(material.equipSound()).model(material.assetId()).build())
                .repairable(material.repairIngredient());
    }

    @Unique
    public AttributeModifiersComponent CreateInteractionAttributeModifiers(float InteractionDistance, float attackDamageBonus, float attackDamage, float attackSpeed){
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage + attackDamageBonus, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.BLOCK_INTERACTION_RANGE,
                        new EntityAttributeModifier(At_mite.BASE_BLOCK_INTERACTION_RANGE, InteractionDistance, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(At_mite.BASE_ENTITY_INTERACTION_RANGE, InteractionDistance, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .build();
    }
}