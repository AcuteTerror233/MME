package com.acuteterror233.mite.mixin.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Mixin for {@code FurnaceBlockEntity} — fixes the furnace's default display name.
 *
 * <p>{@code getDefaultName} is overridden to derive the name from the registered furnace block
 * instead of the vanilla constant, so replacement blocks (see {@code BlocksMixin} registration
 * interception) show their own translation keys in the furnace GUI. Applies on both sides; the
 * component is created wherever the menu opens.</p>
 */
@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin extends AbstractFurnaceBlockEntity {
    protected FurnaceBlockEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(type, worldPosition, blockState, recipeType);
    }

    /**
     * Overridden: the default container name is the furnace block's own name.
     *
     * @return the display name component of the placed furnace block
     */
    @Override
    protected @NonNull Component getDefaultName() {
        return getBlockState().getBlock().getName();
    }
}
