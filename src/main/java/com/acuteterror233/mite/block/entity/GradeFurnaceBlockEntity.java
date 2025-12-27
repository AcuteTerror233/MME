package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.screen.GradeFurnaceScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class GradeFurnaceBlockEntity extends AbstractGradeFurnaceBlockEntity{
    public GradeFurnaceBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 1);
    }

    public GradeFurnaceBlockEntity(BlockPos pos, BlockState state, int maxCombustionGrade) {
        super(AtBlocks.GRADE_FURNACE_BLOCK_ENTITY, pos, state, RecipeType.SMELTING, maxCombustionGrade);
    }

    @Override
    protected Text getContainerName() {
        return Text.empty();
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GradeFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
