package com.acuteterror233.mite.mixin.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CraftingResultInventory.class)
public class CraftingResultInventoryMixin {
    @Overwrite
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }
}
