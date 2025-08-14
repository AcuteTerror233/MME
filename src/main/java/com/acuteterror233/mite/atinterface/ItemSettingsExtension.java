package com.acuteterror233.mite.atinterface;

import com.acuteterror233.mite.item.equipment.At_ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.EquipmentType;

public interface ItemSettingsExtension {
    default Item.Settings hand_axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        return null;
    }
    default Item.Settings mattock(ToolMaterial material, float attackDamage, float attackSpeed) {
        return null;
    }
    default Item.Settings war_hammer(ToolMaterial material, float attackDamage, float attackSpeed) {
        return null;
    }
    default Item.Settings dagger(ToolMaterial material, float attackDamage, float attackSpeed) {
        return null;
    }
    default Item.Settings battle_axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        return null;
    }
    default Item.Settings shears(ToolMaterial material, float attackDamage, float attackSpeed){
        return null;
    }
    default Item.Settings At_armor(At_ArmorMaterial material, EquipmentType type) {
        return null;
    }
}
