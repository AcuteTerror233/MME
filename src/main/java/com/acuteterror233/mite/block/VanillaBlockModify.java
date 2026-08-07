package com.acuteterror233.mite.block;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 原版方块属性修改器。
 * 在模组初始化时批量修改原版方块的硬度、爆炸抗性、挖掘工具等属性。
 */
public final class VanillaBlockModify {
    public static final Map<Identifier, Function<BlockBehaviour.Properties, Block>> BLOCK_FACTORY_MODIFY = createBlockFactoryModifyMap();
    public static final Map<Identifier, UnaryOperator<BlockBehaviour.Properties>> BLOCK_SETTINGS_MODIFY = createBlockSettingsModifyMap();

    private static Map<Identifier, Function<BlockBehaviour.Properties, Block>> createBlockFactoryModifyMap() {
        Map<Identifier, Function<BlockBehaviour.Properties, Block>> result = new HashMap<>();
        result.put(Identifier.withDefaultNamespace("anvil"), settings -> new MMEAnvilBlock(settings, MMEItemTags.IRON_NOT_ALLOWED_MATERIAL, Blocks.CHIPPED_ANVIL));
        result.put(Identifier.withDefaultNamespace("chipped_anvil"), settings -> new MMEAnvilBlock(settings, MMEItemTags.IRON_NOT_ALLOWED_MATERIAL, Blocks.DAMAGED_ANVIL));
        result.put(Identifier.withDefaultNamespace("damaged_anvil"), settings -> new MMEAnvilBlock(settings, MMEItemTags.IRON_NOT_ALLOWED_MATERIAL, Blocks.AIR));
        result.put(Identifier.withDefaultNamespace("crafting_table"), Block::new);
        result.put(Identifier.withDefaultNamespace("furnace"), settings -> new GradeFurnaceBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.STONE)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .strength(0.3F)
                        .lightLevel(Blocks.litBlockEmission(13))
                        .setId(ResourceKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("furnace"))), 2)
        );
        result.put(Identifier.withDefaultNamespace("blast_furnace"), settings -> new GradeFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).setId(ResourceKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("blast_furnace"))), 4));
        result.put(Identifier.withDefaultNamespace("smoker"), settings -> new GradeFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).setId(ResourceKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("smoker"))), 1));
        return result;
    } 
    private static Map<Identifier, UnaryOperator<BlockBehaviour.Properties>> createBlockSettingsModifyMap() {
        Map<Identifier, UnaryOperator<BlockBehaviour.Properties>> result = new HashMap<>();
        result.put(Identifier.withDefaultNamespace("anvil"),settings -> BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(0.3F, 1200.0F)
                .sound(SoundType.ANVIL)
                .pushReaction(PushReaction.BLOCK));
        result.put(Identifier.withDefaultNamespace("chipped_anvil"),settings -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL));
        result.put(Identifier.withDefaultNamespace("damaged_anvil"),settings -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL));
        result.put(Identifier.withDefaultNamespace("crafting_table"), settings -> settings.strength(0.3F));
        result.put(Identifier.withDefaultNamespace("obsidian"), settings -> settings.strength(2.0F, 1200.0F));
        result.put(Identifier.withDefaultNamespace("enchanting_table"), properties -> BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .lightLevel(blockStatex -> 7)
                .strength(0.3F, 1200.0F));
        result.put(Identifier.withDefaultNamespace("chest"), properties -> properties.strength(0.3F));
        result.put(Identifier.withDefaultNamespace("short_grass"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("fern"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("flowering_azalea"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("bush"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("sugar_cane"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("tall_grass"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("large_fern"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("twisting_vines"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("weeping_vines"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("kelp"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("firefly_bush"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("tall_dry_grass"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("dead_bush"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("short_dry_grass"), properties -> properties.strength(0.01F));
        result.put(Identifier.withDefaultNamespace("wheat"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("potatoes"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("carrots"), properties ->  properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("beetroots"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("melon_stem"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("pumpkin_stem"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("nether_wart"), properties -> properties.strength(0.05F));
        return result;
    }
     
}
