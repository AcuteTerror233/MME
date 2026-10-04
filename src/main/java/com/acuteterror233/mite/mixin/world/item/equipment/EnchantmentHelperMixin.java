package com.acuteterror233.mite.mixin.world.item.equipment;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code EnchantmentHelper} — Modifies enchantment helper logic.
 * <ul>
 * <li>{@code getEnchantmentCost} is overwritten to keep the classic three-offer cost curve
 * (top offer scaled by twice the cost tier);</li>
 * <li>{@code processMobExperience} is scaled by the current special moon phase dimension
 * attribute (double XP under star/blood/hunt moons, ×1.5 under turbid moons), leaving
 * player kills untouched.</li>
 * </ul>
 */
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    /** Classic enchantment cost curve: 0 cost requires enchantability, the three slots use k/3, k*2/3+1 and max(k, tier*2). */
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
    /** Moon-phase XP multiplier: ×2 under STAR/BLOOD/HUNT moon, ×1.5 under TURBID moon; player deaths are skipped. */
    @Inject(method = "processMobExperience", at = @At("RETURN"), cancellable = true)
    private static void processMobExperience(ServerLevel serverLevel, Entity killer, Entity killed, int amount, CallbackInfoReturnable<Integer> cir) {
        if (killed.is(EntityTypes.PLAYER)) {
            return;
        }
        switch (serverLevel.environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE)) {
            case STAR_MOON, BLOOD_MOON, HUNT_MOON -> cir.setReturnValue(cir.getReturnValueI() * 2);
            case TURBID_MOON ->  cir.setReturnValue((int) (cir.getReturnValueI() * 1.5F));
        }
    }

    }
