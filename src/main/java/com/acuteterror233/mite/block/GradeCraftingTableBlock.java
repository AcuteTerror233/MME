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

public class GradeCraftingTableBlock extends Block {
    private final Block[] upperLevelCraftingTable;
    private final TagKey<Item> disableMaterialsTag;
    public GradeCraftingTableBlock(Properties settings, TagKey<Item> disableMaterialsTag){
        this(settings, disableMaterialsTag, new Block[0]);
    }

    public GradeCraftingTableBlock(Properties settings, TagKey<Item> disableMaterialsTag, Block... upperLevelCraftingTable) {
        super(settings);
        this.disableMaterialsTag = disableMaterialsTag;
        this.upperLevelCraftingTable = upperLevelCraftingTable;
    }
    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide) {
            player.openMenu(state.getMenuProvider(world, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        }
        return InteractionResult.SUCCESS;
    }
    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider(
                (syncId, inventory, player) -> new GradeCraftingTableMenu(
                        syncId,
                        inventory,
                        ContainerLevelAccess.create(world, pos),
                        this.upperLevelCraftingTable,
                        this.disableMaterialsTag
                ), getName()
        );
    }
}
