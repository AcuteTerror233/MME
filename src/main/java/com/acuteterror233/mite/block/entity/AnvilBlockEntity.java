package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEAnvilBlock;
import com.acuteterror233.mite.block.MMEBlockEntityTypes;
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

/**
 * Anvil block entity.
 * Stores the anvil's damage level and material limit information, controls anvil degradation logic.
 */
public class AnvilBlockEntity extends BlockEntity {
    /**
     * Damage chain for vanilla anvils (and the fallback for any anvil entity created without an
     * explicit {@code damageBlock}): each anvil maps to the next more damaged state, ending in air.
     */
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
    private Block damageBlock;

    /** Creates the entity with zeroed damage, degrading via {@link #ANVIL_MAP}. */
    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        super(MMEBlockEntityTypes.ANVIL, pos, state);
        this.maxDamage = 0;
        this.damage = 0;
    }
    /** Creates the entity bound to a fixed degradation target block. */
    public AnvilBlockEntity(BlockPos pos, BlockState state, Block damageBlock) {
        this(pos, state);
        this.damageBlock = damageBlock;
    }

    /** Persists {@code maxDamage} and {@code damage}. */
    @Override
    protected void saveAdditional(ValueOutput nbt) {
        nbt.putInt("maxDamage", this.maxDamage);
        nbt.putInt("damage", this.damage);
        super.saveAdditional(nbt);
    }

    /** Restores {@code maxDamage} and {@code damage}, defaulting both to 0. */
    @Override
    protected void loadAdditional(ValueInput nbt) {
        super.loadAdditional(nbt);
        this.maxDamage = nbt.getIntOr("maxDamage", 0);
        this.damage = (nbt.getIntOr("damage", 0));
    }

    /** @return the maximum use count before degradation. */
    public Integer getMaxDamage() {
        return this.maxDamage;
    }

    /** Sets the maximum use count before degradation. */
    public void setMaxDamage(Integer maxDamage) {
        this.maxDamage = maxDamage;
    }

    /** @return the accumulated damage. */
    public Integer getDamage() {
        return this.damage;
    }

    /** Sets the damage, clamped to {@code maxDamage}. */
    public void setDamage(Integer damage) {
        this.damage = damage >= this.maxDamage ? this.maxDamage : damage;
    }

    /**
     * Adds use damage; on reaching {@code maxDamage} the anvil degrades.
     * Degrading replaces the block with {@code damageBlock} (or the {@link #ANVIL_MAP} successor
     * when none was set), preserves the facing, plays the anvil-break sound, and carries the
     * leftover damage and max damage onto the replacement block entity.
     *
     * @param Damage damage to add (one use adds 1)
     */
    public void addDamage(Integer Damage) {
        if (this.level != null) {
            this.damage += Damage;
            if (this.damage >= this.maxDamage) {
                BlockState state = this.damageBlock == null ? ANVIL_MAP.getOrDefault(getBlockState().getBlock(), Blocks.AIR).defaultBlockState() : this.damageBlock.defaultBlockState();
                if (state.hasProperty(MMEAnvilBlock.FACING)) {
                    this.level.setBlock(getBlockPos(), state.setValue(MMEAnvilBlock.FACING, getBlockState().getValue(MMEAnvilBlock.FACING)), 2);
                    this.level.levelEvent(LevelEvent.SOUND_ANVIL_BROKEN, worldPosition, 0);
                    if (this.level.getBlockEntity(getBlockPos()) instanceof AnvilBlockEntity blockEntity) {
                        blockEntity.setDamage(this.damage - this.maxDamage);
                        blockEntity.setMaxDamage(this.maxDamage);
                    }
                }else {
                    this.level.removeBlock(getBlockPos(), false);
                    this.level.levelEvent(LevelEvent.SOUND_ANVIL_BROKEN, worldPosition, 0);
                }
            }
        }
    }
}