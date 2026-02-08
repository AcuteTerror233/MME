package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrowingPlantHeadBlock.class)
public class GrowingPlantHeadBlockMixin {
    @Mutable
    @Shadow
    @Final
    private double growPerTickProbability;
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(BlockBehaviour.Properties properties, Direction direction, VoxelShape voxelShape, boolean bl, double d, CallbackInfo ci){
        this.growPerTickProbability = d / 2;
    }

}
