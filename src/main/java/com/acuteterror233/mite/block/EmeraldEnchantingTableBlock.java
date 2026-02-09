package com.acuteterror233.mite.block;

import com.acuteterror233.mite.inventory.MMEEnchantmentMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EmeraldEnchantingTableBlock extends EnchantingTableBlock {
    public EmeraldEnchantingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof EnchantingTableBlockEntity) {
            Component component = ((Nameable)blockEntity).getDisplayName();
            return new SimpleMenuProvider((i, inventory, player) -> new MMEEnchantmentMenu(i, inventory, ContainerLevelAccess.create(level, blockPos), 10), component);
        } else {
            return null;
        }
    }
}
