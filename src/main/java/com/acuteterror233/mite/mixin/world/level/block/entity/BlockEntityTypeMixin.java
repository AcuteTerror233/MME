package com.acuteterror233.mite.mixin.world.level.block.entity;

import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityTypes.class)
public class BlockEntityTypeMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityTypes;register(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;"))
    private static <T extends BlockEntity>BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> key, BlockEntityType.BlockEntitySupplier<? extends T> factory, Block[] validBlocks) {
        if (key.identifier().getPath().equals("enchanting_table")){
            return BlockEntityTypes.register(key, factory, new Block[]{Blocks.ENCHANTING_TABLE, MMEBlocks.EMERALD_ENCHANTING_TABLE});
        }
        return BlockEntityTypes.register(key, factory, validBlocks);
    }
}
