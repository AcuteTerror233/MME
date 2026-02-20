package com.acuteterror233.mite.block;

import com.acuteterror233.mite.registry.tag.MMETags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class VanillaBlockModify {
    public static final Map<ResourceLocation, Function<BlockBehaviour.Properties, Block>> BLOCK_FACTORY_MODIFY = Map.ofEntries(
            Map.entry(ResourceLocation.withDefaultNamespace("anvil"), settings -> new MMEAnvilBlock(settings, MMETags.IRON_NOT_ALLOWED_MATERIAL)),
            Map.entry(ResourceLocation.withDefaultNamespace("chipped_anvil"), settings -> new MMEAnvilBlock(settings, MMETags.IRON_NOT_ALLOWED_MATERIAL)),
            Map.entry(ResourceLocation.withDefaultNamespace("damaged_anvil"), settings -> new MMEAnvilBlock(settings, MMETags.IRON_NOT_ALLOWED_MATERIAL)),
            Map.entry(ResourceLocation.withDefaultNamespace("crafting_table"), Block::new),
            Map.entry(ResourceLocation.withDefaultNamespace("furnace"), settings -> new GradeFurnaceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .strength(0.3F)
                            .lightLevel(Blocks.litBlockEmission(13))
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("furnace"))), 2)
            ),
            Map.entry(ResourceLocation.withDefaultNamespace("blast_furnace"), settings -> new GradeFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("blast_furnace"))), 0)),
            Map.entry(ResourceLocation.withDefaultNamespace("smoker"), settings -> new GradeFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("smoker"))), 1))
    );
    public static final Map<ResourceLocation, UnaryOperator<BlockBehaviour.Properties>> BLOCK_SETTINGS_MODIFY = Map.ofEntries(
            Map.entry(ResourceLocation.withDefaultNamespace("anvil"),settings -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(0.3F, 1200.0F)
                    .sound(SoundType.ANVIL)
                    .pushReaction(PushReaction.BLOCK)),
            Map.entry(ResourceLocation.withDefaultNamespace("chipped_anvil"),settings -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)),
            Map.entry(ResourceLocation.withDefaultNamespace("damaged_anvil"),settings -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)),
            Map.entry(ResourceLocation.withDefaultNamespace("crafting_table"), settings -> settings.strength(0.3F)),
            Map.entry(ResourceLocation.withDefaultNamespace("obsidian"), settings -> settings.strength(3.0F, 1200.0F)),
            Map.entry(ResourceLocation.withDefaultNamespace("enchanting_table"), properties -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .lightLevel(blockStatex -> 7)
                    .strength(0.3F, 1200.0F)),
            Map.entry(ResourceLocation.withDefaultNamespace("chest"), properties -> properties.strength(0.3F)),
            Map.entry(ResourceLocation.withDefaultNamespace("short_grass"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("fern"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("flowering_azalea"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("bush"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("sugar_cane"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("tall_grass"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("large_fern"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("twisting_vines"), properties -> properties.strength(0.01F)),
            Map.entry(ResourceLocation.withDefaultNamespace("weeping_vines"), properties -> properties.strength(0.01F))
    );
}
