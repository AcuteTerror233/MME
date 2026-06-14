package com.acuteterror233.mite.block.entity;

import com.acuteterror233.mite.block.MMEAnvilBlock;
import com.acuteterror233.mite.block.MMEBlockEntityTypes;
import com.acuteterror233.mite.block.MMEBlocks;
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

/**
 * 铁砧方块实体。
 * 存储铁砧的损坏等级和材料限制信息，控制铁砧降级逻辑。
 */
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
    private Block damageBlock;

    public AnvilBlockEntity(BlockPos pos, BlockState state) {
        super(MMEBlockEntityTypes.ANVIL, pos, state);
        this.maxDamage = 0;
        this.damage = 0;
    }
    public AnvilBlockEntity(BlockPos pos, BlockState state, Block damageBlock) {
        this(pos, state);
        this.damageBlock = damageBlock;
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
        this.damage = damage >= this.maxDamage ? this.maxDamage : damage;
    }

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