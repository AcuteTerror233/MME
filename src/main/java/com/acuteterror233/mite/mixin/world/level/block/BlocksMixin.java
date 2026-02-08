package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.event.VanillaRegisterModify;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
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
    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return null;
    }
    @Inject(method = "register(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, CallbackInfoReturnable<Block> cir) {
        Block modify = VanillaRegisterModify.BLOCK_REGISTER.invoker().Modify(key, factory, settings.setId(key));
        if (modify != null) {
            cir.setReturnValue(Registry.register(BuiltInRegistries.BLOCK, key, modify));
        }
    }

    @Inject(method = "logProperties", at = @At("RETURN"), cancellable = true)
    private static void logProperties(MapColor topMapColor, MapColor sideMapColor, SoundType sounds, CallbackInfoReturnable<BlockBehaviour.Properties> cir) {
        cir.setReturnValue(cir.getReturnValue().requiresCorrectToolForDrops());
    }
    @Inject(method = "netherStemProperties", at = @At("RETURN"), cancellable = true)
    private static void netherStemProperties(MapColor mapColor, CallbackInfoReturnable<BlockBehaviour.Properties> cir) {
        cir.setReturnValue(cir.getReturnValue().requiresCorrectToolForDrops());
    }
}