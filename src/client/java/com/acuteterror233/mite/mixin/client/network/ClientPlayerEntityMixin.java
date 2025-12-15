package com.acuteterror233.mite.mixin.client.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    /**
     * @author Acuteterror233
     * @reason 将原版6点FoodLevel以下就禁用疾跑改成0点才禁用疾跑
     */
    @Overwrite
    private boolean canSprint() {
        return this.getVehicle() != null || this.getHungerManager().getFoodLevel() != 0 || this.getAbilities().allowFlying;
    }
}
