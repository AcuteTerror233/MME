package com.acuteterror233.mite.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin implements ToggleableFeature {
    @Overwrite
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        float f = state.getHardness(world, pos);
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = player.canHarvest(state) ? 300 : 20000;
            return player.getBlockBreakingSpeed(state) / f / i;
        }
    }
}
