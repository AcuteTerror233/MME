package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.block.entity.GradeFurnaceBlockEntity;
import com.acuteterror233.mite.block.entity.RunePortalBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

/**
 * MME 方块实体类型注册中心。
 */
public class MMEBlockEntityTypes {

    public static final BlockEntityType<AnvilBlockEntity> ANVIL =
            register(
                    MMEBlockEntityTypeIds.ANVIL
                    , AnvilBlockEntity::new
                    , MMEBlocks.NETHERITE_ANVIL
                    , MMEBlocks.CHIPPED_NETHERITE_ANVIL
                    , MMEBlocks.DAMAGED_NETHERITE_ANVIL
                    , MMEBlocks.ADAMANTIUM_ANVIL
                    , MMEBlocks.CHIPPED_ADAMANTIUM_ANVIL
                    , MMEBlocks.DAMAGED_ADAMANTIUM_ANVIL
                    , MMEBlocks.MITHRIL_ANVIL
                    , MMEBlocks.CHIPPED_MITHRIL_ANVIL
                    , MMEBlocks.DAMAGED_MITHRIL_ANVIL
                    , MMEBlocks.ANCIENT_METAL_ANVIL
                    , MMEBlocks.CHIPPED_ANCIENT_METAL_ANVIL
                    , MMEBlocks.DAMAGED_ANCIENT_METAL_ANVIL
                    , Blocks.ANVIL
                    , Blocks.CHIPPED_ANVIL
                    , Blocks.DAMAGED_ANVIL
                    , MMEBlocks.SILVER_ANVIL
                    , MMEBlocks.CHIPPED_SILVER_ANVIL
                    , MMEBlocks.DAMAGED_SILVER_ANVIL
                    , MMEBlocks.COPPER_ANVIL
                    , MMEBlocks.CHIPPED_COPPER_ANVIL
                    , MMEBlocks.DAMAGED_COPPER_ANVIL
                    , MMEBlocks.GOLDEN_ANVIL
                    , MMEBlocks.CHIPPED_GOLDEN_ANVIL
                    , MMEBlocks.DAMAGED_GOLDEN_ANVIL
            );
    public static final BlockEntityType<RunePortalBlockEntity> RUNE_PORTAL =
            register(
                    MMEBlockEntityTypeIds.RUNE_PORTAL
                    , RunePortalBlockEntity::new
                    , MMEBlocks.RUNE_PORTAL
            );
    public static final BlockEntityType<GradeFurnaceBlockEntity> GRADE_FURNACE =
            register(
                    MMEBlockEntityTypeIds.GRADE_FURNACE
                    , GradeFurnaceBlockEntity::new
                    , MMEBlocks.CLAY_FURNACE
                    , MMEBlocks.HARDENED_CLAY_FURNACE
                    , MMEBlocks.SANDSTONE_FURNACE
                    , MMEBlocks.OBSIDIAN_FURNACE
                    , MMEBlocks.NETHERRACK_FURNACE
                    , Blocks.FURNACE
                    , Blocks.SMOKER
                    , Blocks.BLAST_FURNACE
            );

    private static <T extends BlockEntity> BlockEntityType<T> register(
            final ResourceKey<BlockEntityType<?>> key, final BlockEntityType.BlockEntitySupplier<? extends T> factory, final Block... validBlocks
    ) {
        var id = key.identifier();
        if (validBlocks.length == 0) {
            MME.LOGGER.warn("Block entity type {} requires at least one valid block to be defined!", id);
        }

        if (id.getNamespace().equals("mme")) {
            Util.fetchChoiceType(References.BLOCK_ENTITY, id.getPath());
        }

        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, new BlockEntityType<>(factory, Set.of(validBlocks)));
    }

    public static void init() {
    }
}
