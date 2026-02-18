package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin implements FeatureElement {

    /**
     * @author AcuteTerror233
     * @reason 破坏速度重构
     */
    @Overwrite
    public float getDestroyProgress(BlockState state, Player player, BlockGetter world, BlockPos pos) {
        float f = state.getDestroySpeed(world, pos);
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = player.hasCorrectToolForDrops(state) ? 450 : 20000;
            return player.getDestroySpeed(state) / f / i;
        }
    }
}