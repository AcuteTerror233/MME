package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.atinterface.BlockStateModelGeneratorExtension;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockModelDefinitionCreator;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.MultipartBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.MultipartModelConditionBuilder;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(BlockStateModelGenerator.class)
public abstract class BlockStateModelGeneratorMixin implements BlockStateModelGeneratorExtension {
    @Shadow
    public static WeightedVariant createWeightedVariant(Identifier id) {
        return null;
    }

    @Shadow @Final public Consumer<BlockModelDefinitionCreator> blockStateCollector;
    @Shadow
    public static MultipartModelConditionBuilder createMultipartConditionBuilder() {
        return null;
    }
    @Shadow @Final public void registerItemModel(Block block){}
    @Shadow @Final public static ModelVariantOperator ROTATE_Y_90;

    @Unique
    @Override
    public void registerBars(Block Block) {
        WeightedVariant weightedVariant = createWeightedVariant(ModelIds.getBlockSubModelId(Block, "_post_ends"));
        WeightedVariant weightedVariant2 = createWeightedVariant(ModelIds.getBlockSubModelId(Block, "_post"));
        WeightedVariant weightedVariant3 = createWeightedVariant(ModelIds.getBlockSubModelId(Block, "_cap"));
        WeightedVariant weightedVariant4 = createWeightedVariant(ModelIds.getBlockSubModelId(Block, "_cap_alt"));
        WeightedVariant weightedVariant5 = createWeightedVariant(ModelIds.getBlockSubModelId(Block, "_side"));
        WeightedVariant weightedVariant6 = createWeightedVariant(ModelIds.getBlockSubModelId(Block, "_side_alt"));
        this.blockStateCollector
                .accept(
                        MultipartBlockModelDefinitionCreator.create(Block)
                                .with(weightedVariant)
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, false),
                                        weightedVariant2
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, true).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, false),
                                        weightedVariant3
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, true).put(Properties.SOUTH, false).put(Properties.WEST, false),
                                        weightedVariant3.apply(ROTATE_Y_90)
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, true).put(Properties.WEST, false),
                                        weightedVariant4
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, true),
                                        weightedVariant4.apply(ROTATE_Y_90)
                                )
                                .with(createMultipartConditionBuilder().put(Properties.NORTH, true), weightedVariant5)
                                .with(createMultipartConditionBuilder().put(Properties.EAST, true), weightedVariant5.apply(ROTATE_Y_90))
                                .with(createMultipartConditionBuilder().put(Properties.SOUTH, true), weightedVariant6)
                                .with(createMultipartConditionBuilder().put(Properties.WEST, true), weightedVariant6.apply(ROTATE_Y_90))
                );
        this.registerItemModel(Block);
    }
}
