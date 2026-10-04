package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code FarmBlock} (vanilla farmland) — adds the MME {@code FERTILE} state property.
 *
 * <p>The constructor inject registers a default state of dry, non-fertile farmland, and the
 * {@code createBlockStateDefinition} override adds {@link MMEBlockStateProperties#FERTILE} next to
 * the vanilla {@code MOISTURE} property. Fertility is consumed by the crop mixin's growth bonus
 * ({@code CropBlock#getGrowthSpeed}). Runs on both sides since block states are synchronized.</p>
 */
@Mixin(FarmlandBlock.class)
public abstract class FarmBlockMixin extends Block {
    /** Shadowed vanilla moisture property (0 dry .. 7 wet). */
    @Shadow
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    public FarmBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * Injected at the tail of the constructor: defaults farmland to moisture 0 (dry) and
     * non-fertile.
     *
     * @param baseBlock  the dirt-like block farmland reverts to
     * @param properties vanilla constructor properties
     * @param ci         injection callback (unused; never cancelled)
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Block baseBlock, Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0).setValue(MMEBlockStateProperties.FERTILE, false));
    }

    /**
     * Overridden to extend the vanilla state definition with the {@code FERTILE} property.
     *
     * @param builder the state definition builder to add properties to
     */
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE).add(MMEBlockStateProperties.FERTILE);
    }
}
