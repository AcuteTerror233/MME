package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.block.MMEAnvilBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class AnvilBlockEntity extends BlockEntity {
    public static final Map<Block, Block> ANVIL_MAP = new HashMap<>(){{
        put(AtBlocks.ADAMANTIUM_ANVIL, AtBlocks.CHIPPED_ADAMANTIUM_ANVIL);
        put(AtBlocks.CHIPPED_ADAMANTIUM_ANVIL, AtBlocks.DAMAGED_ADAMANTIUM_ANVIL);
        put(AtBlocks.DAMAGED_ADAMANTIUM_ANVIL, Blocks.AIR);
        put(AtBlocks.MITHRIL_ANVIL, AtBlocks.CHIPPED_MITHRIL_ANVIL);
        put(AtBlocks.CHIPPED_MITHRIL_ANVIL, AtBlocks.DAMAGED_MITHRIL_ANVIL);
        put(AtBlocks.DAMAGED_MITHRIL_ANVIL, Blocks.AIR);
        put(AtBlocks.ANCIENT_METAL_ANVIL, AtBlocks.CHIPPED_ANCIENT_METAL_ANVIL);
        put(AtBlocks.CHIPPED_ANCIENT_METAL_ANVIL, AtBlocks.DAMAGED_ANCIENT_METAL_ANVIL);
        put(AtBlocks.DAMAGED_ANCIENT_METAL_ANVIL, Blocks.AIR);
        put(Blocks.ANVIL, Blocks.CHIPPED_ANVIL);
        put(Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
        put(Blocks.DAMAGED_ANVIL, Blocks.AIR);
        put(AtBlocks.GOLDEN_ANVIL, AtBlocks.CHIPPED_GOLDEN_ANVIL);
        put(AtBlocks.CHIPPED_GOLDEN_ANVIL, AtBlocks.DAMAGED_GOLDEN_ANVIL);
        put(AtBlocks.DAMAGED_GOLDEN_ANVIL, Blocks.AIR);
        put(AtBlocks.SILVER_ANVIL, AtBlocks.CHIPPED_SILVER_ANVIL);
        put(AtBlocks.CHIPPED_SILVER_ANVIL, AtBlocks.DAMAGED_SILVER_ANVIL);
        put(AtBlocks.DAMAGED_SILVER_ANVIL, Blocks.AIR);
        put(AtBlocks.COPPER_ANVIL, AtBlocks.CHIPPED_COPPER_ANVIL);
        put(AtBlocks.CHIPPED_COPPER_ANVIL, AtBlocks.DAMAGED_COPPER_ANVIL);
        put(AtBlocks.DAMAGED_COPPER_ANVIL, Blocks.AIR);
    }};
    private Integer maxDamage;
    private Integer damage;

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        super(AtBlocks.ANVIL_BLOCK_ENTITY, pos, state);
        this.maxDamage = 0;
        this.damage = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        nbt.putInt("maxDamage", this.maxDamage);
        nbt.putInt("damage", this.damage);
        super.saveAdditional(nbt, registryLookup);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        this.maxDamage = nbt.getIntOr("maxDamage", 0);
        this.damage = (nbt.getIntOr("damage", 0));
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

    public void addDamage(Integer Damage) {
        if (this.level != null) {
            this.damage = Math.clamp(this.damage + Damage, 0, this.maxDamage);
            int DamageThreshold = this.maxDamage / 3;
            if (this.damage >= this.maxDamage) {
                this.level.removeBlock(this.worldPosition, false);
                this.level.levelEvent(LevelEvent.SOUND_ANVIL_USED, worldPosition, 0);
            } else if (this.damage >= DamageThreshold * 2) {
                generate();
            } else if (this.damage >= DamageThreshold) {
                generate();
            }
        }
    }

    private void generate() {
        Block NewAnvil = ANVIL_MAP.getOrDefault(getBlockState().getBlock(), Blocks.AIR);
        BlockState state = Blocks.AIR.defaultBlockState();
        if (!NewAnvil.equals(Blocks.AIR)) {
            state = NewAnvil.defaultBlockState().setValue(MMEAnvilBlock.FACING, getBlockState().getValue(MMEAnvilBlock.FACING));
        }
        this.level.setBlock(this.worldPosition, state, Block.UPDATE_ALL);
        BlockEntity entity = this.level.getBlockEntity(this.worldPosition);
        if (entity instanceof AnvilBlockEntity anvilBlockEntity) {
            anvilBlockEntity.setMaxDamage(this.maxDamage);
            anvilBlockEntity.setDamage(this.damage);
        }
        this.level.levelEvent(LevelEvent.SOUND_ANVIL_BROKEN, worldPosition, 0);
    }
}