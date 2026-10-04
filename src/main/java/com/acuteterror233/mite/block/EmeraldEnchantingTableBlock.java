package com.acuteterror233.mite.block;

import com.acuteterror233.mite.interfaces.EnchantmentMenuExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Emerald enchanting table.
 * Opens the vanilla {@link EnchantmentMenu} with the MME enchantment power capped at 10;
 * the moon phase influence and the upgrade path are applied by {@code EnchantmentMenuMixin}.
 */
public class EmeraldEnchantingTableBlock extends EnchantingTableBlock {
    public EmeraldEnchantingTableBlock(Properties properties) {
        super(properties);
    }

    /** Serves the vanilla {@link EnchantmentMenu} with the enchantment power cap set to 10 via the duck interface. */
    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof EnchantingTableBlockEntity) {
            Component component = ((Nameable)blockEntity).getDisplayName();
            return new SimpleMenuProvider((i, inventory, _) -> {
                EnchantmentMenu menu = new EnchantmentMenu(i, inventory, ContainerLevelAccess.create(level, blockPos));
                ((EnchantmentMenuExtension) menu).MME$SetMaxEnchantmentLevel(10);
                return menu;
            }, component);
        } else {
            return null;
        }
    }
}
