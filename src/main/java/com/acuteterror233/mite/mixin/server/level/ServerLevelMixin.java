package com.acuteterror233.mite.mixin.server.level;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerLevel.class)
public class ServerLevelMixin implements GetFuelGradeRegistryExtension {
    @Shadow @Final private MinecraftServer server;
    @Unique
    @Override
    public FuelGradeRegistry MME$GetFuelGradeRegistry() {
        return ((GetFuelGradeRegistryExtension)server).MME$GetFuelGradeRegistry();
    }
}
