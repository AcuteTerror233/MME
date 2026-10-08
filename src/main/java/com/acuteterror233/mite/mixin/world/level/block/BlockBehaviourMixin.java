package com.acuteterror233.mite.mixin.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin for {@code BlockBehaviour} — drastically slows block breaking for all blocks.
 *
 * <p>MITE-style mining rebalance, applied to every block in the game (client-side break progress
 * plus server-side validation): the per-tick divisor of the destroy-speed formula is raised from
 * vanilla's 30/100 to 350 (correct tool) / 15000 (wrong tool), so even soft blocks take many times
 * longer to mine and using the wrong tool is nearly pointless. Unbreakable blocks (-1.0 hardness)
 * still return 0 progress. Fully overwritten via {@code @Overwrite}; implements
 * {@code FeatureElement} to satisfy the vanilla interface contract.</p>
 */
@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin implements FeatureElement {

    /**
     * @author AcuteTerror233
     * @reason Block break speed modification
     */
    @Overwrite
    public float getDestroyProgress(BlockState state, Player player, BlockGetter world, BlockPos pos) {
        float f = state.getDestroySpeed(world, pos);
        if (f == -1.0F || player.getFoodData().getFoodLevel() <= 0) {
            return 0.0F;
        } else {
            // 350 ticks with the correct tool, 15000 without — vanilla uses 30/100.
            int i = player.hasCorrectToolForDrops(state) ? 350 : 15000;
            return player.getDestroySpeed(state) / f / i;
        }
    }
}