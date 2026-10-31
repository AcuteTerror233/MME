package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import com.acuteterror233.mite.item.MMEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

/**
 * Onion crop block, a {@link CropBlock} with five growth stages ({@code age 0-4}).
 * Growth is throttled to three of every four random ticks and bone meal advances it by only
 * one third of the vanilla amount. The block also carries the shared
 * {@link MMEBlockStateProperties#DISEASE_LEVEL} property.
 */
public class OnionBlock extends CropBlock {
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    private static final VoxelShape[] SHAPES = Block.boxes(MAX_AGE, age -> Block.column(16.0, 0.0, 2 + age * 2));

    public OnionBlock(final BlockBehaviour.Properties properties) {
        super(properties);
    }

    /** @return the onion's {@link #AGE} property. */
    @Override
    protected @NonNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    /** @return the onion's maximum age, {@value #MAX_AGE}. */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /** @return the onion item, used as this crop's seed. */
    @Override
    protected @NonNull ItemLike getBaseSeedId() {
        return MMEItems.ONION;
    }

    /** Only delegates to vanilla growth on three of four random ticks, throttling growth by 25%. */
    @Override
    protected void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (random.nextInt(MAX_AGE) != 0) {
            super.randomTick(state, level, pos, random);
        }
    }

    /** Bone meal advances the crop by a third of the vanilla amount. */
    @Override
    protected int getBonemealAgeIncrease(final Level level) {
        return super.getBonemealAgeIncrease(level) / 3;
    }

    /** Registers the vanilla {@link #AGE} property plus the shared disease-level property. */
    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE).add(MMEBlockStateProperties.DISEASE_LEVEL);
    }

    /** @return the precomputed shape for the crop's current age. */
    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPES[this.getAge(state)];
    }
}
