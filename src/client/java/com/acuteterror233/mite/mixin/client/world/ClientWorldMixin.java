package com.acuteterror233.mite.mixin.client.world;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientWorld.class)
public class ClientWorldMixin implements GetFuelGradeRegistryExtension {
    @Shadow @Final private ClientPlayNetworkHandler networkHandler;

    @Unique
    @Override
    public FuelGradeRegistry getFuelGradeRegistry() {
        return ((GetFuelGradeRegistryExtension)networkHandler).getFuelGradeRegistry();
    }

}
