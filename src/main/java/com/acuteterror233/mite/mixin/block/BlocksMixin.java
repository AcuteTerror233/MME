package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.block.AtAnvilBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Shadow @Final public static Block ANVIL;
    @Shadow @Final public static Block CHIPPED_ANVIL;
    @Shadow @Final public static Block DAMAGED_ANVIL;
    @Shadow private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){return null;}
    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 321))
    private static Block ANVIL(String string, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
        return register(string, AtAnvilBlock::new, AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .strength(0.5F, 1200.0F)
                .sounds(BlockSoundGroup.ANVIL)
                .pistonBehavior(PistonBehavior.BLOCK));
    }
    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 322))
    private static Block CHIPPED_ANVIL(String string, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
        return register(string, AtAnvilBlock::new, AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .strength(0.5F, 1200.0F)
                .sounds(BlockSoundGroup.ANVIL)
                .pistonBehavior(PistonBehavior.BLOCK));
    }
    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 323))
    private static Block DAMAGED_ANVIL(String string, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
        return register(string, AtAnvilBlock::new, AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .strength(0.5F, 1200.0F)
                .sounds(BlockSoundGroup.ANVIL)
                .pistonBehavior(PistonBehavior.BLOCK));
    }
}