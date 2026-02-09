package com.acuteterror233.mite.mixin.world.item.equipment;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Overwrite
    public static int getEnchantmentCost(RandomSource randomSource, int i, int j, ItemStack itemStack) {
        Enchantable enchantable = itemStack.get(DataComponents.ENCHANTABLE);
        if (enchantable == null) {
            return 0;
        } else {
            int k = randomSource.nextInt(8) + 1 + (j >> 1) + randomSource.nextInt(j + 1);
            if (i == 0) {
                return Math.max(k / 3, 1);
            } else {
                return i == 1 ? k * 2 / 3 + 1 : Math.max(k, j * 2);
            }
        }
    }
}
