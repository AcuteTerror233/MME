package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmBlock.class)
public abstract class FarmBlockMixin extends Block {
    @Shadow
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    public FarmBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0).setValue(MMEBlockStateProperties.FERTILE, false));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE).add(MMEBlockStateProperties.FERTILE);
    }
}
