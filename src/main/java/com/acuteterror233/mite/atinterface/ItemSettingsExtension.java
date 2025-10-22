package com.acuteterror233.mite.atinterface;

import com.acuteterror233.mite.item.equipment.AtArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.EquipmentType;

public interface ItemSettingsExtension {
    Item.Settings hand_axe(ToolMaterial material, float attackDamage, float attackSpeed);

    Item.Settings mattock(ToolMaterial material, float attackDamage, float attackSpeed);

    Item.Settings war_hammer(ToolMaterial material, float attackDamage, float attackSpeed);

    Item.Settings dagger(ToolMaterial material, float attackDamage, float attackSpeed);

    Item.Settings battle_axe(ToolMaterial material, float attackDamage, float attackSpeed);

    Item.Settings shears(ToolMaterial material, float attackDamage, float attackSpeed);

    Item.Settings scythe(ToolMaterial material, float attackDamage, float attackSpeed);

    Item.Settings at_armor(AtArmorMaterial material, EquipmentType type);

    boolean getUseMaxDamage();
}
