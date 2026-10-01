package com.acuteterror233.mite.mixin.world.entity.animal.fish;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WaterAnimal.class)
public class WaterAnimalMixin {

    @Overwrite
    protected int getBaseExperienceReward(final ServerLevel level) {
        return 0;
    }
}
