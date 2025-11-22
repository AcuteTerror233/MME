package com.acuteterror233.mite.block;

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

public class CopperCraftingTable extends LevelCraftingTableBlock{
    public CopperCraftingTable(Settings settings) {
        super(settings);
    }
    @Nullable
    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new CraftingTableScreenHandler(syncId,
                        inventory,
                        ScreenHandlerContext.create(world, pos),
                        getTestRules(AtTags.COPPER_NOT_ALLOWED_MATERIAL, Items.IRON_INGOT),
                        AtBlocks.COPPER_CRAFTING_TABLE), getName()
        );
    }
}
