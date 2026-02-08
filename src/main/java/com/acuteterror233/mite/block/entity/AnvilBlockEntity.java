package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEBlocks;
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
        put(MMEBlocks.ADAMANTIUM_ANVIL, MMEBlocks.CHIPPED_ADAMANTIUM_ANVIL);
        put(MMEBlocks.CHIPPED_ADAMANTIUM_ANVIL, MMEBlocks.DAMAGED_ADAMANTIUM_ANVIL);
        put(MMEBlocks.DAMAGED_ADAMANTIUM_ANVIL, Blocks.AIR);
        put(MMEBlocks.MITHRIL_ANVIL, MMEBlocks.CHIPPED_MITHRIL_ANVIL);
        put(MMEBlocks.CHIPPED_MITHRIL_ANVIL, MMEBlocks.DAMAGED_MITHRIL_ANVIL);
        put(MMEBlocks.DAMAGED_MITHRIL_ANVIL, Blocks.AIR);
        put(MMEBlocks.ANCIENT_METAL_ANVIL, MMEBlocks.CHIPPED_ANCIENT_METAL_ANVIL);
        put(MMEBlocks.CHIPPED_ANCIENT_METAL_ANVIL, MMEBlocks.DAMAGED_ANCIENT_METAL_ANVIL);
        put(MMEBlocks.DAMAGED_ANCIENT_METAL_ANVIL, Blocks.AIR);
        put(Blocks.ANVIL, Blocks.CHIPPED_ANVIL);
        put(Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
        put(Blocks.DAMAGED_ANVIL, Blocks.AIR);
        put(MMEBlocks.GOLDEN_ANVIL, MMEBlocks.CHIPPED_GOLDEN_ANVIL);
        put(MMEBlocks.CHIPPED_GOLDEN_ANVIL, MMEBlocks.DAMAGED_GOLDEN_ANVIL);
        put(MMEBlocks.DAMAGED_GOLDEN_ANVIL, Blocks.AIR);
        put(MMEBlocks.SILVER_ANVIL, MMEBlocks.CHIPPED_SILVER_ANVIL);
        put(MMEBlocks.CHIPPED_SILVER_ANVIL, MMEBlocks.DAMAGED_SILVER_ANVIL);
        put(MMEBlocks.DAMAGED_SILVER_ANVIL, Blocks.AIR);
        put(MMEBlocks.COPPER_ANVIL, MMEBlocks.CHIPPED_COPPER_ANVIL);
        put(MMEBlocks.CHIPPED_COPPER_ANVIL, MMEBlocks.DAMAGED_COPPER_ANVIL);
        put(MMEBlocks.DAMAGED_COPPER_ANVIL, Blocks.AIR);
    }};
    private Integer maxDamage;
    private Integer damage;

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        super(MMEBlocks.ANVIL_BLOCK_ENTITY, pos, state);
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