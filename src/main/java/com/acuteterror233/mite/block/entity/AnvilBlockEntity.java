package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEAnvilBlock;
import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.HashMap;
import java.util.Map;

public class AnvilBlockEntity extends BlockEntity {
    public static final Map<Block, Block> ANVIL_MAP = new HashMap<>(){{
        put(MMEBlocks.NETHERITE_ANVIL, MMEBlocks.CHIPPED_NETHERITE_ANVIL);
        put(MMEBlocks.CHIPPED_NETHERITE_ANVIL, MMEBlocks.DAMAGED_NETHERITE_ANVIL);
        put(MMEBlocks.DAMAGED_NETHERITE_ANVIL, Blocks.AIR);
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
    protected void saveAdditional(ValueOutput valueOutput) {
        valueOutput.putInt("maxDamage", this.maxDamage);
        valueOutput.putInt("damage", this.damage);
        super.saveAdditional(valueOutput);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.maxDamage = valueInput.getIntOr("maxDamage", 0);
        this.damage = valueInput.getIntOr("damage", 0);
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
            int oldDamage = this.damage;
            this.damage = Math.clamp(this.damage + Damage, 0, this.maxDamage);
            int DamageThreshold = this.maxDamage / 3;
            if (this.damage >= this.maxDamage) {
                this.level.removeBlock(this.worldPosition, false);
                this.level.levelEvent(LevelEvent.SOUND_ANVIL_USED, worldPosition, 0);
            } else if (oldDamage < (DamageThreshold * 2) && this.damage >= (DamageThreshold * 2) || oldDamage < DamageThreshold && this.damage >= DamageThreshold) {
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