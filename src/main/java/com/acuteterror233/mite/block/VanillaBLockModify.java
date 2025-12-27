package com.acuteterror233.mite.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class VanillaBLockModify {
    public static final Map<Identifier, Function<AbstractBlock.Settings, Block>> BLOCK_FACTORY_MODIFY = Map.ofEntries(
            Map.entry(Identifier.ofVanilla("anvil"), AtAnvilBlock::new),
            Map.entry(Identifier.ofVanilla("chipped_anvil"), AtAnvilBlock::new),
            Map.entry(Identifier.ofVanilla("damaged_anvil"), AtAnvilBlock::new),
            Map.entry(Identifier.ofVanilla("crafting_table"), Block::new),
            Map.entry(Identifier.ofVanilla("furnace"), settings -> new GradeFurnaceBlock(settings, 2)),
            Map.entry(Identifier.ofVanilla("blast_furnace"), settings -> new GradeFurnaceBlock(settings, 0)),
            Map.entry(Identifier.ofVanilla("smoker"), settings -> new GradeFurnaceBlock(settings, 1))
    );
    public static final Map<Identifier, UnaryOperator<AbstractBlock.Settings>> BLOCK_SETTINGS_MODIFY = Map.ofEntries(
            Map.entry(Identifier.ofVanilla("anvil"),settings -> AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(0.5F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK)),
            Map.entry(Identifier.ofVanilla("chipped_anvil"),settings -> AbstractBlock.Settings.copy(Blocks.ANVIL)),
            Map.entry(Identifier.ofVanilla("damaged_anvil"),settings -> AbstractBlock.Settings.copy(Blocks.ANVIL)),
            Map.entry(Identifier.ofVanilla("crafting_table"), settings -> settings.strength(0.5F)),
            Map.entry(Identifier.ofVanilla("obsidian"), settings -> settings.strength(5.0F, 1200.0F))

    );
}
