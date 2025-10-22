package com.acuteterror233.mite.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class RunePortalBlockEntity extends BlockEntity{
    private BlockPos destinationPos;
    public RunePortalBlockEntity(BlockPos pos, BlockState state) {
        super(AtBlocks.RUNE_PORTAL_BLOCK_ENTITY, pos, state);
    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.put("destinationpos", BlockPos.CODEC, destinationPos);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.get("destinationpos", BlockPos.CODEC).ifPresent(blockPos -> this.destinationPos = blockPos);
        super.readNbt(nbt, registryLookup);
    }

    public void setDestinationPosPos(BlockPos pos) {
        this.destinationPos = pos;
    }

    public BlockPos getDestinationPos() {
        return this.destinationPos;
    }
}
