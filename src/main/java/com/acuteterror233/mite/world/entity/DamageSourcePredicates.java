package com.acuteterror233.mite.world.entity;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * 伤害来源判定工具类。
 * 提供银器武器检测等方法，用于火元素等实体的伤害免疫逻辑。
 */
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
