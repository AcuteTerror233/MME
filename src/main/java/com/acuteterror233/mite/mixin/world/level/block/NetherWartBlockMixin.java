package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin for {@code NetherWartBlock} — restricts nether wart growth to MME's underground dimension.
 *
 * <p>{@code randomTick} is fully overwritten: the wart only advances its age (up to 3, 1/20 chance
 * per random tick) while the chunk lives in {@link MMEDimensionTypeRegistrar#UNDERGROUND_LEVEL_KEY};
 * anywhere else random ticks are no-ops, so nether warts no longer grow outside the MME underground
 * level. Server-driven via random ticks.</p>
 */
@Mixin(NetherWartBlock.class)
public class NetherWartBlockMixin {
    /** Shadowed vanilla age property (0 planted .. 3 mature). */
    @Final
    @Shadow
    public static IntegerProperty AGE;

    /**
     * @author AcuteTerror233
     * @reason Adds Nether dimension check
     *
     * <p>Overwrites vanilla {@code randomTick}: applies the vanilla 1/20 age-advance roll only in
     * the MME underground level.</p>
     *
     * @param blockState  the nether wart state being ticked
     * @param serverLevel the server level performing the tick
     * @param blockPos    the wart position
     * @param randomSource the level random source
     */
    @Overwrite
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevel.dimension().equals(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY)) {
            int i = blockState.getValue(AGE);
            if (i < 3 && randomSource.nextInt(20) == 0) {
                blockState = blockState.setValue(AGE, i + 1);
                serverLevel.setBlock(blockPos, blockState, 2);
            }
        }
    }
}
