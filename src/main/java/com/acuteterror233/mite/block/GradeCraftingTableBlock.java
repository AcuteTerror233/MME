package com.acuteterror233.mite.block;

import com.acuteterror233.mite.interfaces.TimedCraftingMenuExtension;
import com.acuteterror233.mite.material.MetalMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Grade crafting table block, gating usable materials by its {@link MetalMaterial}.
 * Opens the vanilla {@link CraftingMenu} and injects the metal material through the
 * {@link TimedCraftingMenuExtension} duck interface; CraftingMenuMixin implements the timed
 * crafting on top of it. Each tier's table only crafts with items its material allows.
 */
public class GradeCraftingTableBlock extends Block {
    private final MetalMaterial metal;
    public GradeCraftingTableBlock(Properties settings, MetalMaterial metal) {
        super(settings);
        this.metal = metal;
    }

    /** @return the metal material this table enforces. */
    public MetalMaterial metal() {
        return this.metal;
    }

    /** Opens the crafting menu on use (server side) and awards the crafting-table interaction stat. */
    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide()) {
            player.openMenu(state.getMenuProvider(world, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        }
        return InteractionResult.SUCCESS;
    }
    /** Serves a vanilla {@link CraftingMenu} with this table's {@link MetalMaterial} set via the duck interface. */
    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider(
                (syncId, inventory, _) -> {
                    CraftingMenu menu = new CraftingMenu(syncId, inventory, ContainerLevelAccess.create(world, pos));
                    ((TimedCraftingMenuExtension) menu).MME$SetMetalMaterial(this.metal);
                    return menu;
                }, getName()
        );
    }
}
