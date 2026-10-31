package com.acuteterror233.mite.world.entity;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * Damage source predicate utility class.
 * Provides silver weapon detection used by the damage immunity of the shadow, wight,
 * invisible stalker and fire elemental.
 */
public class DamageSourcePredicates {
    /**
     * @param damageSource the incoming damage source to inspect
     * @return {@code true} when the attacking weapon qualifies to bypass silver/enchantment-based
     *         damage immunity: there is no weapon item, the weapon is tagged {@code mme:silver_tools},
     *         or the weapon carries any enchantment.
     */
    public static boolean isSilverWeaponOrEnchanted(DamageSource damageSource) {
        ItemStack weapon = damageSource.getWeaponItem();
        if (weapon == null || weapon.is(MMEItemTags.SILVER_TOOLS)) {
            return true;
        }
        ItemEnchantments enchantments = weapon.get(DataComponents.ENCHANTMENTS);
        return enchantments != null && !enchantments.isEmpty();
    }
}
