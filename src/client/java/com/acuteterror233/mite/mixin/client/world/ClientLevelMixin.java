package com.acuteterror233.mite.mixin.client.world;

import com.acuteterror233.mite.atinterface.GetFuelGradeRegistryExtension;
import com.acuteterror233.mite.item.FuelGradeRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientLevel.class)
public class ClientLevelMixin implements GetFuelGradeRegistryExtension {
    @Shadow @Final private ClientPacketListener connection;

    @Unique
    @Override
    public FuelGradeRegistry MME$GetFuelGradeRegistry() {
        return ((GetFuelGradeRegistryExtension)connection).MME$GetFuelGradeRegistry();
    }

}
