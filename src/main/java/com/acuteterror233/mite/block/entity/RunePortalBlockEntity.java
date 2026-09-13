package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/**
 * Rune portal block entity.
 * Stores the portal's target coordinates, supports NBT persistence.
 */
public class RunePortalBlockEntity extends BlockEntity{
    private BlockPos destinationPos;
    public RunePortalBlockEntity(BlockPos pos, BlockState state) {
        super(MMEBlockEntityTypes.RUNE_PORTAL, pos, state);
        this.destinationPos = pos;
    }
    @Override
    protected void saveAdditional(ValueOutput nbt) {
        nbt.store("destinations", BlockPos.CODEC, destinationPos);
        super.saveAdditional(nbt);
    }

    @Override
    protected void loadAdditional(ValueInput nbt) {
        super.loadAdditional(nbt);
        nbt.read("destinations", BlockPos.CODEC).ifPresent(blockPos -> this.destinationPos = blockPos);
    }

    public void setDestinationPosPos(BlockPos pos) {
        this.destinationPos = pos;
    }

    public BlockPos getDestinationPos() {
        return this.destinationPos;
    }
}
