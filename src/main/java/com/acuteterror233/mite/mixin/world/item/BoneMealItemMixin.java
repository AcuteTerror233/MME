package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin {
    /**
     * @author AcuteTerror233
     * @reason 修改骨粉的行为
     */
    @Overwrite
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState state = level.getBlockState(blockPos);
        if (!level.isClientSide() && state.getBlock() instanceof CropBlock && state.hasProperty(MMEBlockStateProperties.DISEASE_LEVEL) && state.getValue(MMEBlockStateProperties.DISEASE_LEVEL) == 1) {
            level.setBlock(blockPos, state.setValue(MMEBlockStateProperties.DISEASE_LEVEL, 0), 2);
            level.levelEvent(1505, blockPos, 15);
            useOnContext.getItemInHand().consume(1, useOnContext.getPlayer());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
