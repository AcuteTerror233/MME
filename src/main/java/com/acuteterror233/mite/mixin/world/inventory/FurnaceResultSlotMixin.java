package com.acuteterror233.mite.mixin.world.inventory;

import com.acuteterror233.mite.block.entity.AbstractGradeFurnaceBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceResultSlot.class)
public abstract class FurnaceResultSlotMixin extends Slot {
    @Final
    @Shadow
    private Player player;

    public FurnaceResultSlotMixin(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Inject(method = "checkTakeAchievements", at = @At("HEAD"))
    public void checkTakeAchievements(ItemStack itemStack, CallbackInfo ci) {
        if (this.player instanceof ServerPlayer serverPlayer && this.container instanceof AbstractGradeFurnaceBlockEntity abstractFurnaceBlockEntity) {
            abstractFurnaceBlockEntity.dropExperienceForRecipesUsed(serverPlayer);
        }
    }
}
