package com.acuteterror233.mite.atinterface;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

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
}
