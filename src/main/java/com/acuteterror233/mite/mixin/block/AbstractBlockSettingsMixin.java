package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.AbstractBlockSettingsExtension;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractBlock.Settings.class)
public abstract class AbstractBlockSettingsMixin implements AbstractBlockSettingsExtension {
    @Shadow boolean toolRequired;
    @Nullable @Unique Block anvil;
    @Inject(method = "sounds", at = @At("HEAD"))
    public void sounds(BlockSoundGroup soundGroup, CallbackInfoReturnable<AbstractBlock.Settings> cir) {
        if (soundGroup == BlockSoundGroup.WOOD){
            this.toolRequired = true;
        }
    }
    @Override
    public Block getAnvil() {
        return this.anvil;
    }
    @Override
    public AbstractBlock.Settings setAnvil(Block anvil) {
        this.anvil = anvil;
        return (AbstractBlock.Settings) (Object) this;
    }
}