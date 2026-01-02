package com.acuteterror233.mite.mixin.item.equipment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.equipment.EquipmentType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EquipmentType.class)
public class EquipmentTypeMixin {
    @Mutable
    @Shadow
    @Final
    private int baseMaxDamage;

    @Inject(method = "<init>", at = @At(value = "RETURN", shift = At.Shift.AFTER))
    private void init(String string, int i, EquipmentSlot equipmentSlot, int baseMaxDamage, String name, CallbackInfo ci) {
        switch (name){
            case "helmet":
                this.baseMaxDamage = 5;
                break;
            case "chestplate":
                this.baseMaxDamage = 8;
                break;
            case "leggings":
                this.baseMaxDamage = 7;
                break;
            case "boots":
                this.baseMaxDamage = 4;
                break;
        }
    }
}
