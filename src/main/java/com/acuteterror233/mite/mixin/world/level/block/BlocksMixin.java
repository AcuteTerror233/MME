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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

/**
 * Mixin for {@code Blocks} — intercepts vanilla block registration and hardens tool requirements.
 *
 * <p>Two mechanisms: the {@code register} inject runs at head and hands every vanilla block
 * factory to the {@link VanillaRegisterModify#BLOCK_REGISTER} event, letting MME replace a block
 * with a custom subclass before it enters the registry (non-null return wins). The
 * {@code logProperties} / {@code netherStemProperties} return injects force
 * {@code requiresCorrectToolForDrops} on wood/nether-stem material families so their drops demand
 * the proper tool. Both run in the common registration phase (client + server).</p>
 */
@Mixin(Blocks.class)
public class BlocksMixin {
    /**
     * Injected at the head of {@code Blocks#register}: offers the block factory to the
     * {@code BLOCK_REGISTER} modify-event and registers the replacement when one is provided.
     *
     * @param key      resource key the block is registered under
     * @param factory  vanilla factory producing the block from its properties
     * @param settings block behaviour properties (already tagged with the id)
     * @param cir      return callback; set when a modified block replaces the vanilla one
     */
    @Inject(method = "register(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", at = @At(value = "HEAD"), cancellable = true)
    private static void onRegister(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, CallbackInfoReturnable<Block> cir) {
        Block modify = VanillaRegisterModify.BLOCK_REGISTER.invoker().Modify(key, factory, settings.setId(key));
        if (modify != null) {
            cir.setReturnValue(Registry.register(BuiltInRegistries.BLOCK, key, modify));
        }
    }

    /**
     * Injected at the return of {@code Blocks#logProperties}: forces correct-tool drops on the
     * resulting properties (applies to all log-family blocks built from it).
     */
    @Inject(method = "logProperties", at = @At("RETURN"), cancellable = true)
    private static void logProperties(MapColor topMapColor, MapColor sideMapColor, SoundType sounds, CallbackInfoReturnable<BlockBehaviour.Properties> cir) {
        cir.setReturnValue(cir.getReturnValue().requiresCorrectToolForDrops());
    }

    /**
     * Injected at the return of {@code Blocks#netherStemProperties}: forces correct-tool drops on
     * the resulting properties (applies to crimson/warped stem-family blocks).
     */
    @Inject(method = "netherStemProperties", at = @At("RETURN"), cancellable = true)
    private static void netherStemProperties(MapColor mapColor, CallbackInfoReturnable<BlockBehaviour.Properties> cir) {
        cir.setReturnValue(cir.getReturnValue().requiresCorrectToolForDrops());
    }
}