package com.acuteterror233.mite.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class AnvilBlockEntity extends BlockEntity {
    private Integer damage;
    private Integer maxDamage;
    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        super(AtBlocks.ANVIL_BLOCK_ENTITY, pos, state);
        this.damage = 0;
        this.maxDamage = 0;
    }
    
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("maxDamage", this.maxDamage);
        nbt.putInt("damage", this.damage);
        super.writeNbt(nbt, registryLookup);
    }
    
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.damage = nbt.contains("damage") ? nbt.getInt("damage").orElse(0) : 0;
        this.maxDamage = nbt.contains("maxDamage") ? nbt.getInt("maxDamage").orElse(0) : 0;
    }
    public Integer getMaxDamage() {
        return this.maxDamage;
    }

    public void setMaxDamage(Integer maxDamage) {
        this.maxDamage = maxDamage;
    }

    public Integer getDamage() {
        return this.damage;
    }

    public void setDamage(Integer damage) {
        this.damage = Math.clamp(this.damage + damage, 0, this.maxDamage);
    }
}