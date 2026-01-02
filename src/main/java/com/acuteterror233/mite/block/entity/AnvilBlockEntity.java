package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.AtAnvilBlock;
import com.acuteterror233.mite.block.AtBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;

/**
 * 铁砧方块实体：持久化记录铁砧最大耐久与当前损伤，
 * 并在损伤跨越阈值时切换为破损/裂痕形态或被破坏。
 */
public class AnvilBlockEntity extends BlockEntity {
    private Integer maxDamage;
    private Integer damage;

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        super(AtBlocks.ANVIL_BLOCK_ENTITY, pos, state);
        this.maxDamage = 0;
        this.damage = 0;
    }

    @Override
    /**
     * 将耐久数据写入 NBT。
     */
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("maxDamage", this.maxDamage);
        nbt.putInt("damage", this.damage);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    /**
     * 从 NBT 读取耐久数据。
     */
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.maxDamage = nbt.getInt("maxDamage", 0);
        this.damage = (nbt.getInt("damage", 0));
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

    /**
     * 增加损伤并根据三等分阈值切换铁砧形态或销毁。
     */
    public void addDamage(Integer damage) {
        int newDamage = this.damage + damage;
        this.damage = Math.clamp(newDamage, 0, this.maxDamage);
        int damageThreshold = this.maxDamage / 3;
        BlockState state = this.world.getBlockState(this.pos);
        Block anvil = state.getBlock();
        if (newDamage >= this.maxDamage) {
            this.world.removeBlock(this.pos, false);
            this.world.syncWorldEvent(WorldEvents.ANVIL_USED, pos, 0);
        } else if (newDamage >= damageThreshold * 2) {
            Block block = AtBlocks.ANVIL_MAP.get(anvil);
            generate(block, state);
        } else if (newDamage >= damageThreshold) {
            Block block = AtBlocks.ANVIL_MAP.get(anvil);
            generate(block, state);
        }
    }


    /**
     * 切换当前坐标处的铁砧方块并回填方块实体耐久数据。
     */
    private void generate(Block block, BlockState state) {
        if (this.world != null) {
            this.world.setBlockState(this.pos, block.getDefaultState().with(AtAnvilBlock.FACING, state.get(AtAnvilBlock.FACING)), Block.NOTIFY_ALL);
            BlockEntity entity = this.world.getBlockEntity(this.pos);
            if (entity instanceof AnvilBlockEntity anvilBlockEntity) {
                anvilBlockEntity.setMaxDamage(this.maxDamage);
                anvilBlockEntity.setDamage(this.damage);
            }
            this.world.syncWorldEvent(WorldEvents.ANVIL_DESTROYED, pos, 0);
        }
    }
}