package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.entity.AbstractGradeFurnaceBlockEntity;
import com.acuteterror233.mite.block.entity.GradeFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GradeFurnaceBlock extends FurnaceBlock {
    private final int maxCombustionGrade;
    GradeFurnaceBlock(Properties settings, int maxCombustionGrade) {
        super(settings);
        this.maxCombustionGrade = maxCombustionGrade;
    }
    @Override
    protected void openContainer(Level world, BlockPos pos, Player player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof GradeFurnaceBlockEntity gradeFurnaceBlockEntity) {
            player.openMenu(new MenuProvider() {
                @Override
                public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                    return gradeFurnaceBlockEntity.createMenu(syncId, playerInventory);
                }

                @Override
                public @NotNull Component getDisplayName() {
                    return getName();
                }
            });
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world1, BlockState state1, BlockEntityType<T> type) {
        if (world1 instanceof ServerLevel serverWorld) {
            return createTickerHelper(type, MMEBlocks.GRADE_FURNACE_BLOCK_ENTITY, (world, pos, state, blockEntity) -> AbstractGradeFurnaceBlockEntity.tick(serverWorld, pos, state, blockEntity));
        }
        return null;
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GradeFurnaceBlockEntity(pos, state, this.maxCombustionGrade);
    }
}
