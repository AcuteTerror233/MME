package com.acuteterror233.mite.mixin.server;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import com.mojang.datafixers.DataFixer;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
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
    @Shadow @Final private LayeredRegistryAccess<RegistryLayer> registries;
    @Shadow @Final protected WorldData worldData;
    @Unique private FuelGradeRegistry fuelgraderegistry;
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Thread serverThread, LevelStorageSource.LevelStorageAccess session, PackRepository dataPackManager, WorldStem saveLoader, Proxy proxy, DataFixer dataFixer, Services apiServices, ChunkProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci){
        this.fuelgraderegistry = FuelGradeRegistry.createDefault(this.registries.compositeAccess(), this.worldData.enabledFeatures());
    }
    @Inject(method = "reloadResources", at = @At("TAIL"))
    private void reloadResources(Collection<String> dataPacks, CallbackInfoReturnable<CompletableFuture<Void>> cir){
        this.fuelgraderegistry = FuelGradeRegistry.createDefault(this.registries.compositeAccess(), this.worldData.enabledFeatures());
    }

    @Unique
    @Override
    public FuelGradeRegistry MME$GetFuelGradeRegistry() {
        return this.fuelgraderegistry;
    }
}
