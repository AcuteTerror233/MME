package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code BeetrootBlock} — adds the MME crop {@code DISEASE_LEVEL} state property.
 *
 * <p>{@code createBlockStateDefinition} is overwritten so every beetroot block state also carries
 * {@link MMEBlockStateProperties#DISEASE_LEVEL} alongside the vanilla {@code AGE_3}; the disease
 * mechanic itself (growth, spreading, effects) is driven by {@code CropBlockMixin}. Runs on both
 * sides since block states are synchronized.</p>
 */
@Mixin(BeetrootBlock.class)
public abstract class BeetrootBlockMixin extends CropBlock {
    /**
     * Mirrors {@code BeetrootBlock}'s constructor; required because the mixin extends
     * {@link CropBlock}.
     *
     * @param properties block behaviour properties forwarded to {@link CropBlock}
     */
    public BeetrootBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * Overwrites vanilla {@code createBlockStateDefinition}: registers the vanilla three-stage
     * beetroot age plus the custom disease-level property.
     *
     * @param builder the state definition builder to add properties to
     */
    @Overwrite
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MMEBlockStateProperties.DISEASE_LEVEL).add(BlockStateProperties.AGE_3);
    }
}
