package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.atinterface.BlockStateModelGeneratorExtension;
import com.acuteterror233.mite.data.AtModels;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.MultipartModelConditionBuilder;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(BlockStateModelGenerator.class)
public abstract class BlockStateModelGeneratorMixin implements BlockStateModelGeneratorExtension {
    @Shadow
    public static WeightedVariant createWeightedVariant(Identifier id) {
        return null;
    }
    @Shadow
    public static VariantsBlockModelDefinitionCreator createSingletonBlockState(Block block, WeightedVariant model) {
        return null;
    }
    @Shadow
    public static MultipartModelConditionBuilder createMultipartConditionBuilder() {
        return null;
    }
    @Shadow @Final public abstract void registerItemModel(Block block);
    @Shadow @Final public Consumer<BlockModelDefinitionCreator> blockStateCollector;
    @Shadow @Final public BiConsumer<Identifier, ModelSupplier> modelCollector;
    @Shadow @Final public static ModelVariantOperator ROTATE_Y_90;
    @Shadow @Final private static BlockStateVariantMap<ModelVariantOperator> SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS;

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
    // 四张图,第一张图是基本材质,完整铁砧的注册名,之后三张都是砧顶,注册名字+top,
    @Unique
    @Override
    public void registerAnvil(Block intact_anvil, Block chipped_anvil, Block damaged_anvil) {
        WeightedVariant weightedVariant1 = createWeightedVariant(AtModels.TEMPLATE_ANVIL.upload(intact_anvil, AtModels.TEMPLATE_ANVIL(intact_anvil,intact_anvil),this.modelCollector));
        WeightedVariant weightedVariant2 = createWeightedVariant(AtModels.TEMPLATE_ANVIL.upload(chipped_anvil, AtModels.TEMPLATE_ANVIL(intact_anvil,chipped_anvil),this.modelCollector));
        WeightedVariant weightedVariant3 = createWeightedVariant(AtModels.TEMPLATE_ANVIL.upload(damaged_anvil, AtModels.TEMPLATE_ANVIL(intact_anvil,damaged_anvil),this.modelCollector));
        this.blockStateCollector.accept(createSingletonBlockState(intact_anvil, weightedVariant1).coordinate(SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS));
        this.blockStateCollector.accept(createSingletonBlockState(chipped_anvil, weightedVariant2).coordinate(SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS));
        this.blockStateCollector.accept(createSingletonBlockState(damaged_anvil, weightedVariant3).coordinate(SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS));
    }
}
