package com.acuteterror233.mite.item;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ManureItem extends Item {
    public ManureItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState state = level.getBlockState(blockPos);
        if (!level.isClientSide() && state.is(Blocks.FARMLAND)) {
            level.setBlock(blockPos, state.setValue(MMEBlockStateProperties.FERTILE, true), 2);
            level.levelEvent(1505, blockPos, 15);
            useOnContext.getItemInHand().consume(1, useOnContext.getPlayer());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
