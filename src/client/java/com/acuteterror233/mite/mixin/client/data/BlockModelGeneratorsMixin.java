package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.atinterface.BlockModelGeneratorsExtension;
import com.acuteterror233.mite.data.TemplateAnvilModels;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
}
