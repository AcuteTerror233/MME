package com.acuteterror233.mite.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

/**
 * 符文传送门方块实体：记录目标传送位置并持久化到 NBT。
 */
public class RunePortalBlockEntity extends BlockEntity{
    private BlockPos destinationPos;
    public RunePortalBlockEntity(BlockPos pos, BlockState state) {
        super(AtBlocks.RUNE_PORTAL_BLOCK_ENTITY, pos, state);
    }
    @Override
    /**
     * 写入目标坐标。
     */
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.put("destinationpos", BlockPos.CODEC, destinationPos);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    /**
     * 读取目标坐标。
     */
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.get("destinationpos", BlockPos.CODEC).ifPresent(blockPos -> this.destinationPos = blockPos);
        super.readNbt(nbt, registryLookup);
    }

    /**
     * 设置目标位置。
     */
    public void setDestinationPosPos(BlockPos pos) {
        this.destinationPos = pos;
    }

    /**
     * 获取目标位置。
     */
    public BlockPos getDestinationPos() {
        return this.destinationPos;
    }
}
