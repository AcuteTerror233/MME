package com.acuteterror233.mite.mixin.client.network;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin implements GetFuelGradeRegistryExtension {
    @Unique private FuelGradeRegistry fuelGradeRegistry;
    @Shadow @Final private FeatureSet enabledFeatures;
    @Shadow @Final private DynamicRegistryManager.Immutable combinedDynamicRegistries;
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(MinecraftClient client, ClientConnection clientConnection, ClientConnectionState clientConnectionState, CallbackInfo ci){
        this.fuelGradeRegistry = FuelGradeRegistry.createDefault(clientConnectionState.receivedRegistries(), this.enabledFeatures);
    }
    @Inject(method = "onSynchronizeTags", at = @At("TAIL"))
    private void onSynchronizeTags(CallbackInfo ci){
        this.fuelGradeRegistry = FuelGradeRegistry.createDefault(combinedDynamicRegistries, this.enabledFeatures);
    }

    @Unique
    @Override
    public FuelGradeRegistry getFuelGradeRegistry() {
        return this.fuelGradeRegistry;
    }
}
