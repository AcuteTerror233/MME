package com.acuteterror233.mite.mixin.world.item.equipment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code ArmorType} — Registers MME custom armor types.
 * Replaces the vanilla per-unit durability of every armor type with much lower MME values, so all
 * armor pieces (vanilla and MME) built on {@code ArmorType} compute durability from the reduced
 * {@code unitDurability}.
 */
@Mixin(ArmorType.class)
public class ArmorTypeMixin {
    /** Per-unit durability used for the armor piece's total durability calculation; lowered well below the vanilla values. */
    @Mutable
    @Shadow
    @Final
    private int unitDurability;

    /** Constructor hook: overrides unit durability per armor slot (helmet 5, chestplate 8, leggings 7, boots 4). */
    @Inject(method = "<init>", at = @At(value = "RETURN", shift = At.Shift.AFTER))
    private void init(String string, int i, EquipmentSlot equipmentSlot, int baseMaxDamage, String name, CallbackInfo ci) {
        switch (name){
            case "helmet":
                this.unitDurability = 5;
                break;
            case "chestplate":
                this.unitDurability = 8;
                break;
            case "leggings":
                this.unitDurability = 7;
                break;
            case "boots":
                this.unitDurability = 4;
                break;
        }
    }
}
