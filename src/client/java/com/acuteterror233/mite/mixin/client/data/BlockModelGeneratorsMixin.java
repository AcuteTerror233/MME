package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.atinterface.BlockModelGeneratorsExtension;
import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import com.acuteterror233.mite.data.TemplateAnvilModels;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Mixin(BlockModelGenerators.class)
public abstract class BlockModelGeneratorsMixin implements BlockModelGeneratorsExtension {
    @Shadow
    public static MultiVariant plainVariant(ResourceLocation id) {
        return null;
    }
    @Shadow
    public static MultiVariantGenerator createSimpleBlock(Block block, MultiVariant model) {
        return null;
    }
    @Shadow
    @Final
    public ResourceLocation createSuffixedVariant(
            Block block, String string, ModelTemplate modelTemplate, Function<ResourceLocation, TextureMapping> function
    ){
        return null;
    }

    @Shadow @Final public Consumer<BlockModelDefinitionGenerator> blockStateOutput;
    @Shadow @Final public BiConsumer<ResourceLocation, ModelInstance> modelOutput;
    @Shadow @Final private static PropertyDispatch<VariantMutator> ROTATION_HORIZONTAL_FACING_ALT;

    // 四张图,第一张图是基本材质,完整铁砧的注册名,之后三张都是砧顶,注册id+top,
    @Unique
    @Override
    public void MME$registerAnvil(Block intact_anvil, Block chipped_anvil, Block damaged_anvil) {
        MultiVariant weightedVariant1 = plainVariant(TemplateAnvilModels.TEMPLATE_ANVIL.create(intact_anvil, TemplateAnvilModels.TEMPLATE_ANVIL(intact_anvil, intact_anvil), this.modelOutput));
        MultiVariant weightedVariant2 = plainVariant(TemplateAnvilModels.TEMPLATE_ANVIL.create(chipped_anvil, TemplateAnvilModels.TEMPLATE_ANVIL(intact_anvil, chipped_anvil), this.modelOutput));
        MultiVariant weightedVariant3 = plainVariant(TemplateAnvilModels.TEMPLATE_ANVIL.create(damaged_anvil, TemplateAnvilModels.TEMPLATE_ANVIL(intact_anvil, damaged_anvil), this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(intact_anvil, weightedVariant1).with(ROTATION_HORIZONTAL_FACING_ALT));
        this.blockStateOutput.accept(createSimpleBlock(chipped_anvil, weightedVariant2).with(ROTATION_HORIZONTAL_FACING_ALT));
        this.blockStateOutput.accept(createSimpleBlock(damaged_anvil, weightedVariant3).with(ROTATION_HORIZONTAL_FACING_ALT));
    }


    @Unique
    @Override
    public void MME$registerCrop(Block block, Property<Integer> property, int... is) {
        if (property.getPossibleValues().size() != is.length) {
            throw new IllegalArgumentException();
        } else {
            ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
            ResourceLocation path = ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, resourceLocation.getPath());
            Int2ObjectMap<ResourceLocation> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            Int2ObjectMap<ResourceLocation> int2ObjectDiseasesMap = new Int2ObjectOpenHashMap<>();
            Int2ObjectMap<ResourceLocation> int2ObjectWitherMap = new Int2ObjectOpenHashMap<>();
            this.blockStateOutput
                    .accept(
                            MultiVariantGenerator.dispatch(block)
                                    .with(
                                            PropertyDispatch.initial(property, MMEBlockStateProperties.DISEASE_LEVEL)
                                                    .generate(
                                                            (age, diseaseLevel) -> {
                                                                int i = is[age];
                                                                switch (diseaseLevel){
                                                                    case 2 -> {
                                                                        return plainVariant(int2ObjectWitherMap.computeIfAbsent(
                                                                                        i,
                                                                                        integer -> ModelTemplates.CROP.create(path.withPrefix("block/").withSuffix("_wither_stage" + integer), TextureMapping.crop(path.withPrefix("block/crops/").withSuffix("_wither_stage" + integer)), this.modelOutput)
                                                                                )
                                                                        );
                                                                    }
                                                                    case 1 -> {
                                                                        return plainVariant(int2ObjectDiseasesMap.computeIfAbsent(
                                                                                        i,
                                                                                        integer -> ModelTemplates.CROP.create(path.withPrefix("block/").withSuffix("_diseases_stage" + integer), TextureMapping.crop(path.withPrefix("block/crops/").withSuffix("_diseases_stage" + integer)), this.modelOutput)
                                                                                )
                                                                        );
                                                                    }
                                                                    default -> {
                                                                        return plainVariant(int2ObjectMap.computeIfAbsent(
                                                                                        i,
                                                                                        integer -> ModelTemplates.CROP.create(resourceLocation.withPrefix("block/").withSuffix("_stage" + integer), TextureMapping.crop(resourceLocation.withPrefix("block/").withSuffix("_stage" + integer)), this.modelOutput)
                                                                                )
                                                                        );
                                                                    }
                                                                }
                                                            }
                                                    )
                                    )
                    );
        }
    }

    @Unique
    @Override
    public void MME$registerFarmland() {
        TextureMapping farmland = new TextureMapping()
                .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND));
        TextureMapping farmlandMoist = new TextureMapping()
                .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist"));
        TextureMapping farmlandManure = new TextureMapping()
                .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_manure"));
        TextureMapping farmlandManureMoist = new TextureMapping()
                .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_moist_manure"));

        MultiVariant multiVariantFarmlandManureMoist = plainVariant(ModelTemplates.FARMLAND.create(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_manure_moist"), farmlandManureMoist, this.modelOutput));
        MultiVariant multiVariantFarmlandManure = plainVariant(ModelTemplates.FARMLAND.create(ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_manure"), farmlandManure, this.modelOutput));
        MultiVariant multiVariantFarmlandMoist = plainVariant(ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist"), farmlandMoist, this.modelOutput));
        MultiVariant multiVariantFarmland = plainVariant(ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(Blocks.FARMLAND), farmland, this.modelOutput));

        this.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(Blocks.FARMLAND)
                        .with(
                                PropertyDispatch.initial(BlockStateProperties.MOISTURE, MMEBlockStateProperties.FERTILE)
                                        .generate((moisture, fertile) -> {
                                            if (fertile) {
                                                if (moisture != 7){
                                                    return multiVariantFarmlandManure;
                                                }else {
                                                    return multiVariantFarmlandManureMoist;
                                                }
                                            }else {
                                                if (moisture != 7){
                                                    return multiVariantFarmland;
                                                }else {
                                                    return multiVariantFarmlandMoist;
                                                }
                                            }
                                        })
                        )
                );
    }
}
