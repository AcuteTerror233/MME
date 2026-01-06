package com.acuteterror233.mite.mixin.client.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }

    /**
     * @author Acuteterror233
     * @reason 将原版6点FoodLevel以下就禁用疾跑改成0点才禁用疾跑
     */
    @Overwrite
    private boolean hasEnoughFoodToSprint() {
        return this.getVehicle() != null || this.getFoodData().getFoodLevel() != 0 || this.getAbilities().mayfly;
    }
}
