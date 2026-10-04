package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code TorchflowerCropBlock} — adds the MME crop {@code DISEASE_LEVEL} state property.
 *
 * <p>{@code createBlockStateDefinition} is overwritten so every torchflower crop state also carries
 * {@link MMEBlockStateProperties#DISEASE_LEVEL} alongside the vanilla two-stage {@code AGE_1};
 * the shared disease mechanics are driven by {@code CropBlockMixin}. Runs on both sides since
 * block states are synchronized.</p>
 */
@Mixin(TorchflowerCropBlock.class)
public abstract class TorchflowerCropBlockMixin extends CropBlock {
    protected TorchflowerCropBlockMixin(Properties properties) {
        super(properties);
    }


    /**
     * Overwrites vanilla {@code createBlockStateDefinition}: registers the vanilla torchflower age
     * plus the custom disease-level property.
     *
     * @param builder the state definition builder to add properties to
     */
    @Overwrite
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MMEBlockStateProperties.DISEASE_LEVEL).add(BlockStateProperties.AGE_1);
    }
}
