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

@Mixin(BeetrootBlock.class)
public abstract class BeetrootBlockMixin extends CropBlock {
    public BeetrootBlockMixin(Properties properties) {
        super(properties);
    }

    @Overwrite
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MMEBlockStateProperties.DISEASE_LEVEL).add(BlockStateProperties.AGE_3);
    }
}
