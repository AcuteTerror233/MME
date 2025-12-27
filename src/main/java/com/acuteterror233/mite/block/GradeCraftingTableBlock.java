package com.acuteterror233.mite.block;

import com.acuteterror233.mite.screen.GradeCraftingTableScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GradeCraftingTableBlock extends Block {
    private final Block[] upperLevelCraftingTable;
    private final TagKey<Item> disableMaterialsTag;
    public GradeCraftingTableBlock(Settings settings, TagKey<Item> disableMaterialsTag){
        this(settings, disableMaterialsTag, new Block[0]);
    }

    public GradeCraftingTableBlock(Settings settings, TagKey<Item> disableMaterialsTag, Block... upperLevelCraftingTable) {
        super(settings);
        this.disableMaterialsTag = disableMaterialsTag;
        this.upperLevelCraftingTable = upperLevelCraftingTable;
    }
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        }
        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new GradeCraftingTableScreenHandler(
                        syncId,
                        inventory,
                        ScreenHandlerContext.create(world, pos),
                        this.upperLevelCraftingTable,
                        this.disableMaterialsTag
                ), getName()
        );
    }
}
