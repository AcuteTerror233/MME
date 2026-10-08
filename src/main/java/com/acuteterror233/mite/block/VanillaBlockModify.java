package com.acuteterror233.mite.block;

import com.acuteterror233.mite.material.MMEMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
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
 * Vanilla block property modifier.
 * Batch-modifies vanilla block hardness, explosion resistance, mining tools, etc. at vanilla
 * bootstrap time: {@code BootstrapMixin} consults these maps through {@code VanillaRegisterModify}
 * as each vanilla block registers, either replacing the block factory outright or adjusting the
 * properties passed to it.
 */
public final class VanillaBlockModify {
    /** Factory replacements keyed by vanilla block id; may be combined with a settings modifier. */
    public static final Map<Identifier, Function<BlockBehaviour.Properties, Block>> BLOCK_FACTORY_MODIFY = createBlockFactoryModifyMap();
    /** Property adjustments keyed by vanilla block id; values may tweak or fully rebuild the properties. */
    public static final Map<Identifier, UnaryOperator<BlockBehaviour.Properties>> BLOCK_SETTINGS_MODIFY = createBlockSettingsModifyMap();

    /**
     * Factory replacements keyed by vanilla block id.
     * Vanilla anvils are rebuilt as {@link MMEAnvilBlock} (each state chaining to the next more
     * damaged one); the crafting table is rebuilt as a plain {@link Block}, dropping its crafting
     * GUI (MME replaces crafting with the grade crafting tables).
     */
    private static Map<Identifier, Function<BlockBehaviour.Properties, Block>> createBlockFactoryModifyMap() {
        Map<Identifier, Function<BlockBehaviour.Properties, Block>> result = new HashMap<>();
        result.put(Identifier.withDefaultNamespace("anvil"), settings -> new MMEAnvilBlock(settings, MMEMaterials.IRON, Blocks.CHIPPED_ANVIL));
        result.put(Identifier.withDefaultNamespace("chipped_anvil"), settings -> new MMEAnvilBlock(settings, MMEMaterials.IRON, Blocks.DAMAGED_ANVIL));
        result.put(Identifier.withDefaultNamespace("damaged_anvil"), settings -> new MMEAnvilBlock(settings, MMEMaterials.IRON, Blocks.AIR));
        result.put(Identifier.withDefaultNamespace("crafting_table"), Block::new);
        result.put(Identifier.withDefaultNamespace("dirt"), properties -> new ColoredFallingBlock(new ColorRGBA(9923917), properties));
        return result;
    }
    /**
     * Property adjustments keyed by vanilla block id.
     * Crop/plant blocks are made faster to break; the anvil becomes easy to mine but
     * blast-resistant and immovable; the furnace is rebuilt from scratch and must re-attach its
     * registry id via {@code setId}.
     */
    private static Map<Identifier, UnaryOperator<BlockBehaviour.Properties>> createBlockSettingsModifyMap() {
        Map<Identifier, UnaryOperator<BlockBehaviour.Properties>> result = new HashMap<>();
        result.put(Identifier.withDefaultNamespace("anvil"),settings -> BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(0.3F, 1200.0F)
                .sound(SoundType.ANVIL)
                .pushReaction(PushReaction.IMMOVEABLE));
        result.put(Identifier.withDefaultNamespace("chipped_anvil"),settings -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL));
        result.put(Identifier.withDefaultNamespace("damaged_anvil"),settings -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL));
        result.put(Identifier.withDefaultNamespace("crafting_table"), settings -> settings.strength(0.3F));
        result.put(Identifier.withDefaultNamespace("obsidian"), settings -> settings.strength(2.0F, 1200.0F));
        result.put(Identifier.withDefaultNamespace("enchanting_table"), properties -> BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .lightLevel(_ -> 7)
                .strength(0.3F, 1200.0F));
        result.put(Identifier.withDefaultNamespace("chest"), properties -> properties.strength(0.3F));
        result.put(Identifier.withDefaultNamespace("short_grass"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("fern"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("flowering_azalea"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("bush"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("sugar_cane"), properties -> properties.strength(1F));
        result.put(Identifier.withDefaultNamespace("tall_grass"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("large_fern"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("twisting_vines"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("weeping_vines"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("kelp"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("firefly_bush"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("tall_dry_grass"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("dead_bush"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("short_dry_grass"), properties -> properties.strength(0.1F));
        result.put(Identifier.withDefaultNamespace("wheat"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("potatoes"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("carrots"), properties ->  properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("beetroots"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("melon_stem"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("pumpkin_stem"), properties -> properties.strength(0.05F));
        result.put(Identifier.withDefaultNamespace("nether_wart"), properties -> properties.strength(0.05F));

        result.put(Identifier.withDefaultNamespace("furnace"), properties ->
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.STONE)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .strength(0.3F)
                        .lightLevel(Blocks.litBlockEmission(13))
                        .setId(ResourceKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("furnace")))
        );
        return result;
    }

}
