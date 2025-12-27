package com.acuteterror233.mite.mixin.server;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import com.mojang.datafixers.DataFixer;
import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.ApiServices;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;


@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements GetFuelGradeRegistryExtension {
    @Shadow @Final private CombinedDynamicRegistries<ServerDynamicRegistryType> combinedDynamicRegistries;
    @Shadow @Final protected SaveProperties saveProperties;
    @Unique private FuelGradeRegistry fuelgraderegistry;
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci){
        this.fuelgraderegistry = FuelGradeRegistry.createDefault(this.combinedDynamicRegistries.getCombinedRegistryManager(), this.saveProperties.getEnabledFeatures());
    }
    @Inject(method = "reloadResources", at = @At("TAIL"))
    private void reloadResources(Collection<String> dataPacks, CallbackInfoReturnable<CompletableFuture<Void>> cir){
        this.fuelgraderegistry = FuelGradeRegistry.createDefault(this.combinedDynamicRegistries.getCombinedRegistryManager(), this.saveProperties.getEnabledFeatures());
    }

    @Unique
    @Override
    public FuelGradeRegistry getFuelGradeRegistry() {
        return this.fuelgraderegistry;
    }
}
