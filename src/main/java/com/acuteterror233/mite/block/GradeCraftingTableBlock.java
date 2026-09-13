package com.acuteterror233.mite.block;

import com.acuteterror233.mite.inventory.GradeCraftingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Grade crafting table block, restricting the material grade that can be used.
 * Each grade of crafting table can only use items within the corresponding material tag for crafting, with a configurable upgrade target workbench.
 */
public class GradeCraftingTableBlock extends Block {
    private final TagKey<Item> exceptionsTag;
    private final TagKey<Item> disableMaterialsTag;
    private final float craftingSpeedBonus;
    public GradeCraftingTableBlock(Properties settings, TagKey<Item> disableMaterialsTag, TagKey<Item> exceptionsTag, float craftingSpeedBonus) {
        super(settings);
        this.exceptionsTag = exceptionsTag;
        this.disableMaterialsTag = disableMaterialsTag;
        this.craftingSpeedBonus = craftingSpeedBonus;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide()) {
            player.openMenu(state.getMenuProvider(world, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        }
        return InteractionResult.SUCCESS;
    }
    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider(
                (syncId, inventory, _) -> new GradeCraftingTableMenu(
                        syncId,
                        inventory,
                        ContainerLevelAccess.create(world, pos),
                        this,
                        this.exceptionsTag,
                        this.disableMaterialsTag,
                        this.craftingSpeedBonus
                ), getName()
        );
    }
}
