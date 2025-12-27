package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.entity.AbstractGradeFurnaceBlockEntity;
import com.acuteterror233.mite.block.entity.GradeFurnaceBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GradeFurnaceBlock extends FurnaceBlock {
    private final int grade;
    GradeFurnaceBlock(Settings settings, int grade) {
        super(settings);
        this.grade = grade;
    }
    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof GradeFurnaceBlockEntity) {
            player.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return ((GradeFurnaceBlockEntity) blockEntity).createScreenHandler(syncId, playerInventory);
                }

                @Override
                public Text getDisplayName() {
                    return getName();
                }
            });
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world1, BlockState state1, BlockEntityType<T> type) {
        if (world1 instanceof ServerWorld serverWorld) {
            return validateTicker(type, AtBlocks.GRADE_FURNACE_BLOCK_ENTITY, (world, pos, state, blockEntity) -> AbstractGradeFurnaceBlockEntity.tick(serverWorld, pos, state, blockEntity));
        }
        return null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GradeFurnaceBlockEntity(pos, state, grade);
    }
}
