package com.acuteterror233.mite.block;

import com.acuteterror233.mite.item.AtItems;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.screen.CraftingTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ObsidianCraftingTable extends LevelCraftingTableBlock{
    public ObsidianCraftingTable(Settings settings) {
        super(settings);
    }
    @Nullable
    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new CraftingTableScreenHandler(syncId,
                        inventory,
                        ScreenHandlerContext.create(world, pos),
                        getTestRules(AtTags.GOLD_NOT_ALLOWED_MATERIAL, Items.COPPER_INGOT, AtItems.SILVER_INGOT),
                        AtBlocks.OBSIDIAN_CRAFTING_TABLE), getName()
        );
    }
}
