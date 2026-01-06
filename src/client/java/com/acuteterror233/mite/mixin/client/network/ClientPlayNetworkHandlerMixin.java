package com.acuteterror233.mite.mixin.client.network;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin implements GetFuelGradeRegistryExtension {
    @Unique private FuelGradeRegistry fuelGradeRegistry;
    @Shadow @Final private FeatureFlagSet enabledFeatures;
    @Shadow @Final private RegistryAccess.Frozen registryAccess;
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Minecraft client, Connection clientConnection, CommonListenerCookie clientConnectionState, CallbackInfo ci){
        this.fuelGradeRegistry = FuelGradeRegistry.createDefault(clientConnectionState.receivedRegistries(), this.enabledFeatures);
    }
    @Inject(method = "handleUpdateTags", at = @At("TAIL"))
    private void onSynchronizeTags(CallbackInfo ci){
        this.fuelGradeRegistry = FuelGradeRegistry.createDefault(registryAccess, this.enabledFeatures);
    }

    @Unique
    @Override
    public FuelGradeRegistry MME$GetFuelGradeRegistry() {
        return this.fuelGradeRegistry;
    }
}
