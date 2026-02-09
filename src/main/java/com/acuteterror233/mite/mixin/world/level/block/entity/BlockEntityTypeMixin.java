package com.acuteterror233.mite.mixin.world.level.block.entity;

import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;register(Ljava/lang/String;Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;"))
    private static <T extends BlockEntity>BlockEntityType<T> register(String string, BlockEntityType.BlockEntitySupplier<? extends T> blockEntitySupplier, Block[] blocks) {
        if (string.equals("enchanting_table")){
            return BlockEntityType.register(string, blockEntitySupplier, new Block[]{Blocks.ENCHANTING_TABLE, MMEBlocks.EMERALD_ENCHANTING_TABLE});
        }
        return BlockEntityType.register(string, blockEntitySupplier, blocks);
    }
}
