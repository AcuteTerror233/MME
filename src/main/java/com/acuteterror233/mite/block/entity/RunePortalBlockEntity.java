package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 符文传送门方块实体。
 * 存储传送门的目标坐标，支持 NBT 持久化。
 */
public class RunePortalBlockEntity extends BlockEntity{
    private BlockPos destinationPos;
    public RunePortalBlockEntity(BlockPos pos, BlockState state) {
        super(MMEBlockEntityTypes.RUNE_PORTAL, pos, state);
        this.destinationPos = pos;
    }
    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        nbt.store("destinations", BlockPos.CODEC, destinationPos);
        super.saveAdditional(nbt, registryLookup);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        nbt.read("destinations", BlockPos.CODEC).ifPresent(blockPos -> this.destinationPos = blockPos);
    }

    public void setDestinationPosPos(BlockPos pos) {
        this.destinationPos = pos;
    }

    public BlockPos getDestinationPos() {
        return this.destinationPos;
    }
}
