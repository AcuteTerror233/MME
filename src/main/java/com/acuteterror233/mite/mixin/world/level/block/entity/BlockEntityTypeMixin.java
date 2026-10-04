package com.acuteterror233.mite.mixin.world.level.block.entity;

import com.acuteterror233.mite.event.VanillaRegisterModify;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code BlockEntityTypes} — intercepts vanilla block-entity-type registration.
 *
 * <p>A redirect on every {@code register} call inside the static initializer hands the valid-blocks
 * array (the blocks a block entity may live in) to the
 * {@link VanillaRegisterModify#BLOCK_ENTITY_TYPE} event, so MME can extend or replace which blocks
 * host a given block entity (e.g. custom furnaces accepting the furnace block entity). Non-null
 * event results win; otherwise vanilla registration proceeds. Runs during registry setup on both
 * sides.</p>
 */
@Mixin(BlockEntityTypes.class)
public class BlockEntityTypeMixin {
    /**
     * Redirects the {@code BlockEntityTypes#register} calls in the class initializer: consults the
     * {@code BLOCK_ENTITY_TYPE} modify-event for a replacement valid-blocks array.
     *
     * @param key         resource key the block entity type registers under
     * @param factory     supplier constructing the block entity instances
     * @param validBlocks vanilla array of blocks this type is valid on
     * @param <T>         the block entity type being registered
     * @return the registered block entity type (with possibly modified valid blocks)
     */
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityTypes;register(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;"))
    private static <T extends BlockEntity>BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> key, BlockEntityType.BlockEntitySupplier<? extends T> factory, Block[] validBlocks) {
        Block[] modifyValidBlocks = VanillaRegisterModify.BLOCK_ENTITY_TYPE.invoker().ModifyValidBlocks(key, validBlocks);
        if (modifyValidBlocks != null) {
            return BlockEntityTypes.register(key, factory, modifyValidBlocks);
        }
        return BlockEntityTypes.register(key, factory, validBlocks);
    }
}
