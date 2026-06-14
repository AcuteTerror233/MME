package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlockEntityTypes;
import com.acuteterror233.mite.inventory.GradeBlastFurnaceMenu;
import com.acuteterror233.mite.inventory.GradeFurnaceMenu;
import com.acuteterror233.mite.inventory.GradeSmokerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * 等级熔炉方块实体，继承抽象等级熔炉逻辑。
 * 根据方块类型自动选择对应的配方类型（烧炼/烟熏/爆破）。
 */
public class GradeFurnaceBlockEntity extends AbstractGradeFurnaceBlockEntity {

    public GradeFurnaceBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 1);
    }

    public GradeFurnaceBlockEntity(BlockPos pos, BlockState state, int maxCombustionGrade) {
        super(MMEBlockEntityTypes.GRADE_FURNACE, pos, state, getRecipeType(state), maxCombustionGrade);
    }

    private static RecipeType<? extends AbstractCookingRecipe> getRecipeType(BlockState state) {
        if (state.is(Blocks.SMOKER)) {
            return RecipeType.SMOKING;
        } else if (state.is(Blocks.BLAST_FURNACE)) {
            return RecipeType.BLASTING;
        }
        return RecipeType.SMELTING;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.empty();
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        if (this.getBlockState().is(Blocks.SMOKER)) {
            return new GradeSmokerMenu(syncId, playerInventory, this, this.propertyDelegate);
        } else if (this.getBlockState().is(Blocks.BLAST_FURNACE)) {
            return new GradeBlastFurnaceMenu(syncId, playerInventory, this, this.propertyDelegate);
        }
        return new GradeFurnaceMenu(syncId, playerInventory, this, this.propertyDelegate);
    }
}
