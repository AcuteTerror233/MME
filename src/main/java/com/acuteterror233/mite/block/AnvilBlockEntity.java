package com.acuteterror233.mite.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;

public class AnvilBlockEntity extends BlockEntity {
    private Integer maxDamage;
    private Integer damage;

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        super(AtBlocks.ANVIL_BLOCK_ENTITY, pos, state);
        this.maxDamage = 0;
        this.damage = 0;
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
        this.maxDamage = nbt.getInt("maxDamage").orElse(0);
        this.damage = nbt.getInt("damage").orElse(0);
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
        this.damage = damage;
    }

    public void addDamage(Integer damage) {
        int i = this.damage + damage;
        this.damage = Math.clamp(i, 0, this.maxDamage);
        int i1 = this.maxDamage / 3;
        assert this.world != null;
        Block anvil = this.world.getBlockState(this.pos).getBlock();
        if (i >= this.maxDamage) {
            this.world.removeBlock(this.pos, false);
            this.world.syncWorldEvent(WorldEvents.ANVIL_USED, pos, 0);
        } else if (i >= i1 * 2) {
            Block block = Registries.BLOCK.get(Identifier.of(Registries.BLOCK.getId(anvil).toString().replace("chipped", "damaged")));
            generate(block);
        } else if (i >= i1) {
            Block block = Registries.BLOCK.get(Identifier.of(Registries.BLOCK.getId(anvil).toString().replace(".", ":chipped")));
            generate(block);
        }
    }

    private void generate(Block block) {
        if (this.world != null) {
            this.world.setBlockState(this.pos, block.getDefaultState(), Block.NOTIFY_ALL);
            BlockEntity entity = this.world.getBlockEntity(this.pos);
            if (entity instanceof AnvilBlockEntity anvilBlockEntity) {
                anvilBlockEntity.setMaxDamage(this.maxDamage);
                anvilBlockEntity.setDamage(this.damage);
            }
            this.world.syncWorldEvent(WorldEvents.ANVIL_DESTROYED, pos, 0);
        }
    }
}