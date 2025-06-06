package com.acuteterror233.mite.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.block.AbstractBlock$Settings")
public abstract class AbstractBlockSettingsMixin {
    @Shadow boolean toolRequired;
    @Inject(method = "sounds", at = @At("HEAD"))
    public void sounds(BlockSoundGroup soundGroup, CallbackInfoReturnable<AbstractBlock.Settings> cir) {
        if (soundGroup == BlockSoundGroup.WOOD){
            this.toolRequired = true;
        }
    }
}
