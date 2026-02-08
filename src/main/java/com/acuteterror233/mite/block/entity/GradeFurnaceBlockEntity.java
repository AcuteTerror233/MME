package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.screen.GradeFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GradeFurnaceBlockEntity extends AbstractGradeFurnaceBlockEntity{
    public GradeFurnaceBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 1);
    }

    public GradeFurnaceBlockEntity(BlockPos pos, BlockState state, int maxCombustionGrade) {
        super(MMEBlocks.GRADE_FURNACE_BLOCK_ENTITY, pos, state, RecipeType.SMELTING, maxCombustionGrade);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.empty();
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new GradeFurnaceMenu(syncId, playerInventory, this, this.propertyDelegate);
    }
}
