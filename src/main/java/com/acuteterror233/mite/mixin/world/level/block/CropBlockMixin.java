package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends VegetationBlock implements BonemealableBlock {
    @Unique
    private static final IntegerProperty DISEASE_LEVEL = MMEBlockStateProperties.DISEASE_LEVEL;
    @Shadow
    @Final
    public static IntegerProperty AGE;
    @Shadow
    public abstract int getAge(BlockState blockState);
    @Shadow
    public abstract int getMaxAge();
    @Shadow
    public abstract BlockState getStateForAge(int i);
    @Shadow
    protected static float getGrowthSpeed(Block block, BlockGetter blockGetter, BlockPos blockPos) {
        return 0;
    }
    @Shadow
    protected abstract IntegerProperty getAgeProperty();
    @Shadow
    @Final
    public abstract boolean isMaxAge(BlockState blockState);

    protected CropBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.stateDefinition.any().setValue(getAgeProperty(), 0).setValue(DISEASE_LEVEL, 0));
    }
    @Inject(method = "getGrowthSpeed", at = @At("RETURN"), cancellable = true)
    private static void growthSpeed(Block block, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Float> cir) {
        BlockState state = blockGetter.getBlockState(blockPos.below());
        if (state.hasProperty(MMEBlockStateProperties.FERTILE)) {
            cir.setReturnValue(cir.getReturnValueF() + 5);
        }
    }

    @Overwrite
    public boolean isRandomlyTicking(BlockState blockState) {
        return !this.isMaxAge(blockState) || blockState.getValue(DISEASE_LEVEL) != 2;
    }

    @Overwrite
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int diseaseLevel = blockState.getValue(DISEASE_LEVEL);
        if (serverLevel.getRawBrightness(blockPos, 0) >= 9 && diseaseLevel == 0) {
            int i = this.getAge(blockState);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed((CropBlock) (Object)this, serverLevel, blockPos);
                if (randomSource.nextInt((int)(50.0F / f) + 1) == 0) {
                    serverLevel.setBlock(blockPos, this.getStateForAge(i + 1), 2);
                }
            }
            if (randomSource.nextInt(5000) == 0) {
                serverLevel.setBlock(blockPos, blockState.setValue(DISEASE_LEVEL, 1), 2);
            }
        }else {
            BlockPos[] around = new BlockPos[]{blockPos.east(), blockPos.east().north(), blockPos.north(), blockPos.north().west(), blockPos.west(), blockPos.west().south(), blockPos.south().east()};
            for (BlockPos blockPos1 : around) {
                BlockState state = serverLevel.getBlockState(blockPos1);
                if (state.hasProperty(DISEASE_LEVEL) && state.getValue(DISEASE_LEVEL) == 0) {
                    if (randomSource.nextInt(5) == 0) {
                        serverLevel.setBlock(blockPos1, state.setValue(DISEASE_LEVEL, 1), 2);
                    }
                }
            }
            if (randomSource.nextInt(10) == 0 ) {
                serverLevel.setBlock(blockPos, blockState.setValue(DISEASE_LEVEL, 2), 2);
            }
        }
    }

    @Overwrite
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISEASE_LEVEL).add(AGE);
    }
}
