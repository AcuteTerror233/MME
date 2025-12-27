package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.event.VanillaRegisterModify;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Shadow
    @Final
    public static Block ANVIL;
    @Shadow
    @Final
    public static Block CHIPPED_ANVIL;
    @Shadow
    @Final
    public static Block DAMAGED_ANVIL;

    @Shadow
    private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return null;
    }
    @Inject(method = "register(Lnet/minecraft/registry/RegistryKey;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, CallbackInfoReturnable<Block> cir) {
        Block modify = VanillaRegisterModify.BLOCK_REGISTER.invoker().Modify(key, factory, settings.registryKey(key));
        if (modify != null) {
            cir.setReturnValue(Registry.register(Registries.BLOCK, key, modify));
        }
    }

    @Inject(method = "createLogSettings", at = @At("RETURN"), cancellable = true)
    private static void createLogSettings(MapColor topMapColor, MapColor sideMapColor, BlockSoundGroup sounds, CallbackInfoReturnable<AbstractBlock.Settings> cir) {
        cir.setReturnValue(cir.getReturnValue().requiresTool());
    }
    @Inject(method = "createNetherStemSettings", at = @At("RETURN"), cancellable = true)
    private static void createNetherStemSettings(MapColor mapColor, CallbackInfoReturnable<AbstractBlock.Settings> cir) {
        cir.setReturnValue(cir.getReturnValue().requiresTool());
    }
}