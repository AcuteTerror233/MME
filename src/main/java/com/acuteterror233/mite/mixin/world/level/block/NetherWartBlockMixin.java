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

@Mixin(NetherWartBlock.class)
public class NetherWartBlockMixin {
    @Final
    @Shadow
    public static IntegerProperty AGE;
    /**
     * @author AcuteTerror233
     * @reason 添加地狱维度的判断
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
