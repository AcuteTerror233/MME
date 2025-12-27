package com.acuteterror233.mite.mixin.server.world;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements GetFuelGradeRegistryExtension {
    @Shadow @Final private MinecraftServer server;
    @Unique
    @Override
    public FuelGradeRegistry getFuelGradeRegistry() {
        return ((GetFuelGradeRegistryExtension)server).getFuelGradeRegistry();
    }
}
