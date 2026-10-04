package com.acuteterror233.mite.mixin.world.entity.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code ItemEntity} — Halves the falling gravity of dropped feather items.
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow
    public abstract ItemStack getItem();

    /** Feathers fall at half the default item gravity; other items are unchanged. */
    @Inject(method = "getDefaultGravity", at = @At("RETURN"), cancellable = true)
    protected void getDefaultGravity(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(cir.getReturnValue() / (getItem().is(Items.FEATHER) ? 2 : 1));
    }
}
