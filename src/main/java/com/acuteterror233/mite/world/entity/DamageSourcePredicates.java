package com.acuteterror233.mite.world.entity;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class DamageSourcePredicates {
    public static boolean isSilverWeaponOrEnchanted(DamageSource damageSource) {
        ItemStack weapon = damageSource.getWeaponItem();
        if (weapon == null || weapon.is(MMEItemTags.SILVER_TOOLS)) {
            return true;
        }
        ItemEnchantments enchantments = weapon.get(DataComponents.ENCHANTMENTS);
        return enchantments != null && !enchantments.isEmpty();
    }
}
