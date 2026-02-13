package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class RunePortalBlockEntity extends BlockEntity{
    private BlockPos destinationPos;
    public RunePortalBlockEntity(BlockPos pos, BlockState state) {
        super(MMEBlocks.RUNE_PORTAL_BLOCK_ENTITY, pos, state);
    }
    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        valueOutput.store("destinations", BlockPos.CODEC, destinationPos);
        super.saveAdditional(valueOutput);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        valueInput.read("destinations", BlockPos.CODEC).ifPresent(blockPos -> this.destinationPos = blockPos);
    }

    public void setDestinationPosPos(BlockPos pos) {
        this.destinationPos = pos;
    }

    public BlockPos getDestinationPos() {
        return this.destinationPos;
    }
}
