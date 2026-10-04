package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import com.acuteterror233.mite.data.TemplateAnvilModels;
import com.acuteterror233.mite.interfaces.BlockModelGeneratorsExtension;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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

/**
 * Client datagen mixin into {@code BlockModelGenerators}, implementing
 * {@link BlockModelGeneratorsExtension} so {@code MMEModelProvider} can generate MME-specific
 * block models/blockstates (metal anvils, crops with disease states, fertilized farmland).
 * Runs only during {@code runDatagen}.
 */
@Mixin(BlockModelGenerators.class)
public abstract class BlockModelGeneratorsMixin implements BlockModelGeneratorsExtension {
    @Shadow
    public static MultiVariant plainVariant(Identifier id) {
        return null;
    }
    @Shadow
    public static MultiVariantGenerator createSimpleBlock(Block block, MultiVariant model) {
        return null;
    }
    @Shadow
    @Final
    public Identifier createSuffixedVariant(
            Block block, String string, ModelTemplate modelTemplate, Function<Identifier, TextureMapping> function
    ){
        return null;
    }

    @Shadow @Final public Consumer<BlockModelDefinitionGenerator> blockStateOutput;
    @Shadow @Final public BiConsumer<Identifier, ModelInstance> modelOutput;
    @Shadow @Final public static PropertyDispatch<VariantMutator> ROTATION_HORIZONTAL_FACING_ALT;

    // Four textures: first is the base material, registry name of intact anvil; the next three are anvil tops, registry id + top
    /**
     * Generates the model and blockstate JSON for one metal's three anvil damage states: each
     * state gets its own model from the shared anvil template (body/particle textures from the
     * intact anvil, top texture per state) and a horizontal facing rotation dispatch.
     *
     * @param intact_anvil   the intact anvil block (provides the shared textures)
     * @param chipped_anvil  the chipped anvil block
     * @param damaged_anvil  the damaged anvil block
     */
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


    /**
     * Generates crop blockstate JSON dispatching on both the age property and MME's disease
     * level: healthy textures follow the vanilla {@code block/<crop>_stageN} scheme, while
     * diseased/withered variants use MME-namespaced models keyed by the age's texture stage.
     *
     * @param block    the crop block
     * @param property the crop age property
     * @param is       texture stage per age value; length must equal the property's value count
     * @throws IllegalArgumentException if {@code is.length} differs from the property's value count
     */
    @Unique
    @Override
    public void MME$registerCrop(Block block, Property<Integer> property, int... is) {
        if (property.getPossibleValues().size() != is.length) {
            throw new IllegalArgumentException();
        } else {
            Identifier resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
            Identifier path = Identifier.fromNamespaceAndPath(MME.MOD_ID, resourceLocation.getPath());
            // Lazy per-stage model caches for each disease level, so models are created at most once.
            Int2ObjectMap<Identifier> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            Int2ObjectMap<Identifier> int2ObjectDiseasesMap = new Int2ObjectOpenHashMap<>();
            Int2ObjectMap<Identifier> int2ObjectWitherMap = new Int2ObjectOpenHashMap<>();
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
                                                                                        integer -> ModelTemplates.CROP.create(path.withPrefix("block/").withSuffix("_wither_stage" + integer), TextureMapping.crop(new Material(path.withPrefix("block/crops/").withSuffix("_wither_stage" + integer))), this.modelOutput)
                                                                                )
                                                                        );
                                                                    }
                                                                    case 1 -> {
                                                                        return plainVariant(int2ObjectDiseasesMap.computeIfAbsent(
                                                                                        i,
                                                                                        integer -> ModelTemplates.CROP.create(path.withPrefix("block/").withSuffix("_diseases_stage" + integer), TextureMapping.crop(new Material(path.withPrefix("block/crops/").withSuffix("_diseases_stage" + integer))), this.modelOutput)
                                                                                )
                                                                        );
                                                                    }
                                                                    default -> {
                                                                        return plainVariant(int2ObjectMap.computeIfAbsent(
                                                                                        i,
                                                                                        integer -> ModelTemplates.CROP.create(resourceLocation.withPrefix("block/").withSuffix("_stage" + integer), TextureMapping.crop(new Material(resourceLocation.withPrefix("block/").withSuffix("_stage" + integer))), this.modelOutput)
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

    /**
     * Generates farmland blockstate JSON dispatching on vanilla {@code MOISTURE} and MME's
     * {@code FERTILE} property, producing four model variants (dry/moist × normal/manured).
     * Side and bottom always use dirt textures; only the top texture differs.
     */
    @Unique
    @Override
    public void MME$registerFarmland() {
        TextureMapping farmland = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND));
        TextureMapping farmlandMoist = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist"));
        TextureMapping farmlandManure = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, new Material(Identifier.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_manure")));
        TextureMapping farmlandManureMoist = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.DIRT))
                .put(TextureSlot.TOP, new Material(Identifier.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_moist_manure")));

        MultiVariant multiVariantFarmlandManureMoist = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP_INDENTED.create(Identifier.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_manure_moist"), farmlandManureMoist, this.modelOutput));
        MultiVariant multiVariantFarmlandManure = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP_INDENTED.create(Identifier.fromNamespaceAndPath(MME.MOD_ID, "block/farmland_manure"), farmlandManure, this.modelOutput));
        MultiVariant multiVariantFarmlandMoist = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP_INDENTED.create(TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist").sprite(), farmlandMoist, this.modelOutput));
        MultiVariant multiVariantFarmland = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP_INDENTED.create(TextureMapping.getBlockTexture(Blocks.FARMLAND).sprite(), farmland, this.modelOutput));

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
